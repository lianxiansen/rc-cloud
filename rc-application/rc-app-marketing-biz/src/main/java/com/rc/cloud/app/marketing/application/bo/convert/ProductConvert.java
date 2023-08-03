package com.rc.cloud.app.marketing.application.bo.convert;

import com.rc.cloud.api.product.bo.ProductBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author WJF
 * @create 2023-08-02 16:09
 * @description TODO
 */

@Mapper
public interface ProductConvert {

    ProductConvert INSTANCE= Mappers.getMapper(ProductConvert.class);

}
