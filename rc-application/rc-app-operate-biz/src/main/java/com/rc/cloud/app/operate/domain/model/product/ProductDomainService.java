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
        if(productRepository.exist(product.getId())){
            throw new IllegalArgumentException("该商品已存在");
        }
        int flag=productRepository.insertProduct(product);
        return flag;
    }

    public int updateProduct(Product product){
        if(!productRepository.exist(product.getId())){
            throw new IllegalArgumentException("该商品不存在");
        }
        int flag=productRepository.updateProduct(product);
        return flag;
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
