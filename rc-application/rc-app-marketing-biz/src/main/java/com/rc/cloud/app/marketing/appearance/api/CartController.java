package com.rc.cloud.app.marketing.appearance.api;

import com.rc.cloud.api.product.bo.ProductSkuBO;
import com.rc.cloud.api.product.bo.ProductSkuImageBO;
import com.rc.cloud.app.marketing.appearance.api.vo.CartProductVO;
import com.rc.cloud.app.marketing.application.bo.*;
import com.rc.cloud.app.marketing.appearance.api.vo.ShopCartVO;
import com.rc.cloud.app.marketing.application.dto.CartDTO;
import com.rc.cloud.app.marketing.application.service.CartApplicationService;
import com.rc.cloud.app.marketing.domain.entity.price.PriceContext;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author WJF
 * @create 2023-07-25 13:11
 * @description TODO
 */
@Tag(name = "购物车")
@RestController
@RequestMapping("/app/v1/cart")
public class CartController {
    @Resource
    private CartApplicationService cartApplicationService;

    @PostMapping("/getlistByShopIds")
    @Operation(summary = "获取购物车列表")
    public CodeResult<List<ShopCartVO>> getlistByShopIds(@RequestBody List<String> shopIds) {
        List<CartBO> cartList = cartApplicationService.getCartListByShopIds(shopIds);
       
        List<ShopCartVO> shopCartVOList = new ArrayList<>();
        shopIds.forEach(shopId -> {
            ShopCartVO shopCartVO = new ShopCartVO();
            shopCartVO.setShopInfo(ramdomShop());
            List<CartBO> groupByShop = cartList.stream().filter(cartBO -> shopId.equals(cartBO.getShopId())).collect(Collectors.toList());
            List<CartProductVO> groupByproduct = groupByproduct(groupByShop);
            shopCartVO.setCartProductList(groupByproduct);

            shopCartVOList.add(shopCartVO);
        });
        return CodeResult.ok(shopCartVOList);
    }

    @PostMapping("/getlist")
    @Operation(summary = "根据产品唯一id获取购物车数量")
    public CodeResult<Map<String, Integer>> getCartList(@RequestBody List<String> productUniqueIds) {
        List<CartBO> cartList = cartApplicationService.getCartList(productUniqueIds);
        List<CartBO> cartBOs = cartList.stream().filter(x -> x.getState() == 1).collect(Collectors.toList());
        //暂时注释，方便测试
        //Map<String, Integer> maps = cartBOs.stream().collect(Collectors.toMap(CartBO::getProductuniqueid, CartBO::getNum, (key1, key2) -> key2));
        Map<String, Integer> maps1 = cartList.stream().collect(Collectors.toMap(x->x.getCartProductSkuDetailBO().getSkuCode(), CartBO::getNum, (key1, key2) -> key2));
        return CodeResult.ok(maps1);
    }


    @PostMapping("/saveCart")
    @Operation(summary = "增加购物车")
    public CodeResult<Boolean> saveCart(@RequestBody List<CartDTO> dto) {
        if (!cartApplicationService.saveCart(dto)) {
            return CodeResult.fail("部分商品已过期");
        }
        return CodeResult.ok();
    }

    @DeleteMapping("/deleteCart")
    @Operation(summary = "删除购物车")
    public CodeResult<Boolean> deleteCart(@RequestBody List<String> productUniqueIds) {
        cartApplicationService.deleteCartByProductUniqueid(productUniqueIds);
        return CodeResult.ok();
    }

    @PostMapping("/calPrice")
    @Operation(summary = "根据选择产品获取总价")
    public CodeResult<BigDecimal> calPrice(@RequestBody List<String> productUniqueIds) {
        PriceContext context = cartApplicationService.calPrice(productUniqueIds);
        return CodeResult.ok(context.getFinalOrderPrice());
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

    private List<CartProductVO> groupByproduct(List<CartBO> groupByShop) {
        List<CartProductVO> list = new ArrayList<>();
        Map<CartProductVO, List<CartBO>> map = groupByShop.stream().collect(Collectors.groupingBy(
                x -> {
                    return new CartProductVO()
                            .setProductName(x.getCartProductDetailBO().getName())
                            .setProductId(x.getCartProductDetailBO().getId())
                            .setProductImage(x.getCartProductDetailBO().getMasterImage())
                            .setOutid(x.getCartProductDetailBO().getOutId());
                }
        ));
        for (CartProductVO key : map.keySet()) {
            List<CartBO> cartBOS = map.get(key);
            CartProductVO productBO = new CartProductVO(key);
            productBO.setDetailList(cartBOS);
            list.add(productBO);
        }
        return list;
    }
}
