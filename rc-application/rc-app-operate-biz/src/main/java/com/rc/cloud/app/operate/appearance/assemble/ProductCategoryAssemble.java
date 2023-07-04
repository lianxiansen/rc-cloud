package com.rc.cloud.app.operate.appearance.assemble;

import com.rc.cloud.app.operate.appearance.vo.ProductCategoryVO;
import com.rc.cloud.app.operate.application.bo.ProductCategoryBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductCategoryAssemble {
    ProductCategoryAssemble INSTANCE = Mappers.getMapper(ProductCategoryAssemble.class);

    ProductCategoryVO convert2ProductCategoryVO(ProductCategoryBO a);
}
