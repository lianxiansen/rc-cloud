package com.rc.cloud.app.operate.appearance.assemble;

import com.rc.cloud.app.operate.appearance.request.ProductCategoryCreateRequest;
import com.rc.cloud.app.operate.application.data.ProductCategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductCategoryAssemble {
    ProductCategoryAssemble INSTANCE = Mappers.getMapper(ProductCategoryAssemble.class);

    ProductCategoryDTO convert2ProductCategoryDTO(ProductCategoryCreateRequest source);


    ProductCategoryCreateRequest convert2ProductCategoryVO(ProductCategoryDTO source);
}
