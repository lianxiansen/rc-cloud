package com.rc.cloud.app.operate.infrastructure.persistence.convert;

import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuAttribute;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductSkuAttributePO;
import org.mapstruct.factory.Mappers;

import java.util.List;

public interface ProductSkuAttributeConvert {

    ProductSkuAttributeConvert INSTANCE = Mappers.getMapper(ProductSkuAttributeConvert.class);

    ProductSkuAttribute convert(ProductSkuAttributePO productAttributePO);

    ProductSkuAttributePO convert(ProductSkuAttribute productAttribute);

    List<ProductSkuAttribute> convertList(List<ProductSkuAttributePO> list);
}
