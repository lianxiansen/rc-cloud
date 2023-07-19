package com.rc.cloud.app.operate.appearance.admin.v1;

import com.rc.cloud.app.operate.application.bo.ProductBO;
import com.rc.cloud.app.operate.application.dto.*;
import com.rc.cloud.app.operate.application.service.ProductApplicationService;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@Tag(name = "产品")
@RestController
@RequestMapping("/operate/admin/product")
@Validated
public class ProductController {

    @Autowired
    private ProductApplicationService productApplicationService;
    @PostMapping("create")
    @Operation(summary = "创建产品")
    public CodeResult<ProductBO> createProduct(@Valid @RequestBody ProductSaveDTO productSaveDTO) {
        return CodeResult.ok( productApplicationService.createProduct(productSaveDTO));
    }



    @PostMapping("edit")
    @Operation(summary = "修改产品")
    public CodeResult<ProductBO> editProduct(@Valid @RequestBody ProductSaveDTO productSaveDTO) {
        return CodeResult.ok( productApplicationService.updateProduct(productSaveDTO));
    }

    @PostMapping("changeNewStatus")
    @Operation(summary = "修改New字段")
    public CodeResult<Long> changeNewStatus(@Valid @RequestBody ProductChangeNewStatusDTO dto){
        productApplicationService.changeNewStatus(dto.getProductId(), dto.isNewFlag());
        return CodeResult.ok();
    }

    @PostMapping("changeEnableStatus")
    @Operation(summary = "修改Enable字段")
    public CodeResult<Long> changeEnableStatus(@Valid @RequestBody ProductChangeEnableStatusDTO dto){
        productApplicationService.changeEnableStatus(dto.getProductId(), dto.isEnableFlag());
        return CodeResult.ok();
    }


    @PostMapping("changeOnShelfStatus")
    @Operation(summary = "修改产品字段")
    public CodeResult<Long> changeOnShelfStatus(@Valid @RequestBody ProductChangeOnShelfStatusDTO dto){
        productApplicationService.changeOnShelfStatus(dto.getProductId(), dto.getOnShelfStatus());
        return CodeResult.ok();
    }


    @PostMapping("changePublicStatus")
    @Operation(summary = "修改Public字段")
    public CodeResult<Long> changePublicStatus(@Valid @RequestBody ProductChangePublicStatusDTO dto){
        productApplicationService.changePublicStatus(dto.getProductId(), dto.isPublicFlag());
        return CodeResult.ok();
    }

    @PostMapping("changeRecommendStatus")
    @Operation(summary = "修改Recommend字段")
    public CodeResult<Long> changeRecommendStatus(@Valid @RequestBody ProductChangeRecommendStatusDTO dto){
        productApplicationService.changeRecommendStatus(dto.getProductId(), dto.isRecommendFlag());
        return CodeResult.ok();
    }



}
