package com.rc.cloud.app.system.contorller.admin.dept;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.system.controller.admin.v1.dept.PostController;
import com.rc.cloud.app.system.mapper.dept.PostMapper;
import com.rc.cloud.app.system.model.dept.SysPostPO;
import com.rc.cloud.app.system.vo.dept.post.PostCreateReqVO;
import com.rc.cloud.app.system.vo.dept.post.PostUpdateReqVO;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author rc@hqf
 * @date 2023/07/23
 * @description 关联 {@link PostController} 类
 */
@RcTest
public class PostControllerTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Resource
    private PostMapper postMapper;

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
     * @date 2023/07/24
     * @description 创建岗位相关测试
     */
    @Nested
    class CreatePostTests {
        // happy path1: 创建岗位成功
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:post:create"})
        public void createPost_success() throws Exception {
            PostCreateReqVO postCreateReqVO = new PostCreateReqVO();
            postCreateReqVO.setName("测试职位");
            postCreateReqVO.setCode("cszw");
            postCreateReqVO.setSort(99);
            postCreateReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
            postCreateReqVO.setRemark("备注信息");
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(postCreateReqVO);
            mvc.perform(post("/admin/post/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").isNotEmpty());
            SysPostPO dbPostPO = postMapper.selectByName(postCreateReqVO.getName());
            assertNotEquals(null, dbPostPO);
            assertEquals(postCreateReqVO.getName(), dbPostPO.getName());
            assertEquals(postCreateReqVO.getCode(), dbPostPO.getCode());
            assertEquals(postCreateReqVO.getSort(), dbPostPO.getSort());
            assertEquals(postCreateReqVO.getStatus(), dbPostPO.getStatus());
            assertEquals(postCreateReqVO.getRemark(), dbPostPO.getRemark());
        }

        // sad path1: 创建岗位失败，岗位名称为空
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:post:create"})
        public void createPost_failed_when_nameIsNull() throws Exception {
            PostCreateReqVO postCreateReqVO = new PostCreateReqVO();
            postCreateReqVO.setName("");
            postCreateReqVO.setCode("cszw");
            postCreateReqVO.setSort(99);
            postCreateReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
            postCreateReqVO.setRemark("备注信息");
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(postCreateReqVO);
            mvc.perform(post("/admin/post/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(10030))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("请求参数不正确:岗位名称不能为空"));
        }

        // sad path2: 创建岗位失败，岗位code为空
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:post:create"})
        public void createPost_failed_when_codeIsNull() throws Exception {
            PostCreateReqVO postCreateReqVO = new PostCreateReqVO();
            postCreateReqVO.setName("测试职位");
            postCreateReqVO.setCode("");
            postCreateReqVO.setSort(99);
            postCreateReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
            postCreateReqVO.setRemark("备注信息");
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(postCreateReqVO);
            mvc.perform(post("/admin/post/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(10030))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("请求参数不正确:岗位编码不能为空"));
        }

        // sad path3: 创建岗位失败，岗位名称已存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:post:create"})
        public void createPost_failed_when_nameIsExist() throws Exception {
            SysPostPO postPO = createPost1();
            PostCreateReqVO postCreateReqVO = new PostCreateReqVO();
            postCreateReqVO.setName(postPO.getName());
            postCreateReqVO.setCode("cszw");
            postCreateReqVO.setSort(99);
            postCreateReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
            postCreateReqVO.setRemark("备注信息");
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(postCreateReqVO);
            mvc.perform(post("/admin/post/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002005002))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("已经存在该名字的岗位"));
        }

        // sad path4: 创建岗位失败，岗位编码已存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:post:create"})
        public void createPost_failed_when_codeIsExist() throws Exception {
            SysPostPO postPO = createPost1();
            PostCreateReqVO postCreateReqVO = new PostCreateReqVO();
            postCreateReqVO.setName("测试职位");
            postCreateReqVO.setCode(postPO.getCode());
            postCreateReqVO.setSort(99);
            postCreateReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
            postCreateReqVO.setRemark("备注信息");
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(postCreateReqVO);
            mvc.perform(post("/admin/post/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002005003))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("已经存在该标识的岗位"));
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/25
     * @description 分页获取岗位相关测试
     */
    @Nested
    class GetPostPageTests {
        // happy path1: 分页获取岗位成功
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:post:query"})
        public void getPostPage_success() throws Exception {
            SysPostPO postPO = createPost1();
            mvc.perform(get("/admin/post/page"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data.total").value(1))
                    .andExpect(jsonPath("$.data.list").isArray())
                    .andExpect(jsonPath("$.data.list").isNotEmpty())
                    .andExpect(jsonPath("$.data.list[0].id").value(postPO.getId()))
                    .andExpect(jsonPath("$.data.list[0].name").value(postPO.getName()))
                    .andExpect(jsonPath("$.data.list[0].code").value(postPO.getCode()))
                    .andExpect(jsonPath("$.data.list[0].sort").value(postPO.getSort()))
                    .andExpect(jsonPath("$.data.list[0].status").value(postPO.getStatus()))
                    .andExpect(jsonPath("$.data.list[0].remark").value(postPO.getRemark()));
        }
    }


    /**
     * @author rc@hqf
     * @date 2023/07/25
     * @description 获取岗位精简列表相关测试
     */
    @Nested
    class GetPostSimpleListTests{
        // happy path1: 获取岗位精简列表成功
        @Test
        @WithMockUser(username = "admin")
        public void getPostListAllSimple_success() throws Exception {
            SysPostPO postPO = createPost1();
            mvc.perform(get("/admin/post/list-all-simple"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").isArray())
                    .andExpect(jsonPath("$.data").isNotEmpty())
                    .andExpect(jsonPath("$.data[0].id").value(postPO.getId()))
                    .andExpect(jsonPath("$.data[0].name").value(postPO.getName()));
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/25
     * @description 通过ID获取岗位相关测试
     */
    @Nested
    class GetPostByIDTests{

        // happy path1: 通过ID获取岗位成功
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:post:query"})
        public void getPostByIdExist_then_success() throws Exception {
            SysPostPO postPO = createPost1();
            mvc.perform(get("/admin/post/" + postPO.getId()))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data.id").value(postPO.getId()))
                    .andExpect(jsonPath("$.data.name").value(postPO.getName()))
                    .andExpect(jsonPath("$.data.code").value(postPO.getCode()))
                    .andExpect(jsonPath("$.data.sort").value(postPO.getSort()))
                    .andExpect(jsonPath("$.data.status").value(postPO.getStatus()))
                    .andExpect(jsonPath("$.data.remark").value(postPO.getRemark()));
        }

        // sad path1: 通过ID获取岗位失败，岗位不存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:post:query"})
        public void getPostByIdNotExist_then_throwNotFoundException() throws Exception {
            mvc.perform(get("/admin/post/9999999"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002005000))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("当前岗位不存在"));
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/25
     * @description 更新岗位相关测试
     */
    @Nested
    class UpdatePostTests{
        // happy path1: 更新岗位成功
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:post:update"})
        public void updatePost_success() throws Exception {
            SysPostPO postPO = createPost1();
            PostUpdateReqVO postUpdateReqVO = new PostUpdateReqVO();
            postUpdateReqVO.setId(postPO.getId());
            postUpdateReqVO.setName("前端2");
            postUpdateReqVO.setCode("front2");
            postUpdateReqVO.setStatus(CommonStatusEnum.DISABLE.getStatus());
            postUpdateReqVO.setSort(3);
            postUpdateReqVO.setRemark("备注信息");
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(postUpdateReqVO);
            mvc.perform(put("/admin/post/update")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").value(true));
        }
        // sad path1: 更新岗位失败，岗位不存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:post:update"})
        public void updatePost_failed_when_postNotExist() throws Exception {
            PostUpdateReqVO postUpdateReqVO = new PostUpdateReqVO();
            postUpdateReqVO.setId("9999999");
            postUpdateReqVO.setName("前端2");
            postUpdateReqVO.setCode("front2");
            postUpdateReqVO.setStatus(CommonStatusEnum.DISABLE.getStatus());
            postUpdateReqVO.setSort(3);
            postUpdateReqVO.setRemark("备注信息");
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(postUpdateReqVO);
            mvc.perform(put("/admin/post/update")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002005000))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("当前岗位不存在"));
        }

        // sad path2: 更新岗位失败，岗位名称为空
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:post:update"})
        public void updatePost_failed_when_nameIsNull() throws Exception {
            SysPostPO postPO = createPost1();
            PostUpdateReqVO postUpdateReqVO = new PostUpdateReqVO();
            postUpdateReqVO.setId(postPO.getId());
            postUpdateReqVO.setName("");
            postUpdateReqVO.setCode("front2");
            postUpdateReqVO.setStatus(CommonStatusEnum.DISABLE.getStatus());
            postUpdateReqVO.setSort(3);
            postUpdateReqVO.setRemark("备注信息");
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(postUpdateReqVO);
            mvc.perform(put("/admin/post/update")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(10030))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("请求参数不正确:岗位名称不能为空"));
        }

        // sad path3: 更新岗位失败，岗位code为空
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:post:update"})
        public void updatePost_failed_when_codeIsNull() throws Exception {
            SysPostPO postPO = createPost1();
            PostUpdateReqVO postUpdateReqVO = new PostUpdateReqVO();
            postUpdateReqVO.setId(postPO.getId());
            postUpdateReqVO.setName("前端2");
            postUpdateReqVO.setCode("");
            postUpdateReqVO.setStatus(CommonStatusEnum.DISABLE.getStatus());
            postUpdateReqVO.setSort(3);
            postUpdateReqVO.setRemark("备注信息");
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(postUpdateReqVO);
            mvc.perform(put("/admin/post/update")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(10030))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("请求参数不正确:岗位编码不能为空"));
        }

        // sad path4: 更新岗位失败，岗位名称已存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:post:update"})
        public void updatePost_failed_when_nameIsExist() throws Exception {
            SysPostPO postPO1 = createPost1();
            SysPostPO postPO2 = createPost2();
            PostUpdateReqVO postUpdateReqVO = new PostUpdateReqVO();
            postUpdateReqVO.setId(postPO2.getId());
            postUpdateReqVO.setName(postPO1.getName());
            postUpdateReqVO.setCode("front2");
            postUpdateReqVO.setStatus(CommonStatusEnum.DISABLE.getStatus());
            postUpdateReqVO.setSort(3);
            postUpdateReqVO.setRemark("备注信息");
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(postUpdateReqVO);
            mvc.perform(put("/admin/post/update")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002005002))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("已经存在该名字的岗位"));
        }

        // sad path5: 更新岗位失败，岗位编码已存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:post:update"})
        public void updatePost_failed_when_codeIsExist() throws Exception {
            SysPostPO postPO1 = createPost1();
            SysPostPO postPO2 = createPost2();
            PostUpdateReqVO postUpdateReqVO = new PostUpdateReqVO();
            postUpdateReqVO.setId(postPO2.getId());
            postUpdateReqVO.setName("前端2");
            postUpdateReqVO.setCode(postPO1.getCode());
            postUpdateReqVO.setStatus(CommonStatusEnum.DISABLE.getStatus());
            postUpdateReqVO.setSort(3);
            postUpdateReqVO.setRemark("备注信息");
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(postUpdateReqVO);
            mvc.perform(put("/admin/post/update")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002005003))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("已经存在该标识的岗位"));
        }
    }


    /**
     * @author rc@hqf
     * @date 2023/07/25
     * @description 批量删除岗位相关测试
     */
    @Nested
    class DeletePostTests {
        // 根据ID删除
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:post:delete"})
        public void deletePostById_success() throws Exception {
            List<String> ids = new ArrayList<>();
            ids.add(createPost1().getId());
            ids.add(createPost2().getId());
            mvc.perform(delete("/admin/post")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(ids.toString())
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").value(true));
        }
    }


    private SysPostPO createPost1() throws Exception {
        SysPostPO postPO = new SysPostPO();
        postPO.setName("测试岗位1");
        postPO.setCode("test_post1");
        postPO.setSort(1);
        postPO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        postPO.setRemark("备注信息11");
        postMapper.insert(postPO);
        return postPO;
    }

    private SysPostPO createPost2() throws Exception {
        SysPostPO postPO = new SysPostPO();
        postPO.setName("测试岗位2");
        postPO.setCode("test_post2");
        postPO.setSort(2);
        postPO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        postPO.setRemark("备注信息22");
        postMapper.insert(postPO);
        return postPO;
    }
}
