package com.rc.cloud.app.operate.appearance.assemble;

import com.rc.cloud.app.operate.appearance.vo.BrandVO;
import com.rc.cloud.app.operate.application.bo.BrandBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BrandAssemble {
    BrandAssemble INSTANCE = Mappers.getMapper(BrandAssemble.class);

    BrandVO convert2BrandVO(BrandBO a);
}
