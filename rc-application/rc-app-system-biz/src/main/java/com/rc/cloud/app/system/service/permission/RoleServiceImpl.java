package com.rc.cloud.app.system.service.permission;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.common.annotations.VisibleForTesting;
import com.rc.cloud.app.system.convert.permission.RoleConvert;
import com.rc.cloud.app.system.enums.permission.DataScopeEnum;
import com.rc.cloud.app.system.enums.permission.RoleCodeEnum;
import com.rc.cloud.app.system.enums.permission.RoleTypeEnum;
import com.rc.cloud.app.system.mapper.permission.RoleMapper;
import com.rc.cloud.app.system.model.permission.SysRolePO;
import com.rc.cloud.app.system.vo.permission.role.RoleCreateReqVO;
import com.rc.cloud.app.system.vo.permission.role.RoleExportReqVO;
import com.rc.cloud.app.system.vo.permission.role.RolePageReqVO;
import com.rc.cloud.app.system.vo.permission.role.RoleUpdateReqVO;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.tenant.core.util.TenantUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.rc.cloud.app.system.enums.ErrorCodeConstants.*;
import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;
import static com.rc.cloud.common.core.util.collection.CollectionUtils.convertMap;


/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 角色服务实现类
 */
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    /**
     * 角色缓存
     * key：角色编号 {@link SysRolePO#getId()}
     * <p>
     * 这里声明 volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    @Getter
    private volatile Map<String, SysRolePO> roleCache;

    @Resource
    private PermissionService permissionService;

    @Resource
    private RoleMapper roleMapper;

//    @Resource
//    private RoleProducer roleProducer;

    /**
     * 初始化 {@link #roleCache} 缓存
     */
    @Override
    @PostConstruct
    public void initLocalCache() {
        // 注意：忽略自动多租户，因为要全局初始化缓存
        TenantUtils.executeIgnore(() -> {
            // 第一步：查询数据
            List<SysRolePO> roleList = roleMapper.selectList();
            log.info("[initLocalCache][缓存角色，数量为:{}]", roleList.size());

            // 第二步：构建缓存
            roleCache = convertMap(roleList, SysRolePO::getId);
        });
    }

    @Override
    @Transactional
    public String createRole(RoleCreateReqVO reqVO, Integer type) {
        // 校验角色
        validateRoleDuplicate(reqVO.getName(), reqVO.getCode(), null);
        // 插入到数据库
        SysRolePO role = RoleConvert.INSTANCE.convert(reqVO);
        role.setType(ObjectUtil.defaultIfNull(type, RoleTypeEnum.CUSTOM.getType()));
        role.setStatus(CommonStatusEnum.ENABLE.getStatus());
        role.setDataScope(DataScopeEnum.ALL.getScope()); // 默认可查看所有数据。原因是，可能一些项目不需要项目权限
        roleMapper.insert(role);
        // 插入角色菜单关联关系
        if (CollUtil.isNotEmpty(reqVO.getMenuIds())) {
            permissionService.assignRoleMenu(role.getId(), reqVO.getMenuIds());
        }
        // 发送刷新消息
//        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
//            @Override
//            public void afterCommit() {
//                roleProducer.sendRoleRefreshMessage();
//            }
//        });
        // 返回
        return role.getId();
    }

    @Override
    public void updateRole(RoleUpdateReqVO reqVO) {
        // 校验是否可以更新
        validateRoleForUpdate(reqVO.getId());
        // 校验角色的唯一字段是否重复
        validateRoleDuplicate(reqVO.getName(), reqVO.getCode(), reqVO.getId());

        // 更新到数据库
        SysRolePO updateObj = RoleConvert.INSTANCE.convert(reqVO);
        roleMapper.updateById(updateObj);
        // 插入角色菜单关联关系
        if (CollUtil.isNotEmpty(reqVO.getMenuIds())) {
            permissionService.assignRoleMenu(reqVO.getId(), reqVO.getMenuIds());
        }
        // 发送刷新消息
//        roleProducer.sendRoleRefreshMessage();
    }

    @Override
    public void updateRoleStatus(String id, Integer status) {
        // 校验是否可以更新
        validateRoleForUpdate(id);

        // 更新状态
        SysRolePO roleDO = new SysRolePO();
        roleDO.setId(id);
        roleDO.setStatus(status);
        SysRolePO updateObj = roleDO;
        roleMapper.updateById(updateObj);
        // 发送刷新消息
//        roleProducer.sendRoleRefreshMessage();
    }

    @Override
    public void updateRoleDataScope(String id, Integer dataScope, Set<String> dataScopeDeptIds) {
        // 校验是否可以更新
        validateRoleForUpdate(id);

        // 更新数据范围
        SysRolePO updateObject = new SysRolePO();
        updateObject.setId(id);
        updateObject.setDataScope(dataScope);
        updateObject.setDataScopeDeptIds(dataScopeDeptIds);
        roleMapper.updateById(updateObject);
        // 发送刷新消息
//        roleProducer.sendRoleRefreshMessage();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(String id) {
        // 校验是否可以更新
        validateRoleForUpdate(id);
        // 标记删除
        roleMapper.deleteById(id);
        // 删除相关数据
        permissionService.processRoleDeleted(id);
        // 发送刷新消息. 注意，需要事务提交后，在进行发送刷新消息。不然 db 还未提交，结果缓存先刷新了
//        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
//
//            @Override
//            public void afterCommit() {
//                roleProducer.sendRoleRefreshMessage();
//            }
//
//        });
    }

    @Override
    public SysRolePO getRoleFromCache(String id) {
        return roleCache.get(id);
    }

    @Override
    public List<SysRolePO> getRoleListByStatus(@Nullable Collection<Integer> statuses) {
        if (CollUtil.isEmpty(statuses)) {
            return roleMapper.selectList();
        }
        return roleMapper.selectListByStatus(statuses);
    }

    @Override
    public List<SysRolePO> getRoleListFromCache(Collection<String> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return roleCache.values().stream().filter(roleDO -> ids.contains(roleDO.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean hasAnySuperAdmin(Collection<SysRolePO> roleList) {
        if (CollectionUtil.isEmpty(roleList)) {
            return false;
        }
        return roleList.stream().anyMatch(role -> RoleCodeEnum.isSuperAdmin(role.getCode()));
    }

    @Override
    public SysRolePO getRole(String id) {
        return roleMapper.selectById(id);
    }

    @Override
    public PageResult<SysRolePO> getRolePage(RolePageReqVO reqVO) {
        return roleMapper.selectPage(reqVO);
    }

    @Override
    public List<SysRolePO> getRoleList(RoleExportReqVO reqVO) {
        return roleMapper.selectList(reqVO);
    }

    /**
     * 校验角色的唯一字段是否重复
     * <p>
     * 1. 是否存在相同名字的角色
     * 2. 是否存在相同编码的角色
     *
     * @param name 角色名字
     * @param code 角色额编码
     * @param id   角色编号
     */
    @VisibleForTesting
    void validateRoleDuplicate(String name, String code, String id) {
        // 0. 超级管理员，不允许创建
        if (RoleCodeEnum.isSuperAdmin(code)) {
            throw exception(ROLE_ADMIN_CODE_ERROR, code);
        }
        // 1. 该 name 名字被其它角色所使用
        SysRolePO role = roleMapper.selectByName(name);
        if (role != null && !role.getId().equals(id)) {
            throw exception(ROLE_NAME_DUPLICATE, name);
        }
        // 2. 是否存在相同编码的角色
        if (!StringUtils.hasText(code)) {
            return;
        }
        // 该 code 编码被其它角色所使用
        role = roleMapper.selectByCode(code);
        if (role != null && !role.getId().equals(id)) {
            throw exception(ROLE_CODE_DUPLICATE, code);
        }
    }

    /**
     * 校验角色是否可以被更新
     *
     * @param id 角色编号
     */
    @VisibleForTesting
    void validateRoleForUpdate(String id) {
        SysRolePO roleDO = roleMapper.selectById(id);
        if (roleDO == null) {
            throw exception(ROLE_NOT_EXISTS);
        }
        // 内置角色，不允许删除
        if (RoleTypeEnum.SYSTEM.getType().equals(roleDO.getType())) {
            throw exception(ROLE_CAN_NOT_UPDATE_SYSTEM_TYPE_ROLE);
        }
    }

    @Override
    public void validateRoleList(Collection<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // 获得角色信息
        List<SysRolePO> roles = roleMapper.selectBatchIds(ids);
        Map<String, SysRolePO> roleMap = convertMap(roles, SysRolePO::getId);
        // 校验
        ids.forEach(id -> {
            SysRolePO role = roleMap.get(id);
            if (role == null) {
                throw exception(ROLE_NOT_EXISTS);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(role.getStatus())) {
                throw exception(ROLE_IS_DISABLE, role.getName());
            }
        });
    }

    @Override
    public void deleteRoles(List<String> idList) {
        if (CollectionUtil.isEmpty(idList)) {
            return;
        }
        // 校验是否可以更新
        idList.forEach(this::validateRoleForUpdate);
        // 标记删除
        roleMapper.deleteBatchIds(idList);
        // 删除相关数据
        idList.forEach(id -> permissionService.processRoleDeleted(id));
    }
}
