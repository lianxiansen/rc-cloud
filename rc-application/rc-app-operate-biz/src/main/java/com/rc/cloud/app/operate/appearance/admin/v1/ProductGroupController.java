package com.rc.cloud.app.operate.appearance.admin.v1;

import com.rc.cloud.app.operate.appearance.admin.resp.ProductGroupItemResponse;
import com.rc.cloud.app.operate.appearance.admin.resp.ProductGroupResponse;
import com.rc.cloud.app.operate.appearance.admin.resp.convert.ProductGroupItemConvert;
import com.rc.cloud.app.operate.application.bo.ProductGroupBO;
import com.rc.cloud.app.operate.application.bo.ProductGroupItemBO;
import com.rc.cloud.app.operate.application.dto.ProductGroupCreateDTO;
import com.rc.cloud.app.operate.application.dto.ProductGroupFindDTO;
import com.rc.cloud.app.operate.application.dto.ProductGroupGetDTO;
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
@RequestMapping("/admin/productGroup")
@Validated
public class ProductGroupController {

    @Autowired
    private ProductGroupApplicationService productGroupApplicationService;

    @PostMapping("create")
    @Operation(summary = "创建产品组合")
    public CodeResult<ProductGroupResponse> create(@Valid @RequestBody ProductGroupCreateDTO productGroupCreateDTO) {
        ProductGroupBO bo = productGroupApplicationService.create(productGroupCreateDTO);
        return CodeResult.ok(ProductGroupResponse.from(bo));
    }

    @PostMapping("release")
    @Operation(summary = "解除产品组合")
    public CodeResult<Boolean> release(@Valid @RequestBody ProductGroupGetDTO request) {
        return CodeResult.ok(productGroupApplicationService.release(request.getId()));
    }


    @PostMapping("createItem")
    @Operation(summary = "添加组合项")
    public CodeResult<ProductGroupItemResponse> createItem(@Valid @RequestBody ProductGroupItemCreateDTO productGroupCreateDTO) {
        ProductGroupItemBO itemBO = productGroupApplicationService.createItem(productGroupCreateDTO);
        return CodeResult.ok(ProductGroupItemConvert.INSTANCE.convert2ProductGroupItemVO(itemBO));
    }

    @PostMapping("findList")
    @Operation(summary = "获取产品组合列表")
    public CodeResult<List<ProductGroupResponse>> findList(@Valid @RequestBody ProductGroupFindDTO request) {
        List<ProductGroupBO> boList = productGroupApplicationService.findList(request.getProductId());
        return CodeResult.ok(ProductGroupResponse.from(boList));
    }

}
