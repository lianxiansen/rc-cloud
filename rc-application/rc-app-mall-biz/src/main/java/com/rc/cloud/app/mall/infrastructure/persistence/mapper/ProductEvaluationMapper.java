package com.rc.cloud.app.mall.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.rc.cloud.app.mall.appearance.request.ProductEvaluationListQuery;
import com.rc.cloud.app.mall.appearance.request.ProductEvaluationVO;
import com.rc.cloud.app.mall.appearance.request.ProductEvaluationRateVO;
import com.rc.cloud.app.mall.application.data.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenjianxiang
 * @since 2021-03-15
 */
public interface ProductEvaluationMapper extends BaseMapper<ProductEvaluation> {

    ProductEvaluationStatisticsDTO getProductEvaluationStatisticsDTO(String brandName);

    ProductEvaluationStatisticsDTO getBrandProductEvaluationStatistics(@Param("brand_name") String brandName);

    List<AdminProductEvaluationDTO> getAdminProductEvaluationDTOList(@Param("query") ProductEvaluationVO query);

    BaseMapperDTO getProductEvaluationRateDTO(@Param("query") ProductEvaluationRateVO query);

    FavorityProductEvaluationDTO getFavorityProductEvaluationDTO(@Param("product_id") int product_id);

    List<EvaluationDTO> getEvaluationDTOList(@Param("brand_name") String brandName, @Param("reputation") String reputation);

    List<ProductEvaluationListDTO> getPageList(@Param("query") ProductEvaluationListQuery query);
}
