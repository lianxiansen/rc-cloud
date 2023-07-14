package com.rc.cloud.app.system.mapper.permission;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rc.cloud.app.system.model.permission.SysRolePO;
import com.rc.cloud.app.system.vo.permission.role.RoleExportReqVO;
import com.rc.cloud.app.system.vo.permission.role.RolePageReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import jodd.util.StringUtil;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 角色mapper
 */
@Mapper
public interface RoleMapper extends BaseMapperX<SysRolePO> {

    /**
     * 分页查询
     *
     * @param reqVO 查询参数
     * @return 分页查询结果
     */
    default PageResult<SysRolePO> selectPage(RolePageReqVO reqVO) {
        if (StringUtil.isEmpty(reqVO.getOrder())) {
            reqVO.setOrder("id");
            reqVO.setAsc(false);
        }
        QueryWrapper<SysRolePO> wrapper = new QueryWrapper<>();
        wrapper.lambda().like(StringUtil.isNotEmpty(reqVO.getName()), SysRolePO::getName, reqVO.getName())
                .like(StringUtil.isNotEmpty(reqVO.getCode()), SysRolePO::getCode, reqVO.getCode())
                .eq(reqVO.getStatus() != null, SysRolePO::getStatus, reqVO.getStatus());
        wrapper.orderBy(true, reqVO.getAsc(), StrUtil.toUnderlineCase(reqVO.getOrder()));
        return selectPage(reqVO, wrapper);
    }

    /**
     * 查询列表
     *
     * @param reqVO 查询参数
     * @return 查询结果
     */
    default List<SysRolePO> selectList(RoleExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SysRolePO>()
                .likeIfPresent(SysRolePO::getName, reqVO.getName())
                .likeIfPresent(SysRolePO::getCode, reqVO.getCode())
                .eqIfPresent(SysRolePO::getStatus, reqVO.getStatus())
                .betweenIfPresent(BaseDO::getCreateTime, reqVO.getCreateTime()));
    }

    /**
     * 根据名称查询
     *
     * @param name 名称
     * @return 查询结果
     */
    default SysRolePO selectByName(String name) {
        return selectOne(SysRolePO::getName, name);
    }

    /**
     * 根据code查询
     *
     * @param code 编码
     * @return 查询结果
     */
    default SysRolePO selectByCode(String code) {
        return selectOne(SysRolePO::getCode, code);
    }

    /**
     * 根据状态查询
     *
     * @param status 状态
     * @return 查询结果
     */
    default List<SysRolePO> selectListByStatus(@Nullable Collection<Integer> status) {
        return selectList(SysRolePO::getStatus, status);
    }

    /**
     * 根据角色id列表查询角色列表
     *
     * @param roleIds 角色id列表
     * @return 角色列表
     */
    default List<SysRolePO> listRolesByRoleIds(Set<String> roleIds) {
        QueryWrapper<SysRolePO> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(SysRolePO::getId, roleIds);
        return selectList(wrapper);
    }

    /**
     * 根据角色id列表查询角色编码列表
     *
     * @param roleIds 角色id列表
     * @return 角色编码列表
     */
    default Set<String> selectCodesByIds(Set<String> roleIds) {
        return listRolesByRoleIds(roleIds).stream().map(SysRolePO::getCode).collect(Collectors.toSet());
    }
}
