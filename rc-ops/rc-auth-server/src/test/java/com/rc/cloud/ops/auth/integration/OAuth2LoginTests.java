/**
 * @author oliveoil
 * date 2023-06-21 14:59
 */
package com.rc.cloud.ops.auth.integration;

import com.alibaba.fastjson.JSONObject;
import com.rc.cloud.common.test.annotation.RcTest;
import com.rc.cloud.ops.auth.common.dto.LoginReturnDTO;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

import static com.rc.cloud.common.core.constant.CacheConstants.DEFAULT_CODE_KEY;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RcTest
public class OAuth2LoginTests {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 可能因为网络问题，导致验证码生成延迟，导致测试失败，可以多试几次
     * @throws Exception
     */
    @Test
    public void loginReturnToken_success() throws Exception {
        String key = this.createCaptcha();
        String code = stringRedisTemplate.opsForValue().get(DEFAULT_CODE_KEY + key);
        String url = "http://localhost:8020/auth/oauth2/token?key="
                + key
                + "&code=" + code
                + "&grant_type=password&scope=server";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic cmM6cmM=");
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

    public String createCaptcha() {
        String key = "26711687158751595";
        String url = "http://localhost:8020/code?key=" + key;
        restTemplate.getForObject(url, String.class);
        return key;
    }
}
