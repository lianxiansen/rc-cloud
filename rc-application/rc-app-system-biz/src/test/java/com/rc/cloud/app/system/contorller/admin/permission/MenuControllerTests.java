/**
 * @author oliveoil
 * date 2023-06-13 13:23
 */
package com.rc.cloud.app.system.contorller.admin.permission;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.system.common.cache.RedisCache;
import com.rc.cloud.app.system.common.cache.RedisKeys;
import com.rc.cloud.common.test.annotation.RcTest;
import com.rc.cloud.app.system.controller.admin.permission.MenuController;
import com.rc.cloud.app.system.enums.permission.MenuTypeEnum;
import com.rc.cloud.app.system.service.permission.MenuService;
import com.rc.cloud.app.system.vo.auth.AuthLoginReqVO;
import com.rc.cloud.app.system.vo.auth.AuthLoginRespVO;
import com.rc.cloud.app.system.vo.permission.menu.MenuCreateReqVO;
import com.rc.cloud.app.system.vo.permission.menu.MenuUpdateReqVO;
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
 * 关联 {@link MenuController} 类
 */
@RcTest
public class MenuControllerTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

//    @Resource
//    private AdminAuthService authService;

    @Resource
    private RedisCache redisCache;

//    @Resource
//    private CaptchaService captchaService;

    @Resource
    private MenuService menuService;

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
    public void createDirMenu_success() throws Exception {
        // 添加目录
        MenuCreateReqVO menuCreateReqVO = new MenuCreateReqVO();
        menuCreateReqVO.setName("测试目录");
        menuCreateReqVO.setType(MenuTypeEnum.DIR.getType());
        menuCreateReqVO.setSort(1);
        menuCreateReqVO.setParentId(0L);
        menuCreateReqVO.setPath("/test");
        menuCreateReqVO.setIcon("test");
        menuCreateReqVO.setComponent("test/menu");
        menuCreateReqVO.setComponentName("TestMenu");
        menuCreateReqVO.setStatus(0);
        menuCreateReqVO.setVisible(true);
        menuCreateReqVO.setKeepAlive(true);
        menuCreateReqVO.setAlwaysShow(true);
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(menuCreateReqVO);
        mvc.perform(post("/sys/menu/create")
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
    public void updateDictData_success() throws Exception {
        MenuUpdateReqVO menuUpdateReqVO = new MenuUpdateReqVO();
        menuUpdateReqVO.setId(1L);
        menuUpdateReqVO.setName("系统管理2");
        menuUpdateReqVO.setParentId(0L);
        menuUpdateReqVO.setStatus(0);
        menuUpdateReqVO.setType(MenuTypeEnum.DIR.getType());
        menuUpdateReqVO.setSort(100);
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(menuUpdateReqVO);
        mvc.perform(put("/sys/menu/update")
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

    // 根据ID删除
    @Test
    public void deleteMenuById_success() throws Exception {
        MenuCreateReqVO menuCreateReqVO = new MenuCreateReqVO();
        menuCreateReqVO.setName("测试目录");
        menuCreateReqVO.setType(MenuTypeEnum.DIR.getType());
        menuCreateReqVO.setSort(1);
        menuCreateReqVO.setParentId(0L);
        menuCreateReqVO.setPath("/test");
        menuCreateReqVO.setIcon("test");
        menuCreateReqVO.setComponent("test/menu");
        menuCreateReqVO.setComponentName("TestMenu");
        menuCreateReqVO.setStatus(0);
        menuCreateReqVO.setVisible(true);
        menuCreateReqVO.setKeepAlive(true);
        menuCreateReqVO.setAlwaysShow(true);
        Long menuId = menuService.createMenu(menuCreateReqVO);
        mvc.perform(delete("/sys/menu/" + menuId)
                        .header("Authorization", "Bearer " + getToken().getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(true));
    }

    @Test
    public void getMenuList_success() throws Exception {
        mvc.perform(get("/sys/menu/list")
                        .header("Authorization", "Bearer " + getToken().getAccessToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    public void getMenuListAllSimple_success() throws Exception {
        mvc.perform(get("/sys/menu/list-all-simple")
                        .header("Authorization", "Bearer " + getToken().getAccessToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    public void getMenuById_success() throws Exception {
        mvc.perform(get("/sys/menu/get/" + 1)
                        .header("Authorization", "Bearer " + getToken().getAccessToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("系统管理"));
    }

    private AuthLoginRespVO getToken() {
        AuthLoginReqVO login = new AuthLoginReqVO();
        login.setUsername("admin");
        login.setPassword("123456");
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
