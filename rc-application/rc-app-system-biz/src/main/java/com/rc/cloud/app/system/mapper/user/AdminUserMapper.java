package com.rc.cloud.app.system.mapper.user;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.rc.cloud.app.system.model.user.SysUserPO;
import com.rc.cloud.app.system.vo.user.user.UserExportReqVO;
import com.rc.cloud.app.system.vo.user.user.UserPageReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import jodd.util.StringUtil;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Mapper
public interface AdminUserMapper extends BaseMapperX<SysUserPO> {

    default SysUserPO selectByUsername(String username) {
        return selectOne(SysUserPO::getUsername, username);
    }

    default SysUserPO selectByEmail(String email) {
        return selectOne(SysUserPO::getEmail, email);
    }

    default SysUserPO selectByMobile(String mobile) {
        return selectOne(SysUserPO::getMobile, mobile);
    }

    default PageResult<SysUserPO> selectPage(UserPageReqVO reqVO, Collection<String> deptIds) {
        if (StringUtil.isEmpty(reqVO.getOrder())) {
            reqVO.setOrder("id");
            reqVO.setAsc(false);
        }
        QueryWrapper<SysUserPO> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .like(StringUtil.isNotEmpty(reqVO.getUsername()), SysUserPO::getUsername, reqVO.getUsername())
                .like(StringUtil.isNotEmpty(reqVO.getMobile()), SysUserPO::getMobile, reqVO.getMobile())
                .eq(reqVO.getStatus() != null, SysUserPO::getStatus, reqVO.getStatus())
                .eq(reqVO.getSex() != null, SysUserPO::getSex, reqVO.getSex())
                .in(CollectionUtils.isNotEmpty(deptIds), SysUserPO::getDeptId, deptIds);
        wrapper.orderBy(true, reqVO.getAsc(), StrUtil.toUnderlineCase(reqVO.getOrder()));
        return selectPage(reqVO, wrapper);
    }

    default List<SysUserPO> selectList(UserExportReqVO reqVO, Collection<String> deptIds) {
        return selectList(new LambdaQueryWrapperX<SysUserPO>()
                .likeIfPresent(SysUserPO::getUsername, reqVO.getUsername())
                .likeIfPresent(SysUserPO::getMobile, reqVO.getMobile())
                .eqIfPresent(SysUserPO::getStatus, reqVO.getStatus())
                .betweenIfPresent(SysUserPO::getCreateTime, reqVO.getCreateTime())
                .inIfPresent(SysUserPO::getDeptId, deptIds));
    }

    default List<SysUserPO> selectListByNickname(String nickname) {
        return selectList(new LambdaQueryWrapperX<SysUserPO>().like(SysUserPO::getNickname, nickname));
    }

    default List<SysUserPO> selectListByStatus(Integer status) {
        return selectList(SysUserPO::getStatus, status);
    }

    default List<SysUserPO> selectListByDeptIds(Collection<String> deptIds) {
        return selectList(SysUserPO::getDeptId, deptIds);
    }

    default Optional<SysUserPO> findOptionalByUsername(String username) {
        SysUserPO sysUserPO = selectOne(new LambdaQueryWrapperX<SysUserPO>().eq(SysUserPO::getUsername, username));
        if (sysUserPO == null) {
            return Optional.empty();
        }
        return Optional.of(sysUserPO);
    }
}
