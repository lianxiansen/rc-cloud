package com.rc.cloud.app.system.convert.tenant;

import com.rc.cloud.app.system.model.tenant.SysTenantPO;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantCreateReqVO;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantExcelVO;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantRespVO;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantUpdateReqVO;
import com.rc.cloud.app.system.vo.user.user.UserCreateReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 租户转换类
 */
@Mapper
public interface TenantConvert {

    TenantConvert INSTANCE = Mappers.getMapper(TenantConvert.class);

    /**
     * 创建请求参数转换
     *
     * @param bean SysTenantPO
     * @return DictTypeDetailRespVO
     */
    SysTenantPO convert(TenantCreateReqVO bean);

    /**
     * 更新请求参数转换
     *
     * @param bean SysTenantPO
     * @return DictTypeDetailRespVO
     */
    SysTenantPO convert(TenantUpdateReqVO bean);

    /**
     * PO查询结果转换VO
     *
     * @param bean SysTenantPO
     * @return DictTypeRespVO
     */
    TenantRespVO convert(SysTenantPO bean);

    /**
     * PO列表查询结果转换VO列表
     *
     * @param list SysTenantPO
     * @return DictTypeDetailRespVO
     */
    List<TenantRespVO> convertList(List<SysTenantPO> list);

    /**
     * 分页查询结果转换
     *
     * @param page 分页查询结果
     * @return 分页查询结果
     */
    PageResult<TenantRespVO> convertPage(PageResult<SysTenantPO> page);

    /**
     * PO列表查询结果转换VO列表
     *
     * @param list SysTenantPO
     * @return DictTypeDetailRespVO
     */
    List<TenantExcelVO> convertList02(List<SysTenantPO> list);

    /**
     * 创建请求参数转换
     *
     * @param bean SysTenantPO
     * @return DictTypeDetailRespVO
     */
    default UserCreateReqVO convert02(TenantCreateReqVO bean) {
        UserCreateReqVO reqVO = new UserCreateReqVO();
        reqVO.setUsername(bean.getUsername());
        reqVO.setPassword(bean.getPassword());
        reqVO.setNickname(bean.getContactName());
        reqVO.setMobile(bean.getContactMobile());
        return reqVO;
    }

}
