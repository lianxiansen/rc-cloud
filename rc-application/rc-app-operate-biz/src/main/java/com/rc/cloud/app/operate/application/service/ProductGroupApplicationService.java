package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.bo.ProductGroupBO;
import com.rc.cloud.app.operate.application.bo.ProductGroupItemBO;
import com.rc.cloud.app.operate.application.dto.ProductGroupCreateDTO;
import com.rc.cloud.app.operate.application.dto.ProductGroupItemCreateDTO;

import java.util.List;

public interface ProductGroupApplicationService {
    /**
     * 常见产品组合
     * @param productGroupCreateDTO
     * @return
     */
    ProductGroupBO create(ProductGroupCreateDTO productGroupCreateDTO);

    /**
     * 解除组合
     * @param id
     * @return
     */
    boolean release(String id);

    /**
     * 创建产品组合项
     * @param productGroupItemCreateDTO
     * @return
     */
    ProductGroupItemBO createItem(ProductGroupItemCreateDTO productGroupItemCreateDTO);

    List<ProductGroupBO> findList(String productId);
}
