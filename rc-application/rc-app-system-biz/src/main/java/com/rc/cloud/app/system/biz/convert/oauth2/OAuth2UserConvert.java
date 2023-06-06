package com.rc.cloud.app.system.biz.convert.oauth2;

import com.rc.cloud.app.system.biz.model.dept.DeptDO;
import com.rc.cloud.app.system.biz.model.dept.PostDO;
import com.rc.cloud.app.system.biz.model.user.AdminUserDO;
import com.rc.cloud.app.system.biz.vo.oauth2.user.OAuth2UserInfoRespVO;
import com.rc.cloud.app.system.biz.vo.oauth2.user.OAuth2UserUpdateReqVO;
import com.rc.cloud.app.system.biz.vo.user.profile.UserProfileUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OAuth2UserConvert {

    OAuth2UserConvert INSTANCE = Mappers.getMapper(OAuth2UserConvert.class);

    OAuth2UserInfoRespVO convert(AdminUserDO bean);
    OAuth2UserInfoRespVO.Dept convert(DeptDO dept);
    List<OAuth2UserInfoRespVO.Post> convertList(List<PostDO> list);

    UserProfileUpdateReqVO convert(OAuth2UserUpdateReqVO bean);

}
