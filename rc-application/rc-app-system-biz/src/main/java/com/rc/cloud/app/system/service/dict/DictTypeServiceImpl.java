package com.rc.cloud.app.system.service.dict;

import cn.hutool.core.util.StrUtil;
import com.google.common.annotations.VisibleForTesting;
import com.rc.cloud.app.system.api.dict.entity.SysDictDataDO;
import com.rc.cloud.app.system.api.dict.entity.SysDictTypeDO;
import com.rc.cloud.app.system.convert.dict.DictTypeConvert;
import com.rc.cloud.app.system.mapper.dict.DictTypeMapper;
import com.rc.cloud.app.system.vo.dict.SysDictVO;
import com.rc.cloud.app.system.vo.dict.type.DictTypeCreateReqVO;
import com.rc.cloud.app.system.vo.dict.type.DictTypeExportReqVO;
import com.rc.cloud.app.system.vo.dict.type.DictTypePageReqVO;
import com.rc.cloud.app.system.vo.dict.type.DictTypeUpdateReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.rc.cloud.app.system.enums.ErrorCodeConstants.*;
import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;


/**
 * 字典类型 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Log4j2
public class DictTypeServiceImpl implements DictTypeService {

    @Resource
    private DictDataService dictDataService;

    @Resource
    private DictTypeMapper dictTypeMapper;

    @Override
    public PageResult<SysDictTypeDO> getDictTypePage(DictTypePageReqVO reqVO) {
        return dictTypeMapper.selectPage(reqVO);
    }

    @Override
    public List<SysDictTypeDO> getDictTypeList(DictTypeExportReqVO reqVO) {
        return dictTypeMapper.selectList(reqVO);
    }

    @Override
    public SysDictTypeDO getDictType(Long id) {
        return dictTypeMapper.selectById(id);
    }

    @Override
    public SysDictTypeDO getDictType(String type) {
        return dictTypeMapper.selectByType(type);
    }

    @Override
    public Long createDictType(DictTypeCreateReqVO reqVO) {
        // 校验正确性
        validateDictTypeForCreateOrUpdate(null, reqVO.getName(), reqVO.getType());

        // 插入字典类型
        SysDictTypeDO dictTypeDO = DictTypeConvert.INSTANCE.convert(reqVO);
        dictTypeMapper.insert(dictTypeDO);
        return dictTypeDO.getId();
    }

    @Override
    public void updateDictType(DictTypeUpdateReqVO reqVO) {
        // 校验正确性
        validateDictTypeForCreateOrUpdate(reqVO.getId(), reqVO.getName(), null);

        // 更新字典类型
        SysDictTypeDO updateObj = DictTypeConvert.INSTANCE.convert(reqVO);
        dictTypeMapper.updateById(updateObj);
    }

    @Override
    public void deleteDictType(Long id) {
        // 校验是否存在
        SysDictTypeDO dictType = validateDictTypeExists(id);
        // 校验是否有字典数据
        if (dictDataService.countByDictType(dictType.getType()) > 0) {
            throw exception(DICT_TYPE_HAS_CHILDREN);
        }
        // 删除字典类型
        dictTypeMapper.updateToDelete(id);
    }

    @Override
    public List<SysDictTypeDO> getDictTypeList() {
        return dictTypeMapper.selectList();
    }

    @Override
    public List<SysDictVO> getDictList() {
        // 全部字典类型列表
        List<SysDictTypeDO> typeList = dictTypeMapper.selectList();

        // 全部字典数据列表
        List<SysDictDataDO> dataList = dictDataService.selectListBySortAsc();

        // 全部字典列表
        List<SysDictVO> dictList = new ArrayList<>(typeList.size());
        for (SysDictTypeDO type : typeList) {
            SysDictVO dict = new SysDictVO();
            dict.setType(type.getType());
            for (SysDictDataDO data : dataList) {
                if (type.getType().equals(data.getDictType())) {
                    dict.getDataList().add(new SysDictVO.DictData(data.getLabel(), data.getValue(), data.getCssClass()));
                }
            }

            // 数据来源动态SQL
//            if (type.getDictSource() == DictSourceEnum.SQL.getValue()) {
//                // 增加动态列表
//                String sql = type.getDictSql();
//                try {
//                    dict.setDataList(sysDictDataRepository.getListForSql(sql));
//                } catch (Exception e) {
//                    log.error("增加动态字典异常: type=" + type, e);
//                }
//            }

            dictList.add(dict);
        }

        return dictList;
    }

    private void validateDictTypeForCreateOrUpdate(Long id, String name, String type) {
        // 校验自己存在
        validateDictTypeExists(id);
        // 校验字典类型的名字的唯一性
        validateDictTypeNameUnique(id, name);
        // 校验字典类型的类型的唯一性
        validateDictTypeUnique(id, type);
    }

    @VisibleForTesting
    void validateDictTypeNameUnique(Long id, String name) {
        SysDictTypeDO dictType = dictTypeMapper.selectByName(name);
        if (dictType == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典类型
        if (id == null) {
            throw exception(DICT_TYPE_NAME_DUPLICATE);
        }
        if (!dictType.getId().equals(id)) {
            throw exception(DICT_TYPE_NAME_DUPLICATE);
        }
    }

    @VisibleForTesting
    void validateDictTypeUnique(Long id, String type) {
        if (StrUtil.isEmpty(type)) {
            return;
        }
        SysDictTypeDO dictType = dictTypeMapper.selectByType(type);
        if (dictType == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典类型
        if (id == null) {
            throw exception(DICT_TYPE_TYPE_DUPLICATE);
        }
        if (!dictType.getId().equals(id)) {
            throw exception(DICT_TYPE_TYPE_DUPLICATE);
        }
    }

    @VisibleForTesting
    SysDictTypeDO validateDictTypeExists(Long id) {
        if (id == null) {
            return null;
        }
        SysDictTypeDO dictType = dictTypeMapper.selectById(id);
        if (dictType == null) {
            throw exception(DICT_TYPE_NOT_EXISTS);
        }
        return dictType;
    }

}
