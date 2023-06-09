package com.rc.cloud.app.system.convert.auth;

import com.rc.cloud.app.system.api.oauth2.dto.OAuth2AccessTokenCheckRespDTO;
import com.rc.cloud.app.system.api.oauth2.dto.OAuth2AccessTokenRespDTO;
import com.rc.cloud.app.system.model.oauth2.OAuth2AccessTokenDO;
import com.rc.cloud.app.system.vo.oauth2.token.OAuth2AccessTokenRespVO;
import com.rc.cloud.common.core.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OAuth2TokenConvert {

    OAuth2TokenConvert INSTANCE = Mappers.getMapper(OAuth2TokenConvert.class);

    OAuth2AccessTokenCheckRespDTO convert(OAuth2AccessTokenDO bean);

    PageResult<OAuth2AccessTokenRespVO> convert(PageResult<OAuth2AccessTokenDO> page);

    OAuth2AccessTokenRespDTO convert2(OAuth2AccessTokenDO bean);

}
