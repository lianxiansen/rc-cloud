package com.rc.cloud.app.operate.infrastructure.constants;

import com.rc.cloud.common.core.exception.ErrorCode;
/**
 * @author liandy
 */
public interface BrandErrorCodeConstants extends ErrorCodeConstants{
    ErrorCode ID_NOT_EMPTY = new ErrorCode(2005000000, "唯一标识不为空");
    ErrorCode NAME_NOT_EMPTY = new ErrorCode(2005000002, "品牌名称不为空");
    ErrorCode ID_INVALID = new ErrorCode(2005000002, "品牌唯一标识符无效");
}
