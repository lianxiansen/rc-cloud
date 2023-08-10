package com.rc.cloud.app.operate.infrastructure.constants;

import com.rc.cloud.app.operate.domain.model.productgroup.specification.AppendProductGroupItemLimitSpecification;
import com.rc.cloud.common.core.exception.ErrorCode;

/**
 * @author liandy
 */
public interface ProductGroupErrorCodeConstants extends ErrorCodeConstants{
    // ========== 产品组合 模块 2003000000 ==========
    ErrorCode ID_NOT_EMPTY = new ErrorCode(2003000000, "产品组合唯一标识不为空");
    ErrorCode ID_INVALID = new ErrorCode(2003000000, "产品组合唯一标识无效");
    ErrorCode PRODUCT_GROUP_NOT_EXISTS = new ErrorCode(2003000001, "产品组合不存在");
    ErrorCode PRODUCT_ID_NOT_EMPTY = new ErrorCode(2003000002, "产品唯一标识不为空");
    ErrorCode PRODUCT_NOT_EXISTS = new ErrorCode(2003000003, "产品不存在");
    ErrorCode PRODUCT_GROUP_ID_NOT_EMPTY = new ErrorCode(2003000004, "产品组合唯一标识不为空");
    ErrorCode PRODUCT_ID_IN_GROUP_NOT_EMPTY = new ErrorCode(2003000005, "组合的产品唯一标识不为空");
    ErrorCode PRODUCT_IN_GROUP_NOT_EXISTS = new ErrorCode(2003000006, "组合的产品不存在");

    ErrorCode PRODUCT_GROUP_ITEM_NUM_LIMIT = new ErrorCode(2003000007, "单个组合的产品数量不超过"+ AppendProductGroupItemLimitSpecification.MAX_ITEM_SIZE);
    ErrorCode PRODUCT_GROUP_NAME_NOT_EMPTY = new ErrorCode(2003000008, "产品组合名称不为空");
    ErrorCode ProductGroupItemNumMoreThenLimit = new ErrorCode(2003000008, "组合产品数量达到上限");
}
