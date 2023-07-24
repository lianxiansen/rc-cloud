package com.rc.cloud.app.system.service.dept;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.rc.cloud.app.system.convert.dept.DeptConvert;
import com.rc.cloud.app.system.enums.dept.DeptIdEnum;
import com.rc.cloud.app.system.mapper.dept.DeptMapper;
import com.rc.cloud.app.system.mapper.user.AdminUserMapper;
import com.rc.cloud.app.system.model.dept.SysDeptPO;
import com.rc.cloud.app.system.vo.dept.dept.DeptCreateReqVO;
import com.rc.cloud.app.system.vo.dept.dept.DeptListReqVO;
import com.rc.cloud.app.system.vo.dept.dept.DeptUpdateReqVO;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.tenant.core.context.TenantContextHolder;
import com.rc.cloud.common.tenant.core.util.TenantUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

import static com.rc.cloud.app.system.enums.ErrorCodeConstants.*;
import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 部门服务实现类
 */
@Service
@Validated
@Slf4j
public class DeptServiceImpl implements DeptService {

    /**
     * 部门缓存
     * <p>
     * key：部门编号 {@link SysDeptPO#getId()}
     * <p>
     * 这里声明 volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    @Getter
    private volatile Map<String, SysDeptPO> deptCache;
    /**
     * 父部门缓存
     * key：部门编号 {@link SysDeptPO#getParentId()}
     * value: 直接子部门列表
     * <p>
     * 这里声明 volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    @Getter
    private volatile Multimap<String, SysDeptPO> parentDeptCache;

    @Resource
    private DeptMapper deptMapper;

    @Resource
    private AdminUserMapper adminUserMapper;

//    @Resource
//    private DeptProducer deptProducer;

    /**
     * 初始化 {@link #parentDeptCache} 和 {@link #deptCache} 缓存
     */
    @Override
    @PostConstruct
    public synchronized void initLocalCache() {
        // 注意：忽略自动多租户，因为要全局初始化缓存
        TenantUtils.executeIgnore(() -> {
            // 第一步：查询数据
            List<SysDeptPO> depts = deptMapper.selectList();
            log.info("[initLocalCache][缓存部门，数量为:{}]", depts.size());

            // 第二步：构建缓存
            ImmutableMap.Builder<String, SysDeptPO> builder = ImmutableMap.builder();
            ImmutableMultimap.Builder<String, SysDeptPO> parentBuilder = ImmutableMultimap.builder();
            depts.forEach(deptDO -> {
                builder.put(deptDO.getId(), deptDO);
                parentBuilder.put(deptDO.getParentId(), deptDO);
            });
            deptCache = builder.build();
            parentDeptCache = parentBuilder.build();
        });
    }

    @Override
    public String createDept(DeptCreateReqVO reqVO) {
        // 校验正确性
        if (reqVO.getParentId() == null) {
            reqVO.setParentId(DeptIdEnum.ROOT.getId());
        }
        validateForCreateOrUpdate(null, reqVO.getParentId(), reqVO.getName());
        // 插入部门
        SysDeptPO dept = DeptConvert.INSTANCE.convert(reqVO);
        deptMapper.insert(dept);
        // 发送刷新消息
//        deptProducer.sendDeptRefreshMessage();
        return dept.getId();
    }

    @Override
    public void updateDept(DeptUpdateReqVO reqVO) {
        // 校验正确性
        if (reqVO.getParentId() == null) {
            reqVO.setParentId(DeptIdEnum.ROOT.getId());
        }
        validateForCreateOrUpdate(reqVO.getId(), reqVO.getParentId(), reqVO.getName());
        // 更新部门
        SysDeptPO updateObj = DeptConvert.INSTANCE.convert(reqVO);
        deptMapper.updateById(updateObj);
        // 发送刷新消息
//        deptProducer.sendDeptRefreshMessage();
    }

    @Override
    public void deleteDept(String id) {
        // 校验是否存在
        validateDeptExists(id);
        // 校验是否有子部门
        validateDeptHasChildren(id);
        // 校验该部门下是否有员工
        validateDeptHasAdminUser(id);
        // 删除部门
        deptMapper.deleteById(id);
        // 发送刷新消息
//        deptProducer.sendDeptRefreshMessage();
    }

    private void validateDeptHasAdminUser(String id) {
        Long count = adminUserMapper.selectCountByDeptId(id);
        if (count > 0) {
            throw exception(DEPT_EXISTS_USER);
        }
    }

    private void validateDeptHasChildren(String id) {
        if (deptMapper.selectCountByParentId(id) > 0) {
            throw exception(DEPT_EXITS_CHILDREN);
        }
    }

    @Override
    public List<SysDeptPO> getDeptList(DeptListReqVO reqVO) {
        return deptMapper.selectList(reqVO);
    }

    @Override
    public List<SysDeptPO> getDeptListByParentIdFromCache(String parentId, boolean recursive) {
        if (parentId == null) {
            return Collections.emptyList();
        }
        List<SysDeptPO> result = new ArrayList<>();
        // 递归，简单粗暴
        getDeptsByParentIdFromCache(result, parentId,
                recursive ? Integer.MAX_VALUE : 1, // 如果递归获取，则无限；否则，只递归 1 次
                parentDeptCache);
        return result;
    }

    /**
     * 递归获取所有的子部门，添加到 result 结果
     *
     * @param result         结果
     * @param parentId       父编号
     * @param recursiveCount 递归次数
     * @param parentDeptMap  父部门 Map，使用缓存，避免变化
     */
    private void getDeptsByParentIdFromCache(List<SysDeptPO> result, String parentId, int recursiveCount,
                                             Multimap<String, SysDeptPO> parentDeptMap) {
        // 递归次数为 0，结束！
        if (recursiveCount == 0) {
            return;
        }

        // 获得子部门
        Collection<SysDeptPO> depts = parentDeptMap.get(parentId);
        if (CollUtil.isEmpty(depts)) {
            return;
        }
        // 针对多租户，过滤掉非当前租户的部门
        String tenantId = TenantContextHolder.getTenantId();
        if (tenantId != null) {
            depts = CollUtil.filterNew(depts, dept -> tenantId.equals(dept.getTenantId()));
        }
        result.addAll(depts);

        // 继续递归
        depts.forEach(dept -> getDeptsByParentIdFromCache(result, dept.getId(),
                recursiveCount - 1, parentDeptMap));
    }

    private void validateForCreateOrUpdate(String id, String parentId, String name) {
        // 校验自己存在
        validateDeptExists(id);
        // 校验父部门的有效性
        validateParentDeptEnable(id, parentId);
        // 校验部门名的唯一性
        validateDeptNameUnique(id, parentId, name);
    }

    private void validateParentDeptEnable(String id, String parentId) {
        if (parentId == null || DeptIdEnum.ROOT.getId().equals(parentId)) {
            return;
        }
        // 不能设置自己为父部门
        if (parentId.equals(id)) {
            throw exception(DEPT_PARENT_ERROR);
        }
        // 父岗位不存在
        SysDeptPO dept = deptMapper.selectById(parentId);
        if (dept == null) {
            throw exception(DEPT_PARENT_NOT_EXITS);
        }
        // 父部门被禁用
        if (!CommonStatusEnum.ENABLE.getStatus().equals(dept.getStatus())) {
            throw exception(DEPT_NOT_ENABLE, dept.getName());
        }
        // 父部门不能是原来的子部门
        List<SysDeptPO> children = getDeptListByParentIdFromCache(id, true);
        if (children.stream().anyMatch(dept1 -> dept1.getId().equals(parentId))) {
            throw exception(DEPT_PARENT_IS_CHILD);
        }
    }

    private void validateDeptExists(String id) {
        if (id == null) {
            return;
        }
        SysDeptPO dept = deptMapper.selectById(id);
        if (dept == null) {
            throw exception(DEPT_NOT_FOUND);
        }
    }

    private void validateDeptNameUnique(String id, String parentId, String name) {
        SysDeptPO deptPO = deptMapper.selectByParentIdAndName(parentId, name);
        if (deptPO == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的岗位
        if (id == null) {
            throw exception(DEPT_NAME_DUPLICATE);
        }
        if (!deptPO.getId().equals(id)) {
            throw exception(DEPT_NAME_DUPLICATE);
        }
    }

    @Override
    public List<SysDeptPO> getDeptList(Collection<String> ids) {
        return deptMapper.selectBatchIds(ids);
    }

    @Override
    public SysDeptPO getDept(String id) {
        SysDeptPO deptDO = deptMapper.selectById(id);
        if (deptDO == null) {
            throw exception(DEPT_NOT_FOUND);
        }
        return deptDO;
    }

    @Override
    public void validateDeptList(Collection<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // 获得科室信息
        Map<String, SysDeptPO> deptMap = getDeptMap(ids);
        // 校验
        ids.forEach(id -> {
            SysDeptPO dept = deptMap.get(id);
            if (dept == null) {
                throw exception(DEPT_NOT_FOUND);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(dept.getStatus())) {
                throw exception(DEPT_NOT_ENABLE, dept.getName());
            }
        });
    }

}
