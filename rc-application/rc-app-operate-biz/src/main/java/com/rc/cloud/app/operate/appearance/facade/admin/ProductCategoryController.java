package com.rc.cloud.app.operate.appearance.facade.admin;

import com.rc.cloud.app.operate.appearance.assemble.ProductCategoryAssemble;
import com.rc.cloud.app.operate.appearance.request.ProductCategoryVO;
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
    public CodeResult<Long> createProductCategory(@Valid @RequestBody ProductCategoryVO productCategoryVO) {
        productCategoryApplicationService.createProductCategory(ProductCategoryAssemble.INSTANCE.convert2ProductCategoryDTO(productCategoryVO));
        return CodeResult.ok();
    }

}
