package com.rc.cloud.app.operate.appearance.convert;

import com.rc.cloud.app.operate.appearance.vo.ProductCategoryVO;
import com.rc.cloud.app.operate.application.bo.ProductCategoryBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductCategoryConvert {
    ProductCategoryConvert INSTANCE = Mappers.getMapper(ProductCategoryConvert.class);

    ProductCategoryVO convert2ProductCategoryVO(ProductCategoryBO a);
}