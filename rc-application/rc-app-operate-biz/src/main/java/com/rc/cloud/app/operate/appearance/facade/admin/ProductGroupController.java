package com.rc.cloud.app.operate.appearance.facade.admin;

import com.rc.cloud.app.operate.appearance.vo.ProductGroupVO;
import com.rc.cloud.app.operate.application.bo.ProductGroupBO;
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
@RequestMapping("/operate/productGroup")
@Validated
public class ProductGroupController {

    @Autowired
    private ProductGroupApplicationService productGroupApplicationService;

    @PostMapping("create")
    @Operation(summary = "创建产品组合")
    public CodeResult<Long> create(@Valid @RequestBody ProductGroupCreateDTO ProductGroupCreateDTO) {
        productGroupApplicationService.createProductGroup(ProductGroupCreateDTO);
        return CodeResult.ok();
    }

    @GetMapping("release")
    @Operation(summary = "解除产品组合")
    public CodeResult<Long> release(String id) {
        productGroupApplicationService.release(id);
        return CodeResult.ok();
    }



    @GetMapping("appendGroupItem")
    @Operation(summary = "添加组合产品")
    public CodeResult<Long> appendGroupItem(@Valid @RequestBody ProductGroupItemCreateDTO productGroupCreateDTO) {
        productGroupApplicationService.appendGroupItem(productGroupCreateDTO);
        return CodeResult.ok();
    }

    @GetMapping("selectList")
    @Operation(summary = "检索产品组合列表")
    public CodeResult<List<ProductGroupVO>> selectList(String productId) {
        List<ProductGroupBO> boList = productGroupApplicationService.selectList(productId);
        return CodeResult.ok(ProductGroupVO.from(boList));
    }

}
