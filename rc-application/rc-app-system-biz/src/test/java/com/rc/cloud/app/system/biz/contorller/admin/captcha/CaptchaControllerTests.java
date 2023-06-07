/**
 * @author oliveoil
 * date 2023-06-07 09:12
 */
package com.rc.cloud.app.system.biz.contorller.admin.captcha;

import com.rc.cloud.app.system.biz.controller.admin.captcha.CaptchaController;
import com.rc.cloud.app.system.biz.service.captcha.CaptchaService;
import com.rc.cloud.app.system.biz.vo.captcha.CaptchaVO;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.test.core.ut.BaseMockitoUnitTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.rc.cloud.common.test.core.util.RandomUtils.randomPojo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * 针对 {@link CaptchaController}的单元测试
 */
public class CaptchaControllerTests extends BaseMockitoUnitTest {

    @InjectMocks
    private CaptchaController captchaController;

    @Mock
    private CaptchaService captchaService;

    @Test
    public void test_getCaptcha_success() {
        CaptchaVO captchaVO = randomPojo(CaptchaVO.class);
        when(captchaService.generate()).thenReturn(captchaVO);
        CodeResult<CaptchaVO> result = captchaController.captcha();
        assertEquals(200, result.getCode());
        CaptchaVO captchaVORes = result.getData();
        assertNotNull(captchaVORes.getImage());
        assertNotNull(captchaVORes.getKey());
    }
}
