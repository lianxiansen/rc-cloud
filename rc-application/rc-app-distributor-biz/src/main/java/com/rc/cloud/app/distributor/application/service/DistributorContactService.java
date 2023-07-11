package com.rc.cloud.app.distributor.application.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rc.cloud.app.distributor.appearance.req.DistributorContactUpdatePasswordReqVO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorContactPO;

import java.util.List;

/**
 * @author WJF
 * @create 2023-06-27 8:38
 * @description TODO
 */

public interface DistributorContactService extends IService<DistributorContactPO>{
    void updatePassword(DistributorContactUpdatePasswordReqVO updatePasswordReqVO);

    void resetPassword(String id);

    void updateContacts(String distributorId, List<DistributorContactPO> contactDOS);

    List<DistributorContactPO> getByDistributorId(String distributorId);

    DistributorContactPO getByMobile(String mobile);
}
