package com.rc.cloud.app.operate.domain.model.productimage;

import cn.hutool.core.collection.CollectionUtil;
import com.rc.cloud.app.operate.domain.common.ProductImageTypeEnum;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: chenjianxiang
 * @Date: 2023/8/3
 * @Description:
 */
@Service
public class ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    public void deleteProductImageByProductId(ProductId productId){
        productImageRepository.deleteProductImageByProductId(productId);
    }

    /**
     * 获取商品图片列表
     * @param productId
     * @return
     */
    public List<ProductImage> getProductMasterImageByProductId(ProductId productId){
        return productImageRepository.getProductImageByProductId(productId, ProductImageTypeEnum.MasterImage.value);
    }

    /**
     * 获取商品尺寸图片
     * @param productId
     * @return
     */
    public List<ProductImage> getProductSizeImageByProductId(ProductId productId){
        return productImageRepository.getProductImageByProductId(productId, ProductImageTypeEnum.SizeImage.value);
    }

    /**
     * 更新商品尺寸图片
     * @param productId
     * @param sizeImageList 尺寸图片
     */
    public void updateProductSizeImageList(ProductId productId,List<ProductImage> sizeImageList) {
        updateProductImageWithType(productId,sizeImageList,ProductImageTypeEnum.SizeImage);
    }

    /**
     * 更新商品主图图片
     * @param productId
     * @param masterImageList 主图图片
     */
    public void updateProductMasterImageList(ProductId productId,List<ProductImage> masterImageList) {
        updateProductImageWithType(productId,masterImageList,ProductImageTypeEnum.MasterImage);
    }

    /**
     * 当前传入的images可以为空
     * 如果为空则表示
     * @param productId
     * @param images
     * @param imageType
     */
    private void updateProductImageWithType(ProductId productId,List<ProductImage> images,ProductImageTypeEnum imageType){
        List<ProductImage> oriList = null;
        if(imageType==ProductImageTypeEnum.MasterImage){
            oriList = getProductMasterImageByProductId(productId);
        }else if(imageType==ProductImageTypeEnum.SizeImage){
            oriList = getProductSizeImageByProductId(productId);
        }
        List<ProductImage> addList = CollectionUtil.subtractToList(images, oriList);
        List<ProductImage> removeList = CollectionUtil.subtractToList(oriList, images);
        List<ProductImage> intersectionList =
                CollectionUtil.intersection(oriList,images).stream().collect(Collectors.toList());

        if(removeList!=null){
            for (ProductImage productImage : removeList) {
                productImageRepository.deleteProductImage(productImage.getId());
            }
        }
        batchInsertProductImage(addList);
        if(intersectionList!=null && intersectionList.size()>0){
            for (ProductImage productImage : intersectionList) {
                productImageRepository.updateProductImage(productImage);
            }
        }
    }



    public void batchInsertProductImage(List<ProductImage> productImageList) {
        if (productImageList != null && productImageList.size() > 0) {
            for (ProductImage productImage : productImageList) {
                this.productImageRepository.insertProductImage(productImage);
            }
        }
    }


    public void insertProductSizeImageList(List<ProductImage> productSizeImageList) {
        batchInsertProductImage(productSizeImageList);
    }

    public void insertProductMasterImageList(List<ProductImage> productMasterImageList) {
        batchInsertProductImage(productMasterImageList);
    }
}
