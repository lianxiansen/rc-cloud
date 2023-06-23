/**
 * @author oliveoil
 * date 2023-06-23 08:16
 */
package com.rc.cloud.ops.auth.common.dto;

public class CaptchaDTO {

    private String randomStr;
    private String code;

    public String getRandomStr() {
        return randomStr;
    }

    public void setRandomStr(String randomStr) {
        this.randomStr = randomStr;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
