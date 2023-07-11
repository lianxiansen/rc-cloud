package com.rc.cloud.app.system.service.dict;

import cn.hutool.core.collection.CollUtil;
import com.google.common.annotations.VisibleForTesting;
import com.rc.cloud.app.system.model.dict.SysDictDataPO;
import com.rc.cloud.app.system.convert.dict.DictDataConvert;
import com.rc.cloud.app.system.mapper.dict.DictDataMapper;
import com.rc.cloud.app.system.model.dict.SysDictTypeDO;
import com.rc.cloud.app.system.vo.dict.data.DictDataCreateReqVO;
import com.rc.cloud.app.system.vo.dict.data.DictDataExportReqVO;
import com.rc.cloud.app.system.vo.dict.data.DictDataPageReqVO;
import com.rc.cloud.app.system.vo.dict.data.DictDataUpdateReqVO;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.util.collection.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.rc.cloud.app.system.enums.ErrorCodeConstants.*;
import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;


/**
 * 字典数据 Service 实现类
 *
 * @author ruoyi
 */
@Service
@Slf4j
public class DictDataServiceImpl implements DictDataService {

    /**
     * 排序 dictType > sort
     */
    private static final Comparator<SysDictDataPO> COMPARATOR_TYPE_AND_SORT = Comparator
            .comparing(SysDictDataPO::getDictType)
            .thenComparingInt(SysDictDataPO::getSort);

    @Resource
    private DictTypeService dictTypeService;

    @Resource
    private DictDataMapper dictDataMapper;

    @Override
    public List<SysDictDataPO> getDictDataList() {
        List<SysDictDataPO> list = dictDataMapper.selectList();
        list.sort(COMPARATOR_TYPE_AND_SORT);
        return list;
    }

    @Override
    public PageResult<SysDictDataPO> getDictDataPage(DictDataPageReqVO reqVO) {
        return dictDataMapper.selectPage(reqVO);
    }

    @Override
    public List<SysDictDataPO> getDictDataList(DictDataExportReqVO reqVO) {
        List<SysDictDataPO> list = dictDataMapper.selectList(reqVO);
        list.sort(COMPARATOR_TYPE_AND_SORT);
        return list;
    }

    @Override
    public SysDictDataPO getDictData(String id) {
        return dictDataMapper.selectById(id);
    }

    @Override
    public String createDictData(DictDataCreateReqVO reqVO) {
        // 校验正确性
        validateDictDataForCreateOrUpdate(null, reqVO.getValue(), reqVO.getDictType());

        // 插入字典类型
        SysDictDataPO dictData = DictDataConvert.INSTANCE.convert(reqVO);
        dictDataMapper.insert(dictData);
        return dictData.getId();
    }

    @Override
    public void updateDictData(DictDataUpdateReqVO reqVO) {
        // 校验正确性
        validateDictDataForCreateOrUpdate(reqVO.getId(), reqVO.getValue(), reqVO.getDictType());

        // 更新字典类型
        SysDictDataPO updateObj = DictDataConvert.INSTANCE.convert(reqVO);
        dictDataMapper.updateById(updateObj);
    }

    @Override
    public void deleteDictData(String id) {
        // 校验是否存在
        validateDictDataExists(id);

        // 删除字典数据
        dictDataMapper.deleteById(id);
    }

    @Override
    public long countByDictType(String dictType) {
        return dictDataMapper.selectCountByDictType(dictType);
    }

    private void validateDictDataForCreateOrUpdate(String id, String value, String dictType) {
        // 校验自己存在
        validateDictDataExists(id);
        // 校验字典类型有效
        validateDictTypeExists(dictType);
        // 校验字典数据的值的唯一性
        validateDictDataValueUnique(id, dictType, value);
    }

    @VisibleForTesting
    public void validateDictDataValueUnique(String id, String dictType, String value) {
        SysDictDataPO dictData = dictDataMapper.selectByDictTypeAndValue(dictType, value);
        if (dictData == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典数据
        if (id == null) {
            throw exception(DICT_DATA_VALUE_DUPLICATE);
        }
        if (!dictData.getId().equals(id)) {
            throw exception(DICT_DATA_VALUE_DUPLICATE);
        }
    }

    @VisibleForTesting
    public void validateDictDataExists(String id) {
        if (id == null) {
            return;
        }
        SysDictDataPO dictData = dictDataMapper.selectById(id);
        if (dictData == null) {
            throw exception(DICT_DATA_NOT_EXISTS);
        }
    }

    @VisibleForTesting
    public void validateDictTypeExists(String type) {
        SysDictTypeDO dictType = dictTypeService.getDictTypeByType(type);
        if (dictType == null) {
            throw exception(DICT_TYPE_NOT_EXISTS);
        }
        if (!CommonStatusEnum.ENABLE.getStatus().equals(dictType.getStatus())) {
            throw exception(DICT_TYPE_NOT_ENABLE);
        }
    }

    @Override
    public void validateDictDataList(String dictType, Collection<String> values) {
        if (CollUtil.isEmpty(values)) {
            return;
        }
        Map<String, SysDictDataPO> dictDataMap = CollectionUtils.convertMap(
                dictDataMapper.selectByDictTypeAndValues(dictType, values), SysDictDataPO::getValue);
        // 校验
        values.forEach(value -> {
            SysDictDataPO dictData = dictDataMap.get(value);
            if (dictData == null) {
                throw exception(DICT_DATA_NOT_EXISTS);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(dictData.getStatus())) {
                throw exception(DICT_DATA_NOT_ENABLE, dictData.getLabel());
            }
        });
    }

    @Override
    public SysDictDataPO getDictData(String dictType, String value) {
        return dictDataMapper.selectByDictTypeAndValue(dictType, value);
    }

    @Override
    public SysDictDataPO parseDictData(String dictType, String label) {
        return dictDataMapper.selectByDictTypeAndLabel(dictType, label);
    }

    @Override
    public List<SysDictDataPO> selectListBySortAsc() {
        return dictDataMapper.selectListBySortAsc();
    }

    @Override
    public void deleteDictDatas(List<String> idList) {
        dictDataMapper.deleteBatchIds(idList);
    }
}
