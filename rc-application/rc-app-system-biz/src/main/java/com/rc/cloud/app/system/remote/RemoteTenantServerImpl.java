package com.rc.cloud.app.system.remote;

import com.rc.cloud.app.system.service.tenant.TenantService;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.security.annotation.Inner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 供多租户远程调用
 *
 * @author oliveoil
 * @date 2023-07-05
 */
@RestController
@RequestMapping("/sys/tenant")
public class RemoteTenantServerImpl {
    @Resource
    private TenantService tenantService;


    @Inner
    @GetMapping("/getTenantIdList")
    public CodeResult<List<String>> getTenantIdList() {
        return CodeResult.ok(tenantService.getTenantIdList());
    }


    @Inner
    @GetMapping("/validateTenant")
    public CodeResult validateTenant(String id) {
        tenantService.validTenant(id);
        return CodeResult.ok();
    }
}
