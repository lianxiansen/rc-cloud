package com.rc.cloud.app.system.mapper.tenant;

import com.rc.cloud.app.system.model.tenant.SysTenantPackageDO;
import com.rc.cloud.app.system.vo.tenant.packages.TenantPackagePageReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 租户套餐 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TenantPackageMapper extends BaseMapperX<SysTenantPackageDO> {

    default PageResult<SysTenantPackageDO> selectPage(TenantPackagePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysTenantPackageDO>()
                .likeIfPresent(SysTenantPackageDO::getName, reqVO.getName())
                .eqIfPresent(SysTenantPackageDO::getStatus, reqVO.getStatus())
                .likeIfPresent(SysTenantPackageDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(SysTenantPackageDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SysTenantPackageDO::getId));
    }

    default List<SysTenantPackageDO> selectListByStatus(Integer status) {
        return selectList(SysTenantPackageDO::getStatus, status);
    }
}
