package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.bo.ProductCategoryBO;
import com.rc.cloud.app.operate.application.dto.ProductCategoryCreateDTO;
import com.rc.cloud.app.operate.application.dto.ProductCategoryUpdateDTO;

import java.util.List;
/**
 * @Author liandy
 * @Date 2023/7/24 9:22
 * @Description  产品分类应用服务
 * @Version 1.0
 */
public interface ProductCategoryApplicationService {
    ProductCategoryBO create(ProductCategoryCreateDTO productCreateCategoryDTO);

    ProductCategoryBO update(ProductCategoryUpdateDTO productCategoryUpdateDTO);

    boolean remove(String id);

    List<ProductCategoryBO> findAll();

    ProductCategoryBO findById(String id);
}
