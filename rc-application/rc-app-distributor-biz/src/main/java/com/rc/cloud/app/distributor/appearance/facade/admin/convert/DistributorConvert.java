package com.rc.cloud.app.distributor.appearance.facade.admin.convert;

import java.util.*;

import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorCreateReqVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.resp.DistributorExcelVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.resp.DistributorRespVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorUpdateReqVO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorPO;
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

    DistributorPO convert(DistributorCreateReqVO bean);

    DistributorPO convert(DistributorUpdateReqVO bean);

    DistributorRespVO convert(DistributorPO bean);

    List<DistributorRespVO> convertList(List<DistributorPO> list);

    PageResult<DistributorRespVO> convertPage(PageResult<DistributorPO> page);

    List<DistributorExcelVO> convertList02(List<DistributorPO> list);
}
