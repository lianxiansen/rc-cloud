package com.rc.cloud.app.distributor.appearance.facade.admin.convert;

import java.util.*;
import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorSourceCreateReqVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.resp.DistributorSourceRespVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorSourceUpdateReqVO;
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

    DistributorSourcePO convert(DistributorSourceCreateReqVO bean);

    DistributorSourcePO convert(DistributorSourceUpdateReqVO bean);

    DistributorSourceRespVO convert(DistributorSourcePO bean);

    List<DistributorSourceRespVO> convertList(List<DistributorSourcePO> list);

    PageResult<DistributorSourceRespVO> convertPage(PageResult<DistributorSourcePO> page);

}
