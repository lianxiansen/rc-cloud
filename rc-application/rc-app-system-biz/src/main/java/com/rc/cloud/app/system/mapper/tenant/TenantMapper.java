package com.rc.cloud.app.system.mapper.tenant;

import com.rc.cloud.app.system.model.tenant.SysTenantPO;
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
public interface TenantMapper extends BaseMapperX<SysTenantPO> {

    default PageResult<SysTenantPO> selectPage(TenantPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysTenantPO>()
                .likeIfPresent(SysTenantPO::getName, reqVO.getName())
                .likeIfPresent(SysTenantPO::getContactName, reqVO.getContactName())
                .likeIfPresent(SysTenantPO::getContactMobile, reqVO.getContactMobile())
                .eqIfPresent(SysTenantPO::getStatus, reqVO.getStatus())
                .betweenIfPresent(SysTenantPO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SysTenantPO::getId));
    }

    default List<SysTenantPO> selectList(TenantExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SysTenantPO>()
                .likeIfPresent(SysTenantPO::getName, reqVO.getName())
                .likeIfPresent(SysTenantPO::getContactName, reqVO.getContactName())
                .likeIfPresent(SysTenantPO::getContactMobile, reqVO.getContactMobile())
                .eqIfPresent(SysTenantPO::getStatus, reqVO.getStatus())
                .betweenIfPresent(SysTenantPO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SysTenantPO::getId));
    }

    default SysTenantPO selectByName(String name) {
        return selectOne(SysTenantPO::getName, name);
    }

    default Long selectCountByPackageId(String packageId) {
        return selectCount(SysTenantPO::getPackageId, packageId);
    }

    default List<SysTenantPO> selectListByPackageId(String packageId) {
        return selectList(SysTenantPO::getPackageId, packageId);
    }
}
