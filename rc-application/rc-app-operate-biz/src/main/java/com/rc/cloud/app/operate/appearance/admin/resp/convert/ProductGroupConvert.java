package com.rc.cloud.app.operate.appearance.admin.resp.convert;

import com.rc.cloud.app.operate.appearance.admin.resp.ProductGroupResponse;
import com.rc.cloud.app.operate.application.bo.ProductGroupBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductGroupConvert {
    ProductGroupConvert INSTANCE = Mappers.getMapper(ProductGroupConvert.class);

    ProductGroupResponse convert2ProductGroupVO(ProductGroupBO a);
}
