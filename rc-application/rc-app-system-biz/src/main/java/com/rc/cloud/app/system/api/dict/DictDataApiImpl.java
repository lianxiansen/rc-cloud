package com.rc.cloud.app.system.api.dict;

import com.rc.cloud.app.system.api.dict.dto.DictDataRespDTO;
import com.rc.cloud.app.system.model.dict.SysDictDataPO;
import com.rc.cloud.app.system.convert.dict.DictDataConvert;
import com.rc.cloud.app.system.service.dict.DictDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 字典数据 API 实现类
 */
@Service
public class DictDataApiImpl implements DictDataApi {

    @Resource
    private DictDataService dictDataService;

    /**
     * 校验数据字典
     *
     * @param dictType 字典类型
     * @param values 字典值列表
     */
    @Override
    public void validateDictDataList(String dictType, Collection<String> values) {
        dictDataService.validateDictDataList(dictType, values);
    }

    /**
     * 获取数据字典
     *
     * @param dictType 字典类型
     * @param value 字典值
     */
    @Override
    public DictDataRespDTO getDictData(String dictType, String value) {
        SysDictDataPO dictData = dictDataService.getDictData(dictType, value);
        return DictDataConvert.INSTANCE.convert02(dictData);
    }

    /**
     * 解析数据字典
     *
     * @param dictType 字典类型
     * @param label 字典标签
     */
    @Override
    public DictDataRespDTO parseDictData(String dictType, String label) {
        SysDictDataPO dictData = dictDataService.parseDictData(dictType, label);
        return DictDataConvert.INSTANCE.convert02(dictData);
    }
}
