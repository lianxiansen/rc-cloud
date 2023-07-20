package com.rc.cloud.app.distributor.application.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorContactUpdatePasswordReqVO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorContactPO;

import java.util.List;

/**
 * @author WJF
 * @create 2023-06-27 8:38
 * @description TODO
 */

public interface DistributorContactService extends IService<DistributorContactPO> {
    /**
     * 更新经销商联系人密码
     * @param updatePasswordReqVO
     */
    void updatePassword(DistributorContactUpdatePasswordReqVO updatePasswordReqVO);

    /**
     * 重置密码
     * @param id 联系人id
     */
    void resetPassword(String id);

    /**
     * 更新经销商联系人
     * @param contactDOS 联系人列表
     * @param distributorId 经销商id
     */
    void updateContacts(String distributorId, List<DistributorContactPO> contactDOS);

    /**
     * 获取经销商联系人
     * @param distributorId 经销商Id
     * @return DistributorContactPO集合
     */
    List<DistributorContactPO> getByDistributorId(String distributorId);

    /**
     * 通过手机号获取经销商联系人
     * @param mobile 联系人手机号
     * @return DistributorContactPO
     */
    DistributorContactPO getByMobile(String mobile);
}
