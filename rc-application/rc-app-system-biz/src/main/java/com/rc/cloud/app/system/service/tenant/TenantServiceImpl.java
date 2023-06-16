package com.rc.cloud.app.system.service.tenant;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.rc.cloud.app.system.api.permission.entity.SysRoleDO;
import com.rc.cloud.app.system.api.tenant.entity.SysTenantDO;
import com.rc.cloud.app.system.convert.tenant.TenantConvert;
import com.rc.cloud.app.system.mapper.tenant.TenantMapper;
import com.rc.cloud.app.system.api.tenant.entity.SysTenantPackageDO;
import com.rc.cloud.app.system.api.permission.entity.SysMenuDO;
import com.rc.cloud.app.system.service.permission.MenuService;
import com.rc.cloud.app.system.service.permission.PermissionService;
import com.rc.cloud.app.system.service.permission.RoleService;
import com.rc.cloud.app.system.service.tenant.handler.TenantInfoHandler;
import com.rc.cloud.app.system.service.tenant.handler.TenantMenuHandler;
import com.rc.cloud.app.system.service.user.AdminUserService;
import com.rc.cloud.app.system.vo.permission.role.RoleCreateReqVO;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantCreateReqVO;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantExportReqVO;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantPageReqVO;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantUpdateReqVO;
import com.rc.cloud.app.system.enums.permission.RoleCodeEnum;
import com.rc.cloud.app.system.enums.permission.RoleTypeEnum;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.util.collection.CollectionUtils;
import com.rc.cloud.common.core.util.date.DateUtils;
import com.rc.cloud.common.tenant.config.TenantProperties;
import com.rc.cloud.common.tenant.core.context.TenantContextHolder;
import com.rc.cloud.common.tenant.core.util.TenantUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.rc.cloud.app.system.enums.ErrorCodeConstants.*;
import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;
import static java.util.Collections.singleton;

/**
 * 租户 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class TenantServiceImpl implements TenantService {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired(required = false) // 由于 yudao.tenant.enable 配置项，可以关闭多租户的功能，所以这里只能不强制注入
    private TenantProperties tenantProperties;

    @Resource
    private TenantMapper tenantMapper;

    @Resource
    private TenantPackageService tenantPackageService;
    @Resource
    @Lazy // 延迟，避免循环依赖报错
    private AdminUserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;
    @Resource
    private PermissionService permissionService;

    @Override
    public List<Long> getTenantIdList() {
        List<SysTenantDO> tenants = tenantMapper.selectList();
        return CollectionUtils.convertList(tenants, SysTenantDO::getId);
    }

    @Override
    public void validTenant(Long id) {
        SysTenantDO tenant = getTenant(id);
        if (tenant == null) {
            throw exception(TENANT_NOT_EXISTS);
        }
        if (tenant.getStatus().equals(CommonStatusEnum.DISABLE.getStatus())) {
            throw exception(TENANT_DISABLE, tenant.getName());
        }
        if (DateUtils.isExpired(tenant.getExpireTime())) {
            throw exception(TENANT_EXPIRE, tenant.getName());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createTenant(TenantCreateReqVO createReqVO) {
        // 校验租户名称是否重复
        validTenantNameDuplicate(createReqVO.getName(), null);
        // 校验套餐被禁用
        SysTenantPackageDO tenantPackage = tenantPackageService.validTenantPackage(createReqVO.getPackageId());

        // 创建租户
        SysTenantDO tenant = TenantConvert.INSTANCE.convert(createReqVO);
        tenantMapper.insert(tenant);

        TenantUtils.execute(tenant.getId(), () -> {
            // 创建角色
            Long roleId = createRole(tenantPackage);
            // 创建用户，并分配角色
            Long userId = createUser(roleId, createReqVO);
            // 修改租户的管理员
            SysTenantDO tenantDO = new SysTenantDO();
            tenantDO.setId(tenant.getId());
            tenantDO.setContactUserId(userId);
            tenantMapper.updateById(tenantDO);
        });
        return tenant.getId();
    }

    private Long createUser(Long roleId, TenantCreateReqVO createReqVO) {
        // 创建用户
        Long userId = userService.createUser(TenantConvert.INSTANCE.convert02(createReqVO));
        // 分配角色
        permissionService.assignUserRole(userId, singleton(roleId));
        return userId;
    }

    private Long createRole(SysTenantPackageDO tenantPackage) {
        // 创建角色
        RoleCreateReqVO reqVO = new RoleCreateReqVO();
        reqVO.setName(RoleCodeEnum.TENANT_ADMIN.getName());
        reqVO.setCode(RoleCodeEnum.TENANT_ADMIN.getCode());
        reqVO.setSort(0);
        reqVO.setRemark("系统自动生成");
        Long roleId = roleService.createRole(reqVO, RoleTypeEnum.SYSTEM.getType());
        // 分配权限
        permissionService.assignRoleMenu(roleId, tenantPackage.getMenuIds());
        return roleId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTenant(TenantUpdateReqVO updateReqVO) {
        // 校验存在
        SysTenantDO tenant = validateUpdateTenant(updateReqVO.getId());
        // 校验租户名称是否重复
        validTenantNameDuplicate(updateReqVO.getName(), updateReqVO.getId());
        // 校验套餐被禁用
        SysTenantPackageDO tenantPackage = tenantPackageService.validTenantPackage(updateReqVO.getPackageId());

        // 更新租户
        SysTenantDO updateObj = TenantConvert.INSTANCE.convert(updateReqVO);
        tenantMapper.updateById(updateObj);
        // 如果套餐发生变化，则修改其角色的权限
        if (ObjectUtil.notEqual(tenant.getPackageId(), updateReqVO.getPackageId())) {
            updateTenantRoleMenu(tenant.getId(), tenantPackage.getMenuIds());
        }
    }

    private void validTenantNameDuplicate(String name, Long id) {
        SysTenantDO tenant = tenantMapper.selectByName(name);
        if (tenant == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同名字的租户
        if (id == null) {
            throw exception(TENANT_NAME_DUPLICATE, name);
        }
        if (!tenant.getId().equals(id)) {
            throw exception(TENANT_NAME_DUPLICATE, name);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTenantRoleMenu(Long tenantId, Set<Long> menuIds) {
        TenantUtils.execute(tenantId, () -> {
            // 获得所有角色
            List<SysRoleDO> roles = roleService.getRoleListByStatus(null);
            roles.forEach(role -> Assert.isTrue(tenantId.equals(role.getTenantId()), "角色({}/{}) 租户不匹配",
                    role.getId(), role.getTenantId(), tenantId)); // 兜底校验
            // 重新分配每个角色的权限
            roles.forEach(role -> {
                // 如果是租户管理员，重新分配其权限为租户套餐的权限
                if (Objects.equals(role.getCode(), RoleCodeEnum.TENANT_ADMIN.getCode())) {
                    permissionService.assignRoleMenu(role.getId(), menuIds);
                    log.info("[updateTenantRoleMenu][租户管理员({}/{}) 的权限修改为({})]", role.getId(), role.getTenantId(), menuIds);
                    return;
                }
                // 如果是其他角色，则去掉超过套餐的权限
                Set<Long> roleMenuIds = permissionService.getRoleMenuIds(role.getId());
                roleMenuIds = CollUtil.intersectionDistinct(roleMenuIds, menuIds);
                permissionService.assignRoleMenu(role.getId(), roleMenuIds);
                log.info("[updateTenantRoleMenu][角色({}/{}) 的权限修改为({})]", role.getId(), role.getTenantId(), roleMenuIds);
            });
        });
    }

    @Override
    public void deleteTenant(Long id) {
        // 校验存在
        validateUpdateTenant(id);
        // 删除
        tenantMapper.deleteById(id);
    }

    private SysTenantDO validateUpdateTenant(Long id) {
        SysTenantDO tenant = tenantMapper.selectById(id);
        if (tenant == null) {
            throw exception(TENANT_NOT_EXISTS);
        }
        // 内置租户，不允许删除
        if (isSystemTenant(tenant)) {
            throw exception(TENANT_CAN_NOT_UPDATE_SYSTEM);
        }
        return tenant;
    }

    @Override
    public SysTenantDO getTenant(Long id) {
        return tenantMapper.selectById(id);
    }

    @Override
    public PageResult<SysTenantDO> getTenantPage(TenantPageReqVO pageReqVO) {
        return tenantMapper.selectPage(pageReqVO);
    }

    @Override
    public List<SysTenantDO> getTenantList(TenantExportReqVO exportReqVO) {
        return tenantMapper.selectList(exportReqVO);
    }

    @Override
    public SysTenantDO getTenantByName(String name) {
        return tenantMapper.selectByName(name);
    }

    @Override
    public Long getTenantCountByPackageId(Long packageId) {
        return tenantMapper.selectCountByPackageId(packageId);
    }

    @Override
    public List<SysTenantDO> getTenantListByPackageId(Long packageId) {
        return tenantMapper.selectListByPackageId(packageId);
    }

    @Override
    public void handleTenantInfo(TenantInfoHandler handler) {
        // 如果禁用，则不执行逻辑
        if (isTenantDisable()) {
            return;
        }
        // 获得租户
        SysTenantDO tenant = getTenant(TenantContextHolder.getRequiredTenantId());
        // 执行处理器
        handler.handle(tenant);
    }

    @Override
    public void handleTenantMenu(TenantMenuHandler handler) {
        // 如果禁用，则不执行逻辑
        if (isTenantDisable()) {
            return;
        }
        // 获得租户，然后获得菜单
        Long tenantId = TenantContextHolder.getRequiredTenantId();
        SysTenantDO tenant = getTenant(tenantId);
        Set<Long> menuIds;
        if (isSystemTenant(tenant)) { // 系统租户，菜单是全量的
            menuIds = CollectionUtils.convertSet(menuService.getMenuList(), SysMenuDO::getId);
        } else {
            menuIds = tenantPackageService.getTenantPackage(tenant.getPackageId()).getMenuIds();
        }
        // 执行处理器
        handler.handle(menuIds);
    }

    private static boolean isSystemTenant(SysTenantDO tenant) {
        return Objects.equals(tenant.getPackageId(), SysTenantDO.PACKAGE_ID_SYSTEM);
    }

    private boolean isTenantDisable() {
        return tenantProperties == null || Boolean.FALSE.equals(tenantProperties.getEnable());
    }

}
