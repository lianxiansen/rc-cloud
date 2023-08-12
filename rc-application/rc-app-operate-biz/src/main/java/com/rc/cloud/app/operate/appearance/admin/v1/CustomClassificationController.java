package com.rc.cloud.app.operate.appearance.admin.v1;

import com.rc.cloud.app.operate.appearance.admin.resp.CustomClassificationResponse;
import com.rc.cloud.app.operate.application.bo.CustomClassificationBO;
import com.rc.cloud.app.operate.application.dto.CustomClassificationCreateDTO;
import com.rc.cloud.app.operate.application.dto.CustomClassificationGetDTO;
import com.rc.cloud.app.operate.application.dto.CustomClassificationUpdateDTO;
import com.rc.cloud.app.operate.application.service.CustomClassificationApplicationService;
import com.rc.cloud.common.core.util.tree.TreeUtil;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

@Tag(name = "自定义产品自定义分类")
@RestController
@RequestMapping("/admin/customClassification")
@Validated
public class CustomClassificationController {

    @Autowired
    private CustomClassificationApplicationService customClassificationApplicationService;

    @PostMapping("create")
    @Operation(summary = "创建自定义产品分类")
    public CodeResult<CustomClassificationResponse> create(@Valid @RequestBody CustomClassificationCreateDTO request) {
        return CodeResult.ok(CustomClassificationResponse.from(customClassificationApplicationService.createCustomClassification(request)));
    }


    @PostMapping("update")
    @Operation(summary = "更新自定义产品分类")
    public CodeResult<CustomClassificationResponse> update(@Valid @RequestBody CustomClassificationUpdateDTO request) {
        return CodeResult.ok(CustomClassificationResponse.from(customClassificationApplicationService.updateCustomClassification(request)));
    }

    @PostMapping("remove")
    @Operation(summary = "删除自定义产品分类")
    public CodeResult<Long> remove(@RequestBody CustomClassificationGetDTO customClassificationGetDTO) {
        if(customClassificationApplicationService.removeCustomClassification(customClassificationGetDTO.getId())){
            return CodeResult.ok();
        }
        return CodeResult.fail();
    }

    @PostMapping("findAll")
    @Operation(summary = "自定义产品分类列表")
    public CodeResult<List<CustomClassificationResponse>> findAll() {
        List<CustomClassificationBO> bos =customClassificationApplicationService.findCustomClassifications();
        List<CustomClassificationResponse> responses=CustomClassificationResponse.from(bos);
        return CodeResult.ok(responses);
    }

    @PostMapping("findById")
    @Operation(summary = "根据唯一标识查找自定义产品分类")
    public CodeResult<CustomClassificationResponse> findById(@RequestBody  CustomClassificationGetDTO customClassificationGetDTO) {
        CustomClassificationBO bo = customClassificationApplicationService.findCustomClassificationById(customClassificationGetDTO.getId());
        return CodeResult.ok(CustomClassificationResponse.from(bo));
    }

    @PostMapping("findTreeList")
    @Operation(summary = "自定义产品分类树形列表")
    public CodeResult<List<CustomClassificationResponse>> findTreeList() {
        List<CustomClassificationBO> bos =customClassificationApplicationService.findCustomClassifications();
        List<CustomClassificationResponse> responses=CustomClassificationResponse.from(bos);
        responses.sort(Comparator.comparing(CustomClassificationResponse::getSort));
        return CodeResult.ok(TreeUtil.build(responses));
    }

}
