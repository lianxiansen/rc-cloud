package com.rc.cloud.app.system.service.user;

import cn.hutool.core.util.RandomUtil;
import com.rc.cloud.app.system.model.dept.SysDeptPO;
import com.rc.cloud.app.system.model.dept.SysPostPO;
import com.rc.cloud.app.system.model.tenant.SysTenantPO;
import com.rc.cloud.app.system.model.user.SysUserPO;
import com.rc.cloud.app.system.mapper.dept.UserPostMapper;
import com.rc.cloud.app.system.mapper.user.AdminUserMapper;
import com.rc.cloud.app.system.model.dept.SysUserPostPO;
import com.rc.cloud.app.system.service.dept.DeptService;
import com.rc.cloud.app.system.service.dept.PostService;
import com.rc.cloud.app.system.service.permission.PermissionService;
import com.rc.cloud.app.system.service.tenant.TenantService;
import com.rc.cloud.app.system.vo.user.profile.UserProfileUpdatePasswordReqVO;
import com.rc.cloud.app.system.vo.user.profile.UserProfileUpdateReqVO;
import com.rc.cloud.app.system.vo.user.user.UserCreateReqVO;
import com.rc.cloud.app.system.vo.user.user.UserExportReqVO;
import com.rc.cloud.app.system.vo.user.user.UserPageReqVO;
import com.rc.cloud.app.system.vo.user.user.UserUpdateReqVO;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.core.enums.SexEnum;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.util.collection.ArrayUtils;
import com.rc.cloud.common.core.util.collection.CollectionUtils;
import com.rc.cloud.common.test.core.ut.BaseDbUnitTest;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static cn.hutool.core.util.RandomUtil.randomEle;
import static com.rc.cloud.app.system.enums.ErrorCodeConstants.*;
import static com.rc.cloud.common.core.util.collection.SetUtils.asSet;
import static com.rc.cloud.common.core.util.date.LocalDateTimeUtils.buildBetweenTime;
import static com.rc.cloud.common.core.util.date.LocalDateTimeUtils.buildTime;
import static com.rc.cloud.common.core.util.object.ObjectUtils.cloneIgnoreId;
import static com.rc.cloud.common.test.core.util.AssertUtils.assertPojoEquals;
import static com.rc.cloud.common.test.core.util.AssertUtils.assertServiceException;
import static com.rc.cloud.common.test.core.util.RandomUtils.*;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.util.Lists.newArrayList;

@Import(AdminUserServiceImpl.class)
public class AdminUserServiceImplTest extends BaseDbUnitTest {

    @Resource
    private AdminUserServiceImpl userService;

    @Resource
    private AdminUserMapper userMapper;
    @Resource
    private UserPostMapper userPostMapper;

    @MockBean
    private DeptService deptService;
    @MockBean
    private PostService postService;
    @MockBean
    private PermissionService permissionService;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private TenantService tenantService;
//    @MockBean
//    private FileApi fileApi;

    @Test
    public void testCreatUser_success() {
        // 准备参数
        UserCreateReqVO reqVO = randomPojo(UserCreateReqVO.class, o -> {
            o.setSex(RandomUtil.randomEle(SexEnum.values()).getSex());
            o.setMobile(randomString());
            o.setStatus(randomEle(CommonStatusEnum.values()).getStatus());
            o.setPostIds(asSet("1", "2"));
        });
        // mock 账户额度充足
        SysTenantPO tenant = randomPojo(SysTenantPO.class, o -> o.setAccountCount(1));
        doNothing().when(tenantService).handleTenantInfo(argThat(handler -> {
            handler.handle(tenant);
            return true;
        }));
        // mock deptService 的方法
        SysDeptPO dept = randomPojo(SysDeptPO.class, o -> {
            o.setId(reqVO.getDeptId());
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
        });
        when(deptService.getDept(eq(dept.getId()))).thenReturn(dept);
        // mock postService 的方法
        List<SysPostPO> posts = CollectionUtils.convertList(reqVO.getPostIds(), postId ->
                randomPojo(SysPostPO.class, o -> {
                    o.setId(postId);
                    o.setStatus(CommonStatusEnum.ENABLE.getStatus());
                }));
        when(postService.getPostList(eq(reqVO.getPostIds()), isNull())).thenReturn(posts);
        // mock passwordEncoder 的方法
        when(passwordEncoder.encode(eq(reqVO.getPassword()))).thenReturn("yudaoyuanma");

        // 调用
        String userId = userService.createUser(reqVO);
        // 断言
        SysUserPO user = userMapper.selectById(userId);
        assertPojoEquals(reqVO, user, "password");
        assertEquals("yudaoyuanma", user.getPassword());
        // 断言关联岗位
        List<SysUserPostPO> userPosts = userPostMapper.selectListByUserId(user.getId());
        assertEquals("1", userPosts.get(0).getPostId());
        assertEquals("2", userPosts.get(1).getPostId());
    }

    @Test
    public void testCreatUser_max() {
        // 准备参数
        UserCreateReqVO reqVO = randomPojo(UserCreateReqVO.class);
        // mock 账户额度不足
        SysTenantPO tenant = randomPojo(SysTenantPO.class, o -> o.setAccountCount(-1));
        doNothing().when(tenantService).handleTenantInfo(argThat(handler -> {
            handler.handle(tenant);
            return true;
        }));

        // 调用，并断言异常
        assertServiceException(() -> userService.createUser(reqVO), USER_COUNT_MAX, -1);
    }

    @Test
    public void testUpdateUser_success() {
        // mock 数据
        SysUserPO dbUser = randomAdminUserDO(o -> o.setPostIds(asSet("1", "2")));
        userMapper.insert(dbUser);
        SysUserPostPO userPostDO1 = new SysUserPostPO();
        userPostDO1.setUserId(dbUser.getId());
        userPostDO1.setPostId("1");
        userPostMapper.insert(userPostDO1);
        SysUserPostPO userPostDO2 = new SysUserPostPO();
        userPostDO2.setUserId(dbUser.getId());
        userPostDO2.setPostId("2");
        userPostMapper.insert(userPostDO2);
        // 准备参数
        UserUpdateReqVO reqVO = randomPojo(UserUpdateReqVO.class, o -> {
            o.setId(dbUser.getId());
            o.setSex(randomEle(SexEnum.values()).getSex());
            o.setMobile(randomString());
            o.setPostIds(asSet("2", "3"));
        });
        // mock deptService 的方法
        SysDeptPO dept = randomPojo(SysDeptPO.class, o -> {
            o.setId(reqVO.getDeptId());
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
        });
        when(deptService.getDept(eq(dept.getId()))).thenReturn(dept);
        // mock postService 的方法
        List<SysPostPO> posts = CollectionUtils.convertList(reqVO.getPostIds(), postId ->
                randomPojo(SysPostPO.class, o -> {
                    o.setId(postId);
                    o.setStatus(CommonStatusEnum.ENABLE.getStatus());
                }));
        when(postService.getPostList(eq(reqVO.getPostIds()), isNull())).thenReturn(posts);

        // 调用
        userService.updateUser(reqVO);
        // 断言
        SysUserPO user = userMapper.selectById(reqVO.getId());
        assertPojoEquals(reqVO, user);
        // 断言关联岗位
        List<SysUserPostPO> userPosts = userPostMapper.selectListByUserId(user.getId());
        assertEquals("2", userPosts.get(0).getPostId());
        assertEquals("3", userPosts.get(1).getPostId());
    }

    @Test
    public void testUpdateUserLogin() {
        // mock 数据
        SysUserPO user = randomAdminUserDO(o -> o.setLoginDate(null));
        userMapper.insert(user);
        // 准备参数
        String id = user.getId();
        String loginIp = randomString();

        // 调用
        userService.updateUserLogin(id, loginIp);
        // 断言
        SysUserPO dbUser = userMapper.selectById(id);
        assertEquals(loginIp, dbUser.getLoginIp());
        assertNotNull(dbUser.getLoginDate());
    }

    @Test
    public void testUpdateUserProfile_success() {
        // mock 数据
        SysUserPO dbUser = randomAdminUserDO();
        userMapper.insert(dbUser);
        // 准备参数
        String userId = dbUser.getId();
        UserProfileUpdateReqVO reqVO = randomPojo(UserProfileUpdateReqVO.class, o -> {
            o.setMobile(randomString());
            o.setSex(randomEle(SexEnum.values()).getSex());
        });

        // 调用
        userService.updateUserProfile(userId, reqVO);
        // 断言
        SysUserPO user = userMapper.selectById(userId);
        assertPojoEquals(reqVO, user);
    }

    @Test
    public void testUpdateUserPassword_success() {
        // mock 数据
        SysUserPO dbUser = randomAdminUserDO(o -> o.setPassword("encode:tudou"));
        userMapper.insert(dbUser);
        // 准备参数
        String userId = dbUser.getId();
        UserProfileUpdatePasswordReqVO reqVO = randomPojo(UserProfileUpdatePasswordReqVO.class, o -> {
            o.setOldPassword("tudou");
            o.setNewPassword("yuanma");
        });
        // mock 方法
        when(passwordEncoder.encode(anyString())).then(
                (Answer<String>) invocationOnMock -> "encode:" + invocationOnMock.getArgument(0));
        when(passwordEncoder.matches(eq(reqVO.getOldPassword()), eq(dbUser.getPassword()))).thenReturn(true);

        // 调用
        userService.updateUserPassword(userId, reqVO);
        // 断言
        SysUserPO user = userMapper.selectById(userId);
        assertEquals("encode:yuanma", user.getPassword());
    }

//    @Test
//    public void testUpdateUserAvatar_success() throws Exception {
//        // mock 数据
//        AdminUserDO dbUser = randomAdminUserDO();
//        userMapper.insert(dbUser);
//        // 准备参数
//        Long userId = dbUser.getId();
//        byte[] avatarFileBytes = randomBytes(10);
//        ByteArrayInputStream avatarFile = new ByteArrayInputStream(avatarFileBytes);
//        // mock 方法
//        String avatar = randomString();
//        when(fileApi.createFile(eq( avatarFileBytes))).thenReturn(avatar);
//
//        // 调用
//        userService.updateUserAvatar(userId, avatarFile);
//        // 断言
//        AdminUserDO user = userMapper.selectById(userId);
//        assertEquals(avatar, user.getAvatar());
//    }

    @Test
    public void testUpdateUserPassword02_success() {
        // mock 数据
        SysUserPO dbUser = randomAdminUserDO();
        userMapper.insert(dbUser);
        // 准备参数
        String userId = dbUser.getId();
        String password = "yudao";
        // mock 方法
        when(passwordEncoder.encode(anyString())).then(
                (Answer<String>) invocationOnMock -> "encode:" + invocationOnMock.getArgument(0));

        // 调用
        userService.updateUserPassword(userId, password);
        // 断言
        SysUserPO user = userMapper.selectById(userId);
        assertEquals("encode:" + password, user.getPassword());
    }

    @Test
    public void testUpdateUserStatus() {
        // mock 数据
        SysUserPO dbUser = randomAdminUserDO();
        userMapper.insert(dbUser);
        // 准备参数
        String userId = dbUser.getId();
        Integer status = randomCommonStatus();

        // 调用
        userService.updateUserStatus(userId, status);
        // 断言
        SysUserPO user = userMapper.selectById(userId);
        assertEquals(status, user.getStatus());
    }

    @Test
    public void testDeleteUser_success(){
        // mock 数据
        SysUserPO dbUser = randomAdminUserDO();
        userMapper.insert(dbUser);
        // 准备参数
        String userId = dbUser.getId();

        // 调用数据
        userService.deleteUser(userId);
        // 校验结果
        assertNull(userMapper.selectById(userId));
        // 校验调用次数
        verify(permissionService, times(1)).processUserDeleted(eq(userId));
    }

    @Test
    public void testGetUserByUsername() {
        // mock 数据
        SysUserPO dbUser = randomAdminUserDO();
        userMapper.insert(dbUser);
        // 准备参数
        String username = dbUser.getUsername();

        // 调用
        SysUserPO user = userService.getUserByUsername(username);
        // 断言
        // 暂时不比较权限
        dbUser.setAuthoritySet(null);
        assertPojoEquals(dbUser, user);
    }

    @Test
    public void testGetUserByMobile() {
        // mock 数据
        SysUserPO dbUser = randomAdminUserDO();
        userMapper.insert(dbUser);
        // 准备参数
        String mobile = dbUser.getMobile();

        // 调用
        SysUserPO user = userService.getUserByMobile(mobile);
        // 断言
        // 暂时不比较权限
        dbUser.setAuthoritySet(null);
        assertPojoEquals(dbUser, user);
    }

    @Test
    public void testGetUserPage() {
        // mock 数据
        SysUserPO dbUser = initGetUserPageData();
        // 准备参数
        UserPageReqVO reqVO = new UserPageReqVO();
        reqVO.setUsername("tu");
        reqVO.setMobile("1560");
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        reqVO.setCreateTime(buildBetweenTime(2020, 12, 1, 2020, 12, 24));
        reqVO.setDeptId("1"); // 其中，1L 是 2L 的父部门
        // mock 方法
        List<SysDeptPO> deptList = newArrayList(randomPojo(SysDeptPO.class, o -> o.setId("2")));
        when(deptService.getDeptListByParentIdFromCache(eq(reqVO.getDeptId()), eq(true))).thenReturn(deptList);

        // 调用
        PageResult<SysUserPO> pageResult = userService.getUserPage(reqVO);
        // 断言
        assertEquals(1, pageResult.getTotal());
        assertEquals(1, pageResult.getList().size());
        // 暂时不比较权限
        dbUser.setAuthoritySet(null);
        assertPojoEquals(dbUser, pageResult.getList().get(0));
    }

    @Test
    public void testGetUserList_export() {
        // mock 数据
        SysUserPO dbUser = initGetUserPageData();
        // 准备参数
        UserExportReqVO reqVO = new UserExportReqVO();
        reqVO.setUsername("tu");
        reqVO.setMobile("1560");
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        reqVO.setCreateTime(buildBetweenTime(2020, 12, 1, 2020, 12, 24));
        reqVO.setDeptId("1"); // 其中，1L 是 2L 的父部门
        // mock 方法
        List<SysDeptPO> deptList = newArrayList(randomPojo(SysDeptPO.class, o -> o.setId("2")));
        when(deptService.getDeptListByParentIdFromCache(eq(reqVO.getDeptId()), eq(true))).thenReturn(deptList);

        // 调用
        List<SysUserPO> list = userService.getUserList(reqVO);
        // 断言
        assertEquals(1, list.size());
        // 暂时不比较权限
        dbUser.setAuthoritySet(null);
        assertPojoEquals(dbUser, list.get(0));
    }

    /**
     * 初始化 getUserPage 方法的测试数据
     */
    private SysUserPO initGetUserPageData() {
        // mock 数据
        SysUserPO dbUser = randomAdminUserDO(o -> { // 等会查询到
            o.setUsername("tudou");
            o.setMobile("15601691300");
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
            o.setCreateTime(buildTime(2020, 12, 12));
            o.setDeptId("2");
        });
        userMapper.insert(dbUser);
        // 测试 username 不匹配
        userMapper.insert(cloneIgnoreId(dbUser, o -> o.setUsername("dou")));
        // 测试 mobile 不匹配
        userMapper.insert(cloneIgnoreId(dbUser, o -> o.setMobile("18818260888")));
        // 测试 status 不匹配
        userMapper.insert(cloneIgnoreId(dbUser, o -> o.setStatus(CommonStatusEnum.DISABLE.getStatus())));
        // 测试 createTime 不匹配
        userMapper.insert(cloneIgnoreId(dbUser, o -> o.setCreateTime(buildTime(2020, 11, 11))));
        // 测试 dept 不匹配
        userMapper.insert(cloneIgnoreId(dbUser, o -> o.setDeptId("0")));
        return dbUser;
    }

    @Test
    public void testGetUser() {
        // mock 数据
        SysUserPO dbUser = randomAdminUserDO();
        userMapper.insert(dbUser);
        // 准备参数
        String userId = dbUser.getId();

        // 调用
        SysUserPO user = userService.getUser(userId);
        // 断言
        // 暂时不比较权限
        dbUser.setAuthoritySet(null);
        assertPojoEquals(dbUser, user);
    }

    @Test
    public void testGetUserListByDeptIds() {
        // mock 数据
        SysUserPO dbUser = randomAdminUserDO(o -> o.setDeptId("1"));
        userMapper.insert(dbUser);
        // 测试 deptId 不匹配
        userMapper.insert(cloneIgnoreId(dbUser, o -> o.setDeptId("2")));
        // 准备参数
        Collection<String> deptIds = singleton("1");

        // 调用
        List<SysUserPO> list = userService.getUserListByDeptIds(deptIds);
        // 断言
        assertEquals(1, list.size());
        // 暂时不比较权限
        dbUser.setAuthoritySet(null);
        assertEquals(dbUser, list.get(0));
    }

    /**
     * 情况一，校验不通过，导致插入失败
     */
//    @Test
//    public void testImportUserList_01() {
//        // 准备参数
//        UserImportExcelVO importUser = randomPojo(UserImportExcelVO.class, o -> {
//        });
//        // mock 方法，模拟失败
//        doThrow(new ServiceException(DEPT_NOT_FOUND)).when(deptService).validateDeptList(any());
//
//        // 调用
//        UserImportRespVO respVO = userService.importUserList(newArrayList(importUser), true);
//        // 断言
//        assertEquals(0, respVO.getCreateUsernames().size());
//        assertEquals(0, respVO.getUpdateUsernames().size());
//        assertEquals(1, respVO.getFailureUsernames().size());
//        assertEquals(DEPT_NOT_FOUND.getMsg(), respVO.getFailureUsernames().get(importUser.getUsername()));
//    }

    /**
     * 情况二，不存在，进行插入
     */
//    @Test
//    public void testImportUserList_02() {
//        // 准备参数
//        UserImportExcelVO importUser = randomPojo(UserImportExcelVO.class, o -> {
//            o.setStatus(randomEle(CommonStatusEnum.values()).getStatus()); // 保证 status 的范围
//            o.setSex(randomEle(SexEnum.values()).getSex()); // 保证 sex 的范围
//        });
//        // mock deptService 的方法
//        DeptDO dept = randomPojo(DeptDO.class, o -> {
//            o.setId(importUser.getDeptId());
//            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
//        });
//        when(deptService.getDept(eq(dept.getId()))).thenReturn(dept);
//        // mock passwordEncoder 的方法
////        when(passwordEncoder.encode(eq("yudaoyuanma"))).thenReturn("java");
//
//        // 调用
//        UserImportRespVO respVO = userService.importUserList(newArrayList(importUser), true);
//        // 断言
//        assertEquals(1, respVO.getCreateUsernames().size());
//        AdminUserDO user = userMapper.selectByUsername(respVO.getCreateUsernames().get(0));
//        assertPojoEquals(importUser, user);
//        assertEquals("java", user.getPassword());
//        assertEquals(0, respVO.getUpdateUsernames().size());
//        assertEquals(0, respVO.getFailureUsernames().size());
//    }

    /**
     * 情况三，存在，但是不强制更新
     */
//    @Test
//    public void testImportUserList_03() {
//        // mock 数据
//        AdminUserDO dbUser = randomAdminUserDO();
//        userMapper.insert(dbUser);
//        // 准备参数
//        UserImportExcelVO importUser = randomPojo(UserImportExcelVO.class, o -> {
//            o.setStatus(randomEle(CommonStatusEnum.values()).getStatus()); // 保证 status 的范围
//            o.setSex(randomEle(SexEnum.values()).getSex()); // 保证 sex 的范围
//            o.setUsername(dbUser.getUsername());
//        });
//        // mock deptService 的方法
//        DeptDO dept = randomPojo(DeptDO.class, o -> {
//            o.setId(importUser.getDeptId());
//            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
//        });
//        when(deptService.getDept(eq(dept.getId()))).thenReturn(dept);
//
//        // 调用
//        UserImportRespVO respVO = userService.importUserList(newArrayList(importUser), false);
//        // 断言
//        assertEquals(0, respVO.getCreateUsernames().size());
//        assertEquals(0, respVO.getUpdateUsernames().size());
//        assertEquals(1, respVO.getFailureUsernames().size());
//        assertEquals(USER_USERNAME_EXISTS.getMsg(), respVO.getFailureUsernames().get(importUser.getUsername()));
//    }

    /**
     * 情况四，存在，强制更新
     */
//    @Test
//    public void testImportUserList_04() {
//        // mock 数据
//        AdminUserDO dbUser = randomAdminUserDO();
//        userMapper.insert(dbUser);
//        // 准备参数
//        UserImportExcelVO importUser = randomPojo(UserImportExcelVO.class, o -> {
//            o.setStatus(randomEle(CommonStatusEnum.values()).getStatus()); // 保证 status 的范围
//            o.setSex(randomEle(SexEnum.values()).getSex()); // 保证 sex 的范围
//            o.setUsername(dbUser.getUsername());
//        });
//        // mock deptService 的方法
//        DeptDO dept = randomPojo(DeptDO.class, o -> {
//            o.setId(importUser.getDeptId());
//            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
//        });
//        when(deptService.getDept(eq(dept.getId()))).thenReturn(dept);
//
//        // 调用
//        UserImportRespVO respVO = userService.importUserList(newArrayList(importUser), true);
//        // 断言
//        assertEquals(0, respVO.getCreateUsernames().size());
//        assertEquals(1, respVO.getUpdateUsernames().size());
//        AdminUserDO user = userMapper.selectByUsername(respVO.getUpdateUsernames().get(0));
//        assertPojoEquals(importUser, user);
//        assertEquals(0, respVO.getFailureUsernames().size());
//    }

    @Test
    public void testValidateUserExists_notExists() {
        assertServiceException(() -> userService.validateUserExists(randomLongId().toString()), USER_NOT_EXISTS);
    }

    @Test
    public void testValidateUsernameUnique_usernameExistsForCreate() {
        // 准备参数
        String username = randomString();
        // mock 数据
        userMapper.insert(randomAdminUserDO(o -> o.setUsername(username)));

        // 调用，校验异常
        assertServiceException(() -> userService.validateUsernameUnique(null, username),
                USER_USERNAME_EXISTS);
    }

    @Test
    public void testValidateUsernameUnique_usernameExistsForUpdate() {
        // 准备参数
        String id = randomLongId().toString();
        String username = randomString();
        // mock 数据
        userMapper.insert(randomAdminUserDO(o -> o.setUsername(username)));

        // 调用，校验异常
        assertServiceException(() -> userService.validateUsernameUnique(id, username),
                USER_USERNAME_EXISTS);
    }

    @Test
    public void testValidateEmailUnique_emailExistsForCreate() {
        // 准备参数
        String email = randomString();
        // mock 数据
        userMapper.insert(randomAdminUserDO(o -> o.setEmail(email)));

        // 调用，校验异常
        assertServiceException(() -> userService.validateEmailUnique(null, email),
                USER_EMAIL_EXISTS);
    }

    @Test
    public void testValidateEmailUnique_emailExistsForUpdate() {
        // 准备参数
        String id = randomLongId().toString();
        String email = randomString();
        // mock 数据
        userMapper.insert(randomAdminUserDO(o -> o.setEmail(email)));

        // 调用，校验异常
        assertServiceException(() -> userService.validateEmailUnique(id, email),
                USER_EMAIL_EXISTS);
    }

    @Test
    public void testValidateMobileUnique_mobileExistsForCreate() {
        // 准备参数
        String mobile = randomString();
        // mock 数据
        userMapper.insert(randomAdminUserDO(o -> o.setMobile(mobile)));

        // 调用，校验异常
        assertServiceException(() -> userService.validateMobileUnique(null, mobile),
                USER_MOBILE_EXISTS);
    }

    @Test
    public void testValidateMobileUnique_mobileExistsForUpdate() {
        // 准备参数
        String id = randomLongId().toString();
        String mobile = randomString();
        // mock 数据
        userMapper.insert(randomAdminUserDO(o -> o.setMobile(mobile)));

        // 调用，校验异常
        assertServiceException(() -> userService.validateMobileUnique(id, mobile),
                USER_MOBILE_EXISTS);
    }

    @Test
    public void testValidateOldPassword_notExists() {
        assertServiceException(() -> userService.validateOldPassword(randomLongId().toString(), randomString()),
                USER_NOT_EXISTS);
    }

    @Test
    public void testValidateOldPassword_passwordFailed() {
        // mock 数据
        SysUserPO user = randomAdminUserDO();
        userMapper.insert(user);
        // 准备参数
        String id = user.getId();
        String oldPassword = user.getPassword();

        // 调用，校验异常
        assertServiceException(() -> userService.validateOldPassword(id, oldPassword),
                USER_PASSWORD_FAILED);
        // 校验调用
        verify(passwordEncoder, times(1)).matches(eq(oldPassword), eq(user.getPassword()));
    }

    @Test
    public void testUserListByPostIds() {
        // 准备参数
        Collection<String> postIds = asSet("10", "20");
        // mock user1 数据
        SysUserPO user1 = randomAdminUserDO(o -> o.setPostIds(asSet("10", "30")));
        userMapper.insert(user1);
        SysUserPostPO userPostDO1 = new SysUserPostPO();
        userPostDO1.setUserId(user1.getId());
        userPostDO1.setPostId("10");
        userPostMapper.insert(userPostDO1);
        SysUserPostPO userPostDO2 = new SysUserPostPO();
        userPostDO2.setUserId(user1.getId());
        userPostDO2.setPostId("30");
        userPostMapper.insert(userPostDO2);
        // mock user2 数据
        SysUserPO user2 = randomAdminUserDO(o -> o.setPostIds(singleton("100")));
        userMapper.insert(user2);
        SysUserPostPO userPostDO3 = new SysUserPostPO();
        userPostDO3.setUserId(user2.getId());
        userPostDO3.setPostId("100");
        userPostMapper.insert(userPostDO3);

        // 调用
        List<SysUserPO> result = userService.getUserListByPostIds(postIds);
        // 断言
        assertEquals(1, result.size());
        // 暂时不比较权限
        user1.setAuthoritySet(null);
        assertEquals(user1, result.get(0));
    }

    @Test
    public void testGetUserList() {
        // mock 数据
        SysUserPO user = randomAdminUserDO();
        userMapper.insert(user);
        // 测试 id 不匹配
        userMapper.insert(randomAdminUserDO());
        // 准备参数
        Collection<String> ids = singleton(user.getId());

        // 调用
        List<SysUserPO> result = userService.getUserList(ids);
        // 断言
        assertEquals(1, result.size());
        // 暂时不比较权限
        user.setAuthoritySet(null);
        assertEquals(user, result.get(0));
    }

    @Test
    public void testGetUserMap() {
        // mock 数据
        SysUserPO user = randomAdminUserDO();
        userMapper.insert(user);
        // 测试 id 不匹配
        userMapper.insert(randomAdminUserDO());
        // 准备参数
        Collection<String> ids = singleton(user.getId());

        // 调用
        Map<String, SysUserPO> result = userService.getUserMap(ids);
        // 断言
        assertEquals(1, result.size());
        // 暂时不比较权限
        user.setAuthoritySet(null);
        assertEquals(user, result.get(user.getId()));
    }

    @Test
    public void testGetUserListByNickname() {
        // mock 数据
        SysUserPO user = randomAdminUserDO(o -> o.setNickname("芋头"));
        userMapper.insert(user);
        // 测试 nickname 不匹配
        userMapper.insert(randomAdminUserDO(o -> o.setNickname("源码")));
        // 准备参数
        String nickname = "芋";

        // 调用
        List<SysUserPO> result = userService.getUserListByNickname(nickname);
        // 断言
        assertEquals(1, result.size());
        // 暂时不比较权限
        user.setAuthoritySet(null);
        assertEquals(user, result.get(0));
    }

    @Test
    public void testGetUserListByStatus() {
        // mock 数据
        SysUserPO user = randomAdminUserDO(o -> o.setStatus(CommonStatusEnum.DISABLE.getStatus()));
        userMapper.insert(user);
        // 测试 status 不匹配
        userMapper.insert(randomAdminUserDO(o -> o.setStatus(CommonStatusEnum.ENABLE.getStatus())));
        // 准备参数
        Integer status = CommonStatusEnum.DISABLE.getStatus();

        // 调用
        List<SysUserPO> result = userService.getUserListByStatus(status);
        // 断言
        assertEquals(1, result.size());
        // 暂时不比较权限
        user.setAuthoritySet(null);
        assertEquals(user, result.get(0));
    }

    @Test
    public void testValidateUserList_success() {
        // mock 数据
        SysUserPO sysUserPO = randomAdminUserDO();
        sysUserPO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        userMapper.insert(sysUserPO);
        // 准备参数
        List<String> ids = singletonList(sysUserPO.getId());

        // 调用，无需断言
        userService.validateUserList(ids);
    }

    @Test
    public void testValidateUserList_notFound() {
        // 准备参数
        List<String> ids = singletonList(randomLongId().toString());

        // 调用, 并断言异常
        assertServiceException(() -> userService.validateUserList(ids), USER_NOT_EXISTS);
    }

    @Test
    public void testValidateUserList_notEnable() {
        // mock 数据
        SysUserPO sysUserPO = randomAdminUserDO();
        sysUserPO.setStatus(CommonStatusEnum.DISABLE.getStatus());
        userMapper.insert(sysUserPO);
        // 准备参数
        List<String> ids = singletonList(sysUserPO.getId());

        // 调用, 并断言异常
        assertServiceException(() -> userService.validateUserList(ids), USER_IS_DISABLE,
                sysUserPO.getNickname());
    }

    // ========== 随机对象 ==========

    @SafeVarargs
    private static SysUserPO randomAdminUserDO(Consumer<SysUserPO>... consumers) {
        Consumer<SysUserPO> consumer = (o) -> {
            o.setStatus(randomEle(CommonStatusEnum.values()).getStatus()); // 保证 status 的范围
            o.setSex(randomEle(SexEnum.values()).getSex()); // 保证 sex 的范围
        };
        return randomPojo(SysUserPO.class, ArrayUtils.append(consumer, consumers));
    }

}
