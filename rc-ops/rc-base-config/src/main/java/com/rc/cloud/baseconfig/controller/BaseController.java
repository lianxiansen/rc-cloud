package com.rc.cloud.baseconfig.controller;

import com.rc.cloud.baseconfig.service.DistrictService;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
     * 获取全国所有省市区
     */
    @Operation(summary = "获取省市区")
    @GetMapping("/getAllProvinceCity")
    public String getAllProvinceCity() {
        return districtService.getAllProvinceCity().toString();
    }
}
