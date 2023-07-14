package com.rc.cloud.app.system.convert.user;

import com.rc.cloud.app.system.model.dept.SysDeptPO;
import com.rc.cloud.app.system.model.dept.SysPostPO;
import com.rc.cloud.app.system.model.permission.SysRolePO;
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

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 用户转换类
 */
@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    /**
     * 分页查询结果转换
     *
     * @param pager 分页查询结果
     * @return 分页查询结果
     */
    PageResult<UserRespVO> toUserIPage(RcPage<SysUserPO> pager);

    /**
     * PO查询结果转换VO
     *
     * @param bean SysUserPO
     * @return UserRespVO
     */
    UserPageItemRespVO convert(SysUserPO bean);

    /**
     * PO列表查询结果转换VO列表
     *
     * @param bean SysUserPO
     * @return UserRespVO
     */
    UserPageItemRespVO.Dept convert(SysDeptPO bean);

    /**
     * 创建请求参数转换
     *
     * @param bean UserCreateReqVO
     * @return SysUserPO
     */
    SysUserPO convert(UserCreateReqVO bean);

    /**
     * 更新请求参数转换
     *
     * @param bean UserUpdateReqVO
     * @return SysUserPO
     */
    SysUserPO convert(UserUpdateReqVO bean);

    /**
     * PO列表查询结果转换VO列表
     *
     * @param bean SysUserPO
     * @return UserRespVO
     */
    UserProfileRespVO convert03(SysUserPO bean);

    /**
     * PO列表查询结果转换用户角色列表
     *
     * @param list List<SysRolePO>
     * @return List<UserProfileRespVO.Role>
     */
    List<UserProfileRespVO.Role> convertList(List<SysRolePO> list);

    /**
     * PO查询结果转换用户部门
     *
     * @param bean SysDeptPO
     * @return UserProfileRespVO.Dept
     */
    UserProfileRespVO.Dept convert02(SysDeptPO bean);

    /**
     * 更新请求参数转换
     *
     * @param bean UserProfileUpdateReqVO
     * @return SysUserPO
     */
    SysUserPO convert(UserProfileUpdateReqVO bean);

    /**
     * 更新密码请求参数转换
     *
     * @param bean UserProfileUpdatePasswordReqVO
     * @return SysUserPO
     */
    SysUserPO convert(UserProfileUpdatePasswordReqVO bean);

    /**
     * PO列表查询结果转换用户岗位列表
     *
     * @param list List<SysPostPO>
     * @return List<UserProfileRespVO.Post>
     */
    List<UserProfileRespVO.Post> convertList02(List<SysPostPO> list);

//    List<UserProfileRespVO.SocialUser> convertList03(List<SocialUserDO> list);

    /**
     * PO列表查询结果转换用户简单信息列表
     *
     * @param list List<SysUserPO>
     * @return List<UserSimpleRespVO>
     */
    List<UserSimpleRespVO> convertList04(List<SysUserPO> list);
}
