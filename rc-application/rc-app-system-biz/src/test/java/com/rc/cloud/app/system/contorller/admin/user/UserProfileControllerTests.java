/**
 * @author oliveoil
 * date 2023-06-13 13:23
 */
package com.rc.cloud.app.system.contorller.admin.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.system.controller.admin.v1.user.UserProfileController;
import com.rc.cloud.app.system.mapper.tenant.TenantPackageMapper;
import com.rc.cloud.app.system.model.tenant.SysTenantPO;
import com.rc.cloud.app.system.model.tenant.SysTenantPackagePO;
import com.rc.cloud.app.system.model.user.SysUserPO;
import com.rc.cloud.app.system.service.tenant.TenantService;
import com.rc.cloud.app.system.service.user.AdminUserService;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantCreateReqVO;
import com.rc.cloud.app.system.vo.user.profile.UserProfileUpdatePasswordReqVO;
import com.rc.cloud.app.system.vo.user.profile.UserProfileUpdateReqVO;
import com.rc.cloud.app.system.vo.user.user.UserCreateReqVO;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.core.enums.SexEnum;
import com.rc.cloud.common.tenant.core.context.TenantContextHolder;
import com.rc.cloud.common.test.annotation.RcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

import static com.rc.cloud.common.core.util.date.LocalDateTimeUtils.buildTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 关联 {@link UserProfileController} 类
 */
@RcTest
public class UserProfileControllerTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Resource
    private AdminUserService userService;

    @Resource
    private TenantPackageMapper tenantPackageMapper;

    @Resource
    private TenantService tenantService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Qualifier("springSecurityFilterChain")
    @BeforeEach
    public void setup() {
        SysTenantPO tenant = createTenant();
        TenantContextHolder.setTenantId(tenant.getId());
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @Test
    @WithMockUser("admin")
    public void getUserProfileById_success() throws Exception {
        SysUserPO user = createUser();
        mvc.perform(get("/admin/user/profile/get"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.username").value(user.getUsername()))
                .andExpect(jsonPath("$.data.nickname").value(user.getNickname()))
                .andExpect(jsonPath("$.data.remark").value(user.getRemark()))
                .andExpect(jsonPath("$.data.email").value(user.getEmail()))
                .andExpect(jsonPath("$.data.mobile").value(user.getMobile()))
                .andExpect(jsonPath("$.data.sex").value(user.getSex()))
                .andExpect(jsonPath("$.data.status").value(user.getStatus()));
    }

    @Test
    @WithMockUser("admin")
    public void updateUserProfile_success() throws Exception {
        SysUserPO user = createUser();
        UserProfileUpdateReqVO updateReqVO = new UserProfileUpdateReqVO();
        updateReqVO.setNickname("admin1");
        updateReqVO.setEmail("32737238@qq.com");
        updateReqVO.setMobile("13666666666");
        updateReqVO.setSex(SexEnum.FEMALE.getSex());
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(updateReqVO);
        mvc.perform(put("/admin/user/profile/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(true));
        SysUserPO dbUser = userService.getUserByUsername(user.getUsername());
        assertEquals(updateReqVO.getNickname(), dbUser.getNickname());
        assertEquals(updateReqVO.getEmail(), dbUser.getEmail());
        assertEquals(updateReqVO.getMobile(), dbUser.getMobile());
        assertEquals(updateReqVO.getSex(), dbUser.getSex());
    }

    @Test
    @WithMockUser("admin")
    public void updatePassword_success() throws Exception {
        SysUserPO user = createUser();
        UserProfileUpdatePasswordReqVO updateReqVO = new UserProfileUpdatePasswordReqVO();
        updateReqVO.setOldPassword("123456");
        updateReqVO.setNewPassword("1234567");
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(updateReqVO);
        mvc.perform(put("/admin/user/profile/update-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isNotEmpty());
        SysUserPO dbUser = userService.getUserByUsername(user.getUsername());
        assertTrue(passwordEncoder.matches(updateReqVO.getNewPassword(), dbUser.getPassword()));
    }

    @Test
    public void createPassword() {
        String encode = passwordEncoder.encode("123456");
        System.out.println("加密结果：" + encode);
    }

    @Test
    public void matchesPassword() {
        assertTrue(passwordEncoder.matches("123456", "{bcrypt}$2a$10$N/dANgQ.fBEFXSJDO5HUbO7lroYAbpWOm2z/IsmczXSmstDquYeRq"));
    }

    private SysUserPO createUser() {
        UserCreateReqVO createReqVO = new UserCreateReqVO();
        createReqVO.setUsername("admin");
        createReqVO.setNickname("test_nickname111");
        createReqVO.setAvatar("www.baidu.com111");
        createReqVO.setRemark("备注111");
        createReqVO.setEmail("123232111@qq.com");
        createReqVO.setMobile("13777777111");
        createReqVO.setSex(1);
        createReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        createReqVO.setPassword("123456");
        String userId = userService.createUser(createReqVO);
        return userService.getUser(userId);
    }

    private SysTenantPO createTenant() {
        SysTenantPackagePO tenantPackage = this.createTenantPackage();
        TenantCreateReqVO tenantCreateReqVO = new TenantCreateReqVO();
        tenantCreateReqVO.setUsername("testuser123");
        tenantCreateReqVO.setPassword("test_password");
        tenantCreateReqVO.setName("test_tenant_name");
        tenantCreateReqVO.setDomain("https://www.baidu.com");
        tenantCreateReqVO.setContactName("huang");
        tenantCreateReqVO.setContactMobile("13777777777");
        tenantCreateReqVO.setPackageId(tenantPackage.getId());
        tenantCreateReqVO.setStatus(0);
        tenantCreateReqVO.setAccountCount(10000);
        tenantCreateReqVO.setExpireTime(buildTime(2033, 2, 2));
        String tenantId = tenantService.createTenant(tenantCreateReqVO);
        SysTenantPO tenant = tenantService.getTenant(tenantId);
        return tenant;
    }

    private SysTenantPackagePO createTenantPackage() {
        SysTenantPackagePO tenantPackage = new SysTenantPackagePO();
        tenantPackage.setName("test_tenant_name1");
        tenantPackage.setStatus(CommonStatusEnum.ENABLE.getStatus());
        tenantPackage.setRemark("test_tenant_remark1");
        Set<String> menuIds = new HashSet<>();
        menuIds.add("1");
        tenantPackage.setMenuIds(menuIds);
        tenantPackageMapper.insert(tenantPackage);
        return tenantPackage;
    }
}
