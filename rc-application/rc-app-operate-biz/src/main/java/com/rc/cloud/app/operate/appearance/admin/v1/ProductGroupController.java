package com.rc.cloud.app.operate.appearance.admin.v1;

import com.rc.cloud.app.operate.appearance.admin.res.ProductGroupItemResponse;
import com.rc.cloud.app.operate.appearance.admin.convert.ProductGroupConvert;
import com.rc.cloud.app.operate.appearance.admin.convert.ProductGroupItemConvert;
import com.rc.cloud.app.operate.appearance.admin.res.ProductGroupResponse;
import com.rc.cloud.app.operate.application.bo.ProductGroupBO;
import com.rc.cloud.app.operate.application.bo.ProductGroupItemBO;
import com.rc.cloud.app.operate.application.dto.ProductGroupCreateDTO;
import com.rc.cloud.app.operate.application.dto.ProductGroupItemCreateDTO;
import com.rc.cloud.app.operate.application.service.ProductGroupApplicationService;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Tag(name = "产品组合")
@RestController
@RequestMapping("/operate/admin/productGroup")
@Validated
public class ProductGroupController {

    @Autowired
    private ProductGroupApplicationService productGroupApplicationService;

    @PostMapping("create")
    @Operation(summary = "创建产品组合")
    public CodeResult<ProductGroupResponse> create(@Valid @RequestBody ProductGroupCreateDTO productGroupCreateDTO) {
        ProductGroupBO bo = productGroupApplicationService.create(productGroupCreateDTO);
        return CodeResult.ok(ProductGroupConvert.INSTANCE.convert2ProductGroupVO(bo));
    }

    @DeleteMapping("release")
    @Operation(summary = "解除产品组合")
    public CodeResult<Boolean> release(@RequestParam(name = "id", required = true) String id) {
        return CodeResult.ok(productGroupApplicationService.release(id));
    }


    @PostMapping("createItem")
    @Operation(summary = "添加组合项")
    public CodeResult<ProductGroupItemResponse> createItem(@Valid @RequestBody ProductGroupItemCreateDTO productGroupCreateDTO) {
        ProductGroupItemBO itemBO = productGroupApplicationService.createItem(productGroupCreateDTO);
        return CodeResult.ok(ProductGroupItemConvert.INSTANCE.convert2ProductGroupItemVO(itemBO));
    }

    @GetMapping("findAll")
    @Operation(summary = "获取产品组合列表")
    public CodeResult<List<ProductGroupResponse>> findAll(@RequestParam(name = "productId", required = true) String productId) {
        List<ProductGroupBO> boList = productGroupApplicationService.findAll(productId);
        return CodeResult.ok(ProductGroupResponse.from(boList));
    }

}
