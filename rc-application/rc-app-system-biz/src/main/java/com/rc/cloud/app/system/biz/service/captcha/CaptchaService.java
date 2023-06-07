package com.rc.cloud.app.system.biz.service.captcha;

import com.rc.cloud.app.system.biz.vo.captcha.CaptchaVO;

/**
 * 验证码
 * @author oliveoil
 */
public interface CaptchaService {
    /**
     * 生成验证码
     */
    CaptchaVO generate();

    /**
     * 验证码效验
     *
     * @param key  key
     * @param code 验证码
     * @return true：成功  false：失败
     */
    boolean validate(String key, String code);
}
