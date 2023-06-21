package com.rc.cloud.app.system.controller.admin.oauthclient;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rc.cloud.app.system.api.oauthclient.entity.SysOauthClientDetailsDO;
import com.rc.cloud.app.system.service.oauthclient.OauthClientDetailsService;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.security.annotation.Inner;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lengleng
 * @since 2018-05-15
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/client")
@Tag(name = "客户端管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class OauthClientDetailsController {

	private final OauthClientDetailsService oauthClientDetailsService;

	/**
	 * 通过ID查询
	 * @param clientId 客户端id
	 * @return SysOauthClientDetails
	 */
	@GetMapping("/{clientId}")
	public CodeResult<List<SysOauthClientDetailsDO>> getByClientId(@PathVariable String clientId) {
		return CodeResult.ok(oauthClientDetailsService
			.list(Wrappers.<SysOauthClientDetailsDO>lambdaQuery().eq(SysOauthClientDetailsDO::getClientId, clientId)));
	}

	/**
	 * 简单分页查询
	 * @param page 分页对象
	 * @param sysOauthClientDetails 系统终端
	 * @return
	 */
	@GetMapping("/page")
	public CodeResult<IPage<SysOauthClientDetailsDO>> getOauthClientDetailsPage(Page page,
			SysOauthClientDetailsDO sysOauthClientDetails) {
		return CodeResult.ok(oauthClientDetailsService.page(page, Wrappers.query(sysOauthClientDetails)));
	}

	/**
	 * 添加
	 * @param sysOauthClientDetails 实体
	 * @return success/false
	 */
//	@SysLog("添加终端")
	@PostMapping
//	@PreAuthorize("@pms.hasPermission('sys_client_add')")
	public CodeResult<Boolean> add(@Valid @RequestBody SysOauthClientDetailsDO sysOauthClientDetails) {
		return CodeResult.ok(oauthClientDetailsService.save(sysOauthClientDetails));
	}

	/**
	 * 删除
	 * @param id ID
	 * @return success/false
	 */
//	@SysLog("删除终端")
	@DeleteMapping("/{id}")
//	@PreAuthorize("@pms.hasPermission('sys_client_del')")
	public CodeResult<Boolean> removeById(@PathVariable String id) {
		return CodeResult.ok(oauthClientDetailsService.removeClientDetailsById(id));
	}

	/**
	 * 编辑
	 * @param sysOauthClientDetails 实体
	 * @return success/false
	 */
//	@SysLog("编辑终端")
	@PutMapping
//	@PreAuthorize("@pms.hasPermission('sys_client_edit')")
	public CodeResult<Boolean> update(@Valid @RequestBody SysOauthClientDetailsDO sysOauthClientDetails) {
		return CodeResult.ok(oauthClientDetailsService.updateClientDetailsById(sysOauthClientDetails));
	}

//	@SysLog("清除终端缓存")
	@DeleteMapping("/cache")
//	@PreAuthorize("@pms.hasPermission('sys_client_del')")
	public CodeResult clearClientCache() {
		oauthClientDetailsService.clearClientCache();
		return CodeResult.ok();
	}

	@Inner
	@GetMapping("/getClientDetailsById/{clientId}")
	public CodeResult getClientDetailsById(@PathVariable String clientId) {
		SysOauthClientDetailsDO sysOauthClientDetailsDO = oauthClientDetailsService.getOne(
				Wrappers.<SysOauthClientDetailsDO>lambdaQuery().eq(SysOauthClientDetailsDO::getClientId, clientId), false);
		return CodeResult.ok(sysOauthClientDetailsDO);
	}
}
