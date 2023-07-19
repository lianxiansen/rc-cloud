package com.rc.cloud.app.system.controller.admin.v1.logger;

import com.rc.cloud.app.system.convert.logger.LoginLogConvert;
import com.rc.cloud.app.system.model.logger.LoginLogPO;
import com.rc.cloud.app.system.service.logger.LoginLogService;
import com.rc.cloud.app.system.vo.logger.loginlog.LoginLogPageReqVO;
import com.rc.cloud.app.system.vo.logger.loginlog.LoginLogRespVO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author rc@hqf
 * @date 2023/07/19
 * @description 登录日志 Controller
 */
@Tag(name = "管理后台 - 登录日志")
@RestController
@RequestMapping("/admin/v1/login-log")
@Validated
public class LoginLogController {

    @Resource
    private LoginLogService loginLogService;

    @GetMapping("/page")
    @Operation(summary = "获得登录日志分页列表")
//    @PreAuthorize("@ss.hasPermission('system:login-log:query')")
    public CodeResult<PageResult<LoginLogRespVO>> getLoginLogPage(@Valid LoginLogPageReqVO reqVO) {
        PageResult<LoginLogPO> page = loginLogService.getLoginLogPage(reqVO);
        return CodeResult.ok(LoginLogConvert.INSTANCE.convertPage(page));
    }
}
