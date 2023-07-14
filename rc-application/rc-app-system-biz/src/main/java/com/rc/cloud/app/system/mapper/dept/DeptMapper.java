package com.rc.cloud.app.system.mapper.dept;

import com.rc.cloud.app.system.model.dept.SysDeptPO;
import com.rc.cloud.app.system.vo.dept.dept.DeptListReqVO;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 部门mapper
 */
@Mapper
public interface DeptMapper extends BaseMapperX<SysDeptPO> {

    /**
     * 根据条件查询部门列表
     *
     * @param reqVO 查询条件
     * @return 部门列表
     */
    default List<SysDeptPO> selectList(DeptListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SysDeptPO>()
                .likeIfPresent(SysDeptPO::getName, reqVO.getName())
                .eqIfPresent(SysDeptPO::getStatus, reqVO.getStatus()));
    }

    /**
     * 根据父部门id和部门名称查询部门
     *
     * @param parentId 父部门id
     * @param name     部门名称
     * @return 部门
     */
    default SysDeptPO selectByParentIdAndName(String parentId, String name) {
        return selectOne(SysDeptPO::getParentId, parentId, SysDeptPO::getName, name);
    }

    /**
     * 根据父部门id查询部门数量
     *
     * @param parentId 父部门id
     * @return 部门数量
     */
    default Long selectCountByParentId(String parentId) {
        return selectCount(SysDeptPO::getParentId, parentId);
    }

}
