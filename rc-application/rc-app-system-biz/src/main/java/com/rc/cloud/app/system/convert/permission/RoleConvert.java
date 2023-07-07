package com.rc.cloud.app.system.convert.permission;

import com.rc.cloud.app.system.api.permission.vo.SysRoleVO;
import com.rc.cloud.app.system.model.permission.SysRoleDO;
import com.rc.cloud.app.system.service.permission.bo.RoleCreateReqBO;
import com.rc.cloud.app.system.vo.permission.role.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RoleConvert {

    RoleConvert INSTANCE = Mappers.getMapper(RoleConvert.class);

    SysRoleDO convert(RoleUpdateReqVO bean);

    List<SysRoleVO> convertToVOList(List<SysRoleDO> list);

    RoleRespVO convert(SysRoleDO bean);

    SysRoleDO convert(RoleCreateReqVO bean);

    List<RoleSimpleRespVO> convertList02(List<SysRoleDO> list);

    List<RoleExcelVO> convertList03(List<SysRoleDO> list);

    SysRoleDO convert(RoleCreateReqBO bean);

}
