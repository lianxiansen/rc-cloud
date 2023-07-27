package com.rc.cloud.app.system.contorller.admin.tenant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.system.controller.admin.v1.tenant.TenantController;
import com.rc.cloud.app.system.mapper.tenant.TenantPackageMapper;
import com.rc.cloud.app.system.model.tenant.SysTenantPO;
import com.rc.cloud.app.system.model.tenant.SysTenantPackagePO;
import com.rc.cloud.app.system.service.tenant.TenantService;
import com.rc.cloud.app.system.service.user.AdminUserService;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantCreateReqVO;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantUpdateReqVO;
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

    @Qualifier("springSecurityFilterChain")
    @BeforeEach
    public void setup() {
        TenantContextHolder.setTenantId("1");
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
    }

    /**
     * @author rc@hqf
     * @date 2023/07/27
     * @description 更新租户相关测试
     */
    @Nested
    class UpdateTenantTests {
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
            mvc.perform(get("/admin/tenant/page"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data.total").value(4))
                    .andExpect(jsonPath("$.data.list").isArray())
                    .andExpect(jsonPath("$.data.list").isNotEmpty())
                    .andExpect(jsonPath("$.data.list[0].name").value("zuhu1"));
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
}
