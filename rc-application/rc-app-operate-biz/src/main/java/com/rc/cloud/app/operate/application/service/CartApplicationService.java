package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.bo.*;
import com.rc.cloud.app.operate.application.bo.convert.CartConvert;
import com.rc.cloud.app.operate.application.bo.convert.ProductSkuConvert;
import com.rc.cloud.app.operate.application.dto.CartDTO;
import com.rc.cloud.app.operate.domain.model.cart.Cart;
import com.rc.cloud.app.operate.domain.model.cart.CartDomainService;
import com.rc.cloud.app.operate.domain.model.cart.identifier.CartId;
import com.rc.cloud.app.operate.domain.model.cart.identifier.UserId;
import com.rc.cloud.app.operate.domain.model.product.ProductDomainService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WJF
 * @create 2023-07-25 11:18
 * @description TODO
 */
@Service
public class CartApplicationService {

    @Resource
    private CartDomainService cartDomainService;

    @Resource
    private ProductDomainService productDomainService;

    public CartListBO getCartList() {
        List<Cart> list = cartDomainService.getList();
        List<CartBO> cartBOS = CartConvert.INSTANCE.convertList(list);

        CartListBO bo = new CartListBO();
        bo.setValidList(cartBOS);

        List<ProductSkuBO> productSkuBOS = new ArrayList<>();
        cartBOS.forEach(cartBO -> {
            cartBO.setProductSkuBO(ramdomSku());
            cartBO.setShopBO(ramdomShop());
        });

        return bo;
    }

    public void addCart(CartDTO cartDTO) {
        Cart cart = CartConvert.INSTANCE.convert(cartDTO);
        cart.setUserId(new UserId("admin"));
        cartDomainService.create(cart);
    }

    public void deleteCart(String id) {
        Cart cart = new Cart();
        cart.setId(new CartId(id));
        cartDomainService.delete(cart);
    }


    private ProductSkuBO ramdomSku() {
        ProductSkuBO bo = new ProductSkuBO();
        bo.setCartonSizeHeight(RandomUtils.nextInt(1, 20));
        bo.setCartonSizeWidth(RandomUtils.nextInt(1, 20));
        bo.setLimitBuy(RandomUtils.nextInt(1, 20));
        bo.setSkuCode(RandomStringUtils.randomNumeric(5));
        bo.setOutId(RandomStringUtils.randomNumeric(5));

        ProductSkuImageBO imageBO = new ProductSkuImageBO();
        List<ProductSkuImageBO> imageBOS = new ArrayList<>();
        imageBO.setSort(0);
        imageBO.setUrl("http://www.zjffcat.com/storage/uploads/20230724/375f97e06e1db292c59830ed1306ae72.jpg");
        imageBOS.add(imageBO);
        imageBO = new ProductSkuImageBO();
        imageBO.setSort(1);
        imageBO.setUrl("http://www.zjffcat.com/storage/uploads/20230724/826886c84f24ee1759424bfae4f5a4ae.jpg");
        imageBOS.add(imageBO);
        imageBO = new ProductSkuImageBO();
        imageBO.setSort(2);
        imageBO.setUrl("http://www.zjffcat.com/storage/uploads/20230724/ff132347d581da2ecc0f8bd0ff0a1fbf.jpg");
        imageBOS.add(imageBO);
        imageBO = new ProductSkuImageBO();
        imageBO.setSort(3);
        imageBO.setUrl("http://www.zjffcat.com/storage/uploads/20230724/ad189229fa8e4eac58f28d45fc6b25d4.jpg");
        imageBOS.add(imageBO);

        bo.setSkuImages(imageBOS);
        return bo;
    }

    private ShopBO ramdomShop() {
        ShopBO bo = new ShopBO("小店" + RandomUtils.nextInt(1, 200));
        return bo;
    }
}
