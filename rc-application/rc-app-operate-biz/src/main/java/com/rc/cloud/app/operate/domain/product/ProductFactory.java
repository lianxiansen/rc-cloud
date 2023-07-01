package com.rc.cloud.app.operate.domain.product;

import com.rc.cloud.app.operate.domain.product.identifier.BrandId;
import com.rc.cloud.app.operate.domain.product.valobj.*;
import com.rc.cloud.app.operate.domain.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.tenant.service.TenantService;
import com.rc.cloud.app.operate.domain.tenant.valobj.TenantId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName: ProductFactory
 * @Author: liandy
 * @Date: 2023/6/24 09:56
 * @Description: TODO
 */
@Component
public class ProductFactory {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TenantService tenantService;
    public ProductAggregation createProduct(TenantId tenantId, Name name, Remark remark, Tag tag, BrandId brandId, ProductCategoryId productCategoryId,
                                            CustomClassification customClassification, Newest newest, Explosives explosives, Recommend recommend, Open open,
                                            OnshelfStatus onshelfStatus, Enable enable, Video video, MasterImage masterImage, Type type, List<Image> productImages){
        ProductId productId = productRepository.nextProductId();
        validateTenantId(tenantId);
//        validateProductCategoryId(productCategoryId);
        ProductAggregation productEntry=new ProductAggregation(productId,tenantId,name,productCategoryId);
        productEntry.setCustomClassification(customClassification);
        productEntry.setNewest(newest);
        productEntry.setExplosives(explosives);
        productEntry.setRecommend(recommend);
        productEntry.setOpen(open);
        //TODO 设置产品属性
        productImages.forEach(item->{
            addProductImage(productEntry,item);
        });
        return productEntry;
    }

//    private void validateProductCategoryId(ProductCategoryId productCategoryId){
//        if(!productCategoryExistService.execute(productCategoryId)){
//            throw new IllegalArgumentException("产品分类错误");
//        }
//    }

    private void validateTenantId(TenantId tenantId){
        if(!tenantService.exists(tenantId)){
            throw new IllegalArgumentException("所属租户错误");
        }
    }

    private void addProductImage(ProductAggregation productEntry, Image productImage){
        if(!productEntry.isEnable()){
            throw new RuntimeException("商品已失效");
        }
        ProductImageEntity productImageEntry=new ProductImageEntity(productRepository.nextProductImageId(),
                productEntry.getId(),productImage);
        productEntry.addProductImage(productImageEntry);
    }


}
