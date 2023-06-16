package com.rc.cloud.app.system.service.tenant;

import cn.hutool.core.collection.CollUtil;
import com.rc.cloud.app.system.api.tenant.entity.SysTenantDO;
import com.rc.cloud.app.system.api.tenant.entity.SysTenantPackageDO;
import com.rc.cloud.app.system.convert.tenant.TenantPackageConvert;
import com.rc.cloud.app.system.mapper.tenant.TenantPackageMapper;
import com.rc.cloud.app.system.vo.tenant.packages.TenantPackageCreateReqVO;
import com.rc.cloud.app.system.vo.tenant.packages.TenantPackagePageReqVO;
import com.rc.cloud.app.system.vo.tenant.packages.TenantPackageUpdateReqVO;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.core.pojo.PageResult;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static com.rc.cloud.app.system.enums.ErrorCodeConstants.*;
import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;


/**
 * 租户套餐 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TenantPackageServiceImpl implements TenantPackageService {

    @Resource
    private TenantPackageMapper tenantPackageMapper;

    @Resource
    @Lazy // 避免循环依赖的报错
    private TenantService tenantService;

    @Override
    public Long createTenantPackage(TenantPackageCreateReqVO createReqVO) {
        // 插入
        SysTenantPackageDO tenantPackage = TenantPackageConvert.INSTANCE.convert(createReqVO);
        tenantPackageMapper.insert(tenantPackage);
        // 返回
        return tenantPackage.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTenantPackage(TenantPackageUpdateReqVO updateReqVO) {
        // 校验存在
        SysTenantPackageDO tenantPackage = validateTenantPackageExists(updateReqVO.getId());
        // 更新
        SysTenantPackageDO updateObj = TenantPackageConvert.INSTANCE.convert(updateReqVO);
        tenantPackageMapper.updateById(updateObj);
        // 如果菜单发生变化，则修改每个租户的菜单
        if (!CollUtil.isEqualList(tenantPackage.getMenuIds(), updateReqVO.getMenuIds())) {
            List<SysTenantDO> tenants = tenantService.getTenantListByPackageId(tenantPackage.getId());
            tenants.forEach(tenant -> tenantService.updateTenantRoleMenu(tenant.getId(), updateReqVO.getMenuIds()));
        }
    }

    @Override
    public void deleteTenantPackage(Long id) {
        // 校验存在
        validateTenantPackageExists(id);
        // 校验正在使用
        validateTenantUsed(id);
        // 删除
        tenantPackageMapper.deleteById(id);
    }

    private SysTenantPackageDO validateTenantPackageExists(Long id) {
        SysTenantPackageDO tenantPackage = tenantPackageMapper.selectById(id);
        if (tenantPackage == null) {
            throw exception(TENANT_PACKAGE_NOT_EXISTS);
        }
        return tenantPackage;
    }

    private void validateTenantUsed(Long id) {
        if (tenantService.getTenantCountByPackageId(id) > 0) {
            throw exception(TENANT_PACKAGE_USED);
        }
    }

    @Override
    public SysTenantPackageDO getTenantPackage(Long id) {
        return tenantPackageMapper.selectById(id);
    }

    @Override
    public PageResult<SysTenantPackageDO> getTenantPackagePage(TenantPackagePageReqVO pageReqVO) {
        return tenantPackageMapper.selectPage(pageReqVO);
    }

    @Override
    public SysTenantPackageDO validTenantPackage(Long id) {
        SysTenantPackageDO tenantPackage = tenantPackageMapper.selectById(id);
        if (tenantPackage == null) {
            throw exception(TENANT_PACKAGE_NOT_EXISTS);
        }
        if (tenantPackage.getStatus().equals(CommonStatusEnum.DISABLE.getStatus())) {
            throw exception(TENANT_PACKAGE_DISABLE, tenantPackage.getName());
        }
        return tenantPackage;
    }

    @Override
    public List<SysTenantPackageDO> getTenantPackageListByStatus(Integer status) {
        return tenantPackageMapper.selectListByStatus(status);
    }

}
