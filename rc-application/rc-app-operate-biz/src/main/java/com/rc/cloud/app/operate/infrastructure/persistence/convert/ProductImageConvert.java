package com.rc.cloud.app.operate.infrastructure.persistence.convert;

import com.rc.cloud.app.operate.domain.model.product.ProductImage;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductImagePO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductImageConvert {


    ProductImageConvert INSTANCE = Mappers.getMapper(ProductImageConvert.class);

    ProductImage convert(ProductImagePO productImagePO);

    ProductImagePO convert(ProductImage productImage);

    List<ProductImage> convertList(List<ProductImagePO> list);
}
