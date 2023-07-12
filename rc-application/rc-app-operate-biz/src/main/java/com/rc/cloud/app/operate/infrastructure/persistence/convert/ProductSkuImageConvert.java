package com.rc.cloud.app.operate.infrastructure.persistence.convert;

import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuImage;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductSkuImagePO;
import org.mapstruct.factory.Mappers;

import java.util.List;

public interface ProductSkuImageConvert {

    ProductSkuImageConvert INSTANCE = Mappers.getMapper(ProductSkuImageConvert.class);

    ProductSkuImage convert(ProductSkuImagePO productImagePO);

    ProductSkuImagePO convert(ProductSkuImage productImage);

    List<ProductSkuImage> convertList(List<ProductSkuImagePO> list);
}
