package com.rc.cloud.app.operate.appearance.facade.admin;

import com.rc.cloud.app.operate.appearance.admin.v1.req.BrandCreateRequest;
import com.rc.cloud.app.operate.appearance.admin.v1.req.BrandUpdateRequest;
import com.rc.cloud.app.operate.core.AbstractWebApplicationTest;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.BrandMapper;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.BrandPO;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import com.rc.cloud.common.test.annotation.RcTest;
import com.rc.cloud.common.test.core.util.RandomUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import javax.annotation.Resource;
import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RcTest
public class BrandControllerIntegratedTest extends AbstractWebApplicationTest {
    @Resource
    private IdRepository idRepository;
    @Resource
    private BrandMapper brandMapper;
    private BrandCreateRequest brandCreateRequest;
    private BrandUpdateRequest brandUpdateRequest;

    @Override
    protected void initFixture() {
        initBrandCreateRequest();
        initBrandUpdateRequest();
    }



    @DisplayName(value = "创建品牌")
    @Test
    public void create() throws Exception {
        String requestBody = "{\n" +
                "  \"name\" : \"" + brandCreateRequest.getName() + "\",\n" +
                "  \"logo\" : \"" + brandCreateRequest.getLogo() + "\",\n" +
                "  \"type\" : \"" + brandCreateRequest.getType() + "\",\n" +
                "  \"enabled\" : " + brandCreateRequest.getEnabled() + ",\n" +
                "  \"sort\" : " + brandCreateRequest.getSort().intValue() + "\n" +
                "}";
        mvc.perform(post("/admin/brand/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.name").value(brandCreateRequest.getName()))
                .andExpect(jsonPath("$.data.logo").value(brandCreateRequest.getLogo()))
                .andExpect(jsonPath("$.data.type").value(brandCreateRequest.getType()))
                .andExpect(jsonPath("$.data.sort").value(brandCreateRequest.getSort()))
                .andExpect(jsonPath("$.data.enabled").value(brandCreateRequest.getEnabled()));
    }

    @DisplayName(value = "更新品牌")
    @Test
    public void update() throws Exception {
        BrandPO po = saveBrandPO();
        String requestBody = "{\n" +
                "  \"id\" : \"" + po.getId() + "\",\n" +
                "  \"name\" : \"" + brandUpdateRequest.getName() + "\",\n" +
                "  \"logo\" : \"" + brandUpdateRequest.getLogo() + "\",\n" +
                "  \"type\" : \"" + brandUpdateRequest.getType() + "\",\n" +
                "  \"enabled\" : " + brandUpdateRequest.getEnabled() + ",\n" +
                "  \"sort\" : " + brandUpdateRequest.getSort().intValue() + "\n" +
                "}";
        mvc.perform(put("/admin/brand/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(po.getId()))
                .andExpect(jsonPath("$.data.name").value(brandUpdateRequest.getName()))
                .andExpect(jsonPath("$.data.logo").value(brandUpdateRequest.getLogo()))
                .andExpect(jsonPath("$.data.type").value(brandUpdateRequest.getType()))
                .andExpect(jsonPath("$.data.sort").value(brandUpdateRequest.getSort()))
                .andExpect(jsonPath("$.data.enabled").value(brandUpdateRequest.getEnabled()));
    }


    @DisplayName(value = "删除品牌")
    @Test
    public void remove() throws Exception {
        BrandPO po = saveBrandPO();
        mvc.perform(delete("/admin/brand/remove").param("id", po.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true));
    }


    @DisplayName(value = "品牌分页查询")
    @Test
    public void selectPageResult() throws Exception {
        //数据准备
        brandMapper.delete(new LambdaQueryWrapperX<BrandPO>());
        int dataSize = 15;
        for (int i = 0; i < dataSize; i++) {
            saveBrandPO();
        }
        //执行、验证
        mvc.perform(get("/admin/brand/selectPageResult")
                        .param("pageNo", "1")
                        .param("pageSize", "10")
                        .param("name", brandCreateRequest.getName())
                        .characterEncoding(Charset.defaultCharset())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.total").value(dataSize))
                .andExpect(jsonPath("$.data.list").isArray())
                .andExpect(jsonPath("$.data.list[0].id").isNotEmpty())
                .andExpect(jsonPath("$.data.list[0].name").value(brandCreateRequest.getName()))
                .andExpect(jsonPath("$.data.list[0].logo").value(brandCreateRequest.getLogo()))
                .andExpect(jsonPath("$.data.list[0].type").value(brandCreateRequest.getType()))
                .andExpect(jsonPath("$.data.list[0].sort").value(brandCreateRequest.getSort()))
                .andExpect(jsonPath("$.data.list[0].enabled").value(brandCreateRequest.getEnabled()));
    }

    @DisplayName(value = "根据唯一标识查找品牌")
    @Test
    public void findById() throws Exception {
        BrandPO po = saveBrandPO();
        mvc.perform(get("/admin/brand/findById")
                        .param("id", po.getId())
                        .characterEncoding(Charset.defaultCharset())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(po.getId()))
                .andExpect(jsonPath("$.data.name").value(brandCreateRequest.getName()))
                .andExpect(jsonPath("$.data.logo").value(brandCreateRequest.getLogo()))
                .andExpect(jsonPath("$.data.type").value(brandCreateRequest.getType()))
                .andExpect(jsonPath("$.data.sort").value(brandCreateRequest.getSort()))
                .andExpect(jsonPath("$.data.enabled").value(brandCreateRequest.getEnabled()));
    }

    @DisplayName(value = "品牌列表查询")
    @Test
    public void findList() throws Exception {
        //数据准备
        brandMapper.delete(new LambdaQueryWrapperX<BrandPO>());
        int dataSize = 15;
        for (int i = 0; i < dataSize; i++) {
            saveBrandPO();
        }
        //执行验证
        mvc.perform(get("/admin/brand/findList")
                        .param("name", "振信")
                        .characterEncoding(Charset.defaultCharset())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].id").isNotEmpty())
                .andExpect(jsonPath("$.data[0].name").value(brandCreateRequest.getName()))
                .andExpect(jsonPath("$.data[0].logo").value(brandCreateRequest.getLogo()))
                .andExpect(jsonPath("$.data[0].type").value(brandCreateRequest.getType()))
                .andExpect(jsonPath("$.data[0].sort").value(brandCreateRequest.getSort()))
                .andExpect(jsonPath("$.data[0].enabled").value(brandCreateRequest.getEnabled()));
    }

    private BrandPO saveBrandPO() {
        BrandPO po = new BrandPO();
        po.setId(idRepository.nextId());
        po.setName(brandCreateRequest.getName());
        po.setLogo(brandCreateRequest.getLogo());
        po.setSort(brandCreateRequest.getSort());
        po.setType(brandCreateRequest.getType());
        po.setEnabled(brandCreateRequest.getEnabled());
        brandMapper.insert(po);
        return po;
    }

    private void initBrandCreateRequest() {
        brandCreateRequest = RandomUtils.randomPojo(BrandCreateRequest.class, o -> {
            o.setName("振信");
            o.setLogo("http://zx.zjffcat.com/storage/uploads/20210713/08885d526a7eeb318aae72d710ef5ea0.jpg");
            o.setEnabled(true);
            o.setType("自有");
            o.setSort(99);
        });
    }

    private void initBrandUpdateRequest() {
        brandUpdateRequest = RandomUtils.randomPojo(BrandUpdateRequest.class, o -> {
            o.setName("振国");
            o.setLogo("http://zx.zjffcat.com/storage/uploads/20210713/08885d526a7eeb318aae72d710ef5ea0123.jpg");
            o.setEnabled(false);
            o.setType("公有");
            o.setSort(1);
        });
    }
}
