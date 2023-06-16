package com.rc.cloud.app.system.convert.user;

import com.rc.cloud.app.system.api.dept.entity.SysPostDO;
import com.rc.cloud.app.system.api.permission.entity.SysRoleDO;
import com.rc.cloud.app.system.api.user.dto.AdminUserRespDTO;
import com.rc.cloud.app.system.api.user.entity.SysUserDO;
import com.rc.cloud.app.system.api.dept.entity.SysDeptDO;
import com.rc.cloud.app.system.vo.user.profile.UserProfileRespVO;
import com.rc.cloud.app.system.vo.user.profile.UserProfileUpdatePasswordReqVO;
import com.rc.cloud.app.system.vo.user.profile.UserProfileUpdateReqVO;
import com.rc.cloud.app.system.vo.user.user.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    UserPageItemRespVO convert(SysUserDO bean);

    UserPageItemRespVO.Dept convert(SysDeptDO bean);

    SysUserDO convert(UserCreateReqVO bean);

    SysUserDO convert(UserUpdateReqVO bean);

    UserExcelVO convert02(SysUserDO bean);

    SysUserDO convert(UserImportExcelVO bean);

    UserProfileRespVO convert03(SysUserDO bean);

    List<UserProfileRespVO.Role> convertList(List<SysRoleDO> list);

    UserProfileRespVO.Dept convert02(SysDeptDO bean);

    SysUserDO convert(UserProfileUpdateReqVO bean);

    SysUserDO convert(UserProfileUpdatePasswordReqVO bean);

    List<UserProfileRespVO.Post> convertList02(List<SysPostDO> list);

//    List<UserProfileRespVO.SocialUser> convertList03(List<SocialUserDO> list);

    List<UserSimpleRespVO> convertList04(List<SysUserDO> list);

    AdminUserRespDTO convert4(SysUserDO bean);

    List<AdminUserRespDTO> convertList4(List<SysUserDO> users);

}
