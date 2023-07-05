package com.rc.cloud.app.operate.appearance.facade.admin;

import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.operate.application.dto.ProductCategoryCreateDTO;
import com.rc.cloud.app.operate.application.service.ProductCategoryApplicationService;
import com.rc.cloud.common.test.annotation.RcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @ClassName: ProductCategoryControllerTest
 * @Author: liandy
 * @Date: 2023/7/5 11:03
 * @Description: TODO
 */
@RcTest
public class ProductCategoryControllerTest {
    @Autowired
    private WebApplicationContext context;

    private
    MockMvc mvc;
    @Autowired
    private ProductCategoryApplicationService productCategoryApplicationService;

    @MockBean
    private RemoteIdGeneratorService remoteIdGeneratorService;
    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
//                .apply(springSecurity())
                .build();
        Answer answer = new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String method = invocation.getMethod().getName();
                if(method == "uidGenerator"){
                    return UUID.randomUUID().toString().substring(0,31);
                }
                return null;
            }
        };
        when(remoteIdGeneratorService.uidGenerator()).thenAnswer(answer);
    }

    @Test
    public void create() throws Exception {
        ProductCategoryCreateDTO productCategoryCreateDTO = new ProductCategoryCreateDTO();
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(productCategoryCreateDTO);
        mvc.perform(post("/operate/productCategory/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isEmpty());
    }

}
