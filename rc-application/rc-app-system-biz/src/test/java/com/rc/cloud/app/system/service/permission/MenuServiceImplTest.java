package com.rc.cloud.app.system.service.permission;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.rc.cloud.app.system.model.permission.SysMenuPO;
import com.rc.cloud.app.system.mapper.permission.MenuMapper;
import com.rc.cloud.app.system.service.tenant.TenantService;
import com.rc.cloud.app.system.vo.permission.menu.MenuCreateReqVO;
import com.rc.cloud.app.system.vo.permission.menu.MenuListReqVO;
import com.rc.cloud.app.system.vo.permission.menu.MenuUpdateReqVO;
import com.rc.cloud.app.system.enums.permission.MenuTypeEnum;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.test.core.ut.BaseDbUnitTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.*;

import static com.rc.cloud.app.system.model.permission.SysMenuPO.ID_ROOT;
import static com.rc.cloud.app.system.enums.ErrorCodeConstants.*;
import static com.rc.cloud.common.core.util.collection.SetUtils.asSet;
import static com.rc.cloud.common.core.util.object.ObjectUtils.cloneIgnoreId;
import static com.rc.cloud.common.test.core.util.AssertUtils.assertPojoEquals;
import static com.rc.cloud.common.test.core.util.AssertUtils.assertServiceException;
import static com.rc.cloud.common.test.core.util.RandomUtils.*;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@Import(MenuServiceImpl.class)
public class MenuServiceImplTest extends BaseDbUnitTest {

    @Resource
    private MenuServiceImpl menuService;

    @Resource
    private MenuMapper menuMapper;

    @MockBean
    private PermissionService permissionService;
//    @MockBean
//    private MenuProducer menuProducer;
    @MockBean
    private TenantService tenantService;

    @Test
    public void testInitLocalCache_success() {
        SysMenuPO menuDO1 = randomPojo(SysMenuPO.class);
        menuMapper.insert(menuDO1);
        SysMenuPO menuDO2 = randomPojo(SysMenuPO.class);
        menuMapper.insert(menuDO2);

        // 调用
        menuService.initLocalCache();
        // 校验 menuCache 缓存
        Map<String, SysMenuPO> menuCache = menuService.getMenuCache();
        assertEquals(2, menuCache.size());
        assertPojoEquals(menuDO1, menuCache.get(menuDO1.getId()));
        assertPojoEquals(menuDO2, menuCache.get(menuDO2.getId()));
        // 校验 permissionMenuCache 缓存
        Multimap<String, SysMenuPO> permissionMenuCache = menuService.getPermissionMenuCache();
        assertEquals(2, permissionMenuCache.size());
        assertPojoEquals(menuDO1, permissionMenuCache.get(menuDO1.getPermission()));
        assertPojoEquals(menuDO2, permissionMenuCache.get(menuDO2.getPermission()));
    }

    @Test
    public void testCreateMenu_success() {
        // mock 数据（构造父菜单）
        SysMenuPO menuDO = createMenuDO(MenuTypeEnum.MENU,
                "parent", "0");
        menuMapper.insert(menuDO);
        String parentId = menuDO.getId();
        // 准备参数
        MenuCreateReqVO reqVO = randomPojo(MenuCreateReqVO.class, o -> {
            o.setParentId(parentId);
            o.setName("testSonName");
            o.setType(MenuTypeEnum.MENU.getType());
        });
        String menuId = menuService.createMenu(reqVO);

        // 校验记录的属性是否正确
        SysMenuPO dbMenu = menuMapper.selectById(menuId);
        assertPojoEquals(reqVO, dbMenu);
        // 校验调用
//        verify(menuProducer).sendMenuRefreshMessage();
    }

    @Test
    public void testUpdateMenu_success() {
        // mock 数据（构造父子菜单）
        SysMenuPO sonMenuDO = initParentAndSonMenu();
        String sonId = sonMenuDO.getId();
        // 准备参数
        MenuUpdateReqVO reqVO = randomPojo(MenuUpdateReqVO.class, o -> {
            o.setId(sonId);
            o.setName("testSonName"); // 修改名字
            o.setParentId(sonMenuDO.getParentId());
            o.setType(MenuTypeEnum.MENU.getType());
        });

        // 调用
        menuService.updateMenu(reqVO);
        // 校验记录的属性是否正确
        SysMenuPO dbMenu = menuMapper.selectById(sonId);
        assertPojoEquals(reqVO, dbMenu);
        // 校验调用
//        verify(menuProducer).sendMenuRefreshMessage();
    }

    @Test
    public void testUpdateMenu_sonIdNotExist() {
        // 准备参数
        MenuUpdateReqVO reqVO = randomPojo(MenuUpdateReqVO.class);
        // 调用，并断言异常
        assertServiceException(() -> menuService.updateMenu(reqVO), MENU_NOT_EXISTS);
    }

    @Test
    public void testDeleteMenu_success() {
        // mock 数据
        SysMenuPO menuDO = randomPojo(SysMenuPO.class);
        menuMapper.insert(menuDO);
        // 准备参数
        String id = menuDO.getId();

        // 调用
        menuService.deleteMenu(id);
        // 断言
        SysMenuPO dbMenuDO = menuMapper.selectById(id);
        assertNull(dbMenuDO);
        verify(permissionService).processMenuDeleted(id);
//        verify(menuProducer).sendMenuRefreshMessage();
    }

    @Test
    public void testDeleteMenu_menuNotExist() {
        assertServiceException(() -> menuService.deleteMenu(randomLongId().toString()),
                MENU_NOT_EXISTS);
    }

    @Test
    public void testDeleteMenu_existChildren() {
        // mock 数据（构造父子菜单）
        SysMenuPO sonMenu = initParentAndSonMenu();
        // 准备参数
        String parentId = sonMenu.getParentId();

        // 调用并断言异常
        assertServiceException(() -> menuService.deleteMenu(parentId), MENU_EXISTS_CHILDREN);
    }

    @Test
    public void testGetMenuList_all() {
        // mock 数据
        SysMenuPO menu100 = randomPojo(SysMenuPO.class);
        menuMapper.insert(menu100);
        SysMenuPO menu101 = randomPojo(SysMenuPO.class);
        menuMapper.insert(menu101);
        // 准备参数

        // 调用
        List<SysMenuPO> list = menuService.getMenuList();
        // 断言
        assertEquals(2, list.size());
        assertPojoEquals(menu100, list.get(0));
        assertPojoEquals(menu101, list.get(1));
    }

    @Test
    public void testGetMenuList() {
        // mock 数据
        SysMenuPO menuDO = randomPojo(SysMenuPO.class, o ->
        {
            o.setName("芋艿");
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
        });

        menuMapper.insert(menuDO);
        // 测试 status 不匹配
        menuMapper.insert(cloneIgnoreId(menuDO, o -> o.setStatus(CommonStatusEnum.DISABLE.getStatus())));
        // 测试 name 不匹配
        menuMapper.insert(cloneIgnoreId(menuDO, o -> o.setName("艿")));
        // 准备参数
        MenuListReqVO menuListReqVO = new MenuListReqVO();
        menuListReqVO.setName("芋");
        menuListReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());

        // 调用
        List<SysMenuPO> result = menuService.getMenuList(menuListReqVO);
        // 断言
        assertEquals(1, result.size());
        assertPojoEquals(menuDO, result.get(0));
    }

    @Test
    public void testGetMenuListByTenant() {
        // mock 数据
        SysMenuPO menu100 = randomPojo(SysMenuPO.class, o -> {
            o.setId("100");
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
        });
        menuMapper.insert(menu100);
        SysMenuPO menu101 = randomPojo(SysMenuPO.class, o -> {
            o.setId("101");
            o.setStatus(CommonStatusEnum.DISABLE.getStatus());
        });
        menuMapper.insert(menu101);
        SysMenuPO menu102 = randomPojo(SysMenuPO.class, o -> {
            o.setId("102");
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
        });
        menuMapper.insert(menu102);
        // mock 过滤菜单
        Set<String> menuIds = asSet("100", "101");
        doNothing().when(tenantService).handleTenantMenu(argThat(handler -> {
            handler.handle(menuIds);
            return true;
        }));
        // 准备参数
        MenuListReqVO menuListReqVO = new MenuListReqVO();
        menuListReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());

        // 调用
        List<SysMenuPO> result = menuService.getMenuListByTenant(menuListReqVO);
        // 断言
        assertEquals(1, result.size());
        assertPojoEquals(menu100, result.get(0));
    }

    @Test
    public void testListMenusFromCache_withoutId() {
        // mock 缓存
        Map<String, SysMenuPO> menuCache = new HashMap<>();
        // 可被匹配
        SysMenuPO menuDO = randomPojo(SysMenuPO.class, o -> {
            o.setId("1");
            o.setType(MenuTypeEnum.MENU.getType());
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
        });
        menuCache.put(menuDO.getId(), menuDO);
        // 测试 type 不匹配
        menuCache.put("3", randomPojo(SysMenuPO.class, o -> {
            o.setId("3");
            o.setType(MenuTypeEnum.BUTTON.getType());
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
        }));
        // 测试 status 不匹配
        menuCache.put("4", randomPojo(SysMenuPO.class, o -> {
            o.setId("4");
            o.setType(MenuTypeEnum.MENU.getType());
            o.setStatus(CommonStatusEnum.DISABLE.getStatus());
        }));
        menuService.setMenuCache(menuCache);
        // 准备参数
        Collection<Integer> menuTypes = singletonList(MenuTypeEnum.MENU.getType());
        Collection<Integer> menusStatuses = singletonList(CommonStatusEnum.ENABLE.getStatus());

        // 调用
        List<SysMenuPO> list = menuService.getMenuListFromCache(menuTypes, menusStatuses);
        // 断言
        assertEquals(1, list.size());
        assertPojoEquals(menuDO, list.get(0));
    }

    @Test
    public void testListMenusFromCache_withId() {
        // mock 缓存
        Map<String, SysMenuPO> menuCache = new HashMap<>();
        // 可被匹配
        SysMenuPO menuDO = randomPojo(SysMenuPO.class, o -> {
            o.setId("1");
            o.setType(MenuTypeEnum.MENU.getType());
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
        });
        menuCache.put(menuDO.getId(), menuDO);
        // 测试 id 不匹配
        menuCache.put("2", randomPojo(SysMenuPO.class, o -> {
            o.setId("2");
            o.setType(MenuTypeEnum.MENU.getType());
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
        }));
        // 测试 type 不匹配
        menuCache.put("3", randomPojo(SysMenuPO.class, o -> {
            o.setId("3");
            o.setType(MenuTypeEnum.BUTTON.getType());
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
        }));
        // 测试 status 不匹配
        menuCache.put("4", randomPojo(SysMenuPO.class, o -> {
            o.setId("4");
            o.setType(MenuTypeEnum.MENU.getType());
            o.setStatus(CommonStatusEnum.DISABLE.getStatus());
        }));
        menuService.setMenuCache(menuCache);
        // 准备参数
        Collection<String> menuIds = asList("1", "3", "4");
        Collection<Integer> menuTypes = singletonList(MenuTypeEnum.MENU.getType());
        Collection<Integer> menusStatuses = singletonList(CommonStatusEnum.ENABLE.getStatus());

        // 调用
        List<SysMenuPO> list = menuService.getMenuListFromCache(menuIds, menuTypes, menusStatuses);
        // 断言
        assertEquals(1, list.size());
        assertPojoEquals(menuDO, list.get(0));
    }

    @Test
    public void testGetMenuListByPermissionFromCache() {
        // mock 缓存
        Multimap<String, SysMenuPO> permissionMenuCache = LinkedListMultimap.create();
        // 可被匹配
        SysMenuPO menuDO01 = randomPojo(SysMenuPO.class, o -> {
            o.setId("1");
            o.setPermission("123");
        });
        permissionMenuCache.put(menuDO01.getPermission(), menuDO01);
        SysMenuPO menuDO02 = randomPojo(SysMenuPO.class, o -> {
            o.setId("2");
            o.setPermission("123");
        });
        permissionMenuCache.put(menuDO02.getPermission(), menuDO02);
        // 不可匹配
        permissionMenuCache.put("456", randomPojo(SysMenuPO.class, o -> {
            o.setId("3");
            o.setPermission("456");
        }));
        menuService.setPermissionMenuCache(permissionMenuCache);
        // 准备参数
        String permission = "123";

        // 调用
        List<SysMenuPO> list = menuService.getMenuListByPermissionFromCache(permission);
        // 断言
        assertEquals(2, list.size());
        assertPojoEquals(menuDO01, list.get(0));
        assertPojoEquals(menuDO02, list.get(1));
    }

    @Test
    public void testGetMenu() {
        // mock 数据
        SysMenuPO menu = randomPojo(SysMenuPO.class);
        menuMapper.insert(menu);
        // 准备参数
        String id = menu.getId();

        // 调用
        SysMenuPO dbMenu = menuService.getMenu(id);
        // 断言
        assertPojoEquals(menu, dbMenu);
    }

    @Test
    public void testValidateParentMenu_success() {
        // mock 数据
        SysMenuPO menuDO = createMenuDO(MenuTypeEnum.MENU, "parent", "0");
        menuMapper.insert(menuDO);
        // 准备参数
        String parentId = menuDO.getId();

        // 调用，无需断言
        menuService.validateParentMenu(parentId, null);
    }

    @Test
    public void testValidateParentMenu_canNotSetSelfToBeParent() {
        // 调用，并断言异常
        assertServiceException(() -> menuService.validateParentMenu("1", "1"),
                MENU_PARENT_ERROR);
    }

    @Test
    public void testValidateParentMenu_parentNotExist() {
        // 调用，并断言异常
        assertServiceException(() -> menuService.validateParentMenu(randomLongId().toString(), null),
                MENU_PARENT_NOT_EXISTS);
    }

    @Test
    public void testValidateParentMenu_parentTypeError() {
        // mock 数据
        SysMenuPO menuDO = createMenuDO(MenuTypeEnum.BUTTON, "parent", "0");
        menuMapper.insert(menuDO);
        // 准备参数
        String parentId = menuDO.getId();

        // 调用，并断言异常
        assertServiceException(() -> menuService.validateParentMenu(parentId, null),
                MENU_PARENT_NOT_DIR_OR_MENU);
    }

    @Test
    public void testValidateMenu_success() {
        // mock 父子菜单
        SysMenuPO sonMenu = initParentAndSonMenu();
        // 准备参数
        String parentId = sonMenu.getParentId();
        String otherSonMenuId = randomLongId().toString();
        String otherSonMenuName = randomString();

        // 调用，无需断言
        menuService.validateMenu(parentId, otherSonMenuName, otherSonMenuId);
    }

    @Test
    public void testValidateMenu_sonMenuNameDuplicate() {
        // mock 父子菜单
        SysMenuPO sonMenu = initParentAndSonMenu();
        // 准备参数
        String parentId = sonMenu.getParentId();
        String otherSonMenuId = randomLongId().toString();
        String otherSonMenuName = sonMenu.getName(); //相同名称

        // 调用，并断言异常
        assertServiceException(() -> menuService.validateMenu(parentId, otherSonMenuName, otherSonMenuId),
                MENU_NAME_DUPLICATE);
    }

    // ====================== 初始化方法 ======================

    /**
     * 构造父子菜单，返回子菜单
     *
     * @return 子菜单
     */
    private SysMenuPO initParentAndSonMenu() {
        // 构造父子菜单
        SysMenuPO parentMenuDO = createMenuDO(MenuTypeEnum.MENU, "parent", ID_ROOT);
        menuMapper.insert(parentMenuDO);
        // 构建子菜单
        SysMenuPO sonMenuDO = createMenuDO(MenuTypeEnum.MENU, "testSonName",
                parentMenuDO.getParentId());
        menuMapper.insert(sonMenuDO);
        return sonMenuDO;
    }

    private SysMenuPO createMenuDO(MenuTypeEnum type, String name, String parentId) {
        return createMenuDO(type, name, parentId, randomCommonStatus());
    }

    private SysMenuPO createMenuDO(MenuTypeEnum type, String name, String parentId, Integer status) {
        return randomPojo(SysMenuPO.class, o -> {
            o.setId(null);
            o.setName(name);
            o.setParentId(parentId);
            o.setType(type.getType());
            o.setStatus(status);
        });
    }

}
