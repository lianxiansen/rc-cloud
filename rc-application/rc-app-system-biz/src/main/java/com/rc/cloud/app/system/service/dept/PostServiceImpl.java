package com.rc.cloud.app.system.service.dept;

import cn.hutool.core.collection.CollUtil;
import com.rc.cloud.app.system.model.dept.SysPostPO;
import com.rc.cloud.app.system.convert.dept.PostConvert;
import com.rc.cloud.app.system.mapper.dept.PostMapper;
import com.rc.cloud.app.system.vo.dept.post.PostCreateReqVO;
import com.rc.cloud.app.system.vo.dept.post.PostExportReqVO;
import com.rc.cloud.app.system.vo.dept.post.PostPageReqVO;
import com.rc.cloud.app.system.vo.dept.post.PostUpdateReqVO;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.core.pojo.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.rc.cloud.app.system.enums.ErrorCodeConstants.*;
import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;
import static com.rc.cloud.common.core.util.collection.CollectionUtils.convertMap;


/**
 * 岗位 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class PostServiceImpl implements PostService {

    @Resource
    private PostMapper postMapper;

    @Override
    public String createPost(PostCreateReqVO reqVO) {
        // 校验正确性
        validatePostForCreateOrUpdate(null, reqVO.getName(), reqVO.getCode());

        // 插入岗位
        SysPostPO post = PostConvert.INSTANCE.convert(reqVO);
        postMapper.insert(post);
        return post.getId();
    }

    @Override
    public void updatePost(PostUpdateReqVO reqVO) {
        // 校验正确性
        validatePostForCreateOrUpdate(reqVO.getId(), reqVO.getName(), reqVO.getCode());

        // 更新岗位
        SysPostPO updateObj = PostConvert.INSTANCE.convert(reqVO);
        postMapper.updateById(updateObj);
    }

    @Override
    public void deletePost(String id) {
        // 校验是否存在
        validatePostExists(id);
        // 删除部门
        postMapper.deleteById(id);
    }

    private void validatePostForCreateOrUpdate(String id, String name, String code) {
        // 校验自己存在
        validatePostExists(id);
        // 校验岗位名的唯一性
        validatePostNameUnique(id, name);
        // 校验岗位编码的唯一性
        validatePostCodeUnique(id, code);
    }

    private void validatePostNameUnique(String id, String name) {
        SysPostPO post = postMapper.selectByName(name);
        if (post == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的岗位
        if (id == null) {
            throw exception(POST_NAME_DUPLICATE);
        }
        if (!post.getId().equals(id)) {
            throw exception(POST_NAME_DUPLICATE);
        }
    }

    private void validatePostCodeUnique(String id, String code) {
        SysPostPO post = postMapper.selectByCode(code);
        if (post == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的岗位
        if (id == null) {
            throw exception(POST_CODE_DUPLICATE);
        }
        if (!post.getId().equals(id)) {
            throw exception(POST_CODE_DUPLICATE);
        }
    }

    private void validatePostExists(String id) {
        if (id == null) {
            return;
        }
        if (postMapper.selectById(id) == null) {
            throw exception(POST_NOT_FOUND);
        }
    }

    @Override
    public List<SysPostPO> getPostList(Collection<String> ids, Collection<Integer> statuses) {
        return postMapper.selectList(ids, statuses);
    }

    @Override
    public PageResult<SysPostPO> getPostPage(PostPageReqVO reqVO) {
        return postMapper.selectPage(reqVO);
    }

    @Override
    public List<SysPostPO> getPostList(PostExportReqVO reqVO) {
        return postMapper.selectList(reqVO);
    }

    @Override
    public SysPostPO getPost(String id) {
        return postMapper.selectById(id);
    }

    @Override
    public void validatePostList(Collection<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // 获得岗位信息
        List<SysPostPO> posts = postMapper.selectBatchIds(ids);
        Map<String, SysPostPO> postMap = convertMap(posts, SysPostPO::getId);
        // 校验
        ids.forEach(id -> {
            SysPostPO post = postMap.get(id);
            if (post == null) {
                throw exception(POST_NOT_FOUND);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(post.getStatus())) {
                throw exception(POST_NOT_ENABLE, post.getName());
            }
        });
    }

    @Override
    public void deletePosts(List<String> idList) {
        postMapper.deleteBatchIds(idList);
    }
}
