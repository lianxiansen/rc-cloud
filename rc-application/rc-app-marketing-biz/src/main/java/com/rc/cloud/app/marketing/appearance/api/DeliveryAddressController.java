package com.rc.cloud.app.marketing.appearance.api;


import com.rc.cloud.app.marketing.appearance.api.req.DeliveryAddressCreateRequest;
import com.rc.cloud.app.marketing.appearance.api.req.DeliveryAddressUpdateRequest;
import com.rc.cloud.app.marketing.appearance.api.req.convert.DeliveryAddressCreateRequestConvert;
import com.rc.cloud.app.marketing.appearance.api.req.convert.DeliveryAddressUpdateRequestConvert;
import com.rc.cloud.app.marketing.appearance.api.resp.DeliveryAddressResponse;
import com.rc.cloud.app.marketing.appearance.api.resp.convert.DeliveryAddressCreateResponseConvert;
import com.rc.cloud.app.marketing.application.bo.DeliveryAddressBO;
import com.rc.cloud.app.marketing.application.service.DeliveryAddressApplicationService;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @ClassName DeliveryAddressController
 * @Author liandy
 * @Date 2023/8/7 08:42
 * @Description 收货地址控制器
 * @Version 1.0
 */
@Tag(name = "收货地址")
@RestController
@RequestMapping("/app/v1/deliveryAddress")
@Validated
public class DeliveryAddressController {

    @Autowired
    private DeliveryAddressApplicationService deliveryAddressApplicationService;

    @PostMapping("create")
    @Operation(summary = "新建收货地址")
    public CodeResult<DeliveryAddressResponse> create(@Valid @RequestBody DeliveryAddressCreateRequest deliveryAddressCreateRequest) {
        DeliveryAddressBO bo=deliveryAddressApplicationService.createDeliveryAddress(DeliveryAddressCreateRequestConvert.INSTANCE.convert(deliveryAddressCreateRequest));
        return CodeResult.ok(DeliveryAddressCreateResponseConvert.INSTANCE.convert(bo));
    }
    @PostMapping("update")
    @Operation(summary = "更新收货地址")
    public CodeResult<DeliveryAddressResponse> update(@Valid @RequestBody DeliveryAddressUpdateRequest deliveryAddressUpdateRequest) {
        DeliveryAddressBO bo=deliveryAddressApplicationService.updateDeliveryAddress(DeliveryAddressUpdateRequestConvert.INSTANCE.convert(deliveryAddressUpdateRequest));
        return CodeResult.ok(DeliveryAddressCreateResponseConvert.INSTANCE.convert(bo));
    }

    @GetMapping("findList")
    @Operation(summary = "获取收货地址列表")
    public CodeResult<List<DeliveryAddressResponse>> findList() {
        List<DeliveryAddressBO> boList=deliveryAddressApplicationService.findCustomerDeliveryAddresses();
        return CodeResult.ok(DeliveryAddressCreateResponseConvert.INSTANCE.convert(boList));
    }


}
