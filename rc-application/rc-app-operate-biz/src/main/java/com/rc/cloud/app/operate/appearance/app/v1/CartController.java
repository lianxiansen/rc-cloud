package com.rc.cloud.app.operate.appearance.app.v1;

import com.rc.cloud.app.operate.appearance.app.v1.convert.CartConvert;
import com.rc.cloud.app.operate.appearance.app.v1.resp.CartListResponse;
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

    @PostMapping("/addCart")
    @Operation(summary = "增加购物车")
    public CodeResult<Boolean> addCart(@RequestBody CartDTO dto) {
        cartApplicationService.addCart(dto);
        return CodeResult.ok();
    }

    @DeleteMapping("/deleteCart")
    @Operation(summary = "删除购物车")
    public CodeResult<Boolean> deleteCart(@RequestParam("id") String id) {
        cartApplicationService.deleteCart(id);
        return CodeResult.ok();
    }
}
