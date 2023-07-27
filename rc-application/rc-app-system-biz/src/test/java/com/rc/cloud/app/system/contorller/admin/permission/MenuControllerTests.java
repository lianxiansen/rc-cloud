package com.rc.cloud.app.system.contorller.admin.permission;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.system.controller.admin.v1.permission.MenuController;
import com.rc.cloud.app.system.enums.permission.MenuTypeEnum;
import com.rc.cloud.app.system.mapper.permission.MenuMapper;
import com.rc.cloud.app.system.mapper.tenant.TenantPackageMapper;
import com.rc.cloud.app.system.model.permission.SysMenuPO;
import com.rc.cloud.app.system.model.tenant.SysTenantPO;
import com.rc.cloud.app.system.model.tenant.SysTenantPackagePO;
import com.rc.cloud.app.system.service.tenant.TenantService;
import com.rc.cloud.app.system.vo.permission.menu.MenuCreateReqVO;
import com.rc.cloud.app.system.vo.permission.menu.MenuUpdateReqVO;
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
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author oliveoil
 * @date 2023-06-13 13:23
 * 关联 {@link MenuController} 类
 */
@RcTest
public class MenuControllerTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private TenantService tenantService;

    @Resource
    private TenantPackageMapper tenantPackageMapper;

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
     * @description 创建菜单相关测试
     */
    @Nested
    class CreateMenuTests {
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:menu:create"})
        public void createDirMenu_success() throws Exception {
            // 添加目录
            MenuCreateReqVO menuCreateReqVO = new MenuCreateReqVO();
            menuCreateReqVO.setName("测试目录");
            menuCreateReqVO.setType(MenuTypeEnum.DIR.getType());
            menuCreateReqVO.setSort(1);
            menuCreateReqVO.setParentId("0");
            menuCreateReqVO.setPath("/test");
            menuCreateReqVO.setIcon("test");
            menuCreateReqVO.setComponent("test/menu");
            menuCreateReqVO.setComponentName("TestMenu");
            menuCreateReqVO.setStatus(0);
            menuCreateReqVO.setVisible(true);
            menuCreateReqVO.setKeepAlive(true);
            menuCreateReqVO.setAlwaysShow(true);
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(menuCreateReqVO);
            mvc.perform(post("/admin/menu/create")
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
     * @description 更新菜单相关测试
     */
    @Nested
    class UpdateMenuTests {
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:menu:update"})
        public void updateDictData_success() throws Exception {
            SysMenuPO menu = createMenu();
            MenuUpdateReqVO menuUpdateReqVO = new MenuUpdateReqVO();
            menuUpdateReqVO.setId(menu.getId());
            menuUpdateReqVO.setName("系统管理2");
            menuUpdateReqVO.setParentId("0");
            menuUpdateReqVO.setStatus(0);
            menuUpdateReqVO.setType(MenuTypeEnum.DIR.getType());
            menuUpdateReqVO.setSort(100);
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(menuUpdateReqVO);
            mvc.perform(put("/admin/menu/update")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
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
     * @description 删除菜单相关测试
     */
    @Nested
    class DeleteMenuTests {
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:menu:delete"})
        public void deleteMenuById_success() throws Exception {
            SysMenuPO menu = createMenu();
            mvc.perform(delete("/admin/menu?id=" + menu.getId())
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
     * @description 获取菜单列表相关测试
     */
    @Nested
    class GetMenuListTests {
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:menu:query"})
        public void getMenuList_success() throws Exception {
            SysMenuPO menu = createMenu();
            mvc.perform(get("/admin/menu/list"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").isArray())
                    .andExpect(jsonPath("$.data").isNotEmpty());
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/27
     * @description 获取菜单简单列表相关测试
     */
    @Nested
    class GetMenuSimpleListTests {
        @Test
        @WithMockUser()
        public void getMenuListAllSimple_success() throws Exception {
            SysMenuPO menu = createMenu();
            mvc.perform(get("/admin/menu/list-all-simple"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").isArray())
                    .andExpect(jsonPath("$.data").isNotEmpty());
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/27
     * @description 通过id获取菜单相关测试
     */
    @Nested
    class GetMenuByIDTests {
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:menu:query"})
        public void getMenuById_success() throws Exception {
            SysMenuPO menu = createMenu();
            mvc.perform(get("/admin/menu/get/" + menu.getId()))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data.name").value(menu.getName()));
        }
    }


    private SysMenuPO createMenu() {
        SysMenuPO sysMenuPO = new SysMenuPO();
        sysMenuPO.setName("测试目录");
        sysMenuPO.setType(MenuTypeEnum.DIR.getType());
        sysMenuPO.setSort(1);
        sysMenuPO.setParentId("0");
        sysMenuPO.setPath("/test");
        sysMenuPO.setIcon("test");
        sysMenuPO.setComponent("test/menu");
        sysMenuPO.setComponentName("TestMenu");
        sysMenuPO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        sysMenuPO.setVisible(true);
        sysMenuPO.setKeepAlive(true);
        sysMenuPO.setAlwaysShow(true);
        menuMapper.insert(sysMenuPO);
        return sysMenuPO;
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
