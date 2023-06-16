package com.rc.cloud.common.core.exception;

import com.rc.cloud.common.core.exception.enums.GlobalErrorCodeConstants;
import org.springframework.http.HttpStatus;

/**
 * 失败异常
 * httpCode INTERNAL_SERVER_ERROR 500
 * code FAIL 10200
 * @author pedro@TaleLin
 * @author Juzi@TaleLin
 * @author colorful@TaleLin
 */
public class FailedException extends HttpException {

    private static final long serialVersionUID = -661265124636854465L;

    protected int code = GlobalErrorCodeConstants.FAIL.getCode();

    protected int httpCode = HttpStatus.OK.value();

    public FailedException() {
        super(GlobalErrorCodeConstants.FAIL.getCode(), GlobalErrorCodeConstants.FAIL.getMsg());
        super.ifDefaultMessage=true;
    }

    public FailedException(String message) {
        super(message);
    }

    public FailedException(int code) {
        super(code, GlobalErrorCodeConstants.FAIL.getMsg());
        this.code = code;
        super.ifDefaultMessage=true;
    }

    @Deprecated
    public FailedException(String message, int code) {
        super(message, code);
        this.code = code;
    }

    public FailedException(int code, String message) {
        super(code, message);
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public int getHttpCode() {
        return httpCode;
    }
}
