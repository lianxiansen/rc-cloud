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
    /**
     * 创建产品分类
     * @param productCreateCategoryDTO 产品分类创建request
     * @return 产品分类业务对象
     */
    ProductCategoryBO createProductCategory(ProductCategoryCreateDTO productCreateCategoryDTO);

    /**
     * 更新产品分类
     * @param productCategoryUpdateDTO 产品分类更新request
     * @return 产品分类业务对象
     */
    ProductCategoryBO updateProductCategory(ProductCategoryUpdateDTO productCategoryUpdateDTO);

    /**
     * 删除产品分类
     * @param id
     * @return
     */
    boolean removeProductCategory(String id);

    /**
     * 获取产品分类列表
     * @return
     */
    List<ProductCategoryBO> findProductCategorys();

    /**
     * 根据唯一标识查找产品分类
     * @param id
     * @return
     */
    ProductCategoryBO findProductCategoryById(String id);
}
