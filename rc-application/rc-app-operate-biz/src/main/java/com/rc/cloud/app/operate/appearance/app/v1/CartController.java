package com.rc.cloud.app.operate.appearance.app.v1;

import com.rc.cloud.app.operate.appearance.app.v1.convert.CartConvert;
import com.rc.cloud.app.operate.appearance.app.v1.resp.CartListResponse;
import com.rc.cloud.app.operate.application.bo.CartBO;
import com.rc.cloud.app.operate.application.bo.CartListBO;
import com.rc.cloud.app.operate.application.dto.CartDTO;
import com.rc.cloud.app.operate.application.service.CartApplicationService;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @GetMapping("/getlist")
    @Operation(summary = "获取购物车列表")
    public CodeResult<CartListBO> getlist() {
        CartListBO cartList = cartApplicationService.getCartList();

        // CartListResponse response = CartConvert.convert(cartList);
        return CodeResult.ok(cartList);
    }

    @PostMapping("/getlist")
    @Operation(summary = "根据产品唯一id获取购物车数量")
    public CodeResult<Map<String, Integer>> getCartList(@RequestBody List<String> productUniqueIds) {
        CartListBO cartList = cartApplicationService.getCartList(productUniqueIds);

        Map<String, Integer> maps = cartList.getValidList().stream().collect(Collectors.toMap(CartBO::getProductuniqueid, CartBO::getNum, (key1, key2) -> key2));
        // CartListResponse response = CartConvert.convert(cartList);
        return CodeResult.ok(maps);
    }


    @PostMapping("/saveCart")
    @Operation(summary = "增加购物车")
    public CodeResult<Boolean> saveCart(@RequestBody List<CartDTO> dto) {
        cartApplicationService.saveCart(dto);
        return CodeResult.ok();
    }

    @DeleteMapping("/deleteCart")
    @Operation(summary = "删除购物车")
    public CodeResult<Boolean> deleteCart(@RequestBody List<String> productUniqueIds) {
        cartApplicationService.deleteCartByProductuniqueid(productUniqueIds);
        return CodeResult.ok();
    }
}
