package com.rc.cloud.app.system.mapper.dict;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rc.cloud.app.system.api.dict.model.SysDictDataDO;
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
public interface DictDataMapper extends BaseMapperX<SysDictDataDO> {

    default SysDictDataDO selectByDictTypeAndValue(String dictType, String value) {
        return selectOne(SysDictDataDO::getDictType, dictType, SysDictDataDO::getValue, value);
    }

    default SysDictDataDO selectByDictTypeAndLabel(String dictType, String label) {
        return selectOne(SysDictDataDO::getDictType, dictType, SysDictDataDO::getLabel, label);
    }

    default List<SysDictDataDO> selectByDictTypeAndValues(String dictType, Collection<String> values) {
        return selectList(new LambdaQueryWrapper<SysDictDataDO>().eq(SysDictDataDO::getDictType, dictType)
                .in(SysDictDataDO::getValue, values));
    }

    default long selectCountByDictType(String dictType) {
        return selectCount(SysDictDataDO::getDictType, dictType);
    }

    default PageResult<SysDictDataDO> selectPage(DictDataPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysDictDataDO>()
                .likeIfPresent(SysDictDataDO::getLabel, reqVO.getLabel())
                .eqIfPresent(SysDictDataDO::getDictType, reqVO.getDictType())
                .eqIfPresent(SysDictDataDO::getStatus, reqVO.getStatus())
                .orderByDesc(Arrays.asList(SysDictDataDO::getDictType, SysDictDataDO::getSort)));
    }

    default List<SysDictDataDO> selectList(DictDataExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SysDictDataDO>()
                .likeIfPresent(SysDictDataDO::getLabel, reqVO.getLabel())
                .eqIfPresent(SysDictDataDO::getDictType, reqVO.getDictType())
                .eqIfPresent(SysDictDataDO::getStatus, reqVO.getStatus()));
    }

}
