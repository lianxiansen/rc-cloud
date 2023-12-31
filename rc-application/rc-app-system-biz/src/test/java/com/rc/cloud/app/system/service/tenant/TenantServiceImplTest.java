package com.rc.cloud.app.system.service.tenant;

import com.rc.cloud.app.system.model.permission.SysMenuPO;
import com.rc.cloud.app.system.model.tenant.SysTenantPO;
import com.rc.cloud.app.system.model.tenant.SysTenantPackagePO;
import com.rc.cloud.app.system.mapper.tenant.TenantMapper;
import com.rc.cloud.app.system.model.permission.SysRolePO;
import com.rc.cloud.app.system.service.permission.MenuService;
import com.rc.cloud.app.system.service.permission.PermissionService;
import com.rc.cloud.app.system.service.permission.RoleService;
import com.rc.cloud.app.system.service.tenant.handler.TenantInfoHandler;
import com.rc.cloud.app.system.service.tenant.handler.TenantMenuHandler;
import com.rc.cloud.app.system.service.user.AdminUserService;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantCreateReqVO;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantExportReqVO;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantPageReqVO;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantUpdateReqVO;
import com.rc.cloud.app.system.enums.permission.RoleCodeEnum;
import com.rc.cloud.app.system.enums.permission.RoleTypeEnum;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.tenant.config.TenantProperties;
import com.rc.cloud.common.tenant.core.context.TenantContextHolder;
import com.rc.cloud.common.test.core.ut.BaseDbUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.rc.cloud.app.system.model.tenant.SysTenantPO.PACKAGE_ID_SYSTEM;
import static com.rc.cloud.app.system.enums.ErrorCodeConstants.*;
import static com.rc.cloud.common.core.util.collection.SetUtils.asSet;
import static com.rc.cloud.common.core.util.date.LocalDateTimeUtils.buildBetweenTime;
import static com.rc.cloud.common.core.util.date.LocalDateTimeUtils.buildTime;
import static com.rc.cloud.common.core.util.object.ObjectUtils.cloneIgnoreId;
import static com.rc.cloud.common.test.core.util.AssertUtils.assertPojoEquals;
import static com.rc.cloud.common.test.core.util.AssertUtils.assertServiceException;
import static com.rc.cloud.common.test.core.util.RandomUtils.*;
import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * {@link TenantServiceImpl} 的单元测试类
 *
 * @author 芋道源码
 */
@Import(TenantServiceImpl.class)
public class TenantServiceImplTest extends BaseDbUnitTest {

    @Resource
    private TenantServiceImpl tenantService;

    @Resource
    private TenantMapper tenantMapper;

    @MockBean
    private TenantProperties tenantProperties;
    @MockBean
    private TenantPackageService tenantPackageService;
    @MockBean
    private AdminUserService userService;
    @MockBean
    private RoleService roleService;
    @MockBean
    private MenuService menuService;
    @MockBean
    private PermissionService permissionService;

    @BeforeEach
    public void setUp() {
        // 清理租户上下文
        TenantContextHolder.clear();
    }

    @Test
    public void testGetTenantIdList() {
        // mock 数据
        SysTenantPO tenant = randomPojo(SysTenantPO.class, o -> o.setId("2"));
        tenantMapper.insert(tenant);

        // 调用，并断言业务异常
        List<String> result = tenantService.getTenantIdList();
        assertTrue(result.contains("2"));
    }

    @Test
    public void testValidTenant_notExists() {
        assertServiceException(() -> tenantService.validTenant(randomLongId().toString()), TENANT_NOT_EXISTS);
    }

    @Test
    public void testValidTenant_disable() {
        // mock 数据
        SysTenantPO tenant = randomPojo(SysTenantPO.class, o -> {
            o.setId("2");
            o.setStatus(CommonStatusEnum.DISABLE.getStatus());
        });
        tenantMapper.insert(tenant);

        // 调用，并断言业务异常
        assertServiceException(() -> tenantService.validTenant("2"), TENANT_DISABLE, tenant.getName());
    }

    @Test
    public void testValidTenant_expired() {
        // mock 数据
        SysTenantPO tenant = randomPojo(SysTenantPO.class, o -> {
            o.setId("2");
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
            o.setExpireTime(buildTime(2020, 2, 2));
        });
        tenantMapper.insert(tenant);

        // 调用，并断言业务异常
        assertServiceException(() -> tenantService.validTenant("2"), TENANT_EXPIRE, tenant.getName());
    }

    @Test
    public void testValidTenant_success() {
        // mock 数据
        SysTenantPO tenant = randomPojo(SysTenantPO.class, o -> {
            o.setId("2");
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
            o.setExpireTime(LocalDateTime.now().plusDays(1));
        });
        tenantMapper.insert(tenant);

        // 调用，并断言业务异常
        tenantService.validTenant("1");
    }

    @Test
    public void testCreateTenant() {
        // mock 套餐 100L
        SysTenantPackagePO tenantPackage = randomPojo(SysTenantPackagePO.class, o -> o.setId("100"));
        when(tenantPackageService.validTenantPackage(eq("100"))).thenReturn(tenantPackage);
        // mock 角色 200L
        when(roleService.createRole(argThat(role -> {
            assertEquals(RoleCodeEnum.TENANT_ADMIN.getName(), role.getName());
            assertEquals(RoleCodeEnum.TENANT_ADMIN.getCode(), role.getCode());
            assertEquals(0, role.getSort());
            assertEquals("系统自动生成", role.getRemark());
            return true;
        }), eq(RoleTypeEnum.SYSTEM.getType()))).thenReturn("200");
        // mock 用户 300L
        when(userService.createUser(argThat(user -> {
            assertEquals("yunai", user.getUsername());
            assertEquals("yuanma", user.getPassword());
            assertEquals("芋道", user.getNickname());
            assertEquals("15601691300", user.getMobile());
            return true;
        }))).thenReturn("300");

        // 准备参数
        TenantCreateReqVO reqVO = randomPojo(TenantCreateReqVO.class, o -> {
            o.setContactName("芋道");
            o.setContactMobile("15601691300");
            o.setPackageId("100");
            o.setStatus(randomCommonStatus());
            o.setDomain("https://www.iocoder.cn");
            o.setUsername("yunai");
            o.setPassword("yuanma");
        });

        // 调用
        String tenantId = tenantService.createTenant(reqVO);
        // 断言
        assertNotNull(tenantId);
        // 校验记录的属性是否正确
        SysTenantPO tenant = tenantMapper.selectById(tenantId);
        assertPojoEquals(reqVO, tenant);
        assertEquals("300", tenant.getContactUserId());
        // verify 分配权限
        verify(permissionService).assignRoleMenu(eq("200"), same(tenantPackage.getMenuIds()));
        // verify 分配角色
        verify(permissionService).assignUserRole(eq("300"), eq(singleton("200")));
    }

    @Test
    public void testUpdateTenant_success() {
        // mock 数据
        SysTenantPO dbTenant = randomPojo(SysTenantPO.class, o -> o.setStatus(randomCommonStatus()));
        tenantMapper.insert(dbTenant);// @Sql: 先插入出一条存在的数据
        // 准备参数
        TenantUpdateReqVO reqVO = randomPojo(TenantUpdateReqVO.class, o -> {
            o.setId(dbTenant.getId()); // 设置更新的 ID
            o.setStatus(randomCommonStatus());
            o.setDomain(randomString());
        });

        // mock 套餐
        SysTenantPackagePO tenantPackage = randomPojo(SysTenantPackagePO.class,
                o -> o.setMenuIds(asSet("200", "201")));
        when(tenantPackageService.validTenantPackage(eq(reqVO.getPackageId()))).thenReturn(tenantPackage);
        // mock 所有角色
        SysRolePO role100 = randomPojo(SysRolePO.class, o -> {
            o.setId("100");
            o.setCode(RoleCodeEnum.TENANT_ADMIN.getCode());
        });
        role100.setTenantId(dbTenant.getId());
        SysRolePO role101 = randomPojo(SysRolePO.class, o -> o.setId("101"));
        role101.setTenantId(dbTenant.getId());
        when(roleService.getRoleListByStatus(isNull())).thenReturn(asList(role100, role101));
        // mock 每个角色的权限
        when(permissionService.getRoleMenuIds(eq("101"))).thenReturn(asSet("201", "202"));

        // 调用
        tenantService.updateTenant(reqVO);
        // 校验是否更新正确
        SysTenantPO tenant = tenantMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, tenant);
        // verify 设置角色权限
        verify(permissionService).assignRoleMenu(eq("100"), eq(asSet("200", "201")));
        verify(permissionService).assignRoleMenu(eq("101"), eq(asSet("201")));
    }

    @Test
    public void testUpdateTenant_notExists() {
        // 准备参数
        TenantUpdateReqVO reqVO = randomPojo(TenantUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> tenantService.updateTenant(reqVO), TENANT_NOT_EXISTS);
    }

    @Test
    public void testUpdateTenant_system() {
        // mock 数据
        SysTenantPO dbTenant = randomPojo(SysTenantPO.class, o -> o.setPackageId(PACKAGE_ID_SYSTEM));
        tenantMapper.insert(dbTenant);// @Sql: 先插入出一条存在的数据
        // 准备参数
        TenantUpdateReqVO reqVO = randomPojo(TenantUpdateReqVO.class, o -> {
            o.setId(dbTenant.getId()); // 设置更新的 ID
        });

        // 调用，校验业务异常
        assertServiceException(() -> tenantService.updateTenant(reqVO), TENANT_CAN_NOT_UPDATE_SYSTEM);
    }

    @Test
    public void testDeleteTenant_success() {
        // mock 数据
        SysTenantPO dbTenant = randomPojo(SysTenantPO.class,
                o -> o.setStatus(randomCommonStatus()));
        tenantMapper.insert(dbTenant);// @Sql: 先插入出一条存在的数据
        // 准备参数
        String id = dbTenant.getId();

        // 调用
        tenantService.deleteTenant(id);
        // 校验数据不存在了
        assertNull(tenantMapper.selectById(id));
    }

    @Test
    public void testDeleteTenant_notExists() {
        // 准备参数
        String id = randomLongId().toString();

        // 调用, 并断言异常
        assertServiceException(() -> tenantService.deleteTenant(id), TENANT_NOT_EXISTS);
    }

    @Test
    public void testDeleteTenant_system() {
        // mock 数据
        SysTenantPO dbTenant = randomPojo(SysTenantPO.class, o -> o.setPackageId(PACKAGE_ID_SYSTEM));
        tenantMapper.insert(dbTenant);// @Sql: 先插入出一条存在的数据
        // 准备参数
        String id = dbTenant.getId();

        // 调用, 并断言异常
        assertServiceException(() -> tenantService.deleteTenant(id), TENANT_CAN_NOT_UPDATE_SYSTEM);
    }

    @Test
    public void testGetTenant() {
        // mock 数据
        SysTenantPO dbTenant = randomPojo(SysTenantPO.class);
        tenantMapper.insert(dbTenant);// @Sql: 先插入出一条存在的数据
        // 准备参数
        String id = dbTenant.getId();

        // 调用
        SysTenantPO result = tenantService.getTenant(id);
        // 校验存在
        assertPojoEquals(result, dbTenant);
    }

    @Test
    public void testGetTenantPage() {
        // mock 数据
        SysTenantPO dbTenant = randomPojo(SysTenantPO.class, o -> { // 等会查询到
            o.setName("芋道源码");
            o.setContactName("芋艿");
            o.setContactMobile("15601691300");
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
            o.setCreateTime(buildTime(2020, 12, 12));
        });
        tenantMapper.insert(dbTenant);
        // 测试 name 不匹配
        tenantMapper.insert(cloneIgnoreId(dbTenant, o -> o.setName(randomString())));
        // 测试 contactName 不匹配
        tenantMapper.insert(cloneIgnoreId(dbTenant, o -> o.setContactName(randomString())));
        // 测试 contactMobile 不匹配
        tenantMapper.insert(cloneIgnoreId(dbTenant, o -> o.setContactMobile(randomString())));
        // 测试 status 不匹配
        tenantMapper.insert(cloneIgnoreId(dbTenant, o -> o.setStatus(CommonStatusEnum.DISABLE.getStatus())));
        // 测试 createTime 不匹配
        tenantMapper.insert(cloneIgnoreId(dbTenant, o -> o.setCreateTime(buildTime(2021, 12, 12))));
        // 准备参数
        TenantPageReqVO reqVO = new TenantPageReqVO();
        reqVO.setName("芋道");
        reqVO.setContactName("艿");
        reqVO.setContactMobile("1560");
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        reqVO.setCreateTime(buildBetweenTime(2020, 12, 1, 2020, 12, 24));

        // 调用
        PageResult<SysTenantPO> pageResult = tenantService.getTenantPage(reqVO);
        // 断言
        assertEquals(1, pageResult.getTotal());
        assertEquals(1, pageResult.getList().size());
        assertPojoEquals(dbTenant, pageResult.getList().get(0));
    }

    @Test
    public void testGetTenantList() {
        // mock 数据
        SysTenantPO dbTenant = randomPojo(SysTenantPO.class, o -> { // 等会查询到
            o.setName("芋道源码");
            o.setContactName("芋艿");
            o.setContactMobile("15601691300");
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
            o.setCreateTime(buildTime(2020, 12, 12));
        });
        tenantMapper.insert(dbTenant);
        // 测试 name 不匹配
        tenantMapper.insert(cloneIgnoreId(dbTenant, o -> o.setName(randomString())));
        // 测试 contactName 不匹配
        tenantMapper.insert(cloneIgnoreId(dbTenant, o -> o.setContactName(randomString())));
        // 测试 contactMobile 不匹配
        tenantMapper.insert(cloneIgnoreId(dbTenant, o -> o.setContactMobile(randomString())));
        // 测试 status 不匹配
        tenantMapper.insert(cloneIgnoreId(dbTenant, o -> o.setStatus(CommonStatusEnum.DISABLE.getStatus())));
        // 测试 createTime 不匹配
        tenantMapper.insert(cloneIgnoreId(dbTenant, o -> o.setCreateTime(buildTime(2021, 12, 12))));
        // 准备参数
        TenantExportReqVO reqVO = new TenantExportReqVO();
        reqVO.setName("芋道");
        reqVO.setContactName("艿");
        reqVO.setContactMobile("1560");
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        reqVO.setCreateTime(buildBetweenTime(2020, 12, 1, 2020, 12, 24));

        // 调用
        List<SysTenantPO> list = tenantService.getTenantList(reqVO);
        // 断言
        assertEquals(1, list.size());
        assertPojoEquals(dbTenant, list.get(0));
    }

    @Test
    public void testGetTenantByName() {
        // mock 数据
        SysTenantPO dbTenant = randomPojo(SysTenantPO.class, o -> o.setName("芋道"));
        tenantMapper.insert(dbTenant);// @Sql: 先插入出一条存在的数据

        // 调用
        SysTenantPO result = tenantService.getTenantByName("芋道");
        // 校验存在
        assertPojoEquals(result, dbTenant);
    }

    @Test
    public void testGetTenantListByPackageId() {
        // mock 数据
        SysTenantPO dbTenant1 = randomPojo(SysTenantPO.class, o -> o.setPackageId("1"));
        tenantMapper.insert(dbTenant1);// @Sql: 先插入出一条存在的数据
        SysTenantPO dbTenant2 = randomPojo(SysTenantPO.class, o -> o.setPackageId("2"));
        tenantMapper.insert(dbTenant2);// @Sql: 先插入出一条存在的数据

        // 调用
        List<SysTenantPO> result = tenantService.getTenantListByPackageId("1");
        assertEquals(1, result.size());
        assertPojoEquals(dbTenant1, result.get(0));
    }

    @Test
    public void testGetTenantCountByPackageId() {
        // mock 数据
        SysTenantPO dbTenant1 = randomPojo(SysTenantPO.class, o -> o.setPackageId("1"));
        tenantMapper.insert(dbTenant1);// @Sql: 先插入出一条存在的数据
        SysTenantPO dbTenant2 = randomPojo(SysTenantPO.class, o -> o.setPackageId("2"));
        tenantMapper.insert(dbTenant2);// @Sql: 先插入出一条存在的数据

        // 调用
        Long count = tenantService.getTenantCountByPackageId("1");
        assertEquals(1, count);
    }

    @Test
    public void testHandleTenantInfo_disable() {
        // 准备参数
        TenantInfoHandler handler = mock(TenantInfoHandler.class);
        // mock 禁用
        when(tenantProperties.getEnable()).thenReturn(false);

        // 调用
        tenantService.handleTenantInfo(handler);
        // 断言
        verify(handler, never()).handle(any());
    }

    @Test
    public void testHandleTenantInfo_success() {
        // 准备参数
        TenantInfoHandler handler = mock(TenantInfoHandler.class);
        // mock 未禁用
        when(tenantProperties.getEnable()).thenReturn(true);
        // mock 租户
        SysTenantPO dbTenant = randomPojo(SysTenantPO.class);
        tenantMapper.insert(dbTenant);// @Sql: 先插入出一条存在的数据
        TenantContextHolder.setTenantId(dbTenant.getId());

        // 调用
        tenantService.handleTenantInfo(handler);
        // 断言
        verify(handler).handle(argThat(argument -> {
            assertPojoEquals(dbTenant, argument);
            return true;
        }));
    }

    @Test
    public void testHandleTenantMenu_disable() {
        // 准备参数
        TenantMenuHandler handler = mock(TenantMenuHandler.class);
        // mock 禁用
        when(tenantProperties.getEnable()).thenReturn(false);

        // 调用
        tenantService.handleTenantMenu(handler);
        // 断言
        verify(handler, never()).handle(any());
    }

    @Test // 系统租户的情况
    public void testHandleTenantMenu_system() {
        // 准备参数
        TenantMenuHandler handler = mock(TenantMenuHandler.class);
        // mock 未禁用
        when(tenantProperties.getEnable()).thenReturn(true);
        // mock 租户
        SysTenantPO dbTenant = randomPojo(SysTenantPO.class, o -> o.setPackageId(PACKAGE_ID_SYSTEM));
        tenantMapper.insert(dbTenant);// @Sql: 先插入出一条存在的数据
        TenantContextHolder.setTenantId(dbTenant.getId());
        // mock 菜单
        when(menuService.getMenuList()).thenReturn(Arrays.asList(randomPojo(SysMenuPO.class, o -> o.setId("100")),
                        randomPojo(SysMenuPO.class, o -> o.setId("101"))));

        // 调用
        tenantService.handleTenantMenu(handler);
        // 断言
        verify(handler).handle(asSet("100", "101"));
    }

    @Test // 普通租户的情况
    public void testHandleTenantMenu_normal() {
        // 准备参数
        TenantMenuHandler handler = mock(TenantMenuHandler.class);
        // mock 未禁用
        when(tenantProperties.getEnable()).thenReturn(true);
        // mock 租户
        SysTenantPO dbTenant = randomPojo(SysTenantPO.class, o -> o.setPackageId("200"));
        tenantMapper.insert(dbTenant);// @Sql: 先插入出一条存在的数据
        TenantContextHolder.setTenantId(dbTenant.getId());
        // mock 菜单
        when(tenantPackageService.getTenantPackage(eq("200"))).thenReturn(randomPojo(SysTenantPackagePO.class,
                o -> o.setMenuIds(asSet("100", "101"))));

        // 调用
        tenantService.handleTenantMenu(handler);
        // 断言
        verify(handler).handle(asSet("100", "101"));
    }
}
