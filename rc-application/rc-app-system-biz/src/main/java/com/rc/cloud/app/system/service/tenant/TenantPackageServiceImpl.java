package com.rc.cloud.app.system.service.tenant;

import cn.hutool.core.collection.CollUtil;
import com.rc.cloud.app.system.convert.tenant.TenantPackageConvert;
import com.rc.cloud.app.system.mapper.tenant.TenantPackageMapper;
import com.rc.cloud.app.system.model.tenant.SysTenantPO;
import com.rc.cloud.app.system.model.tenant.SysTenantPackagePO;
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
 * @author rc@hqf
 * @date 2023/07/14
 * @description 租户套餐 Service 实现类
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
    public String createTenantPackage(TenantPackageCreateReqVO createReqVO) {
        // 校验重复
        validateForCreateOrUpdate(null, createReqVO.getName());
        // 插入
        SysTenantPackagePO tenantPackage = TenantPackageConvert.INSTANCE.convert(createReqVO);
        tenantPackageMapper.insert(tenantPackage);
        // 返回
        return tenantPackage.getId();
    }

    private void validateForCreateOrUpdate(String id, String name) {
        // 校验自己存在
        validateTenantPackageExists(id);
        // 校验重复
        validateTenantPackageNameUnique(id, name);
    }

    private void validateTenantPackageNameUnique(String id, String name) {
        SysTenantPackagePO sysTenantPackagePO = tenantPackageMapper.selectByName(name);
        if (sysTenantPackagePO == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的租户套餐
        if (id == null) {
            throw exception(TENANT_PACKAGE_NAME_DUPLICATE, name);
        }
        if (!sysTenantPackagePO.getId().equals(id)) {
            throw exception(TENANT_PACKAGE_NAME_DUPLICATE, name);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTenantPackage(TenantPackageUpdateReqVO updateReqVO) {
        // 校验存在
        SysTenantPackagePO tenantPackage = tenantPackageMapper.selectById(updateReqVO.getId());
        // 校验重复
        validateForCreateOrUpdate(updateReqVO.getId(), updateReqVO.getName());
        // 更新
        SysTenantPackagePO updateObj = TenantPackageConvert.INSTANCE.convert(updateReqVO);
        tenantPackageMapper.updateById(updateObj);
        // 如果菜单发生变化，则修改每个租户的菜单
        if (!CollUtil.isEqualList(tenantPackage.getMenuIds(), updateReqVO.getMenuIds())) {
            List<SysTenantPO> tenants = tenantService.getTenantListByPackageId(tenantPackage.getId());
            tenants.forEach(tenant -> tenantService.updateTenantRoleMenu(tenant.getId(), updateReqVO.getMenuIds()));
        }
    }

    @Override
    public void deleteTenantPackage(String id) {
        // 校验存在
        validateTenantPackageExists(id);
        // 校验正在使用
        validateTenantUsed(id);
        // 删除
        tenantPackageMapper.deleteById(id);
    }

    private void validateTenantPackageExists(String id) {
        if (id == null) {
            return;
        }

        SysTenantPackagePO tenantPackage = tenantPackageMapper.selectById(id);
        if (tenantPackage == null) {
            throw exception(TENANT_PACKAGE_NOT_EXISTS);
        }
    }

    private void validateTenantUsed(String id) {
        if (tenantService.getTenantCountByPackageId(id) > 0) {
            throw exception(TENANT_PACKAGE_USED);
        }
    }

    @Override
    public SysTenantPackagePO getTenantPackage(String id) {
        return tenantPackageMapper.selectById(id);
    }

    @Override
    public PageResult<SysTenantPackagePO> getTenantPackagePage(TenantPackagePageReqVO pageReqVO) {
        return tenantPackageMapper.selectPage(pageReqVO);
    }

    @Override
    public SysTenantPackagePO validTenantPackage(String id) {
        SysTenantPackagePO tenantPackage = tenantPackageMapper.selectById(id);
        if (tenantPackage == null) {
            throw exception(TENANT_PACKAGE_NOT_EXISTS);
        }
        if (tenantPackage.getStatus().equals(CommonStatusEnum.DISABLE.getStatus())) {
            throw exception(TENANT_PACKAGE_DISABLE, tenantPackage.getName());
        }
        return tenantPackage;
    }

    @Override
    public List<SysTenantPackagePO> getTenantPackageListByStatus(Integer status) {
        return tenantPackageMapper.selectListByStatus(status);
    }

}
