package com.rc.cloud.app.system.mapper.permission;

import com.rc.cloud.app.system.api.permission.model.SysRoleDO;
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

}
