package com.rc.cloud.app.operate.infrastructure.repository.persistence.convert;

import com.rc.cloud.app.operate.domain.model.product.ProductImage;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductImagePO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductImageConvert {


    ProductImageConvert INSTANCE = Mappers.getMapper(ProductImageConvert.class);


    @Mapping(source = "id",target = "id.id")
    @Mapping(source = "url",target = "url")
    @Mapping(source = "sort",target = "sort")
    ProductImage convert(ProductImagePO productImagePO);

    @Mapping(source = "id.id",target = "id")
    @Mapping(source = "url",target = "url")
    @Mapping(source = "sort",target = "sort")
    ProductImagePO convert(ProductImage productImage);

    List<ProductImage> convertList(List<ProductImagePO> list);
}
