package com.rc.cloud.app.system.convert.permission;

import com.rc.cloud.app.system.api.permission.vo.SysRoleVO;
import com.rc.cloud.app.system.model.permission.SysRolePO;
import com.rc.cloud.app.system.service.permission.bo.RoleCreateReqBO;
import com.rc.cloud.app.system.vo.permission.role.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 角色转换类
 */
@Mapper
public interface RoleConvert {

    RoleConvert INSTANCE = Mappers.getMapper(RoleConvert.class);

    /**
     * 更新请求参数转换
     *
     * @param bean 分页查询结果
     * @return 分页查询结果
     */
    SysRolePO convert(RoleUpdateReqVO bean);

    /**
     * po列表转换vo列表
     *
     * @param list List<SysRolePO>
     * @return List<SysRoleVO>
     */
    List<SysRoleVO> convertToVOList(List<SysRolePO> list);

    /**
     * po转换vo
     *
     * @param bean SysRolePO
     * @return SysRoleVO
     */
    RoleRespVO convert(SysRolePO bean);

    /**
     * 创建请求参数VO转换PO
     *
     * @param bean RoleCreateReqVO
     * @return SysRolePO
     */
    SysRolePO convert(RoleCreateReqVO bean);

    /**
     * po列表转换vo列表
     *
     * @param list List<SysRolePO>
     * @return List<RoleSimpleRespVO>
     */
    List<RoleSimpleRespVO> convertList02(List<SysRolePO> list);

    /**
     * 创建请求参数BO转换PO
     *
     * @param bean RoleCreateReqBO
     * @return SysRolePO
     */
    SysRolePO convert(RoleCreateReqBO bean);

}
