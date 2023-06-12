/**
 * @author oliveoil
 * date 2023-05-02 07:57
 */
package com.rc.cloud.app.system.contorller.admin.dept;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.system.common.cache.RedisCache;
import com.rc.cloud.app.system.common.cache.RedisKeys;
import com.rc.cloud.app.system.common.test.RcTest;
import com.rc.cloud.app.system.service.auth.AdminAuthService;
import com.rc.cloud.app.system.service.captcha.CaptchaService;
import com.rc.cloud.app.system.vo.auth.AuthLoginReqVO;
import com.rc.cloud.app.system.vo.auth.AuthLoginRespVO;
import com.rc.cloud.app.system.vo.captcha.CaptchaVO;
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

@RcTest
public class DeptControllerTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;
    @Resource
    private AdminAuthService authService;

    @Resource
    private RedisCache redisCache;

    @Resource
    private CaptchaService captchaService;

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
    public void getDeptList_success() throws Exception {
        mvc.perform(get("/sys/dept/list")
                        .header("Authorization", "Bearer " + getToken().getAccessToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data[1].name").value("深圳总公司"));
    }

//    @Test
//    public void getOrgByIdExist_then_success() throws Exception {
//        mvc.perform(get("/sys/org/3")
//                        .header("Authorization", "Bearer " + getAccessToken()))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(0))
//                .andExpect(jsonPath("$.message").value("success"))
//                .andExpect(jsonPath("$.data.orgName").value("IT部"));
//    }
//
//    @Test
//    public void getOrgById_when_ParentExist_then_return_parentName() throws Exception {
//        mvc.perform(get("/sys/org/6")
//                        .header("Authorization", "Bearer " + getAccessToken()))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(0))
//                .andExpect(jsonPath("$.message").value("success"))
//                .andExpect(jsonPath("$.data.orgName").value("项目组A"))
//                .andExpect(jsonPath("$.data.parentName").value("IT部"));
//    }
//
//    @Test
//    public void getOrgByIdNotExist_then_throwNotFoundException() throws Exception {
//        mvc.perform(get("/sys/org/9999999")
//                        .header("Authorization", "Bearer " + getAccessToken()))
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.code").value(10020))
//                .andExpect(jsonPath("$.message").value("机构不存在"));
//    }
//
//    @Test
//    public void saveOrg_success() throws Exception {
//        SysOrgCreateDTO createDTO = new SysOrgCreateDTO();
//        createDTO.setOrgName("测试项目组001");
//        createDTO.setSort(3);
//        createDTO.setPid(3L);
//        createDTO.setParentName("IT部");
//        ObjectMapper mapper = new ObjectMapper();
//        String requestBody = mapper.writerWithDefaultPrettyPrinter()
//                .writeValueAsString(createDTO);
//        mvc.perform(post("/sys/org/save")
//                        .header("Authorization", "Bearer " + getAccessToken())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(0))
//                .andExpect(jsonPath("$.message").value("success"))
//                .andExpect(jsonPath("$.data.orgName").value("测试项目组001"))
//                .andExpect(jsonPath("$.data.parentName").value("IT部"))
//                .andExpect(jsonPath("$.data.children").isEmpty())
//                .andExpect(jsonPath("$.data.sort").value(3))
//                .andExpect(jsonPath("$.data.pid").value(3));
//    }
//
//    @Test
//    public void updateOrg_success() throws Exception {
//        SysOrgUpdateDTO updateDTO = new SysOrgUpdateDTO();
//        updateDTO.setId(3L);
//        updateDTO.setOrgName("IT部2部");
//        updateDTO.setSort(3);
//        updateDTO.setPid(0L);
//        ObjectMapper mapper = new ObjectMapper();
//        String requestBody = mapper.writerWithDefaultPrettyPrinter()
//                .writeValueAsString(updateDTO);
//        mvc.perform(put("/sys/org/update")
//                        .header("Authorization", "Bearer " + getAccessToken())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(0))
//                .andExpect(jsonPath("$.message").value("success"))
//                .andExpect(jsonPath("$.data.orgName").value("IT部2部"))
//                .andExpect(jsonPath("$.data.sort").value(3));
//    }
//
//    @Test
//    public void deleteOrg_success() throws Exception {
//        mvc.perform(delete("/sys/org/6")
//                        .header("Authorization", "Bearer " + getAccessToken()))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(0))
//                .andExpect(jsonPath("$.message").value("success"));
//    }
//
//    @Test
//    public void deleteOrg_when_idNotExist_then_throwNotFoundException() throws Exception {
//        mvc.perform(delete("/sys/org/999999")
//                        .header("Authorization", "Bearer " + getAccessToken()))
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.code").value(10020))
//                .andExpect(jsonPath("$.message").value("删除失败，该机构不存在"));
//    }
//
//    @Test
//    public void deleteOrg_when_childExist_then_throwNotFailedException() throws Exception {
//        mvc.perform(delete("/sys/org/3")
//                        .header("Authorization", "Bearer " + getAccessToken()))
//                .andDo(print())
//                .andExpect(status().isInternalServerError())
//                .andExpect(jsonPath("$.code").value(10200))
//                .andExpect(jsonPath("$.message").value("删除失败，请先删除该机构下的子机构"));
//    }
//
//    @Test
//    public void deleteOrg_when_hasUser_then_throwNotFailedException() throws Exception {
//        mvc.perform(delete("/sys/org/7")
//                        .header("Authorization", "Bearer " + getAccessToken()))
//                .andDo(print())
//                .andExpect(status().isInternalServerError())
//                .andExpect(jsonPath("$.code").value(10200))
//                .andExpect(jsonPath("$.message").value("该机构下有用户，不能删除"));
//    }

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
