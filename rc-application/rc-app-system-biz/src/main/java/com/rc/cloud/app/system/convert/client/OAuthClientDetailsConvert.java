package com.rc.cloud.app.system.convert.client;

import com.rc.cloud.app.system.model.dept.SysPostPO;
import com.rc.cloud.app.system.model.oauthclient.SysOauthClientDetailsPO;
import com.rc.cloud.app.system.vo.client.OauthClientDetailsCreateReqVO;
import com.rc.cloud.app.system.vo.client.OauthClientDetailsRespVO;
import com.rc.cloud.app.system.vo.client.OauthClientDetailsUpdateReqVO;
import com.rc.cloud.app.system.vo.dept.post.PostRespVO;
import com.rc.cloud.common.core.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OAuthClientDetailsConvert {

    OAuthClientDetailsConvert INSTANCE = Mappers.getMapper(OAuthClientDetailsConvert.class);

    PageResult<OauthClientDetailsRespVO> convertPage(PageResult<SysOauthClientDetailsPO> page);

    OauthClientDetailsRespVO convert(SysOauthClientDetailsPO sysOauthClientDetailsPO);
    SysOauthClientDetailsPO convert(OauthClientDetailsCreateReqVO oauthClientDetailsCreateReqVO);
    SysOauthClientDetailsPO convert(OauthClientDetailsUpdateReqVO oauthClientDetailsUpdateReqVO);
}
