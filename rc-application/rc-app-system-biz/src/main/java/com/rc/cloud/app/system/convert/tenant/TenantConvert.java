package com.rc.cloud.app.system.convert.tenant;

import com.rc.cloud.app.system.model.tenant.SysTenantPO;
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

    SysTenantPO convert(TenantCreateReqVO bean);

    SysTenantPO convert(TenantUpdateReqVO bean);

    TenantRespVO convert(SysTenantPO bean);

    List<TenantRespVO> convertList(List<SysTenantPO> list);

    PageResult<TenantRespVO> convertPage(PageResult<SysTenantPO> page);

    List<TenantExcelVO> convertList02(List<SysTenantPO> list);

    default UserCreateReqVO convert02(TenantCreateReqVO bean) {
        UserCreateReqVO reqVO = new UserCreateReqVO();
        reqVO.setUsername(bean.getUsername());
        reqVO.setPassword(bean.getPassword());
        reqVO.setNickname(bean.getContactName());
        reqVO.setMobile(bean.getContactMobile());
        return reqVO;
    }

}
