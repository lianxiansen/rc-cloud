package com.rc.cloud.app.operate.appearance.admin.v1;

import com.rc.cloud.app.operate.appearance.admin.res.ProductRecommendResponse;
import com.rc.cloud.app.operate.appearance.admin.res.convert.ProductRecommendConvert;
import com.rc.cloud.app.operate.application.bo.ProductRecommendBO;
import com.rc.cloud.app.operate.application.dto.ProductRecommendCreateDTO;
import com.rc.cloud.app.operate.application.service.ProductRecommendApplicationService;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Tag(name = "产品推荐")
@RestController
@RequestMapping("/admin/productRecommend")
@Validated
public class ProductRecommendController {

    @Autowired
    private ProductRecommendApplicationService ProductRecommendApplicationService;

    @PostMapping("create")
    @Operation(summary = "创建产品推荐")
    public CodeResult<ProductRecommendResponse> create(@Valid @RequestBody ProductRecommendCreateDTO ProductRecommendCreateDTO) {
        ProductRecommendBO bo = ProductRecommendApplicationService.create(ProductRecommendCreateDTO);
        return CodeResult.ok(ProductRecommendConvert.INSTANCE.convert2ProductRecommendResponse(bo));
    }

    @DeleteMapping("release")
    @Operation(summary = "解除产品推荐")
    public CodeResult<Boolean> release(@RequestParam(name = "id", required = true) String id) {
        return CodeResult.ok(ProductRecommendApplicationService.release(id));
    }




    @GetMapping("findListByProductId")
    @Operation(summary = "获取产品推荐列表")
    public CodeResult<List<ProductRecommendResponse>> findAll(@RequestParam(name = "productId", required = true) String productId) {
        List<ProductRecommendBO> boList = ProductRecommendApplicationService.findListByProductId(productId);
        return CodeResult.ok(ProductRecommendResponse.from(boList));
    }

}
