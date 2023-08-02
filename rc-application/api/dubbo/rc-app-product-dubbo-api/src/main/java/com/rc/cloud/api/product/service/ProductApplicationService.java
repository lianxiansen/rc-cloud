package com.rc.cloud.api.product.service;

import com.rc.cloud.api.product.bo.ProductBO;
import com.rc.cloud.api.product.bo.ProductRemoveBO;
import com.rc.cloud.api.product.dto.ProductListQueryDTO;
import com.rc.cloud.api.product.dto.ProductQueryDTO;
import com.rc.cloud.api.product.dto.ProductRemoveDTO;
import com.rc.cloud.api.product.dto.ProductSaveDTO;
import com.rc.cloud.common.core.pojo.PageResult;

/**
 * @ClassName: ProductService
 * @Author: liandy
 * @Date: 2023/6/23 15:51
 * @Description:
 */
public interface ProductApplicationService {



    /**
     * 创建商品
     *
     *
     * @param productSaveDTO
     * @return
     */
     ProductBO createProduct(ProductSaveDTO productSaveDTO);
    /**
     * 修改商品
     *
     * @param productSaveDTO
     * @return
     */
    ProductBO updateProduct(ProductSaveDTO productSaveDTO);


    /**
     * 移除商品
     * @param productRemoveDTO
     * @return
     */
    ProductRemoveBO removeProductBatch(ProductRemoveDTO productRemoveDTO);
    /**
     * 获取商品
     * TODO
     * @param productQueryDTO
     * @return
     */
    ProductBO getProduct(ProductQueryDTO productQueryDTO);

    /**
     * 获取商品列表
     *
     * @return
     */
     PageResult<ProductBO> getProductList(ProductListQueryDTO query);

     int changeNewStatus(String productId, boolean newFlag);


     int changeOnShelfStatus(String productId, int onShelfStatus);

     int changePublicStatus(String productId, boolean publicFlag);

     int changeRecommendStatus(String productId, boolean recommendFlag);


}
