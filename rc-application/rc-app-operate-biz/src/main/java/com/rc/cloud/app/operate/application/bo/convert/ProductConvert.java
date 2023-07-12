package com.rc.cloud.app.operate.application.bo.convert;

import com.rc.cloud.app.operate.application.bo.ProductBO;
import com.rc.cloud.app.operate.application.dto.ProductSaveDTO;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductPO;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

public interface ProductConvert
{


    ProductConvert INSTANCE = Mappers.getMapper(ProductConvert.class);

    ProductBO convert(Product product);

    Product convert(ProductSaveDTO productSaveDTO);

    List<ProductBO> convertList(List<Product> productList);
}
