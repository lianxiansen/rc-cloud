package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.bo.*;
import com.rc.cloud.app.operate.application.bo.convert.CartConvert;
import com.rc.cloud.app.operate.application.bo.convert.PriceConvert;
import com.rc.cloud.app.operate.application.bo.convert.ProductSkuConvert;
import com.rc.cloud.app.operate.application.dto.CartDTO;
import com.rc.cloud.app.operate.application.dto.ProductListQueryDTO;
import com.rc.cloud.app.operate.application.dto.ProductQueryDTO;
import com.rc.cloud.app.operate.domain.model.cart.Cart;
import com.rc.cloud.app.operate.domain.model.cart.CartDomainService;
import com.rc.cloud.app.operate.domain.model.cart.CartProductDetail;
import com.rc.cloud.app.operate.domain.model.cart.identifier.ProductUniqueId;
import com.rc.cloud.app.operate.domain.model.cart.identifier.ShopId;
import com.rc.cloud.app.operate.domain.model.cart.identifier.UserId;
import com.rc.cloud.app.operate.domain.model.price.PriceCalParam;
import com.rc.cloud.app.operate.domain.model.price.PriceContext;
import com.rc.cloud.app.operate.domain.model.price.ProductPack;
import com.rc.cloud.app.operate.domain.model.price.enums.OrderChannelEnum;
import com.rc.cloud.app.operate.infrastructure.util.ListUtil;
import com.rc.cloud.common.core.domain.IdUtil;
import com.rc.cloud.common.core.pojo.PageResult;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;
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
    private PriceApplicationService priceApplicationService;

    @Resource
    private ProductApplicationService productApplicationService;

    public List<ShopCartBO> getCartListByShopIds(List<String> shopIds) {
        UserId userId = new UserId("admin");
        List<Cart> list = cartDomainService.getListByShopIds(userId, IdUtil.toList(shopIds, ShopId.class));
        List<CartBO> cartBOS = CartConvert.INSTANCE.convertList(list);
        cartBOS.forEach(cartBO -> {
            cartBO.setShopBO(ramdomShop());
            cartBO.setCartProductDetailBO(randomDetail());
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

    public PriceContext calPrice(List<String> productUniqueIdList) {
        CartListBO cartList = getCartList(productUniqueIdList);

        PriceCalParam req = new PriceCalParam();
        req.setOrderNo("SO2020070611120001");
        req.setOversea(false);
        req.setMemberCode("M21152");
        req.setOrderChannel(OrderChannelEnum.APP);
        req.setCouponId(80081L);
        List<ProductPack> productPackList = PriceConvert.convertProductPack(cartList);
        req.setProductPackList(productPackList);
        return priceApplicationService.calPrice(req);
    }

    public CartListBO getCartList(List<String> productUniqueIdList) {
        UserId userId = new UserId("admin");
        //获取最新商品信息，用于判断购物车是否过期
        PageResult<ProductBO> productList = productApplicationService.getProductList(new ProductListQueryDTO());
        List<ProductSkuBO> alSkuBOs = new ArrayList<>();
        productList.getList().forEach(productBO -> {
            alSkuBOs.addAll(productBO.getSkus());
        });
        Map<String, List<String>> map = alSkuBOs.stream().collect(Collectors.toMap(ProductSkuBO::getSkuCode,
                productSkuBO -> productSkuBO.getSkuAttributes().stream().map(x -> x.getAttributeValue()).collect(Collectors.toList()),
                (key1, key2) -> key2));

        List<ProductUniqueId> productUniqueIds = IdUtil.toList(productUniqueIdList, ProductUniqueId.class);
        List<Cart> list = cartDomainService.getList(userId, productUniqueIds);
        List<CartBO> cartBOS = CartConvert.INSTANCE.convertList(list);
        cartBOS.forEach(cartBO -> {
            cartBO.setShopBO(ramdomShop());
            cartBO.getCartProductDetailBO().getSkuAttributes();
            cartBO.setState(0);
            Optional<ProductBO> any = productList.getList().stream().filter(productBO -> cartBO.getProductid().equals(productBO.getId())).findAny();

            //如果产品或者产品sku不存在，购物车失效
            if (any.isPresent() && map.containsKey(cartBO.getProductuniqueid())) {
                //如果sku属性完全相同，购物车有效
                if (ListUtil.isEqual(map.get(cartBO.getProductuniqueid()), cartBO.getCartProductDetailBO().getSkuAttributes())) {
                    cartBO.setState(1);
                }
            }
        });
        CartListBO bO = new CartListBO();
        bO.setCartList(cartBOS);
        return bO;
    }

    public Boolean saveCart(List<CartDTO> cartDTOList) {
        List<Cart> cartList = CartConvert.INSTANCE.convert(cartDTOList);
        //获取最新商品信息，用于保存购物车详情
        PageResult<ProductBO> productList = productApplicationService.getProductList(new ProductListQueryDTO());
        cartList.forEach(cart -> {
            cart.setUserId(new UserId("admin"));
            String productid = cart.getCartProductDetail().getProductId().id();
            String skuCode = cart.getCartProductDetail().getSkuCode();
            //获取产品，设置购物车产品属性
            Optional<ProductBO> any = productList.getList().stream().filter(productBO -> productid.equals(productBO.getId())).findAny();
            if (any.isPresent()) {
                ProductBO productBO = any.get();

                CartProductDetail cartProductDetail = CartConvert.INSTANCE.convert(productBO, skuCode);
                cart.setCartProductDetail(cartProductDetail);
            }

        });
        cartList = cartList.stream().filter(cart -> cart.getCartProductDetail().getSkuAttributes() != null).collect(Collectors.toList());
        cartDomainService.save(cartList);
        return true;
    }


    public void deleteCartByProductuniqueid(List<String> productuniqueids) {
        UserId userId = new UserId("admin");
        cartDomainService.deleteCartByProductuniqueid(userId, IdUtil.toList(productuniqueids, ProductUniqueId.class));
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

    private CartProductDetailBO randomDetail() {
        CartProductDetailBO bo = new CartProductDetailBO();
        bo.setProductName("手机支架");
        bo.setProductImage("http://www.zjffcat.com/storage/uploads/20230724/375f97e06e1db292c59830ed1306ae72.jpg");
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
