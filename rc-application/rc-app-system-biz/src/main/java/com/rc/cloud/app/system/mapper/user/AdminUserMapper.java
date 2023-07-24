package com.rc.cloud.app.system.mapper.user;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.rc.cloud.app.system.model.user.SysUserPO;
import com.rc.cloud.app.system.vo.user.user.UserExportReqVO;
import com.rc.cloud.app.system.vo.user.user.UserPageReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import jodd.util.StringUtil;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 用户信息mapper
 */
@Mapper
public interface AdminUserMapper extends BaseMapperX<SysUserPO> {

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    default SysUserPO selectByUsername(String username) {
        return selectOne(SysUserPO::getUsername, username);
    }

    /**
     * 根据邮箱查询用户信息
     *
     * @param email 邮箱
     * @return 用户信息
     */
    default SysUserPO selectByEmail(String email) {
        return selectOne(SysUserPO::getEmail, email);
    }

    /**
     * 根据手机号查询用户信息
     *
     * @param mobile 手机号
     * @return 用户信息
     */
    default SysUserPO selectByMobile(String mobile) {
        return selectOne(SysUserPO::getMobile, mobile);
    }

    /**
     * 查询分页数据
     *
     * @param reqVO   查询条件
     * @param deptIds 部门id集合
     * @return 分页数据
     */
    default PageResult<SysUserPO> selectPage(UserPageReqVO reqVO, Collection<String> deptIds) {
        if (StringUtil.isEmpty(reqVO.getOrder())) {
            reqVO.setOrder("id");
            reqVO.setAsc(false);
        }
        QueryWrapper<SysUserPO> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .like(StringUtil.isNotEmpty(reqVO.getUsername()), SysUserPO::getUsername, reqVO.getUsername())
                .like(StringUtil.isNotEmpty(reqVO.getMobile()), SysUserPO::getMobile, reqVO.getMobile())
                .eq(reqVO.getStatus() != null, SysUserPO::getStatus, reqVO.getStatus())
                .eq(reqVO.getSex() != null, SysUserPO::getSex, reqVO.getSex())
                .in(CollectionUtils.isNotEmpty(deptIds), SysUserPO::getDeptId, deptIds);
        wrapper.orderBy(true, reqVO.getAsc(), StrUtil.toUnderlineCase(reqVO.getOrder()));
        return selectPage(reqVO, wrapper);
    }

    /**
     * 查询导出数据
     *
     * @param reqVO   查询条件
     * @param deptIds 部门id集合
     * @return 导出数据
     */
    default List<SysUserPO> selectList(UserExportReqVO reqVO, Collection<String> deptIds) {
        return selectList(new LambdaQueryWrapperX<SysUserPO>()
                .likeIfPresent(SysUserPO::getUsername, reqVO.getUsername())
                .likeIfPresent(SysUserPO::getMobile, reqVO.getMobile())
                .eqIfPresent(SysUserPO::getStatus, reqVO.getStatus())
                .betweenIfPresent(SysUserPO::getCreateTime, reqVO.getCreateTime())
                .inIfPresent(SysUserPO::getDeptId, deptIds));
    }

    /**
     * 根据用户昵称模糊查询用户信息
     *
     * @param nickname 用户昵称
     * @return 用户信息
     */
    default List<SysUserPO> selectListByNickname(String nickname) {
        return selectList(new LambdaQueryWrapperX<SysUserPO>().like(SysUserPO::getNickname, nickname));
    }

    /**
     * 根据用户状态查询用户信息
     *
     * @param status 用户状态
     * @return 用户信息
     */
    default List<SysUserPO> selectListByStatus(Integer status) {
        return selectList(SysUserPO::getStatus, status);
    }

    /**
     * 根据部门id集合查询用户信息列表
     *
     * @param deptIds 部门id集合
     * @return 用户信息列表
     */
    default List<SysUserPO> selectListByDeptIds(Collection<String> deptIds) {
        return selectList(SysUserPO::getDeptId, deptIds);
    }

    /**
     * 根据部门id查询用户数量
     *
     * @param deptId 部门id
     * @return 用户数量
     */
    default Long selectCountByDeptId(String deptId) {
        return selectCount(new LambdaQueryWrapperX<SysUserPO>().eq(SysUserPO::getDeptId, deptId));
    }
}
