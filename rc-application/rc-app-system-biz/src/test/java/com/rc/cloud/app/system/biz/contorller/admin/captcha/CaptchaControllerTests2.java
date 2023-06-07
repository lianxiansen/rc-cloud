/**
 * @author oliveoil
 * date 2023-06-07 09:12
 */
package com.rc.cloud.app.system.biz.contorller.admin.captcha;

import com.rc.cloud.app.system.biz.common.test.RcTest;
import com.rc.cloud.app.system.biz.controller.admin.captcha.CaptchaController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 针对 {@link CaptchaController}的单元测试
 */
@RcTest
public class CaptchaControllerTests2 {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void get_captcha_success() throws Exception {
        mvc.perform(get("/sys/captcha/get"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.image").isNotEmpty())
                .andExpect(jsonPath("$.data.key").isNotEmpty());
    }
}
