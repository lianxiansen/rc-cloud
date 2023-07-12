package com.rc.cloud.app.operate.appearance.facade.admin;

import com.rc.cloud.app.operate.application.dto.ProductCopyDTO;
import com.rc.cloud.app.operate.application.dto.ProductModifyDTO;
import com.rc.cloud.app.operate.application.dto.ProductSaveDTO;
import com.rc.cloud.app.operate.application.dto.ProductSaveVO;
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
@RequestMapping("/operate/product")
@Validated
public class ProductController {

    @Autowired
    private ProductApplicationService productApplicationService;
    @PostMapping("create")
    @Operation(summary = "创建产品")
    public CodeResult<Long> createProduct(@Valid @RequestBody ProductSaveDTO productSaveDTO) {
        productApplicationService.createProduct(productSaveDTO);
        return CodeResult.ok();
    }



    @PostMapping("edit")
    @Operation(summary = "修改产品")
    public CodeResult<Long> editProduct(@Valid @RequestBody ProductSaveDTO productSaveDTO) {
        productApplicationService.updateProduct(productSaveDTO);
        return CodeResult.ok();
    }

    @PostMapping("modifyField")
    @Operation(summary = "修改产品字段")
    public CodeResult<Long> modifyProductStatus(@Valid @RequestBody ProductModifyDTO productModifyDTO){
        productApplicationService.modifyProductField(productModifyDTO);
        return CodeResult.ok();
    }



}
