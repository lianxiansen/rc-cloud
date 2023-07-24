package com.rc.cloud.app.shop.application.convert;

import com.rc.cloud.app.shop.application.dto.ApplicationPageConfigCreateDTO;
import com.rc.cloud.app.shop.application.dto.ApplicationPageConfigPublishDTO;
import com.rc.cloud.app.shop.application.dto.ApplicationPageConfigSaveDTO;
import com.rc.cloud.app.shop.infrastructure.persistence.po.ApplicationPageConfigPO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author WJF
 * @create 2023-07-21 14:35
 * @description TODO
 */

@Mapper
public interface ApplicationPageConfigDTOConvert {

    ApplicationPageConfigDTOConvert INSTANCE = Mappers.getMapper(ApplicationPageConfigDTOConvert.class);

    ApplicationPageConfigPO convert(ApplicationPageConfigCreateDTO reqVO);

    ApplicationPageConfigPO convert(ApplicationPageConfigSaveDTO reqVO);

    ApplicationPageConfigPO convert(ApplicationPageConfigPublishDTO reqVO);


}
