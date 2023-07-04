/**
 * @author oliveoil
 * date 2023-06-13 13:23
 */
package com.rc.cloud.app.system.contorller.admin.tenant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.system.controller.admin.tenant.TenantPackageController;
import com.rc.cloud.app.system.service.tenant.TenantPackageService;
import com.rc.cloud.app.system.vo.tenant.packages.TenantPackageCreateReqVO;
import com.rc.cloud.app.system.vo.tenant.packages.TenantPackageUpdateReqVO;
import com.rc.cloud.common.tenant.core.context.TenantContextHolder;
import com.rc.cloud.common.test.annotation.RcTest;
import org.junit.jupiter.api.BeforeEach;
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

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 关联 {@link TenantPackageController} 类
 */
@RcTest
public class TenantPackageControllerTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Resource
    private TenantPackageService tenantPackageService;

    @Qualifier("springSecurityFilterChain")
    @BeforeEach
    public void setup() {
        TenantContextHolder.setTenantId(1L);
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:tenant-package:create"})
    public void createTenantPackage_success() throws Exception {
        TenantPackageCreateReqVO createReqVO = new TenantPackageCreateReqVO();
        createReqVO.setName("test_tenant_name");
        createReqVO.setStatus(0);
        createReqVO.setRemark("test_tenant_remark");
        Set<Long> menuIds = new HashSet<>();
        menuIds.add(1L);
        menuIds.add(2L);
        menuIds.add(5L);
        createReqVO.setMenuIds(menuIds);

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(createReqVO);
        mvc.perform(post("/sys/tenant-package/create")
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
    @WithMockUser(username = "admin", authorities = {"sys:tenant-package:update"})
    public void updateTenantPackage_success() throws Exception {
        TenantPackageUpdateReqVO updateReqVO = new TenantPackageUpdateReqVO();
        updateReqVO.setId(111L);
        updateReqVO.setName("test_tenant_name");
        updateReqVO.setStatus(0);
        updateReqVO.setRemark("test_tenant_remark");
        Set<Long> menuIds = new HashSet<>();
        menuIds.add(2162L);
        updateReqVO.setMenuIds(menuIds);

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(updateReqVO);
        mvc.perform(put("/sys/tenant-package/update")
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
        Set<Long> menuIds = new HashSet<>();
        menuIds.add(1L);
        menuIds.add(2L);
        menuIds.add(5L);
        createReqVO.setMenuIds(menuIds);
        Long tenantPackageId = tenantPackageService.createTenantPackage(createReqVO);
        mvc.perform(delete("/sys/tenant-package/" + tenantPackageId)
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
        mvc.perform(get("/sys/tenant-package/get/" + 111))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("普通套餐"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:tenant-package:query"})
    public void getTenantPackagePage_success() throws Exception {
        mvc.perform(get("/sys/tenant-package/page"))
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
        mvc.perform(get("/sys/tenant-package/get-simple-list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data[0].name").value("普通套餐"));
    }
}
