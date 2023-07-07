package com.rc.cloud.app.system.mapper.dept;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rc.cloud.app.system.model.dept.SysUserPostDO;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface UserPostMapper extends BaseMapperX<SysUserPostDO> {

    default List<SysUserPostDO> selectListByUserId(String userId) {
        return selectList(SysUserPostDO::getUserId, userId);
    }

    default Set<String> selectPostIdsByUserId(String userId) {
        QueryWrapper<SysUserPostDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().select(SysUserPostDO::getPostId);
        wrapper.lambda().eq(SysUserPostDO::getUserId, userId);
        return selectList(wrapper).stream().map(SysUserPostDO::getPostId).collect(Collectors.toSet());
    }

    default void deleteByUserIdAndPostId(String userId, Collection<String> postIds) {
        delete(new LambdaQueryWrapperX<SysUserPostDO>()
                .eq(SysUserPostDO::getUserId, userId)
                .in(SysUserPostDO::getPostId, postIds));
    }

    default List<SysUserPostDO> selectListByPostIds(Collection<String> postIds) {
        return selectList(SysUserPostDO::getPostId, postIds);
    }

    default void deleteByUserId(String userId) {
        delete(Wrappers.lambdaUpdate(SysUserPostDO.class).eq(SysUserPostDO::getUserId, userId));
    }
}
