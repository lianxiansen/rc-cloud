package com.rc.cloud.app.operate.appearance.admin.v1;

import com.rc.cloud.app.operate.appearance.admin.res.ProductCategoryResponse;
import com.rc.cloud.app.operate.application.bo.ProductCategoryBO;
import com.rc.cloud.app.operate.application.dto.ProductCategoryCreateDTO;
import com.rc.cloud.app.operate.application.dto.ProductCategoryUpdateDTO;
import com.rc.cloud.app.operate.application.service.ProductCategoryApplicationService;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Tag(name = "产品分类")
@RestController
@RequestMapping("/operate/admin/productCategory")
@Validated
public class ProductCategoryController {

    @Autowired
    private ProductCategoryApplicationService productCategoryApplicationService;

    @PostMapping("create")
    @Operation(summary = "创建产品分类")
    public CodeResult<ProductCategoryResponse> create(@Valid @RequestBody ProductCategoryCreateDTO productCategoryCreateRequest) {
        return CodeResult.ok(ProductCategoryResponse.from(productCategoryApplicationService.create(productCategoryCreateRequest)));
    }


    @PutMapping("update")
    @Operation(summary = "更新产品分类")
    public CodeResult<ProductCategoryResponse> update(@Valid @RequestBody ProductCategoryUpdateDTO productCategoryUpdateRequest) {
        return CodeResult.ok(ProductCategoryResponse.from(productCategoryApplicationService.update(productCategoryUpdateRequest)));
    }

    @DeleteMapping("remove")
    @Operation(summary = "删除产品分类")
    public CodeResult<Long> remove(String id) {
        if(productCategoryApplicationService.remove(id)){
            return CodeResult.ok();
        }
        return CodeResult.fail();
    }

    @GetMapping("findAll")
    @Operation(summary = "产品分类列表")
    public CodeResult<List<ProductCategoryResponse>> findAll() {
        List<ProductCategoryBO> boList = productCategoryApplicationService.findAll();
        return CodeResult.ok(ProductCategoryResponse.from(boList));
    }
    @GetMapping("findById")
    @Operation(summary = "根据唯一标识查找产品分类")
    public CodeResult<ProductCategoryResponse> findById(String id) {
        ProductCategoryBO bo = productCategoryApplicationService.findById(id);
        return CodeResult.ok(ProductCategoryResponse.from(bo));
    }

}
