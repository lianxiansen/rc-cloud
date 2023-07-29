package com.rc.cloud.app.system.contorller.admin.permission;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.system.controller.admin.v1.permission.PermissionController;
import com.rc.cloud.app.system.enums.permission.MenuTypeEnum;
import com.rc.cloud.app.system.enums.permission.RoleTypeEnum;
import com.rc.cloud.app.system.mapper.dept.DeptMapper;
import com.rc.cloud.app.system.mapper.dept.PostMapper;
import com.rc.cloud.app.system.mapper.permission.MenuMapper;
import com.rc.cloud.app.system.mapper.permission.RoleMapper;
import com.rc.cloud.app.system.mapper.tenant.TenantPackageMapper;
import com.rc.cloud.app.system.model.dept.SysDeptPO;
import com.rc.cloud.app.system.model.dept.SysPostPO;
import com.rc.cloud.app.system.model.permission.SysMenuPO;
import com.rc.cloud.app.system.model.permission.SysRolePO;
import com.rc.cloud.app.system.model.tenant.SysTenantPO;
import com.rc.cloud.app.system.model.tenant.SysTenantPackagePO;
import com.rc.cloud.app.system.model.user.SysUserPO;
import com.rc.cloud.app.system.service.permission.PermissionService;
import com.rc.cloud.app.system.service.permission.RoleService;
import com.rc.cloud.app.system.service.tenant.TenantService;
import com.rc.cloud.app.system.service.user.AdminUserService;
import com.rc.cloud.app.system.vo.permission.permission.PermissionAssignRoleDataScopeReqVO;
import com.rc.cloud.app.system.vo.permission.permission.PermissionAssignRoleMenuReqVO;
import com.rc.cloud.app.system.vo.permission.permission.PermissionAssignUserRoleReqVO;
import com.rc.cloud.app.system.vo.permission.role.RoleCreateReqVO;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantCreateReqVO;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.rc.cloud.common.core.util.date.LocalDateTimeUtils.buildTime;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author oliveoil
 * @date 2023-06-13 13:23
 * 关联 {@link PermissionController} 类
 */
@RcTest
public class PermissionControllerTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private AdminUserService userService;

    @Resource
    private PostMapper postMapper;

    @Resource
    private DeptMapper deptMapper;

    @Resource
    private TenantService tenantService;

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private RoleService roleService;

    @Resource
    private TenantPackageMapper tenantPackageMapper;

    @Resource
    private PermissionService permissionService;

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
     * @date 2023/07/28
     * @description 角色资源列表相关测试
     * 返回的是菜单id列表
     */
    @Nested
    class ListRoleResourcesTests {
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:permission:assign-role-menu"})
        public void listRoleResources_success() throws Exception {
            Map<String, String> map = createRoleWithMenu();
            String roleId = map.get("roleId");
            String menuId = map.get("menuId");
            String postRes = mvc.perform(get("/admin/permission/list-role-resources/" + roleId))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").isArray())
                    .andExpect(jsonPath("$.data").isNotEmpty())
                    .andReturn().getResponse().getContentAsString();
            String dbMenuIdsStr = JSONObject.parseObject(postRes).get("data").toString();
            Set<String> dbMenuIds = JSONObject.parseObject(dbMenuIdsStr, Set.class);
            assert dbMenuIds.contains(menuId);
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/28
     * @description 给角色分配菜单相关测试
     */
    @Nested
    class AssignRoleMenuTests {
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:permission:assign-role-menu"})
        public void assignRoleMenu_success() throws Exception {
            SysRolePO role = createRole();
            SysMenuPO menu = createMenu();
            PermissionAssignRoleMenuReqVO reqVO = new PermissionAssignRoleMenuReqVO();
            reqVO.setRoleId(role.getId());
            Set<String> menuIds = new HashSet<>();
            menuIds.add(menu.getId());
            reqVO.setMenuIds(menuIds);
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(reqVO);
            mvc.perform(post("/admin/permission/assign-role-menu")
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
     * @date 2023/07/28
     * @description 给角色分配数据权限相关测试
     */
    @Nested
    class AssignRoleDataScopeTests {
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:permission:assign-role-data-scope"})
        public void assignRoleDataScope_success() throws Exception {
            PermissionAssignRoleDataScopeReqVO reqVO = new PermissionAssignRoleDataScopeReqVO();
            SysRolePO role = createRole();
            reqVO.setRoleId(role.getId());
            reqVO.setDataScope(1);
            Set<String> dataScopeDeptIds = new HashSet<>();
            dataScopeDeptIds.add("1");
            dataScopeDeptIds.add("2");
            dataScopeDeptIds.add("5");
            reqVO.setDataScopeDeptIds(dataScopeDeptIds);
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(reqVO);
            mvc.perform(post("/admin/permission/assign-role-data-scope")
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
     * @date 2023/07/28
     * @description 通过用户ID获取用户拥有的角色ID列表相关测试
     */
    @Nested
    class ListUserRolesTests {
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:permission:assign-user-role"})
        public void listUserRoles_success() throws Exception {
            // 创建角色
            SysRolePO role = createRole();
            // 创建用户
            SysUserPO user = createUser();
            // 给用户分配角色
            Set<String> roleIds = new HashSet<>();
            roleIds.add(role.getId());
            permissionService.assignUserRole(user.getId(), roleIds);
            String postRes = mvc.perform(get("/admin/permission/list-user-roles/" + user.getId()))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").isArray())
                    .andExpect(jsonPath("$.data").isNotEmpty())
                    .andReturn().getResponse().getContentAsString();
            String roleIdsStr = JSONObject.parseObject(postRes).get("data").toString();
            Set<String> roleIdsSet = JSONObject.parseObject(roleIdsStr, Set.class);
            assert roleIdsSet.contains(role.getId());
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/28
     * @description 给用户分配角色相关测试
     */
    @Nested
    class AssignUserRoleTests {
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:permission:assign-user-role"})
        public void assignUserRole_success() throws Exception {
            SysUserPO sysUserPO = createUser();
            SysRolePO role = createRole();
            PermissionAssignUserRoleReqVO reqVO = new PermissionAssignUserRoleReqVO();
            reqVO.setUserId(sysUserPO.getId());
            Set<String> roleIds = new HashSet<>();
            roleIds.add(role.getId());
            reqVO.setRoleIds(roleIds);
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(reqVO);
            mvc.perform(post("/admin/permission/assign-user-role")
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


    private SysRolePO createRole() {
        SysRolePO role = new SysRolePO();
        role.setName("测试角色");
        role.setSort(3);
        role.setCode("test_role");
        role.setRemark("备注");
        role.setStatus(CommonStatusEnum.ENABLE.getStatus());
        role.setType(2);
        roleMapper.insert(role);
        return role;
    }

    private Map<String, String> createRoleWithMenu() {
        RoleCreateReqVO roleCreateReqVO = new RoleCreateReqVO();
        roleCreateReqVO.setName("测试角色");
        roleCreateReqVO.setSort(3);
        roleCreateReqVO.setCode("test_role");
        roleCreateReqVO.setRemark("备注");
        SysMenuPO menu = createMenu();
        Set<String> menuIds = new HashSet<>();
        menuIds.add(menu.getId());
        roleCreateReqVO.setMenuIds(menuIds);
        String roleId = roleService.createRole(roleCreateReqVO, RoleTypeEnum.CUSTOM.getType());
        Map<String, String> map = new HashMap<>();
        map.put("roleId", roleId);
        map.put("menuId", menu.getId());
        return map;
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
