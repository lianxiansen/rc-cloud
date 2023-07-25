package com.rc.cloud.app.system.contorller.admin.tenant;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.system.controller.admin.v1.tenant.TenantPackageController;
import com.rc.cloud.app.system.mapper.tenant.TenantPackageMapper;
import com.rc.cloud.app.system.model.tenant.SysTenantPackagePO;
import com.rc.cloud.app.system.service.tenant.TenantPackageService;
import com.rc.cloud.app.system.vo.tenant.packages.TenantPackageCreateReqVO;
import com.rc.cloud.app.system.vo.tenant.packages.TenantPackageUpdateReqVO;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    private TenantPackageService tenantPackageService;

    @Resource
    private TenantPackageMapper tenantPackageMapper;

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
            SysTenantPackagePO tenantPackage = createTenantPackage();
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

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:tenant-package:update"})
    public void updateTenantPackage_success() throws Exception {
        TenantPackageUpdateReqVO updateReqVO = new TenantPackageUpdateReqVO();
        updateReqVO.setId("111");
        updateReqVO.setName("test_tenant_name");
        updateReqVO.setStatus(0);
        updateReqVO.setRemark("test_tenant_remark");
        Set<String> menuIds = new HashSet<>();
        menuIds.add("2162");
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
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:tenant-package:delete"})
    public void deleteTenantPackageById_success() throws Exception {
        TenantPackageCreateReqVO createReqVO = new TenantPackageCreateReqVO();
        createReqVO.setName("test_tenant_name");
        createReqVO.setStatus(0);
        createReqVO.setRemark("test_tenant_remark");
        Set<String> menuIds = new HashSet<>();
        menuIds.add("1");
        menuIds.add("2");
        menuIds.add("5");
        createReqVO.setMenuIds(menuIds);
        String tenantPackageId = tenantPackageService.createTenantPackage(createReqVO);
        mvc.perform(delete("/admin/tenant-package/" + tenantPackageId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(true));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:tenant-package:query"})
    public void getTenantPackageById_success() throws Exception {
        mvc.perform(get("/admin/tenant-package/get/" + 111))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("普通套餐"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:tenant-package:query"})
    public void getTenantPackagePage_success() throws Exception {
        mvc.perform(get("/admin/tenant-package/page"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.total").value(1))
                .andExpect(jsonPath("$.data.list").isArray())
                .andExpect(jsonPath("$.data.list").isNotEmpty())
                .andExpect(jsonPath("$.data.list[0].name").value("普通套餐"));
    }

    @Test
    @WithMockUser("admin")
    public void getTenantPackageSimpleList_success() throws Exception {
        mvc.perform(get("/admin/tenant-package/get-simple-list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data[0].name").value("普通套餐"));
    }

    private SysTenantPackagePO createTenantPackage() {
        SysTenantPackagePO tenantPackage = new SysTenantPackagePO();
        tenantPackage.setName("test_tenant_name");
        tenantPackage.setStatus(CommonStatusEnum.ENABLE.getStatus());
        tenantPackage.setRemark("test_tenant_remark");
        Set<String> menuIds = new HashSet<>();
        menuIds.add("1");
        menuIds.add("2");
        menuIds.add("5");
        tenantPackage.setMenuIds(menuIds);
        tenantPackageMapper.insert(tenantPackage);
        return tenantPackage;
    }
}
