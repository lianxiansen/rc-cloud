/**
 * @author oliveoil
 * date 2023-05-02 07:57
 */
package com.rc.cloud.app.system.contorller.admin.dept;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.system.common.cache.RedisCache;
import com.rc.cloud.app.system.common.cache.RedisKeys;
import com.rc.cloud.common.test.annotation.RcTest;
import com.rc.cloud.app.system.controller.admin.dept.DeptController;
//import com.rc.cloud.app.system.service.auth.AdminAuthService;
//import com.rc.cloud.app.system.service.captcha.CaptchaService;
import com.rc.cloud.app.system.vo.auth.AuthLoginReqVO;
import com.rc.cloud.app.system.vo.auth.AuthLoginRespVO;
import com.rc.cloud.app.system.vo.dept.dept.DeptCreateReqVO;
import com.rc.cloud.app.system.vo.dept.dept.DeptUpdateReqVO;
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
 * 关联 {@link DeptController} 类
 */
@RcTest
public class DeptControllerTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;
//    @Resource
//    private AdminAuthService authService;

    @Resource
    private RedisCache redisCache;

//    @Resource
//    private CaptchaService captchaService;

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
                .andExpect(jsonPath("$.data[1].name").value("黄岩总公司"));
    }

    @Test
    public void getDeptById_success() throws Exception {
        mvc.perform(get("/sys/dept/100")
                        .header("Authorization", "Bearer " + getToken().getAccessToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.name").value("柔川信息"));
    }

    @Test
    public void getDeptById_when_ParentExist_then_returnParentName() throws Exception {
        mvc.perform(get("/sys/dept/101")
                        .header("Authorization", "Bearer " + getToken().getAccessToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.name").value("黄岩总公司"))
                .andExpect(jsonPath("$.data.parentName").value("柔川信息"));
    }

    @Test
    public void getDeptByIdNotExist_then_throwNotFoundException() throws Exception {
        mvc.perform(get("/sys/dept/9999999")
                        .header("Authorization", "Bearer " + getToken().getAccessToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1002004002))
                .andExpect(jsonPath("$.msg").value("当前部门不存在"));
    }

    @Test
    public void createDept_success() throws Exception {
        DeptCreateReqVO deptCreateReqVO = new DeptCreateReqVO();
        deptCreateReqVO.setName("测试项目组001");
        deptCreateReqVO.setSort(3);
        deptCreateReqVO.setParentId(101L);
        deptCreateReqVO.setPhone("12345678901");
        deptCreateReqVO.setEmail("123232@qq.com");
        deptCreateReqVO.setStatus(1);
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(deptCreateReqVO);
        mvc.perform(post("/sys/dept/create")
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

    // TODO:: create dept sad path

    @Test
    public void updateDept_success() throws Exception {
        DeptUpdateReqVO deptUpdateReqVO = new DeptUpdateReqVO();
        deptUpdateReqVO.setId(105L);
        deptUpdateReqVO.setName("测试部门2");
        deptUpdateReqVO.setSort(77);
        deptUpdateReqVO.setParentId(0L);
        deptUpdateReqVO.setStatus(1);
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(deptUpdateReqVO);
        mvc.perform(put("/sys/dept/update")
                        .header("Authorization", "Bearer " + getToken().getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(true));
    }

    @Test
    public void deleteDept_success() throws Exception {
        mvc.perform(delete("/sys/dept/109")
                        .header("Authorization", "Bearer " + getToken().getAccessToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(true));
    }

    @Test
    public void deleteDept_when_idNotExist_then_throwNotFoundException() throws Exception {
        mvc.perform(delete("/sys/dept/999999")
                        .header("Authorization", "Bearer " + getToken().getAccessToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1002004002))
                .andExpect(jsonPath("$.msg").value("当前部门不存在"))
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    public void deleteDept_when_childExist_then_throwNotFailedException() throws Exception {
        mvc.perform(delete("/sys/dept/101")
                        .header("Authorization", "Bearer " + getToken().getAccessToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1002004003))
                .andExpect(jsonPath("$.msg").value("存在子部门，无法删除"))
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    public void listDeptAllSimple_success() throws Exception {
        mvc.perform(get("/sys/dept/list-all-simple")
                        .header("Authorization", "Bearer " + getToken().getAccessToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray());
    }
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
//        String key = getCaptcha().getKey();
        String key = "1234";
        login.setKey(key);
        String captchaCode = getCaptchaCode(key);
        login.setCaptcha(captchaCode);
//        return authService.login(login);
        return null;
    }

//    private CaptchaVO getCaptcha() {
//        return captchaService.generate();
//    }

    private String getCaptchaCode(String key) {
        key = RedisKeys.getCaptchaKey(key);
        return (String) redisCache.get(key);
    }
}
