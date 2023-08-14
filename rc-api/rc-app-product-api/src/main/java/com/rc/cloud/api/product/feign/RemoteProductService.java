package com.rc.cloud.api.product.feign;

import cn.hutool.core.bean.BeanUtil;
import com.rc.cloud.api.product.dto.ProductDetailResponse;
import com.rc.cloud.api.product.dto.ProductQueryDTO;
import com.rc.cloud.api.product.dto.ProductSkuRequest;
import com.rc.cloud.api.product.dto.ProductValidateResponse;
import com.rc.cloud.common.core.constant.ServiceNameConstants;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.feign.FeignRequestInterceptor;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WJF
 * @create 2023-08-14 9:13
 * @description 通过feign远程调用权限服务
 */
@FeignClient(
        contextId = "remoteProductService",
        value = ServiceNameConstants.PRODUCT_SERVICE,
        configuration = FeignRequestInterceptor.class
)
public interface RemoteProductService {
    @PostMapping("/admin/product/validate")
    @Operation(summary = "校验产品是否可以购买")
    CodeResult<List<ProductValidateResponse>> validateProduct(@RequestBody List<ProductSkuRequest> query);

    @PostMapping("/admin/product/get")
    @Operation(summary = "获得产品")
    CodeResult<ProductDetailResponse> getProduct(@RequestBody ProductQueryDTO query);
}
