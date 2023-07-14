package com.rc.cloud.app.distributor.infrastructure.persistence.mapper;

import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorDetailPO;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author WJF
 * @create 2023-06-26 14:49
 * @description TODO
 */
@Mapper
public interface DistributorDetailMapper extends BaseMapperX<DistributorDetailPO> {
    /**
     * 获取经销商详细信息
     *
     * @param id 经销商id
     * @return DistributorDetailPO
     */
    default DistributorDetailPO selectByDistributorId(String id) {
        return selectOne(DistributorDetailPO::getDistributorId, id);
    }
}
