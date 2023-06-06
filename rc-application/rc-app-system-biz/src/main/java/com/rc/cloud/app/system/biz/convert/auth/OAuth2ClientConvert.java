package com.rc.cloud.app.system.biz.convert.auth;

import com.rc.cloud.app.system.biz.model.oauth2.OAuth2ClientDO;
import com.rc.cloud.app.system.biz.vo.oauth2.client.OAuth2ClientCreateReqVO;
import com.rc.cloud.app.system.biz.vo.oauth2.client.OAuth2ClientRespVO;
import com.rc.cloud.app.system.biz.vo.oauth2.client.OAuth2ClientUpdateReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * OAuth2 客户端 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface OAuth2ClientConvert {

    OAuth2ClientConvert INSTANCE = Mappers.getMapper(OAuth2ClientConvert.class);

    OAuth2ClientDO convert(OAuth2ClientCreateReqVO bean);

    OAuth2ClientDO convert(OAuth2ClientUpdateReqVO bean);

    OAuth2ClientRespVO convert(OAuth2ClientDO bean);

    List<OAuth2ClientRespVO> convertList(List<OAuth2ClientDO> list);

    PageResult<OAuth2ClientRespVO> convertPage(PageResult<OAuth2ClientDO> page);

}
