package com.rc.cloud.app.system.service.dept;

import cn.hutool.core.collection.CollUtil;
import com.rc.cloud.app.system.model.dept.SysDeptPO;
import com.rc.cloud.app.system.vo.dept.dept.DeptCreateReqVO;
import com.rc.cloud.app.system.vo.dept.dept.DeptListReqVO;
import com.rc.cloud.app.system.vo.dept.dept.DeptUpdateReqVO;
import com.rc.cloud.common.core.util.collection.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 部门服务接口
 */
public interface DeptService {

    /**
     * 初始化部门的本地缓存
     */
    void initLocalCache();

    /**
     * 创建部门
     *
     * @param reqVO 部门信息
     * @return 部门编号
     */
    String createDept(DeptCreateReqVO reqVO);

    /**
     * 更新部门
     *
     * @param reqVO 部门信息
     */
    void updateDept(DeptUpdateReqVO reqVO);

    /**
     * 删除部门
     *
     * @param id 部门编号
     */
    void deleteDept(String id);

    /**
     * 筛选部门列表
     *
     * @param reqVO 筛选条件请求 VO
     * @return 部门列表
     */
    List<SysDeptPO> getDeptList(DeptListReqVO reqVO);

    /**
     * 获得所有子部门，从缓存中
     *
     * @param parentId  部门编号
     * @param recursive 是否递归获取所有
     * @return 子部门列表
     */
    List<SysDeptPO> getDeptListByParentIdFromCache(String parentId, boolean recursive);

    /**
     * 获得部门信息数组
     *
     * @param ids 部门编号数组
     * @return 部门信息数组
     */
    List<SysDeptPO> getDeptList(Collection<String> ids);

    /**
     * 获得指定编号的部门 Map
     *
     * @param ids 部门编号数组
     * @return 部门 Map
     */
    default Map<String, SysDeptPO> getDeptMap(Collection<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyMap();
        }
        List<SysDeptPO> list = getDeptList(ids);
        return CollectionUtils.convertMap(list, SysDeptPO::getId);
    }

    /**
     * 获得部门信息
     *
     * @param id 部门编号
     * @return 部门信息
     */
    SysDeptPO getDept(String id);

    /**
     * 校验部门们是否有效。如下情况，视为无效：
     * 1. 部门编号不存在
     * 2. 部门被禁用
     *
     * @param ids 角色编号数组
     */
    void validateDeptList(Collection<String> ids);

}
