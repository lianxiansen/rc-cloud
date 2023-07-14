package com.rc.cloud.app.system.remote;

import com.rc.cloud.app.system.service.tenant.TenantService;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.security.annotation.Inner;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 供多租户远程调用
 */
@RestController
@RequestMapping("/sys/tenant")
@Validated
public class RemoteTenantServerImpl {
    @Resource
    private TenantService tenantService;

    /**
     * 获取所有租户id
     *
     * @return 租户id列表
     */
    @Inner
    @GetMapping("/getTenantIdList")
    public CodeResult<List<String>> getTenantIdList() {
        return CodeResult.ok(tenantService.getTenantIdList());
    }

    /**
     * 校验租户是否存在
     *
     * @param id 租户id
     * @return 租户id列表
     */
    @Inner
    @GetMapping("/validateTenant")
    public CodeResult validateTenant(String id) {
        tenantService.validTenant(id);
        return CodeResult.ok();
    }
}
