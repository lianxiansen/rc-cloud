package com.rc.cloud.app.operate.appearance.convert;

import com.rc.cloud.app.operate.appearance.vo.BrandVO;
import com.rc.cloud.app.operate.application.bo.BrandBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BrandConvert {
    BrandConvert INSTANCE = Mappers.getMapper(BrandConvert.class);

    BrandVO convert2BrandVO(BrandBO a);
}
