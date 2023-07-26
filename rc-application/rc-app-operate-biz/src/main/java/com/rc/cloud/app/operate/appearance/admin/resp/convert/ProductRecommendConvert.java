package com.rc.cloud.app.operate.appearance.admin.resp.convert;

import com.rc.cloud.app.operate.appearance.admin.resp.ProductRecommendResponse;
import com.rc.cloud.app.operate.application.bo.ProductRecommendBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductRecommendConvert {
    ProductRecommendConvert INSTANCE = Mappers.getMapper(ProductRecommendConvert.class);

    ProductRecommendResponse convert2ProductRecommendResponse(ProductRecommendBO a);
}
