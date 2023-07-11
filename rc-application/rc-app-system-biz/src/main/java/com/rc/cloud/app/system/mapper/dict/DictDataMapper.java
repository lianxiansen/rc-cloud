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

@Mapper
public interface DictDataMapper extends BaseMapperX<SysDictDataPO> {

    default SysDictDataPO selectByDictTypeAndValue(String dictType, String value) {
        return selectOne(SysDictDataPO::getDictType, dictType, SysDictDataPO::getValue, value);
    }

    default SysDictDataPO selectByDictTypeAndLabel(String dictType, String label) {
        return selectOne(SysDictDataPO::getDictType, dictType, SysDictDataPO::getLabel, label);
    }

    default List<SysDictDataPO> selectByDictTypeAndValues(String dictType, Collection<String> values) {
        return selectList(new LambdaQueryWrapper<SysDictDataPO>().eq(SysDictDataPO::getDictType, dictType)
                .in(SysDictDataPO::getValue, values));
    }

    default long selectCountByDictType(String dictType) {
        return selectCount(SysDictDataPO::getDictType, dictType);
    }

    default PageResult<SysDictDataPO> selectPage(DictDataPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysDictDataPO>()
                .likeIfPresent(SysDictDataPO::getLabel, reqVO.getLabel())
                .eqIfPresent(SysDictDataPO::getDictType, reqVO.getDictType())
                .eqIfPresent(SysDictDataPO::getStatus, reqVO.getStatus())
                .orderByDesc(Arrays.asList(SysDictDataPO::getDictType, SysDictDataPO::getSort)));
    }

    default List<SysDictDataPO> selectList(DictDataExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SysDictDataPO>()
                .likeIfPresent(SysDictDataPO::getLabel, reqVO.getLabel())
                .eqIfPresent(SysDictDataPO::getDictType, reqVO.getDictType())
                .eqIfPresent(SysDictDataPO::getStatus, reqVO.getStatus()));
    }

    default List<SysDictDataPO> selectListBySortAsc() {
        return selectList(new LambdaQueryWrapperX<SysDictDataPO>().orderByAsc(SysDictDataPO::getSort));
    }
}
