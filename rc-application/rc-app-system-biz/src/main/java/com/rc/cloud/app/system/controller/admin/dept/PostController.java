package com.rc.cloud.app.system.controller.admin.dept;

import com.rc.cloud.app.system.api.dept.entity.SysPostDO;
import com.rc.cloud.app.system.convert.dept.PostConvert;
import com.rc.cloud.app.system.service.dept.PostService;
import com.rc.cloud.app.system.vo.dept.post.*;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.excel.util.ExcelUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.rc.cloud.app.system.enums.ErrorCodeConstants.POST_NOT_FOUND;
import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;


@Tag(name = "管理后台 - 岗位")
@RestController
@RequestMapping("/sys/post")
@Validated
public class PostController {

    @Resource
    private PostService postService;

    @PostMapping("/create")
    @Operation(summary = "创建岗位")
//    @PreAuthorize("@ss.hasPermission('system:post:create')")
    public CodeResult<Long> createPost(@Valid @RequestBody PostCreateReqVO reqVO) {
        Long postId = postService.createPost(reqVO);
        return CodeResult.ok(postId);
    }

    @PutMapping("/update")
    @Operation(summary = "修改岗位")
//    @PreAuthorize("@ss.hasPermission('system:post:update')")
    public CodeResult<Boolean> updatePost(@Valid @RequestBody PostUpdateReqVO reqVO) {
        postService.updatePost(reqVO);
        return CodeResult.ok(true);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除岗位")
//    @PreAuthorize("@ss.hasPermission('system:post:delete')")
    public CodeResult<Boolean> deletePost(@PathVariable("id") Long id) {
        postService.deletePost(id);
        return CodeResult.ok(true);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "获得岗位信息")
    @Parameter(name = "id", description = "岗位编号", required = true, example = "1024")
//    @PreAuthorize("@ss.hasPermission('system:post:query')")
    public CodeResult<PostRespVO> getPost(@PathVariable("id") Long id) {
        SysPostDO postDO = postService.getPost(id);
        if (postDO == null) {
            throw exception(POST_NOT_FOUND);
        }
        return CodeResult.ok(PostConvert.INSTANCE.convert(postDO));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "获取岗位精简信息列表", description = "只包含被开启的岗位，主要用于前端的下拉选项")
    public CodeResult<List<PostSimpleRespVO>> getSimplePostList() {
        // 获得岗位列表，只要开启状态的
        List<SysPostDO> list = postService.getPostList(null, Collections.singleton(CommonStatusEnum.ENABLE.getStatus()));
        // 排序后，返回给前端
        list.sort(Comparator.comparing(SysPostDO::getSort));
        return CodeResult.ok(PostConvert.INSTANCE.convertList02(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得岗位分页列表")
//    @PreAuthorize("@ss.hasPermission('system:post:query')")
    public CodeResult<PageResult<PostRespVO>> getPostPage(@Validated PostPageReqVO reqVO) {
        return CodeResult.ok(PostConvert.INSTANCE.convertPage(postService.getPostPage(reqVO)));
    }

    @GetMapping("/export")
    @Operation(summary = "岗位管理")
//    @PreAuthorize("@ss.hasPermission('system:post:export')")
//    @OperateLog(type = EXPORT)
    public void export(HttpServletResponse response, @Validated PostExportReqVO reqVO) throws IOException {
        List<SysPostDO> posts = postService.getPostList(reqVO);
        List<PostExcelVO> data = PostConvert.INSTANCE.convertList03(posts);
        // 输出
        ExcelUtils.write(response, "岗位数据.xls", "岗位列表", PostExcelVO.class, data);
    }

}
