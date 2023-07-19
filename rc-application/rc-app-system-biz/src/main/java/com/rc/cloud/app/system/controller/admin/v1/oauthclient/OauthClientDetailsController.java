package com.rc.cloud.app.system.controller.admin.v1.oauthclient;

import com.rc.cloud.app.system.convert.client.OAuthClientDetailsConvert;
import com.rc.cloud.app.system.model.oauthclient.SysOauthClientDetailsPO;
import com.rc.cloud.app.system.service.oauthclient.OauthClientDetailsService;
import com.rc.cloud.app.system.vo.client.OauthClientDetailsCreateReqVO;
import com.rc.cloud.app.system.vo.client.OauthClientDetailsPageReqVO;
import com.rc.cloud.app.system.vo.client.OauthClientDetailsRespVO;
import com.rc.cloud.app.system.vo.client.OauthClientDetailsUpdateReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 客户端管理
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/v1/client")
@Tag(name = "客户端管理模块")
@Validated
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class OauthClientDetailsController {

    private final OauthClientDetailsService oauthClientDetailsService;

    /**
     * 简单分页查询
     *
     * @param reqVO 分页参数
     * @return 分页结果
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
     *
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
     *
     * @param reqVO OauthClientDetailsCreateReqVO
     * @return success/false
     */
    // @SysLog("添加终端")
    @PostMapping("create")
    // @PreAuthorize("@pms.hasPermission('sys_client_add')")
    public CodeResult<String> create(@Valid @RequestBody OauthClientDetailsCreateReqVO reqVO) {
        String id = oauthClientDetailsService.create(reqVO);
        return CodeResult.ok(id);
    }

    /**
     * 删除
     *
     * @param id clientID
     * @return success/false
     */
    // @SysLog("删除终端")
    @DeleteMapping()
    // @PreAuthorize("@pms.hasPermission('sys_client_del')")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CodeResult<Boolean> removeById(@RequestParam("id") String id) {
        oauthClientDetailsService.removeClientDetailsById(id);
        return CodeResult.ok(true);
    }

    /**
     * 编辑
     *
     * @param reqVO OauthClientDetailsUpdateReqVO
     * @return success/false
     */
    // @SysLog("编辑终端")
    @PutMapping("update")
    @Operation(summary = "修改客户端")
    // @PreAuthorize("@pms.hasPermission('sys_client_edit')")
    public CodeResult<Boolean> update(@Valid @RequestBody OauthClientDetailsUpdateReqVO reqVO) {
        oauthClientDetailsService.update(reqVO);
        return CodeResult.ok(true);
    }

//// @SysLog("清除终端缓存")
// @DeleteMapping("/cache")
//// @PreAuthorize("@pms.hasPermission('sys_client_del')")
// public CodeResult clearClientCache() {
// oauthClientDetailsService.clearClientCache();
// return CodeResult.ok();
// }
}
