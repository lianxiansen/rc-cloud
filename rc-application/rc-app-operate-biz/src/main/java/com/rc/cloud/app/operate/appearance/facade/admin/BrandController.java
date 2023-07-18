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
    public CodeResult<BrandBO> create(@Valid @RequestBody BrandCreateDTO brandCreateDTO) {
        return CodeResult.ok(brandApplicationService.create(brandCreateDTO));
    }


    @PostMapping("update")
    @Operation(summary = "更新品牌")
    public CodeResult<BrandBO> update(@Valid @RequestBody BrandUpdateDTO brandUpdateDTO) {
        return CodeResult.ok(brandApplicationService.update(brandUpdateDTO));
    }

    @GetMapping("remove")
    @Operation(summary = "删除品牌")
    public CodeResult<Boolean> remove(String id) {
        if(brandApplicationService.remove(id)){
            return CodeResult.ok();
        }
        return CodeResult.fail();
    }




    @PostMapping("selectPageResult")
    @Operation(summary = "品牌分页查询")
    public CodeResult<PageResult<BrandVO>> selectPageResult(@Valid @RequestBody BrandQueryPageDTO brandQueryPageDTO) {
        PageResult<BrandBO> boList = brandApplicationService.selectPageResult(brandQueryPageDTO);
        return CodeResult.ok(BrandVO.from(boList));
    }

}
