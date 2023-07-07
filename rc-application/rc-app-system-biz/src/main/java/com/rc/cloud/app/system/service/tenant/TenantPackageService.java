package com.rc.cloud.app.system.service.tenant;


import com.rc.cloud.app.system.model.tenant.SysTenantPackageDO;
import com.rc.cloud.app.system.vo.tenant.packages.TenantPackageCreateReqVO;
import com.rc.cloud.app.system.vo.tenant.packages.TenantPackagePageReqVO;
import com.rc.cloud.app.system.vo.tenant.packages.TenantPackageUpdateReqVO;
import com.rc.cloud.common.core.pojo.PageResult;

import javax.validation.Valid;
import java.util.List;

/**
 * 租户套餐 Service 接口
 *
 * @author 芋道源码
 */
public interface TenantPackageService {

    /**
     * 创建租户套餐
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createTenantPackage(@Valid TenantPackageCreateReqVO createReqVO);

    /**
     * 更新租户套餐
     *
     * @param updateReqVO 更新信息
     */
    void updateTenantPackage(@Valid TenantPackageUpdateReqVO updateReqVO);

    /**
     * 删除租户套餐
     *
     * @param id 编号
     */
    void deleteTenantPackage(String id);

    /**
     * 获得租户套餐
     *
     * @param id 编号
     * @return 租户套餐
     */
    SysTenantPackageDO getTenantPackage(String id);

    /**
     * 获得租户套餐分页
     *
     * @param pageReqVO 分页查询
     * @return 租户套餐分页
     */
    PageResult<SysTenantPackageDO> getTenantPackagePage(TenantPackagePageReqVO pageReqVO);

    /**
     * 校验租户套餐
     *
     * @param id 编号
     * @return 租户套餐
     */
    SysTenantPackageDO validTenantPackage(String id);

    /**
     * 获得指定状态的租户套餐列表
     *
     * @param status 状态
     * @return 租户套餐
     */
    List<SysTenantPackageDO> getTenantPackageListByStatus(Integer status);

}
