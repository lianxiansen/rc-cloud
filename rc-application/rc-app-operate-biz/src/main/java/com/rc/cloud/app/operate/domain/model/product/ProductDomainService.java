package com.rc.cloud.app.operate.domain.model.product;

import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductImage;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDomainService {
   // public Product clone

    @Repository
    private ProductRepository productRepository;

    public  int createProduct(Product product){
        int flag=0;
        flag+= productRepository.insertProduct(product);
        if(product.getProductImages()!=null){
            flag+=productRepository.batchSaveProductImage(product.getProductImages());
        }
        if(product.getProductAttribute()!=null){
            flag+=productRepository.insertProductAttribute(product.getProductAttribute());
        }
        return flag>=3?1:0;
    }

    public int updateProduct(Product product){
        int flag=0;
        flag+= productRepository.updateProduct(product);
        //判断图片有没有更改
        if(judgeImageChange(product)) {
            //移除相册
            productRepository.removeProductImageByProductId(product.getId());
            if(product.getProductImages()!=null){
                flag+=productRepository.batchSaveProductImage(product.getProductImages());
            }
        }
        productRepository.removeProductAttributeByProductId(product.getId());
        flag+=productRepository.insertProductAttribute(product.getProductAttribute());
        return flag>=3?1:0;
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



    public int onShelf(Product product){
        return 1;
    }

    public int offShelf(Product product){
        return 1;
    }


    public int setRecomend(Product product){
        return 1;
    }

    public int cancelRecomend(Product product){
        return 1;
    }
    public int setNews(Product product){
        return 1;
    }

    public int cancelNews(Product product){
        return 1;
    }

    public int setExplosives(Product product){
        return 1;
    }

    public int cancelExplosives(Product product){
        return 1;
    }



}
