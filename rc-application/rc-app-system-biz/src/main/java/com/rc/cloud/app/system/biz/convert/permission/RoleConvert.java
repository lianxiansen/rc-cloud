package com.rc.cloud.app.system.biz.convert.permission;

import com.rc.cloud.app.system.biz.model.permission.RoleDO;
import com.rc.cloud.app.system.biz.service.permission.bo.RoleCreateReqBO;
import com.rc.cloud.app.system.biz.vo.permission.role.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RoleConvert {

    RoleConvert INSTANCE = Mappers.getMapper(RoleConvert.class);

    RoleDO convert(RoleUpdateReqVO bean);

    RoleRespVO convert(RoleDO bean);

    RoleDO convert(RoleCreateReqVO bean);

    List<RoleSimpleRespVO> convertList02(List<RoleDO> list);

    List<RoleExcelVO> convertList03(List<RoleDO> list);

    RoleDO convert(RoleCreateReqBO bean);

}
