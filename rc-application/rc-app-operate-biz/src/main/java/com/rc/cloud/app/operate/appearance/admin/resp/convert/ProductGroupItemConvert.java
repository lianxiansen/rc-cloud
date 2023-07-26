package com.rc.cloud.app.operate.appearance.admin.resp.convert;

import com.rc.cloud.app.operate.appearance.admin.resp.ProductGroupItemResponse;
import com.rc.cloud.app.operate.application.bo.ProductGroupItemBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductGroupItemConvert {
    ProductGroupItemConvert INSTANCE = Mappers.getMapper(ProductGroupItemConvert.class);

    ProductGroupItemResponse convert2ProductGroupItemVO(ProductGroupItemBO a);
}
