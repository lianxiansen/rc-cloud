package com.rc.cloud.app.operate.domain.model.product;

import com.rc.cloud.app.operate.application.dto.ProductListQueryDTO;
import com.rc.cloud.app.operate.domain.common.ProductShelfStatusEnum;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Explosives;
import com.rc.cloud.app.operate.domain.model.product.valobj.OnshelfStatus;
import com.rc.cloud.app.operate.domain.model.product.valobj.Recommend;
import com.rc.cloud.app.operate.domain.model.product.valobj.Url;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.util.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
   // public Product clone

    @Autowired
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

    public int deleteProduct(ProductId productId){
        productRepository.deleteProduct(productId);
        return 1;
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


    public int setRecommend(ProductId productId){
        Product product = productRepository.findById(productId);
        AssertUtils.notNull(product, "product must not be null");
        product.setRecommendFlag(new Recommend(true));
        return productRepository.updateProduct(product);
    }

    public int cancelRecommend(ProductId productId){
        Product product = productRepository.findById(productId);
        AssertUtils.notNull(product, "product must not be null");
        product.setRecommendFlag(new Recommend(false));
        return productRepository.updateProduct(product);
    }

    public int setPublic(ProductId productId){
        Product product = productRepository.findById(productId);
        AssertUtils.notNull(product, "product must not be null");
        product.setPublicFlag(true);
        return productRepository.updateProduct(product);
    }

    public int cancelPublic(ProductId productId){
        Product product = productRepository.findById(productId);
        AssertUtils.notNull(product, "product must not be null");
        product.setPublicFlag(false);
        return productRepository.updateProduct(product);
    }


    public int setNews(ProductId productId){
        Product product = productRepository.findById(productId);
        AssertUtils.notNull(product, "product must not be null");
        product.setNewFlag(true);
        return productRepository.updateProduct(product);
    }

    public int cancelNews(ProductId productId){
        Product product = productRepository.findById(productId);
        AssertUtils.notNull(product, "product must not be null");
        product.setPublicFlag(false);
        return productRepository.updateProduct(product);
    }

    public int setExplosives(ProductId productId ,String url){
        Product product = productRepository.findById(productId);
        AssertUtils.notNull(product, "product must not be null");
        product.setExplosives(new Explosives(true,new Url(url)));
        return productRepository.updateProduct(product);
    }

    public int cancelExplosives(ProductId productId){
        Product product = productRepository.findById(productId);
        AssertUtils.notNull(product, "product must not be null");
        product.setExplosives(new Explosives(false,null));
        return productRepository.updateProduct(product);
    }


    public Product findProductById(ProductId productId) {
        Product product = productRepository.findById(productId);

        return product;
    }

    public PageResult<Product> getProductPageList(ProductListQueryDTO productListQueryDTO) {
        return productRepository.getProductPageList(productListQueryDTO);
    }


}
