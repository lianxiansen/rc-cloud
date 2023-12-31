package com.rc.cloud.app.system.contorller.admin.permission;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.system.controller.admin.v1.permission.RoleController;
import com.rc.cloud.app.system.mapper.permission.RoleMapper;
import com.rc.cloud.app.system.model.permission.SysRolePO;
import com.rc.cloud.app.system.vo.permission.role.RoleCreateReqVO;
import com.rc.cloud.app.system.vo.permission.role.RoleUpdateReqVO;
import com.rc.cloud.app.system.vo.permission.role.RoleUpdateStatusReqVO;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.tenant.core.context.TenantContextHolder;
import com.rc.cloud.common.test.annotation.RcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author oliveoil
 * @date 2023-06-13 13:23
 * 关联 {@link RoleController} 类
 */
@RcTest
public class RoleControllerTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Resource
    private RoleMapper roleMapper;

    @Qualifier("springSecurityFilterChain")
    @BeforeEach
    public void setup() {
        TenantContextHolder.setTenantId("1");
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    /**
     * @author rc@hqf
     * @date 2023/07/27
     * @description 创建角色相关测试
     */
    @Nested
    class CreateRoleTests {

        // happy path: 创建角色成功
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:role:create"})
        public void createRole_success() throws Exception {
            RoleCreateReqVO createReqVO = new RoleCreateReqVO();
            createReqVO.setName("测试角色");
            createReqVO.setSort(3);
            createReqVO.setCode("test_role");
            createReqVO.setRemark("备注");
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(createReqVO);
            String postRes = mvc.perform(post("/admin/role/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").isNotEmpty())
                    .andReturn().getResponse().getContentAsString();   // 将相应的数据转换为字符串
            String roleId = JSONObject.parseObject(postRes).get("data").toString();
            SysRolePO role = roleMapper.selectById(roleId);
            assertNotEquals(null, role);
            assertEquals(createReqVO.getName(), role.getName());
            assertEquals(createReqVO.getSort(), role.getSort());
            assertEquals(createReqVO.getCode(), role.getCode());
            assertEquals(createReqVO.getRemark(), role.getRemark());
            assertEquals(CommonStatusEnum.ENABLE.getStatus(), role.getStatus());
            assertEquals(2, role.getType()); // 默认为2
        }

        // sad path1: 角色名称已存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:role:create"})
        public void createRole_failed_nameExist() throws Exception {
            SysRolePO role = createRole();
            RoleCreateReqVO createReqVO = new RoleCreateReqVO();
            createReqVO.setName(role.getName());
            createReqVO.setSort(3);
            createReqVO.setCode("test_role");
            createReqVO.setRemark("备注");
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(createReqVO);
            mvc.perform(post("/admin/role/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002002001))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("已经存在名为【" + createReqVO.getName() + "】的角色"));
        }

        // sad path2: 角色编码已存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:role:create"})
        public void createRole_failed_codeExist() throws Exception {
            SysRolePO role = createRole();
            RoleCreateReqVO createReqVO = new RoleCreateReqVO();
            createReqVO.setName("测试角色111");
            createReqVO.setSort(3);
            createReqVO.setCode(role.getCode());
            createReqVO.setRemark("备注");
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(createReqVO);
            mvc.perform(post("/admin/role/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002002002))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("已经存在编码为【" + createReqVO.getCode() + "】的角色"));
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/27
     * @description 更新角色相关测试
     */
    @Nested
    class UpdateRoleTests {

        // happy path: 更新角色成功
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:role:update"})
        public void updateRole_success() throws Exception {
            SysRolePO role = createRole();
            RoleUpdateReqVO updateReqVO = new RoleUpdateReqVO();
            updateReqVO.setId(role.getId());
            updateReqVO.setName("测试角色111");
            updateReqVO.setSort(77);
            updateReqVO.setCode("test_role");
            updateReqVO.setRemark("备注");
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(updateReqVO);
            mvc.perform(put("/admin/role/update")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").value(true));
            SysRolePO dbRole = roleMapper.selectById(role.getId());
            assertEquals(updateReqVO.getName(), dbRole.getName());
            assertEquals(updateReqVO.getSort(), dbRole.getSort());
            assertEquals(updateReqVO.getCode(), dbRole.getCode());
            assertEquals(updateReqVO.getRemark(), dbRole.getRemark());
        }

        // sad path1: 角色名称已存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:role:update"})
        public void updateRole_failed_nameExist() throws Exception {
            SysRolePO role = createRole();
            SysRolePO role2 = createRole2();
            RoleUpdateReqVO updateReqVO = new RoleUpdateReqVO();
            updateReqVO.setId(role.getId());
            updateReqVO.setName(role2.getName());
            updateReqVO.setSort(77);
            updateReqVO.setCode("test_role");
            updateReqVO.setRemark("备注");
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(updateReqVO);
            mvc.perform(put("/admin/role/update")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002002001))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("已经存在名为【" + updateReqVO.getName() + "】的角色"));
        }

        // sad path2: 角色编码已存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:role:update"})
        public void updateRole_failed_codeExist() throws Exception {
            SysRolePO role = createRole();
            SysRolePO role2 = createRole2();
            RoleUpdateReqVO updateReqVO = new RoleUpdateReqVO();
            updateReqVO.setId(role.getId());
            updateReqVO.setName("测试角色111");
            updateReqVO.setSort(77);
            updateReqVO.setCode(role2.getCode());
            updateReqVO.setRemark("备注");
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(updateReqVO);
            mvc.perform(put("/admin/role/update")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002002002))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("已经存在编码为【" + updateReqVO.getCode() + "】的角色"));
        }

        // sad path: 不能操作内置角色
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:role:update"})
        public void updateRole_failed_cannotOperateBuiltInRole() throws Exception {
            SysRolePO role = createInnerRole();
            RoleUpdateReqVO updateReqVO = new RoleUpdateReqVO();
            updateReqVO.setId(role.getId());
            updateReqVO.setName("测试角色111");
            updateReqVO.setSort(77);
            updateReqVO.setCode("test_role");
            updateReqVO.setRemark("备注");
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(updateReqVO);
            mvc.perform(put("/admin/role/update")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002002003))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("不能操作类型为系统内置的角色"));
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/27
     * @description 更新角色状态相关测试
     */
    @Nested
    class UpdateRoleStatusTests {
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:role:update"})
        public void updateRoleStatus_success() throws Exception {
            SysRolePO role = createRole();
            RoleUpdateStatusReqVO updateStatusReqVO = new RoleUpdateStatusReqVO();
            updateStatusReqVO.setId(role.getId());
            updateStatusReqVO.setStatus(CommonStatusEnum.DISABLE.getStatus());
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(updateStatusReqVO);
            mvc.perform(put("/admin/role/update-status")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").value(true));
            SysRolePO dbRole = roleMapper.selectById(role.getId());
            assertEquals(updateStatusReqVO.getStatus(), dbRole.getStatus());
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/27
     * @description 删除角色相关测试
     */
    @Nested
    class DeleteRoleTests {
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:role:delete"})

        public void deleteRole_success() throws Exception {
            SysRolePO role = createRole();
            Set<String> roleIds = new HashSet<String>() {{
                add(role.getId());
            }};
            mvc.perform(delete("/admin/role")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(roleIds.toString())
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").value(true));
            SysRolePO dbRolePO = roleMapper.selectById(role.getId());
            assertEquals(null, dbRolePO);
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/27
     * @description 通过ID获取角色相关测试
     */
    @Nested
    class GetRoleByIdTests {
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:role:query"})
        public void getRoleById_success() throws Exception {
            SysRolePO role = createRole();
            mvc.perform(get("/admin/role/" + role.getId()))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.data.name").value(role.getName()))
                    .andExpect(jsonPath("$.data.sort").value(role.getSort()))
                    .andExpect(jsonPath("$.data.code").value(role.getCode()))
                    .andExpect(jsonPath("$.data.remark").value(role.getRemark()))
                    .andExpect(jsonPath("$.data.status").value(role.getStatus()))
                    .andExpect(jsonPath("$.data.type").value(role.getType()));
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/27
     * @description 获取角色分页相关测试
     */
    @Nested
    class GetRolePageTests {
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:role:query"})
        public void getRolePage_success() throws Exception {
            SysRolePO role = createRole();
            mvc.perform(get("/admin/role/page"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data.total").value(1))
                    .andExpect(jsonPath("$.data.list").isArray())
                    .andExpect(jsonPath("$.data.list").isNotEmpty())
                    .andExpect(jsonPath("$.data.list[0].name").value(role.getName()))
                    .andExpect(jsonPath("$.data.list[0].sort").value(role.getSort()))
                    .andExpect(jsonPath("$.data.list[0].code").value(role.getCode()))
                    .andExpect(jsonPath("$.data.list[0].remark").value(role.getRemark()))
                    .andExpect(jsonPath("$.data.list[0].status").value(role.getStatus()))
                    .andExpect(jsonPath("$.data.list[0].type").value(role.getType()));
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/27
     * @description 获取角色简单列表相关测试
     */
    @Nested
    class GetRoleSimpleListTests {
        @Test
        @WithMockUser()
        public void getRoleListAllSimple_success() throws Exception {
            SysRolePO role = createRole();
            mvc.perform(get("/admin/role/list-all-simple"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").isArray())
                    .andExpect(jsonPath("$.data").isNotEmpty())
                    .andExpect(jsonPath("$.data[0].id").value(role.getId()))
                    .andExpect(jsonPath("$.data[0].name").value(role.getName()));
        }
    }

    private SysRolePO createRole() {
        SysRolePO role = new SysRolePO();
        role.setName("测试角色");
        role.setSort(3);
        role.setCode("test_role");
        role.setRemark("备注");
        role.setStatus(CommonStatusEnum.ENABLE.getStatus());
        role.setType(2);
        roleMapper.insert(role);
        return role;
    }

    private SysRolePO createRole2() {
        SysRolePO role = new SysRolePO();
        role.setName("测试角色22");
        role.setSort(33);
        role.setCode("test_role2");
        role.setRemark("备注2");
        role.setStatus(CommonStatusEnum.ENABLE.getStatus());
        role.setType(2);
        roleMapper.insert(role);
        return role;
    }

    private SysRolePO createInnerRole() {
        SysRolePO role = new SysRolePO();
        role.setName("测试角色0");
        role.setSort(0);
        role.setCode("test_role0");
        role.setRemark("备注0");
        role.setStatus(CommonStatusEnum.ENABLE.getStatus());
        role.setType(1);
        roleMapper.insert(role);
        return role;
    }
}
