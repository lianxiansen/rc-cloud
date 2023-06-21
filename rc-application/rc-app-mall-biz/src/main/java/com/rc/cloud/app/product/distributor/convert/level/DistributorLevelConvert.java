package com.rc.cloud.app.product.distributor.convert.level;

import java.util.*;

import com.rc.cloud.app.product.distributor.controller.app.level.vo.AppDistributorLevelCreateReqVO;
import com.rc.cloud.app.product.distributor.controller.app.level.vo.AppDistributorLevelRespVO;
import com.rc.cloud.app.product.distributor.controller.app.level.vo.AppDistributorLevelUpdateReqVO;
import com.rc.cloud.app.product.distributor.dal.dataobject.level.DistributorLevelDO;
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

    DistributorLevelDO convert(AppDistributorLevelCreateReqVO bean);

    DistributorLevelDO convert(AppDistributorLevelUpdateReqVO bean);

    AppDistributorLevelRespVO convert(DistributorLevelDO bean);

    List<AppDistributorLevelRespVO> convertList(List<DistributorLevelDO> list);

    PageResult<AppDistributorLevelRespVO> convertPage(PageResult<DistributorLevelDO> page);

}
