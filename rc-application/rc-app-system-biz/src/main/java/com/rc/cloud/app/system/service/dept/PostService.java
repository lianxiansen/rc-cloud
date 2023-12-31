package com.rc.cloud.app.system.service.dept;

import com.rc.cloud.app.system.model.dept.SysPostPO;
import com.rc.cloud.app.system.vo.dept.post.PostCreateReqVO;
import com.rc.cloud.app.system.vo.dept.post.PostExportReqVO;
import com.rc.cloud.app.system.vo.dept.post.PostPageReqVO;
import com.rc.cloud.app.system.vo.dept.post.PostUpdateReqVO;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.core.pojo.PageResult;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;

import static com.rc.cloud.common.core.util.collection.SetUtils.asSet;


/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 岗位服务接口
 */
public interface PostService {

    /**
     * 创建岗位
     *
     * @param reqVO 岗位信息
     * @return 岗位编号
     */
    String createPost(PostCreateReqVO reqVO);

    /**
     * 更新岗位
     *
     * @param reqVO 岗位信息
     */
    void updatePost(PostUpdateReqVO reqVO);

    /**
     * 删除岗位信息
     *
     * @param id 岗位编号
     */
    void deletePost(String id);

    /**
     * 获得岗位列表
     *
     * @param ids 岗位编号数组。如果为空，不进行筛选
     * @return 部门列表
     */
    default List<SysPostPO> getPostList(@Nullable Collection<String> ids) {
        return getPostList(ids, asSet(CommonStatusEnum.ENABLE.getStatus(), CommonStatusEnum.DISABLE.getStatus()));
    }

    /**
     * 获得符合条件的岗位列表
     *
     * @param ids      岗位编号数组。如果为空，不进行筛选
     * @param statuses 状态数组。如果为空，不进行筛选
     * @return 部门列表
     */
    List<SysPostPO> getPostList(@Nullable Collection<String> ids, @Nullable Collection<Integer> statuses);

    /**
     * 获得岗位分页列表
     *
     * @param reqVO 分页条件
     * @return 部门分页列表
     */
    PageResult<SysPostPO> getPostPage(PostPageReqVO reqVO);

    /**
     * 获得岗位列表
     *
     * @param reqVO 查询条件
     * @return 部门列表
     */
    List<SysPostPO> getPostList(PostExportReqVO reqVO);

    /**
     * 获得岗位信息
     *
     * @param id 岗位编号
     * @return 岗位信息
     */
    SysPostPO getPost(String id);

    /**
     * 校验岗位们是否有效。如下情况，视为无效：
     * 1. 岗位编号不存在
     * 2. 岗位被禁用
     *
     * @param ids 岗位编号数组
     */
    void validatePostList(Collection<String> ids);

    /**
     * 批量删除岗位
     *
     * @param idList 岗位编号数组
     */
    void deletePosts(List<String> idList);
}
