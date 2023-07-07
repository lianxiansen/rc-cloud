package com.rc.cloud.app.system.service.permission;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.rc.cloud.app.system.api.permission.dto.DeptDataPermissionRespDTO;
import com.rc.cloud.app.system.model.permission.SysMenuDO;
import com.rc.cloud.app.system.model.permission.SysRoleDO;
import com.rc.cloud.app.system.model.user.SysUserDO;
import com.rc.cloud.app.system.mapper.permission.RoleMenuMapper;
import com.rc.cloud.app.system.mapper.permission.UserRoleMapper;
import com.rc.cloud.app.system.model.dept.SysDeptDO;
import com.rc.cloud.app.system.model.permission.SysRoleMenuDO;
import com.rc.cloud.app.system.model.permission.SysUserRoleDO;
import com.rc.cloud.app.system.service.dept.DeptService;
import com.rc.cloud.app.system.service.user.AdminUserService;
import com.rc.cloud.app.system.enums.permission.DataScopeEnum;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.test.core.ut.BaseDbUnitTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.rc.cloud.common.core.util.collection.SetUtils.asSet;
import static com.rc.cloud.common.test.core.util.AssertUtils.assertPojoEquals;
import static com.rc.cloud.common.test.core.util.RandomUtils.*;
import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Import({PermissionServiceImpl.class})
public class PermissionServiceTest extends BaseDbUnitTest {

    @Resource
    private PermissionServiceImpl permissionService;

    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private UserRoleMapper userRoleMapper;

    @MockBean
    private RoleService roleService;
    @MockBean
    private MenuService menuService;
    @MockBean
    private DeptService deptService;
    @MockBean
    private AdminUserService userService;

//    @MockBean
//    private PermissionProducer permissionProducer;

    @Test
    public void testInitLocalCacheForRoleMenu() {
        // mock 数据
        SysRoleMenuDO roleMenuDO01 = randomPojo(SysRoleMenuDO.class, o -> {
            o.setRoleId("1");
            o.setMenuId("10");
        });
        roleMenuMapper.insert(roleMenuDO01);
        SysRoleMenuDO roleMenuDO02 = randomPojo(SysRoleMenuDO.class, o -> {
            o.setRoleId("1");
            o.setMenuId("20");
        });
        roleMenuMapper.insert(roleMenuDO02);

        // 调用
        permissionService.initLocalCacheForRoleMenu();
        // 断言 roleMenuCache 缓存
        assertEquals(1, permissionService.getRoleMenuCache().keySet().size());
        assertEquals(asList("10", "20"), permissionService.getRoleMenuCache().get("1"));
        // 断言 menuRoleCache 缓存
        assertEquals(2, permissionService.getMenuRoleCache().size());
        assertEquals(singletonList("1"), permissionService.getMenuRoleCache().get("10"));
        assertEquals(singletonList("1"), permissionService.getMenuRoleCache().get("20"));
    }

    @Test
    public void testInitLocalCacheForUserRole() {
        // mock 数据
        SysUserRoleDO userRoleDO01 = randomPojo(SysUserRoleDO.class, o -> {
            o.setUserId("1");
            o.setRoleId("10");
        });
        userRoleMapper.insert(userRoleDO01);
        SysUserRoleDO roleMenuDO02 = randomPojo(SysUserRoleDO.class, o -> {
            o.setUserId("1");
            o.setRoleId("20");
        });
        userRoleMapper.insert(roleMenuDO02);

        // 调用
        permissionService.initLocalCacheForUserRole();
        // 断言 roleMenuCache 缓存
        assertEquals(1, permissionService.getUserRoleCache().size());
        assertEquals(asSet("10", "20"), permissionService.getUserRoleCache().get("1"));
    }

    @Test
    public void testGetRoleMenuListFromCache_superAdmin() {
        // 准备参数
        Collection<String> roleIds = singletonList("100");
        Collection<Integer> menuTypes = asList(2, 3);
        Collection<Integer> menusStatuses = asList(0, 1);
        // mock 方法
        List<SysRoleDO> roleList = singletonList(randomPojo(SysRoleDO.class, o -> o.setId("100")));
        when(roleService.getRoleListFromCache(eq(roleIds))).thenReturn(roleList);
        when(roleService.hasAnySuperAdmin(same(roleList))).thenReturn(true);
        List<SysMenuDO> menuList = randomPojoList(SysMenuDO.class);
        when(menuService.getMenuListFromCache(eq(menuTypes), eq(menusStatuses))).thenReturn(menuList);

        // 调用
        List<SysMenuDO> result = permissionService.getRoleMenuListFromCache(roleIds, menuTypes, menusStatuses);
        // 断言
        assertSame(menuList, result);
    }

    @Test
    public void testGetRoleMenuListFromCache_normal() {
        // 准备参数
        Collection<String> roleIds = asSet("100", "200");
        Collection<Integer> menuTypes = asList(2, 3);
        Collection<Integer> menusStatuses = asList(0, 1);
        // mock 方法
        Multimap<String, String> roleMenuCache = ImmutableMultimap.<String, String>builder().put("200", "1000")
                .put("200", "200").put("200", "2001").build();
        permissionService.setRoleMenuCache(roleMenuCache);
        List<SysMenuDO> menuList = randomPojoList(SysMenuDO.class);
        when(menuService.getMenuListFromCache(eq(asList("1000", "2000", "2001")), eq(menuTypes), eq(menusStatuses))).thenReturn(menuList);

        // 调用
        List<SysMenuDO> result = permissionService.getRoleMenuListFromCache(roleIds, menuTypes, menusStatuses);
        // 断言
        assertSame(menuList, result);
    }

    @Test
    public void testGetUserRoleIdsFromCache() {
        // 准备参数
        String userId = "1";
        Collection<Integer> roleStatuses = singleton(CommonStatusEnum.ENABLE.getStatus());
        // mock 方法
        Map<String, Set<String>> userRoleCache = MapUtil.<String, Set<String>>builder()
                .put("1", asSet("10", "20")).build();
        permissionService.setUserRoleCache(userRoleCache);
        SysRoleDO roleDO01 = randomPojo(SysRoleDO.class, o -> o.setStatus(CommonStatusEnum.ENABLE.getStatus()));
        when(roleService.getRoleFromCache(eq("10"))).thenReturn(roleDO01);
        SysRoleDO roleDO02 = randomPojo(SysRoleDO.class, o -> o.setStatus(CommonStatusEnum.DISABLE.getStatus()));
        when(roleService.getRoleFromCache(eq("20"))).thenReturn(roleDO02);

        // 调用
        Set<String> roleIds = permissionService.getUserRoleIdsFromCache(userId, roleStatuses);
        // 断言
        assertEquals(asSet(10L), roleIds);
    }

    @Test
    public void testGetRoleMenuIds_superAdmin() {
        // 准备参数
        String roleId = "100";
        // mock 方法
        when(roleService.hasAnySuperAdmin(eq(singleton("100")))).thenReturn(true);
        SysMenuDO menuDO = randomPojo(SysMenuDO.class);
        menuDO.setId("1");
        List<SysMenuDO> menuList = singletonList(menuDO);
        when(menuService.getMenuList()).thenReturn(menuList);

        // 调用
        Set<String> menuIds = permissionService.getRoleMenuIds(roleId);
        // 断言
        assertEquals(singleton(1L), menuIds);
    }

    @Test
    public void testGetRoleMenuIds_normal() {
        // 准备参数
        String roleId = "100";
        // mock 数据
        SysRoleMenuDO roleMenuDO1 = randomPojo(SysRoleMenuDO.class);
        roleMenuDO1.setRoleId("100");
        roleMenuDO1.setMenuId("1");
        roleMenuMapper.insert(roleMenuDO1);
        SysRoleMenuDO roleMenuDO2 = randomPojo(SysRoleMenuDO.class);
        roleMenuDO2.setRoleId("100");
        roleMenuDO2.setMenuId("2");
        roleMenuMapper.insert(roleMenuDO2);

        // 调用
        Set<String> menuIds = permissionService.getRoleMenuIds(roleId);
        // 断言
        assertEquals(asSet("1", "2"), menuIds);
    }

    @Test
    public void testAssignRoleMenu() {
        // 准备参数
        String roleId = "1";
        Set<String> menuIds = asSet("200", "300");
        // mock 数据
        SysRoleMenuDO roleMenuDO1 = randomPojo(SysRoleMenuDO.class);
        roleMenuDO1.setRoleId("1");
        roleMenuDO1.setMenuId("100");
        roleMenuMapper.insert(roleMenuDO1);
        SysRoleMenuDO roleMenuDO2 = randomPojo(SysRoleMenuDO.class);
        roleMenuDO2.setRoleId("1");
        roleMenuDO2.setMenuId("200");
        roleMenuMapper.insert(roleMenuDO2);

        // 调用
        permissionService.assignRoleMenu(roleId, menuIds);
        // 断言
        List<SysRoleMenuDO> roleMenuList = roleMenuMapper.selectList();
        assertEquals(2, roleMenuList.size());
        assertEquals("1", roleMenuList.get(0).getRoleId());
        assertEquals("200", roleMenuList.get(0).getMenuId());
        assertEquals("1", roleMenuList.get(1).getRoleId());
        assertEquals("300", roleMenuList.get(1).getMenuId());
//        verify(permissionProducer).sendRoleMenuRefreshMessage();
    }

    @Test
    public void testAssignUserRole() {
        // 准备参数
        String userId = "1";
        Set<String> roleIds = asSet("200", "300");
        // mock 数据
        SysUserRoleDO userRoleDO1 = randomPojo(SysUserRoleDO.class);
        userRoleDO1.setUserId("1");
        userRoleDO1.setRoleId("100");
        userRoleMapper.insert(userRoleDO1);
        SysUserRoleDO userRoleDO2 = randomPojo(SysUserRoleDO.class);
        userRoleDO2.setUserId("1");
        userRoleDO2.setRoleId("200");
        userRoleMapper.insert(userRoleDO2);

        // 调用
        permissionService.assignUserRole(userId, roleIds);
        // 断言
        List<SysUserRoleDO> userRoleDOList = userRoleMapper.selectList();
        assertEquals(2, userRoleDOList.size());
        assertEquals("1", userRoleDOList.get(0).getUserId());
        assertEquals("200", userRoleDOList.get(0).getRoleId());
        assertEquals("1", userRoleDOList.get(1).getUserId());
        assertEquals("300", userRoleDOList.get(1).getRoleId());
//        verify(permissionProducer).sendUserRoleRefreshMessage();
    }

    @Test
    public void testGetUserRoleIdListByUserId() {
        // 准备参数
        String userId = "1";
        // mock 数据
        SysUserRoleDO userRoleDO01 = randomPojo(SysUserRoleDO.class, o -> {
            o.setUserId("1");
            o.setRoleId("10");
        });
        userRoleMapper.insert(userRoleDO01);
        SysUserRoleDO userRoleDO02 = randomPojo(SysUserRoleDO.class, o -> {
            o.setUserId("1");
            o.setRoleId("20");
        });
        userRoleMapper.insert(userRoleDO02);

        // 调用
        Set<String> result = permissionService.getUserRoleIdListByUserId(userId);
        // 断言
        assertEquals(asSet(10L, 20L), result);
    }

    @Test
    public void testGetUserRoleIdListByRoleIds() {
        // 准备参数
        Collection<String> roleIds = asSet("10", "20");
        // mock 数据
        SysUserRoleDO userRoleDO01 = randomPojo(SysUserRoleDO.class, o -> {
            o.setUserId("1");
            o.setRoleId("10");
        });
        userRoleMapper.insert(userRoleDO01);
        SysUserRoleDO roleMenuDO02 = randomPojo(SysUserRoleDO.class, o -> {
            o.setUserId("2");
            o.setRoleId("20");
        });
        userRoleMapper.insert(roleMenuDO02);

        // 调用
        Set<String> result = permissionService.getUserRoleIdListByRoleIds(roleIds);
        // 断言
        assertEquals(asSet("1", "2"), result);
    }

    @Test
    public void testAssignRoleDataScope() {
        // 准备参数
        String roleId = "1";
        Integer dataScope = 2;
        Set<String> dataScopeDeptIds = asSet("10", "20");

        // 调用
        permissionService.assignRoleDataScope(roleId, dataScope, dataScopeDeptIds);
        // 断言
        verify(roleService).updateRoleDataScope(eq(roleId), eq(dataScope), eq(dataScopeDeptIds));
    }

    @Test
    public void testProcessRoleDeleted() {
        // 准备参数
        String roleId = randomLongId().toString();
        // mock 数据 UserRole
        SysUserRoleDO userRoleDO01 = randomPojo(SysUserRoleDO.class, o -> o.setRoleId(roleId)); // 被删除
        userRoleMapper.insert(userRoleDO01);
        SysUserRoleDO userRoleDO02 = randomPojo(SysUserRoleDO.class); // 不被删除
        userRoleMapper.insert(userRoleDO02);
        // mock 数据 RoleMenu
        SysRoleMenuDO roleMenuDO01 = randomPojo(SysRoleMenuDO.class, o -> o.setRoleId(roleId)); // 被删除
        roleMenuMapper.insert(roleMenuDO01);
        SysRoleMenuDO roleMenuDO02 = randomPojo(SysRoleMenuDO.class); // 不被删除
        roleMenuMapper.insert(roleMenuDO02);

        // 调用
        permissionService.processRoleDeleted(roleId);
        // 断言数据 RoleMenuDO
        List<SysRoleMenuDO> dbRoleMenus = roleMenuMapper.selectList();
        assertEquals(1, dbRoleMenus.size());
        assertPojoEquals(dbRoleMenus.get(0), roleMenuDO02);
        // 断言数据 UserRoleDO
        List<SysUserRoleDO> dbUserRoles = userRoleMapper.selectList();
        assertEquals(1, dbUserRoles.size());
        assertPojoEquals(dbUserRoles.get(0), userRoleDO02);
        // 断言调用
//        verify(permissionProducer).sendRoleMenuRefreshMessage();
//        verify(permissionProducer).sendUserRoleRefreshMessage();
    }

    @Test
    public void testProcessMenuDeleted() {
        // 准备参数
        String menuId = randomLongId().toString();
        // mock 数据
        SysRoleMenuDO roleMenuDO01 = randomPojo(SysRoleMenuDO.class, o -> o.setMenuId(menuId)); // 被删除
        roleMenuMapper.insert(roleMenuDO01);
        SysRoleMenuDO roleMenuDO02 = randomPojo(SysRoleMenuDO.class); // 不被删除
        roleMenuMapper.insert(roleMenuDO02);

        // 调用
        permissionService.processMenuDeleted(menuId);
        // 断言数据
        List<SysRoleMenuDO> dbRoleMenus = roleMenuMapper.selectList();
        assertEquals(1, dbRoleMenus.size());
        assertPojoEquals(dbRoleMenus.get(0), roleMenuDO02);
        // 断言调用
//        verify(permissionProducer).sendRoleMenuRefreshMessage();
    }

    @Test
    public void testProcessUserDeleted() {
        // 准备参数
        String userId = randomLongId().toString();
        // mock 数据
        SysUserRoleDO userRoleDO01 = randomPojo(SysUserRoleDO.class, o -> o.setUserId(userId)); // 被删除
        userRoleMapper.insert(userRoleDO01);
        SysUserRoleDO userRoleDO02 = randomPojo(SysUserRoleDO.class); // 不被删除
        userRoleMapper.insert(userRoleDO02);

        // 调用
        permissionService.processUserDeleted(userId);
        // 断言数据
        List<SysUserRoleDO> dbUserRoles = userRoleMapper.selectList();
        assertEquals(1, dbUserRoles.size());
        assertPojoEquals(dbUserRoles.get(0), userRoleDO02);
        // 断言调用
//        verify(permissionProducer).sendUserRoleRefreshMessage();
    }

    @Test
    public void testHasAnyPermissions_superAdmin() {
        // 准备参数
        String userId = "1";
        String[] roles = new String[]{"system:user:query", "system:user:create"};
        // mock 用户与角色的缓存
        permissionService.setUserRoleCache(MapUtil.<String, Set<String>>builder().put("1", asSet("100")).build());
        SysRoleDO role = randomPojo(SysRoleDO.class, o -> {
            o.setId("100");
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
        });
        when(roleService.getRoleFromCache(eq("100"))).thenReturn(role);
        // mock 其它方法
        when(roleService.hasAnySuperAdmin(eq(asSet("100")))).thenReturn(true);

        // 调用
        boolean has = permissionService.hasAnyPermissions(userId, roles);
        // 断言
        assertTrue(has);
    }

    @Test
    public void testHasAnyPermissions_normal() {
        // 准备参数
        String userId = "1";
        String[] roles = new String[]{"system:user:query", "system:user:create"};
        // mock 用户与角色的缓存
        permissionService.setUserRoleCache(MapUtil.<String, Set<String>>builder().put("1", asSet("100")).build());
        SysRoleDO role = randomPojo(SysRoleDO.class, o -> {
            o.setId("100");
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
        });

        when(roleService.getRoleFromCache(eq("100"))).thenReturn(role);
        // mock 其它方法
        SysMenuDO menu = randomPojo(SysMenuDO.class, o -> o.setId("1000"));
        when(menuService.getMenuListByPermissionFromCache(eq("system:user:create"))).thenReturn(singletonList(menu));
        permissionService.setMenuRoleCache(ImmutableMultimap.<String, String>builder().put("1000", "100").build());


        // 调用
        boolean has = permissionService.hasAnyPermissions(userId, roles);
        // 断言
        assertTrue(has);
    }

    @Test
    public void testHasAnyRoles_superAdmin() {
        // 准备参数
        String userId = "1";
        String[] roles = new String[]{"yunai", "tudou"};
        // mock 用户与角色的缓存
        permissionService.setUserRoleCache(MapUtil.<String, Set<String>>builder().put("1", asSet("100")).build());
        SysRoleDO role = randomPojo(SysRoleDO.class, o -> {
            o.setId("100");
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
        });
        when(roleService.getRoleFromCache(eq("100"))).thenReturn(role);
        // mock 其它方法
        when(roleService.hasAnySuperAdmin(eq(asSet("100")))).thenReturn(true);

        // 调用
        boolean has = permissionService.hasAnyRoles(userId, roles);
        // 断言
        assertTrue(has);
    }

    @Test
    public void testHasAnyRoles_normal() {
        // 准备参数
        String userId = "1";
        String[] roles = new String[]{"yunai", "tudou"};
        // mock 用户与角色的缓存
        permissionService.setUserRoleCache(MapUtil.<String, Set<String>>builder().put("1", asSet("100")).build());
        SysRoleDO role = randomPojo(SysRoleDO.class, o -> {
            o.setId("100");
            o.setCode("yunai");
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
        });
        when(roleService.getRoleFromCache(eq("100"))).thenReturn(role);
        // mock 其它方法
        when(roleService.getRoleListFromCache(eq(asSet("100")))).thenReturn(singletonList(role));

        // 调用
        boolean has = permissionService.hasAnyRoles(userId, roles);
        // 断言
        assertTrue(has);
    }

    @Test
    public void testGetDeptDataPermission_All() {
        // 准备参数
        String userId = "1";
        // mock 用户的角色编号
        permissionService.setUserRoleCache(MapUtil.<String, Set<String>>builder().put("1", asSet("2")).build());
        // mock 获得用户的角色
        SysRoleDO roleDO = randomPojo(SysRoleDO.class, o -> {
            o.setId("2");
            o.setDataScope(DataScopeEnum.ALL.getScope());
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
        });
        when(roleService.getRoleListFromCache(eq(singleton("2")))).thenReturn(singletonList(roleDO));
        when(roleService.getRoleFromCache(eq("2"))).thenReturn(roleDO);

        // 调用
        DeptDataPermissionRespDTO result = permissionService.getDeptDataPermission(userId);
        // 断言
        assertTrue(result.getAll());
        assertFalse(result.getSelf());
        assertTrue(CollUtil.isEmpty(result.getDeptIds()));
    }

    @Test
    public void testGetDeptDataPermission_DeptCustom() {
        // 准备参数
        String userId = "1";
        // mock 用户的角色编号
        permissionService.setUserRoleCache(MapUtil.<String, Set<String>>builder().put("1", asSet("2")).build());
        // mock 获得用户的角色
        SysRoleDO roleDO = randomPojo(SysRoleDO.class, o -> {
            o.setId("2");
            o.setDataScope(DataScopeEnum.DEPT_CUSTOM.getScope());
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
        });
        when(roleService.getRoleListFromCache(eq(singleton("2")))).thenReturn(singletonList(roleDO));
        when(roleService.getRoleFromCache(eq("2"))).thenReturn(roleDO);
        // mock 部门的返回
        SysUserDO sysUserDO = new SysUserDO();
        sysUserDO.setDeptId("3");
        when(userService.getUser(eq("1"))).thenReturn(sysUserDO, null, null); // 最后返回 null 的目的，看看会不会重复调用

        // 调用
        DeptDataPermissionRespDTO result = permissionService.getDeptDataPermission(userId);
        // 断言
        assertFalse(result.getAll());
        assertFalse(result.getSelf());
        assertEquals(roleDO.getDataScopeDeptIds().size() + 1, result.getDeptIds().size());
        assertTrue(CollUtil.containsAll(result.getDeptIds(), roleDO.getDataScopeDeptIds()));
        assertTrue(CollUtil.contains(result.getDeptIds(), 3L));
    }

    @Test
    public void testGetDeptDataPermission_DeptOnly() {
        // 准备参数
        String userId = "1";
        // mock 用户的角色编号
        permissionService.setUserRoleCache(MapUtil.<String, Set<String>>builder().put("1", asSet("2")).build());
        // mock 获得用户的角色
        SysRoleDO roleDO = randomPojo(SysRoleDO.class, o -> {
            o.setDataScope(DataScopeEnum.DEPT_ONLY.getScope());
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
        });
        when(roleService.getRoleListFromCache(eq(singleton("2")))).thenReturn(singletonList(roleDO));
        when(roleService.getRoleFromCache(eq("2"))).thenReturn(roleDO);
        // mock 部门的返回
        SysUserDO sysUserDO = new SysUserDO();
        sysUserDO.setDeptId("3");
        when(userService.getUser(eq("1"))).thenReturn(sysUserDO, null, null); // 最后返回 null 的目的，看看会不会重复调用

        // 调用
        DeptDataPermissionRespDTO result = permissionService.getDeptDataPermission(userId);
        // 断言
        assertFalse(result.getAll());
        assertFalse(result.getSelf());
        assertEquals(1, result.getDeptIds().size());
        assertTrue(CollUtil.contains(result.getDeptIds(), "3"));
    }

    @Test
    public void testGetDeptDataPermission_DeptAndChild() {
        // 准备参数
        String userId = "1";
        // mock 用户的角色编号
        permissionService.setUserRoleCache(MapUtil.<String, Set<String>>builder().put("1", asSet("2")).build());
        // mock 获得用户的角色
        SysRoleDO roleDO = randomPojo(SysRoleDO.class, o -> {
            o.setDataScope(DataScopeEnum.DEPT_AND_CHILD.getScope());
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
        });
        when(roleService.getRoleListFromCache(eq(singleton("2")))).thenReturn(singletonList(roleDO));
        when(roleService.getRoleFromCache(eq("2"))).thenReturn(roleDO);
        // mock 部门的返回
        SysUserDO sysUserDO = new SysUserDO();
        sysUserDO.setDeptId("3");
        when(userService.getUser(eq("1"))).thenReturn(sysUserDO, null, null); // 最后返回 null 的目的，看看会不会重复调用
        // mock 方法（部门）
        SysDeptDO deptDO = randomPojo(SysDeptDO.class);
        when(deptService.getDeptListByParentIdFromCache(eq("3"), eq(true)))
                .thenReturn(singletonList(deptDO));

        // 调用
        DeptDataPermissionRespDTO result = permissionService.getDeptDataPermission(userId);
        // 断言
        assertFalse(result.getAll());
        assertFalse(result.getSelf());
        assertEquals(2, result.getDeptIds().size());
        assertTrue(CollUtil.contains(result.getDeptIds(), deptDO.getId()));
        assertTrue(CollUtil.contains(result.getDeptIds(), "3"));
    }

    @Test
    public void testGetDeptDataPermission_Self() {
        // 准备参数
        String userId = "1";
        // mock 用户的角色编号
        permissionService.setUserRoleCache(MapUtil.<String, Set<String>>builder().put("1", asSet("2")).build());
        // mock 获得用户的角色
        SysRoleDO roleDO = randomPojo(SysRoleDO.class, o -> {
            o.setDataScope(DataScopeEnum.SELF.getScope());
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
        });
        when(roleService.getRoleListFromCache(eq(singleton("2")))).thenReturn(singletonList(roleDO));
        when(roleService.getRoleFromCache(eq("2"))).thenReturn(roleDO);

        // 调用
        DeptDataPermissionRespDTO result = permissionService.getDeptDataPermission(userId);
        // 断言
        assertFalse(result.getAll());
        assertTrue(result.getSelf());
        assertTrue(CollUtil.isEmpty(result.getDeptIds()));
    }

}
