package com.rc.cloud.app.shop.appearance.controller;

import com.rc.cloud.app.shop.appearance.convert.ApplicationPageConfigConvert;
import com.rc.cloud.app.shop.appearance.req.ApplicationPageConfigCreateReqVO;
import com.rc.cloud.app.shop.appearance.req.ApplicationPageConfigPublishReqVO;
import com.rc.cloud.app.shop.appearance.req.ApplicationPageConfigSaveReqVO;
import com.rc.cloud.app.shop.appearance.resp.ApplicationPageConfigDataRespVO;
import com.rc.cloud.app.shop.appearance.resp.ApplicationPageConfigRespVO;
import com.rc.cloud.app.shop.application.dto.ApplicationPageConfigCreateDTO;
import com.rc.cloud.app.shop.application.dto.ApplicationPageConfigPublishDTO;
import com.rc.cloud.app.shop.application.dto.ApplicationPageConfigSaveDTO;
import com.rc.cloud.app.shop.application.service.ApplicationPageService;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author WJF
 * @create 2023-07-21 15:17
 * @description TODO
 */
@RestController
@RequestMapping("/shop/v1")
public class ApplicationPageController {

    @Resource
    private ApplicationPageService pageService;

    /**
     * 保存页面配置数据
     *
     * @param reqVO
     * @return CodeResult
     */
    @PutMapping("/savePageConfig")
    @Operation(summary = "保存页面配置数据")
    public CodeResult savePageConfig(ApplicationPageConfigSaveReqVO reqVO) {
        ApplicationPageConfigSaveDTO dto = ApplicationPageConfigConvert.INSTANCE.convert(reqVO);
        pageService.savePageConfig(dto);
        return CodeResult.ok();
    }

    /**
     * 创建页面配置
     *
     * @param reqVO
     * @return CodeResult
     */
    @PostMapping("/createPageConfig")
    @Operation(summary = "创建页面配置")
    public CodeResult createPageConfig(@Valid @RequestBody ApplicationPageConfigCreateReqVO reqVO) {
        ApplicationPageConfigCreateDTO dto = ApplicationPageConfigConvert.INSTANCE.convert(reqVO);
        pageService.createPageConfig(dto);
        return CodeResult.ok();
    }

    /**
     * 删除页面配置
     *
     * @param id
     * @return CodeResult
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除页面配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CodeResult deletePageConfig(@RequestParam("id") String id) {
        pageService.deletePageConfig(id);
        return CodeResult.ok();
    }

    /**
     * 获取配置数据
     *
     * @param id
     * @return CodeResult
     */
    @GetMapping("/get")
    @Operation(summary = "获取配置数据")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public ApplicationPageConfigDataRespVO getConfig(@RequestParam("id") String id) {
        return pageService.getConfig(id);
    }

    /**
     * 获取配置列表
     *
     * @return CodeResult
     */
    @GetMapping("/list")
    @Operation(summary = "获得配置列表")
    public CodeResult<List<ApplicationPageConfigRespVO>> getList() {
        return CodeResult.ok(pageService.getList());
    }

    /**
     * 发布页面
     *
     * @param reqVO
     * @return CodeResult
     */
    @DeleteMapping("/publish")
    @Operation(summary = "发布页面")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CodeResult publishPageConfig(ApplicationPageConfigPublishReqVO reqVO) {
        ApplicationPageConfigPublishDTO dto = ApplicationPageConfigConvert.INSTANCE.convert(reqVO);
        pageService.publishPageConfig(dto);
        return CodeResult.ok();
    }
}
