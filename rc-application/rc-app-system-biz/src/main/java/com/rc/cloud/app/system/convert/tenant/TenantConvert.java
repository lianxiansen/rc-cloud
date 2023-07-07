package com.rc.cloud.app.system.convert.tenant;

import com.rc.cloud.app.system.model.tenant.SysTenantDO;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantCreateReqVO;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantExcelVO;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantRespVO;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantUpdateReqVO;
import com.rc.cloud.app.system.vo.user.user.UserCreateReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 租户 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface TenantConvert {

    TenantConvert INSTANCE = Mappers.getMapper(TenantConvert.class);

    SysTenantDO convert(TenantCreateReqVO bean);

    SysTenantDO convert(TenantUpdateReqVO bean);

    TenantRespVO convert(SysTenantDO bean);

    List<TenantRespVO> convertList(List<SysTenantDO> list);

    PageResult<TenantRespVO> convertPage(PageResult<SysTenantDO> page);

    List<TenantExcelVO> convertList02(List<SysTenantDO> list);

    default UserCreateReqVO convert02(TenantCreateReqVO bean) {
        UserCreateReqVO reqVO = new UserCreateReqVO();
        reqVO.setUsername(bean.getUsername());
        reqVO.setPassword(bean.getPassword());
        reqVO.setNickname(bean.getContactName());
        reqVO.setMobile(bean.getContactMobile());
        return reqVO;
    }

}
