package com.rc.cloud.app.distributor.appearance.facade.admin.convert;

import java.util.*;

import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorLevelPO;
import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorLevelCreateReqVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.resp.DistributorLevelRespVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorLevelUpdateReqVO;
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

    DistributorLevelPO convert(DistributorLevelCreateReqVO bean);

    DistributorLevelPO convert(DistributorLevelUpdateReqVO bean);

    DistributorLevelRespVO convert(DistributorLevelPO bean);

    List<DistributorLevelRespVO> convertList(List<DistributorLevelPO> list);

    PageResult<DistributorLevelRespVO> convertPage(PageResult<DistributorLevelPO> page);

}
