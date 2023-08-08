package com.rc.cloud.app.marketing.appearance.api;


import com.rc.cloud.app.marketing.appearance.api.req.PlaceOrderWithCartRequest;
import com.rc.cloud.app.marketing.appearance.api.resp.PlaceOrderResp;
import com.rc.cloud.app.marketing.application.service.OrderApplicationService;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/order")
@Validated
public class OrderController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private OrderApplicationService orderApplicationService;

    @PostMapping("placeOrder")
    @Operation(summary = "下单")
    public CodeResult<PlaceOrderResp> placeOrder(PlaceOrderWithCartRequest req) {
        return CodeResult.ok(PlaceOrderResp.from(orderApplicationService.placeOrderWithCart(req.toPlaceOrderDTO())));
    }


}
