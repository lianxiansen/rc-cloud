package com.rc.cloud.baseconfig.controller;

import com.rc.cloud.baseconfig.service.DistrictService;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WJF
 * @create 2023-06-26 10:45
 * @description TODO
 */
@RestController
@RequestMapping("/baseconfig/")
public class BaseController {

    @Autowired
    private DistrictService districtService;

    /*
     * 获取全国所有省
     */
    @Operation(summary = "获取省")
    @GetMapping("/getAllProvince")
    public CodeResult getAllProvince() {
        return CodeResult.ok(districtService.getAllProvince());
    }

    /*
     * 获取全国所有省市区
     */
    @Operation(summary = "根据上级行政编码获取所有地区")
    @GetMapping("/getByParentCode/{code}")
    public CodeResult getByParentCode(@PathVariable String code) {
        return CodeResult.ok(districtService.getByParentCode(code));
    }
}
