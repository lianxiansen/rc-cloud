package com.rc.cloud.app.marketing.infrastructure.repository;

import com.rc.cloud.api.product.dto.*;
import com.rc.cloud.api.product.feign.RemoteProductService;
import com.rc.cloud.app.marketing.domain.entity.cart.*;
import com.rc.cloud.app.marketing.domain.entity.cart.identifier.ProductId;
import com.rc.cloud.common.core.web.CodeResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author WJF
 * @create 2023-08-07 13:12
 * @description TODO
 */
@Repository
public class CartProductRepositoryImpl implements CartProductRepository {

    @Autowired
    private RemoteProductService productService;

    @Override
    public List<CartProductSkuInfo> getProductList(List<Cart> cartList) {
        //构建入参
        List<ProductSkuRequest> dto = new ArrayList<>();
        cartList.forEach(cart -> {
            String productId = cart.getCartProductDetail().getId().id();
            String productSkuId = cart.getCartProductSkuDetail().getSkuCode();
            ProductSkuRequest item = new ProductSkuRequest(productId, productSkuId);
            dto.add(item);
        });

        //远程调用
        CodeResult<List<ProductValidateResponse>> listCodeResult = productService.validateProduct(dto);
        if (!listCodeResult.isSuccess() || listCodeResult.getData() == null) {
            return null;
        }

        List<CartProductSkuInfo> productInfoList = new ArrayList<>();
        List<ProductValidateResponse> data = listCodeResult.getData();
        data.stream().filter(x -> x.isEnabled()).forEach(sku -> {
            CartProductSkuInfo info = new CartProductSkuInfo();
            info.setProductId(sku.getProductSku().getProductId());
            info.setPrice(sku.getProductSku().getPrice());
            info.setAttributes(sku.getProductSku().getAttributes());
            info.setSkuId(sku.getProductSku().getSkuId());
            info.setAttributeValues(sku.getProductSku().getAttributeValues());
            info.setProductName(sku.getProductSku().getProductName());
            info.setProductImage(sku.getProductSku().getProductImage());
            productInfoList.add(info);
        });

        return productInfoList;
    }

    @Override
    public Map<String, Object> getProduct(String productId, String productSkuId) {

        ProductQueryDTO queryDTO = new ProductQueryDTO();
        queryDTO.setProductId(productId);
        queryDTO.setNeedProductSku(true);

        CodeResult<ProductDetailResponse> result = productService.getProduct(queryDTO);

        if (!result.isSuccess() || result.getData() == null) {
            return new HashMap<>();
        }
        ProductDetailResponse product = result.getData();
        CartProductDetail detail = new CartProductDetail();
        BeanUtils.copyProperties(product, detail);
        detail.setId(new ProductId(product.getId()));
        detail.setSpuCode(product.getSpuCode());
        detail.setMasterImage(product.getListImage());

        CartProductSkuDetail skuDetail = new CartProductSkuDetail();
        ProductSkuDetailResponse productSkuDetailResponse = product.getSkus().stream().filter(sku -> sku.getId().equals(productSkuId)).findAny().get();
        BeanUtils.copyProperties(productSkuDetailResponse, skuDetail);
        skuDetail.setSkuCode(productSkuId);
        skuDetail.setSkuAttributes(productSkuDetailResponse.getAttributes().stream().map(x -> x.getValue()).collect(Collectors.toList()));
        skuDetail.setPrice(new BigDecimal(productSkuDetailResponse.getPrice()));
        skuDetail.setWeight(new BigDecimal(productSkuDetailResponse.getWeight()));
        if (productSkuDetailResponse.getAlbums() != null && productSkuDetailResponse.getAlbums().size() > 0) {
            skuDetail.setSkuImage(productSkuDetailResponse.getAlbums().get(0).getUrl());
        }

        Map<String, Object> map = new HashMap<>();
        map.put(CartAttributeKey.PRODUCT_DETAIL, detail);
        map.put(CartAttributeKey.PRODUCT_SKU_DETAIL, skuDetail);

        return map;
    }
}
