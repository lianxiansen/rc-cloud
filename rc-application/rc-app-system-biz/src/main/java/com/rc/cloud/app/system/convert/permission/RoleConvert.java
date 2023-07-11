package com.rc.cloud.app.system.convert.permission;

import com.rc.cloud.app.system.api.permission.vo.SysRoleVO;
import com.rc.cloud.app.system.model.permission.SysRolePO;
import com.rc.cloud.app.system.service.permission.bo.RoleCreateReqBO;
import com.rc.cloud.app.system.vo.permission.role.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RoleConvert {

    RoleConvert INSTANCE = Mappers.getMapper(RoleConvert.class);

    SysRolePO convert(RoleUpdateReqVO bean);

    List<SysRoleVO> convertToVOList(List<SysRolePO> list);

    RoleRespVO convert(SysRolePO bean);

    SysRolePO convert(RoleCreateReqVO bean);

    List<RoleSimpleRespVO> convertList02(List<SysRolePO> list);

    List<RoleExcelVO> convertList03(List<SysRolePO> list);

    SysRolePO convert(RoleCreateReqBO bean);

}
