package com.rc.cloud.app.system.convert.user;

import com.rc.cloud.app.system.model.dept.SysDeptPO;
import com.rc.cloud.app.system.model.dept.SysPostPO;
import com.rc.cloud.app.system.model.permission.SysRolePO;
import com.rc.cloud.app.system.api.user.dto.AdminUserRespDTO;
import com.rc.cloud.app.system.model.user.SysUserPO;
import com.rc.cloud.app.system.vo.user.profile.UserProfileRespVO;
import com.rc.cloud.app.system.vo.user.profile.UserProfileUpdatePasswordReqVO;
import com.rc.cloud.app.system.vo.user.profile.UserProfileUpdateReqVO;
import com.rc.cloud.app.system.vo.user.user.*;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.page.RcPage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    PageResult<UserRespVO> toUserIPage(RcPage<SysUserPO> pager);

    UserPageItemRespVO convert(SysUserPO bean);

    UserPageItemRespVO.Dept convert(SysDeptPO bean);

    SysUserPO convert(UserCreateReqVO bean);

    SysUserPO convert(UserUpdateReqVO bean);

    UserExcelVO convert02(SysUserPO bean);

    SysUserPO convert(UserImportExcelVO bean);

    UserProfileRespVO convert03(SysUserPO bean);

    List<UserProfileRespVO.Role> convertList(List<SysRolePO> list);

    UserProfileRespVO.Dept convert02(SysDeptPO bean);

    SysUserPO convert(UserProfileUpdateReqVO bean);

    SysUserPO convert(UserProfileUpdatePasswordReqVO bean);

    List<UserProfileRespVO.Post> convertList02(List<SysPostPO> list);

//    List<UserProfileRespVO.SocialUser> convertList03(List<SocialUserDO> list);

    List<UserSimpleRespVO> convertList04(List<SysUserPO> list);

    AdminUserRespDTO convert4(SysUserPO bean);

    List<AdminUserRespDTO> convertList4(List<SysUserPO> users);

}
