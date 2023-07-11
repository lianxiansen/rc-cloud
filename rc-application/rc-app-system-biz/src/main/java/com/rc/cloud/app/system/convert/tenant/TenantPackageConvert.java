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
 * 租户套餐 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface TenantPackageConvert {

    TenantPackageConvert INSTANCE = Mappers.getMapper(TenantPackageConvert.class);

    SysTenantPackagePO convert(TenantPackageCreateReqVO bean);

    SysTenantPackagePO convert(TenantPackageUpdateReqVO bean);

    TenantPackageRespVO convert(SysTenantPackagePO bean);

    List<TenantPackageRespVO> convertList(List<SysTenantPackagePO> list);

    PageResult<TenantPackageRespVO> convertPage(PageResult<SysTenantPackagePO> page);

    List<TenantPackageSimpleRespVO> convertList02(List<SysTenantPackagePO> list);

}
