package com.rc.cloud.app.system.controller.admin.v1.permission;

import com.rc.cloud.app.system.convert.permission.MenuConvert;
import com.rc.cloud.app.system.enums.permission.MenuTypeEnum;
import com.rc.cloud.app.system.model.permission.SysMenuPO;
import com.rc.cloud.app.system.model.user.SysUserPO;
import com.rc.cloud.app.system.service.permission.MenuService;
import com.rc.cloud.app.system.service.user.AdminUserService;
import com.rc.cloud.app.system.vo.permission.menu.*;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.core.util.tree.TreeUtil;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.security.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

import static com.rc.cloud.app.system.enums.ErrorCodeConstants.USER_NOT_EXISTS;
import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 菜单管理
 */
@Tag(name = "管理后台 - 菜单")
@RestController
@RequestMapping("/admin/menu")
@Validated
public class MenuController {

    @Resource
    private MenuService menuService;

    @Resource
    private AdminUserService userService;

    /**
     * 创建菜单
     *
     * @param reqVO 菜单信息
     * @return 菜单编号
     */
    @PostMapping("/create")
    @Operation(summary = "创建菜单")
    @PreAuthorize("@pms.hasPermission('sys:menu:create')")
    public CodeResult<String> createMenu(@Valid @RequestBody MenuCreateReqVO reqVO) {
        String menuId = menuService.createMenu(reqVO);
        return CodeResult.ok(menuId);
    }

    /**
     * 修改菜单
     *
     * @param reqVO 菜单信息
     * @return 是否成功
     */
    @PutMapping("/update")
    @Operation(summary = "修改菜单")
    @PreAuthorize("@pms.hasPermission('sys:menu:update')")
    public CodeResult<Boolean> updateMenu(@Valid @RequestBody MenuUpdateReqVO reqVO) {
        menuService.updateMenu(reqVO);
        return CodeResult.ok(true);
    }

    /**
     * 删除菜单
     *
     * @param id 菜单编号
     * @return 是否成功
     */
    @DeleteMapping()
    @Operation(summary = "删除菜单")
    @Parameter(name = "id", description = "角色编号", required = true, example = "1024")
    @PreAuthorize("@pms.hasPermission('sys:menu:delete')")
    public CodeResult<Boolean> deleteMenu(@RequestParam String id) {
        menuService.deleteMenu(id);
        return CodeResult.ok(true);
    }

    /**
     * 获取菜单列表
     *
     * @param reqVO 查询条件
     * @return 菜单列表
     */
    @GetMapping("/list")
    @Operation(summary = "获取菜单列表", description = "用于【菜单管理】界面")
    @PreAuthorize("@pms.hasPermission('sys:menu:query')")
    public CodeResult<List<MenuRespVO>> getMenuList(MenuListReqVO reqVO) {
        List<SysMenuPO> list = menuService.getMenuList(reqVO);
        list.sort(Comparator.comparing(SysMenuPO::getSort));
        return CodeResult.ok(TreeUtil.build(MenuConvert.INSTANCE.convertList(list)));
    }

    /**
     * 获取菜单列表
     *
     * @return 菜单列表
     */
    @GetMapping("/list-all-simple")
    @Operation(summary = "获取菜单精简信息列表", description = "只包含被开启的菜单，用于【角色分配菜单】功能的选项。在多租户的场景下，会只返回租户所在套餐有的菜单")
    public CodeResult<List<MenuSimpleRespVO>> getSimpleMenuList() {
        // 获得菜单列表，只要开启状态的
        MenuListReqVO reqVO = new MenuListReqVO();
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        List<SysMenuPO> list = menuService.getMenuListByTenant(reqVO);
        // 排序后，返回给前端
        list.sort(Comparator.comparing(SysMenuPO::getSort));
        return CodeResult.ok(MenuConvert.INSTANCE.convertList02(list));
    }

    /**
     * 根据id获取菜单信息
     *
     * @param id 菜单编号
     * @return 菜单信息
     */
    @GetMapping("/get/{id}")
    @Operation(summary = "获取菜单信息")
    @PreAuthorize("@pms.hasPermission('sys:menu:query')")
    public CodeResult<MenuRespVO> getMenu(@PathVariable String id) {
        SysMenuPO menu = menuService.getMenu(id);
        return CodeResult.ok(MenuConvert.INSTANCE.convert(menu));
    }

    /**
     * 获取根导航菜单
     *
     * @return 根导航菜单
     */
    @GetMapping("/root-nav")
    @Operation(summary = "获取根导航菜单")
    public CodeResult<List<MenuRespVO>> getRootNavMenuList() {
        String username = SecurityUtils.getUsername();
        Optional<SysUserPO> optionalByUsername = userService.findOptionalByUsername(username);
        SysUserPO user = optionalByUsername.orElseThrow(() -> exception(USER_NOT_EXISTS));
        List<SysMenuPO> list = menuService.getUsableUserMenuList(user.getId(), "0", MenuTypeEnum.DIR.getType());
        return CodeResult.ok(MenuConvert.INSTANCE.convertList(list));
    }

    /**
     * 根据父菜单ID获取子导航菜单
     *
     * @param parentId 父菜单ID
     * @return 子导航菜单
     */
    @GetMapping("/child-nav/{parentId}")
    @Operation(summary = "根据父菜单ID获取子导航菜单")
    public CodeResult<List<MenuRespVO>> getChildNavMenuList(@PathVariable String parentId) {
        String username = SecurityUtils.getUsername();
        Optional<SysUserPO> optionalByUsername = userService.findOptionalByUsername(username);
        SysUserPO user = optionalByUsername.orElseThrow(() -> exception(USER_NOT_EXISTS));

        List<SysMenuPO> list = menuService.getUsableUserMenuList(user.getId(), parentId, MenuTypeEnum.MENU.getType());
        if (list.isEmpty()) {
            return CodeResult.ok(new ArrayList<>());
        }
        List<MenuRespVO> result = MenuConvert.INSTANCE.convertList(list);
        result.forEach(item -> {
            List<SysMenuPO> userChildMenuList = menuService.getUsableUserMenuList(user.getId(), item.getId(), MenuTypeEnum.MENU.getType());
            item.setChildren(MenuConvert.INSTANCE.convertList(userChildMenuList));
        });
        return CodeResult.ok(result);
    }

    /**
     * 获取菜单导航
     *
     * @return 菜单导航
     */
    @GetMapping("nav")
    @Operation(summary = "获取菜单导航", description = "用于前端动态路由")
    public CodeResult<List<MenuRespVO>> getNav() {
        String username = SecurityUtils.getUsername();
        Optional<SysUserPO> optionalByUsername = userService.findOptionalByUsername(username);
        SysUserPO user = optionalByUsername.orElseThrow(() -> exception(USER_NOT_EXISTS));
        List<SysMenuPO> menuList = menuService.getUsableUserMenuList(user.getId(), null, MenuTypeEnum.MENU.getType());
        return CodeResult.ok(TreeUtil.build(MenuConvert.INSTANCE.convertList(menuList)));
    }

    /**
     * 获取登录用户的权限列表
     *
     * @return 权限列表
     */
    @GetMapping("/authority")
    @Operation(summary = "获取登录用户的权限列表")
    public CodeResult<Set<String>> getUserAuthority() {
        // 获取登录用户的ID
        String username = SecurityUtils.getUsername();
        Optional<SysUserPO> optionalByUsername = userService.findOptionalByUsername(username);
        SysUserPO user = optionalByUsername.orElseThrow(() -> exception(USER_NOT_EXISTS));
        Set<String> list = menuService.getUserAuthorityByUserId(user.getId());
        return CodeResult.ok(list);
    }
}
