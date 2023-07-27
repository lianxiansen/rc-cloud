package com.rc.cloud.app.system.contorller.admin.tenant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.system.controller.admin.v1.tenant.TenantController;
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
import com.rc.cloud.app.system.vo.tenant.tenant.TenantUpdateReqVO;
import com.rc.cloud.app.system.vo.user.user.UserCreateReqVO;
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
 * 关联 {@link TenantController} 类
 */
@RcTest
public class TenantControllerTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Resource
    private TenantService tenantService;

    @Resource
    private TenantPackageMapper tenantPackageMapper;

    @Resource
    private AdminUserService userService;

    @Resource
    private DeptMapper deptMapper;

    @Resource
    private PostMapper postMapper;

    @Qualifier("springSecurityFilterChain")
    @BeforeEach
    public void setup() {
        SysTenantPO defaultTenant = createDefaultTenant();
        TenantContextHolder.setTenantId(defaultTenant.getId());
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    /**
     * @author rc@hqf
     * @date 2023/07/27
     * @description 通过租户名称获取租户ID相关测试
     */
    @Nested
    class GetTenantIdByNameTests {

        // happy path: 通过租户名称获取租户ID成功
        @Test
        @WithMockUser
        public void getIdByName_success() throws Exception {
            SysTenantPO sysTenantPO = createTenant();
            mvc.perform(get("/admin/tenant/get-id-by-name?name=" + sysTenantPO.getName()))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").value(sysTenantPO.getId()));
        }

        // sad path: 通过租户名称获取租户ID，不存在时返回空
        @Test
        @WithMockUser
        public void getIdByName_fail() throws Exception {
            mvc.perform(get("/admin/tenant/get-id-by-name?name=" + "test_tenant_name"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").isEmpty());
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/27
     * @description 创建租户相关测试
     */
    @Nested
    class CreateTenantTests {

        // happy path: 创建租户成功
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:tenant:create"})
        public void createTenant_success() throws Exception {
            SysTenantPackagePO tenantPackage = createTenantPackage();
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
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(tenantCreateReqVO);
            mvc.perform(post("/admin/tenant/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").isNotEmpty());
            // 验证数据库中是否存在该租户
            SysTenantPO dbTenantPO = tenantService.getTenantByName(tenantCreateReqVO.getName());
            assertNotEquals(null, dbTenantPO);
            assertEquals(tenantCreateReqVO.getName(), dbTenantPO.getName());
            assertEquals(tenantCreateReqVO.getDomain(), dbTenantPO.getDomain());
            assertEquals(tenantCreateReqVO.getContactName(), dbTenantPO.getContactName());
            assertEquals(tenantCreateReqVO.getContactMobile(), dbTenantPO.getContactMobile());
            assertEquals(tenantCreateReqVO.getPackageId(), dbTenantPO.getPackageId());
            assertEquals(tenantCreateReqVO.getStatus(), dbTenantPO.getStatus());
            assertEquals(tenantCreateReqVO.getAccountCount(), dbTenantPO.getAccountCount());
            assertEquals(tenantCreateReqVO.getExpireTime(), dbTenantPO.getExpireTime());
            // TODO:: 验证数据库中是否存在该租户的管理员账号
//            SysUserPO dbUserPo = userService.getUserByUsername(tenantCreateReqVO.getUsername());
//            assertNotEquals(null, dbUserPo);
//            assertEquals(tenantCreateReqVO.getUsername(), dbUserPo.getUsername());
//            assertEquals(tenantCreateReqVO.getPassword(), dbUserPo.getPassword());
//            assertEquals(dbTenantPO.getId(), dbUserPo.getTenantId());
        }

        // sad path1: 创建租户失败，租户名称已存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:tenant:create"})
        public void createTenant_fail_when_TenantNameExist() throws Exception {
            SysTenantPackagePO tenantPackage = createTenantPackage();
            SysTenantPO tenant = createTenant();
            TenantCreateReqVO tenantCreateReqVO = new TenantCreateReqVO();
            tenantCreateReqVO.setUsername("testuser123");
            tenantCreateReqVO.setPassword("test_password");
            tenantCreateReqVO.setName(tenant.getName());
            tenantCreateReqVO.setDomain("https://www.baidu.com");
            tenantCreateReqVO.setContactName("huang");
            tenantCreateReqVO.setContactMobile("13777777777");
            tenantCreateReqVO.setPackageId(tenantPackage.getId());
            tenantCreateReqVO.setStatus(0);
            tenantCreateReqVO.setAccountCount(10000);
            tenantCreateReqVO.setExpireTime(buildTime(2033, 2, 2));
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(tenantCreateReqVO);
            mvc.perform(post("/admin/tenant/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002015004))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("名字为【" + tenantCreateReqVO.getName() + "】的租户已存在"));
        }

        // sad path2: 创建租户失败，租户套餐不存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:tenant:create"})
        public void createTenant_fail_when_TenantPackageNotExist() throws Exception {
            TenantCreateReqVO tenantCreateReqVO = new TenantCreateReqVO();
            tenantCreateReqVO.setUsername("testuser123");
            tenantCreateReqVO.setPassword("test_password");
            tenantCreateReqVO.setName("test_tenant_name9874");
            tenantCreateReqVO.setDomain("https://www.baidu.com");
            tenantCreateReqVO.setContactName("huang");
            tenantCreateReqVO.setContactMobile("13777777777");
            tenantCreateReqVO.setPackageId("999999");
            tenantCreateReqVO.setStatus(0);
            tenantCreateReqVO.setAccountCount(10000);
            tenantCreateReqVO.setExpireTime(buildTime(2033, 2, 2));
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(tenantCreateReqVO);
            mvc.perform(post("/admin/tenant/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002016000))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("租户套餐不存在"));
        }

        // sad path3: 创建租户失败，用户名为空
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:tenant:create"})
        public void createTenant_fail_when_UsernameIsEmpty() throws Exception {
            SysTenantPackagePO tenantPackage = createTenantPackage();
            TenantCreateReqVO tenantCreateReqVO = new TenantCreateReqVO();
            tenantCreateReqVO.setUsername("");
            tenantCreateReqVO.setPassword("test_password");
            tenantCreateReqVO.setName("test_tenant_name9874");
            tenantCreateReqVO.setDomain("https://www.baidu.com");
            tenantCreateReqVO.setContactName("huang");
            tenantCreateReqVO.setContactMobile("13777777777");
            tenantCreateReqVO.setPackageId(tenantPackage.getId());
            tenantCreateReqVO.setStatus(0);
            tenantCreateReqVO.setAccountCount(10000);
            tenantCreateReqVO.setExpireTime(buildTime(2033, 2, 2));
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(tenantCreateReqVO);
            mvc.perform(post("/admin/tenant/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(10030))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("请求参数不正确:用户账号长度为 4-30 个字符"));
        }

        // sad path4: 更新租户失败，租户名称为空
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:tenant:create"})
        public void createTenant_fail_when_TenantNameIsEmpty() throws Exception {
            SysTenantPackagePO tenantPackage = createTenantPackage();
            TenantCreateReqVO tenantCreateReqVO = new TenantCreateReqVO();
            tenantCreateReqVO.setUsername("user1234");
            tenantCreateReqVO.setPassword("test_password");
            tenantCreateReqVO.setName("");
            tenantCreateReqVO.setDomain("https://www.baidu.com");
            tenantCreateReqVO.setContactName("huang");
            tenantCreateReqVO.setContactMobile("13777777777");
            tenantCreateReqVO.setPackageId(tenantPackage.getId());
            tenantCreateReqVO.setStatus(0);
            tenantCreateReqVO.setAccountCount(10000);
            tenantCreateReqVO.setExpireTime(buildTime(2033, 2, 2));
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(tenantCreateReqVO);
            mvc.perform(post("/admin/tenant/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(10030))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("请求参数不正确:租户名不能为空"));
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/27
     * @description 更新租户相关测试
     */
    @Nested
    class UpdateTenantTests {

        // happy path: 更新租户成功
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:tenant:update"})
        public void updateTenant_success() throws Exception {
            SysTenantPO sysTenantPO = createTenant();
            TenantUpdateReqVO tenantUpdateReqVO = new TenantUpdateReqVO();
            tenantUpdateReqVO.setId(sysTenantPO.getId());
            tenantUpdateReqVO.setName("test_tenant_name321");
            tenantUpdateReqVO.setDomain("https://www.baidu.com321");
            tenantUpdateReqVO.setContactName("huang321");
            tenantUpdateReqVO.setContactMobile("13777777321");
            tenantUpdateReqVO.setPackageId(sysTenantPO.getPackageId());
            tenantUpdateReqVO.setStatus(CommonStatusEnum.DISABLE.getStatus());
            tenantUpdateReqVO.setAccountCount(321);
            tenantUpdateReqVO.setExpireTime(buildTime(2039, 5, 5));
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(tenantUpdateReqVO);
            mvc.perform(put("/admin/tenant/update")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").isNotEmpty());
            // 验证数据库中是否存在该租户
            SysTenantPO dbTenantPO = tenantService.getTenant(tenantUpdateReqVO.getId());
            assertNotEquals(null, dbTenantPO);
            assertEquals(tenantUpdateReqVO.getName(), dbTenantPO.getName());
            assertEquals(tenantUpdateReqVO.getDomain(), dbTenantPO.getDomain());
            assertEquals(tenantUpdateReqVO.getContactName(), dbTenantPO.getContactName());
            assertEquals(tenantUpdateReqVO.getContactMobile(), dbTenantPO.getContactMobile());
            assertEquals(tenantUpdateReqVO.getPackageId(), dbTenantPO.getPackageId());
            assertEquals(tenantUpdateReqVO.getStatus(), dbTenantPO.getStatus());
            assertEquals(tenantUpdateReqVO.getAccountCount(), dbTenantPO.getAccountCount());
            assertEquals(tenantUpdateReqVO.getExpireTime(), dbTenantPO.getExpireTime());
            // TODO:: 验证数据库中是否存在该租户的管理员账号
        }

        // sad path1: 更新租户失败，租户不存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:tenant:update"})
        public void updateTenant_fail_when_TenantNotExist() throws Exception {
            TenantUpdateReqVO tenantUpdateReqVO = new TenantUpdateReqVO();
            tenantUpdateReqVO.setId("999999");
            tenantUpdateReqVO.setName("test_tenant_name321");
            tenantUpdateReqVO.setDomain("https://www.baidu.com321");
            tenantUpdateReqVO.setContactName("huang321");
            tenantUpdateReqVO.setContactMobile("13777777321");
            tenantUpdateReqVO.setPackageId("999999");
            tenantUpdateReqVO.setStatus(CommonStatusEnum.DISABLE.getStatus());
            tenantUpdateReqVO.setAccountCount(321);
            tenantUpdateReqVO.setExpireTime(buildTime(2039, 5, 5));
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(tenantUpdateReqVO);
            mvc.perform(put("/admin/tenant/update")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002015000))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("租户不存在"));
        }

        // sad path2: 更新租户失败，租户名称已存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:tenant:update"})
        public void updateTenant_fail_when_TenantNameExist() throws Exception {
            SysTenantPO sysTenantPO = createTenant();
            SysTenantPO tenant2 = createTenant2();
            TenantUpdateReqVO tenantUpdateReqVO = new TenantUpdateReqVO();
            tenantUpdateReqVO.setId(sysTenantPO.getId());
            tenantUpdateReqVO.setName(tenant2.getName());
            tenantUpdateReqVO.setDomain("https://www.baidu.com321");
            tenantUpdateReqVO.setContactName("huang321");
            tenantUpdateReqVO.setContactMobile("13777777321");
            tenantUpdateReqVO.setPackageId(sysTenantPO.getPackageId());
            tenantUpdateReqVO.setStatus(CommonStatusEnum.DISABLE.getStatus());
            tenantUpdateReqVO.setAccountCount(321);
            tenantUpdateReqVO.setExpireTime(buildTime(2039, 5, 5));
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(tenantUpdateReqVO);
            mvc.perform(put("/admin/tenant/update")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002015004))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("名字为【" + tenantUpdateReqVO.getName() + "】的租户已存在"));
        }

        // sad path3: 更新租户失败，租户套餐不存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:tenant:update"})
        public void updateTenant_fail_when_TenantPackageNotExist() throws Exception {
            SysTenantPO sysTenantPO = createTenant();
            TenantUpdateReqVO tenantUpdateReqVO = new TenantUpdateReqVO();
            tenantUpdateReqVO.setId(sysTenantPO.getId());
            tenantUpdateReqVO.setName("test_tenant_name321");
            tenantUpdateReqVO.setDomain("https://www.baidu.com321");
            tenantUpdateReqVO.setContactName("huang321");
            tenantUpdateReqVO.setContactMobile("13777777321");
            tenantUpdateReqVO.setPackageId("999999");
            tenantUpdateReqVO.setStatus(CommonStatusEnum.DISABLE.getStatus());
            tenantUpdateReqVO.setAccountCount(321);
            tenantUpdateReqVO.setExpireTime(buildTime(2039, 5, 5));
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(tenantUpdateReqVO);
            mvc.perform(put("/admin/tenant/update")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002016000))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("租户套餐不存在"));
        }

        // sad path4: 更新租户失败，租户名称为空
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:tenant:update"})
        public void updateTenant_fail_when_TenantNameIsNull() throws Exception {
            SysTenantPO sysTenantPO = createTenant();
            TenantUpdateReqVO tenantUpdateReqVO = new TenantUpdateReqVO();
            tenantUpdateReqVO.setId(sysTenantPO.getId());
            tenantUpdateReqVO.setName("");
            tenantUpdateReqVO.setDomain("https://www.baidu.com321");
            tenantUpdateReqVO.setContactName("huang321");
            tenantUpdateReqVO.setContactMobile("13777777321");
            tenantUpdateReqVO.setPackageId(sysTenantPO.getPackageId());
            tenantUpdateReqVO.setStatus(CommonStatusEnum.DISABLE.getStatus());
            tenantUpdateReqVO.setAccountCount(321);
            tenantUpdateReqVO.setExpireTime(buildTime(2039, 5, 5));
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(tenantUpdateReqVO);
            mvc.perform(put("/admin/tenant/update")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(10030))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("请求参数不正确:租户名不能为空"));
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/27
     * @description 删除租户相关测试
     */
    @Nested
    class DeleteTenantTests {
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:tenant:delete"})
        public void deleteTenantById_success() throws Exception {
            SysTenantPO tenant = createTenant();
            mvc.perform(delete("/admin/tenant/" + tenant.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").value(true));
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/27
     * @description 通过ID获取租户相关测试
     */
    @Nested
    class GetTenantByIDTests {
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:tenant:query"})
        public void getTenantById_success() throws Exception {
            SysTenantPO tenant = createTenant();
            mvc.perform(get("/admin/tenant/get/" + tenant.getId()))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data.name").value(tenant.getName()))
                    .andExpect(jsonPath("$.data.domain").value(tenant.getDomain()))
                    .andExpect(jsonPath("$.data.contactName").value(tenant.getContactName()))
                    .andExpect(jsonPath("$.data.contactMobile").value(tenant.getContactMobile()))
                    .andExpect(jsonPath("$.data.packageId").value(tenant.getPackageId()))
                    .andExpect(jsonPath("$.data.status").value(tenant.getStatus()))
                    .andExpect(jsonPath("$.data.accountCount").value(tenant.getAccountCount()));
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/27
     * @description 获取租户分页相关测试
     */
    @Nested
    class GetTenantPageTests {
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:tenant:query"})
        public void getTenantPage_success() throws Exception {
            SysTenantPO tenant = createTenant();
            mvc.perform(get("/admin/tenant/page"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data.total").value(1))
                    .andExpect(jsonPath("$.data.list").isArray())
                    .andExpect(jsonPath("$.data.list").isNotEmpty())
                    .andExpect(jsonPath("$.data.list[0].name").value(tenant.getName()));
        }
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
        tenantCreateReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        tenantCreateReqVO.setAccountCount(10000);
        tenantCreateReqVO.setExpireTime(buildTime(2033, 2, 2));
        String tenantId = tenantService.createTenant(tenantCreateReqVO);
        SysTenantPO tenant = tenantService.getTenant(tenantId);
        return tenant;
    }

    private SysTenantPO createTenant2() {
        SysTenantPackagePO tenantPackage = this.createTenantPackage();
        TenantCreateReqVO tenantCreateReqVO = new TenantCreateReqVO();
        tenantCreateReqVO.setUsername("testuser222");
        tenantCreateReqVO.setPassword("test_password2");
        tenantCreateReqVO.setName("test_tenant_name2");
        tenantCreateReqVO.setDomain("https://www.baidu.com2");
        tenantCreateReqVO.setContactName("huang2");
        tenantCreateReqVO.setContactMobile("13777777722");
        tenantCreateReqVO.setPackageId(tenantPackage.getId());
        tenantCreateReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        tenantCreateReqVO.setAccountCount(10000);
        tenantCreateReqVO.setExpireTime(buildTime(2033, 2, 2));
        String tenantId = tenantService.createTenant(tenantCreateReqVO);
        SysTenantPO tenant = tenantService.getTenant(tenantId);
        return tenant;
    }

    private SysTenantPO createDefaultTenant() {
        SysTenantPackagePO tenantPackage = this.createTenantPackage();
        TenantCreateReqVO tenantCreateReqVO = new TenantCreateReqVO();
        tenantCreateReqVO.setUsername("testuserdefault");
        tenantCreateReqVO.setPassword("testpassword123");
        tenantCreateReqVO.setName("test_tenant_name_default");
        tenantCreateReqVO.setDomain("https://www.baidu.com_default");
        tenantCreateReqVO.setContactName("huang_default");
        tenantCreateReqVO.setContactMobile("13777777865");
        tenantCreateReqVO.setPackageId(tenantPackage.getId());
        tenantCreateReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        tenantCreateReqVO.setAccountCount(10000);
        tenantCreateReqVO.setExpireTime(buildTime(2033, 2, 2));
        String tenantId = tenantService.createTenant(tenantCreateReqVO);
        return tenantService.getTenant(tenantId);
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

    private SysUserPO createUser() {
        SysDeptPO sysDeptPO = createDept();
        SysPostPO sysPostPO = createPost();
        UserCreateReqVO createReqVO = new UserCreateReqVO();
        createReqVO.setUsername("testuser3");
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
