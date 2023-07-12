package com.rc.cloud.app.operate.infrastructure.persistence.convert;

import com.rc.cloud.app.operate.domain.model.product.ProductAttribute;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductAttributePO;
import org.mapstruct.factory.Mappers;

import java.util.List;

public interface ProductAttributeConvert {

    ProductAttributeConvert INSTANCE = Mappers.getMapper(ProductAttributeConvert.class);

    ProductAttribute convert(ProductAttributePO productAttributePO);

    ProductAttributePO convert(ProductAttribute productAttribute);

    List<ProductAttribute> convertList(List<ProductAttributePO> list);
    
}
