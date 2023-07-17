package com.rc.cloud.app.operate.appearance.convert;

import com.rc.cloud.app.operate.appearance.vo.ProductGroupVO;
import com.rc.cloud.app.operate.application.bo.ProductGroupBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductGroupConvert {
    ProductGroupConvert INSTANCE = Mappers.getMapper(ProductGroupConvert.class);

    ProductGroupVO convert2ProductGroupVO(ProductGroupBO a);
}
