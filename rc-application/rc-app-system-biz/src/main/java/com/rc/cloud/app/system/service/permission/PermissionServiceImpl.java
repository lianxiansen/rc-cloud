package com.rc.cloud.app.system.service.permission;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.rc.cloud.app.system.api.permission.dto.DeptDataPermissionRespDTO;
import com.rc.cloud.app.system.api.permission.entity.SysMenuDO;
import com.rc.cloud.app.system.api.user.entity.SysUserDO;
import com.rc.cloud.app.system.common.datapermission.core.annotation.DataPermission;
import com.rc.cloud.app.system.mapper.permission.MenuMapper;
import com.rc.cloud.app.system.mapper.permission.RoleMenuMapper;
import com.rc.cloud.app.system.mapper.permission.UserRoleMapper;
import com.rc.cloud.app.system.api.dept.entity.SysDeptDO;
import com.rc.cloud.app.system.api.permission.entity.SysRoleDO;
import com.rc.cloud.app.system.api.permission.entity.SysRoleMenuDO;
import com.rc.cloud.app.system.api.permission.entity.SysUserRoleDO;
import com.rc.cloud.app.system.service.dept.DeptService;
import com.rc.cloud.app.system.service.user.AdminUserService;
import com.rc.cloud.app.system.enums.permission.DataScopeEnum;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.core.util.collection.CollectionUtils;
import com.rc.cloud.common.core.util.collection.MapUtils;
import com.rc.cloud.common.core.util.json.JsonUtils;
import com.rc.cloud.common.tenant.core.aop.TenantIgnore;
import com.rc.cloud.common.tenant.core.util.TenantUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.function.Supplier;

import static com.rc.cloud.common.core.util.collection.CollectionUtils.convertSet;
import static java.util.Collections.singleton;

/**
 * 权限 Service 实现类
 * time: 2023-06-09
 * @author oliveoil
 */
@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    /**
     * 角色编号与菜单编号的缓存映射
     * key：角色编号
     * value：菜单编号的数组
     *
     * 这里声明 volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    @Getter
    @Setter // 单元测试需要
    private volatile Multimap<Long, Long> roleMenuCache;
    /**
     * 菜单编号与角色编号的缓存映射
     * key：菜单编号
     * value：角色编号的数组
     *
     * 这里声明 volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    @Getter
    @Setter // 单元测试需要
    private volatile Multimap<Long, Long> menuRoleCache;

    /**
     * 用户编号与角色编号的缓存映射
     * key：用户编号
     * value：角色编号的数组
     *
     * 这里声明 volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    @Getter
    @Setter // 单元测试需要
    private volatile Map<Long, Set<Long>> userRoleCache;

    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private DeptService deptService;
    @Resource
    private AdminUserService adminUserService;

//    @Resource
//    private PermissionProducer permissionProducer;

    @Override
    @PostConstruct
    public void initLocalCache() {
        initLocalCacheForRoleMenu();
        initLocalCacheForUserRole();
    }

    /**
     * 刷新 RoleMenu 本地缓存
     */
    @VisibleForTesting
    void initLocalCacheForRoleMenu() {
        // 注意：忽略自动多租户，因为要全局初始化缓存
        TenantUtils.executeIgnore(() -> {
            // 第一步：查询数据
            List<SysRoleMenuDO> roleMenus = roleMenuMapper.selectList();
            log.info("[initLocalCacheForRoleMenu][缓存角色与菜单，数量为:{}]", roleMenus.size());

            // 第二步：构建缓存
            ImmutableMultimap.Builder<Long, Long> roleMenuCacheBuilder = ImmutableMultimap.builder();
            ImmutableMultimap.Builder<Long, Long> menuRoleCacheBuilder = ImmutableMultimap.builder();
            roleMenus.forEach(roleMenuDO -> {
                roleMenuCacheBuilder.put(roleMenuDO.getRoleId(), roleMenuDO.getMenuId());
                menuRoleCacheBuilder.put(roleMenuDO.getMenuId(), roleMenuDO.getRoleId());
            });
            roleMenuCache = roleMenuCacheBuilder.build();
            menuRoleCache = menuRoleCacheBuilder.build();
        });
    }

    /**
     * 刷新 UserRole 本地缓存
     */
    @VisibleForTesting
    void initLocalCacheForUserRole() {
        // 注意：忽略自动多租户，因为要全局初始化缓存
        TenantUtils.executeIgnore(() -> {
            // 第一步：加载数据
            List<SysUserRoleDO> userRoles = userRoleMapper.selectList();
            log.info("[initLocalCacheForUserRole][缓存用户与角色，数量为:{}]", userRoles.size());

            // 第二步：构建缓存。
            ImmutableMultimap.Builder<Long, Long> userRoleCacheBuilder = ImmutableMultimap.builder();
            userRoles.forEach(userRoleDO -> userRoleCacheBuilder.put(userRoleDO.getUserId(), userRoleDO.getRoleId()));
            userRoleCache = CollectionUtils.convertMultiMap2(userRoles, SysUserRoleDO::getUserId, SysUserRoleDO::getRoleId);
        });
    }

    @Override
    public List<SysMenuDO> getRoleMenuListFromCache(Collection<Long> roleIds, Collection<Integer> menuTypes,
                                                    Collection<Integer> menusStatuses) {
        // 任一一个参数为空时，不返回任何菜单
        if (CollectionUtils.isAnyEmpty(roleIds, menuTypes, menusStatuses)) {
            return Collections.emptyList();
        }

        // 判断角色是否包含超级管理员。如果是超级管理员，获取到全部
        List<SysRoleDO> roleList = roleService.getRoleListFromCache(roleIds);
        if (roleService.hasAnySuperAdmin(roleList)) {
            return menuService.getMenuListFromCache(menuTypes, menusStatuses);
        }

        // 获得角色拥有的菜单关联
        List<Long> menuIds = MapUtils.getList(roleMenuCache, roleIds);
        return menuService.getMenuListFromCache(menuIds, menuTypes, menusStatuses);
    }

    @Override
    public Set<Long> getUserRoleIdsFromCache(Long userId, Collection<Integer> roleStatuses) {
        Set<Long> cacheRoleIds = userRoleCache.get(userId);
        // 创建用户的时候没有分配角色，会存在空指针异常
        if (CollUtil.isEmpty(cacheRoleIds)) {
            return Collections.emptySet();
        }
        Set<Long> roleIds = new HashSet<>(cacheRoleIds);
        // 过滤角色状态
        if (CollectionUtil.isNotEmpty(roleStatuses)) {
            roleIds.removeIf(roleId -> {
                SysRoleDO role = roleService.getRoleFromCache(roleId);
                return role == null || !roleStatuses.contains(role.getStatus());
            });
        }
        return roleIds;
    }

    @Override
    public Set<Long> getRoleMenuIds(Long roleId) {
        // 如果是管理员的情况下，获取全部菜单编号
        if (roleService.hasAnySuperAdmin(Collections.singleton(roleId))) {
            return convertSet(menuService.getMenuList(), SysMenuDO::getId);
        }
        // 如果是非管理员的情况下，获得拥有的菜单编号
        return convertSet(roleMenuMapper.selectListByRoleId(roleId), SysRoleMenuDO::getMenuId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignRoleMenu(Long roleId, Set<Long> menuIds) {
        // 获得角色拥有菜单编号
        Set<Long> dbMenuIds = convertSet(roleMenuMapper.selectListByRoleId(roleId),
                SysRoleMenuDO::getMenuId);
        // 计算新增和删除的菜单编号
        Collection<Long> createMenuIds = CollUtil.subtract(menuIds, dbMenuIds);
        Collection<Long> deleteMenuIds = CollUtil.subtract(dbMenuIds, menuIds);
        // 执行新增和删除。对于已经授权的菜单，不用做任何处理
        if (!CollectionUtil.isEmpty(createMenuIds)) {
            roleMenuMapper.insertBatch(CollectionUtils.convertList(createMenuIds, menuId -> {
                SysRoleMenuDO entity = new SysRoleMenuDO();
                entity.setRoleId(roleId);
                entity.setMenuId(menuId);
                return entity;
            }));
        }
        if (!CollectionUtil.isEmpty(deleteMenuIds)) {
            roleMenuMapper.deleteListByRoleIdAndMenuIds(roleId, deleteMenuIds);
        }
        // 发送刷新消息. 注意，需要事务提交后，在进行发送刷新消息。不然 db 还未提交，结果缓存先刷新了
//        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
//
//            @Override
//            public void afterCommit() {
//                permissionProducer.sendRoleMenuRefreshMessage();
//            }
//
//        });
    }

    @Override
    public Set<Long> getUserRoleIdListByUserId(Long userId) {
        return convertSet(userRoleMapper.selectListByUserId(userId),
                SysUserRoleDO::getRoleId);
    }

    @Override
    public Set<String> getPermissionListByUserId(Long userId) {
        // 从用户角色表中查询角色id
        Set<Long> roleIds = getUserRoleIdListByUserId(userId);
        if (CollectionUtil.isEmpty(roleIds)) {
            return Collections.emptySet();
        }
        // 从角色菜单表中查询菜单id
        Set<Long> menuIds = roleMenuMapper.getMenuIdsByRoleIds(roleIds);
        if (CollectionUtil.isEmpty(menuIds)) {
            return Collections.emptySet();
        }
        // 从菜单表中查询权限列表
        return menuMapper.getMenuPermissionListByMenuIds(menuIds);
    }

    @Override
    public Set<Long> getUserRoleIdListByRoleIds(Collection<Long> roleIds) {
        return convertSet(userRoleMapper.selectListByRoleIds(roleIds),
                SysUserRoleDO::getUserId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignUserRole(Long userId, Set<Long> roleIds) {
        // 获得角色拥有角色编号
        Set<Long> dbRoleIds = convertSet(userRoleMapper.selectListByUserId(userId),
                SysUserRoleDO::getRoleId);
        // 计算新增和删除的角色编号
        Collection<Long> createRoleIds = CollUtil.subtract(roleIds, dbRoleIds);
        Collection<Long> deleteMenuIds = CollUtil.subtract(dbRoleIds, roleIds);
        // 执行新增和删除。对于已经授权的角色，不用做任何处理
        if (!CollectionUtil.isEmpty(createRoleIds)) {
            userRoleMapper.insertBatch(CollectionUtils.convertList(createRoleIds, roleId -> {
                SysUserRoleDO entity = new SysUserRoleDO();
                entity.setUserId(userId);
                entity.setRoleId(roleId);
                return entity;
            }));
        }
        if (!CollectionUtil.isEmpty(deleteMenuIds)) {
            userRoleMapper.deleteListByUserIdAndRoleIdIds(userId, deleteMenuIds);
        }
        // 发送刷新消息. 注意，需要事务提交后，在进行发送刷新消息。不然 db 还未提交，结果缓存先刷新了
//        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
//
//            @Override
//            public void afterCommit() {
//                permissionProducer.sendUserRoleRefreshMessage();
//            }
//
//        });
    }

    @Override
    public void assignRoleDataScope(Long roleId, Integer dataScope, Set<Long> dataScopeDeptIds) {
        roleService.updateRoleDataScope(roleId, dataScope, dataScopeDeptIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processRoleDeleted(Long roleId) {
        // 标记删除 UserRole
        userRoleMapper.deleteListByRoleId(roleId);
        // 标记删除 RoleMenu
        roleMenuMapper.deleteListByRoleId(roleId);
        // 发送刷新消息. 注意，需要事务提交后，在进行发送刷新消息。不然 db 还未提交，结果缓存先刷新了
//        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
//
//            @Override
//            public void afterCommit() {
//                permissionProducer.sendRoleMenuRefreshMessage();
//                permissionProducer.sendUserRoleRefreshMessage();
//            }
//
//        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processMenuDeleted(Long menuId) {
        roleMenuMapper.deleteListByMenuId(menuId);
        // 发送刷新消息. 注意，需要事务提交后，在进行发送刷新消息。不然 db 还未提交，结果缓存先刷新了
//        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
//
//            @Override
//            public void afterCommit() {
//                permissionProducer.sendRoleMenuRefreshMessage();
//            }
//
//        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processUserDeleted(Long userId) {
        userRoleMapper.deleteListByUserId(userId);
//        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
//
//            @Override
//            public void afterCommit() {
//                permissionProducer.sendUserRoleRefreshMessage();
//            }
//
//        });
    }

    @Override
    public boolean hasAnyPermissions(Long userId, String... permissions) {
        // 如果为空，说明已经有权限
        if (ArrayUtil.isEmpty(permissions)) {
            return true;
        }

        // 获得当前登录的角色。如果为空，说明没有权限
        Set<Long> roleIds = getUserRoleIdsFromCache(userId, singleton(CommonStatusEnum.ENABLE.getStatus()));
        if (CollUtil.isEmpty(roleIds)) {
            return false;
        }
        // 判断是否是超管。如果是，当然符合条件
        if (roleService.hasAnySuperAdmin(roleIds)) {
            return true;
        }

        // 遍历权限，判断是否有一个满足
        return Arrays.stream(permissions).anyMatch(permission -> {
            List<SysMenuDO> menuList = menuService.getMenuListByPermissionFromCache(permission);
            // 采用严格模式，如果权限找不到对应的 Menu 的话，认为
            if (CollUtil.isEmpty(menuList)) {
                return false;
            }
            // 获得是否拥有该权限，任一一个
            return menuList.stream().anyMatch(menu -> CollUtil.containsAny(roleIds,
                    menuRoleCache.get(menu.getId())));
        });
    }

    @Override
    public boolean hasAnyRoles(Long userId, String... roles) {
        // 如果为空，说明已经有权限
        if (ArrayUtil.isEmpty(roles)) {
            return true;
        }

        // 获得当前登录的角色。如果为空，说明没有权限
        Set<Long> roleIds = getUserRoleIdsFromCache(userId, singleton(CommonStatusEnum.ENABLE.getStatus()));
        if (CollUtil.isEmpty(roleIds)) {
            return false;
        }
        // 判断是否是超管。如果是，当然符合条件
        if (roleService.hasAnySuperAdmin(roleIds)) {
            return true;
        }
        Set<String> userRoles = convertSet(roleService.getRoleListFromCache(roleIds),
                SysRoleDO::getCode);
        return CollUtil.containsAny(userRoles, Sets.newHashSet(roles));
    }

    @Override
    @DataPermission(enable = false) // 关闭数据权限，不然就会出现递归获取数据权限的问题
    @TenantIgnore // 忽略多租户的自动过滤。如果不忽略，会导致添加租户时，因为切换租户，导致获取不到 User。即使忽略，本身该方法不存在跨租户的操作，不会存在问题。
    public DeptDataPermissionRespDTO getDeptDataPermission(Long userId) {
        // 获得用户的角色
        Set<Long> roleIds = getUserRoleIdsFromCache(userId, singleton(CommonStatusEnum.ENABLE.getStatus()));
        // 如果角色为空，则只能查看自己
        DeptDataPermissionRespDTO result = new DeptDataPermissionRespDTO();
        if (CollUtil.isEmpty(roleIds)) {
            result.setSelf(true);
            return result;
        }
        List<SysRoleDO> roles = roleService.getRoleListFromCache(roleIds);

        // 获得用户的部门编号的缓存，通过 Guava 的 Suppliers 惰性求值，即有且仅有第一次发起 DB 的查询
        Supplier<Long> userDeptIdCache = Suppliers.memoize(() -> adminUserService.getUser(userId).getDeptId());
        // 遍历每个角色，计算
        for (SysRoleDO role : roles) {
            // 为空时，跳过
            if (role.getDataScope() == null) {
                continue;
            }
            // 情况一，ALL
            if (Objects.equals(role.getDataScope(), DataScopeEnum.ALL.getScope())) {
                result.setAll(true);
                continue;
            }
            // 情况二，DEPT_CUSTOM
            if (Objects.equals(role.getDataScope(), DataScopeEnum.DEPT_CUSTOM.getScope())) {
                CollUtil.addAll(result.getDeptIds(), role.getDataScopeDeptIds());
                // 自定义可见部门时，保证可以看到自己所在的部门。否则，一些场景下可能会有问题。
                // 例如说，登录时，基于 t_user 的 username 查询会可能被 dept_id 过滤掉
                CollUtil.addAll(result.getDeptIds(), userDeptIdCache.get());
                continue;
            }
            // 情况三，DEPT_ONLY
            if (Objects.equals(role.getDataScope(), DataScopeEnum.DEPT_ONLY.getScope())) {
                CollectionUtils.addIfNotNull(result.getDeptIds(), userDeptIdCache.get());
                continue;
            }
            // 情况四，DEPT_DEPT_AND_CHILD
            if (Objects.equals(role.getDataScope(), DataScopeEnum.DEPT_AND_CHILD.getScope())) {
                List<SysDeptDO> depts = deptService.getDeptListByParentIdFromCache(userDeptIdCache.get(), true);
                CollUtil.addAll(result.getDeptIds(), CollectionUtils.convertList(depts, SysDeptDO::getId));
                // 添加本身部门编号
                CollUtil.addAll(result.getDeptIds(), userDeptIdCache.get());
                continue;
            }
            // 情况五，SELF
            if (Objects.equals(role.getDataScope(), DataScopeEnum.SELF.getScope())) {
                result.setSelf(true);
                continue;
            }
            // 未知情况，error log 即可
            log.error("[getDeptDataPermission][LoginUser({}) role({}) 无法处理]", userId, JsonUtils.toJsonString(result));
        }
        return result;
    }

    @Override
    public Optional<SysUserDO> findOptionalByUsernameWithAuthorities(String username) {
        Optional<SysUserDO> optionalByUsername = adminUserService.findOptionalByUsername(username);
        if (!optionalByUsername.isPresent()) {
            return Optional.empty();
        } else {
            SysUserDO sysUserDO = optionalByUsername.get();
            Set<String> userAuthority = getPermissionListByUserId(sysUserDO.getId());
            optionalByUsername.ifPresent(sysUserDomain ->
                    sysUserDomain.setAuthorities(userAuthority));
        }
        return optionalByUsername;
    }

}
