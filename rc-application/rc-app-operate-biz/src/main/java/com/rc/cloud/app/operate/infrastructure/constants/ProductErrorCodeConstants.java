package com.rc.cloud.app.operate.infrastructure.constants;

import com.rc.cloud.common.core.exception.ErrorCode;

/**
 * @author liandy
 */
public interface ProductErrorCodeConstants extends ErrorCodeConstants {

    ErrorCode PRODUCT_NOT_EXIST_ERROR = new ErrorCode(2001000000, "商品不存在，状态异常");
    ErrorCode NAME_NOT_EMPTY = new ErrorCode(2001000002, "Name must not be null");

    ErrorCode PRODUCT_ID_NOT_EMPTY = new ErrorCode(2001000003, "ProductId must not be null");

    ErrorCode PRODUCT_ONSHELF_ERROR = new ErrorCode(2001000004, "上架失败，状态异常");

    ErrorCode PRODUCT_OFFSHELF_ERROR = new ErrorCode(2001000005, "下架失败，状态异常");

    ErrorCode PRODUCT_TAG_ERROR = new ErrorCode(2001000005, "商品标签格式不对");

    ErrorCode PRODUCT_SKU_NOT_EXIST_ERROR = new ErrorCode(2001000005, "商品skuid不存在，状态异常");

    ErrorCode PRODUCT_SKU_UPDATE_ERROR = new ErrorCode(2001000005, "商品sku修改异常");

    ErrorCode PRODUCT_SKU_ATTIBUTE_REPEAT_ERROR = new ErrorCode(2001000005, "商品sku规格重复");

    ErrorCode PRODUCT_SKU_CODE_REPEAT_ERROR = new ErrorCode(2001000005, "商品skucode重复");

    ErrorCode PRODUCT_CATEGORY_NOT_EXIST_ERROR = new ErrorCode(2001000005, "所选的类别不存在");
    ErrorCode PRODUCT_BRAND_NOT_EXIST_ERROR = new ErrorCode(2001000005, "所选的品牌不存在");
    ErrorCode PRODUCT_CUSTOMCLASSIFICATION_NOT_EXIST_ERROR = new ErrorCode(2001000005, "所选的自定义类别不存在");

}
