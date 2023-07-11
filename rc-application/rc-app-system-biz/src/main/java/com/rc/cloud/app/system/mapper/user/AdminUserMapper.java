package com.rc.cloud.app.system.mapper.user;

import com.rc.cloud.app.system.model.user.SysUserPO;
import com.rc.cloud.app.system.vo.user.user.UserExportReqVO;
import com.rc.cloud.app.system.vo.user.user.UserPageReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Mapper
public interface AdminUserMapper extends BaseMapperX<SysUserPO> {

    default SysUserPO selectByUsername(String username) {
        return selectOne(SysUserPO::getUsername, username);
    }

    default SysUserPO selectByEmail(String email) {
        return selectOne(SysUserPO::getEmail, email);
    }

    default SysUserPO selectByMobile(String mobile) {
        return selectOne(SysUserPO::getMobile, mobile);
    }

    default PageResult<SysUserPO> selectPage(UserPageReqVO reqVO, Collection<String> deptIds) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysUserPO>()
                .likeIfPresent(SysUserPO::getUsername, reqVO.getUsername())
                .likeIfPresent(SysUserPO::getMobile, reqVO.getMobile())
                .eqIfPresent(SysUserPO::getStatus, reqVO.getStatus())
                .eqIfPresent(SysUserPO::getSex, reqVO.getSex())
                .betweenIfPresent(SysUserPO::getCreateTime, reqVO.getCreateTime())
                .inIfPresent(SysUserPO::getDeptId, deptIds)
                .orderByDesc(SysUserPO::getId));
    }

    default List<SysUserPO> selectList(UserExportReqVO reqVO, Collection<String> deptIds) {
        return selectList(new LambdaQueryWrapperX<SysUserPO>()
                .likeIfPresent(SysUserPO::getUsername, reqVO.getUsername())
                .likeIfPresent(SysUserPO::getMobile, reqVO.getMobile())
                .eqIfPresent(SysUserPO::getStatus, reqVO.getStatus())
                .betweenIfPresent(SysUserPO::getCreateTime, reqVO.getCreateTime())
                .inIfPresent(SysUserPO::getDeptId, deptIds));
    }

    default List<SysUserPO> selectListByNickname(String nickname) {
        return selectList(new LambdaQueryWrapperX<SysUserPO>().like(SysUserPO::getNickname, nickname));
    }

    default List<SysUserPO> selectListByStatus(Integer status) {
        return selectList(SysUserPO::getStatus, status);
    }

    default List<SysUserPO> selectListByDeptIds(Collection<String> deptIds) {
        return selectList(SysUserPO::getDeptId, deptIds);
    }

    default Optional<SysUserPO> findOptionalByUsername(String username) {
        SysUserPO sysUserPO = selectOne(new LambdaQueryWrapperX<SysUserPO>().eq(SysUserPO::getUsername, username));
        if (sysUserPO == null) {
            return Optional.empty();
        }
        return Optional.of(sysUserPO);
    }
}
