package com.rc.cloud.app.operate.infrastructure.repository.persistence;

import cn.hutool.core.collection.CollectionUtil;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productimage.ProductImage;
import com.rc.cloud.app.operate.domain.model.productimage.ProductImageRepository;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.convert.ProductImageConvert;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.ProductImageMapper;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductImagePO;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Author: chenjianxiang
 * @Date: 2023/7/28
 * @Description: ProductImagePO 插入与修改不需要关心id，id需要自动生成，列表返回时候需要带上id
 * 如果其实属性一致就认定这个图片是独一无二的
 */
@Repository
public class ProductImageRepositoryImpl  implements ProductImageRepository {

    @Autowired
    private ProductImageMapper productImageMapper;

    /**
     * 通过商品id以及图片类型获取图片
     * @param productId
     * @param imageType
     * @return
     */
    public List<ProductImage> getProductImageByProductId(ProductId productId, Integer imageType) {
        LambdaQueryWrapperX<ProductImagePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductImagePO::getProductId, productId.id());
        wrapper.eq(ProductImagePO::getImageType, imageType);
        return ProductImageConvert.convertList(this.productImageMapper.selectList(wrapper));
    }

    /**
     * 获取图片
     * @param productImage
     */
    @Override
    public void insertProductImage(ProductImage productImage) {
        ProductImagePO productImagePO = ProductImageConvert.convert(productImage);
        this.productImageMapper.insert(productImagePO);
    }


    /**
     * 移除图片
     * 修改图片的时候一般不会带上图片的id所以
     * 不考虑图片的id，仅考虑图片的值
     * @param productId
     * @param url
     * @param sort
     * @param type
     * @return
     */
    public void removeProductImageByProductIdAndUrlAndSortAndType(ProductId productId,String url, int sort, int type) {
        LambdaQueryWrapperX<ProductImagePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductImagePO::getUrl, url);
        wrapper.eq(ProductImagePO::getSort, sort);
        wrapper.eq(ProductImagePO::getImageType, type);
        wrapper.eq(ProductImagePO::getProductId, productId.id());
        productImageMapper.delete(wrapper);
    }


    /**
     * 批量删除所有的商品图片
     * @param productId
     */
    public void deleteProductImageByProductId(ProductId productId) {
        LambdaQueryWrapperX<ProductImagePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductImagePO::getProductId, productId.id());
        productImageMapper.delete(wrapper);
    }


}
