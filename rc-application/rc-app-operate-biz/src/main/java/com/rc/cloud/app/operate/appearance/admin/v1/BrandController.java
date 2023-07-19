package com.rc.cloud.app.operate.appearance.admin.v1;

import com.rc.cloud.app.operate.appearance.admin.res.BrandResponse;
import com.rc.cloud.app.operate.application.bo.BrandBO;
import com.rc.cloud.app.operate.application.dto.BrandCreateDTO;
import com.rc.cloud.app.operate.application.dto.BrandQueryPageDTO;
import com.rc.cloud.app.operate.application.dto.BrandUpdateDTO;
import com.rc.cloud.app.operate.application.service.BrandApplicationService;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Tag(name = "品牌")
@RestController
@RequestMapping("/operate/admin/brand")
@Validated
public class BrandController {

    @Autowired
    private BrandApplicationService brandApplicationService;

    @PostMapping("create")
    @Operation(summary = "创建品牌")
    public CodeResult<BrandResponse> create(@Valid @RequestBody BrandCreateDTO brandCreateDTO) {
        return CodeResult.ok(BrandResponse.from(brandApplicationService.create(brandCreateDTO)));
    }


    @PutMapping("update")
    @Operation(summary = "更新品牌")
    public CodeResult<BrandResponse> update(@Valid @RequestBody BrandUpdateDTO brandUpdateDTO) {
        return CodeResult.ok(BrandResponse.from(brandApplicationService.update(brandUpdateDTO)));
    }

    @DeleteMapping("remove")
    @Operation(summary = "删除品牌")
    public CodeResult remove(@RequestParam(name = "id",required = true) String id) {
        if (brandApplicationService.remove(id)) {
            return CodeResult.ok();
        }
        return CodeResult.fail();
    }


    @GetMapping("selectPageResult")
    @Operation(summary = "品牌分页查询")
    public CodeResult<PageResult<BrandResponse>> selectPageResult(@Valid BrandQueryPageDTO brandQueryPageDTO) {
        PageResult<BrandBO> boList = brandApplicationService.selectPageResult(brandQueryPageDTO);
        return CodeResult.ok(BrandResponse.from(boList));
    }

}
