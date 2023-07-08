package com.rc.cloud.app.operate.appearance.facade.admin;

import com.rc.cloud.app.operate.appearance.vo.BrandVO;
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
@RequestMapping("/operate/brand")
@Validated
public class BrandController {

    @Autowired
    private BrandApplicationService brandApplicationService;

    @PostMapping("create")
    @Operation(summary = "创建品牌")
    public CodeResult<Long> create(@Valid @RequestBody BrandCreateDTO brandCreateDTO) {
        brandApplicationService.createBrand(brandCreateDTO);
        return CodeResult.ok();
    }


    @PostMapping("update")
    @Operation(summary = "更新品牌")
    public CodeResult<Long> update(@Valid @RequestBody BrandUpdateDTO brandUpdateDTO) {
        brandApplicationService.updateBrand(brandUpdateDTO);
        return CodeResult.ok();
    }

    @GetMapping("remove")
    @Operation(summary = "删除品牌")
    public CodeResult<Long> remove(String id) {
        brandApplicationService.remove(id);
        return CodeResult.ok();
    }



    @GetMapping("changeState")
    @Operation(summary = "修改品牌状态")
    public CodeResult<Long> changeState(String id) {
        brandApplicationService.changeState(id);
        return CodeResult.ok();
    }

    @GetMapping("selectPageResult")
    @Operation(summary = "品牌分页查询")
    public CodeResult<PageResult<BrandVO>> selectPageResult(BrandQueryPageDTO brandQueryPageDTO) {
        PageResult<BrandBO> boList = brandApplicationService.selectPageResult(brandQueryPageDTO);
        return CodeResult.ok(BrandVO.from(boList));
    }

}