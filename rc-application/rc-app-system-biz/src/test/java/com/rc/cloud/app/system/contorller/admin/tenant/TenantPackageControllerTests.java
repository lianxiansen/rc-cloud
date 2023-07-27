package com.rc.cloud.app.system.contorller.admin.tenant;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.system.controller.admin.v1.tenant.TenantPackageController;
import com.rc.cloud.app.system.mapper.tenant.TenantPackageMapper;
import com.rc.cloud.app.system.model.tenant.SysTenantPO;
import com.rc.cloud.app.system.model.tenant.SysTenantPackagePO;
import com.rc.cloud.app.system.service.tenant.TenantPackageService;
import com.rc.cloud.app.system.service.tenant.TenantService;
import com.rc.cloud.app.system.vo.tenant.packages.TenantPackageCreateReqVO;
import com.rc.cloud.app.system.vo.tenant.packages.TenantPackageUpdateReqVO;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantCreateReqVO;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author oliveoil
 * @date 2023-06-13 13:23
 * 关联 {@link TenantPackageController} 类
 */
@RcTest
public class TenantPackageControllerTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Resource
    private TenantPackageMapper tenantPackageMapper;

    @Resource
    private TenantService tenantService;

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
     * @date 2023/07/25
     * @description 创建租户套餐相关测试
     */
    @Nested
    class CreateTenantPackageTests {

        // happy path: 创建租户套餐成功
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:tenant-package:create"})
        public void createTenantPackage_success() throws Exception {
            TenantPackageCreateReqVO createReqVO = new TenantPackageCreateReqVO();
            createReqVO.setName("test_tenant_name");
            createReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
            createReqVO.setRemark("test_tenant_remark");
            Set<String> menuIds = new HashSet<>();
            menuIds.add("1");
            menuIds.add("2");
            menuIds.add("5");
            createReqVO.setMenuIds(menuIds);

            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(createReqVO);
            String postRes = mvc.perform(post("/admin/tenant-package/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").isNotEmpty())
                    .andReturn().getResponse().getContentAsString();   //将相应的数据转换为字符串;
            String tenantPackageId = JSONObject.parseObject(postRes).get("data").toString();
            SysTenantPackagePO sysTenantPackagePO = tenantPackageMapper.selectById(tenantPackageId);
            assertNotNull(sysTenantPackagePO);
            assertEquals(createReqVO.getName(), sysTenantPackagePO.getName());
            assertEquals(createReqVO.getStatus(), sysTenantPackagePO.getStatus());
            assertEquals(createReqVO.getRemark(), sysTenantPackagePO.getRemark());
            assertEquals(createReqVO.getMenuIds(), sysTenantPackagePO.getMenuIds());
        }

        // sad path1: 创建租户套餐失败，套餐名为空
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:tenant-package:create"})
        public void createTenantPackage_fail_when_nameIsNull() throws Exception {
            TenantPackageCreateReqVO createReqVO = new TenantPackageCreateReqVO();
            createReqVO.setName("");
            createReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
            createReqVO.setRemark("test_tenant_remark");
            Set<String> menuIds = new HashSet<>();
            menuIds.add("1");
            createReqVO.setMenuIds(menuIds);

            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(createReqVO);
            mvc.perform(post("/admin/tenant-package/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(10030))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("请求参数不正确:套餐名不能为空"));
        }

        // sad path2: 创建租户套餐失败，套餐名已存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:tenant-package:create"})
        public void createTenantPackage_fail_when_nameIsExist() throws Exception {
            SysTenantPackagePO tenantPackage = createTenantPackage1();
            TenantPackageCreateReqVO createReqVO = new TenantPackageCreateReqVO();
            createReqVO.setName(tenantPackage.getName());
            createReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
            createReqVO.setRemark("test_tenant_remark");
            Set<String> menuIds = new HashSet<>();
            menuIds.add("1");
            menuIds.add("2");
            menuIds.add("5");
            createReqVO.setMenuIds(menuIds);

            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(createReqVO);
            mvc.perform(post("/admin/tenant-package/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002016003))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("名字为【" + createReqVO.getName() + "】的租户套餐已存在"));
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/25
     * @description 更新租户套餐相关测试
     */
    @Nested
    class UpdateTenantPackageTests {

        // happy path: 更新租户套餐成功
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:tenant-package:update"})
        public void updateTenantPackage_success() throws Exception {
            SysTenantPackagePO tenantPackagePO = createTenantPackage1();
            TenantPackageUpdateReqVO updateReqVO = new TenantPackageUpdateReqVO();
            updateReqVO.setId(tenantPackagePO.getId());
            updateReqVO.setName("test_tenant_name11");
            updateReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
            updateReqVO.setRemark("test_tenant_remark11");
            Set<String> menuIds = new HashSet<>();
            menuIds.add("25");
            updateReqVO.setMenuIds(menuIds);

            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(updateReqVO);
            mvc.perform(put("/admin/tenant-package/update")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").isNotEmpty());
            SysTenantPackagePO dbTenantPackagePO = tenantPackageMapper.selectById(tenantPackagePO.getId());
            assertNotNull(dbTenantPackagePO);
            assertEquals(updateReqVO.getName(), dbTenantPackagePO.getName());
            assertEquals(updateReqVO.getStatus(), dbTenantPackagePO.getStatus());
            assertEquals(updateReqVO.getRemark(), dbTenantPackagePO.getRemark());
            assertEquals(updateReqVO.getMenuIds(), dbTenantPackagePO.getMenuIds());
        }

        // sad path1: 更新租户套餐失败，套餐名为空
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:tenant-package:update"})
        public void updateTenantPackage_fail_when_nameIsNull() throws Exception {
            SysTenantPackagePO tenantPackagePO = createTenantPackage1();
            TenantPackageUpdateReqVO updateReqVO = new TenantPackageUpdateReqVO();
            updateReqVO.setId(tenantPackagePO.getId());
            updateReqVO.setName("");
            updateReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
            updateReqVO.setRemark("test_tenant_remark11");
            Set<String> menuIds = new HashSet<>();
            menuIds.add("25");
            updateReqVO.setMenuIds(menuIds);

            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(updateReqVO);
            mvc.perform(put("/admin/tenant-package/update")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(10030))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("请求参数不正确:套餐名不能为空"));
        }

        // sad path2: 更新租户套餐失败，套餐名已存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:tenant-package:update"})
        public void updateTenantPackage_fail_when_nameIsExist() throws Exception {
            SysTenantPackagePO tenantPackage1 = createTenantPackage1();
            SysTenantPackagePO tenantPackage2 = createTenantPackage2();
            TenantPackageUpdateReqVO updateReqVO = new TenantPackageUpdateReqVO();
            updateReqVO.setId(tenantPackage1.getId());
            updateReqVO.setName(tenantPackage2.getName());
            updateReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
            updateReqVO.setRemark("test_tenant_remark11");
            Set<String> menuIds = new HashSet<>();
            menuIds.add("25");
            updateReqVO.setMenuIds(menuIds);

            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(updateReqVO);
            mvc.perform(put("/admin/tenant-package/update")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002016003))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("名字为【" + updateReqVO.getName() + "】的租户套餐已存在"));
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/25
     * @description 删除租户套餐相关测试
     */
    @Nested
    class DeleteTenantPackageTests {
        // happy path: 删除租户套餐成功
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:tenant-package:delete"})
        public void deleteTenantPackageById_success() throws Exception {
            SysTenantPackagePO tenantPackagePO = createTenantPackage1();
            mvc.perform(delete("/admin/tenant-package/" + tenantPackagePO.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").value(true));
            SysTenantPackagePO dbTenantPackagePO = tenantPackageMapper.selectById(tenantPackagePO.getId());
            assertNull(dbTenantPackagePO);
        }
        // sad path1: 删除租户套餐失败，租户套餐不存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:tenant-package:delete"})
        public void deleteTenantPackageById_fail_when_tenantPackageIsNotExist() throws Exception {
            mvc.perform(delete("/admin/tenant-package/" + "99999")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002016000))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("租户套餐不存在"));
        }

        // sad path2: 当租户套餐已被使用时，删除租户套餐失败
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:tenant-package:delete"})
        public void deleteTenantPackageById_fail_when_tenantPackageIsUsed() throws Exception {
            SysTenantPackagePO tenantPackagePO = createTenantPackage1();
            createTenant(tenantPackagePO.getId());
            mvc.perform(delete("/admin/tenant-package/" + tenantPackagePO.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002016001))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("租户正在使用该套餐，请给租户重新设置套餐后再尝试删除"));
        }
    }


    /**
     * @author rc@hqf
     * @date 2023/07/25
     * @description 根据ID获取租户套餐相关测试
     */
    @Nested
    class GetTenantPackageByIDTests {
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:tenant-package:query"})
        public void getTenantPackageById_success() throws Exception {
            SysTenantPackagePO tenantPackagePO = createTenantPackage1();
            mvc.perform(get("/admin/tenant-package/get/" + tenantPackagePO.getId()))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data.id").value(tenantPackagePO.getId()))
                    .andExpect(jsonPath("$.data.name").value(tenantPackagePO.getName()))
                    .andExpect(jsonPath("$.data.status").value(tenantPackagePO.getStatus()))
                    .andExpect(jsonPath("$.data.remark").value(tenantPackagePO.getRemark()))
                    .andExpect(jsonPath("$.data.menuIds").isArray())
                    .andExpect(jsonPath("$.data.menuIds").isNotEmpty())
                    .andExpect(jsonPath("$.data.menuIds[0]").value(tenantPackagePO.getMenuIds().toArray()[0]))
                    .andExpect(jsonPath("$.data.menuIds[1]").value(tenantPackagePO.getMenuIds().toArray()[1]))
                    .andExpect(jsonPath("$.data.menuIds[2]").value(tenantPackagePO.getMenuIds().toArray()[2]));
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/25
     * @description 获取租户套餐分页相关测试
     */
    @Nested
    class GetTenantPackagePageTests {
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:tenant-package:query"})
        public void getTenantPackagePage_success() throws Exception {
            SysTenantPackagePO tenantPackagePO = createTenantPackage1();
            mvc.perform(get("/admin/tenant-package/page"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data.total").value(1))
                    .andExpect(jsonPath("$.data.list").isArray())
                    .andExpect(jsonPath("$.data.list").isNotEmpty())
                    .andExpect(jsonPath("$.data.list[0].id").value(tenantPackagePO.getId()))
                    .andExpect(jsonPath("$.data.list[0].name").value(tenantPackagePO.getName()))
                    .andExpect(jsonPath("$.data.list[0].status").value(tenantPackagePO.getStatus()))
                    .andExpect(jsonPath("$.data.list[0].remark").value(tenantPackagePO.getRemark()))
                    .andExpect(jsonPath("$.data.list[0].menuIds").isArray())
                    .andExpect(jsonPath("$.data.list[0].menuIds").isNotEmpty())
                    .andExpect(jsonPath("$.data.list[0].menuIds[0]").value(tenantPackagePO.getMenuIds().toArray()[0]))
                    .andExpect(jsonPath("$.data.list[0].menuIds[1]").value(tenantPackagePO.getMenuIds().toArray()[1]))
                    .andExpect(jsonPath("$.data.list[0].menuIds[2]").value(tenantPackagePO.getMenuIds().toArray()[2]));
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/25
     * @description 获取租户套餐简单列表相关测试
     */
    @Nested
    class GetTenantPackageSimpleListTests {
        @Test
        @WithMockUser("admin")
        public void getTenantPackageSimpleList_success() throws Exception {
            SysTenantPackagePO tenantPackagePO = createTenantPackage1();
            mvc.perform(get("/admin/tenant-package/get-simple-list"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").isArray())
                    .andExpect(jsonPath("$.data").isNotEmpty())
                    .andExpect(jsonPath("$.data[0].id").value(tenantPackagePO.getId()))
                    .andExpect(jsonPath("$.data[0].name").value(tenantPackagePO.getName()));
        }
    }

    private SysTenantPackagePO createTenantPackage1() {
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

    private SysTenantPackagePO createTenantPackage2() {
        SysTenantPackagePO tenantPackage = new SysTenantPackagePO();
        tenantPackage.setName("test_tenant_name2");
        tenantPackage.setStatus(CommonStatusEnum.ENABLE.getStatus());
        tenantPackage.setRemark("test_tenant_remark2");
        Set<String> menuIds = new HashSet<>();
        menuIds.add("7");
        tenantPackage.setMenuIds(menuIds);
        tenantPackageMapper.insert(tenantPackage);
        return tenantPackage;
    }

    private SysTenantPO createTenant(String tenantPackageId) {
        TenantCreateReqVO tenantCreateReqVO = new TenantCreateReqVO();
        tenantCreateReqVO.setUsername("testuser123");
        tenantCreateReqVO.setPassword("test_password");
        tenantCreateReqVO.setName("test_tenant_name");
        tenantCreateReqVO.setDomain("https://www.baidu.com");
        tenantCreateReqVO.setContactName("huang");
        tenantCreateReqVO.setContactMobile("13777777777");
        tenantCreateReqVO.setPackageId(tenantPackageId);
        tenantCreateReqVO.setStatus(0);
        tenantCreateReqVO.setAccountCount(10000);
        tenantCreateReqVO.setExpireTime(buildTime(2033, 2, 2));
        String tenantId = tenantService.createTenant(tenantCreateReqVO);
        SysTenantPO tenant = tenantService.getTenant(tenantId);
        return tenant;
    }
}
