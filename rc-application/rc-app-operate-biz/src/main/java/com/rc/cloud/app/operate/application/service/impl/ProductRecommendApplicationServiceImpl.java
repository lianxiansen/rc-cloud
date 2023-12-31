package com.rc.cloud.app.operate.application.service.impl;

import com.rc.cloud.app.operate.application.bo.ProductRecommendBO;
import com.rc.cloud.app.operate.application.bo.convert.ProductRecommendConvert;
import com.rc.cloud.app.operate.application.dto.ProductRecommendCreateDTO;
import com.rc.cloud.app.operate.application.service.ProductRecommendApplicationService;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductService;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productrecommend.ProductRecommend;
import com.rc.cloud.app.operate.domain.model.productrecommend.ProductRecommendService;
import com.rc.cloud.app.operate.domain.model.productrecommend.identifier.ProductRecommendId;
import com.rc.cloud.app.operate.infrastructure.constants.ProductRecommendErrorCodeConstants;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductRecommendApplicationServiceImpl implements ProductRecommendApplicationService {
    @Resource
    private ProductRecommendService productRecommendService;
    @Resource
    private IdRepository idRepository;
    @Resource
    private ProductService productService;

    @Override
    public ProductRecommendBO create(ProductRecommendCreateDTO productRecommendCreateDTO) {
        if (StringUtils.isEmpty(productRecommendCreateDTO.getProductId())) {
            throw new ServiceException(ProductRecommendErrorCodeConstants.RECOMMEND_PRODUCT_ID_NOT_EMPTY);
        }
        if (StringUtils.isEmpty(productRecommendCreateDTO.getRecommendProductId())) {
            throw new ServiceException(ProductRecommendErrorCodeConstants.BE_RECOMMEND_PRODUCT_ID_NOT_EMPTY);
        }
        Product product = findProduct(productRecommendCreateDTO.getProductId());
        Product recommendProduct = findRecommendProduct(productRecommendCreateDTO.getRecommendProductId());
        ProductRecommend productRecommend = new ProductRecommend(new ProductRecommendId(idRepository.nextId()),product.getId(),recommendProduct.getId());
        productRecommendService.create(productRecommend);
        return ProductRecommendConvert.convert2ProductRecommendBO(productRecommend,product);
    }

    @Override
    public boolean release(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new ServiceException(ProductRecommendErrorCodeConstants.ID_NOT_EMPTY);
        }
        ProductRecommend productRecommend = findProductRecommend(id);
        return productRecommendService.release(productRecommend);
    }




    @Override
    public List<ProductRecommendBO> findList(String productId) {
        if (StringUtils.isEmpty(productId)) {
            throw new ServiceException(ProductRecommendErrorCodeConstants.RECOMMEND_PRODUCT_ID_NOT_EMPTY);
        }
        Product product = findProduct(productId);
        List<ProductRecommend> productRecommends = productRecommendService.findListByProduct(product);
        List<ProductId> productIds = extractRecommendProductIds(productRecommends);
        List<Product> products = productService.findByIdBatch(productIds);
        return  ProductRecommendConvert.convert2ProductRecommendBOBatch(productRecommends,products);
    }



    /**
     * 产品推荐列表中提取被推荐的产品列表
     * @param productRecommends
     * @return
     */
    private List<ProductId> extractRecommendProductIds(List<ProductRecommend> productRecommends ){
        return productRecommends.stream().map(item->item.getRecommendProductId()).distinct().collect(Collectors.toList());
    }

    private Product findRecommendProduct(String recommendProductId) {
        Product product = productService.findProductById(new ProductId(recommendProductId));
        if (Objects.isNull(product)) {
            throw new ServiceException(ProductRecommendErrorCodeConstants.BE_RECOMMEND_PRODUCT_ID_INVALID);
        }
        return product;
    }


    private Product findProduct(String productId) {
        Product product = productService.findProductById(new ProductId(productId));
        if (Objects.isNull(product)) {
            throw new ServiceException(ProductRecommendErrorCodeConstants.RECOMMEND_PRODUCT_ID_INVALID);
        }
        return product;
    }

    private ProductRecommend findProductRecommend(String id) {
        ProductRecommend productRecommend = productRecommendService.findById(new ProductRecommendId(id));
        if (Objects.isNull(productRecommend)) {
            throw new ServiceException(ProductRecommendErrorCodeConstants.RECOMMEND_PRODUCT_NOT_EXISTS);
        }
        return productRecommend;
    }

}


