package com.rc.cloud.app.system.mapper.dept;

import com.rc.cloud.app.system.api.dept.entity.SysDeptDO;
import com.rc.cloud.app.system.vo.dept.dept.DeptListReqVO;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeptMapper extends BaseMapperX<SysDeptDO> {

    default List<SysDeptDO> selectList(DeptListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SysDeptDO>()
                .likeIfPresent(SysDeptDO::getName, reqVO.getName())
                .eqIfPresent(SysDeptDO::getStatus, reqVO.getStatus()));
    }

    default SysDeptDO selectByParentIdAndName(Long parentId, String name) {
        return selectOne(SysDeptDO::getParentId, parentId, SysDeptDO::getName, name);
    }

    default Long selectCountByParentId(Long parentId) {
        return selectCount(SysDeptDO::getParentId, parentId);
    }

}
