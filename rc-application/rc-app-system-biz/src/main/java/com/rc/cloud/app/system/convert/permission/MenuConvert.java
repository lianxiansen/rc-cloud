package com.rc.cloud.app.system.convert.permission;

import com.rc.cloud.app.system.model.permission.SysMenuPO;
import com.rc.cloud.app.system.vo.permission.menu.MenuCreateReqVO;
import com.rc.cloud.app.system.vo.permission.menu.MenuRespVO;
import com.rc.cloud.app.system.vo.permission.menu.MenuSimpleRespVO;
import com.rc.cloud.app.system.vo.permission.menu.MenuUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MenuConvert {

    MenuConvert INSTANCE = Mappers.getMapper(MenuConvert.class);

    List<MenuRespVO> convertList(List<SysMenuPO> list);

    SysMenuPO convert(MenuCreateReqVO bean);

    SysMenuPO convert(MenuUpdateReqVO bean);

    MenuRespVO convert(SysMenuPO bean);

    List<MenuSimpleRespVO> convertList02(List<SysMenuPO> list);

}
