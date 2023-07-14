package com.rc.cloud.app.system.mapper.permission;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rc.cloud.app.system.model.permission.SysUserRolePO;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.baomidou.mybatisplus.extension.toolkit.Db.saveBatch;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 用户角色关联表mapper
 */
@Mapper
public interface UserRoleMapper extends BaseMapperX<SysUserRolePO> {

    /**
     * 根据用户id查询用户角色关联列表
     *
     * @param userId 用户id
     * @return 用户角色关联列表
     */
    default List<SysUserRolePO> selectListByUserId(String userId) {
        return selectList(SysUserRolePO::getUserId, userId);
    }

    /**
     * 根据用户id查询用户角色id列表
     *
     * @param userId 用户id
     * @return 用户角色id列表
     */
    default Set<String> selectRoleIdsByUserId(String userId) {
        QueryWrapper<SysUserRolePO> wrapper = new QueryWrapper<>();
        wrapper.lambda().select(SysUserRolePO::getRoleId);
        wrapper.lambda().eq(SysUserRolePO::getUserId, userId);
        return selectList(wrapper).stream().map(SysUserRolePO::getRoleId).collect(Collectors.toSet());
    }

    /**
     * 根据角色id查询用户id列表
     *
     * @param roleId 角色id
     * @return 用户id列表
     */
    default Set<String> selectUserIdsByRoleId(String roleId) {
        QueryWrapper<SysUserRolePO> wrapper = new QueryWrapper<>();
        wrapper.lambda().select(SysUserRolePO::getUserId);
        wrapper.lambda().eq(SysUserRolePO::getRoleId, roleId);
        return selectList(wrapper).stream().map(SysUserRolePO::getUserId).collect(Collectors.toSet());
    }

    /**
     * 根据用户id和角色id列表查询用户角色关联列表
     *
     * @param userId  用户id
     * @param roleIds 角色id列表
     */
    default void deleteListByUserIdAndRoleIdIds(String userId, Collection<String> roleIds) {
        delete(new LambdaQueryWrapper<SysUserRolePO>()
                .eq(SysUserRolePO::getUserId, userId)
                .in(SysUserRolePO::getRoleId, roleIds));
    }

    /**
     * 根据用户id删除用户角色关联列表
     *
     * @param userId 用户id
     */
    default void deleteListByUserId(String userId) {
        delete(new LambdaQueryWrapper<SysUserRolePO>().eq(SysUserRolePO::getUserId, userId));
    }

    /**
     * 根据角色id删除用户角色关联列表
     *
     * @param roleId 角色id
     */
    default void deleteListByRoleId(String roleId) {
        delete(new LambdaQueryWrapper<SysUserRolePO>().eq(SysUserRolePO::getRoleId, roleId));
    }

    /**
     * 根据角色id列表查询用户角色关联列表
     *
     * @param roleIds 角色id列表
     * @return 用户角色关联列表
     */
    default List<SysUserRolePO> selectListByRoleIds(Collection<String> roleIds) {
        return selectList(SysUserRolePO::getRoleId, roleIds);
    }

    /**
     * 根据角色id列表查询用户id列表
     *
     * @param roleIds 角色id列表
     * @param userId  用户id
     */
    default void deleteBatchByUserIdAndRoleIds(String userId, Collection<String> roleIds) {
        delete(new LambdaQueryWrapperX<SysUserRolePO>()
                .eq(SysUserRolePO::getUserId, userId)
                .in(SysUserRolePO::getRoleId, roleIds));
    }

    /**
     * 保存用户角色关联列表
     *
     * @param roleId  角色id
     * @param userIds 用户id列表
     */
    default void saveUserList(String roleId, List<String> userIds) {
        List<SysUserRolePO> userRoleList = userIds.stream().map(userId -> {
            SysUserRolePO userRole = new SysUserRolePO();
            userRole.setRoleId(roleId);
            userRole.setUserId(userId);
            return userRole;
        }).collect(Collectors.toList());
        saveBatch(userRoleList);
    }

    /**
     * 根据角色id和用户id列表删除用户角色关联列表
     *
     * @param roleId  角色id
     * @param userIds 用户id列表
     */
    default void deleteByUserIds(String roleId, List<String> userIds) {
        delete(new LambdaQueryWrapperX<SysUserRolePO>()
                .eq(SysUserRolePO::getRoleId, roleId)
                .in(SysUserRolePO::getUserId, userIds));
    }
}
