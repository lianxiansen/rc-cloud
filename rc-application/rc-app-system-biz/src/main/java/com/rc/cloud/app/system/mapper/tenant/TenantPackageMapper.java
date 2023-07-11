package com.rc.cloud.app.system.mapper.tenant;

import com.rc.cloud.app.system.model.tenant.SysTenantPackagePO;
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
public interface TenantPackageMapper extends BaseMapperX<SysTenantPackagePO> {

    default PageResult<SysTenantPackagePO> selectPage(TenantPackagePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysTenantPackagePO>()
                .likeIfPresent(SysTenantPackagePO::getName, reqVO.getName())
                .eqIfPresent(SysTenantPackagePO::getStatus, reqVO.getStatus())
                .likeIfPresent(SysTenantPackagePO::getRemark, reqVO.getRemark())
                .betweenIfPresent(SysTenantPackagePO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SysTenantPackagePO::getId));
    }

    default List<SysTenantPackagePO> selectListByStatus(Integer status) {
        return selectList(SysTenantPackagePO::getStatus, status);
    }
}
