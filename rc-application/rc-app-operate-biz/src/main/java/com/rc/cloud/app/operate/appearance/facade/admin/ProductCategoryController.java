package com.rc.cloud.app.operate.appearance.facade.admin;

import com.rc.cloud.app.operate.appearance.assemble.ProductCategoryAssemble;
import com.rc.cloud.app.operate.appearance.vo.ProductCategoryVO;
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
import java.util.ArrayList;
import java.util.List;


@Tag(name = "产品分类")
@RestController
@RequestMapping("/productCategory")
@Validated
public class ProductCategoryController {

    @Autowired
    private ProductCategoryApplicationService productCategoryApplicationService;

    @PostMapping("create")
    @Operation(summary = "创建产品分类")
    public CodeResult<Long> create(@Valid @RequestBody ProductCategoryCreateDTO productCategoryCreateRequest) {
        productCategoryApplicationService.createProductCategory(productCategoryCreateRequest);
        return CodeResult.ok();
    }


    @PostMapping("update")
    @Operation(summary = "更新产品分类")
    public CodeResult<Long> update(@Valid @RequestBody ProductCategoryUpdateDTO productCategoryUpdateRequest) {
        productCategoryApplicationService.updateProductCategory(productCategoryUpdateRequest);
        return CodeResult.ok();
    }

    @GetMapping("remove")
    @Operation(summary = "删除产品分类")
    public CodeResult<Long> remove(String id) {
        productCategoryApplicationService.remove(id);
        return CodeResult.ok();
    }

    @GetMapping("findAll")
    @Operation(summary = "产品类目分类")
    public CodeResult<List<ProductCategoryVO>> findAll() {
        List<ProductCategoryVO> voList = new ArrayList<>();
        List<ProductCategoryBO> boList = productCategoryApplicationService.findAll();
        boList.forEach(bo -> {
            voList.add(ProductCategoryAssemble.INSTANCE.convert2ProductCategoryVO(bo));
        });
        return CodeResult.ok(voList);
    }
}
