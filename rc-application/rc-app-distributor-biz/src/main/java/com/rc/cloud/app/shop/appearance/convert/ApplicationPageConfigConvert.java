package com.rc.cloud.app.shop.appearance.convert;

import com.rc.cloud.app.shop.appearance.req.ApplicationPageConfigCreateReqVO;
import com.rc.cloud.app.shop.appearance.req.ApplicationPageConfigPublishReqVO;
import com.rc.cloud.app.shop.appearance.req.ApplicationPageConfigSaveReqVO;
import com.rc.cloud.app.shop.appearance.resp.ApplicationPageConfigDataRespVO;
import com.rc.cloud.app.shop.appearance.resp.ApplicationPageConfigRespVO;
import com.rc.cloud.app.shop.application.dto.ApplicationPageConfigCreateDTO;
import com.rc.cloud.app.shop.application.dto.ApplicationPageConfigPublishDTO;
import com.rc.cloud.app.shop.application.dto.ApplicationPageConfigSaveDTO;
import com.rc.cloud.app.shop.infrastructure.persistence.po.ApplicationPageConfigPO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author WJF
 * @create 2023-07-21 14:35
 * @description TODO
 */

@Mapper
public interface ApplicationPageConfigConvert {

    ApplicationPageConfigConvert INSTANCE = Mappers.getMapper(ApplicationPageConfigConvert.class);

    ApplicationPageConfigCreateDTO convert(ApplicationPageConfigCreateReqVO reqVO);

    ApplicationPageConfigSaveDTO convert(ApplicationPageConfigSaveReqVO reqVO);

    ApplicationPageConfigPublishDTO convert(ApplicationPageConfigPublishReqVO reqVO);

    ApplicationPageConfigDataRespVO convert(ApplicationPageConfigPO po);

    List<ApplicationPageConfigRespVO> convertList(List<ApplicationPageConfigPO> poList);
}
