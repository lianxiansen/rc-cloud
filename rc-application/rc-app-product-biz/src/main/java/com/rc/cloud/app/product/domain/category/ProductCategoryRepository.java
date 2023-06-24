package com.rc.cloud.app.product.domain.category;

import com.rc.cloud.app.product.appearance.request.ProductCategorySaveVO;
import com.rc.cloud.app.product.appearance.request.ProductCategoryVO;
import com.rc.cloud.app.product.domain.category.valobj.*;
import com.rc.cloud.app.product.infrastructure.persistence.po.ProductCategory;

import java.util.List;

/**
 * @Author wangzhihao
 * @Date 2021-03-28
 * @Description:
 */
public interface ProductCategoryRepository{
    List<ProductCategoryEntry> getFirstList(ProductCategoryLocked locked, ProductCategoryLayer layer,ProductCategoryParent parent);

    ProductCategoryId nextId();
//    List<ProductCategory> getFirstList();

    //优惠券用到
    List<ProductCategory> getProductCategoryOtherList(ProductCategoryVO vo);

    //拿数量
    long getLowerCount(int id);

    //拿数量
//    long getFindCount(List<CategoryFindGoods> list, int id);

    //拿数量
    long getBannerCount(int id);

    //通用列表
    List<ProductCategory> getProductCategoryList(ProductCategoryVO vo);

    //根据category_id拿列表
    List<List<Integer>> getProductCategoryLevel(int category_id);

    //根据keyword拿列表
    List<List<Integer>> getProductCategoryLevel(String keyword);

    //一条
    ProductCategory getProductCategory(int id);

    int copyAndSaveProductCategory(ProductCategorySaveVO vo, ProductCategory model);

    int saveProductCategory(ProductCategory model);

    //显示隐藏
    int updateCheckStatus(String ids, boolean checkStatus);

    //更新排序
    int updateSortId(String ids, String checkStatus);

    //删除
    int removeProductCategory(String ids);

    //刷新缓存
    void refreshRedis(Object ids);

    //
    List<ProductCategory> getRedisList();


}
