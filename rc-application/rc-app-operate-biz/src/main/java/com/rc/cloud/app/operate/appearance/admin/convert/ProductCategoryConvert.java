package com.rc.cloud.app.operate.appearance.admin.convert;

import com.rc.cloud.app.operate.appearance.admin.res.ProductCategoryResponse;
import com.rc.cloud.app.operate.application.bo.ProductCategoryBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductCategoryConvert {
    ProductCategoryConvert INSTANCE = Mappers.getMapper(ProductCategoryConvert.class);

    ProductCategoryResponse convert2ProductCategoryVO(ProductCategoryBO a);
}
