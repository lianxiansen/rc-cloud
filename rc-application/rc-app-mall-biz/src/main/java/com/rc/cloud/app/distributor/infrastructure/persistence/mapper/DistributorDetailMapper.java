package com.rc.cloud.app.distributor.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorChannelDO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorDetailDO;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author WJF
 * @create 2023-06-26 14:49
 * @description TODO
 */
@Mapper
public interface DistributorDetailMapper extends BaseMapperX<DistributorDetailDO> {
    default DistributorDetailDO selectByDistributorId(Long id){
        return selectOne(DistributorDetailDO::getDistributorId,id);
    }
}
