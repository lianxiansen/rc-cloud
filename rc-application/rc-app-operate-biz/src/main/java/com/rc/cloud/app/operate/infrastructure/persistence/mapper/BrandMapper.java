package com.rc.cloud.app.operate.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.po.BrandDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BrandMapper extends BaseMapper<BrandDO> {
}