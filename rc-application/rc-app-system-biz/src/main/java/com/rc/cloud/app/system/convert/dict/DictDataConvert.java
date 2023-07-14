package com.rc.cloud.app.system.convert.dict;

import com.rc.cloud.app.system.api.dict.dto.DictDataRespDTO;
import com.rc.cloud.app.system.model.dict.SysDictDataPO;
import com.rc.cloud.app.system.vo.dict.data.*;
import com.rc.cloud.common.core.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 字典数据转换类
 */
@Mapper
public interface DictDataConvert {

    DictDataConvert INSTANCE = Mappers.getMapper(DictDataConvert.class);

    /**
     * po列表转换为vo列表
     *
     * @param list List<SysDictDataPO>
     * @return List<DictDataSimpleRespVO>
     */
    List<DictDataSimpleRespVO> convertList(List<SysDictDataPO> list);

    /**
     * po转换为vo
     *
     * @param bean SysDictDataPO
     * @return DictDataRespVO
     */
    DictDataRespVO convert(SysDictDataPO bean);

    /**
     * 分页查询结果转换
     *
     * @param page 分页查询结果
     * @return 分页查询结果
     */
    PageResult<DictDataRespVO> convertPage(PageResult<SysDictDataPO> page);

    /**
     * 更新请求参数转换
     *
     * @param bean DictDataUpdateReqVO
     * @return SysDictDataPO
     */
    SysDictDataPO convert(DictDataUpdateReqVO bean);

    /**
     * 创建请求参数转换
     *
     * @param bean DictDataCreateReqVO
     * @return SysDictDataPO
     */
    SysDictDataPO convert(DictDataCreateReqVO bean);

    /**
     * excel导出转换
     *
     * @param bean List<SysDictDataPO>
     * @return List<DictDataExcelVO>
     */
    List<DictDataExcelVO> convertList02(List<SysDictDataPO> bean);

    /**
     * po转换为dto
     *
     * @param bean SysDictDataPO
     * @return DictDataRespDTO
     */
    DictDataRespDTO convert02(SysDictDataPO bean);
}
