package com.rc.cloud.app.system.mapper.oauthclient;

import com.rc.cloud.app.system.model.oauthclient.SysOauthClientDetailsPO;
import com.rc.cloud.app.system.vo.client.OauthClientDetailsPageReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 客户端信息mapper
 */
@Mapper
public interface OauthClientDetailsMapper extends BaseMapperX<SysOauthClientDetailsPO> {

    /**
     * 分页查询
     *
     * @param reqVO 分页查询参数
     * @return 分页查询结果
     */
    default PageResult<SysOauthClientDetailsPO> selectPage(OauthClientDetailsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysOauthClientDetailsPO>()
                .likeIfPresent(SysOauthClientDetailsPO::getClientId, reqVO.getClientId())
                .eqIfPresent(SysOauthClientDetailsPO::getScope, reqVO.getScope())
                .eqIfPresent(SysOauthClientDetailsPO::getAuthorizedGrantTypes, reqVO.getAuthorizedGrantTypes())
                .eqIfPresent(SysOauthClientDetailsPO::getAutoapprove, reqVO.getAutoapprove())
                .orderByDesc(SysOauthClientDetailsPO::getCreateTime));
    }

    /**
     * 根据clientId查询
     *
     * @param clientId clientId
     * @return 查询结果
     */
    default SysOauthClientDetailsPO selectByClientId(String clientId) {
        return selectOne(new LambdaQueryWrapperX<SysOauthClientDetailsPO>()
                .eq(SysOauthClientDetailsPO::getClientId, clientId));
    }
}
