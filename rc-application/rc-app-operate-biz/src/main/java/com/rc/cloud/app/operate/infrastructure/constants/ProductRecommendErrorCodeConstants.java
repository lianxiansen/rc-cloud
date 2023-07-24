package com.rc.cloud.app.operate.infrastructure.constants;

import com.rc.cloud.common.core.exception.ErrorCode;

/**
 * @author liandy
 */
public interface ProductRecommendErrorCodeConstants extends ErrorCodeConstants{
    ErrorCode ID_NOT_EMPTY = new ErrorCode(2004000000, "唯一标识不为空");
    ErrorCode PRODUCT_ID_NOT_EMPTY =new ErrorCode(2004000001, "产品唯一标识不为空") ;
    ErrorCode RECOMMEND_PRODUCT_ID_NOT_EMPTY =new ErrorCode(2004000002, "推荐产品唯一标识不为空") ;
    ErrorCode PRODUCT_NOT_EXISTS = new ErrorCode(2004000003, "产品不存在") ;
    ErrorCode RECOMMEND_PRODUCT_NOT_EXISTS = new ErrorCode(2004000004, "推荐产品不存在") ;
}
