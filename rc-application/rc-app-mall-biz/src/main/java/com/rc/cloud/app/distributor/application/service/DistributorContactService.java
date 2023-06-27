package com.rc.cloud.app.distributor.application.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorContactUpdatePasswordReqVO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorContactDO;

import java.util.List;

/**
 * @author WJF
 * @create 2023-06-27 8:38
 * @description TODO
 */

public interface DistributorContactService extends IService<DistributorContactDO>{
    void updatePassword(AppDistributorContactUpdatePasswordReqVO updatePasswordReqVO);

    void resetPassword(Long id);

    void updateContacts(Long distributorId, List<DistributorContactDO> contactDOS);

    List<DistributorContactDO> getByDistributorId(Long distributorId);
}
