package com.rc.cloud.app.marketing.application.service.impl;

import com.rc.cloud.api.product.bo.ProductSkuBO;
import com.rc.cloud.api.product.bo.ProductSkuImageBO;
import com.rc.cloud.app.marketing.application.bo.*;
import com.rc.cloud.app.marketing.application.service.CartApplicationService;
import com.rc.cloud.app.marketing.domain.entity.cart.*;
import com.rc.cloud.app.marketing.domain.entity.cart.identifier.ProductUniqueId;
import com.rc.cloud.app.marketing.domain.entity.cart.identifier.ShopId;
import com.rc.cloud.app.marketing.domain.entity.cart.identifier.UserId;
import com.rc.cloud.app.marketing.domain.entity.price.PriceCalParam;
import com.rc.cloud.app.marketing.domain.entity.price.PriceContext;
import com.rc.cloud.app.marketing.domain.entity.price.PriceService;
import com.rc.cloud.app.marketing.domain.entity.price.ProductPack;
import com.rc.cloud.app.marketing.domain.entity.price.enums.OrderChannelEnum;
import com.rc.cloud.app.marketing.application.bo.convert.CartConvert;
import com.rc.cloud.app.marketing.application.bo.convert.PriceConvert;
import com.rc.cloud.app.marketing.application.dto.CartDTO;
import com.rc.cloud.common.core.domain.IdUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author WJF
 * @create 2023-07-25 11:18
 * @description TODO
 */
@Service
public class CartApplicationServiceImpl implements CartApplicationService {

    @Resource
    private CartService cartService;

    @Resource
    private PriceService priceService;

    @Resource
    private CartFactory cartFactory;

    /**
     * 根据店铺返回购物车列表
     * 返回按照商品分组，统一商品下包含不同规格
     *
     * @param shopIds
     * @return List<ShopCartBO>
     */
    @Override
    public List<CartBO> getCartListByShopIds(String user, List<String> shopIds) {
        UserId userId = new UserId(user);
        List<Cart> list = cartService.getListByShopIds(userId, IdUtil.toList(shopIds, ShopId.class));

        return CartConvert.INSTANCE.convertList(list);
    }


    @Override
    public PriceContext calPrice(String user, List<String> productUniqueIdList) {
        List<CartBO> cartList = getCartList(user, productUniqueIdList);

        PriceCalParam req = new PriceCalParam();
        req.setOrderNo("SO2020070611120001");
        req.setOversea(false);
        req.setMemberCode("M21152");
        req.setOrderChannel(OrderChannelEnum.APP);
        req.setCouponId(80081L);
        List<ProductPack> productPackList = PriceConvert.convertProductPack(cartList);
        req.setProductPackList(productPackList);
        return priceService.calPrice(req);
    }

    @Override
    public List<CartBO> getCartList(String user, List<String> productUniqueIdList) {
        UserId userId = new UserId(user);

        List<ProductUniqueId> productUniqueIds = IdUtil.toList(productUniqueIdList, ProductUniqueId.class);
        List<Cart> list = cartService.getList(userId, productUniqueIds);

        return CartConvert.INSTANCE.convertList(list);
    }


    @Override
    public Boolean saveCart(String user, List<CartDTO> cartDTOList) {
        final List<Cart> cartList = new ArrayList<>();

        //构造购物车领域对象
        cartDTOList.forEach(cartDTO -> {
            UserId userId = new UserId(user);

            String productid = cartDTO.getProductId();
            String skuCode = cartDTO.getProductUniqueid();
            String shopId = cartDTO.getShopId();
            ShopInfo shopInfo = new ShopInfo();
            shopInfo.setShopId(new ShopId(shopId));

            Cart cart = cartFactory.newCart(userId, cartDTO.getNum(), productid, skuCode, shopInfo);
            cartList.add(cart);
        });
        List<Cart> saveList = cartList.stream().filter(cart -> cart.getCartProductDetail() != null && cart.getCartProductSkuDetail() != null).collect(Collectors.toList());
        cartService.create(saveList);
        return true;
    }

    @Override
    public void deleteCartByProductUniqueid(String user, List<String> productuniqueids) {
        UserId userId = new UserId(user);
        cartService.deleteCartByProductuniqueid(userId, IdUtil.toList(productuniqueids, ProductUniqueId.class));
    }

    private ProductSkuBO randomSku() {
        ProductSkuBO bo = new ProductSkuBO();
        bo.setCartonSizeHeight(RandomUtils.nextInt(1, 20));
        bo.setCartonSizeWidth(RandomUtils.nextInt(1, 20));
        bo.setLimitBuy(RandomUtils.nextInt(1, 20));
        bo.setSkuCode(RandomStringUtils.randomNumeric(5));
        bo.setOutId(RandomStringUtils.randomNumeric(5));
        bo.setPrice(BigDecimal.valueOf(5));

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

    private CartProductSkuDetailBO randomDetail() {
        CartProductSkuDetailBO bo = new CartProductSkuDetailBO();
        bo.setSkuImage("http://www.zjffcat.com/storage/uploads/20230724/826886c84f24ee1759424bfae4f5a4ae.jpg");
        bo.setCartonSizeHeight(RandomUtils.nextInt(1, 20));
        bo.setCartonSizeWidth(RandomUtils.nextInt(1, 20));
        bo.setLimitBuy(RandomUtils.nextInt(1, 20));
        bo.setSkuCode(RandomStringUtils.randomNumeric(5));
        bo.setOutId(RandomStringUtils.randomNumeric(5));
        bo.setPrice(BigDecimal.valueOf(5));
        return bo;
    }

    private ShopBO ramdomShop() {
        ShopBO bo = new ShopBO("小店" + RandomUtils.nextInt(1, 200));
        return bo;
    }
}
