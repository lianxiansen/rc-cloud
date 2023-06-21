package com.rc.cloud.app.distributor.application.convert;

import java.util.*;

import com.rc.cloud.app.distributor.appearance.req.AppDistributorCreateReqVO;
import com.rc.cloud.app.distributor.appearance.resp.AppDistributorExcelVO;
import com.rc.cloud.app.distributor.appearance.resp.AppDistributorRespVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorUpdateReqVO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorDO;
import com.rc.cloud.common.core.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
 * 经销商 Convert
 *
 * @author wjf
 */
@Mapper
public interface DistributorConvert {

    DistributorConvert INSTANCE = Mappers.getMapper(DistributorConvert.class);

    DistributorDO convert(AppDistributorCreateReqVO bean);

    DistributorDO convert(AppDistributorUpdateReqVO bean);

    AppDistributorRespVO convert(DistributorDO bean);

    List<AppDistributorRespVO> convertList(List<DistributorDO> list);

    PageResult<AppDistributorRespVO> convertPage(PageResult<DistributorDO> page);

    List<AppDistributorExcelVO> convertList02(List<DistributorDO> list);
}
