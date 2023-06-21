package com.rc.cloud.app.system.mapper.dept;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rc.cloud.app.system.api.dept.entity.SysUserPostDO;
import com.rc.cloud.app.system.api.permission.entity.SysUserRoleDO;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface UserPostMapper extends BaseMapperX<SysUserPostDO> {

    default List<SysUserPostDO> selectListByUserId(Long userId) {
        return selectList(SysUserPostDO::getUserId, userId);
    }

    default Set<Long> selectPostIdsByUserId(Long userId) {
        QueryWrapper<SysUserPostDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().select(SysUserPostDO::getPostId);
        wrapper.lambda().eq(SysUserPostDO::getUserId, userId);
        return selectList(wrapper).stream().map(SysUserPostDO::getPostId).collect(Collectors.toSet());
    }

    default void deleteByUserIdAndPostId(Long userId, Collection<Long> postIds) {
        delete(new LambdaQueryWrapperX<SysUserPostDO>()
                .eq(SysUserPostDO::getUserId, userId)
                .in(SysUserPostDO::getPostId, postIds));
    }

    default List<SysUserPostDO> selectListByPostIds(Collection<Long> postIds) {
        return selectList(SysUserPostDO::getPostId, postIds);
    }

    default void deleteByUserId(Long userId) {
        delete(Wrappers.lambdaUpdate(SysUserPostDO.class).eq(SysUserPostDO::getUserId, userId));
    }
}
