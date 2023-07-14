package com.rc.cloud.app.system.mapper.dict;

import com.rc.cloud.app.system.model.dict.SysDictTypePO;
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
public interface DictTypeMapper extends BaseMapperX<SysDictTypePO> {

    default PageResult<SysDictTypePO> selectPage(DictTypePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysDictTypePO>()
                .likeIfPresent(SysDictTypePO::getName, reqVO.getName())
                .likeIfPresent(SysDictTypePO::getType, reqVO.getType())
                .eqIfPresent(SysDictTypePO::getStatus, reqVO.getStatus())
                .betweenIfPresent(SysDictTypePO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SysDictTypePO::getId));
    }

    default List<SysDictTypePO> selectList(DictTypeExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SysDictTypePO>()
                .likeIfPresent(SysDictTypePO::getName, reqVO.getName())
                .likeIfPresent(SysDictTypePO::getType, reqVO.getType())
                .eqIfPresent(SysDictTypePO::getStatus, reqVO.getStatus())
                .betweenIfPresent(SysDictTypePO::getCreateTime, reqVO.getCreateTime()));
    }

    default SysDictTypePO selectByType(String type) {
        return selectOne(SysDictTypePO::getType, type);
    }

    default SysDictTypePO selectByName(String name) {
        return selectOne(SysDictTypePO::getName, name);
    }

    int deleteById(@Param("id") String id, @Param("deletedTime") LocalDateTime deletedTime);

    @Update("UPDATE sys_dict_type SET deleted = 1 WHERE id = #{id}")
    void updateToDelete(@Param("id") String id);
}
