package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.bo.ProductRecommendBO;
import com.rc.cloud.app.operate.application.dto.ProductRecommendCreateDTO;

import java.util.List;

public interface ProductRecommendApplicationService {
    ProductRecommendBO create(ProductRecommendCreateDTO productRecommendCreateDTO);

    /**
     * 取消推荐
     * @param id 产品推荐唯一标识
     * @return
     */
    boolean release(String id);

    List<ProductRecommendBO> findList(String productId);
}
