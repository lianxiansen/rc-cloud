package com.rc.cloud.app.operate.infrastructure.constants;

import com.rc.cloud.common.core.exception.ErrorCode;
/**
 * @author liandy
 */
public interface ProductCategoryErrorCodeConstants extends ErrorCodeConstants{
    ErrorCode REMOVE_SHOULD_NOT_HAS_CHILD = new ErrorCode(2002000001, "产品分类删除失败，含有子分类");
    ErrorCode ID_NOT_EMPTY = new ErrorCode(2002000002, "产品分类唯一标识不为空");
    ErrorCode ID_INVALID = new ErrorCode(2002000002, "产品分类唯一标识无效");
    ErrorCode PRODUCT_CATEGORY_NOT_EXISTS = new ErrorCode(2002000003, "产品分类不存在");
    ErrorCode NAME_NOT_EMPTY = new ErrorCode(2002000004, "产品分类名称不为空");
    ErrorCode PARENT_NOT_EXISTS = new ErrorCode(2002000005, "上级分类对象不存在");
    ErrorCode RE_INHERIT_SHOULD_NOT_SPECIFY_MYSELF = new ErrorCode(2002000006, "上级分类错误");


}
