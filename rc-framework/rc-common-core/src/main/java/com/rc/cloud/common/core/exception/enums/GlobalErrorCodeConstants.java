package com.rc.cloud.common.core.exception.enums;

import com.rc.cloud.common.core.exception.ErrorCode;

/**
 * 全局错误码枚举
 * 0-99999 系统异常编码保留
 * @author oliveoil
 */
public interface GlobalErrorCodeConstants {

    ErrorCode SUCCESS = new ErrorCode(200, "成功");
    ErrorCode FAIL = new ErrorCode(500, "失败");


    // ========== 客户端错误段(10000-10020) ==========
    ErrorCode UN_AUTHORIZATION = new ErrorCode(10000, "认证失败");
    ErrorCode UN_AUTHENTICATION = new ErrorCode(10010, "授权失败");
    ErrorCode NOT_FOUND = new ErrorCode(10020, "资源不存在");
    ErrorCode NOT_FOUND_HTTP_SERVLET_REQUEST = new ErrorCode(10021, "无法获取HttpServletRequest");

    ErrorCode PARAMETER_ERROR = new ErrorCode(10030, "请求参数不正确");
    ErrorCode TOKEN_INVALID = new ErrorCode(10040, "令牌失效");
    ErrorCode TOKEN_EXPIRED = new ErrorCode(10041, "令牌过期");
    ErrorCode REFRESH_FAILED = new ErrorCode(10050, "刷新令牌获取失败");
    ErrorCode REFRESH_TOKEN_EXPIRED = new ErrorCode(10051, "刷新令牌已过期");
    ErrorCode REFRESH_TOKEN_INVALID = new ErrorCode(10052, "刷新令牌已失效");
    ErrorCode FORBIDDEN = new ErrorCode(10070, "没有该操作权限");
    ErrorCode METHOD_NOT_ALLOWED = new ErrorCode(10080, "请求方法不允许");
    ErrorCode DUPLICATED = new ErrorCode(10090, "字段重复");
    ErrorCode LOCKED = new ErrorCode(10120, "请求失败，请稍后重试"); // 并发请求，不允许
    ErrorCode TOO_MANY_REQUESTS = new ErrorCode(10140, "请求过于频繁，请稍后重试");


    // ========== 服务端错误段(10201-10300) ==========
    ErrorCode NOT_IMPLEMENTED = new ErrorCode(10201, "功能未实现/未开启");

    // ========== 自定义错误段(10900-11000) ==========
    ErrorCode REPEATED_REQUESTS = new ErrorCode(10900, "重复请求，请稍后重试");
    ErrorCode DEMO_DENY = new ErrorCode(10901, "演示模式，禁止写操作");

    ErrorCode UNKNOWN = new ErrorCode(99999, "未知错误");
}
