package com.rc.cloud.app.system.mapper.dept;

import com.rc.cloud.app.system.model.dept.SysDeptPO;
import com.rc.cloud.app.system.vo.dept.dept.DeptListReqVO;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeptMapper extends BaseMapperX<SysDeptPO> {

    default List<SysDeptPO> selectList(DeptListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SysDeptPO>()
                .likeIfPresent(SysDeptPO::getName, reqVO.getName())
                .eqIfPresent(SysDeptPO::getStatus, reqVO.getStatus()));
    }

    default SysDeptPO selectByParentIdAndName(String parentId, String name) {
        return selectOne(SysDeptPO::getParentId, parentId, SysDeptPO::getName, name);
    }

    default Long selectCountByParentId(String parentId) {
        return selectCount(SysDeptPO::getParentId, parentId);
    }

}
