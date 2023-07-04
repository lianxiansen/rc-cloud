/**
 * @author oliveoil
 * date 2023-06-13 13:23
 */
package com.rc.cloud.app.system.contorller.admin.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.system.controller.admin.user.UserController;
import com.rc.cloud.app.system.vo.user.user.UserCreateReqVO;
import com.rc.cloud.app.system.vo.user.user.UserUpdatePasswordReqVO;
import com.rc.cloud.app.system.vo.user.user.UserUpdateReqVO;
import com.rc.cloud.app.system.vo.user.user.UserUpdateStatusReqVO;
import com.rc.cloud.common.tenant.core.context.TenantContextHolder;
import com.rc.cloud.common.test.annotation.RcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 关联 {@link UserController} 类
 */
@RcTest
public class UserControllerTests {

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
    @WithMockUser(username = "admin", authorities = {"sys:user:create"})
    public void createUser_success() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getPrincipal());
        UserCreateReqVO createReqVO = new UserCreateReqVO();
        createReqVO.setUsername("testuser123");
        createReqVO.setNickname("test_nickname");
        createReqVO.setAvatar("www.baidu.com");
        createReqVO.setRemark("备注");
        createReqVO.setDeptId(100L);
        createReqVO.setPostIds(new HashSet<Long>() {{add(1L); add(2L);}});
        createReqVO.setEmail("123232@qq.com");
        createReqVO.setMobile("13777777766");
        createReqVO.setSex(1);
        createReqVO.setPassword("123456");
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(createReqVO);
        mvc.perform(post("/sys/user/create")
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
    @WithMockUser(username = "admin", authorities = {"sys:user:update"})
    public void updateUser_success() throws Exception {
        UserUpdateReqVO updateReqVO = new UserUpdateReqVO();
        updateReqVO.setId(1L);
        updateReqVO.setUsername("testuser12377");
        updateReqVO.setNickname("test_nickname");
        updateReqVO.setAvatar("www.baidu.com");
        updateReqVO.setRemark("备注");
        updateReqVO.setDeptId(100L);
        updateReqVO.setPostIds(new HashSet<Long>() {{add(1L); add(2L);}});
        updateReqVO.setEmail("123232@qq.com");
        updateReqVO.setMobile("13777777789");
        updateReqVO.setRoleIds(new HashSet<Long>() {{add(1L); add(2L);}});
        updateReqVO.setSex(1);
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(updateReqVO);
        mvc.perform(put("/sys/user/update")
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
    @WithMockUser(username = "admin", authorities = {"sys:user:delete"})
    public void deleteUser_success() throws Exception {
        mvc.perform(delete("/sys/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[1,2]")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(true));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:user:update-password"})
    public void updateUserPassword_success() throws Exception {
        UserUpdatePasswordReqVO updatePasswordReqVO = new UserUpdatePasswordReqVO();
        updatePasswordReqVO.setId(1L);
        updatePasswordReqVO.setPassword("1234567");
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(updatePasswordReqVO);
        mvc.perform(put("/sys/user/update-password")
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
    @WithMockUser(username = "admin", authorities = {"sys:user:update"})
    public void updateUserStatus_success() throws Exception {
        UserUpdateStatusReqVO updateStatusReqVO = new UserUpdateStatusReqVO();
        updateStatusReqVO.setId(1L);
        updateStatusReqVO.setStatus(1);
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(updateStatusReqVO);
        mvc.perform(put("/sys/user/update-status")
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
    @WithMockUser(username = "admin", authorities = {"sys:user:query"})
    public void getUserPage_success() throws Exception {
        mvc.perform(get("/sys/user/page"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.total").value(2))
                .andExpect(jsonPath("$.data.list").isArray())
                .andExpect(jsonPath("$.data.list").isNotEmpty())
                .andExpect(jsonPath("$.data.list[0].username").value("hqftest123"));
    }

    @Test
    @WithMockUser(username = "admin")
    public void getUserListAllSimple_success() throws Exception {
        mvc.perform(get("/sys/user/list-all-simple"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data[0].nickname").value("rc"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:user:query"})
    public void getUserById_then_success() throws Exception {
        mvc.perform(get("/sys/user/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.username").value("admin"));
    }
}
