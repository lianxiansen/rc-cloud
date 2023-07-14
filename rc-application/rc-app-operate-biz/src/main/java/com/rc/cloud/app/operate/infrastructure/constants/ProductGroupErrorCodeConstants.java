package com.rc.cloud.app.operate.infrastructure.constants;

import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroup;
import com.rc.cloud.common.core.exception.ErrorCode;

/**
 * @author liandy
 */
public interface ProductGroupErrorCodeConstants extends ErrorCodeConstants{
    // ========== 产品组合 模块 2003000000 ==========
    ErrorCode ID_NOT_EMPTY = new ErrorCode(2003000000, "产品组合唯一标识不为空");
    ErrorCode OBJECT_NOT_EXISTS = new ErrorCode(2003000001, "产品组合不存在");
    ErrorCode PRODUCT_ID_NOT_EMPTY = new ErrorCode(2003000002, "产品唯一标识不为空");
    ErrorCode PRODUCT_NOT_EXISTS = new ErrorCode(2003000003, "产品不存在");
    ErrorCode PRODUCT_GROUP_ID_NOT_EMPTY = new ErrorCode(2003000004, "产品组合唯一标识不为空");
    ErrorCode PRODUCT_ID_IN_GROUP_NOT_EMPTY = new ErrorCode(2003000005, "组合的产品唯一标识不为空");
    ErrorCode PRODUCT_IN_GROUP_NOT_EXISTS = new ErrorCode(2003000006, "组合的产品不存在");

    ErrorCode PRODUCT_GROUP_ITEM_NUM_LIMIT = new ErrorCode(2003000007, "单个组合的产品数量不超过"+ ProductGroup.MAX_ITEM_SIZE);
}
