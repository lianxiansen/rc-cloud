package com.rc.cloud.app.system.convert.permission;

import com.rc.cloud.app.system.model.permission.SysMenuPO;
import com.rc.cloud.app.system.vo.permission.menu.MenuCreateReqVO;
import com.rc.cloud.app.system.vo.permission.menu.MenuRespVO;
import com.rc.cloud.app.system.vo.permission.menu.MenuSimpleRespVO;
import com.rc.cloud.app.system.vo.permission.menu.MenuUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 菜单转换类
 */
@Mapper
public interface MenuConvert {

    MenuConvert INSTANCE = Mappers.getMapper(MenuConvert.class);

    /**
     * PO列表查询结果转换VO列表
     *
     * @param list SysDictTypePO
     * @return DictTypeDetailRespVO
     */
    List<MenuRespVO> convertList(List<SysMenuPO> list);

    /**
     * 创建请求参数转换
     *
     * @param bean MenuCreateReqVO
     * @return SysMenuPO
     */
    SysMenuPO convert(MenuCreateReqVO bean);

    /**
     * 更新请求参数转换
     *
     * @param bean MenuUpdateReqVO
     * @return SysMenuPO
     */
    SysMenuPO convert(MenuUpdateReqVO bean);

    /**
     * PO查询结果转换VO
     *
     * @param bean SysMenuPO
     * @return MenuRespVO
     */
    MenuRespVO convert(SysMenuPO bean);

    /**
     * PO列表查询结果转换VO列表
     *
     * @param list SysDictTypePO
     * @return DictTypeDetailRespVO
     */
    List<MenuSimpleRespVO> convertList02(List<SysMenuPO> list);

}
