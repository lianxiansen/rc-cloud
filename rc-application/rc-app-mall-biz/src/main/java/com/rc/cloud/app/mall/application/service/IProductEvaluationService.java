package com.rc.cloud.app.mall.application.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.rc.cloud.app.mall.appearance.request.ProductEvaluationCountQuery;
import com.rc.cloud.app.mall.appearance.request.ProductEvaluationListQuery;
import com.rc.cloud.app.mall.appearance.request.ProductEvaluationSaveVO;
import com.rc.cloud.app.mall.application.data.EvaluationDTO;
import com.rc.cloud.app.mall.application.data.FavorityProductEvaluationDTO;
import com.rc.cloud.app.mall.application.data.ProductEvaluationListDTO;
import com.rc.cloud.app.mall.application.data.ProductEvaluationStatisticsDTO;


import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenjianxiang
 * @since 2021-03-15
 */
public interface IProductEvaluationService extends IService<ProductEvaluation> {

    ProductEvaluationStatisticsDTO getProductEvaluationStatisticsDTO(String brandName);

    ProductEvaluationStatisticsDTO getBrandProductEvaluationStatistics(String brandName);

    BigDecimal getBadRate(int product_id);//获得差评率

    BigDecimal getBadRate(String brandCommunity_brand_name);//获得差评率

    int updateReply(int id, String reply);//更新回复

    int delete(int id);//删除评论

    int setTop(int id, boolean is_top);//置顶评论

//    List<String> getItem(long id, int type, T_Product t_product);

    int saveProductEvaluation(ProductEvaluation model);//添加评论

    int copyAndSaveProductEvaluation(ProductEvaluationSaveVO vo, ProductEvaluation productEvaluation);

    ProductEvaluation getOne(int id);//获取一条

    ProductEvaluation getByOrderItemId(int orderItemId);

    FavorityProductEvaluationDTO getFavorityProductEvaluationDTO(int productId);

    Boolean submitOrderEvaluation(ProductEvaluation productEvaluation);

    Boolean submitOrderEvaluation(ProductEvaluation productEvaluation, Boolean isSystem);

    List<ProductEvaluation> getList(String productId, int productType);

    PageInfo<EvaluationDTO> getEvaluationDTOList(String brandName, String reputation, int pageIndex, int pageSize);

    PageInfo<ProductEvaluationListDTO> getPageList(ProductEvaluationListQuery query);

    void setRedis(ProductEvaluation productEvaluation);

    void setListRedis(List<ProductEvaluation> evaluations, String productId, int productType);

    Integer getCount(ProductEvaluationCountQuery query);
}
