package com.rc.cloud.app.system.controller.admin.v1.dept;

import com.rc.cloud.app.system.convert.dept.PostConvert;
import com.rc.cloud.app.system.model.dept.SysPostPO;
import com.rc.cloud.app.system.service.dept.PostService;
import com.rc.cloud.app.system.vo.dept.post.*;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.rc.cloud.app.system.enums.ErrorCodeConstants.POST_NOT_FOUND;
import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 岗位管理
 */
@Tag(name = "管理后台 - 岗位")
@RestController
@RequestMapping("/admin/post")
@Validated
public class PostController {

    @Resource
    private PostService postService;

    /**
     * 创建岗位
     *
     * @param reqVO 岗位信息
     * @return 岗位编号
     */
    @PostMapping("/create")
    @Operation(summary = "创建岗位")
    @PreAuthorize("@pms.hasPermission('sys:post:create')")
    public CodeResult<String> createPost(@Valid @RequestBody PostCreateReqVO reqVO) {
        String postId = postService.createPost(reqVO);
        return CodeResult.ok(postId);
    }

    /**
     * 更新岗位
     *
     * @param reqVO 岗位信息
     * @return 是否成功
     */
    @PutMapping("/update")
    @Operation(summary = "修改岗位")
    @PreAuthorize("@pms.hasPermission('sys:post:update')")
    public CodeResult<Boolean> updatePost(@Valid @RequestBody PostUpdateReqVO reqVO) {
        postService.updatePost(reqVO);
        return CodeResult.ok(true);
    }

    /**
     * 批量删除岗位
     *
     * @param idList 岗位编号列表
     * @return 删除结果
     */
    @DeleteMapping()
    @Operation(summary = "删除岗位")
    @Parameter(name = "idList", description = "编号列表", required = true, example = "[1024,1025]")
    @PreAuthorize("@pms.hasPermission('sys:post:delete')")
    public CodeResult<Boolean> deletePost(@RequestBody List<String> idList) {
        postService.deletePosts(idList);
        return CodeResult.ok(true);
    }

    /**
     * 根据id获取岗位信息
     *
     * @param id 岗位编号
     * @return 岗位信息
     */
    @GetMapping(value = "/{id}")
    @Operation(summary = "获得岗位信息")
    @Parameter(name = "id", description = "岗位编号", required = true, example = "1024")
    @PreAuthorize("@pms.hasPermission('sys:post:query')")
    public CodeResult<PostRespVO> getPost(@PathVariable("id") String id) {
        SysPostPO postDO = postService.getPost(id);
        if (postDO == null) {
            throw exception(POST_NOT_FOUND);
        }
        return CodeResult.ok(PostConvert.INSTANCE.convert(postDO));
    }

    /**
     * 获取岗位精简信息列表
     *
     * @return 岗位精简信息列表
     */
    @GetMapping("/list-all-simple")
    @Operation(summary = "获取岗位精简信息列表", description = "只包含被开启的岗位，主要用于前端的下拉选项")
    public CodeResult<List<PostSimpleRespVO>> getSimplePostList() {
        // 获得岗位列表，只要开启状态的
        List<SysPostPO> list = postService.getPostList(null, Collections.singleton(CommonStatusEnum.ENABLE.getStatus()));
        // 排序后，返回给前端
        list.sort(Comparator.comparing(SysPostPO::getSort));
        return CodeResult.ok(PostConvert.INSTANCE.convertList02(list));
    }

    /**
     * 分页获取岗位列表
     *
     * @param reqVO 查询条件
     * @return 岗位列表
     */
    @GetMapping("/page")
    @Operation(summary = "获得岗位分页列表")
    @PreAuthorize("@pms.hasPermission('sys:post:query')")
    public CodeResult<PageResult<PostRespVO>> getPostPage(@Validated PostPageReqVO reqVO) {
        return CodeResult.ok(PostConvert.INSTANCE.convertPage(postService.getPostPage(reqVO)));
    }

//    @GetMapping("/export")
//    @Operation(summary = "岗位管理")
////    @PreAuthorize("@ss.hasPermission('system:post:export')")
////    @OperateLog(type = EXPORT)
//    public void export(HttpServletResponse response, @Validated PostExportReqVO reqVO) throws IOException {
//        List<SysPostDO> posts = postService.getPostList(reqVO);
//        List<PostExcelVO> data = PostConvert.INSTANCE.convertList03(posts);
//        // 输出
//        ExcelUtils.write(response, "岗位数据.xls", "岗位列表", PostExcelVO.class, data);
//    }

}
