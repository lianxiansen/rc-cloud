/**
 * @author oliveoil
 * date 2023-06-21 14:59
 */
package com.rc.cloud.ops.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.rc.cloud.common.test.annotation.RcTest;
import com.rc.cloud.ops.auth.common.dto.CaptchaDTO;
import com.rc.cloud.ops.auth.common.dto.LoginReturnDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static com.rc.cloud.common.core.constant.CacheConstants.DEFAULT_CODE_KEY;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RcTest
public class OAuth2ControllerTests {

    @Autowired
    private WebApplicationContext context;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void loginReturnUserInfo_success() throws Exception {
        CaptchaDTO captchaDTO = this.createCaptcha();
        String url = "http://localhost:8020/auth/oauth2/token?randomStr="
                + captchaDTO.getRandomStr()
                + "&code=" + captchaDTO.getCode()
                + "&grant_type=password&scope=server";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic cGlnOnBpZw==");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("username", "admin");
        map.add("password", "YehdBPev");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        String loginReturnStr = restTemplate.postForObject(url, request, String.class);
        assertNotNull(loginReturnStr);
        LoginReturnDTO loginReturnDTO = JSONObject.parseObject(loginReturnStr, LoginReturnDTO.class);
        assertNotNull(loginReturnDTO.getAccess_token());
        assertNotNull(loginReturnDTO.getRefresh_token());
    }

    public CaptchaDTO createCaptcha() {
        String randomStr = "26711687158751595";
        String url = "http://localhost:8020/code?randomStr=" + randomStr;
        restTemplate.getForObject(url, String.class);
        String code = stringRedisTemplate.opsForValue().get(DEFAULT_CODE_KEY + randomStr);
        CaptchaDTO captchaDTO = new CaptchaDTO();
        captchaDTO.setCode(code);
        captchaDTO.setRandomStr(randomStr);
        return captchaDTO;
    }
}
