package com.rc.cloud.app.operate.infrastructure.constants;

import com.rc.cloud.common.core.exception.ErrorCode;
/**
 * @author liandy
 */
public interface ProductCategoryErrorCodeConstants extends ErrorCodeConstants{
    // ========== 产品分类 模块 2002000000 ==========
    ErrorCode REMOVE_SHOULD_NOT_ASSOCIATED_PRODUCT = new ErrorCode(2002000000, "删除失败，已关联产品");
    ErrorCode REMOVE_SHOULD_NOT_HAS_CHILD = new ErrorCode(2002000001, "删除失败，含有子分类");
    ErrorCode ID_NOT_EMPTY = new ErrorCode(2002000002, "唯一标识不为空");
    ErrorCode OBJECT_NOT_EXISTS = new ErrorCode(2002000003, "对象不存在");
    ErrorCode NAME_NOT_EMPTY = new ErrorCode(2002000004, "品牌名称不为空");
    ErrorCode PARENT_NOT_EXISTS = new ErrorCode(2002000005, "上级分类对象不存在");
    ErrorCode RE_INHERIT_SHOULD_NOT_SPECIFY_MYSELF = new ErrorCode(2002000006, "重新指定上级分类错误");
}
