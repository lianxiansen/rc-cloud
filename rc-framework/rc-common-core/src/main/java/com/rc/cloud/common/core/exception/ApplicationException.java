package com.rc.cloud.common.core.exception;

/**
 * @ClassName: ApplicationException
 * @Author: liandy
 * @Date: 2022/11/11 14:47
 * @Description: 北向网关应用层超类
 */
public class ApplicationException extends RuntimeException{
    public ApplicationException(String msg) {
        super(msg);
    }
}
