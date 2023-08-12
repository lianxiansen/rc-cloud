package com.rc.cloud.app.marketing.infrastructure.repository;

import com.rc.cloud.api.product.service.ProductApplicationService;
import com.rc.cloud.app.marketing.domain.entity.cart.CartProductInfo;
import com.rc.cloud.app.marketing.domain.entity.cart.CartProductRepository;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WJF
 * @create 2023-08-07 13:12
 * @description TODO
 */
@Repository
public class CartProductRepositoryImpl implements CartProductRepository {

    @DubboReference
    private ProductApplicationService productApplicationService;

    @Override
    public List<CartProductInfo> getProductList() {



//        List<ProductSkuBO> alSkuBOs = new ArrayList<>();
//        productList.forEach(cartProduct -> {
//            alSkuBOs.addAll(cartProduct.getSkus());
//        });
//        Map<String, List<String>> map = alSkuBOs.stream().collect(Collectors.toMap(ProductSkuBO::getSkuCode,
//                productSkuBO -> productSkuBO.getSkuAttributes().stream().map(x -> x.getAttributeValue()).collect(Collectors.toList()),
//                (key1, key2) -> key2));
//
//        cartList.forEach(cart -> {
//            cart.setState(0);
//            Optional<ProductBO> any = productList.getList().stream().filter(cartProductBO -> cart.getCartProductDetailBO().getId().equals(cartProductBO.getId())).findAny();
//            //如果产品或者产品sku不存在，购物车失效
//            if (any.isPresent() && map.containsKey(cart.getCartProductSkuDetail().getSkuCode())) {
//                //如果sku属性完全相同，购物车有效
//                if (ListUtil.isEqual(map.get(cart.getCartProductSkuDetail().getSkuCode()), cart.getCartProductSkuDetail().getSkuAttributes())) {
//                    cart.setState(1);
//                    ProductSkuBO skuBO = alSkuBOs.stream().filter(x -> cart.getCartProductSkuDetail().getSkuCode().equals(x.getSkuCode())).findFirst().get();
//                    //设置最新价格
//                    cart.getCartProductSkuDetail().setPrice(skuBO.getPrice());
//                }
//            }
//        });
        return null;
    }

    @Override
    public CartProductInfo getProduct() {
        return null;
    }
}
