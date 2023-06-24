package com.rc.cloud.app.product.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.rc.cloud.app.product.appearance.request.ProductListVO;
import com.rc.cloud.app.product.appearance.request.ProductRequestVO;
import com.rc.cloud.app.product.application.data.*;
import com.rc.cloud.app.product.infrastructure.persistence.po.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author:chenjianxiang
 * @Date 2021/2/8
 * @Description:
 */
public interface ProductMapper extends BaseMapper<Product> {

    List<Product> getProductPageListInAdmin(@Param("query") ProductListVO query);

    List<Product> getNoDeliveryTemplateProductList(@Param("query") NoDeliveryTemplateQuery query);

    List<Product> getSimiliarProductList(@Param("query") SimiliarProductQuery query);

    List<Product> getProductList(@Param("query") SimiliarProductQuery query);

    List<AdminCopartnerProductDTO> getCopartnerProductList(@Param("query") BaseQuery query);

    List<AdminProductListDTO> getAdminProductList(@Param("query") ProductRequestVO query);

    Boolean checkProductIsRefund(@Param("product_id") int productId);

    Boolean checkProductIsDeliveryRefund(@Param("product_id") int productId);
}
