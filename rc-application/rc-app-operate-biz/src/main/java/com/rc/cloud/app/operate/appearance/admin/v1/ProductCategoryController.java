package com.rc.cloud.app.operate.appearance.admin.v1;

import com.rc.cloud.app.operate.appearance.admin.resp.ProductCategoryResponse;
import com.rc.cloud.app.operate.appearance.admin.v1.req.ProductCategoryCreateRequest;
import com.rc.cloud.app.operate.appearance.admin.v1.req.ProductCategoryUpdateRequest;
import com.rc.cloud.app.operate.application.bo.ProductCategoryBO;
import com.rc.cloud.app.operate.application.dto.ProductCategoryCreateDTO;
import com.rc.cloud.app.operate.application.dto.ProductCategoryUpdateDTO;
import com.rc.cloud.app.operate.application.service.ProductCategoryApplicationService;
import com.rc.cloud.common.core.util.tree.TreeUtil;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.web.filter.RepeatSubmit;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;


@Tag(name = "产品分类")
@RestController
@RequestMapping("/admin/productCategory")
@Validated
public class ProductCategoryController {

    @Autowired
    private ProductCategoryApplicationService productCategoryApplicationService;
    @RepeatSubmit
    @PostMapping("create")
    @Operation(summary = "创建产品分类")
    public CodeResult<ProductCategoryResponse> create(@Valid @RequestBody ProductCategoryCreateRequest request) {
        ProductCategoryCreateDTO dto=new ProductCategoryCreateDTO();
        BeanUtils.copyProperties(request,dto);
        return CodeResult.ok(ProductCategoryResponse.from(productCategoryApplicationService.create(dto)));
    }

    @RepeatSubmit
    @PutMapping("update")
    @Operation(summary = "更新产品分类")
    public CodeResult<ProductCategoryResponse> update(@Valid @RequestBody ProductCategoryUpdateRequest request) {
        ProductCategoryUpdateDTO dto=new ProductCategoryUpdateDTO();
        BeanUtils.copyProperties(request,dto);
        return CodeResult.ok(ProductCategoryResponse.from(productCategoryApplicationService.update(dto)));
    }

    @DeleteMapping("remove")
    @Operation(summary = "删除产品分类")
    public CodeResult<Long> remove(@RequestParam(name = "id",required = true) String id) {
        if(productCategoryApplicationService.remove(id)){
            return CodeResult.ok();
        }
        return CodeResult.fail();
    }

    @GetMapping("findAll")
    @Operation(summary = "产品分类列表")
    public CodeResult<List<ProductCategoryResponse>> findAll() {
        List<ProductCategoryBO> bos =productCategoryApplicationService.findAll();
        List<ProductCategoryResponse> responses=ProductCategoryResponse.from(bos);
        return CodeResult.ok(responses);
    }

    @GetMapping("findById")
    @Operation(summary = "根据唯一标识查找产品分类")
    public CodeResult<ProductCategoryResponse> findById(@RequestParam(name = "id",required = true) String id) {
        ProductCategoryBO bo = productCategoryApplicationService.findById(id);
        return CodeResult.ok(ProductCategoryResponse.from(bo));
    }

    @GetMapping("findTreeList")
    @Operation(summary = "产品分类树形列表")
    public CodeResult<List<ProductCategoryResponse>> findTreeList() {
        List<ProductCategoryBO> bos =productCategoryApplicationService.findAll();
        List<ProductCategoryResponse> responses=ProductCategoryResponse.from(bos);
        responses.sort(Comparator.comparing(ProductCategoryResponse::getSort));
        return CodeResult.ok(TreeUtil.build(responses));
    }

}
