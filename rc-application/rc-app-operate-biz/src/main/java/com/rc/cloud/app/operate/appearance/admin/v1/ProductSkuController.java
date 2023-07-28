package com.rc.cloud.app.operate.appearance.admin.v1;

import com.rc.cloud.app.operate.application.dto.ProductSkuGetDTO;
import com.rc.cloud.app.operate.application.dto.ProductSkuSaveDTO;
import com.rc.cloud.app.operate.application.service.ProductSkuApplicationService;
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

/**
 * @Author: chenjianxiang
 * @Date: 2023/7/1
 * @Description:
 */
@Tag(name = "产品")
@RestController
@RequestMapping("/admin/productsku")
@Validated
public class ProductSkuController {

    @Autowired
    private ProductSkuApplicationService productSkuApplicationService;


    @PostMapping("edit")
    @Operation(summary = "修改产品SKU")
    public CodeResult<Long> editProductSku(@Valid @RequestBody ProductSkuSaveDTO productSkuSaveDTO) {
        productSkuApplicationService.updateProductSku(productSkuSaveDTO);
        return CodeResult.ok();
    }

    @PostMapping("get")
    @Operation(summary = "获取产品SKU")
    public CodeResult<Long> getProductSku(@Valid @RequestBody ProductSkuGetDTO productSkuGetDTO) {
        productSkuApplicationService.getProductSku(productSkuGetDTO);
        return CodeResult.ok();
    }




}
