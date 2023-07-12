package com.rc.cloud.app.system.mapper.oauthclient;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rc.cloud.app.system.model.dept.SysPostPO;
import com.rc.cloud.app.system.model.oauthclient.SysOauthClientDetailsPO;
import com.rc.cloud.app.system.vo.client.OauthClientDetailsPageReqVO;
import com.rc.cloud.app.system.vo.dept.post.PostPageReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author rc@hqf
 * @since 2023-07-11
 */
@Mapper
public interface OauthClientDetailsMapper extends BaseMapperX<SysOauthClientDetailsPO> {

    default PageResult<SysOauthClientDetailsPO> selectPage(OauthClientDetailsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysOauthClientDetailsPO>()
                .likeIfPresent(SysOauthClientDetailsPO::getClientId, reqVO.getClientId())
                .eqIfPresent(SysOauthClientDetailsPO::getScope, reqVO.getScope())
                .eqIfPresent(SysOauthClientDetailsPO::getAuthorizedGrantTypes, reqVO.getAuthorizedGrantTypes())
                .eqIfPresent(SysOauthClientDetailsPO::getAutoapprove, reqVO.getAutoapprove())
                .orderByDesc(SysOauthClientDetailsPO::getCreateTime));
    }

    default SysOauthClientDetailsPO selectByClientId(String clientId) {
        return selectOne(new LambdaQueryWrapperX<SysOauthClientDetailsPO>()
                .eq(SysOauthClientDetailsPO::getClientId, clientId));
    }
}
