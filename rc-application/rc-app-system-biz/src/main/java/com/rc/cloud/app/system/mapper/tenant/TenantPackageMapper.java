package com.rc.cloud.app.system.mapper.tenant;

import com.rc.cloud.app.system.model.tenant.SysTenantPackagePO;
import com.rc.cloud.app.system.vo.tenant.packages.TenantPackagePageReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 租户套餐mapper
 */
@Mapper
public interface TenantPackageMapper extends BaseMapperX<SysTenantPackagePO> {

    /**
     * 分页查询
     *
     * @param reqVO 分页查询参数
     * @return 分页查询结果
     */
    default PageResult<SysTenantPackagePO> selectPage(TenantPackagePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysTenantPackagePO>()
                .likeIfPresent(SysTenantPackagePO::getName, reqVO.getName())
                .eqIfPresent(SysTenantPackagePO::getStatus, reqVO.getStatus())
                .likeIfPresent(SysTenantPackagePO::getRemark, reqVO.getRemark())
                .betweenIfPresent(SysTenantPackagePO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SysTenantPackagePO::getId));
    }

    /**
     * 根据状态查询列表
     *
     * @param status 状态
     * @return 查询结果
     */
    default List<SysTenantPackagePO> selectListByStatus(Integer status) {
        return selectList(SysTenantPackagePO::getStatus, status);
    }

    default SysTenantPackagePO selectByName(String name) {
        return selectOne(SysTenantPackagePO::getName, name);
    }
}
