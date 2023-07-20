package com.rc.cloud.app.operate.appearance.admin.res.convert;

import com.rc.cloud.app.operate.appearance.admin.res.BrandResponse;
import com.rc.cloud.app.operate.application.bo.BrandBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BrandConvert {
    BrandConvert INSTANCE = Mappers.getMapper(BrandConvert.class);

    BrandResponse convert2BrandVO(BrandBO a);
}
