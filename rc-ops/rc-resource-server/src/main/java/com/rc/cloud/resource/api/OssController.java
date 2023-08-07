package com.rc.cloud.resource.api;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpUtil;
import com.rc.cloud.common.core.exception.ServiceException2;
import com.rc.cloud.common.core.util.file.FileUtils;
import com.rc.cloud.common.core.validate.QueryGroup;
import com.rc.cloud.common.web.BaseController;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.mybatis.page.PageQuery;
import com.rc.cloud.common.mybatis.page.TableDataInfo;
import com.rc.cloud.resource.application.assembler.OssDTOAssembler;
import com.rc.cloud.resource.application.command.OssCommand;
import com.rc.cloud.resource.application.service.OssApplicationService;
import com.rc.cloud.resource.application.dto.OssDTO;
import com.rc.cloud.resource.application.service.OssQueryService;
import com.rc.cloud.resource.application.service.OssUploadApplicationService;
import com.rc.cloud.resource.domain.model.oss.Oss;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hqf@rc
 * @date 2022-04-25
 * 文件上传 控制层
 */
@Validated
@Api(value = "对象存储控制器", tags = {"对象存储管理"})
@RestController
@RequestMapping("/oss")
public class OssController extends BaseController {

    @Autowired
    private OssApplicationService ossApplicationService;

    @Autowired
    private OssUploadApplicationService ossUploadApplicationService;

    @Autowired
    private OssQueryService ossQueryService;

    /**
     * 查询OSS对象存储列表
     */
    @ApiOperation("查询OSS对象存储列表")
    @GetMapping("/list")
    public TableDataInfo<OssDTO> list(@Validated(QueryGroup.class) OssCommand bo, PageQuery pageQuery) {
        return ossQueryService.queryPage(bo, pageQuery);
    }

    /**
     * 上传OSS对象存储
     */
    @ApiOperation("上传OSS对象存储")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "file", value = "文件", paramType = "query", dataTypeClass = File.class, required = true)
    })
    @PostMapping("/upload")
    public CodeResult<Map<String, String>> upload(@RequestPart("file") MultipartFile file) {
        if (ObjectUtil.isNull(file)) {
            throw new ServiceException2("上传文件不能为空");
        }
        OssDTO ossDTO = ossUploadApplicationService.upload(file);
        Map<String, String> map = new HashMap<>(2);
        map.put("url", ossDTO.getUrl());
        map.put("fileName", ossDTO.getFileName());
        return CodeResult.ok(map);
    }

    @ApiOperation("下载OSS对象存储")
    @GetMapping("/download/{ossId}")
    public void download(@ApiParam("OSS对象ID") @PathVariable String ossId, HttpServletResponse response) throws IOException {
        Oss oss = ossQueryService.getById(ossId);
        OssDTO ossDTO = OssDTOAssembler.fromOss(oss);
        if (ObjectUtil.isNull(ossDTO)) {
            throw new ServiceException2("文件数据不存在!");
        }
        response.reset();
        FileUtils.setAttachmentResponseHeader(response, ossDTO.getOriginalName());
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE + "; charset=UTF-8");
        long data;
        try {
            data = HttpUtil.download(ossDTO.getUrl(), response.getOutputStream(), false);
        } catch (HttpException e) {
            if (e.getMessage().contains("403")) {
                throw new ServiceException2("无读取权限, 请在对应的OSS开启'公有读'权限!");
            } else {
                throw new ServiceException2(e.getMessage());
            }
        }
        response.setContentLength(Convert.toInt(data));
    }

    /**
     * 删除OSS对象存储
     */
    @ApiOperation("删除OSS对象存储")
    @DeleteMapping("/{ossIds}")
    public CodeResult<Void> remove(@ApiParam("OSS对象ID串")
                                   @NotEmpty(message = "主键不能为空")
                                   @PathVariable Long[] ossIds) {
        return toAjax(ossApplicationService.deleteWithValidByIds(Arrays.asList(ossIds), true) ? 1 : 0);
    }
}
