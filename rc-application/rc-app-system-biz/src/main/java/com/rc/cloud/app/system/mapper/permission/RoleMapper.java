package com.rc.cloud.app.system.mapper.permission;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rc.cloud.app.system.model.permission.SysRoleDO;
import com.rc.cloud.app.system.vo.permission.role.RoleExportReqVO;
import com.rc.cloud.app.system.vo.permission.role.RolePageReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.Nullable;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface RoleMapper extends BaseMapperX<SysRoleDO> {

    default PageResult<SysRoleDO> selectPage(RolePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysRoleDO>()
                .likeIfPresent(SysRoleDO::getName, reqVO.getName())
                .likeIfPresent(SysRoleDO::getCode, reqVO.getCode())
                .eqIfPresent(SysRoleDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(BaseDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SysRoleDO::getId));
    }

    default List<SysRoleDO> selectList(RoleExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SysRoleDO>()
                .likeIfPresent(SysRoleDO::getName, reqVO.getName())
                .likeIfPresent(SysRoleDO::getCode, reqVO.getCode())
                .eqIfPresent(SysRoleDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(BaseDO::getCreateTime, reqVO.getCreateTime()));
    }

    default SysRoleDO selectByName(String name) {
        return selectOne(SysRoleDO::getName, name);
    }

    default SysRoleDO selectByCode(String code) {
        return selectOne(SysRoleDO::getCode, code);
    }

    default List<SysRoleDO> selectListByStatus(@Nullable Collection<Integer> statuses) {
        return selectList(SysRoleDO::getStatus, statuses);
    }

    default List<SysRoleDO> listRolesByRoleIds(Set<String> roleIds) {
        QueryWrapper<SysRoleDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(SysRoleDO::getId, roleIds);
        return selectList(wrapper);
    }

    default Set<String> selectCodesByIds(Set<String> roleIds) {
        return listRolesByRoleIds(roleIds).stream().map(SysRoleDO::getCode).collect(Collectors.toSet());
    }
}
