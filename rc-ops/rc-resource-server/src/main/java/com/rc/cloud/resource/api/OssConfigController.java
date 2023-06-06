package com.rc.cloud.resource.api;

import com.rc.cloud.common.core.validate.AddGroup;
import com.rc.cloud.common.core.validate.EditGroup;
import com.rc.cloud.common.core.validate.QueryGroup;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.mybatis.page.PageQuery;
import com.rc.cloud.common.mybatis.page.TableDataInfo;
import com.rc.cloud.resource.application.command.OssConfigCommand;
import com.rc.cloud.resource.application.service.OssConfigApplicationService;
import com.rc.cloud.resource.application.dto.OssConfigDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

/**
 * 对象存储配置Controller
 * @author hqf@rc
 * @date 2022-04-21
 */
@Validated
@Api(value = "对象存储配置控制器", tags = {"对象存储配置管理"})
@RestController
@RequestMapping("/oss/config")
public class OssConfigController {

    @Autowired
    private OssConfigApplicationService ossConfigApplicationService;

    /**
     * 查询对象存储配置列表
     */
    @ApiOperation("查询对象存储配置列表")
    @GetMapping("/list")
    public TableDataInfo<OssConfigDTO> list(@Validated(QueryGroup.class) OssConfigCommand bo, PageQuery pageQuery) {
        return ossConfigApplicationService.queryPageList(bo, pageQuery);
    }

    /**
     * 获取对象存储配置详细信息
     */
    @ApiOperation("获取对象存储配置详细信息")
    @GetMapping("/{ossConfigId}")
    public CodeResult<OssConfigDTO> getInfo(@ApiParam("OSS配置ID")
                                              @NotNull(message = "主键不能为空")
                                              @PathVariable("ossConfigId") String ossConfigId) {
        return CodeResult.ok(ossConfigApplicationService.queryById(ossConfigId));
    }

    /**
     * 新增对象存储配置
     */
    @ApiOperation("新增对象存储配置")
    @PostMapping()
    public CodeResult add(@Validated(AddGroup.class) @RequestBody OssConfigCommand cmd) {
        return CodeResult.ok(ossConfigApplicationService.insertByCmd(cmd) ? 1 : 0);
    }

    /**
     * 修改对象存储配置
     */
    @ApiOperation("修改对象存储配置")
    @PutMapping()
    public CodeResult edit(@Validated(EditGroup.class) @RequestBody OssConfigCommand cmd) {
        return CodeResult.ok(ossConfigApplicationService.updateByCmd(cmd) ? 1 : 0);
    }

    /**
     * 删除对象存储配置
     */
    @ApiOperation("删除对象存储配置")
    @DeleteMapping("/{ossConfigIds}")
    public CodeResult remove(@ApiParam("OSS配置ID串")
                                   @NotEmpty(message = "主键不能为空")
                                   @PathVariable String[] ossConfigIds) {
        return CodeResult.ok(ossConfigApplicationService.deleteWithValidByIds(Arrays.asList(ossConfigIds), true) ? 1 : 0);
    }

    /**
     * 状态修改
     */
    @ApiOperation("状态修改")
    @PutMapping("/changeStatus")
    public CodeResult changeStatus(@RequestBody OssConfigCommand cmd) {
        return CodeResult.ok(ossConfigApplicationService.updateOssConfigStatus(cmd));
    }
}
