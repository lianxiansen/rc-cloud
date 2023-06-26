package com.rc.cloud.app.system.service.permission;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.rc.cloud.app.system.api.permission.entity.SysMenuDO;
import com.rc.cloud.app.system.api.user.entity.SysUserDO;
import com.rc.cloud.app.system.convert.permission.MenuConvert;
import com.rc.cloud.app.system.mapper.permission.MenuMapper;
import com.rc.cloud.app.system.service.tenant.TenantService;
import com.rc.cloud.app.system.service.user.AdminUserService;
import com.rc.cloud.app.system.vo.permission.menu.MenuCreateReqVO;
import com.rc.cloud.app.system.vo.permission.menu.MenuListReqVO;
import com.rc.cloud.app.system.vo.permission.menu.MenuUpdateReqVO;
import com.rc.cloud.app.system.enums.permission.MenuTypeEnum;
import com.rc.cloud.common.core.constant.CacheConstants;
import com.rc.cloud.common.core.util.StringUtils;
import com.rc.cloud.common.core.util.collection.CollectionUtils;
import com.rc.cloud.common.security.service.RcUserDetailsService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.rc.cloud.app.system.api.permission.entity.SysMenuDO.ID_ROOT;
import static com.rc.cloud.app.system.enums.ErrorCodeConstants.*;
import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;


/**
 * 菜单 Service 实现
 *
 * @author 芋道源码
 */
@Service
@Slf4j
public class MenuServiceImpl implements MenuService {

    /**
     * 菜单缓存
     * key：菜单编号
     *
     * 这里声明 volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    @Getter
    @Setter
    private volatile Map<Long, SysMenuDO> menuCache;
    /**
     * 权限与菜单缓存
     * key：权限 {@link SysMenuDO#getPermission()}
     * value：MenuDO 数组，因为一个权限可能对应多个 MenuDO 对象
     *
     * 这里声明 volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    @Getter
    @Setter
    private volatile Multimap<String, SysMenuDO> permissionMenuCache;

    @Resource
    private MenuMapper menuMapper;
    @Resource
    private PermissionService permissionService;
    @Resource
    @Lazy // 延迟，避免循环依赖报错
    private TenantService tenantService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;



//    @Resource
//    private MenuProducer menuProducer;

    /**
     * 初始化 {@link #menuCache} 和 {@link #permissionMenuCache} 缓存
     */
    @Override
    @PostConstruct
    public synchronized void initLocalCache() {
        // 第一步：查询数据
        List<SysMenuDO> menuList = menuMapper.selectList();
        log.info("[initLocalCache][缓存菜单，数量为:{}]", menuList.size());

        // 第二步：构建缓存
        ImmutableMap.Builder<Long, SysMenuDO> menuCacheBuilder = ImmutableMap.builder();
        ImmutableMultimap.Builder<String, SysMenuDO> permMenuCacheBuilder = ImmutableMultimap.builder();
        menuList.forEach(menuDO -> {
            menuCacheBuilder.put(menuDO.getId(), menuDO);
            if (StrUtil.isNotEmpty(menuDO.getPermission())) { // 会存在 permission 为 null 的情况，导致 put 报 NPE 异常
                permMenuCacheBuilder.put(menuDO.getPermission(), menuDO);
            }
        });
        menuCache = menuCacheBuilder.build();
        permissionMenuCache = permMenuCacheBuilder.build();
    }

    @Override
    public Long createMenu(MenuCreateReqVO reqVO) {
        // 校验父菜单存在
        validateParentMenu(reqVO.getParentId(), null);
        // 校验菜单（自己）
        validateMenu(reqVO.getParentId(), reqVO.getName(), null);

        // 插入数据库
        SysMenuDO menu = MenuConvert.INSTANCE.convert(reqVO);
        initMenuProperty(menu);
        menuMapper.insert(menu);
        // 发送刷新消息
//        menuProducer.sendMenuRefreshMessage();
        // 返回
        return menu.getId();
    }

    @Override
    public void updateMenu(MenuUpdateReqVO reqVO) {
        // 校验更新的菜单是否存在
        if (menuMapper.selectById(reqVO.getId()) == null) {
            throw exception(MENU_NOT_EXISTS);
        }
        // 校验父菜单存在
        validateParentMenu(reqVO.getParentId(), reqVO.getId());
        // 校验菜单（自己）
        validateMenu(reqVO.getParentId(), reqVO.getName(), reqVO.getId());

        // 更新到数据库
        SysMenuDO updateObject = MenuConvert.INSTANCE.convert(reqVO);
        initMenuProperty(updateObject);
        menuMapper.updateById(updateObject);
        // 发送刷新消息
//        menuProducer.sendMenuRefreshMessage();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenu(Long menuId) {
        // 校验是否还有子菜单
        if (menuMapper.selectCountByParentId(menuId) > 0) {
            throw exception(MENU_EXISTS_CHILDREN);
        }
        // 校验删除的菜单是否存在
        if (menuMapper.selectById(menuId) == null) {
            throw exception(MENU_NOT_EXISTS);
        }
        // 标记删除
        menuMapper.deleteById(menuId);
        // 删除授予给角色的权限
        permissionService.processMenuDeleted(menuId);
        // 发送刷新消息. 注意，需要事务提交后，在进行发送刷新消息。不然 db 还未提交，结果缓存先刷新了
//        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
//
//            @Override
//            public void afterCommit() {
//                menuProducer.sendMenuRefreshMessage();
//            }

//        });
    }

    @Override
    public List<SysMenuDO> getMenuList() {
        return menuMapper.selectList();
    }

    @Override
    public List<SysMenuDO> getMenuListByTenant(MenuListReqVO reqVO) {
        List<SysMenuDO> menus = getMenuList(reqVO);
        // 开启多租户的情况下，需要过滤掉未开通的菜单
        tenantService.handleTenantMenu(menuIds -> menus.removeIf(menu -> !CollUtil.contains(menuIds, menu.getId())));
        return menus;
    }

    @Override
    public List<SysMenuDO> getMenuList(MenuListReqVO reqVO) {
        return menuMapper.selectList(reqVO);
    }

    @Override
    public List<SysMenuDO> getMenuListFromCache(Collection<Integer> menuTypes, Collection<Integer> menusStatuses) {
        // 任一一个参数为空，则返回空
        if (CollectionUtils.isAnyEmpty(menuTypes, menusStatuses)) {
            return Collections.emptyList();
        }
        // 创建新数组，避免缓存被修改
        return menuCache.values().stream().filter(menu -> menuTypes.contains(menu.getType())
                && menusStatuses.contains(menu.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<SysMenuDO> getMenuListFromCache(Collection<Long> menuIds, Collection<Integer> menuTypes,
                                                Collection<Integer> menusStatuses) {
        // 任一一个参数为空，则返回空
        if (CollectionUtils.isAnyEmpty(menuIds, menuTypes, menusStatuses)) {
            return Collections.emptyList();
        }
        return menuCache.values().stream().filter(menu -> menuIds.contains(menu.getId())
                && menuTypes.contains(menu.getType())
                && menusStatuses.contains(menu.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<SysMenuDO> getMenuListByPermissionFromCache(String permission) {
        return new ArrayList<>(permissionMenuCache.get(permission));
    }

    @Override
    public SysMenuDO getMenu(Long id) {
        return menuMapper.selectById(id);
    }

    @Override
    public List<SysMenuDO> getRootNavMenuList() {
        return menuMapper.selectPatentMenuList();
    }

    @Override
    public Set<String> getUserAuthorityByUserId(Long userId) {
        return permissionService.getPermissionListByUserId(userId);
    }

    /**
     * 校验父菜单是否合法
     *
     * 1. 不能设置自己为父菜单
     * 2. 父菜单不存在
     * 3. 父菜单必须是 {@link MenuTypeEnum#MENU} 菜单类型
     *
     * @param parentId 父菜单编号
     * @param childId 当前菜单编号
     */
    @VisibleForTesting
    void validateParentMenu(Long parentId, Long childId) {
        if (parentId == null || ID_ROOT.equals(parentId)) {
            return;
        }
        // 不能设置自己为父菜单
        if (parentId.equals(childId)) {
            throw exception(MENU_PARENT_ERROR);
        }
        SysMenuDO menu = menuMapper.selectById(parentId);
        // 父菜单不存在
        if (menu == null) {
            throw exception(MENU_PARENT_NOT_EXISTS);
        }
        // 父菜单必须是目录或者菜单类型
        if (!MenuTypeEnum.DIR.getType().equals(menu.getType())
            && !MenuTypeEnum.MENU.getType().equals(menu.getType())) {
            throw exception(MENU_PARENT_NOT_DIR_OR_MENU);
        }
    }

    /**
     * 校验菜单是否合法
     *
     * 1. 校验相同父菜单编号下，是否存在相同的菜单名
     *
     * @param name 菜单名字
     * @param parentId 父菜单编号
     * @param id 菜单编号
     */
    @VisibleForTesting
    void validateMenu(Long parentId, String name, Long id) {
        SysMenuDO menu = menuMapper.selectByParentIdAndName(parentId, name);
        if (menu == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的菜单
        if (id == null) {
            throw exception(MENU_NAME_DUPLICATE);
        }
        if (!menu.getId().equals(id)) {
            throw exception(MENU_NAME_DUPLICATE);
        }
    }

    /**
     * 初始化菜单的通用属性。
     *
     * 例如说，只有目录或者菜单类型的菜单，才设置 icon
     *
     * @param menu 菜单
     */
    private void initMenuProperty(SysMenuDO menu) {
        // 菜单为按钮类型时，无需 component、icon、path 属性，进行置空
        if (MenuTypeEnum.BUTTON.getType().equals(menu.getType())) {
            menu.setComponent("");
            menu.setComponentName("");
            menu.setIcon("");
            menu.setPath("");
        }
    }

}
