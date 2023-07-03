package com.rc.cloud.app.distributor.application.convert;

import java.util.*;

import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorLevelPO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorLevelCreateReqVO;
import com.rc.cloud.app.distributor.appearance.resp.AppDistributorLevelRespVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorLevelUpdateReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 经销商客户等级 Convert
 *
 * @author wjf
 */
@Mapper
public interface DistributorLevelConvert {

    DistributorLevelConvert INSTANCE = Mappers.getMapper(DistributorLevelConvert.class);

    DistributorLevelPO convert(AppDistributorLevelCreateReqVO bean);

    DistributorLevelPO convert(AppDistributorLevelUpdateReqVO bean);

    AppDistributorLevelRespVO convert(DistributorLevelPO bean);

    List<AppDistributorLevelRespVO> convertList(List<DistributorLevelPO> list);

    PageResult<AppDistributorLevelRespVO> convertPage(PageResult<DistributorLevelPO> page);

}
