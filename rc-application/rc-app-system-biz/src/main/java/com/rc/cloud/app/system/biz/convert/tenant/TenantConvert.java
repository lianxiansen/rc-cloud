package com.rc.cloud.app.system.biz.convert.tenant;

import com.rc.cloud.app.system.biz.model.tenant.TenantDO;
import com.rc.cloud.app.system.biz.vo.tenant.tenant.TenantCreateReqVO;
import com.rc.cloud.app.system.biz.vo.tenant.tenant.TenantExcelVO;
import com.rc.cloud.app.system.biz.vo.tenant.tenant.TenantRespVO;
import com.rc.cloud.app.system.biz.vo.tenant.tenant.TenantUpdateReqVO;
import com.rc.cloud.app.system.biz.vo.user.user.UserCreateReqVO;
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

    TenantDO convert(TenantCreateReqVO bean);

    TenantDO convert(TenantUpdateReqVO bean);

    TenantRespVO convert(TenantDO bean);

    List<TenantRespVO> convertList(List<TenantDO> list);

    PageResult<TenantRespVO> convertPage(PageResult<TenantDO> page);

    List<TenantExcelVO> convertList02(List<TenantDO> list);

    default UserCreateReqVO convert02(TenantCreateReqVO bean) {
        UserCreateReqVO reqVO = new UserCreateReqVO();
        reqVO.setUsername(bean.getUsername());
        reqVO.setPassword(bean.getPassword());
        reqVO.setNickname(bean.getContactName());
        reqVO.setMobile(bean.getContactMobile());
        return reqVO;
    }

}
