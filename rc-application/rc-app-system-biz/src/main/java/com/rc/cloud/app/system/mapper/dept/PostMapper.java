package com.rc.cloud.app.system.mapper.dept;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rc.cloud.app.system.model.dept.SysPostPO;
import com.rc.cloud.app.system.vo.dept.post.PostExportReqVO;
import com.rc.cloud.app.system.vo.dept.post.PostPageReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import jodd.util.StringUtil;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Mapper
public interface PostMapper extends BaseMapperX<SysPostPO> {

    default List<SysPostPO> selectList(Collection<String> ids, Collection<Integer> statuses) {
        return selectList(new LambdaQueryWrapperX<SysPostPO>()
                .inIfPresent(SysPostPO::getId, ids)
                .inIfPresent(SysPostPO::getStatus, statuses));
    }

    default PageResult<SysPostPO> selectPage(PostPageReqVO reqVO) {
        if (StringUtil.isEmpty(reqVO.getOrder())) {
            reqVO.setOrder("id");
            reqVO.setAsc(false);
        }
        QueryWrapper<SysPostPO> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .like(StringUtil.isNotEmpty(reqVO.getCode()), SysPostPO::getCode, reqVO.getCode())
                .like(StringUtil.isNotEmpty(reqVO.getName()), SysPostPO::getName, reqVO.getName())
                .eq(reqVO.getStatus() != null, SysPostPO::getStatus, reqVO.getStatus());
        wrapper.orderBy(true, reqVO.getAsc(), StrUtil.toUnderlineCase(reqVO.getOrder()));
        return selectPage(reqVO, wrapper);
    }

    default List<SysPostPO> selectList(PostExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SysPostPO>()
                .likeIfPresent(SysPostPO::getCode, reqVO.getCode())
                .likeIfPresent(SysPostPO::getName, reqVO.getName())
                .eqIfPresent(SysPostPO::getStatus, reqVO.getStatus()));
    }

    default SysPostPO selectByName(String name) {
        return selectOne(SysPostPO::getName, name);
    }

    default SysPostPO selectByCode(String code) {
        return selectOne(SysPostPO::getCode, code);
    }

    default List<SysPostPO> selectPostsByPostIds(Set<String> postIds) {
        return selectList(new LambdaQueryWrapperX<SysPostPO>()
                .inIfPresent(SysPostPO::getId, postIds));
    }
}