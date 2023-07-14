package com.rc.cloud.app.system.convert.client;

import com.rc.cloud.app.system.model.oauthclient.SysOauthClientDetailsPO;
import com.rc.cloud.app.system.vo.client.OauthClientDetailsCreateReqVO;
import com.rc.cloud.app.system.vo.client.OauthClientDetailsRespVO;
import com.rc.cloud.app.system.vo.client.OauthClientDetailsUpdateReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 客户端信息转换类
 */
@Mapper
public interface OAuthClientDetailsConvert {

    OAuthClientDetailsConvert INSTANCE = Mappers.getMapper(OAuthClientDetailsConvert.class);

    /**
     * 分页查询结果转换
     *
     * @param page 分页查询结果
     * @return 分页查询结果
     */
    PageResult<OauthClientDetailsRespVO> convertPage(PageResult<SysOauthClientDetailsPO> page);

    /**
     * 查询结果转换
     *
     * @param sysOauthClientDetailsPO 查询结果
     * @return 查询结果
     */
    OauthClientDetailsRespVO convert(SysOauthClientDetailsPO sysOauthClientDetailsPO);

    /**
     * 创建请求参数转换
     *
     * @param oauthClientDetailsCreateReqVO 请求参数
     * @return 请求参数
     */
    SysOauthClientDetailsPO convert(OauthClientDetailsCreateReqVO oauthClientDetailsCreateReqVO);

    /**
     * 更新请求参数转换
     *
     * @param oauthClientDetailsUpdateReqVO 请求参数
     * @return 请求参数
     */
    SysOauthClientDetailsPO convert(OauthClientDetailsUpdateReqVO oauthClientDetailsUpdateReqVO);
}
