package com.rc.cloud.app.marketing.application.service.impl;

import com.rc.cloud.api.product.bo.ProductBO;
import com.rc.cloud.api.product.bo.ProductRemoveBO;
import com.rc.cloud.api.product.dto.ProductListQueryDTO;
import com.rc.cloud.api.product.dto.ProductQueryDTO;
import com.rc.cloud.api.product.dto.ProductRemoveDTO;
import com.rc.cloud.api.product.dto.ProductSaveDTO;
import com.rc.cloud.api.product.service.ProductApplicationService;
import com.rc.cloud.common.core.pojo.PageResult;
import org.springframework.stereotype.Service;

/**
 * @ClassName ProductApplicationServiceImpl
 * @Author liandy
 * @Date 2023/8/2 16:31
 * @Description TODO
 * @Version 1.0
 */
@Service
public class ProductApplicationServiceImpl implements ProductApplicationService {
    @Override
    public ProductBO createProduct(ProductSaveDTO productSaveDTO) {
        return null;
    }

    @Override
    public ProductBO updateProduct(ProductSaveDTO productSaveDTO) {
        return null;
    }

    @Override
    public ProductRemoveBO removeProductBatch(ProductRemoveDTO productRemoveDTO) {
        return null;
    }

    @Override
    public ProductBO getProduct(ProductQueryDTO productQueryDTO) {
        return null;
    }

    @Override
    public PageResult<ProductBO> getProductList(ProductListQueryDTO query) {
        return null;
    }

    @Override
    public int changeNewStatus(String productId, boolean newFlag) {
        return 0;
    }

    @Override
    public int changeOnShelfStatus(String productId, int onShelfStatus) {
        return 0;
    }

    @Override
    public int changePublicStatus(String productId, boolean publicFlag) {
        return 0;
    }

    @Override
    public int changeRecommendStatus(String productId, boolean recommendFlag) {
        return 0;
    }
}
