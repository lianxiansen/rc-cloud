package com.rc.cloud.app.product.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qxun.qlive.common.base.BaseQuery;
import com.qxun.qlive.goods.model.Product;
import com.qxun.qlive.goods.model.dto.admin.AdminCopartnerProductDTO;
import com.qxun.qlive.goods.model.dto.admin.AdminProductListDTO;
import com.qxun.qlive.goods.model.vo.NoDeliveryTemplateQuery;
import com.qxun.qlive.goods.model.vo.ProductListVO;
import com.qxun.qlive.goods.model.vo.ProductRequestVO;
import com.qxun.qlive.goods.model.vo.SimiliarProductQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author:chenjianxiang
 * @Date 2021/2/8
 * @Description:
 */
public interface ProductMapper extends BaseMapper<Product> {

    List<Product> getProductPageListInAdmin(@Param("query")ProductListVO query);

    List<Product> getNoDeliveryTemplateProductList(@Param("query") NoDeliveryTemplateQuery query);

    List<Product> getSimiliarProductList(@Param("query") SimiliarProductQuery query);

    List<Product> getProductList(@Param("query") SimiliarProductQuery query);

    List<AdminCopartnerProductDTO> getCopartnerProductList(@Param("query") BaseQuery query);

    List<AdminProductListDTO> getAdminProductList(@Param("query") ProductRequestVO query);

    Boolean checkProductIsRefund(@Param("product_id") int productId);

    Boolean checkProductIsDeliveryRefund(@Param("product_id") int productId);
}
