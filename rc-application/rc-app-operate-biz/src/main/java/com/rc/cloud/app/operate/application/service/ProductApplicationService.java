package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.bo.ProductBO;
import com.rc.cloud.app.operate.application.bo.ProductRemoveBO;
import com.rc.cloud.app.operate.application.bo.ProductValidateBO;
import com.rc.cloud.app.operate.application.dto.*;
import com.rc.cloud.common.core.pojo.PageResult;

import java.util.List;

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
     * 校验商品是否存在
     * @param productValidateDTOs
     * @return
     */
    List<ProductValidateBO> validateProductList(List<ProductValidateDTO> productValidateDTOs);

    /**
     * 获取商品列表
     *
     * @return
     */
     PageResult<ProductBO> getProductList(ProductListQueryDTO query);

     int changeNewStatus(String productId, boolean newFlag);


     int changeOnshelfStatus(String productId, int onshelfStatus);

     int changePublicStatus(String productId, boolean publicFlag);

     int changeRecommendStatus(String productId, boolean recommendFlag);

    int changeExplosivesStatus(String productId, boolean explosivesFlag,String explosivesImage);


    void uploadPromotionImage(String productId, String promotionImage);
}
