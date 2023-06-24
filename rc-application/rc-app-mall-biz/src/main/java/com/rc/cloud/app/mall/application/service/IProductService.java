package com.rc.cloud.app.mall.application.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.rc.cloud.app.mall.appearance.request.ProductListVO;
import com.rc.cloud.app.mall.appearance.request.ProductRequestVO;
import com.rc.cloud.app.mall.appearance.request.ProductSaveSortProductSortVO;
import com.rc.cloud.app.mall.application.data.*;
import com.rc.cloud.app.mall.infrastructure.persistence.po.*;

import java.util.List;

/**
 * @Author: chenjianxiang
 * @Date 2021/2/8
 * @Description:
 */
public interface IProductService extends IService<Product> {

    Product getProduct(int productId);

    Product getProductByAlibabaProductId(String fromAlibabaProductId);

    Product getProductByAlibabaProductId(String fromAlibabaProductId, Integer id);

    List<Product> getProductListByNoDeliveryTemplateId(int noDeliveryTemplateId);

    PageInfo<Product> getProductPageListInAdmin(ProductListVO query);

    PageInfo<AdminProductListDTO> getAdminProductList(ProductRequestVO query);

    List<Product> getSimiliarProductList(List<Integer> productIds);

    boolean saveOrUpdate(Product product);

    Integer saveProduct(Product product, Merchant merchant, WareHouse wareHouse, List<ProductAlbum> productAlbums, List<ProductCreateMaterial> productCreateMaterials, List<GoodsItemSpecificationSaveDTO> goodsSpecs, List<GoodsSkuSaveDTO> goodsSkus, ProductFreightCoupon productFreightCoupon, ProductFullCoupon productFullCoupon, int oriNoDeliveryTemplateID);

    Integer saveProduct(Product product);

    Boolean saveProductSort(List<ProductSaveSortProductSortVO> saveSortProductSortVOS);

    Boolean setProductRecommend(List<Integer> productIds, Boolean isRecommend);

    Boolean setProductShelfStatus(List<Integer> productIds, Boolean isOnShelf);

    Boolean setProductCheckStatus(List<Integer> productIds, Boolean isCheck);

    Boolean deleteGoods(List<Integer> productIds, Boolean isDelete);

    Boolean checkProductIsRefund(int productId);

    Boolean checkProductIsDeliveryRefund(int productId);


    void setProductRedis(Product product, Boolean isFreshOther);

    void setNoDeliveryProductListRedis(int noDeliveryTemplateId);

    void setProductSortRedis(List<ProductSaveSortProductSortVO> saveSortProductSortVOS);

    void setProductRecommendRedis(List<Integer> productIds, Boolean isRecommend);

    void setProductShelfStatusRedis(List<Integer> productIds, Boolean isOnShelf);

    void setProductDeleteRedis(List<Integer> productIds, Boolean isDelete);

    Product getProductRedis(int productId);

    Product getProductRedis(int productId, Boolean isSyncFromDbWhenNull);

    List<Product> getProductsRedis(List<Integer> productIds);

    List<Product> getProductsRedis(List<Integer> productIds, Boolean isSyncFromDbWhenNull);

    Long getProductRedisCount();

    ProductRedisCounter getProductRedisCounterRedis(int productId, Boolean isSyncFromDbWhenNull);

    void saveProductRedisCounterRedis(ProductRedisCounter model);
}
