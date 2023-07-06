package com.rc.cloud.app.system.mapper.user;

import com.rc.cloud.app.system.model.user.entity.SysUserDO;
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
public interface AdminUserMapper extends BaseMapperX<SysUserDO> {

    default SysUserDO selectByUsername(String username) {
        return selectOne(SysUserDO::getUsername, username);
    }

    default SysUserDO selectByEmail(String email) {
        return selectOne(SysUserDO::getEmail, email);
    }

    default SysUserDO selectByMobile(String mobile) {
        return selectOne(SysUserDO::getMobile, mobile);
    }

    default PageResult<SysUserDO> selectPage(UserPageReqVO reqVO, Collection<Long> deptIds) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysUserDO>()
                .likeIfPresent(SysUserDO::getUsername, reqVO.getUsername())
                .likeIfPresent(SysUserDO::getMobile, reqVO.getMobile())
                .eqIfPresent(SysUserDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(SysUserDO::getCreateTime, reqVO.getCreateTime())
                .inIfPresent(SysUserDO::getDeptId, deptIds)
                .orderByDesc(SysUserDO::getId));
    }

    default List<SysUserDO> selectList(UserExportReqVO reqVO, Collection<Long> deptIds) {
        return selectList(new LambdaQueryWrapperX<SysUserDO>()
                .likeIfPresent(SysUserDO::getUsername, reqVO.getUsername())
                .likeIfPresent(SysUserDO::getMobile, reqVO.getMobile())
                .eqIfPresent(SysUserDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(SysUserDO::getCreateTime, reqVO.getCreateTime())
                .inIfPresent(SysUserDO::getDeptId, deptIds));
    }

    default List<SysUserDO> selectListByNickname(String nickname) {
        return selectList(new LambdaQueryWrapperX<SysUserDO>().like(SysUserDO::getNickname, nickname));
    }

    default List<SysUserDO> selectListByStatus(Integer status) {
        return selectList(SysUserDO::getStatus, status);
    }

    default List<SysUserDO> selectListByDeptIds(Collection<Long> deptIds) {
        return selectList(SysUserDO::getDeptId, deptIds);
    }

    default Optional<SysUserDO> findOptionalByUsername(String username) {
        SysUserDO sysUserDO = selectOne(new LambdaQueryWrapperX<SysUserDO>().eq(SysUserDO::getUsername, username));
        if (sysUserDO == null) {
            return Optional.empty();
        }
        return Optional.of(sysUserDO);
    }
}
