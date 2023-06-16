package com.rc.cloud.app.system.mapper.dict;

import com.rc.cloud.app.system.api.dict.entity.SysDictTypeDO;
import com.rc.cloud.app.system.vo.dict.type.DictTypeExportReqVO;
import com.rc.cloud.app.system.vo.dict.type.DictTypePageReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface DictTypeMapper extends BaseMapperX<SysDictTypeDO> {

    default PageResult<SysDictTypeDO> selectPage(DictTypePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysDictTypeDO>()
                .likeIfPresent(SysDictTypeDO::getName, reqVO.getName())
                .likeIfPresent(SysDictTypeDO::getType, reqVO.getType())
                .eqIfPresent(SysDictTypeDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(SysDictTypeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SysDictTypeDO::getId));
    }

    default List<SysDictTypeDO> selectList(DictTypeExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SysDictTypeDO>()
                .likeIfPresent(SysDictTypeDO::getName, reqVO.getName())
                .likeIfPresent(SysDictTypeDO::getType, reqVO.getType())
                .eqIfPresent(SysDictTypeDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(SysDictTypeDO::getCreateTime, reqVO.getCreateTime()));
    }

    default SysDictTypeDO selectByType(String type) {
        return selectOne(SysDictTypeDO::getType, type);
    }

    default SysDictTypeDO selectByName(String name) {
        return selectOne(SysDictTypeDO::getName, name);
    }

    int deleteById(@Param("id") Long id, @Param("deletedTime") LocalDateTime deletedTime);

    @Update("UPDATE sys_dict_type SET deleted = 1 WHERE id = #{id}")
    void updateToDelete(@Param("id") Long id);
}
