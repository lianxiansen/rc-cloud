package com.rc.cloud.app.operate.domain.model.productimage;

import cn.hutool.core.collection.CollectionUtil;
import com.rc.cloud.app.operate.application.dto.ProductImageSaveDTO;
import com.rc.cloud.app.operate.domain.common.ProductImageTypeEnum;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Sort;
import com.rc.cloud.app.operate.domain.model.product.valobj.Url;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDictRepository;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.convert.ProductImageConvert;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductImagePO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductImageDomainService {

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
    private void updateProductSizeImageList(ProductId productId,List<ProductImage> sizeImageList) {
        updateProductImageWithType(productId,sizeImageList,ProductImageTypeEnum.MasterImage);
    }

    /**
     * 更新商品主图图片
     * @param productId
     * @param masterImageList 主图图片
     */
    private void updateProductMasterImageList(ProductId productId,List<ProductImage> masterImageList) {
        updateProductImageWithType(productId,masterImageList,ProductImageTypeEnum.MasterImage);
    }

    private void updateProductImageWithType(ProductId productId,List<ProductImage> images,ProductImageTypeEnum imageType){
        List<ProductImage> oriList = getProductMasterImageByProductId(productId);
        List<ProductImage> addList = CollectionUtil.subtractToList(images, oriList);
        List<ProductImage> removeList = CollectionUtil.subtractToList(oriList, images);
        if(removeList!=null){
            for (ProductImage productImage : removeList) {
                productImageRepository
                        .removeProductImageByProductIdAndUrlAndSortAndType(productImage.getProductId()
                                ,productImage.getUrl().getValue()
                                , productImage.getSort().getValue()
                                , imageType.value);

            }
        }
        batchInsertProductImage(addList);
    }



    public void batchInsertProductImage(List<ProductImage> productImageList) {
        if (productImageList != null && productImageList.size() > 0) {
            for (ProductImage productImage : productImageList) {
                this.productImageRepository.insertProductImage(productImage);
            }
        }
    }


    public void saveProductSizeImageList(List<ProductImage> productSizeImageList) {
        ProductId productId=null;
        if(productSizeImageList!=null && productSizeImageList.size()>0){
            productId =productSizeImageList.get(0).getProductId();
            updateProductSizeImageList(productId,productSizeImageList);
        }

    }

    public void saveProductMasterImageList(List<ProductImage> productMasterImageList) {
        ProductId productId=null;
        if(productMasterImageList!=null && productMasterImageList.size()>0){
            productId =productMasterImageList.get(0).getProductId();
            updateProductMasterImageList(productId,productMasterImageList);
        }
    }
}
