package com.rc.cloud.app.system.mapper.tenant;

import com.rc.cloud.app.system.api.tenant.entity.SysTenantDO;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantExportReqVO;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantPageReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 租户 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TenantMapper extends BaseMapperX<SysTenantDO> {

    default PageResult<SysTenantDO> selectPage(TenantPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysTenantDO>()
                .likeIfPresent(SysTenantDO::getName, reqVO.getName())
                .likeIfPresent(SysTenantDO::getContactName, reqVO.getContactName())
                .likeIfPresent(SysTenantDO::getContactMobile, reqVO.getContactMobile())
                .eqIfPresent(SysTenantDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(SysTenantDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SysTenantDO::getId));
    }

    default List<SysTenantDO> selectList(TenantExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SysTenantDO>()
                .likeIfPresent(SysTenantDO::getName, reqVO.getName())
                .likeIfPresent(SysTenantDO::getContactName, reqVO.getContactName())
                .likeIfPresent(SysTenantDO::getContactMobile, reqVO.getContactMobile())
                .eqIfPresent(SysTenantDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(SysTenantDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SysTenantDO::getId));
    }

    default SysTenantDO selectByName(String name) {
        return selectOne(SysTenantDO::getName, name);
    }

    default Long selectCountByPackageId(String packageId) {
        return selectCount(SysTenantDO::getPackageId, packageId);
    }

    default List<SysTenantDO> selectListByPackageId(String packageId) {
        return selectList(SysTenantDO::getPackageId, packageId);
    }
}
