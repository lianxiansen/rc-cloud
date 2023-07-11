package com.rc.cloud.app.system.convert.dict;

import com.rc.cloud.app.system.api.dict.dto.DictDataRespDTO;
import com.rc.cloud.app.system.model.dict.SysDictDataPO;
import com.rc.cloud.app.system.vo.dict.data.*;
import com.rc.cloud.common.core.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DictDataConvert {

    DictDataConvert INSTANCE = Mappers.getMapper(DictDataConvert.class);

    List<DictDataSimpleRespVO> convertList(List<SysDictDataPO> list);

    DictDataRespVO convert(SysDictDataPO bean);

    PageResult<DictDataRespVO> convertPage(PageResult<SysDictDataPO> page);

    SysDictDataPO convert(DictDataUpdateReqVO bean);

    SysDictDataPO convert(DictDataCreateReqVO bean);

    List<DictDataExcelVO> convertList02(List<SysDictDataPO> bean);

    DictDataRespDTO convert02(SysDictDataPO bean);

}
