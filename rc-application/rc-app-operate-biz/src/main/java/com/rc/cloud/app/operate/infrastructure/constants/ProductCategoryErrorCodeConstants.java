package com.rc.cloud.app.operate.infrastructure.constants;

import com.rc.cloud.common.core.exception.ErrorCode;

public interface ProductCategoryErrorCodeConstants extends ErrorCodeConstants{
    // ========== 产品分类 模块 1002000000 ==========
    ErrorCode REMOVE_SHOULD_NOT_ASSOCIATED_PRODUCT = new ErrorCode(1002000000, "删除失败，已关联产品");
    ErrorCode REMOVE_SHOULD_NOT_HAS_CHILD = new ErrorCode(1002000000, "删除失败，含有子分类");
    ErrorCode NOT_EXISTS = new ErrorCode(1002000000, "删除失败，唯一标识不为空");
}
