package com.rc.cloud.app.system.biz.convert.permission;

import com.rc.cloud.app.system.biz.model.permission.MenuDO;
import com.rc.cloud.app.system.biz.vo.permission.menu.MenuCreateReqVO;
import com.rc.cloud.app.system.biz.vo.permission.menu.MenuRespVO;
import com.rc.cloud.app.system.biz.vo.permission.menu.MenuSimpleRespVO;
import com.rc.cloud.app.system.biz.vo.permission.menu.MenuUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MenuConvert {

    MenuConvert INSTANCE = Mappers.getMapper(MenuConvert.class);

    List<MenuRespVO> convertList(List<MenuDO> list);

    MenuDO convert(MenuCreateReqVO bean);

    MenuDO convert(MenuUpdateReqVO bean);

    MenuRespVO convert(MenuDO bean);

    List<MenuSimpleRespVO> convertList02(List<MenuDO> list);

}
