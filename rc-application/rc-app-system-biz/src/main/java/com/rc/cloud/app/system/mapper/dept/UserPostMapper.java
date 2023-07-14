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

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 用户岗位关联表mapper
 */
@Mapper
public interface UserPostMapper extends BaseMapperX<SysUserPostPO> {

    /**
     * 根据用户id查询用户岗位关联表
     *
     * @param userId 用户id
     * @return 用户岗位关联表
     */
    default List<SysUserPostPO> selectListByUserId(String userId) {
        return selectList(SysUserPostPO::getUserId, userId);
    }

    /**
     * 根据用户id查询岗位id集合
     *
     * @param userId 用户id
     * @return 岗位id集合
     */
    default Set<String> selectPostIdsByUserId(String userId) {
        QueryWrapper<SysUserPostPO> wrapper = new QueryWrapper<>();
        wrapper.lambda().select(SysUserPostPO::getPostId);
        wrapper.lambda().eq(SysUserPostPO::getUserId, userId);
        return selectList(wrapper).stream().map(SysUserPostPO::getPostId).collect(Collectors.toSet());
    }

    /**
     * 根据用户id和岗位id集合删除用户岗位关联表
     *
     * @param userId  用户id
     * @param postIds 岗位id集合
     */
    default void deleteByUserIdAndPostId(String userId, Collection<String> postIds) {
        delete(new LambdaQueryWrapperX<SysUserPostPO>()
                .eq(SysUserPostPO::getUserId, userId)
                .in(SysUserPostPO::getPostId, postIds));
    }

    /**
     * 根据岗位id集合查询用户岗位关联列表
     *
     * @param postIds 岗位id集合
     * @return 用户岗位关联列表
     */
    default List<SysUserPostPO> selectListByPostIds(Collection<String> postIds) {
        return selectList(SysUserPostPO::getPostId, postIds);
    }

    /**
     * 根据用户id删除用户岗位关联表
     *
     * @param userId 用户id
     */
    default void deleteByUserId(String userId) {
        delete(Wrappers.lambdaUpdate(SysUserPostPO.class).eq(SysUserPostPO::getUserId, userId));
    }
}
