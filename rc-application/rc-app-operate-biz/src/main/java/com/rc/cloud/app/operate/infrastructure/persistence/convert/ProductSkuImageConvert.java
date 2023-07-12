package com.rc.cloud.app.operate.infrastructure.persistence.convert;

import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuImage;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductSkuImagePO;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

public interface ProductSkuImageConvert {

    ProductSkuImageConvert INSTANCE = Mappers.getMapper(ProductSkuImageConvert.class);

    @Mapping(source = "id",target = "id")
    @Mapping(source = "url",target = "url")
    @Mapping(source = "sortId",target = "sort")
    ProductSkuImage convert(ProductSkuImagePO productImagePO);

    @Mapping(source = "id",target = "id")
    @Mapping(source = "url",target = "url")
    @Mapping(source = "sort",target = "sortId")
    ProductSkuImagePO convert(ProductSkuImage productImage);

    List<ProductSkuImage> convertList(List<ProductSkuImagePO> list);
}
