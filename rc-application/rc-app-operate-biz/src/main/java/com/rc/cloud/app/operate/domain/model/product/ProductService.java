package com.rc.cloud.app.operate.domain.model.product;

import com.rc.cloud.app.operate.application.dto.ProductListQueryDTO;
import com.rc.cloud.app.operate.domain.common.ProductShelfStatusEnum;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Explosives;
import com.rc.cloud.app.operate.domain.model.product.valobj.OnshelfStatus;
import com.rc.cloud.app.operate.domain.model.product.valobj.Recommend;
import com.rc.cloud.app.operate.domain.model.product.valobj.Url;
import com.rc.cloud.app.operate.infrastructure.constants.ProductErrorCodeConstants;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
   // public Product clone

    @Autowired
    private ProductRepository productRepository;


    public  int createProduct(Product product){
        if(productRepository.exist(product.getId())){
            throw new ServiceException(ProductErrorCodeConstants.PRODUCT_EXIST_ERROR);
        }
        int flag=productRepository.insertProduct(product);
        return flag;
    }

    public int updateProduct(Product product){
        if(!productRepository.exist(product.getId())){
            throw new ServiceException(ProductErrorCodeConstants.PRODUCT_NOT_EXIST_ERROR);
        }
        int flag=productRepository.updateProduct(product);
        return flag;
    }

    public int deleteProduct(ProductId productId){
        productRepository.deleteProduct(productId);
        return 1;
    }

    public int onshelf(ProductId productId){
        Product product = productRepository.findById(productId);
        if(product==null){
            throw new ServiceException(ProductErrorCodeConstants.PRODUCT_NOT_EXIST_ERROR);
        }
        product.setOnshelfStatus(new OnshelfStatus(ProductShelfStatusEnum.Onshelf.value));
        return productRepository.updateProduct(product);
    }

    public int offshelf(ProductId productId){
        Product product = productRepository.findById(productId);
        if(product==null){
            throw new ServiceException(ProductErrorCodeConstants.PRODUCT_NOT_EXIST_ERROR);
        }
        product.setOnshelfStatus(new OnshelfStatus(ProductShelfStatusEnum.Offshelf.value));
        return productRepository.updateProduct(product);
    }


    public int setRecommend(ProductId productId){
        Product product = productRepository.findById(productId);
        if(product==null){
            throw new ServiceException(ProductErrorCodeConstants.PRODUCT_NOT_EXIST_ERROR);
        }
        product.setRecommendFlag(new Recommend(true));
        return productRepository.updateProduct(product);
    }

    public int cancelRecommend(ProductId productId){
        Product product = productRepository.findById(productId);
        if(product==null){
            throw new ServiceException(ProductErrorCodeConstants.PRODUCT_NOT_EXIST_ERROR);
        }
        product.setRecommendFlag(new Recommend(false));
        return productRepository.updateProduct(product);
    }

    public int setPublic(ProductId productId){
        Product product = productRepository.findById(productId);
        if(product==null){
            throw new ServiceException(ProductErrorCodeConstants.PRODUCT_NOT_EXIST_ERROR);
        }
        product.setPublicFlag(true);
        return productRepository.updateProduct(product);
    }

    public int cancelPublic(ProductId productId){
        Product product = productRepository.findById(productId);
        if(product==null){
            throw new ServiceException(ProductErrorCodeConstants.PRODUCT_NOT_EXIST_ERROR);
        }
        product.setPublicFlag(false);
        return productRepository.updateProduct(product);
    }


    public int setNews(ProductId productId){
        Product product = productRepository.findById(productId);
        if(product==null){
            throw new ServiceException(ProductErrorCodeConstants.PRODUCT_NOT_EXIST_ERROR);
        }
        product.setNewFlag(true);
        return productRepository.updateProduct(product);
    }

    public int cancelNews(ProductId productId){
        Product product = productRepository.findById(productId);
        if(product==null){
            throw new ServiceException(ProductErrorCodeConstants.PRODUCT_NOT_EXIST_ERROR);
        }
        product.setNewFlag(false);
        return productRepository.updateProduct(product);
    }

    public int setExplosives(ProductId productId ,String url){
        Product product = productRepository.findById(productId);
        if(product==null){
            throw new ServiceException(ProductErrorCodeConstants.PRODUCT_NOT_EXIST_ERROR);
        }
        if(StringUtils.isEmpty(url)){
            throw new ServiceException(ProductErrorCodeConstants.PRODUCT_EXPLOSIVES_IMAGE_NOT_UPLOAD_ERROR);
        }
        product.setExplosives(new Explosives(true,new Url(url)));
        return productRepository.updateProduct(product);
    }

    public int cancelExplosives(ProductId productId){
        Product product = productRepository.findById(productId);
        if(product==null){
            throw new ServiceException(ProductErrorCodeConstants.PRODUCT_NOT_EXIST_ERROR);
        }
        product.setExplosives(new Explosives(false,new Url("")));
        return productRepository.updateProduct(product);
    }


    public Product findProductById(ProductId productId) {
        Product product = productRepository.findById(productId);
        return product;
    }

    public PageResult<Product> getProductPageList(ProductListQueryDTO productListQueryDTO) {
        return productRepository.getProductPageList(productListQueryDTO);
    }
    public List<Product> findByIdBatch(List<ProductId> productIds) {
        List<Product> products = productRepository.findByIdBatch(productIds);
        return products;
    }

}
