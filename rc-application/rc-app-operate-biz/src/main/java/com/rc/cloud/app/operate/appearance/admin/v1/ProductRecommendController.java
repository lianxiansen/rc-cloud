package com.rc.cloud.app.operate.appearance.admin.v1;

import com.rc.cloud.app.operate.appearance.admin.resp.ProductRecommendResponse;
import com.rc.cloud.app.operate.appearance.admin.resp.convert.ProductRecommendConvert;
import com.rc.cloud.app.operate.application.bo.ProductRecommendBO;
import com.rc.cloud.app.operate.application.dto.ProductRecommendCreateDTO;
import com.rc.cloud.app.operate.application.service.impl.ProductRecommendApplicationServiceImpl;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


@Tag(name = "产品推荐")
@RestController
@RequestMapping("/admin/productRecommend")
@Validated
public class ProductRecommendController {

    @Resource
    private ProductRecommendApplicationServiceImpl ProductRecommendApplicationService;

    @PostMapping("create")
    @Operation(summary = "创建产品推荐")
    public CodeResult<ProductRecommendResponse> create(@Valid @RequestBody ProductRecommendCreateDTO ProductRecommendCreateDTO) {
        ProductRecommendBO bo = ProductRecommendApplicationService.create(ProductRecommendCreateDTO);
        return CodeResult.ok(ProductRecommendConvert.INSTANCE.convert2ProductRecommendResponse(bo));
    }

    @DeleteMapping("release")
    @Operation(summary = "解除产品推荐")
    public CodeResult release(@RequestParam(name = "id", required = true) String id) {
        boolean released = ProductRecommendApplicationService.release(id);
        if(released){
            return CodeResult.ok();
        }
        return CodeResult.fail();
    }




    @GetMapping("findList")
    @Operation(summary = "获取产品推荐列表")
    public CodeResult<List<ProductRecommendResponse>> findList(@RequestParam(name = "productId", required = true) String productId) {
        List<ProductRecommendBO> boList = ProductRecommendApplicationService.findList(productId);
        return CodeResult.ok(ProductRecommendResponse.from(boList));
    }

}
