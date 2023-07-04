package com.rc.cloud.app.distributor.application.convert;

import java.util.*;


import com.rc.cloud.app.distributor.appearance.req.DistributorReputationCreateReqVO;
import com.rc.cloud.app.distributor.appearance.resp.DistributorReputationRespVO;
import com.rc.cloud.app.distributor.appearance.req.DistributorReputationUpdateReqVO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorReputationPO;
import com.rc.cloud.common.core.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 经销商客户信誉 Convert
 *
 * @author wjf
 */
@Mapper
public interface DistributorReputationConvert {

    DistributorReputationConvert INSTANCE = Mappers.getMapper(DistributorReputationConvert.class);

    DistributorReputationPO convert(DistributorReputationCreateReqVO bean);

    DistributorReputationPO convert(DistributorReputationUpdateReqVO bean);

    DistributorReputationRespVO convert(DistributorReputationPO bean);

    List<DistributorReputationRespVO> convertList(List<DistributorReputationPO> list);

    PageResult<DistributorReputationRespVO> convertPage(PageResult<DistributorReputationPO> page);

}
