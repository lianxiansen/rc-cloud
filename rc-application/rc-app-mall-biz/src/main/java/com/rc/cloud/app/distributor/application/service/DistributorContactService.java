package com.rc.cloud.app.distributor.application.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorContactUpdatePasswordReqVO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorContactPO;

import java.util.List;

/**
 * @author WJF
 * @create 2023-06-27 8:38
 * @description TODO
 */

public interface DistributorContactService extends IService<DistributorContactPO>{
    void updatePassword(AppDistributorContactUpdatePasswordReqVO updatePasswordReqVO);

    void resetPassword(Long id);

    void updateContacts(Long distributorId, List<DistributorContactPO> contactDOS);

    List<DistributorContactPO> getByDistributorId(Long distributorId);
}
