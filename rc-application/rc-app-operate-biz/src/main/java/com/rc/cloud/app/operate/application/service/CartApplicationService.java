package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.bo.*;
import com.rc.cloud.app.operate.application.bo.convert.CartConvert;
import com.rc.cloud.app.operate.application.bo.convert.ProductSkuConvert;
import com.rc.cloud.app.operate.application.dto.CartDTO;
import com.rc.cloud.app.operate.domain.model.cart.Cart;
import com.rc.cloud.app.operate.domain.model.cart.CartDomainService;
import com.rc.cloud.app.operate.domain.model.cart.ShopInfo;
import com.rc.cloud.app.operate.domain.model.cart.identifier.CartId;
import com.rc.cloud.app.operate.domain.model.cart.identifier.ProductUniqueId;
import com.rc.cloud.app.operate.domain.model.cart.identifier.ShopId;
import com.rc.cloud.app.operate.domain.model.cart.identifier.UserId;
import com.rc.cloud.app.operate.domain.model.product.ProductDomainService;
import com.rc.cloud.common.core.domain.IdUtil;
import com.rc.cloud.common.core.util.collection.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<ShopCartBO> getCartListByShopIds(List<String> shopIds) {
        List<Cart> list = cartDomainService.getListByShopIds(IdUtil.toList(shopIds, ShopId.class));
        List<CartBO> cartBOS = CartConvert.INSTANCE.convertList(list);
        cartBOS.forEach(cartBO -> {
            cartBO.setProductSkuBO(randomSku());
            cartBO.setShopBO(ramdomShop());
            cartBO.setProductBO(randomSpu());
        });

        List<ShopCartBO> shopCartBOList = new ArrayList<>();
        shopIds.forEach(shopId -> {
            ShopCartBO shopCartBO = new ShopCartBO();
            shopCartBO.setShopInfo(ramdomShop());
            CartListBO listBO = new CartListBO();
            listBO.setCartList(cartBOS.stream().filter(cartBO -> shopId.equals(cartBO.getShopid())).collect(Collectors.toList()));
            shopCartBO.setCartList(listBO);
            shopCartBOList.add(shopCartBO);
        });

        return shopCartBOList;
    }

    public CartListBO getCartList(List<String> productUniqueIdList) {
        List<ProductUniqueId> productUniqueIds = IdUtil.toList(productUniqueIdList, ProductUniqueId.class);
        List<Cart> list = cartDomainService.getList(productUniqueIds);
        List<CartBO> cartBOS = CartConvert.INSTANCE.convertList(list);
        cartBOS.forEach(cartBO -> {
            cartBO.setProductSkuBO(randomSku());
            cartBO.setShopBO(ramdomShop());
            cartBO.setProductBO(randomSpu());
        });
        CartListBO bO = new CartListBO();
        bO.setCartList(cartBOS);
        return bO;
    }


    public void saveCart(List<CartDTO> cartDTOList) {
        List<Cart> cartList = CartConvert.INSTANCE.convert(cartDTOList);
        cartList.forEach(x -> {
            x.setUserId(new UserId("admin"));
        });

        cartDomainService.save(cartList);
    }

    public void deleteCart(String id) {
        cartDomainService.delete(new CartId(id));
    }

    public void deleteCartByProductuniqueid(List<String> productuniqueids) {
        cartDomainService.deleteCartByProductuniqueid(IdUtil.toList(productuniqueids, ProductUniqueId.class));
    }

    private ProductSkuBO randomSku() {
        ProductSkuBO bo = new ProductSkuBO();
        bo.setCartonSizeHeight(RandomUtils.nextInt(1, 20));
        bo.setCartonSizeWidth(RandomUtils.nextInt(1, 20));
        bo.setLimitBuy(RandomUtils.nextInt(1, 20));
        bo.setSkuCode(RandomStringUtils.randomNumeric(5));
        bo.setOutId(RandomStringUtils.randomNumeric(5));
        bo.setPrice(BigDecimal.valueOf(RandomUtils.nextDouble()));

        ProductSkuImageBO imageBO = new ProductSkuImageBO();
        List<ProductSkuImageBO> imageBOS = new ArrayList<>();
        imageBO.setSort(0);
        imageBO.setUrl("http://www.zjffcat.com/storage/uploads/20230724/375f97e06e1db292c59830ed1306ae72.jpg");
        imageBOS.add(imageBO);
//        imageBO = new ProductSkuImageBO();
//        imageBO.setSort(1);
//        imageBO.setUrl("http://www.zjffcat.com/storage/uploads/20230724/826886c84f24ee1759424bfae4f5a4ae.jpg");
//        imageBOS.add(imageBO);
//        imageBO = new ProductSkuImageBO();
//        imageBO.setSort(2);
//        imageBO.setUrl("http://www.zjffcat.com/storage/uploads/20230724/ff132347d581da2ecc0f8bd0ff0a1fbf.jpg");
//        imageBOS.add(imageBO);
//        imageBO = new ProductSkuImageBO();
//        imageBO.setSort(3);
//        imageBO.setUrl("http://www.zjffcat.com/storage/uploads/20230724/ad189229fa8e4eac58f28d45fc6b25d4.jpg");
//        imageBOS.add(imageBO);

        bo.setSkuImages(imageBOS);
        return bo;
    }

    private ProductBO randomSpu() {
        ProductBO bo = new ProductBO();
        bo.setName("手机支架");
        bo.setProductListImage("http://www.zjffcat.com/storage/uploads/20230724/826886c84f24ee1759424bfae4f5a4ae.jpg");
        return bo;
    }

    private ShopBO ramdomShop() {
        ShopBO bo = new ShopBO("小店" + RandomUtils.nextInt(1, 200));
        return bo;
    }
}
