package com.rc.cloud.app.system.contorller.admin.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.system.controller.admin.v1.user.UserController;
import com.rc.cloud.app.system.mapper.dept.DeptMapper;
import com.rc.cloud.app.system.mapper.dept.PostMapper;
import com.rc.cloud.app.system.mapper.tenant.TenantPackageMapper;
import com.rc.cloud.app.system.model.dept.SysDeptPO;
import com.rc.cloud.app.system.model.dept.SysPostPO;
import com.rc.cloud.app.system.model.tenant.SysTenantPO;
import com.rc.cloud.app.system.model.tenant.SysTenantPackagePO;
import com.rc.cloud.app.system.model.user.SysUserPO;
import com.rc.cloud.app.system.service.tenant.TenantService;
import com.rc.cloud.app.system.service.user.AdminUserService;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantCreateReqVO;
import com.rc.cloud.app.system.vo.user.user.UserCreateReqVO;
import com.rc.cloud.app.system.vo.user.user.UserUpdatePasswordReqVO;
import com.rc.cloud.app.system.vo.user.user.UserUpdateReqVO;
import com.rc.cloud.app.system.vo.user.user.UserUpdateStatusReqVO;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.tenant.core.context.TenantContextHolder;
import com.rc.cloud.common.test.annotation.RcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

import static com.rc.cloud.common.core.util.date.LocalDateTimeUtils.buildTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author oliveoil
 * @date 2023-06-13 13:23
 * 关联 {@link UserController} 类
 */
@RcTest
public class UserControllerTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Resource
    private TenantService tenantService;

    @Resource
    private DeptMapper deptMapper;

    @Resource
    private TenantPackageMapper tenantPackageMapper;

    @Resource
    private PostMapper postMapper;

    @Resource
    private AdminUserService userService;

    @Qualifier("springSecurityFilterChain")
    @BeforeEach
    public void setup() {
        SysTenantPO tenant = createTenant();
        TenantContextHolder.setTenantId(tenant.getId());
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    /**
     * @author rc@hqf
     * @date 2023/07/27
     * @description 创建系统用户相关测试
     */
    @Nested
    class CreateUserTests {
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:user:create"})
        public void createUser_success() throws Exception {
            SysDeptPO sysDeptPO = createDept();
            SysPostPO sysPostPO = createPost();
            UserCreateReqVO createReqVO = new UserCreateReqVO();
            createReqVO.setUsername("testuser");
            createReqVO.setNickname("test_nickname");
            createReqVO.setAvatar("www.baidu.com");
            createReqVO.setRemark("备注");
            createReqVO.setDeptId(sysDeptPO.getId());
            createReqVO.setPostIds(new HashSet<String>() {{add(sysPostPO.getId());}});
            createReqVO.setEmail("123232@qq.com");
            createReqVO.setMobile("13777777766");
            createReqVO.setSex(1);
            createReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
            createReqVO.setPassword("123456");
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(createReqVO);
            mvc.perform(post("/admin/user/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").isNotEmpty());
            SysUserPO dbUserPO = userService.getUserByUsername(createReqVO.getUsername());
            assertNotEquals(null, dbUserPO);
            assertEquals(createReqVO.getUsername(), dbUserPO.getUsername());
            assertEquals(createReqVO.getNickname(), dbUserPO.getNickname());
            assertEquals(createReqVO.getAvatar(), dbUserPO.getAvatar());
            assertEquals(createReqVO.getRemark(), dbUserPO.getRemark());
            assertEquals(createReqVO.getDeptId(), dbUserPO.getDeptId());
            assertEquals(createReqVO.getEmail(), dbUserPO.getEmail());
            assertEquals(createReqVO.getMobile(), dbUserPO.getMobile());
            assertEquals(createReqVO.getSex(), dbUserPO.getSex());
            assertEquals(createReqVO.getStatus(), dbUserPO.getStatus());
        }

        // sad path1: 用户名已存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:user:create"})
        public void createUser_usernameExists() throws Exception {
            SysUserPO sysUserPO = createUser1();
            SysDeptPO sysDeptPO = createDept();
            SysPostPO sysPostPO = createPost();
            UserCreateReqVO createReqVO = new UserCreateReqVO();
            createReqVO.setUsername(sysUserPO.getUsername());
            createReqVO.setNickname("test_nickname");
            createReqVO.setAvatar("www.baidu.com");
            createReqVO.setRemark("备注");
            createReqVO.setDeptId(sysDeptPO.getId());
            createReqVO.setPostIds(new HashSet<String>() {{add(sysPostPO.getId());}});
            createReqVO.setEmail("123232@qq.com");
            createReqVO.setMobile("13777777766");
            createReqVO.setSex(1);
            createReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
            createReqVO.setPassword("123456");
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(createReqVO);
            mvc.perform(post("/admin/user/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002003000))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("用户账号已经存在"));
        }

        // sad path2: 部门不存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:user:create"})
        public void createUser_deptNotExists() throws Exception {
            SysPostPO sysPostPO = createPost();
            UserCreateReqVO createReqVO = new UserCreateReqVO();
            createReqVO.setUsername("testuser");
            createReqVO.setNickname("test_nickname");
            createReqVO.setAvatar("www.baidu.com");
            createReqVO.setRemark("备注");
            createReqVO.setDeptId("999999");
            createReqVO.setPostIds(new HashSet<String>() {{add(sysPostPO.getId());}});
            createReqVO.setEmail("123232@qq.com");
            createReqVO.setMobile("13777777766");
            createReqVO.setSex(1);
            createReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
            createReqVO.setPassword("123456");
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(createReqVO);
            mvc.perform(post("/admin/user/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002004002))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("当前部门不存在"));
        }

        // sad path3: 岗位不存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:user:create"})
        public void createUser_postNotExists() throws Exception {
            SysDeptPO sysDeptPO = createDept();
            UserCreateReqVO createReqVO = new UserCreateReqVO();
            createReqVO.setUsername("testuser");
            createReqVO.setNickname("test_nickname");
            createReqVO.setAvatar("www.baidu.com");
            createReqVO.setRemark("备注");
            createReqVO.setDeptId(sysDeptPO.getId());
            createReqVO.setPostIds(new HashSet<String>() {{
                add("999999");
            }});
            createReqVO.setEmail("123232@qq.com");
            createReqVO.setMobile("13777777766");
            createReqVO.setSex(1);
            createReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
            createReqVO.setPassword("123456");
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(createReqVO);
            mvc.perform(post("/admin/user/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002005000))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("当前岗位不存在"));
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/27
     * @description 更新系统用户相关测试
     */
    @Nested
    class UpdateUserTests {

        // happy path: 更新用户信息成功
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:user:update"})
        public void updateUser_success() throws Exception {
            SysUserPO sysUserPO = createUser1();
            SysDeptPO sysDeptPO = createDept();
            SysPostPO sysPostPO = createPost();
            UserUpdateReqVO updateReqVO = new UserUpdateReqVO();
            updateReqVO.setId(sysUserPO.getId());
            updateReqVO.setUsername("testuser12377");
            updateReqVO.setNickname("test_nickname");
            updateReqVO.setAvatar("www.baidu.com");
            updateReqVO.setRemark("备注");
            updateReqVO.setDeptId(sysDeptPO.getId());
            updateReqVO.setPostIds(new HashSet<String>() {{add(sysPostPO.getId());}});
            updateReqVO.setEmail("123232@qq.com");
            updateReqVO.setMobile("13777777789");
            updateReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
            updateReqVO.setRoleIds(new HashSet<String>() {{add("1"); add("2");}});
            updateReqVO.setSex(1);
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(updateReqVO);
            mvc.perform(put("/admin/user/update")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").isNotEmpty());
            SysUserPO dbUserPO = userService.getUser(updateReqVO.getId());
            assertNotEquals(null, dbUserPO);
            assertEquals(updateReqVO.getUsername(), dbUserPO.getUsername());
            assertEquals(updateReqVO.getNickname(), dbUserPO.getNickname());
            assertEquals(updateReqVO.getAvatar(), dbUserPO.getAvatar());
            assertEquals(updateReqVO.getRemark(), dbUserPO.getRemark());
            assertEquals(updateReqVO.getDeptId(), dbUserPO.getDeptId());
            assertEquals(updateReqVO.getEmail(), dbUserPO.getEmail());
            assertEquals(updateReqVO.getMobile(), dbUserPO.getMobile());
            assertEquals(updateReqVO.getSex(), dbUserPO.getSex());
            assertEquals(updateReqVO.getStatus(), dbUserPO.getStatus());
        }

        // sad path1: 用户不存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:user:update"})
        public void updateUser_userNotExists() throws Exception {
            SysDeptPO sysDeptPO = createDept();
            SysPostPO sysPostPO = createPost();
            UserUpdateReqVO updateReqVO = new UserUpdateReqVO();
            updateReqVO.setId("999999");
            updateReqVO.setUsername("testuser12377");
            updateReqVO.setNickname("test_nickname");
            updateReqVO.setAvatar("www.baidu.com");
            updateReqVO.setRemark("备注");
            updateReqVO.setDeptId(sysDeptPO.getId());
            updateReqVO.setPostIds(new HashSet<String>() {{add(sysPostPO.getId());}});
            updateReqVO.setEmail("123232@qq.com");
            updateReqVO.setMobile("13777777789");
            updateReqVO.setRoleIds(new HashSet<String>() {{add("1"); add("2");}});
            updateReqVO.setSex(1);
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(updateReqVO);
            mvc.perform(put("/admin/user/update")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002003003))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("用户不存在"));
        }

        // sad path2: 用户名已存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:user:update"})
        public void updateUser_usernameExists() throws Exception {
            SysUserPO sysUserPO1 = createUser1();
            SysUserPO sysUserPO2 = createUser2();
            SysDeptPO sysDeptPO = createDept();
            SysPostPO sysPostPO = createPost();
            UserUpdateReqVO updateReqVO = new UserUpdateReqVO();
            updateReqVO.setId(sysUserPO1.getId());
            updateReqVO.setUsername(sysUserPO2.getUsername());
            updateReqVO.setNickname("test_nickname");
            updateReqVO.setAvatar("www.baidu.com");
            updateReqVO.setRemark("备注");
            updateReqVO.setDeptId(sysDeptPO.getId());
            updateReqVO.setPostIds(new HashSet<String>() {{add(sysPostPO.getId());}});
            updateReqVO.setEmail("123232@qq.com");
            updateReqVO.setMobile("13777777789");
            updateReqVO.setRoleIds(new HashSet<String>() {{add("1"); add("2");}});
            updateReqVO.setSex(1);
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(updateReqVO);
            mvc.perform(put("/admin/user/update")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002003000))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("用户账号已经存在"));
        }

        // sad path3: 部门不存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:user:update"})
        public void updateUser_deptNotExists() throws Exception {
            SysUserPO sysUserPO = createUser1();
            SysPostPO sysPostPO = createPost();
            UserUpdateReqVO updateReqVO = new UserUpdateReqVO();
            updateReqVO.setId(sysUserPO.getId());
            updateReqVO.setUsername("testuser12377");
            updateReqVO.setNickname("test_nickname");
            updateReqVO.setAvatar("www.baidu.com");
            updateReqVO.setRemark("备注");
            updateReqVO.setDeptId("999999");
            updateReqVO.setPostIds(new HashSet<String>() {{add(sysPostPO.getId());}});
            updateReqVO.setEmail("123232@qq.com");
            updateReqVO.setMobile("13777777789");
            updateReqVO.setRoleIds(new HashSet<String>() {{add("1"); add("2");}});
            updateReqVO.setSex(1);
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(updateReqVO);
            mvc.perform(put("/admin/user/update")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002004002))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("当前部门不存在"));
        }

        // sad path4: 岗位不存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:user:update"})
        public void updateUser_postNotExists() throws Exception {
            SysUserPO sysUserPO = createUser1();
            SysDeptPO sysDeptPO = createDept();
            UserUpdateReqVO updateReqVO = new UserUpdateReqVO();
            updateReqVO.setId(sysUserPO.getId());
            updateReqVO.setUsername("testuser12377");
            updateReqVO.setNickname("test_nickname");
            updateReqVO.setAvatar("www.baidu.com");
            updateReqVO.setRemark("备注");
            updateReqVO.setDeptId(sysDeptPO.getId());
            updateReqVO.setPostIds(new HashSet<String>() {{
                add("999999");
            }});
            updateReqVO.setEmail("123232@qq.com");
            updateReqVO.setMobile("13777777789");
            updateReqVO.setRoleIds(new HashSet<String>() {{
                add("1");
                add("2");
            }});
            updateReqVO.setSex(1);
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(updateReqVO);
            mvc.perform(put("/admin/user/update")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002005000))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("当前岗位不存在"));
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/27
     * @description 删除系统用户相关测试
     */
    @Nested
    class DeleteUserTests {

        // happy path: 删除用户成功
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:user:delete"})
        public void deleteUser_success() throws Exception {
            SysUserPO sysUserPO = createUser1();
            HashSet<String> ids = new HashSet<String>() {{
                add(sysUserPO.getId());
            }};
            mvc.perform(delete("/admin/user")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(ids.toString())
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").value(true));

            SysUserPO dbUserPO = userService.getUser(sysUserPO.getId());
            assertEquals(null, dbUserPO);
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/27
     * @description 更新系统用户字段相关测试
     */
    @Nested
    class UpdateUserFieldTests {

        // happy path: 更新用户密码成功
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:user:update-password"})
        public void updateUserPassword_success() throws Exception {
            SysUserPO sysUserPO = createUser1();
            UserUpdatePasswordReqVO updatePasswordReqVO = new UserUpdatePasswordReqVO();
            updatePasswordReqVO.setId(sysUserPO.getId());
            updatePasswordReqVO.setPassword("1234567");
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(updatePasswordReqVO);
            mvc.perform(put("/admin/user/update-password")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").isNotEmpty());
        }

        // happy path: 更新用户状态成功
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:user:update"})
        public void updateUserStatus_success() throws Exception {
            SysUserPO sysUserPO = createUser1();
            UserUpdateStatusReqVO updateStatusReqVO = new UserUpdateStatusReqVO();
            updateStatusReqVO.setId(sysUserPO.getId());
            updateStatusReqVO.setStatus(CommonStatusEnum.DISABLE.getStatus());
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(updateStatusReqVO);
            mvc.perform(put("/admin/user/update-status")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").isNotEmpty());
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/27
     * @description 获取系统用户分页相关测试
     */
    @Nested
    class GetUserPageTests {
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:user:query"})
        public void getUserPage_success() throws Exception {
            SysUserPO sysUserPO = createUser1();
            mvc.perform(get("/admin/user/page"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data.total").value(2)) // 创建租户时会默认创建一个用户
                    .andExpect(jsonPath("$.data.list").isArray())
                    .andExpect(jsonPath("$.data.list").isNotEmpty())
                    .andExpect(jsonPath("$.data.list[0].username").value(sysUserPO.getUsername()))
                    .andExpect(jsonPath("$.data.list[0].nickname").value(sysUserPO.getNickname()))
                    .andExpect(jsonPath("$.data.list[0].avatar").value(sysUserPO.getAvatar()))
                    .andExpect(jsonPath("$.data.list[0].remark").value(sysUserPO.getRemark()))
                    .andExpect(jsonPath("$.data.list[0].deptId").value(sysUserPO.getDeptId()))
                    .andExpect(jsonPath("$.data.list[0].email").value(sysUserPO.getEmail()))
                    .andExpect(jsonPath("$.data.list[0].mobile").value(sysUserPO.getMobile()));
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/27
     * @description 获取系统用户简单列表相关测试
     */
    @Nested
    class GetUserSimpleListTests {
        @Test
        @WithMockUser(username = "admin")
        public void getUserListAllSimple_success() throws Exception {
            SysUserPO sysUserPO = createUser1();
            mvc.perform(get("/admin/user/list-all-simple"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").isArray())
                    .andExpect(jsonPath("$.data").isNotEmpty())
                    .andExpect(jsonPath("$.data[0].id").value(sysUserPO.getId()))
                    .andExpect(jsonPath("$.data[0].nickname").value(sysUserPO.getNickname()));
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/27
     * @description 通过ID系统用户相关测试
     */
    @Nested
    class GetUserByIdTests {
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:user:query"})
        public void getUserById_then_success() throws Exception {
            SysUserPO sysUserPO = createUser1();
            mvc.perform(get("/admin/user/" + sysUserPO.getId()))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data.id").value(sysUserPO.getId()))
                    .andExpect(jsonPath("$.data.username").value(sysUserPO.getUsername()))
                    .andExpect(jsonPath("$.data.nickname").value(sysUserPO.getNickname()))
                    .andExpect(jsonPath("$.data.avatar").value(sysUserPO.getAvatar()))
                    .andExpect(jsonPath("$.data.remark").value(sysUserPO.getRemark()))
                    .andExpect(jsonPath("$.data.deptId").value(sysUserPO.getDeptId()))
                    .andExpect(jsonPath("$.data.email").value(sysUserPO.getEmail()))
                    .andExpect(jsonPath("$.data.mobile").value(sysUserPO.getMobile()));
        }
    }

    private SysUserPO createUser1() {
        SysDeptPO sysDeptPO = createDept();
        SysPostPO sysPostPO = createPost();
        UserCreateReqVO createReqVO = new UserCreateReqVO();
        createReqVO.setUsername("testuser111");
        createReqVO.setNickname("test_nickname111");
        createReqVO.setAvatar("www.baidu.com111");
        createReqVO.setRemark("备注111");
        createReqVO.setDeptId(sysDeptPO.getId());
        createReqVO.setPostIds(new HashSet<String>() {{add(sysPostPO.getId());}});
        createReqVO.setEmail("123232111@qq.com");
        createReqVO.setMobile("13777777111");
        createReqVO.setSex(1);
        createReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        createReqVO.setPassword("123111");
        String userId = userService.createUser(createReqVO);
        return userService.getUser(userId);
    }

    private SysUserPO createUser2() {
        SysDeptPO sysDeptPO = createDept();
        SysPostPO sysPostPO = createPost();
        UserCreateReqVO createReqVO = new UserCreateReqVO();
        createReqVO.setUsername("testuser222");
        createReqVO.setNickname("test_nickname222");
        createReqVO.setAvatar("www.baidu.com222");
        createReqVO.setRemark("备注222");
        createReqVO.setDeptId(sysDeptPO.getId());
        createReqVO.setPostIds(new HashSet<String>() {{add(sysPostPO.getId());}});
        createReqVO.setEmail("123232222@qq.com");
        createReqVO.setMobile("13777777222");
        createReqVO.setSex(1);
        createReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        createReqVO.setPassword("123222");
        String userId = userService.createUser(createReqVO);
        return userService.getUser(userId);
    }

    private SysTenantPO createTenant() {
        SysTenantPackagePO tenantPackage = this.createTenantPackage();
        TenantCreateReqVO tenantCreateReqVO = new TenantCreateReqVO();
        tenantCreateReqVO.setUsername("testuser123");
        tenantCreateReqVO.setPassword("test_password");
        tenantCreateReqVO.setName("test_tenant_name");
        tenantCreateReqVO.setDomain("https://www.baidu.com");
        tenantCreateReqVO.setContactName("huang");
        tenantCreateReqVO.setContactMobile("13777777777");
        tenantCreateReqVO.setPackageId(tenantPackage.getId());
        tenantCreateReqVO.setStatus(0);
        tenantCreateReqVO.setAccountCount(10000);
        tenantCreateReqVO.setExpireTime(buildTime(2033, 2, 2));
        String tenantId = tenantService.createTenant(tenantCreateReqVO);
        SysTenantPO tenant = tenantService.getTenant(tenantId);
        return tenant;
    }

    private SysTenantPackagePO createTenantPackage() {
        SysTenantPackagePO tenantPackage = new SysTenantPackagePO();
        tenantPackage.setName("test_tenant_name1");
        tenantPackage.setStatus(CommonStatusEnum.ENABLE.getStatus());
        tenantPackage.setRemark("test_tenant_remark1");
        Set<String> menuIds = new HashSet<>();
        menuIds.add("1");
        menuIds.add("2");
        menuIds.add("5");
        tenantPackage.setMenuIds(menuIds);
        tenantPackageMapper.insert(tenantPackage);
        return tenantPackage;
    }

    private SysDeptPO createDept() {
        SysDeptPO deptPO = new SysDeptPO();
        deptPO.setName("柔川信息");
        deptPO.setParentId("0");
        deptPO.setSort(1);
        deptPO.setLeaderUserId("1");
        deptPO.setPhone("12345678901");
        deptPO.setEmail("123123");
        deptPO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        deptMapper.insert(deptPO);
        return deptPO;
    }

    private SysPostPO createPost() {
        SysPostPO postPO = new SysPostPO();
        postPO.setName("测试岗位1");
        postPO.setCode("test_post1");
        postPO.setSort(1);
        postPO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        postPO.setRemark("备注信息11");
        postMapper.insert(postPO);
        return postPO;
    }
}
