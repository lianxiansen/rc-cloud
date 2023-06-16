package com.rc.cloud.app.system.convert.tenant;

import com.rc.cloud.app.system.api.tenant.entity.SysTenantPackageDO;
import com.rc.cloud.app.system.vo.tenant.packages.TenantPackageCreateReqVO;
import com.rc.cloud.app.system.vo.tenant.packages.TenantPackageRespVO;
import com.rc.cloud.app.system.vo.tenant.packages.TenantPackageSimpleRespVO;
import com.rc.cloud.app.system.vo.tenant.packages.TenantPackageUpdateReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 租户套餐 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface TenantPackageConvert {

    TenantPackageConvert INSTANCE = Mappers.getMapper(TenantPackageConvert.class);

    SysTenantPackageDO convert(TenantPackageCreateReqVO bean);

    SysTenantPackageDO convert(TenantPackageUpdateReqVO bean);

    TenantPackageRespVO convert(SysTenantPackageDO bean);

    List<TenantPackageRespVO> convertList(List<SysTenantPackageDO> list);

    PageResult<TenantPackageRespVO> convertPage(PageResult<SysTenantPackageDO> page);

    List<TenantPackageSimpleRespVO> convertList02(List<SysTenantPackageDO> list);

}
