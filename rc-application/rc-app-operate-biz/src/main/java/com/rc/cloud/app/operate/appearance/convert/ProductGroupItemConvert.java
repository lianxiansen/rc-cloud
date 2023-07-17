package com.rc.cloud.app.operate.appearance.convert;

import com.rc.cloud.app.operate.appearance.vo.ProductGroupItemVO;
import com.rc.cloud.app.operate.application.bo.ProductGroupItemBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductGroupItemConvert {
    ProductGroupItemConvert INSTANCE = Mappers.getMapper(ProductGroupItemConvert.class);

    ProductGroupItemVO convert2ProductGroupItemVO(ProductGroupItemBO a);
}
