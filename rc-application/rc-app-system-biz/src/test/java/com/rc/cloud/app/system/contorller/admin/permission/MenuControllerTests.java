package com.rc.cloud.app.system.contorller.admin.permission;

import com.alibaba.fastjson.JSONObject;
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

        // happy path: 创建菜单成功
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
            menuCreateReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
            menuCreateReqVO.setVisible(true);
            menuCreateReqVO.setKeepAlive(true);
            menuCreateReqVO.setAlwaysShow(true);
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(menuCreateReqVO);
            String postRes = mvc.perform(post("/admin/menu/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").isNotEmpty())
                    .andReturn().getResponse().getContentAsString();
            String menuId = JSONObject.parseObject(postRes).get("data").toString();
            SysMenuPO sysMenuPO = menuMapper.selectById(menuId);
            assertNotEquals(null, sysMenuPO);
            assertEquals(menuCreateReqVO.getName(), sysMenuPO.getName());
            assertEquals(menuCreateReqVO.getType(), sysMenuPO.getType());
            assertEquals(menuCreateReqVO.getSort(), sysMenuPO.getSort());
            assertEquals(menuCreateReqVO.getParentId(), sysMenuPO.getParentId());
            assertEquals(menuCreateReqVO.getPath(), sysMenuPO.getPath());
            assertEquals(menuCreateReqVO.getIcon(), sysMenuPO.getIcon());
            assertEquals(menuCreateReqVO.getComponent(), sysMenuPO.getComponent());
            assertEquals(menuCreateReqVO.getComponentName(), sysMenuPO.getComponentName());
            assertEquals(menuCreateReqVO.getStatus(), sysMenuPO.getStatus());
            assertEquals(menuCreateReqVO.getVisible(), sysMenuPO.getVisible());
            assertEquals(menuCreateReqVO.getKeepAlive(), sysMenuPO.getKeepAlive());
            assertEquals(menuCreateReqVO.getAlwaysShow(), sysMenuPO.getAlwaysShow());
        }

        // sad path1: 创建菜单失败，父级菜单不存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:menu:create"})
        public void createDirMenu_fail_parentMenuNotExist() throws Exception {
            // 添加目录
            MenuCreateReqVO menuCreateReqVO = new MenuCreateReqVO();
            menuCreateReqVO.setName("测试目录");
            menuCreateReqVO.setType(MenuTypeEnum.DIR.getType());
            menuCreateReqVO.setSort(1);
            menuCreateReqVO.setParentId("99999");
            menuCreateReqVO.setPath("/test");
            menuCreateReqVO.setIcon("test");
            menuCreateReqVO.setComponent("test/menu");
            menuCreateReqVO.setComponentName("TestMenu");
            menuCreateReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
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
                    .andExpect(jsonPath("$.code").value(1002001001))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("父菜单不存在"));
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/27
     * @description 更新菜单相关测试
     */
    @Nested
    class UpdateMenuTests {

        // happy path: 更新菜单成功
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
            SysMenuPO dbMenuPO = menuMapper.selectById(menu.getId());
            assertEquals(menuUpdateReqVO.getName(), dbMenuPO.getName());
            assertEquals(menuUpdateReqVO.getParentId(), dbMenuPO.getParentId());
            assertEquals(menuUpdateReqVO.getStatus(), dbMenuPO.getStatus());
            assertEquals(menuUpdateReqVO.getType(), dbMenuPO.getType());
            assertEquals(menuUpdateReqVO.getSort(), dbMenuPO.getSort());
        }

        // sad path1: 更新菜单失败，菜单不存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:menu:update"})
        public void updateDictData_fail_menuNotExist() throws Exception {
            MenuUpdateReqVO menuUpdateReqVO = new MenuUpdateReqVO();
            menuUpdateReqVO.setId("99999");
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
                    .andExpect(jsonPath("$.code").value(1002001003))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("菜单不存在"));
        }

        // sad path2: 更新菜单失败，父级菜单不存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:menu:update"})
        public void updateDictData_fail_parentMenuNotExist() throws Exception {
            SysMenuPO menu = createMenu();
            MenuUpdateReqVO menuUpdateReqVO = new MenuUpdateReqVO();
            menuUpdateReqVO.setId(menu.getId());
            menuUpdateReqVO.setName("系统管理2");
            menuUpdateReqVO.setParentId("99999");
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
                    .andExpect(jsonPath("$.code").value(1002001001))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("父菜单不存在"));
        }

        // sad path3: 更新菜单失败，菜单名称已存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:menu:update"})
        public void updateDictData_fail_menuNameExist() throws Exception {
            SysMenuPO menu = createMenu();
            SysMenuPO menu2 = createMenu2();
            MenuUpdateReqVO menuUpdateReqVO = new MenuUpdateReqVO();
            menuUpdateReqVO.setId(menu.getId());
            menuUpdateReqVO.setName(menu2.getName());
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
                    .andExpect(jsonPath("$.code").value(1002001000))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("已经存在该名字的菜单"));
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
            SysMenuPO dbMenuPO = menuMapper.selectById(menu.getId());
            assertEquals(null, dbMenuPO);
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
                    .andExpect(jsonPath("$.data").isNotEmpty())
                    .andExpect(jsonPath("$.data[0].name").value(menu.getName()))
                    .andExpect(jsonPath("$.data[0].type").value(menu.getType()))
                    .andExpect(jsonPath("$.data[0].sort").value(menu.getSort()))
                    .andExpect(jsonPath("$.data[0].parentId").value(menu.getParentId()))
                    .andExpect(jsonPath("$.data[0].path").value(menu.getPath()))
                    .andExpect(jsonPath("$.data[0].icon").value(menu.getIcon()))
                    .andExpect(jsonPath("$.data[0].component").value(menu.getComponent()))
                    .andExpect(jsonPath("$.data[0].componentName").value(menu.getComponentName()))
                    .andExpect(jsonPath("$.data[0].status").value(menu.getStatus()))
                    .andExpect(jsonPath("$.data[0].visible").value(menu.getVisible()))
                    .andExpect(jsonPath("$.data[0].keepAlive").value(menu.getKeepAlive()))
                    .andExpect(jsonPath("$.data[0].alwaysShow").value(menu.getAlwaysShow()));
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
            SysTenantPO tenant = createTenantWithMenu();
            TenantContextHolder.setTenantId(tenant.getId());
            mvc.perform(get("/admin/menu/list-all-simple"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").isArray())
                    .andExpect(jsonPath("$.data").isNotEmpty())
                    .andExpect(jsonPath("$.data[0].name").value("测试目录99"))
                    .andExpect(jsonPath("$.data[0].type").value(1))
                    .andExpect(jsonPath("$.data[0].parentId").value("0"));
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
                    .andExpect(jsonPath("$.data.name").value(menu.getName()))
                    .andExpect(jsonPath("$.data.type").value(menu.getType()))
                    .andExpect(jsonPath("$.data.sort").value(menu.getSort()))
                    .andExpect(jsonPath("$.data.parentId").value(menu.getParentId()))
                    .andExpect(jsonPath("$.data.path").value(menu.getPath()))
                    .andExpect(jsonPath("$.data.icon").value(menu.getIcon()))
                    .andExpect(jsonPath("$.data.component").value(menu.getComponent()))
                    .andExpect(jsonPath("$.data.componentName").value(menu.getComponentName()))
                    .andExpect(jsonPath("$.data.status").value(menu.getStatus()))
                    .andExpect(jsonPath("$.data.visible").value(menu.getVisible()))
                    .andExpect(jsonPath("$.data.keepAlive").value(menu.getKeepAlive()))
                    .andExpect(jsonPath("$.data.alwaysShow").value(menu.getAlwaysShow()));
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

    private SysMenuPO createMenu2() {
        SysMenuPO sysMenuPO = new SysMenuPO();
        sysMenuPO.setName("测试目录22");
        sysMenuPO.setType(MenuTypeEnum.DIR.getType());
        sysMenuPO.setSort(1);
        sysMenuPO.setParentId("0");
        sysMenuPO.setPath("/test2");
        sysMenuPO.setIcon("test2");
        sysMenuPO.setComponent("test/menu2");
        sysMenuPO.setComponentName("TestMenu2");
        sysMenuPO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        sysMenuPO.setVisible(true);
        sysMenuPO.setKeepAlive(true);
        sysMenuPO.setAlwaysShow(true);
        menuMapper.insert(sysMenuPO);
        return sysMenuPO;
    }

    private SysMenuPO createMenu99() {
        SysMenuPO sysMenuPO = new SysMenuPO();
        sysMenuPO.setName("测试目录99");
        sysMenuPO.setType(MenuTypeEnum.DIR.getType());
        sysMenuPO.setSort(1);
        sysMenuPO.setParentId("0");
        sysMenuPO.setPath("/test99");
        sysMenuPO.setIcon("test99");
        sysMenuPO.setComponent("test/menu99");
        sysMenuPO.setComponentName("TestMenu99");
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
        tenantPackage.setMenuIds(menuIds);
        tenantPackageMapper.insert(tenantPackage);
        return tenantPackage;
    }

    private SysTenantPO createTenantWithMenu () {
        SysTenantPackagePO tenantPackage = this.createTenantPackageWithMenu();
        TenantCreateReqVO tenantCreateReqVO = new TenantCreateReqVO();
        tenantCreateReqVO.setUsername("testuserwithmenu");
        tenantCreateReqVO.setPassword("test_with_nemu");
        tenantCreateReqVO.setName("test_with_menu");
        tenantCreateReqVO.setDomain("https://www.baidu.com");
        tenantCreateReqVO.setContactName("huang_with_menu");
        tenantCreateReqVO.setContactMobile("13777777548");
        tenantCreateReqVO.setPackageId(tenantPackage.getId());
        tenantCreateReqVO.setStatus(0);
        tenantCreateReqVO.setAccountCount(10000);
        tenantCreateReqVO.setExpireTime(buildTime(2033, 2, 2));
        String tenantId = tenantService.createTenant(tenantCreateReqVO);
        SysTenantPO tenant = tenantService.getTenant(tenantId);
        return tenant;
    }

    private SysTenantPackagePO createTenantPackageWithMenu () {
        SysTenantPackagePO tenantPackage = new SysTenantPackagePO();
        tenantPackage.setName("test_withmenu");
        tenantPackage.setStatus(CommonStatusEnum.ENABLE.getStatus());
        tenantPackage.setRemark("test_tenant_remark1");
        Set<String> menuIds = new HashSet<>();
        menuIds.add(createMenu99().getId());
        tenantPackage.setMenuIds(menuIds);
        tenantPackageMapper.insert(tenantPackage);
        return tenantPackage;
    }
}
