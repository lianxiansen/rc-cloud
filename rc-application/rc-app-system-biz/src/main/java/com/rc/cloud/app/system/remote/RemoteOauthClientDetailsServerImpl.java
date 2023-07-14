package com.rc.cloud.app.system.remote;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rc.cloud.app.system.api.oauthclient.vo.SysOauthClientDetailsVO;
import com.rc.cloud.app.system.model.oauthclient.SysOauthClientDetailsPO;
import com.rc.cloud.app.system.service.oauthclient.OauthClientDetailsService;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.security.annotation.Inner;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 供客户端程调用
 */
@RestController
@RequestMapping("/sys/client")
@Validated
public class RemoteOauthClientDetailsServerImpl {

    @Resource
    private OauthClientDetailsService oauthClientDetailsService;

    /**
     * 根据客户端id查询客户端信息
     *
     * @param clientId 客户端id
     * @return 客户端信息
     */
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
