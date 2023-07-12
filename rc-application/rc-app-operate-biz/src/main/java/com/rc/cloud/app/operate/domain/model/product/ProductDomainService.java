package com.rc.cloud.app.operate.domain.model.product;

import com.alibaba.fastjson.JSON;
import com.rc.cloud.app.operate.domain.common.ProductShelfStatusEnum;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductImage;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Explosives;
import com.rc.cloud.app.operate.domain.model.product.valobj.Newest;
import com.rc.cloud.app.operate.domain.model.product.valobj.OnshelfStatus;
import com.rc.cloud.app.operate.domain.model.product.valobj.Recommend;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategory;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.Enabled;
import com.rc.cloud.common.core.util.AssertUtils;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductDomainService {
   // public Product clone

    @Resource
    private ProductRepository productRepository;

    public  int createProduct(Product product){
        int flag=productRepository.insertProduct(product);
        if(product.getProductImages()!=null && product.getProductImages().size()!=0){
            productRepository.batchSaveProductImage(product.getProductImages());
        }
        if(product.getProductAttribute()!=null){
            productRepository.insertProductAttribute(product.getProductAttribute());
        }
        return flag;
    }

    public int updateProduct(Product product){
        int flag=0;
        flag =productRepository.updateProduct(product);
        //判断图片有没有更改
        if(judgeImageChange(product)) {
            //移除相册
            productRepository.removeProductImageByProductId(product.getId());
            if(product.getProductImages()!=null){
                productRepository.batchSaveProductImage(product.getProductImages());
            }
        }
        if(judgeAttributeChange(product)){
            productRepository.removeProductAttributeByProductId(product.getId());
            productRepository.insertProductAttribute(product.getProductAttribute());
        }
        return flag;
    }

    /**
     * 判断图片有没有更改
     * @param product
     * @return
     */
    private boolean judgeImageChange(Product product ){
        List<ProductImage> productImages = product.getProductImages();
        if(productImages==null){
            return true;
        }
        List<ProductImage> oriList = productRepository.getProductImageByProductId(product.getId());
        if(oriList==null){
            return true;
        }
        if(oriList.size()!=productImages.size()){
            return true;
        }
        for (ProductImage productImage : productImages) {
            ProductImage p = oriList.stream().filter(x -> x.getSort() == productImage.getSort()
                    && x.getUrl().equals(productImage.getUrl())).findFirst().orElse(null);
            if(p==null){
                return true;
            }
        }
        return false;
    }

    private boolean judgeAttributeChange(Product product){
        ProductAttribute productAttribute = product.getProductAttribute();
        ProductAttribute ori = productRepository.getProductAttributeByProductId(product.getId());
        String attr1 = JSON.toJSONString(productAttribute);
        String attr2 = JSON.toJSONString(ori.getAttributes());
        if(attr1.equals(attr2)){
            return false;
        }else{
            return true;
        }
    }


    public int onShelf(ProductId productId){
        Product product = productRepository.findById(productId);
        AssertUtils.notNull(product, "product must not be null");
        product.setOnshelfStatus(new OnshelfStatus(ProductShelfStatusEnum.OnShelf.value));
        return productRepository.updateProduct(product);
    }

    public int offShelf(ProductId productId){
        Product product = productRepository.findById(productId);
        AssertUtils.notNull(product, "product must not be null");
        product.setOnshelfStatus(new OnshelfStatus(ProductShelfStatusEnum.OffShelf.value));
        return productRepository.updateProduct(product);
    }


    public int setRecomend(ProductId productId){
        Product product = productRepository.findById(productId);
        AssertUtils.notNull(product, "product must not be null");
        product.setRecommend(new Recommend(true));
        return productRepository.updateProduct(product);
    }

    public int cancelRecomend(ProductId productId){
        Product product = productRepository.findById(productId);
        AssertUtils.notNull(product, "product must not be null");
        product.setRecommend(new Recommend(false));
        return productRepository.updateProduct(product);
    }
    public int setNews(ProductId productId){
        Product product = productRepository.findById(productId);
        AssertUtils.notNull(product, "product must not be null");
        product.setNewest(new Newest(true));
        return productRepository.updateProduct(product);
    }

    public int cancelNews(ProductId productId){
        Product product = productRepository.findById(productId);
        AssertUtils.notNull(product, "product must not be null");
        product.setNewest(new Newest(false));
        return productRepository.updateProduct(product);
    }

    public int setExplosives(ProductId productId ,String url){
        Product product = productRepository.findById(productId);
        AssertUtils.notNull(product, "product must not be null");
        product.setExplosives(new Explosives(true,url));
        return productRepository.updateProduct(product);
    }

    public int cancelExplosives(ProductId productId){
        Product product = productRepository.findById(productId);
        AssertUtils.notNull(product, "product must not be null");
        product.setExplosives(new Explosives(false,null));
        return productRepository.updateProduct(product);
    }



}
