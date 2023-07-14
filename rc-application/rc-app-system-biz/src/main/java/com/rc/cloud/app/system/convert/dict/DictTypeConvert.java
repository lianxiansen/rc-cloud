package com.rc.cloud.app.system.convert.dict;

import com.rc.cloud.app.system.model.dict.SysDictTypePO;
import com.rc.cloud.app.system.vo.dict.type.*;
import com.rc.cloud.common.core.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 字典类型转换类
 */
@Mapper
public interface DictTypeConvert {

    DictTypeConvert INSTANCE = Mappers.getMapper(DictTypeConvert.class);

    /**
     * 分页查询结果转换
     *
     * @param bean 分页查询结果
     * @return 分页查询结果
     */
    PageResult<DictTypeRespVO> convertPage(PageResult<SysDictTypePO> bean);

    /**
     * PO查询结果转换VO
     *
     * @param bean SysDictTypePO
     * @return DictTypeRespVO
     */
    DictTypeRespVO convert(SysDictTypePO bean);

    /**
     * 创建请求参数转换
     *
     * @param bean SysDictTypePO
     * @return DictTypeDetailRespVO
     */
    SysDictTypePO convert(DictTypeCreateReqVO bean);

    /**
     * 更新请求参数转换
     *
     * @param bean SysDictTypePO
     * @return DictTypeDetailRespVO
     */
    SysDictTypePO convert(DictTypeUpdateReqVO bean);

    /**
     * PO列表查询结果转换VO列表
     *
     * @param list SysDictTypePO
     * @return DictTypeDetailRespVO
     */
    List<DictTypeSimpleRespVO> convertList(List<SysDictTypePO> list);

    /**
     * PO列表查询结果转换VO列表
     *
     * @param list SysDictTypePO
     * @return DictTypeDetailRespVO
     */
    List<DictTypeExcelVO> convertList02(List<SysDictTypePO> list);

}
