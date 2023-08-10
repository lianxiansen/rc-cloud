package com.rc.cloud.app.operate.application.service.impl;

import com.rc.cloud.app.operate.application.bo.ProductRecommendBO;
import com.rc.cloud.app.operate.application.bo.convert.ProductRecommendConvert;
import com.rc.cloud.app.operate.application.dto.ProductRecommendCreateDTO;
import com.rc.cloud.app.operate.application.service.ProductRecommendApplicationService;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.ProductService;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productrecommend.ProductRecommend;
import com.rc.cloud.app.operate.domain.model.productrecommend.ProductRecommendService;
import com.rc.cloud.app.operate.domain.model.productrecommend.identifier.ProductRecommendId;
import com.rc.cloud.app.operate.infrastructure.constants.ProductRecommendErrorCodeConstants;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductRecommendApplicationServiceImpl implements ProductRecommendApplicationService {
    @Autowired
    private ProductRecommendService productRecommendService;
    @Autowired
    private ProductRepository productRepository;
    @Resource
    private IdRepository idRepository;
    @Autowired
    private ProductService productService;

    @Override
    public ProductRecommendBO create(ProductRecommendCreateDTO productRecommendCreateDTO) {
        if (StringUtils.isEmpty(productRecommendCreateDTO.getProductId())) {
            throw new ServiceException(ProductRecommendErrorCodeConstants.PRODUCT_ID_NOT_EMPTY);
        }
        if (StringUtils.isEmpty(productRecommendCreateDTO.getRecommendProductId())) {
            throw new ServiceException(ProductRecommendErrorCodeConstants.RECOMMEND_PRODUCT_ID_NOT_EMPTY);
        }
        ProductId productId=new ProductId(productRecommendCreateDTO.getProductId());
        ProductId recommendProductId=new ProductId(productRecommendCreateDTO.getRecommendProductId());
        ProductRecommend productRecommend = new ProductRecommend(new ProductRecommendId(idRepository.nextId()),productId,recommendProductId);
        productRecommendService.create(productRecommend);
        Product product = productService.findProductById(productId);
        return ProductRecommendConvert.convert2ProductRecommendBO(productRecommend,product);
    }

    @Override
    public boolean release(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new ServiceException(ProductRecommendErrorCodeConstants.ID_NOT_EMPTY);
        }
        ProductRecommend productRecommend = productRecommendService.findById(new ProductRecommendId(id));
        if (Objects.isNull(productRecommend)) {
            throw new ServiceException(ProductRecommendErrorCodeConstants.PRODUCT_RECOMMEND_NOT_EXISTS);
        }
        return productRecommendService.release(productRecommend);
    }


    @Override
    public List<ProductRecommendBO> findListByProductId(String productId) {
        if (StringUtils.isEmpty(productId)) {
            throw new ServiceException(ProductRecommendErrorCodeConstants.PRODUCT_ID_NOT_EMPTY);
        }
        Product product = productService.findProductById(new ProductId(productId));
        if (Objects.isNull(product)) {
            throw new ServiceException(ProductRecommendErrorCodeConstants.PRODUCT_NOT_EXISTS);
        }
        List<ProductRecommend> productRecommends = productRecommendService.findListFromProduct(product);
        List<Product> products = productRepository.selectBatchIds(findProductIds(productRecommends));
        return  ProductRecommendConvert.convert2ProductRecommendBOBatch(productRecommends,products);
    }

    private List<ProductId> findProductIds(List<ProductRecommend> productRecommends ){
        return productRecommends.stream().map(item->item.getRecommendProductId()).distinct().collect(Collectors.toList());
    }


}


