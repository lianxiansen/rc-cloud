package com.rc.cloud.app.distributor.application.convert;

import com.rc.cloud.app.distributor.appearance.req.DistributorContactCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.DistributorContactUpdateReqVO;
import com.rc.cloud.app.distributor.appearance.resp.DistributorContactRespVO;
import com.rc.cloud.app.distributor.appearance.vo.DistributorContactBaseVO;
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

    List<DistributorContactPO> convertList(List<DistributorContactCreateReqVO> list);

    List<DistributorContactPO> convertList2(List<DistributorContactUpdateReqVO> list);

    List<DistributorContactBaseVO> convertList3(List<DistributorContactPO> list);

    DistributorContactRespVO convert(DistributorContactPO po);
}
