package com.rc.cloud.app.operate.appearance.assemble;

import com.rc.cloud.app.operate.application.dto.ProductCategoryCreateDTO;
import com.rc.cloud.app.operate.application.dto.ProductCategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductCategoryAssemble {
    ProductCategoryAssemble INSTANCE = Mappers.getMapper(ProductCategoryAssemble.class);

    ProductCategoryDTO convert2ProductCategoryDTO(ProductCategoryCreateDTO source);


    ProductCategoryCreateDTO convert2ProductCategoryVO(ProductCategoryDTO source);
}
