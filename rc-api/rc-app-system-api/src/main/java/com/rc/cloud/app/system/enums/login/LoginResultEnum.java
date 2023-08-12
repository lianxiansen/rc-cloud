package com.rc.cloud.app.system.enums.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 登录结果的枚举类
 */
@Getter
@AllArgsConstructor
public enum LoginResultEnum {
    /*
     * 成功
     */
    SUCCESS(0),

    /*
     * 账号或密码不正确
     */
    BAD_CREDENTIALS(10),

    /*
     * 用户被禁用
     */
    USER_DISABLED(20),

    /*
     * 图片验证码不存在
     */
    CAPTCHA_NOT_FOUND(30),

    /*
     * 图片验证码不正确
     */
    CAPTCHA_CODE_ERROR(31),


    /*
     * 错误日志类型
     */
    ERROR(99);

    /**
     * 结果
     */
    private final Integer result;
}
