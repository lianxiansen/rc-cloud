package com.rc.cloud.app.system.mapper.permission;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rc.cloud.app.system.model.permission.SysRolePO;
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
public interface RoleMapper extends BaseMapperX<SysRolePO> {

    default PageResult<SysRolePO> selectPage(RolePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysRolePO>()
                .likeIfPresent(SysRolePO::getName, reqVO.getName())
                .likeIfPresent(SysRolePO::getCode, reqVO.getCode())
                .eqIfPresent(SysRolePO::getStatus, reqVO.getStatus())
                .betweenIfPresent(BaseDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SysRolePO::getId));
    }

    default List<SysRolePO> selectList(RoleExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SysRolePO>()
                .likeIfPresent(SysRolePO::getName, reqVO.getName())
                .likeIfPresent(SysRolePO::getCode, reqVO.getCode())
                .eqIfPresent(SysRolePO::getStatus, reqVO.getStatus())
                .betweenIfPresent(BaseDO::getCreateTime, reqVO.getCreateTime()));
    }

    default SysRolePO selectByName(String name) {
        return selectOne(SysRolePO::getName, name);
    }

    default SysRolePO selectByCode(String code) {
        return selectOne(SysRolePO::getCode, code);
    }

    default List<SysRolePO> selectListByStatus(@Nullable Collection<Integer> statuses) {
        return selectList(SysRolePO::getStatus, statuses);
    }

    default List<SysRolePO> listRolesByRoleIds(Set<String> roleIds) {
        QueryWrapper<SysRolePO> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(SysRolePO::getId, roleIds);
        return selectList(wrapper);
    }

    default Set<String> selectCodesByIds(Set<String> roleIds) {
        return listRolesByRoleIds(roleIds).stream().map(SysRolePO::getCode).collect(Collectors.toSet());
    }
}
