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
            SysUserPO sysUserPO = createUser();
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

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:user:update"})
    public void updateUser_success() throws Exception {
        UserUpdateReqVO updateReqVO = new UserUpdateReqVO();
        updateReqVO.setId("1");
        updateReqVO.setUsername("testuser12377");
        updateReqVO.setNickname("test_nickname");
        updateReqVO.setAvatar("www.baidu.com");
        updateReqVO.setRemark("备注");
        updateReqVO.setDeptId("100");
        updateReqVO.setPostIds(new HashSet<String>() {{add("1"); add("2");}});
        updateReqVO.setEmail("123232@qq.com");
        updateReqVO.setMobile("13777777789");
        updateReqVO.setRoleIds(new HashSet<String>() {{add("1"); add("2");}});
        updateReqVO.setSex(1);
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(updateReqVO);
        mvc.perform(put("/sys/user/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:user:delete"})
    public void deleteUser_success() throws Exception {
        mvc.perform(delete("/sys/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[1,2]")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(true));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:user:update-password"})
    public void updateUserPassword_success() throws Exception {
        UserUpdatePasswordReqVO updatePasswordReqVO = new UserUpdatePasswordReqVO();
        updatePasswordReqVO.setId("1");
        updatePasswordReqVO.setPassword("1234567");
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(updatePasswordReqVO);
        mvc.perform(put("/sys/user/update-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:user:update"})
    public void updateUserStatus_success() throws Exception {
        UserUpdateStatusReqVO updateStatusReqVO = new UserUpdateStatusReqVO();
        updateStatusReqVO.setId("1");
        updateStatusReqVO.setStatus(1);
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(updateStatusReqVO);
        mvc.perform(put("/sys/user/update-status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:user:query"})
    public void getUserPage_success() throws Exception {
        mvc.perform(get("/sys/user/page"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.total").value(2))
                .andExpect(jsonPath("$.data.list").isArray())
                .andExpect(jsonPath("$.data.list").isNotEmpty())
                .andExpect(jsonPath("$.data.list[0].username").value("hqftest123"));
    }

    @Test
    @WithMockUser(username = "admin")
    public void getUserListAllSimple_success() throws Exception {
        mvc.perform(get("/sys/user/list-all-simple"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data[0].nickname").value("rc"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:user:query"})
    public void getUserById_then_success() throws Exception {
        mvc.perform(get("/sys/user/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.username").value("admin"));
    }

    private SysUserPO createUser() {
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
