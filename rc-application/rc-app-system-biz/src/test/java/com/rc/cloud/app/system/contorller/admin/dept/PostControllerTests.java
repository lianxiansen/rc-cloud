/**
 * @author oliveoil
 * date 2023-06-13 09:07
 */
package com.rc.cloud.app.system.contorller.admin.dept;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.system.controller.admin.dept.PostController;
import com.rc.cloud.app.system.vo.dept.post.PostCreateReqVO;
import com.rc.cloud.app.system.vo.dept.post.PostUpdateReqVO;
import com.rc.cloud.common.tenant.core.context.TenantContextHolder;
import com.rc.cloud.common.test.annotation.RcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 关联 {@link PostController} 类
 */
@RcTest
public class PostControllerTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

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
    @WithMockUser(username = "admin", authorities = {"sys:post:query"})
    public void getPostPage_success() throws Exception {
        mvc.perform(get("/sys/post/page"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.total").value(3))
                .andExpect(jsonPath("$.data.list").isArray())
                .andExpect(jsonPath("$.data.list").isNotEmpty())
                .andExpect(jsonPath("$.data.list[0].name").value("普通员工"));
    }

    @Test
    @WithMockUser(username = "admin")
    public void getPostListAllSimple_success() throws Exception {
        mvc.perform(get("/sys/post/list-all-simple"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data[0].name").value("董事长"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:post:query"})
    public void getPostByIdExist_then_success() throws Exception {
        mvc.perform(get("/sys/post/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("董事长"))
                .andExpect(jsonPath("$.data.code").value("ceo"))
                .andExpect(jsonPath("$.data.sort").value(1))
                .andExpect(jsonPath("$.data.status").value(0));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:post:query"})
    public void getPostByIdNotExist_then_throwNotFoundException() throws Exception {
        mvc.perform(get("/sys/post/9999999"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1002005000))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.msg").value("当前岗位不存在"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:post:create"})
    public void createPost_success() throws Exception {
        PostCreateReqVO postCreateReqVO = new PostCreateReqVO();
        postCreateReqVO.setName("测试职位");
        postCreateReqVO.setCode("cszw");
        postCreateReqVO.setSort(99);
        postCreateReqVO.setStatus(0);
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(postCreateReqVO);
        mvc.perform(post("/sys/post/create")
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
    @WithMockUser(username = "admin", authorities = {"sys:post:update"})
    public void updatePost_success() throws Exception {
        PostUpdateReqVO postUpdateReqVO = new PostUpdateReqVO();
        postUpdateReqVO.setId(2L);
        postUpdateReqVO.setName("前端2");
        postUpdateReqVO.setCode("front2");
        postUpdateReqVO.setSort(3);
        postUpdateReqVO.setStatus(0);
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(postUpdateReqVO);
        mvc.perform(put("/sys/post/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(true));
    }

    // 根据ID删除
    @Test
    @WithMockUser(username = "admin", authorities = {"sys:post:delete"})
    public void deletePostById_success() throws Exception {
        mvc.perform(delete("/sys/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[1]")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(true));
    }
// 批量删除
//    @Test
//    public void deletePost_success() throws Exception {
//        List<Long> idList = Lists.newArrayList(2L, 3L);
//        mvc.perform(delete("/sys/post")
//                        .header("Authorization", "Bearer " + getToken().getAccessToken())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(JSON.toJSONString(idList))
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(0))
//                .andExpect(jsonPath("$.message").value("success"));
//    }
}
