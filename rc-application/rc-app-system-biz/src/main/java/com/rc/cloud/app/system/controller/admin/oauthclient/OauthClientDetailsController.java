package com.rc.cloud.app.system.controller.admin.oauthclient;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rc.cloud.app.system.api.oauthclient.vo.SysOauthClientDetailsVO;
import com.rc.cloud.app.system.convert.client.OAuthClientDetailsConvert;
import com.rc.cloud.app.system.model.oauthclient.SysOauthClientDetailsPO;
import com.rc.cloud.app.system.service.oauthclient.OauthClientDetailsService;
import com.rc.cloud.app.system.vo.client.OauthClientDetailsCreateReqVO;
import com.rc.cloud.app.system.vo.client.OauthClientDetailsPageReqVO;
import com.rc.cloud.app.system.vo.client.OauthClientDetailsRespVO;
import com.rc.cloud.app.system.vo.client.OauthClientDetailsUpdateReqVO;
import com.rc.cloud.app.system.vo.user.user.UserUpdateReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.security.annotation.Inner;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
@Validated
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class OauthClientDetailsController {

	private final OauthClientDetailsService oauthClientDetailsService;

	/**
	 * 简单分页查询
	 * @param reqVO 分页参数
	 * @return
	 */
	@GetMapping("/page")
	public CodeResult<PageResult<OauthClientDetailsRespVO>> getOauthClientDetailsPage(@Validated OauthClientDetailsPageReqVO reqVO) {
		return CodeResult.ok(
				OAuthClientDetailsConvert.INSTANCE.convertPage(
						oauthClientDetailsService.getPage(reqVO))
		);
	}

	/**
	 * 通过ID查询
	 * @param id id
	 * @return SysOauthClientDetails
	 */
	@GetMapping("/{id}")
	public CodeResult<OauthClientDetailsRespVO> getByClientId(@PathVariable String id) {
		SysOauthClientDetailsPO sysOauthClientDetailsPO = oauthClientDetailsService.getById(id);
		return CodeResult.ok(OAuthClientDetailsConvert.INSTANCE.convert(sysOauthClientDetailsPO));
	}



	/**
	 * 添加
	 * @param reqVO OauthClientDetailsCreateReqVO
	 * @return success/false
	 */
//	@SysLog("添加终端")
	@PostMapping("create")
//	@PreAuthorize("@pms.hasPermission('sys_client_add')")
	public CodeResult<String> create(@Valid @RequestBody OauthClientDetailsCreateReqVO reqVO) {
		String id = oauthClientDetailsService.create(reqVO);
		return CodeResult.ok(id);
	}

	/**
	 * 删除
	 * @param id clientID
	 * @return success/false
	 */
//	@SysLog("删除终端")
	@DeleteMapping()
//	@PreAuthorize("@pms.hasPermission('sys_client_del')")
	@Parameter(name = "id", description = "编号", required = true, example = "1024")
	public CodeResult<Boolean> removeById(@RequestParam("id") String id) {
		oauthClientDetailsService.removeClientDetailsById(id);
		return CodeResult.ok(true);
	}

	/**
	 * 编辑
	 * @param reqVO OauthClientDetailsUpdateReqVO
	 * @return success/false
	 */
//	@SysLog("编辑终端")
	@PutMapping("update")
	@Operation(summary = "修改客户端")
//	@PreAuthorize("@pms.hasPermission('sys_client_edit')")
	public CodeResult<Boolean> update(@Valid @RequestBody OauthClientDetailsUpdateReqVO reqVO) {
		oauthClientDetailsService.update(reqVO);
		return CodeResult.ok(true);
	}

////	@SysLog("清除终端缓存")
//	@DeleteMapping("/cache")
////	@PreAuthorize("@pms.hasPermission('sys_client_del')")
//	public CodeResult clearClientCache() {
//		oauthClientDetailsService.clearClientCache();
//		return CodeResult.ok();
//	}

	@Inner
	@GetMapping("/getClientDetailsById/{clientId}")
	public CodeResult<SysOauthClientDetailsVO> getClientDetailsById(@PathVariable String clientId) {
		SysOauthClientDetailsPO sysOauthClientDetailsPO = oauthClientDetailsService.getOne(
				Wrappers.<SysOauthClientDetailsPO>lambdaQuery().eq(SysOauthClientDetailsPO::getClientId, clientId));
		SysOauthClientDetailsVO sysOauthClientDetailsVO = new SysOauthClientDetailsVO();
		BeanUtils.copyProperties(sysOauthClientDetailsPO, sysOauthClientDetailsVO);
		return CodeResult.ok(sysOauthClientDetailsVO);
	}
}
