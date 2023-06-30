package com.rc.cloud.app.operate.appearance.assemble;

import com.rc.cloud.app.operate.appearance.request.ProductCategoryVO;
import com.rc.cloud.app.operate.application.data.ProductCategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductCategoryAssemble {
    ProductCategoryAssemble INSTANCE = Mappers.getMapper(ProductCategoryAssemble.class);

    @Mapping(source = "id",target = "id")
    ProductCategoryDTO convert2ProductCategoryDTO(ProductCategoryVO source);


    ProductCategoryVO convert2ProductCategoryVO(ProductCategoryDTO source);
}
