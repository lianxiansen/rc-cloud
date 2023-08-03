package com.rc.cloud.app.marketing.application.service.impl;

import com.rc.cloud.api.product.bo.ProductBO;
import com.rc.cloud.api.product.bo.ProductSkuBO;
import com.rc.cloud.api.product.bo.ProductSkuImageBO;
import com.rc.cloud.api.product.dto.ProductListQueryDTO;
import com.rc.cloud.api.product.service.ProductApplicationService;
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
import com.rc.cloud.app.marketing.infrastructure.util.ListUtil;
import com.rc.cloud.common.core.domain.IdUtil;
import com.rc.cloud.common.core.pojo.PageResult;
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
    private ProductApplicationService productApplicationService;

    /**
     * 根据店铺返回购物车列表
     * 返回按照商品分组，统一商品下包含不同规格
     *
     * @param shopIds
     * @return List<ShopCartBO>
     */
    @Override
    public List<CartBO> getCartListByShopIds(List<String> shopIds) {
        UserId userId = new UserId("admin");
        //获取最新商品信息，用于判断购物车是否过期
        PageResult<ProductBO> productList = productApplicationService.getProductList(new ProductListQueryDTO());

        List<Cart> list = cartService.getListByShopIds(userId, IdUtil.toList(shopIds, ShopId.class));
        List<CartBO> cartBOS = CartConvert.INSTANCE.convertList(list);
        cartBOS.forEach(x -> x.setState(0));
        //设置商品购物车商品过期
        setCartExpireByProduct(cartBOS, productList);


        return cartBOS;
    }


    @Override
    public PriceContext calPrice(List<String> productUniqueIdList) {
        List<CartBO> cartList = getCartList(productUniqueIdList);

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
    public List<CartBO> getCartList(List<String> productUniqueIdList) {
        UserId userId = new UserId("admin");
        //获取最新商品信息，用于判断购物车是否过期
        PageResult<ProductBO> productList = productApplicationService.getProductList(new ProductListQueryDTO());

        List<ProductUniqueId> productUniqueIds = IdUtil.toList(productUniqueIdList, ProductUniqueId.class);
        List<Cart> list = cartService.getList(userId, productUniqueIds);
        List<CartBO> cartBOS = CartConvert.INSTANCE.convertList(list);
        cartBOS.forEach(cartBO -> {
            cartBO.setShopBO(ramdomShop());
            cartBO.setState(1);
        });
        //设置商品购物车商品过期
        setCartExpireByProduct(cartBOS, productList);
        return cartBOS;
    }

    //设置商品购物车商品过期
    private void setCartExpireByProduct(List<CartBO> cartBOS, PageResult<ProductBO> productList) {
        List<ProductSkuBO> alSkuBOs = new ArrayList<>();
        productList.getList().forEach(cartProductBO -> {
            alSkuBOs.addAll(cartProductBO.getSkus());
        });
        Map<String, List<String>> map = alSkuBOs.stream().collect(Collectors.toMap(ProductSkuBO::getSkuCode,
                productSkuBO -> productSkuBO.getSkuAttributes().stream().map(x -> x.getAttributeValue()).collect(Collectors.toList()),
                (key1, key2) -> key2));

        cartBOS.forEach(cartBO -> {
            cartBO.setState(0);
            Optional<ProductBO> any = productList.getList().stream().filter(cartProductBO -> cartBO.getCartProductDetailBO().getId().equals(cartProductBO.getId())).findAny();
            //如果产品或者产品sku不存在，购物车失效
            if (any.isPresent() && map.containsKey(cartBO.getCartProductSkuDetailBO().getSkuCode())) {
                //如果sku属性完全相同，购物车有效
                if (ListUtil.isEqual(map.get(cartBO.getCartProductSkuDetailBO().getSkuCode()), cartBO.getCartProductSkuDetailBO().getSkuAttributes())) {
                    cartBO.setState(1);
                    ProductSkuBO skuBO = alSkuBOs.stream().filter(x -> cartBO.getCartProductSkuDetailBO().getSkuCode().equals(x.getSkuCode())).findFirst().get();
                    //设置最新价格
                    cartBO.getCartProductSkuDetailBO().setPrice(skuBO.getPrice());
                }
            }
        });
    }

    @Override
    public Boolean saveCart(List<CartDTO> cartDTOList) {
        final List<Cart> cartList = new ArrayList<>();

        //获取最新商品信息，用于保存购物车详情
        PageResult<ProductBO> productList = productApplicationService.getProductList(new ProductListQueryDTO());
        cartDTOList.forEach(cartDTO -> {
            UserId userId = new UserId("admin");

            String productid = cartDTO.getProductId();
            String skuCode = cartDTO.getProductUniqueid();
            String shopId = cartDTO.getShopId();
            ShopInfo shopInfo = new ShopInfo();
            shopInfo.setShopId(new ShopId(shopId));

            //获取产品，设置购物车产品属性
            Optional<ProductBO> anyProductBO = productList.getList().stream().filter(productBO -> productid.equals(productBO.getId())).findFirst();
            if (anyProductBO.isPresent()) {
                ProductBO productBO = anyProductBO.get();
                Optional<ProductSkuBO> anyProductSkuBO = productBO.getSkus().stream().filter(skuBO -> skuCode.equals(skuBO.getSkuCode())).findFirst();
                //如果存在相应的规格
                if (anyProductSkuBO.isPresent()) {
                    ProductSkuBO productSkuBO = anyProductSkuBO.get();
                    CartProductDetail cartProductDetail = CartConvert.INSTANCE.convert(productBO);
                    CartProductSkuDetail cartProductSkuDetail = CartConvert.INSTANCE.convert(productSkuBO);

                    Cart cart = cartService.createCommonCart(userId, cartDTO.getNum(), shopInfo, cartProductDetail, cartProductSkuDetail);
                    cartList.add(cart);
                }
            }

        });
        List<Cart> saveList = cartList.stream().filter(cart -> cart.getCartProductSkuDetail().getSkuAttributes() != null).collect(Collectors.toList());
        cartService.save(saveList);
        return true;
    }

    @Override
    public void deleteCartByProductUniqueid(List<String> productuniqueids) {
        UserId userId = new UserId("admin");
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
