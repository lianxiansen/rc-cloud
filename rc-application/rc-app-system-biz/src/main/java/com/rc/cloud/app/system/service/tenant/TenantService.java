package com.rc.cloud.app.system.service.tenant;

import com.rc.cloud.app.system.model.tenant.SysTenantPO;
import com.rc.cloud.app.system.service.tenant.handler.TenantInfoHandler;
import com.rc.cloud.app.system.service.tenant.handler.TenantMenuHandler;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantCreateReqVO;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantExportReqVO;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantPageReqVO;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantUpdateReqVO;
import com.rc.cloud.common.core.pojo.PageResult;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 租户 Service 接口
 */
public interface TenantService {

    /**
     * 创建租户
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createTenant(@Valid TenantCreateReqVO createReqVO);

    /**
     * 更新租户
     *
     * @param updateReqVO 更新信息
     */
    void updateTenant(@Valid TenantUpdateReqVO updateReqVO);

    /**
     * 更新租户的角色菜单
     *
     * @param tenantId 租户编号
     * @param menuIds  菜单编号数组
     */
    void updateTenantRoleMenu(String tenantId, Set<String> menuIds);

    /**
     * 删除租户
     *
     * @param id 编号
     */
    void deleteTenant(String id);

    /**
     * 获得租户
     *
     * @param id 编号
     * @return 租户
     */
    SysTenantPO getTenant(String id);

    /**
     * 获得租户分页
     *
     * @param pageReqVO 分页查询
     * @return 租户分页
     */
    PageResult<SysTenantPO> getTenantPage(TenantPageReqVO pageReqVO);

    /**
     * 获得租户列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 租户列表
     */
    List<SysTenantPO> getTenantList(TenantExportReqVO exportReqVO);

    /**
     * 获得名字对应的租户
     *
     * @param name 组户名
     * @return 租户
     */
    SysTenantPO getTenantByName(String name);

    /**
     * 获得使用指定套餐的租户数量
     *
     * @param packageId 租户套餐编号
     * @return 租户数量
     */
    Long getTenantCountByPackageId(String packageId);

    /**
     * 获得使用指定套餐的租户数组
     *
     * @param packageId 租户套餐编号
     * @return 租户数组
     */
    List<SysTenantPO> getTenantListByPackageId(String packageId);

    /**
     * 进行租户的信息处理逻辑
     * 其中，租户编号从 {@link TenantContextHolder} 上下文中获取
     *
     * @param handler 处理器
     */
    void handleTenantInfo(TenantInfoHandler handler);

    /**
     * 进行租户的菜单处理逻辑
     * 其中，租户编号从 TenantContextHolder 上下文中获取
     *
     * @param handler 处理器
     */
    void handleTenantMenu(TenantMenuHandler handler);

    /**
     * 获得所有租户
     *
     * @return 租户编号数组
     */
    List<String> getTenantIdList();

    /**
     * 校验租户是否合法
     *
     * @param id 租户编号
     */
    void validTenant(String id);
}
