package com.rc.cloud.app.system.mapper.dept;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rc.cloud.app.system.model.dept.SysUserPostPO;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface UserPostMapper extends BaseMapperX<SysUserPostPO> {

    default List<SysUserPostPO> selectListByUserId(String userId) {
        return selectList(SysUserPostPO::getUserId, userId);
    }

    default Set<String> selectPostIdsByUserId(String userId) {
        QueryWrapper<SysUserPostPO> wrapper = new QueryWrapper<>();
        wrapper.lambda().select(SysUserPostPO::getPostId);
        wrapper.lambda().eq(SysUserPostPO::getUserId, userId);
        return selectList(wrapper).stream().map(SysUserPostPO::getPostId).collect(Collectors.toSet());
    }

    default void deleteByUserIdAndPostId(String userId, Collection<String> postIds) {
        delete(new LambdaQueryWrapperX<SysUserPostPO>()
                .eq(SysUserPostPO::getUserId, userId)
                .in(SysUserPostPO::getPostId, postIds));
    }

    default List<SysUserPostPO> selectListByPostIds(Collection<String> postIds) {
        return selectList(SysUserPostPO::getPostId, postIds);
    }

    default void deleteByUserId(String userId) {
        delete(Wrappers.lambdaUpdate(SysUserPostPO.class).eq(SysUserPostPO::getUserId, userId));
    }
}
