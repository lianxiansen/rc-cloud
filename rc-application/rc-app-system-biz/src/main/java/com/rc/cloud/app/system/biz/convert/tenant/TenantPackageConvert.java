package com.rc.cloud.app.system.biz.convert.tenant;

import com.rc.cloud.app.system.biz.model.tenant.TenantPackageDO;
import com.rc.cloud.app.system.biz.vo.tenant.packages.TenantPackageCreateReqVO;
import com.rc.cloud.app.system.biz.vo.tenant.packages.TenantPackageRespVO;
import com.rc.cloud.app.system.biz.vo.tenant.packages.TenantPackageSimpleRespVO;
import com.rc.cloud.app.system.biz.vo.tenant.packages.TenantPackageUpdateReqVO;
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

    TenantPackageDO convert(TenantPackageCreateReqVO bean);

    TenantPackageDO convert(TenantPackageUpdateReqVO bean);

    TenantPackageRespVO convert(TenantPackageDO bean);

    List<TenantPackageRespVO> convertList(List<TenantPackageDO> list);

    PageResult<TenantPackageRespVO> convertPage(PageResult<TenantPackageDO> page);

    List<TenantPackageSimpleRespVO> convertList02(List<TenantPackageDO> list);

}
