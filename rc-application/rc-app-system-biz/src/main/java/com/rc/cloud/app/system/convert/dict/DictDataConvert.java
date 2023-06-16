package com.rc.cloud.app.system.convert.dict;

import com.rc.cloud.app.system.api.dict.dto.DictDataRespDTO;
import com.rc.cloud.app.system.api.dict.model.SysDictDataDO;
import com.rc.cloud.app.system.vo.dict.data.*;
import com.rc.cloud.common.core.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DictDataConvert {

    DictDataConvert INSTANCE = Mappers.getMapper(DictDataConvert.class);

    List<DictDataSimpleRespVO> convertList(List<SysDictDataDO> list);

    DictDataRespVO convert(SysDictDataDO bean);

    PageResult<DictDataRespVO> convertPage(PageResult<SysDictDataDO> page);

    SysDictDataDO convert(DictDataUpdateReqVO bean);

    SysDictDataDO convert(DictDataCreateReqVO bean);

    List<DictDataExcelVO> convertList02(List<SysDictDataDO> bean);

    DictDataRespDTO convert02(SysDictDataDO bean);

}
