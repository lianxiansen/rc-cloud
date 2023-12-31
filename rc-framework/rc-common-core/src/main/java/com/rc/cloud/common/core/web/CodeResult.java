package com.rc.cloud.common.core.web;

import com.rc.cloud.common.core.exception.ErrorCode;
import com.rc.cloud.common.core.exception.enums.GlobalErrorCodeConstants;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @author ruoyi
 */
public class CodeResult<T> implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 成功 */
    public static final ErrorCode SUCCESS = GlobalErrorCodeConstants.SUCCESS;

    /** 失败 */
    public static final ErrorCode FAIL = GlobalErrorCodeConstants.FAIL;

    private boolean success;

    private int code;

    private String msg;

    private T data;

    public static <T> CodeResult<T> ok()
    {
        return restResult(null,true, SUCCESS.getCode(), SUCCESS.getMsg());
    }

    public static <T> CodeResult<T> ok(T data)
    {
        return restResult(data, true, SUCCESS.getCode(), SUCCESS.getMsg());
    }

    public static <T> CodeResult<T> ok(T data, String msg)
    {
        return restResult(data, true, SUCCESS.getCode(), msg);
    }

    public static <T> CodeResult<T> fail()
    {
        return restResult(null,false, FAIL.getCode(), FAIL.getMsg());
    }

    public static <T> CodeResult<T> fail(String msg)
    {
        return restResult(null,false, FAIL.getCode(), msg);
    }

    public static <T> CodeResult<T> fail(T data)
    {
        return restResult(data, false, FAIL.getCode(), FAIL.getMsg());
    }

    public static <T> CodeResult<T> fail(T data, String msg)
    {
        return restResult(data, false, FAIL.getCode(), msg);
    }

    public static <T> CodeResult<T> fail(int code, String msg)
    {
        return restResult(null, false, code, msg);
    }

    public static <T> CodeResult<T> fail(Integer code, String msg)
    {
        return restResult(null, false,code, msg);
    }

    private static <T> CodeResult<T> restResult(T data, boolean success, int code, String msg)
    {
        CodeResult<T> apiResult = new CodeResult<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        apiResult.setSuccess(success);
        return apiResult;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
