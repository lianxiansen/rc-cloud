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
 * @author rc@hqf
 * @date 2023/07/14
 * @description 租户mapper
 */
@Mapper
public interface TenantMapper extends BaseMapperX<SysTenantPO> {

    /**
     * 分页查询
     *
     * @param reqVO 查询参数
     * @return 分页查询结果
     */
    default PageResult<SysTenantPO> selectPage(TenantPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysTenantPO>()
                .likeIfPresent(SysTenantPO::getName, reqVO.getName())
                .likeIfPresent(SysTenantPO::getContactName, reqVO.getContactName())
                .likeIfPresent(SysTenantPO::getContactMobile, reqVO.getContactMobile())
                .eqIfPresent(SysTenantPO::getStatus, reqVO.getStatus())
                .betweenIfPresent(SysTenantPO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SysTenantPO::getId));
    }

    /**
     * 导出查询
     *
     * @param reqVO 查询参数
     * @return 查询结果
     */
    default List<SysTenantPO> selectList(TenantExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SysTenantPO>()
                .likeIfPresent(SysTenantPO::getName, reqVO.getName())
                .likeIfPresent(SysTenantPO::getContactName, reqVO.getContactName())
                .likeIfPresent(SysTenantPO::getContactMobile, reqVO.getContactMobile())
                .eqIfPresent(SysTenantPO::getStatus, reqVO.getStatus())
                .betweenIfPresent(SysTenantPO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SysTenantPO::getId));
    }

    /**
     * 根据租户名称查询
     *
     * @param name 租户名称
     * @return 查询结果
     */
    default SysTenantPO selectByName(String name) {
        return selectOne(SysTenantPO::getName, name);
    }

    /**
     * 根据租户套餐id查询租户数量
     *
     * @param packageId 租户套餐id
     * @return 租户数量
     */
    default Long selectCountByPackageId(String packageId) {
        return selectCount(SysTenantPO::getPackageId, packageId);
    }

    /**
     * 根据租户套餐id查询租户列表
     *
     * @param packageId 租户套餐id
     * @return 租户列表
     */
    default List<SysTenantPO> selectListByPackageId(String packageId) {
        return selectList(SysTenantPO::getPackageId, packageId);
    }
}
