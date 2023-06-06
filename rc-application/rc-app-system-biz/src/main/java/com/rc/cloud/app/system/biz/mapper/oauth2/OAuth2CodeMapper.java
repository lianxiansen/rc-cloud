package com.rc.cloud.app.system.biz.mapper.oauth2;

import com.rc.cloud.app.system.biz.model.oauth2.OAuth2CodeDO;
import com.rc.cloud.common.mybatis.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OAuth2CodeMapper extends BaseMapperX<OAuth2CodeDO> {

    default OAuth2CodeDO selectByCode(String code) {
        return selectOne(OAuth2CodeDO::getCode, code);
    }

}
