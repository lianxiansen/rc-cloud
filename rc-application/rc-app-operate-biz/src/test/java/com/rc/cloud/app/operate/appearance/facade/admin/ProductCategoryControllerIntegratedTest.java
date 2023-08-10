package com.rc.cloud.app.operate.appearance.facade.admin;

import com.rc.cloud.app.operate.appearance.admin.v1.req.ProductCategoryCreateRequest;
import com.rc.cloud.app.operate.appearance.admin.v1.req.ProductCategoryUpdateRequest;
import com.rc.cloud.app.operate.core.AbstractWebApplicationTest;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.ProductCategoryMapper;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductCategoryPO;
import com.rc.cloud.app.operate.infrastructure.util.RandomUtils;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import com.rc.cloud.common.test.annotation.RcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @ClassName: ProductCategoryControllerIntegratedTest
 * @Author: liandy
 * @Date: 2023/7/5 11:03
 * @Description: TODO
 */
@RcTest
public class ProductCategoryControllerIntegratedTest extends AbstractWebApplicationTest {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Autowired
    private IdRepository idRepository;

    private ProductCategoryCreateRequest createRequest;

    private ProductCategoryUpdateRequest updateRequest;

    private ProductCategoryPO rootProductCategoryPO;

    @Override
    protected void initFixture() {
        initCreateRequest();
        initUpdateRequest();
        saveRootProductCategoryPO();
    }


    @Test
    @DisplayName(value = "创建产品分类")
    public void create() throws Exception {
        String requestBody = "{\n" +
                "  \"name\" : \"" + createRequest.getName() + "\",\n" +
                "  \"englishName\" : \"" + createRequest.getEnglishName() + "\",\n" +
                "  \"icon\" : \"" + createRequest.getIcon() + "\",\n" +
                "  \"productCategoryPageImage\" : \"" + createRequest.getProductCategoryPageImage() + "\",\n" +
                "  \"productListPageImage\" : \"" + createRequest.getProductListPageImage() + "\",\n" +
                "  \"parentId\" : \"" + rootProductCategoryPO.getId() + "\",\n" +
                "  \"enabled\" : " + createRequest.getEnabled() + ",\n" +
                "  \"sort\" : " + createRequest.getSort() + "\n" +
                "}";
        mvc.perform(post("/admin/productCategory/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.name").value(createRequest.getName()))
                .andExpect(jsonPath("$.data.englishName").value(createRequest.getEnglishName()))
                .andExpect(jsonPath("$.data.icon").value(createRequest.getIcon()))
                .andExpect(jsonPath("$.data.productCategoryPageImage").value(createRequest.getProductCategoryPageImage()))
                .andExpect(jsonPath("$.data.productListPageImage").value(createRequest.getProductListPageImage()))
                .andExpect(jsonPath("$.data.parentId").value(rootProductCategoryPO.getId()))
                .andExpect(jsonPath("$.data.enabled").value(createRequest.getEnabled()))
                .andExpect(jsonPath("$.data.sort").value(createRequest.getSort()));
    }


    @Test
    @DisplayName(value = "更新产品分类")
    public void update() throws Exception {
        ProductCategoryPO po = saveChildProductCategoryPO();
        String requestBody = "{\n" +
                "  \"id\" : \"" + po.getId() + "\",\n" +
                "  \"name\" : \"" + updateRequest.getName() + "\",\n" +
                "  \"englishName\" : \"" + updateRequest.getEnglishName() + "\",\n" +
                "  \"icon\" : \"" + updateRequest.getIcon() + "\",\n" +
                "  \"productCategoryPageImage\" : \"" + updateRequest.getProductCategoryPageImage() + "\",\n" +
                "  \"productListPageImage\" : \"" + updateRequest.getProductListPageImage() + "\",\n" +
                "  \"parentId\" : \"" + rootProductCategoryPO.getId() + "\",\n" +
                "  \"enabled\" : " + updateRequest.getEnabled() + ",\n" +
                "  \"sort\" : " + updateRequest.getSort() + "\n" +
                "}";
        mvc.perform(put("/admin/productCategory/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .content(requestBody).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.name").value(updateRequest.getName()))
                .andExpect(jsonPath("$.data.englishName").value(updateRequest.getEnglishName()))
                .andExpect(jsonPath("$.data.icon").value(updateRequest.getIcon()))
                .andExpect(jsonPath("$.data.productCategoryPageImage").value(updateRequest.getProductCategoryPageImage()))
                .andExpect(jsonPath("$.data.productListPageImage").value(updateRequest.getProductListPageImage()))
                .andExpect(jsonPath("$.data.parentId").value(rootProductCategoryPO.getId()))
                .andExpect(jsonPath("$.data.enabled").value(updateRequest.getEnabled()))
                .andExpect(jsonPath("$.data.sort").value(updateRequest.getSort()));
    }


    @DisplayName(value = "删除产品分类")
    @Test
    public void remove() throws Exception {
        ProductCategoryPO po = saveChildProductCategoryPO();
        mvc.perform(delete("/admin/productCategory/remove").param("id", po.getId()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true));
    }


    @DisplayName(value = "产品分类列表")
    @Test
    public void findAll() throws Exception {
        productCategoryMapper.delete(new LambdaQueryWrapperX<>());
        int dataSize = 15;
        for (int i = 0; i < dataSize; i++) {
            saveChildProductCategoryPO();
        }
        mvc.perform(get("/admin/productCategory/findAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].id").isNotEmpty())
                .andExpect(jsonPath("$.data[0].name").value(createRequest.getName()))
                .andExpect(jsonPath("$.data[0].englishName").value(createRequest.getEnglishName()))
                .andExpect(jsonPath("$.data[0].icon").value(createRequest.getIcon()))
                .andExpect(jsonPath("$.data[0].productCategoryPageImage").value(createRequest.getProductCategoryPageImage()))
                .andExpect(jsonPath("$.data[0].productListPageImage").value(createRequest.getProductListPageImage()))
                .andExpect(jsonPath("$.data[0].enabled").value(createRequest.getEnabled()))
                .andExpect(jsonPath("$.data[0].sort").value(createRequest.getSort()));
    }

    @DisplayName(value = "产品分类树形列表")
    @Test
    public void findTreeList() throws Exception {
        mvc.perform(get("/admin/productCategory/findTreeList")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray());
    }

    @DisplayName(value = "根据唯一标识查找产品分类")
    @Test
    public void findById() throws Exception {
        ProductCategoryPO po = saveChildProductCategoryPO();
        mvc.perform(get("/admin/productCategory/findById").param("id", po.getId())
                        .characterEncoding(Charset.defaultCharset())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.name").value(createRequest.getName()))
                .andExpect(jsonPath("$.data.englishName").value(createRequest.getEnglishName()))
                .andExpect(jsonPath("$.data.icon").value(createRequest.getIcon()))
                .andExpect(jsonPath("$.data.productCategoryPageImage").value(createRequest.getProductCategoryPageImage()))
                .andExpect(jsonPath("$.data.productListPageImage").value(createRequest.getProductListPageImage()))
                .andExpect(jsonPath("$.data.parentId").value(rootProductCategoryPO.getId()))
                .andExpect(jsonPath("$.data.enabled").value(createRequest.getEnabled()))
                .andExpect(jsonPath("$.data.sort").value(createRequest.getSort()));
    }

    private void initCreateRequest() {
        createRequest = RandomUtils.randomPojo(ProductCategoryCreateRequest.class, o -> {
            o.setName("卡通风");
            o.setEnglishName("KATONG");
            o.setProductCategoryPageImage("http://www.576zx.com/2b454a34bb5934ea82e5602ef14006e1.jpg");
            o.setEnabled(true);
            o.setSort(99);
            o.setIcon("http://www.576zx.com/2b454a34bb5934ea82e5602ef14006e2.jpg");
            o.setProductListPageImage("http://www.576zx.com/2b454a34bb5934ea82e5602ef14006e3.jpg");
        });
    }

    private void initUpdateRequest() {
        updateRequest = RandomUtils.randomPojo(ProductCategoryUpdateRequest.class, o -> {
            o.setName("极简风");
            o.setEnglishName("JIJIAN");
            o.setProductCategoryPageImage("http://www.576zx.com/2b454a34bb5934ea82e5602ef14006e4.jpg");
            o.setEnabled(true);
            o.setSort(88);
            o.setIcon("http://www.576zx.com/2b454a34bb5934ea82e5602ef14006e5.jpg");
            o.setProductListPageImage("http://www.576zx.com/2b454a34bb5934ea82e5602ef14006e6.jpg");
        });
    }

    private void saveRootProductCategoryPO() {
        rootProductCategoryPO = new ProductCategoryPO();
        rootProductCategoryPO.setId(idRepository.nextId());
        rootProductCategoryPO.setName(createRequest.getName());
        rootProductCategoryPO.setEnglishName(createRequest.getEnglishName());
        rootProductCategoryPO.setIcon(createRequest.getIcon());
        rootProductCategoryPO.setEnabled(createRequest.getEnabled());
        rootProductCategoryPO.setProductCategoryPageImage(createRequest.getProductCategoryPageImage());
        rootProductCategoryPO.setProductListPageImage(createRequest.getProductListPageImage());
        rootProductCategoryPO.setSort(createRequest.getSort());
        rootProductCategoryPO.setLayer(1);
        productCategoryMapper.insert(rootProductCategoryPO);
    }

    private ProductCategoryPO saveChildProductCategoryPO() {
        ProductCategoryPO po = new ProductCategoryPO();
        po.setId(idRepository.nextId());
        po.setName(createRequest.getName());
        po.setEnglishName(createRequest.getEnglishName());
        po.setIcon(createRequest.getIcon());
        po.setEnabled(createRequest.getEnabled());
        po.setProductCategoryPageImage(createRequest.getProductCategoryPageImage());
        po.setProductListPageImage(createRequest.getProductListPageImage());
        po.setSort(createRequest.getSort());
        po.setParentId(rootProductCategoryPO.getId());
        po.setLayer(2);
        productCategoryMapper.insert(po);
        return po;
    }
}
