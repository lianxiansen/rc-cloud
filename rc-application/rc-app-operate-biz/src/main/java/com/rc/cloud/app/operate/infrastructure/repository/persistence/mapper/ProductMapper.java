package com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rc.cloud.app.operate.application.dto.*;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductPO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.util.StringUtils;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author:chenjianxiang
 * @Date 2021/2/8
 * @Description:
 */
@Mapper
public interface ProductMapper extends BaseMapperX<ProductPO> {

    default PageResult<ProductPO> selectPage(ProductListQueryDTO queryDTO) {
        if (StringUtils.isEmpty(queryDTO.getOrder())) {
            queryDTO.setOrder("create_time");
            queryDTO.setAsc(false);
        }
        QueryWrapper<ProductPO> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .like(StringUtils.isNotEmpty(queryDTO.getName()),ProductPO::getName, queryDTO.getName())
                .eq(StringUtils.isNotEmpty(queryDTO.getSpuCode()), ProductPO::getSpuCode, queryDTO.getSpuCode())
                .eq(StringUtils.isNotEmpty(queryDTO.getFirstCategory()), ProductPO::getFirstCategory, queryDTO.getFirstCategory())
                .eq(StringUtils.isNotEmpty(queryDTO.getSecondCategory()), ProductPO::getSecondCategory, queryDTO.getSecondCategory())
                .eq(StringUtils.isNotEmpty(queryDTO.getThirdCategory()), ProductPO::getThirdCategory, queryDTO.getThirdCategory());

        wrapper.orderBy(true, queryDTO.getAsc(), StrUtil.toUnderlineCase(queryDTO.getOrder()));
        return selectPage(queryDTO, wrapper);
    }


}
