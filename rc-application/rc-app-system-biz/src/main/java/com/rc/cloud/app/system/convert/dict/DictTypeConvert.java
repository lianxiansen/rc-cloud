package com.rc.cloud.app.system.convert.dict;

import com.rc.cloud.app.system.api.dict.model.SysDictTypeDO;
import com.rc.cloud.app.system.vo.dict.type.*;
import com.rc.cloud.common.core.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DictTypeConvert {

    DictTypeConvert INSTANCE = Mappers.getMapper(DictTypeConvert.class);

    PageResult<DictTypeRespVO> convertPage(PageResult<SysDictTypeDO> bean);

    DictTypeRespVO convert(SysDictTypeDO bean);

    SysDictTypeDO convert(DictTypeCreateReqVO bean);

    SysDictTypeDO convert(DictTypeUpdateReqVO bean);

    List<DictTypeSimpleRespVO> convertList(List<SysDictTypeDO> list);

    List<DictTypeExcelVO> convertList02(List<SysDictTypeDO> list);

}
