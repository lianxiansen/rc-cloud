package com.rc.cloud.app.distributor.application.convert;

import java.util.*;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorSourceCreateReqVO;
import com.rc.cloud.app.distributor.appearance.resp.AppDistributorSourceRespVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorSourceUpdateReqVO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorSourcePO;
import com.rc.cloud.common.core.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 经销商来源 Convert
 *
 * @author wjf
 */
@Mapper
public interface DistributorSourceConvert {

    DistributorSourceConvert INSTANCE = Mappers.getMapper(DistributorSourceConvert.class);

    DistributorSourcePO convert(AppDistributorSourceCreateReqVO bean);

    DistributorSourcePO convert(AppDistributorSourceUpdateReqVO bean);

    AppDistributorSourceRespVO convert(DistributorSourcePO bean);

    List<AppDistributorSourceRespVO> convertList(List<DistributorSourcePO> list);

    PageResult<AppDistributorSourceRespVO> convertPage(PageResult<DistributorSourcePO> page);

}
