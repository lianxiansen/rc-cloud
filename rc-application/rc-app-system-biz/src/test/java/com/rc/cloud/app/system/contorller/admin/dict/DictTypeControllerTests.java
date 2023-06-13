/**
 * @author oliveoil
 * date 2023-06-13 09:46
 */
package com.rc.cloud.app.system.contorller.admin.dict;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.system.common.cache.RedisCache;
import com.rc.cloud.app.system.common.cache.RedisKeys;
import com.rc.cloud.app.system.common.test.RcTest;
import com.rc.cloud.app.system.controller.admin.dict.DictTypeController;
import com.rc.cloud.app.system.service.auth.AdminAuthService;
import com.rc.cloud.app.system.service.captcha.CaptchaService;
import com.rc.cloud.app.system.service.dict.DictTypeService;
import com.rc.cloud.app.system.vo.auth.AuthLoginReqVO;
import com.rc.cloud.app.system.vo.auth.AuthLoginRespVO;
import com.rc.cloud.app.system.vo.captcha.CaptchaVO;
import com.rc.cloud.app.system.vo.dict.type.DictTypeCreateReqVO;
import com.rc.cloud.app.system.vo.dict.type.DictTypeUpdateReqVO;
import com.rc.cloud.common.tenant.core.context.TenantContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 关联 {@link DictTypeController} 类
 */
@RcTest
public class DictTypeControllerTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Resource
    private AdminAuthService authService;

    @Resource
    private RedisCache redisCache;

    @Resource
    private CaptchaService captchaService;

    @Resource
    private DictTypeService dictTypeService;

    @Qualifier("springSecurityFilterChain")
    @BeforeEach
    public void setup() {
        TenantContextHolder.setTenantId(1L);
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void getDictTypeById_success() throws Exception {
        mvc.perform(get("/sys/dict-type/" + 1)
                        .header("Authorization", "Bearer " + getToken().getAccessToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("用户性别"));
    }

    // list-all-simple
    @Test
    public void listDictTypeAllSimple_success() throws Exception {
        mvc.perform(get("/sys/dict-type/list-all-simple")
                        .header("Authorization", "Bearer " + getToken().getAccessToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data[0].name").value("用户性别"));
    }

    @Test
    public void getDictTypePage_success() throws Exception {
        mvc.perform(get("/sys/dict-type/page")
                        .header("Authorization", "Bearer " + getToken().getAccessToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.total").value(56))
                .andExpect(jsonPath("$.data.list").isArray())
                .andExpect(jsonPath("$.data.list").isNotEmpty())
                .andExpect(jsonPath("$.data.list[0].name").value("公众号自动回复的请求关键字匹配模式"));
    }

    @Test
    public void createDictType_success() throws Exception {
        DictTypeCreateReqVO dictTypeCreateReqVO = new DictTypeCreateReqVO();
        dictTypeCreateReqVO.setName("测试字段类型");
        dictTypeCreateReqVO.setStatus(0);
        dictTypeCreateReqVO.setRemark("备注");
        dictTypeCreateReqVO.setType("test_type");
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(dictTypeCreateReqVO);
        mvc.perform(post("/sys/dict-type/create")
                        .header("Authorization", "Bearer " + getToken().getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    public void updateDictType_success() throws Exception {
        DictTypeUpdateReqVO dictTypeUpdateReqVO = new DictTypeUpdateReqVO();
        dictTypeUpdateReqVO.setId(1L);
        dictTypeUpdateReqVO.setName("测试字段类型");
        dictTypeUpdateReqVO.setStatus(0);
        dictTypeUpdateReqVO.setRemark("备注");
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(dictTypeUpdateReqVO);
        mvc.perform(put("/sys/dict-type/update")
                        .header("Authorization", "Bearer " + getToken().getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    // 根据ID删除
    @Test
    public void deleteDictTypeById_success() throws Exception {
        DictTypeCreateReqVO dictTypeCreateReqVO = new DictTypeCreateReqVO();
        dictTypeCreateReqVO.setName("测试字段类型");
        dictTypeCreateReqVO.setStatus(0);
        dictTypeCreateReqVO.setRemark("备注");
        dictTypeCreateReqVO.setType("test_type");
        Long dictTypeId = dictTypeService.createDictType(dictTypeCreateReqVO);
        mvc.perform(delete("/sys/dict-type/" + dictTypeId)
                        .header("Authorization", "Bearer " + getToken().getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(true));
    }


    private AuthLoginRespVO getToken() {
        AuthLoginReqVO login = new AuthLoginReqVO();
        login.setUsername("admin");
        login.setPassword("123456");
        String key = getCaptcha().getKey();
        login.setKey(key);
        String captchaCode = getCaptchaCode(key);
        login.setCaptcha(captchaCode);
        return authService.login(login);
    }

    private CaptchaVO getCaptcha() {
        return captchaService.generate();
    }

    private String getCaptchaCode(String key) {
        key = RedisKeys.getCaptchaKey(key);
        return (String) redisCache.get(key);
    }
}
