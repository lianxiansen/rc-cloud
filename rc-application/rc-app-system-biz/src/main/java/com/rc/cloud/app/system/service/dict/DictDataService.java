package com.rc.cloud.app.system.service.dict;


import com.rc.cloud.app.system.model.dict.SysDictDataPO;
import com.rc.cloud.app.system.vo.dict.data.DictDataCreateReqVO;
import com.rc.cloud.app.system.vo.dict.data.DictDataExportReqVO;
import com.rc.cloud.app.system.vo.dict.data.DictDataPageReqVO;
import com.rc.cloud.app.system.vo.dict.data.DictDataUpdateReqVO;
import com.rc.cloud.common.core.pojo.PageResult;

import java.util.Collection;
import java.util.List;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 字典数据服务接口
 */
public interface DictDataService {

    /**
     * 创建字典数据
     *
     * @param reqVO 字典数据信息
     * @return 字典数据编号
     */
    String createDictData(DictDataCreateReqVO reqVO);

    /**
     * 更新字典数据
     *
     * @param reqVO 字典数据信息
     */
    void updateDictData(DictDataUpdateReqVO reqVO);

    /**
     * 删除字典数据
     *
     * @param id 字典数据编号
     */
    void deleteDictData(String id);

    /**
     * 获得字典数据列表
     *
     * @return 字典数据全列表
     */
    List<SysDictDataPO> getDictDataList();

    /**
     * 获得字典数据分页列表
     *
     * @param reqVO 分页请求
     * @return 字典数据分页列表
     */
    PageResult<SysDictDataPO> getDictDataPage(DictDataPageReqVO reqVO);

    /**
     * 获得字典数据列表
     *
     * @param reqVO 列表请求
     * @return 字典数据列表
     */
    List<SysDictDataPO> getDictDataList(DictDataExportReqVO reqVO);

    /**
     * 获得字典数据详情
     *
     * @param id 字典数据编号
     * @return 字典数据
     */
    SysDictDataPO getDictData(String id);

    /**
     * 获得指定字典类型的数据数量
     *
     * @param dictType 字典类型
     * @return 数据数量
     */
    long countByDictType(String dictType);

    /**
     * 校验字典数据们是否有效。如下情况，视为无效：
     * 1. 字典数据不存在
     * 2. 字典数据被禁用
     *
     * @param dictType 字典类型
     * @param values   字典数据值的数组
     */
    void validateDictDataList(String dictType, Collection<String> values);

    /**
     * 获得指定的字典数据
     *
     * @param dictType 字典类型
     * @param value    字典数据值
     * @return 字典数据
     */
    SysDictDataPO getDictData(String dictType, String value);

    /**
     * 解析获得指定的字典数据，从缓存中
     *
     * @param dictType 字典类型
     * @param label    字典数据标签
     * @return 字典数据
     */
    SysDictDataPO parseDictData(String dictType, String label);

    /**
     * 获得指定字典类型的字典数据列表
     *
     * @return 字典数据列表
     */
    List<SysDictDataPO> selectListBySortAsc();

    /**
     * 根据字典数据id列表删除字典数据
     *
     * @param idList 字典数据id列表
     */
    void deleteDictDatas(List<String> idList);
}
