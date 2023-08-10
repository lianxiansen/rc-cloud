package com.rc.cloud.app.operate.appearance.facade.admin;

import com.rc.cloud.app.operate.application.dto.ProductGroupCreateDTO;
import com.rc.cloud.app.operate.core.AbstractWebApplicationTest;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Name;
import com.rc.cloud.app.operate.domain.model.product.valobj.Url;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroup;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.convert.ProductGroupConvert;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.ProductGroupMapper;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductGroupPO;
import com.rc.cloud.app.operate.infrastructure.util.RandomUtils;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.test.annotation.RcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import javax.annotation.Resource;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @ClassName: ProductGroupAcceptanceTest
 * @Author: liandy
 * @Date: 2023/7/17 07:57
 * 1.创建产品组合
 * 2.解除产品组合
 * 3.添加组合项
 */
@RcTest
public class ProductGroupControllerIntegratedTest extends AbstractWebApplicationTest {

    @MockBean
    private ProductRepository productRepositoryStub;
    @Resource
    private IdRepository idRepository;
    @Autowired
    private ProductGroupMapper productGroupMapper;
    private Product productMock;

    private ProductGroup productGroupMock;


    private ProductGroupCreateDTO productGroupCreateDTO;

    @Override
    protected void initFixture() {
        productMock = mockProduct();
        productGroupCreateDTO = createProductGroupCreateDTO();
        productGroupMock = mockProductGroup();
    }



    @DisplayName(value = "创建产品组合")
    @Test
    public void create() throws Exception {
        String requestBody = "{\n" +
                "  \"productId\": \"" + productGroupCreateDTO.getProductId() + "\",\n" +
                "  \"name\": \"" + productGroupCreateDTO.getName() + "\"\n" +
                "}";
        mvc.perform(post("/admin/productGroup/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.productId").value(productGroupCreateDTO.getProductId()))
                .andExpect(jsonPath("$.data.name").value(productGroupCreateDTO.getName()))
                .andExpect(jsonPath("$.data.description").value("共0款产品"));
    }


    @DisplayName(value = "解除产品组合")
    @Test
    public void release() throws Exception {
        mvc.perform(delete("/admin/productGroup/release").param("id", productGroupMock.getId().id()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isBoolean());
    }

    @DisplayName(value = "添加组合项")
    @Test
    public void createItem() throws Exception {
        String requestBody = "{\n" +
                "  \"productGroupId\": \""+ productGroupMock.getId().id()+"\",\n" +
                "  \"productId\": \""+productMock.getId().id()+"\"\n" +
                "}";
        mvc.perform(post("/admin/productGroup/createItem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.productGroupId").value(productGroupMock.getId().id()))
                .andExpect(jsonPath("$.data.productId").value(productGroupCreateDTO.getProductId()))
                .andExpect(jsonPath("$.data.productName").value(productMock.getName().getValue()))
                .andExpect(jsonPath("$.data.productImage").value(productMock.getProductListImage().getValue()));
    }

    @DisplayName(value = "获取产品组合列表")
    @Test
    public void selectList() throws Exception {
        mvc.perform(get("/admin/productGroup/findList").param("productId", productMock.getId().id()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    private Product mockProduct() {
        Product productMock = new Product(new ProductId(idRepository.nextId()), new Name(RandomUtils.randomString()));
        productMock.setName(new Name("卡通粘钩"));
        productMock.setProductListImage(new Url("http://www.576zx.com/storage/uploads/20220422/ddcf0171bb0edd9087cc4d6bc2788330.jpg"));
        when(productRepositoryStub.findById(productMock.getId())).thenReturn(productMock);
        return productMock;
    }

    private ProductGroupCreateDTO createProductGroupCreateDTO() {
        ProductGroupCreateDTO productGroupCreateDTO = new ProductGroupCreateDTO();
        productGroupCreateDTO.setName(RandomUtils.randomString());
        productGroupCreateDTO.setProductId(productMock.getId().id());
        return productGroupCreateDTO;
    }

    private ProductGroup mockProductGroup() {
        ProductGroupPO po = new ProductGroupPO();
        po.setId(idRepository.nextId());
        po.setName(productGroupCreateDTO.getName());
        po.setProductId(productGroupCreateDTO.getProductId());
        productGroupMapper.insert(po);
        return ProductGroupConvert.convert2ProductGroup(po);
    }
}
