package com.rc.cloud.app.operate.infrastructure.constants;

import com.rc.cloud.common.core.exception.ErrorCode;
/**
 * @author liandy
 */
public interface BrandErrorCodeConstants extends ErrorCodeConstants{
    // ========== 品牌 模块 2001000000 ==========
    ErrorCode ID_NOT_EMPTY = new ErrorCode(2005000000, "唯一标识不为空");
    ErrorCode NAME_NOT_EMPTY = new ErrorCode(2005000002, "品牌名称不为空");
    ErrorCode REMOVE_SHOULD_NOT_ASSOCIATED_PRODUCT = new ErrorCode(2005000003, "删除失败，已关联产品");
}
