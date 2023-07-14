package com.rc.cloud.app.system.mapper.dict;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rc.cloud.app.system.model.dict.SysDictDataPO;
import com.rc.cloud.app.system.vo.dict.data.DictDataExportReqVO;
import com.rc.cloud.app.system.vo.dict.data.DictDataPageReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 字典数据表 Mapper
 */
@Mapper
public interface DictDataMapper extends BaseMapperX<SysDictDataPO> {

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType 字典类型
     * @param value    字典键值
     * @return 字典数据信息
     */
    default SysDictDataPO selectByDictTypeAndValue(String dictType, String value) {
        return selectOne(SysDictDataPO::getDictType, dictType, SysDictDataPO::getValue, value);
    }

    /**
     * 根据字典类型和字典标签查询字典数据信息
     *
     * @param dictType 字典类型
     * @param label    字典标签
     * @return 字典数据信息
     */
    default SysDictDataPO selectByDictTypeAndLabel(String dictType, String label) {
        return selectOne(SysDictDataPO::getDictType, dictType, SysDictDataPO::getLabel, label);
    }

    /**
     * 根据字典类型和字典键值集合查询字典数据信息
     *
     * @param dictType 字典类型
     * @param values   字典键值集合
     * @return 字典数据信息集合
     */
    default List<SysDictDataPO> selectByDictTypeAndValues(String dictType, Collection<String> values) {
        return selectList(new LambdaQueryWrapper<SysDictDataPO>().eq(SysDictDataPO::getDictType, dictType)
                .in(SysDictDataPO::getValue, values));
    }

    /**
     * 根据字典类型查询字典数据信息
     *
     * @param dictType 字典类型
     * @return 字典数据信息集合
     */
    default long selectCountByDictType(String dictType) {
        return selectCount(SysDictDataPO::getDictType, dictType);
    }

    /**
     * 根据字典类型查询字典数据信息
     *
     * @param reqVO 字典类型
     * @return 字典数据信息集合
     */
    default PageResult<SysDictDataPO> selectPage(DictDataPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysDictDataPO>()
                .likeIfPresent(SysDictDataPO::getLabel, reqVO.getLabel())
                .eqIfPresent(SysDictDataPO::getDictType, reqVO.getDictType())
                .eqIfPresent(SysDictDataPO::getStatus, reqVO.getStatus())
                .orderByDesc(Arrays.asList(SysDictDataPO::getDictType, SysDictDataPO::getSort)));
    }

    /**
     * 根据字典类型查询字典数据信息
     *
     * @param reqVO 字典类型
     * @return 字典数据信息集合
     */
    default List<SysDictDataPO> selectList(DictDataExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SysDictDataPO>()
                .likeIfPresent(SysDictDataPO::getLabel, reqVO.getLabel())
                .eqIfPresent(SysDictDataPO::getDictType, reqVO.getDictType())
                .eqIfPresent(SysDictDataPO::getStatus, reqVO.getStatus()));
    }

    /**
     * 根据sort升序查询字典数据列表信息
     *
     * @return 字典数据列表信息
     */
    default List<SysDictDataPO> selectListBySortAsc() {
        return selectList(new LambdaQueryWrapperX<SysDictDataPO>().orderByAsc(SysDictDataPO::getSort));
    }
}
