package com.rc.cloud.app.distributor.application.convert;

import java.util.*;


import com.rc.cloud.app.distributor.appearance.req.AppDistributorReputationCreateReqVO;
import com.rc.cloud.app.distributor.appearance.resp.AppDistributorReputationRespVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorReputationUpdateReqVO;
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

    DistributorReputationPO convert(AppDistributorReputationCreateReqVO bean);

    DistributorReputationPO convert(AppDistributorReputationUpdateReqVO bean);

    AppDistributorReputationRespVO convert(DistributorReputationPO bean);

    List<AppDistributorReputationRespVO> convertList(List<DistributorReputationPO> list);

    PageResult<AppDistributorReputationRespVO> convertPage(PageResult<DistributorReputationPO> page);

}
