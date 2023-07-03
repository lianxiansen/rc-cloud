package com.rc.cloud.app.distributor.application.convert;

import com.rc.cloud.app.distributor.appearance.req.AppDistributorContactCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorContactUpdateReqVO;
import com.rc.cloud.app.distributor.appearance.vo.AppDistributorContactBaseVO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorContactPO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author WJF
 * @create 2023-06-27 11:18
 * @description TODO
 */
@Mapper
public interface DistributorContactConvert {

    DistributorContactConvert INSTANCE = Mappers.getMapper(DistributorContactConvert.class);

    List<DistributorContactPO> convertList(List<AppDistributorContactCreateReqVO> list);

    List<DistributorContactPO> convertList2(List<AppDistributorContactUpdateReqVO> list);

    List<AppDistributorContactBaseVO> convertList3(List<DistributorContactPO> list);
}
