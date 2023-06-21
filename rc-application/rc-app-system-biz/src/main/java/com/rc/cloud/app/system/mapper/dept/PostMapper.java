package com.rc.cloud.app.system.mapper.dept;

import com.rc.cloud.app.system.api.dept.entity.SysPostDO;
import com.rc.cloud.app.system.vo.dept.post.PostExportReqVO;
import com.rc.cloud.app.system.vo.dept.post.PostPageReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Mapper
public interface PostMapper extends BaseMapperX<SysPostDO> {

    default List<SysPostDO> selectList(Collection<Long> ids, Collection<Integer> statuses) {
        return selectList(new LambdaQueryWrapperX<SysPostDO>()
                .inIfPresent(SysPostDO::getId, ids)
                .inIfPresent(SysPostDO::getStatus, statuses));
    }

    default PageResult<SysPostDO> selectPage(PostPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysPostDO>()
                .likeIfPresent(SysPostDO::getCode, reqVO.getCode())
                .likeIfPresent(SysPostDO::getName, reqVO.getName())
                .eqIfPresent(SysPostDO::getStatus, reqVO.getStatus())
                .orderByDesc(SysPostDO::getId));
    }

    default List<SysPostDO> selectList(PostExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SysPostDO>()
                .likeIfPresent(SysPostDO::getCode, reqVO.getCode())
                .likeIfPresent(SysPostDO::getName, reqVO.getName())
                .eqIfPresent(SysPostDO::getStatus, reqVO.getStatus()));
    }

    default SysPostDO selectByName(String name) {
        return selectOne(SysPostDO::getName, name);
    }

    default SysPostDO selectByCode(String code) {
        return selectOne(SysPostDO::getCode, code);
    }

    default List<SysPostDO> selectPostsByPostIds(Set<Long> postIds) {
        return selectList(new LambdaQueryWrapperX<SysPostDO>()
                .inIfPresent(SysPostDO::getId, postIds));
    }
}