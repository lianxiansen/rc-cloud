package com.rc.cloud.app.operate.appearance.facade.admin;

import com.rc.cloud.app.operate.appearance.assemble.ProductCategoryAssemble;
import com.rc.cloud.app.operate.appearance.request.ProductCategoryCreateRequest;
import com.rc.cloud.app.operate.appearance.request.ProductCategoryUpdateRequest;
import com.rc.cloud.app.operate.application.service.ProductCategoryApplicationService;
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


@Tag(name = "产品类目")
@RestController
@RequestMapping("/productCategory")
@Validated
public class ProductCategoryController {

    @Autowired
    private ProductCategoryApplicationService productCategoryApplicationService;

    @PostMapping("create")
    @Operation(summary = "创建产品类目")
    public CodeResult<Long> create(@Valid @RequestBody ProductCategoryCreateRequest productCategoryCreateRequest) {
        productCategoryApplicationService.createProductCategory(ProductCategoryAssemble.INSTANCE.convert2ProductCategoryDTO(productCategoryCreateRequest));
        return CodeResult.ok();
    }


    @PostMapping("update")
    @Operation(summary = "更新产品类目")
    public CodeResult<Long> update(@Valid @RequestBody ProductCategoryUpdateRequest productCategoryUpdateRequest) {
        return CodeResult.ok();
    }

}
