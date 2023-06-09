package com.rc.cloud.app.system.service.oauth2;

import cn.hutool.core.util.IdUtil;
import com.rc.cloud.app.system.mapper.oauth2.OAuth2CodeMapper;
import com.rc.cloud.app.system.model.oauth2.OAuth2CodeDO;
import com.rc.cloud.common.core.util.date.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static com.rc.cloud.app.system.enums.ErrorCodeConstants.OAUTH2_CODE_EXPIRE;
import static com.rc.cloud.app.system.enums.ErrorCodeConstants.OAUTH2_CODE_NOT_EXISTS;
import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * OAuth2.0 授权码 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class OAuth2CodeServiceImpl implements OAuth2CodeService {

    /**
     * 授权码的过期时间，默认 5 分钟
     */
    private static final Integer TIMEOUT = 5 * 60;

    @Resource
    private OAuth2CodeMapper oauth2CodeMapper;

    @Override
    public OAuth2CodeDO createAuthorizationCode(Long userId, Integer userType, String clientId,
                                                List<String> scopes, String redirectUri, String state) {
        OAuth2CodeDO oAuth2CodeDO = new OAuth2CodeDO();
        oAuth2CodeDO.setCode(generateCode());
        oAuth2CodeDO.setUserId(userId);
        oAuth2CodeDO.setUserType(userType);
        oAuth2CodeDO.setClientId(clientId);
        oAuth2CodeDO.setScopes(scopes);
        oAuth2CodeDO.setExpiresTime(LocalDateTime.now().plusSeconds(TIMEOUT));
        oAuth2CodeDO.setRedirectUri(redirectUri);
        oAuth2CodeDO.setState(state);
        OAuth2CodeDO codeDO = oAuth2CodeDO;
        oauth2CodeMapper.insert(codeDO);
        return codeDO;
    }

    @Override
    public OAuth2CodeDO consumeAuthorizationCode(String code) {
        OAuth2CodeDO codeDO = oauth2CodeMapper.selectByCode(code);
        if (codeDO == null) {
            throw exception(OAUTH2_CODE_NOT_EXISTS);
        }
        if (DateUtils.isExpired(codeDO.getExpiresTime())) {
            throw exception(OAUTH2_CODE_EXPIRE);
        }
        oauth2CodeMapper.deleteById(codeDO.getId());
        return codeDO;
    }

    private static String generateCode() {
        return IdUtil.fastSimpleUUID();
    }

}
