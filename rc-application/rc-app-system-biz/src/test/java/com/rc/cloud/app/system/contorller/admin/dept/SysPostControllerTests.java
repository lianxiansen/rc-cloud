///**
// * @author oliveoil
// * date 2023-05-02 10:58
// */
//package com.rc.cloud.app.system.contorller.admin.dept;
//
//import com.alibaba.fastjson.JSON;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.common.collect.Lists;
//import com.yxnet.system.application.auth.SysAccountLoginDTO;
//import com.yxnet.system.application.auth.SysAuthService;
//import com.yxnet.system.application.post.SysPostCreateDTO;
//import com.yxnet.system.application.post.SysPostUpdateDTO;
//import com.yxnet.system.common.test.YxTest;
//import com.yxnet.system.common.vo.SysTokenVO;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.List;
//
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@YxTest
//public class SysPostControllerTests {
//
//    @Autowired
//    private WebApplicationContext context;
//
//    @Autowired
//    private SysAuthService sysAuthService;
//
//    private MockMvc mvc;
//
//    @Qualifier("springSecurityFilterChain")
//    @BeforeEach
//    public void setup() {
//        mvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//    }
//
//    @Test
//    public void getPostPage_success() throws Exception {
//        mvc.perform(get("/sys/post/page")
//                        .header("Authorization", "Bearer " + getAccessToken()))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(0))
//                .andExpect(jsonPath("$.message").value("success"))
//                .andExpect(jsonPath("$.data.total").value(2))
//                .andExpect(jsonPath("$.data.list").isArray())
//                .andExpect(jsonPath("$.data.list").isNotEmpty())
//                .andExpect(jsonPath("$.data.list[0].postName").value("前端"));
//    }
//
//    @Test
//    public void getPostList_success() throws Exception {
//        mvc.perform(get("/sys/post/list")
//                        .header("Authorization", "Bearer " + getAccessToken()))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(0))
//                .andExpect(jsonPath("$.message").value("success"))
//                .andExpect(jsonPath("$.data").isArray())
//                .andExpect(jsonPath("$.data").isNotEmpty())
//                .andExpect(jsonPath("$.data[0].postName").value("前端"));
//    }
//
//    @Test
//    public void getPostByIdExist_then_success() throws Exception {
//        mvc.perform(get("/sys/post/2")
//                        .header("Authorization", "Bearer " + getAccessToken()))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(0))
//                .andExpect(jsonPath("$.message").value("success"))
//                .andExpect(jsonPath("$.data.postName").value("前端"));
//    }
//
//    @Test
//    public void getPostByIdNotExist_then_throwNotFoundException() throws Exception {
//        mvc.perform(get("/sys/post/9999999")
//                        .header("Authorization", "Bearer " + getAccessToken()))
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.code").value(10020))
//                .andExpect(jsonPath("$.message").value("岗位不存在"));
//    }
//
//    @Test
//    public void savePost_success() throws Exception {
//        SysPostCreateDTO createDTO = new SysPostCreateDTO();
//        createDTO.setPostName("测试职位");
//        createDTO.setPostCode("cszw");
//        createDTO.setSort(3);
//        createDTO.setStatus(1);
//        ObjectMapper mapper = new ObjectMapper();
//        String requestBody = mapper.writerWithDefaultPrettyPrinter()
//                .writeValueAsString(createDTO);
//        mvc.perform(post("/sys/post/save")
//                        .header("Authorization", "Bearer " + getAccessToken())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(0))
//                .andExpect(jsonPath("$.message").value("success"))
//                .andExpect(jsonPath("$.data.postName").value("测试职位"))
//                .andExpect(jsonPath("$.data.postCode").value("cszw"))
//                .andExpect(jsonPath("$.data.sort").value(3))
//                .andExpect(jsonPath("$.data.status").value(1));
//    }
//
//    @Test
//    public void updatePost_success() throws Exception {
//        SysPostUpdateDTO updateDTO = new SysPostUpdateDTO();
//        updateDTO.setId(2L);
//        updateDTO.setPostName("前端2");
//        updateDTO.setPostCode("front2");
//        updateDTO.setSort(3);
//        updateDTO.setStatus(1);
//        ObjectMapper mapper = new ObjectMapper();
//        String requestBody = mapper.writerWithDefaultPrettyPrinter()
//                .writeValueAsString(updateDTO);
//        mvc.perform(put("/sys/post/update")
//                        .header("Authorization", "Bearer " + getAccessToken())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(0))
//                .andExpect(jsonPath("$.message").value("success"))
//                .andExpect(jsonPath("$.data.postName").value("前端2"))
//                .andExpect(jsonPath("$.data.postCode").value("front2"))
//                .andExpect(jsonPath("$.data.sort").value(3))
//                .andExpect(jsonPath("$.data.status").value(1));
//    }
//
//    @Test
//    public void deletePost_success() throws Exception {
//        List<Long> idList = Lists.newArrayList(2L, 3L);
//        mvc.perform(delete("/sys/post")
//                        .header("Authorization", "Bearer " + getAccessToken())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(JSON.toJSONString(idList))
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(0))
//                .andExpect(jsonPath("$.message").value("success"));
//    }
//
//    private String getAccessToken() {
//        SysAccountLoginDTO login = new SysAccountLoginDTO();
//        login.setUsername("admin_test");
//        login.setPassword("123");
//        login.setKey("xxx");
//        login.setCaptcha("xxx");
//        SysTokenVO token = sysAuthService.loginAndReturnDoubleToken(login);
//        return token.getAccess_token();
//    }
//}
