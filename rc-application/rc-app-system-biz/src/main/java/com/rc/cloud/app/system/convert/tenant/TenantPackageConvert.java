package com.rc.cloud.app.system.convert.tenant;

import com.rc.cloud.app.system.model.tenant.SysTenantPackagePO;
import com.rc.cloud.app.system.vo.tenant.packages.TenantPackageCreateReqVO;
import com.rc.cloud.app.system.vo.tenant.packages.TenantPackageRespVO;
import com.rc.cloud.app.system.vo.tenant.packages.TenantPackageSimpleRespVO;
import com.rc.cloud.app.system.vo.tenant.packages.TenantPackageUpdateReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 租户套餐转换类
 */
@Mapper
public interface TenantPackageConvert {

    TenantPackageConvert INSTANCE = Mappers.getMapper(TenantPackageConvert.class);

    /**
     * 创建请求参数转换
     *
     * @param bean SysTenantPackagePO
     * @return TenantPackageRespVO
     */
    SysTenantPackagePO convert(TenantPackageCreateReqVO bean);

    /**
     * 更新请求参数转换
     *
     * @param bean SysTenantPackagePO
     * @return TenantPackageRespVO
     */
    SysTenantPackagePO convert(TenantPackageUpdateReqVO bean);

    /**
     * PO查询结果转换VO
     *
     * @param bean SysTenantPackagePO
     * @return TenantPackageRespVO
     */
    TenantPackageRespVO convert(SysTenantPackagePO bean);

    /**
     * PO列表查询结果转换VO列表
     *
     * @param list SysTenantPackagePO
     * @return TenantPackageRespVO
     */
    List<TenantPackageRespVO> convertList(List<SysTenantPackagePO> list);

    /**
     * 分页查询结果转换
     *
     * @param page 分页查询结果
     * @return 分页查询结果
     */
    PageResult<TenantPackageRespVO> convertPage(PageResult<SysTenantPackagePO> page);

    /**
     * PO列表查询结果转换精简VO列表
     *
     * @param list SysTenantPackagePO
     * @return TenantPackageRespVO
     */
    List<TenantPackageSimpleRespVO> convertList02(List<SysTenantPackagePO> list);

}
