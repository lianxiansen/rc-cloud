package com.rc.cloud.app.distributor.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rc.cloud.app.distributor.infrastructure.config.DistributorErrorCodeConstants;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorContactDO;
import com.rc.cloud.common.core.exception.ErrorCode;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import org.apache.commons.collections4.ListUtils;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.stream.Collectors;

import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * @author WJF
 * @create 2023-06-27 8:42
 * @description TODO
 */
@Mapper
public interface DistributorContactMapper extends BaseMapperX<DistributorContactDO> {

}
