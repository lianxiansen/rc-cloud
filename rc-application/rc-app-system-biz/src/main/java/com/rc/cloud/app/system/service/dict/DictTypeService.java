package com.rc.cloud.app.system.service.dict;


import com.rc.cloud.app.system.model.dict.SysDictTypePO;
import com.rc.cloud.app.system.vo.dict.SysDictVO;
import com.rc.cloud.app.system.vo.dict.type.DictTypeCreateReqVO;
import com.rc.cloud.app.system.vo.dict.type.DictTypeExportReqVO;
import com.rc.cloud.app.system.vo.dict.type.DictTypePageReqVO;
import com.rc.cloud.app.system.vo.dict.type.DictTypeUpdateReqVO;
import com.rc.cloud.common.core.pojo.PageResult;

import java.util.List;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 字典类型服务接口
 */
public interface DictTypeService {

    /**
     * 创建字典类型
     *
     * @param reqVO 字典类型信息
     * @return 字典类型编号
     */
    String createDictType(DictTypeCreateReqVO reqVO);

    /**
     * 更新字典类型
     *
     * @param reqVO 字典类型信息
     */
    void updateDictType(DictTypeUpdateReqVO reqVO);

    /**
     * 删除字典类型
     *
     * @param id 字典类型编号
     */
    void deleteDictType(String id);

    /**
     * 获得字典类型分页列表
     *
     * @param reqVO 分页请求
     * @return 字典类型分页列表
     */
    PageResult<SysDictTypePO> getDictTypePage(DictTypePageReqVO reqVO);

    /**
     * 获得字典类型列表
     *
     * @param reqVO 列表请求
     * @return 字典类型列表
     */
    List<SysDictTypePO> getDictTypeList(DictTypeExportReqVO reqVO);

    /**
     * 获得字典类型详情
     *
     * @param id 字典类型编号
     * @return 字典类型
     */
    SysDictTypePO getDictTypeById(String id);

    /**
     * 获得字典类型详情
     *
     * @param type 字典类型
     * @return 字典类型详情
     */
    SysDictTypePO getDictTypeByType(String type);

    /**
     * 获得全部字典类型列表
     *
     * @return 字典类型列表
     */
    List<SysDictTypePO> getDictTypeList();

    /**
     * 获得全部字典列表
     *
     * @return 字典列表
     */
    List<SysDictVO> getDictList();

    /**
     * 批量删除字典类型
     *
     * @param idList 字典类型编号列表
     */
    void deleteDictTypes(List<String> idList);
}
