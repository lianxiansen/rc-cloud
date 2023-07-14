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

import java.util.List;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 字典类型mapper
 */
@Mapper
public interface DictTypeMapper extends BaseMapperX<SysDictTypePO> {

    /**
     * 分页查询字典类型
     *
     * @param reqVO 查询参数
     * @return 分页查询结果
     */
    default PageResult<SysDictTypePO> selectPage(DictTypePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysDictTypePO>()
                .likeIfPresent(SysDictTypePO::getName, reqVO.getName())
                .likeIfPresent(SysDictTypePO::getType, reqVO.getType())
                .eqIfPresent(SysDictTypePO::getStatus, reqVO.getStatus())
                .betweenIfPresent(SysDictTypePO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SysDictTypePO::getId));
    }

    /**
     * 查询字典类型列表
     *
     * @param reqVO 查询参数
     * @return 查询结果
     */
    default List<SysDictTypePO> selectList(DictTypeExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SysDictTypePO>()
                .likeIfPresent(SysDictTypePO::getName, reqVO.getName())
                .likeIfPresent(SysDictTypePO::getType, reqVO.getType())
                .eqIfPresent(SysDictTypePO::getStatus, reqVO.getStatus())
                .betweenIfPresent(SysDictTypePO::getCreateTime, reqVO.getCreateTime()));
    }

    /**
     * 根据字典类型查询
     *
     * @param type 字典类型
     * @return 查询结果
     */
    default SysDictTypePO selectByType(String type) {
        return selectOne(SysDictTypePO::getType, type);
    }

    /**
     * 根据字典名称查询
     *
     * @param name 字典名称
     * @return 查询结果
     */
    default SysDictTypePO selectByName(String name) {
        return selectOne(SysDictTypePO::getName, name);
    }

    /**
     * 删除字典类型
     *
     * @param id 字典类型id
     */
    @Update("UPDATE sys_dict_type SET deleted = 1 WHERE id = #{id}")
    void updateToDelete(@Param("id") String id);
}
