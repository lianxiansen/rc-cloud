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
     * 可能在取redis中的验证码的时候redis中还没生成，导致测试失败，debug调试
     */
    @Test
    public void loginByPassword_success_then_returnToken() throws Exception {
        LoginReturnDTO loginReturnDTO = login();
        assertNotNull(loginReturnDTO);
        assertNotNull(loginReturnDTO.getAccess_token());
        assertNotNull(loginReturnDTO.getRefresh_token());
    }

    /**
     * 通过refresh_token刷新token
     * 可能在取redis中的验证码的时候redis中还没生成，导致测试失败，debug调试
     * @throws Exception
     */
    @Test
    public void refreshToken_success() throws Exception {
        // 根据用户名密码获取token
        LoginReturnDTO loginReturnDTO = login();
        assertNotNull(loginReturnDTO);
        assertNotNull(loginReturnDTO.getAccess_token());
        assertNotNull(loginReturnDTO.getRefresh_token());

        // 根据refresh_token刷新token
        String url = "http://localhost:8020/auth/oauth2/token?grant_type=refresh_token&scope=server&refresh_token=" + loginReturnDTO.getRefresh_token();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic cmM6cmM=");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>(1);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        String loginReturnStr = restTemplate.postForObject(url, request, String.class);
        assertNotNull(loginReturnStr);
        loginReturnDTO = JSONObject.parseObject(loginReturnStr, LoginReturnDTO.class);
        assertNotNull(loginReturnDTO.getAccess_token());
        assertNotNull(loginReturnDTO.getRefresh_token());
    }

    private LoginReturnDTO login() throws Exception {
        String key = this.createCaptcha();
        String code = stringRedisTemplate.opsForValue().get(DEFAULT_CODE_KEY + key);
        String url = "http://localhost:8020/auth/oauth2/token?key="
                + key
                + "&code=" + code
                + "&grant_type=password&scope=server";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic cmNfYWRtaW46cmNfYWRtaW4xMjM0NTY=");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("username", "admin");
        map.add("password", "YehdBPev");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        String loginReturnStr = restTemplate.postForObject(url, request, String.class);
        LoginReturnDTO loginReturnDTO = null;
        if (loginReturnStr != null) {
            loginReturnDTO = JSONObject.parseObject(loginReturnStr, LoginReturnDTO.class);
        }
        return loginReturnDTO;
    }

    private String createCaptcha() {
        String key = "26711687158751595";
        String url = "http://localhost:8020/code?key=" + key;
        restTemplate.getForObject(url, String.class);
        return key;
    }
}
