package com.rc.cloud.app.system.service.user;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.annotations.VisibleForTesting;
import com.rc.cloud.app.system.convert.dept.PostConvert;
import com.rc.cloud.app.system.convert.permission.RoleConvert;
import com.rc.cloud.app.system.model.dept.SysDeptDO;
import com.rc.cloud.app.system.model.dept.SysPostDO;
import com.rc.cloud.app.system.model.dept.SysUserPostDO;
import com.rc.cloud.app.system.model.permission.SysMenuDO;
import com.rc.cloud.app.system.model.permission.SysRoleDO;
import com.rc.cloud.app.system.model.permission.SysUserRoleDO;
import com.rc.cloud.app.system.api.user.dto.UserInfo;
import com.rc.cloud.app.system.api.user.vo.SysUserVO;
import com.rc.cloud.app.system.model.user.SysUserDO;
import com.rc.cloud.app.system.common.datapermission.core.util.DataPermissionUtils;
import com.rc.cloud.app.system.convert.user.UserConvert;
import com.rc.cloud.app.system.mapper.dept.PostMapper;
import com.rc.cloud.app.system.mapper.dept.UserPostMapper;
import com.rc.cloud.app.system.mapper.permission.MenuMapper;
import com.rc.cloud.app.system.mapper.permission.RoleMapper;
import com.rc.cloud.app.system.mapper.permission.UserRoleMapper;
import com.rc.cloud.app.system.mapper.user.AdminUserMapper;
import com.rc.cloud.app.system.service.dept.DeptService;
import com.rc.cloud.app.system.service.dept.PostService;
import com.rc.cloud.app.system.service.permission.PermissionService;
import com.rc.cloud.app.system.service.tenant.TenantService;
import com.rc.cloud.app.system.vo.permission.role.RoleUserPageVO;
import com.rc.cloud.app.system.vo.user.profile.UserProfileUpdatePasswordReqVO;
import com.rc.cloud.app.system.vo.user.profile.UserProfileUpdateReqVO;
import com.rc.cloud.app.system.vo.user.user.*;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.util.collection.CollectionUtils;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import com.rc.cloud.common.mybatis.page.RcPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.rc.cloud.app.system.enums.ErrorCodeConstants.*;
import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;
import static com.rc.cloud.common.core.util.collection.CollectionUtils.convertList;
import static com.rc.cloud.common.core.util.collection.CollectionUtils.convertSet;

/**
 * 后台用户 Service 实现类
 *
 * @author 芋道源码
 */
@Service("adminUserService")
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {

    @Value("${sys.user.init-password:yudaoyuanma}")
    private String userInitPassword;

    @Resource
    private AdminUserMapper userMapper;

    @Resource
    private DeptService deptService;
    @Resource
    private PostService postService;
    @Resource
    private PostMapper postMapper;
    @Resource
    private PermissionService permissionService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    @Lazy // 延迟，避免循环依赖报错
    private TenantService tenantService;

    @Resource
    private UserPostMapper userPostMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private MenuMapper menuMapper;



//    @Resource
//    private FileApi fileApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createUser(UserCreateReqVO reqVO) {
        // 校验账户配合
        tenantService.handleTenantInfo(tenant -> {
            long count = userMapper.selectCount();
            if (count >= tenant.getAccountCount()) {
                throw exception(USER_COUNT_MAX, tenant.getAccountCount());
            }
        });
        // 校验正确性
        validateUserForCreateOrUpdate(null, reqVO.getUsername(), reqVO.getMobile(), reqVO.getEmail(),
                reqVO.getDeptId(), reqVO.getPostIds());
        // 插入用户
        SysUserDO user = UserConvert.INSTANCE.convert(reqVO);
        if (reqVO.getStatus() == null || reqVO.getStatus().equals(CommonStatusEnum.DISABLE.getStatus())) {
            user.setStatus(CommonStatusEnum.DISABLE.getStatus());
        } else {
            user.setStatus(CommonStatusEnum.ENABLE.getStatus());
        }
        user.setPassword(encodePassword(reqVO.getPassword())); // 加密密码
        userMapper.insert(user);
        // 插入关联岗位
        if (CollectionUtil.isNotEmpty(user.getPostIds())) {
            userPostMapper.insertBatch(convertList(user.getPostIds(),
                    postId -> {
                        SysUserPostDO userPostDO = new SysUserPostDO();
                        userPostDO.setUserId(user.getId());
                        userPostDO.setPostId(postId);
                        return userPostDO;
                    })
            );
        }
        // 插入角色
        insertUserRole(reqVO, user.getId());
        return user.getId();
    }

    private void insertUserRole(UserCreateReqVO reqVO, String userId) {
        if (CollectionUtil.isEmpty(reqVO.getRoleIds())) {
            return;
        }
        userRoleMapper.insertBatch(convertList(reqVO.getRoleIds(),
                roleId -> {
                    SysUserRoleDO userRoleDO = new SysUserRoleDO();
                    userRoleDO.setUserId(userId);
                    userRoleDO.setRoleId(roleId);
                    return userRoleDO;
                })
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(UserUpdateReqVO reqVO) {
        // 校验正确性
        validateUserForCreateOrUpdate(reqVO.getId(), reqVO.getUsername(), reqVO.getMobile(), reqVO.getEmail(),
                reqVO.getDeptId(), reqVO.getPostIds());
        // 更新用户
        SysUserDO updateObj = UserConvert.INSTANCE.convert(reqVO);
        userMapper.updateById(updateObj);
        // 更新岗位
        updateUserPost(reqVO, updateObj);
        // 更新角色
        updateUserRole(reqVO);
    }

    private void updateUserRole(UserUpdateReqVO reqVO) {
        String userId = reqVO.getId();
        Set<String> dbRoleIds = userRoleMapper.selectRoleIdsByUserId(userId);
        // 计算新增和删除的角色编号
        Set<String> roleIds = reqVO.getRoleIds();
        Collection<String> createRoleIds = CollUtil.subtract(roleIds, dbRoleIds);
        Collection<String> deleteRoleIds = CollUtil.subtract(dbRoleIds, roleIds);
        // 执行新增和删除。对于已经授权的菜单，不用做任何处理
        if (!CollectionUtil.isEmpty(createRoleIds)) {
            userRoleMapper.insertBatch(convertList(createRoleIds,
                    roleId -> {
                        SysUserRoleDO userRoleDO = new SysUserRoleDO();
                        userRoleDO.setUserId(userId);
                        userRoleDO.setRoleId(roleId);
                        return userRoleDO;
                    })
            );
        }
        if (!CollectionUtil.isEmpty(deleteRoleIds)) {
            userRoleMapper.deleteBatchByUserIdAndRoleIds(userId, deleteRoleIds);
        }
    }

    private void updateUserPost(UserUpdateReqVO reqVO, SysUserDO updateObj) {
        String userId = reqVO.getId();
        Set<String> dbPostIds = convertSet(userPostMapper.selectListByUserId(userId), SysUserPostDO::getPostId);
        // 计算新增和删除的岗位编号
        Set<String> postIds = updateObj.getPostIds();
        Collection<String> createPostIds = CollUtil.subtract(postIds, dbPostIds);
        Collection<String> deletePostIds = CollUtil.subtract(dbPostIds, postIds);
        // 执行新增和删除。对于已经授权的菜单，不用做任何处理
        if (!CollectionUtil.isEmpty(createPostIds)) {
            userPostMapper.insertBatch(convertList(createPostIds,
                    postId -> {
                        SysUserPostDO userPostDO = new SysUserPostDO();
                        userPostDO.setUserId(userId);
                        userPostDO.setPostId(postId);
                        return userPostDO;})
            );
        }
        if (!CollectionUtil.isEmpty(deletePostIds)) {
            userPostMapper.deleteByUserIdAndPostId(userId, deletePostIds);
        }
    }

    @Override
    public void updateUserLogin(String id, String loginIp) {
        SysUserDO sysUserDO = new SysUserDO();
        sysUserDO.setId(id);
        sysUserDO.setLoginIp(loginIp);
        sysUserDO.setLoginDate(LocalDateTime.now());
        userMapper.updateById(sysUserDO);
    }

    @Override
    public void updateUserProfile(String id, UserProfileUpdateReqVO reqVO) {
        // 校验正确性
        validateUserExists(id);
        validateEmailUnique(id, reqVO.getEmail());
        validateMobileUnique(id, reqVO.getMobile());
        // 执行更新
        SysUserDO sysUserDO = UserConvert.INSTANCE.convert(reqVO);
        sysUserDO.setId(id);
        userMapper.updateById(sysUserDO);
    }

    @Override
    public void updateUserPassword(String id, UserProfileUpdatePasswordReqVO reqVO) {
        // 校验旧密码密码
        validateOldPassword(id, reqVO.getOldPassword());
        // 执行更新
        SysUserDO sysUserDO = new SysUserDO();
        sysUserDO.setId(id);
        SysUserDO updateObj = sysUserDO;
        updateObj.setPassword(encodePassword(reqVO.getNewPassword())); // 加密密码
        userMapper.updateById(updateObj);
    }

//    @Override
//    public String updateUserAvatar(String id, InputStream avatarFile) throws Exception {
//        validateUserExists(id);
//        // 存储文件
//        String avatar = fileApi.createFile(IoUtil.readBytes(avatarFile));
//        // 更新路径
//        AdminUserDO sysUserDO = new AdminUserDO();
//        sysUserDO.setId(id);
//        sysUserDO.setAvatar(avatar);
//        userMapper.updateById(sysUserDO);
//        return avatar;
//    }

    @Override
    public void updateUserPassword(String id, String password) {
        // 校验用户存在
        validateUserExists(id);
        // 更新密码
        SysUserDO updateObj = new SysUserDO();
        updateObj.setId(id);
        updateObj.setPassword(encodePassword(password)); // 加密密码
        userMapper.updateById(updateObj);
    }

    @Override
    public void updateUserStatus(String id, Integer status) {
        // 校验用户存在
        validateUserExists(id);
        // 更新状态
        SysUserDO updateObj = new SysUserDO();
        updateObj.setId(id);
        updateObj.setStatus(status);
        userMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(String id) {
        // 校验用户存在
        validateUserExists(id);
        // 删除用户
        userMapper.deleteById(id);
        // 删除用户关联数据
        permissionService.processUserDeleted(id);
        // 删除用户岗位
        userPostMapper.deleteByUserId(id);
    }

    @Override
    public void deleteUsers(List<String> idList) {
        userMapper.deleteBatchIds(idList);
    }

    @Override
    public SysUserDO getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    /**
     * 通过查用户的全部信息
     * @param sysUser 用户
     * @return
     */
    @Override
    public UserInfo getUserInfo(SysUserDO sysUser) {
        UserInfo userInfo = new UserInfo();
        SysUserVO sysUserVO = new SysUserVO();
        BeanUtils.copyProperties(sysUser, sysUserVO);
        userInfo.setSysUser(sysUserVO);
        // 设置角色列表
        Set<String> roleIds = userRoleMapper.selectRoleIdsByUserId(sysUser.getId());
        List<SysRoleDO> roleList = roleMapper.listRolesByRoleIds(roleIds);
        userInfo.setRoleList(RoleConvert.INSTANCE.convertToVOList(roleList));
        // 设置角色列表 （ID）
        userInfo.setRoles(ArrayUtil.toArray(roleIds, String.class));
        // 设置岗位列表
        Set<String> postIds = userPostMapper.selectPostIdsByUserId(sysUser.getId());
        List<SysPostDO> postList = postMapper.selectPostsByPostIds(postIds);
        userInfo.setPostList(PostConvert.INSTANCE.convertToVOList(postList));
        // 设置权限列表（menu.permission）
        Set<String> permissions = roleIds.stream()
                .map(menuMapper::selectListByRoleId)
                .flatMap(Collection::stream)
                .map(SysMenuDO::getPermission)
                .filter(StrUtil::isNotBlank)
                .collect(Collectors.toSet());
        // 如果是组合权限，进行拆解
        Set<String> newPermissions = new HashSet<>();
        for (String permission : permissions) {
            if (StrUtil.contains(permission, ",")) {
                List<String> split = StrUtil.split(permission, ",");
                split = split.stream().filter(StrUtil::isNotBlank).collect(Collectors.toList());
                newPermissions.addAll(split);
            } else {
                newPermissions.add(permission);
            }
        }
        userInfo.setPermissions(ArrayUtil.toArray(newPermissions, String.class));
        return userInfo;
    }

    @Override
    public Optional<SysUserDO> findOptionalByUsername(String username) {
        SysUserDO sysUserDO = userMapper.selectOne(new LambdaQueryWrapperX<SysUserDO>().eq(SysUserDO::getUsername, username));
        if (sysUserDO == null) {
            return Optional.empty();
        }
        return Optional.of(sysUserDO);
    }

    @Override
    public SysUserDO getUserByMobile(String mobile) {
        return userMapper.selectByMobile(mobile);
    }

    @Override
    public PageResult<SysUserDO> getUserPage(UserPageReqVO reqVO) {
        return userMapper.selectPage(reqVO, getDeptCondition(reqVO.getDeptId()));
    }

    @Override
    public SysUserDO getUser(String id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<SysUserDO> getUserListByDeptIds(Collection<String> deptIds) {
        if (CollUtil.isEmpty(deptIds)) {
            return Collections.emptyList();
        }
        return userMapper.selectListByDeptIds(deptIds);
    }

    @Override
    public List<SysUserDO> getUserListByPostIds(Collection<String> postIds) {
        if (CollUtil.isEmpty(postIds)) {
            return Collections.emptyList();
        }
        Set<String> userIds = convertSet(userPostMapper.selectListByPostIds(postIds), SysUserPostDO::getUserId);
        if (CollUtil.isEmpty(userIds)) {
            return Collections.emptyList();
        }
        return userMapper.selectBatchIds(userIds);
    }

    @Override
    public List<SysUserDO> getUserList(Collection<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return userMapper.selectBatchIds(ids);
    }

    @Override
    public void validateUserList(Collection<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // 获得岗位信息
        List<SysUserDO> users = userMapper.selectBatchIds(ids);
        Map<String, SysUserDO> userMap = CollectionUtils.convertMap(users, SysUserDO::getId);
        // 校验
        ids.forEach(id -> {
            SysUserDO user = userMap.get(id);
            if (user == null) {
                throw exception(USER_NOT_EXISTS);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(user.getStatus())) {
                throw exception(USER_IS_DISABLE, user.getNickname());
            }
        });
    }

    @Override
    public List<SysUserDO> getUserList(UserExportReqVO reqVO) {
        return userMapper.selectList(reqVO, getDeptCondition(reqVO.getDeptId()));
    }

    @Override
    public List<SysUserDO> getUserListByNickname(String nickname) {
        return userMapper.selectListByNickname(nickname);
    }

    /**
     * 获得部门条件：查询指定部门的子部门编号们，包括自身
     * @param deptId 部门编号
     * @return 部门编号集合
     */
    private Set<String> getDeptCondition(String deptId) {
        if (deptId == null) {
            return Collections.emptySet();
        }
        Set<String> deptIds = convertSet(deptService.getDeptListByParentIdFromCache(
                deptId, true), SysDeptDO::getId);
        deptIds.add(deptId); // 包括自身
        return deptIds;
    }

    private void validateUserForCreateOrUpdate(String id, String username, String mobile, String email,
                                              String deptId, Set<String> postIds) {
        // 关闭数据权限，避免因为没有数据权限，查询不到数据，进而导致唯一校验不正确
        DataPermissionUtils.executeIgnore(() -> {
            // 校验用户存在
            validateUserExists(id);
            // 校验用户名唯一
            validateUsernameUnique(id, username);
            // 校验手机号唯一
            validateMobileUnique(id, mobile);
            // 校验邮箱唯一
            validateEmailUnique(id, email);
            // 校验部门处于开启状态
            deptService.validateDeptList(CollectionUtils.singleton(deptId));
            // 校验岗位处于开启状态
            postService.validatePostList(postIds);
        });
    }

    @VisibleForTesting
    void validateUserExists(String id) {
        if (id == null) {
            return;
        }
        SysUserDO user = userMapper.selectById(id);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
    }

    @VisibleForTesting
    void validateUsernameUnique(String id, String username) {
        if (StrUtil.isBlank(username)) {
            return;
        }
        SysUserDO user = userMapper.selectByUsername(username);
        if (user == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(USER_USERNAME_EXISTS);
        }
        if (!user.getId().equals(id)) {
            throw exception(USER_USERNAME_EXISTS);
        }
    }

    @VisibleForTesting
    void validateEmailUnique(String id, String email) {
        if (StrUtil.isBlank(email)) {
            return;
        }
        SysUserDO user = userMapper.selectByEmail(email);
        if (user == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(USER_EMAIL_EXISTS);
        }
        if (!user.getId().equals(id)) {
            throw exception(USER_EMAIL_EXISTS);
        }
    }

    @VisibleForTesting
    void validateMobileUnique(String id, String mobile) {
        if (StrUtil.isBlank(mobile)) {
            return;
        }
        SysUserDO user = userMapper.selectByMobile(mobile);
        if (user == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(USER_MOBILE_EXISTS);
        }
        if (!user.getId().equals(id)) {
            throw exception(USER_MOBILE_EXISTS);
        }
    }

    /**
     * 校验旧密码
     * @param id          用户 id
     * @param oldPassword 旧密码
     */
    @VisibleForTesting
    void validateOldPassword(String id, String oldPassword) {
        SysUserDO user = userMapper.selectById(id);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
        if (!isPasswordMatch(oldPassword, user.getPassword())) {
            throw exception(USER_PASSWORD_FAILED);
        }
    }

//    @Override
//    @Transactional(rollbackFor = Exception.class) // 添加事务，异常则回滚所有导入
//    public UserImportRespVO importUserList(List<UserImportExcelVO> importUsers, boolean isUpdateSupport) {
//        if (CollUtil.isEmpty(importUsers)) {
//            throw exception(USER_IMPORT_LIST_IS_EMPTY);
//        }
//        UserImportRespVO respVO = UserImportRespVO.builder().createUsernames(new ArrayList<>())
//                .updateUsernames(new ArrayList<>()).failureUsernames(new LinkedHashMap<>()).build();
//        importUsers.forEach(importUser -> {
//            // 校验，判断是否有不符合的原因
//            try {
//                validateUserForCreateOrUpdate(null, null, importUser.getMobile(), importUser.getEmail(),
//                        importUser.getDeptId(), null);
//            } catch (ServiceException ex) {
//                respVO.getFailureUsernames().put(importUser.getUsername(), ex.getMessage());
//                return;
//            }
//            // 判断如果不存在，在进行插入
//            AdminUserDO existUser = userMapper.selectByUsername(importUser.getUsername());
//            if (existUser == null) {
//                userMapper.insert(UserConvert.INSTANCE.convert(importUser)
//                        .setPassword(encodePassword(userInitPassword)).setPostIds(new HashSet<>())); // 设置默认密码及空岗位编号数组
//                respVO.getCreateUsernames().add(importUser.getUsername());
//                return;
//            }
//            // 如果存在，判断是否允许更新
//            if (!isUpdateSupport) {
//                respVO.getFailureUsernames().put(importUser.getUsername(), USER_USERNAME_EXISTS.getMsg());
//                return;
//            }
//            AdminUserDO updateUser = UserConvert.INSTANCE.convert(importUser);
//            updateUser.setId(existUser.getId());
//            userMapper.updateById(updateUser);
//            respVO.getUpdateUsernames().add(importUser.getUsername());
//        });
//        return respVO;
//    }

    @Override
    public List<SysUserDO> getUserListByStatus(Integer status) {
        return userMapper.selectListByStatus(status);
    }

    @Override
    public boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public Set<String> getUserRoleIds(String id) {
        return userRoleMapper.selectRoleIdsByUserId(id) == null ? new HashSet<>() : userRoleMapper.selectRoleIdsByUserId(id);
    }

    @Override
    public IPage<SysUserDO> roleUserPage(RoleUserPageVO pageVO) {
        IPage<SysUserDO> pager = new RcPage<>(pageVO.getPageNo() - 1, pageVO.getPageSize());
        // 通过角色id查询用户id列表
        Set<String> userIds = userRoleMapper.selectUserIdsByRoleId(pageVO.getRoleId());
        // 通过用户ids，和其他条件查找用户列表
        QueryWrapper<SysUserDO> wrapper = new QueryWrapper<>();
        if (userIds.isEmpty()) {
            return pager;
        }
        wrapper.lambda().in(SysUserDO::getId, userIds);
        String username = pageVO.getUsername();
        String mobile = pageVO.getMobile();
        Integer sex = pageVO.getSex();
        if (username != null && !"".equals(username.trim())) {
            wrapper.lambda().like(SysUserDO::getUsername, username);
        }
        if (mobile != null && !"".equals(mobile.trim())) {
            wrapper.lambda().like(SysUserDO::getMobile, mobile);
        }
        if (sex != null) {
            wrapper.lambda().eq(SysUserDO::getSex, sex);
        }
        return userMapper.selectPage(pager, wrapper);
    }

    /**
     * 对密码进行加密
     *
     * @param password 密码
     * @return 加密后的密码
     */
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
