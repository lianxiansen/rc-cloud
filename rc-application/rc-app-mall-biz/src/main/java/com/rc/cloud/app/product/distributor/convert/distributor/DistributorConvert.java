package com.rc.cloud.app.product.distributor.convert.distributor;

import java.util.*;

import com.rc.cloud.app.product.distributor.controller.app.distributor.vo.AppDistributorCreateReqVO;
import com.rc.cloud.app.product.distributor.controller.app.distributor.vo.AppDistributorExcelVO;
import com.rc.cloud.app.product.distributor.controller.app.distributor.vo.AppDistributorRespVO;
import com.rc.cloud.app.product.distributor.controller.app.distributor.vo.AppDistributorUpdateReqVO;
import com.rc.cloud.app.product.distributor.dal.dataobject.distributor.DistributorDO;
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
