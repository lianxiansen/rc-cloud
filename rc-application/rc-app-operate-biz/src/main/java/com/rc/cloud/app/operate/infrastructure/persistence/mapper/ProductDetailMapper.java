package com.rc.cloud.app.operate.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rc.cloud.app.operate.appearance.request.ProductListVO;
import com.rc.cloud.app.operate.appearance.request.ProductRequestVO;
import com.rc.cloud.app.operate.application.data.*;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductDO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductDetailDO;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author:chenjianxiang
 * @Date 2021/2/8
 * @Description:
 */
@Mapper
public interface ProductDetailMapper extends BaseMapperX<ProductDetailDO> {


}
