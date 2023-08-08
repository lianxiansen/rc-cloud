package com.rc.cloud.app.operate.infrastructure.constants;

import com.rc.cloud.common.core.exception.ErrorCode;

public interface CustomClassificationErrorCodeConstants {


    ErrorCode REMOVE_SHOULD_NOT_ASSOCIATED_PRODUCT = new ErrorCode(2002000000, "删除失败，已关联产品");
    ErrorCode REMOVE_SHOULD_NOT_HAS_CHILD = new ErrorCode(2002000001, "删除失败，含有子分类");
    ErrorCode ID_NOT_EMPTY = new ErrorCode(2002000002, "唯一标识不为空");
    @Deprecated
    ErrorCode OBJECT_NOT_EXISTS = new ErrorCode(2002000003, "对象不存在");
    ErrorCode NAME_NOT_EMPTY = new ErrorCode(2002000004, "自定义分类名称不为空");
    ErrorCode PARENT_NOT_EXISTS = new ErrorCode(2002000005, "自定义分类上级不存在");
    ErrorCode RE_INHERIT_SHOULD_NOT_SPECIFY_MYSELF = new ErrorCode(2002000006, "上级分类错误");


}
