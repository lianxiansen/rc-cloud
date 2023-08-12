package com.rc.cloud.app.operate.appearance.admin.v1;

import com.rc.cloud.app.operate.appearance.admin.v1.req.*;
import com.rc.cloud.app.operate.appearance.admin.v1.res.BrandResponse;
import com.rc.cloud.app.operate.application.bo.BrandBO;
import com.rc.cloud.app.operate.application.dto.BrandCreateDTO;
import com.rc.cloud.app.operate.application.dto.BrandQueryDTO;
import com.rc.cloud.app.operate.application.dto.BrandQueryPageDTO;
import com.rc.cloud.app.operate.application.dto.BrandUpdateDTO;
import com.rc.cloud.app.operate.application.service.BrandApplicationService;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.web.filter.RepeatSubmit;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Tag(name = "品牌")
@RestController
@RequestMapping("/admin/brand")
@Validated
public class BrandController {

    @Autowired
    private BrandApplicationService brandApplicationService;
    @RepeatSubmit
    @PostMapping("create")
    @Operation(summary = "创建品牌")
    public CodeResult<BrandResponse> create(@Valid @RequestBody BrandCreateRequest request) {
        BrandCreateDTO dto=new BrandCreateDTO();
        BeanUtils.copyProperties(request,dto);
        return CodeResult.ok(BrandResponse.from(brandApplicationService.create(dto)));
    }

    @RepeatSubmit
    @PostMapping("update")
    @Operation(summary = "更新品牌")
    public CodeResult<BrandResponse> update(@Valid @RequestBody BrandUpdateRequest request) {
        BrandUpdateDTO dto=new BrandUpdateDTO();
        BeanUtils.copyProperties(request,dto);
        return CodeResult.ok(BrandResponse.from(brandApplicationService.update(dto)));
    }

    @PostMapping("remove")
    @Operation(summary = "删除品牌")
    public CodeResult remove(@Valid @RequestBody BrandGetRequest request) {
        if (brandApplicationService.remove(request.getId())) {
            return CodeResult.ok();
        }
        return CodeResult.fail();
    }


    @PostMapping("selectPageResult")
    @Operation(summary = "品牌分页查询")
    public CodeResult<PageResult<BrandResponse>> selectPageResult(@Valid @RequestBody BrandQueryPageRequest request) {
        BrandQueryPageDTO dto=new BrandQueryPageDTO();
        BeanUtils.copyProperties(request,dto);
        PageResult<BrandBO> boList = brandApplicationService.selectPageResult(dto);
        return CodeResult.ok(BrandResponse.from(boList));
    }


    @PostMapping("findById")
    @Operation(summary = "根据唯一标识查找品牌")
    public CodeResult<BrandResponse> findById(@Valid @RequestBody BrandGetRequest request) {
        BrandBO bo = brandApplicationService.findById(request.getId());
        return CodeResult.ok(BrandResponse.from(bo));
    }


    @PostMapping("findList")
    @Operation(summary = "品牌列表查询")
    public CodeResult<List<BrandResponse>> findList(@Valid BrandQueryRequest request) {
        BrandQueryDTO dto=new BrandQueryDTO();
        BeanUtils.copyProperties(request,dto);
        List<BrandBO> boList = brandApplicationService.findList(dto);
        return CodeResult.ok(BrandResponse.from(boList));
    }

}
