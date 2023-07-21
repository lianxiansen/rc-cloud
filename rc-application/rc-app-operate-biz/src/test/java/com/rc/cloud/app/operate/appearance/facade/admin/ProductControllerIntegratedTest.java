package com.rc.cloud.app.operate.appearance.facade.admin;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.operate.application.dto.*;
import com.rc.cloud.common.test.annotation.RcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RcTest
public class ProductControllerIntegratedTest {

    @Autowired
    private WebApplicationContext context;

    private
    MockMvc mvc;

    private static final String imgUrl = "https://t7.baidu.com/it/u=3556773076,803642467&fm=3031&app=3031&size=f242,150&n=0&f=JPEG&fmt=auto?s=A51064321779538A505174D6020010B0&sec=1688490000&t=4ef579bd316ebdc454ab321a8676bbdf";



    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
//                .apply(springSecurity())
                .build();
    }



    @Test
    public void create() throws Exception {
        ProductSaveDTO product = createProduct();
        String str= JSON.toJSONString(product);
        System.out.println(str);
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(product);
        mvc.perform(post("/admin/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    private ProductSaveDTO createProduct(){
        ProductSaveDTO productSaveDTO=new ProductSaveDTO();
        productSaveDTO.setName("优生活香水洗衣液持久留香柔顺护色机洗手洗天然家庭装");
        String v="[\n" +
                "      {\"name\":\"颜色\",\"value\":\"红\",\"sort\":1},\n" +
                "      {\"name\":\"颜色\",\"value\":\"蓝\",\"sort\":3},\n" +
                "      {\"name\":\"尺寸\",\"value\":\"X\",\"sort\":4},\n" +
                "      {\"name\":\"尺寸\",\"value\":\"XL\",\"sort\":5}\n" +
                "  ]";
        java.util.List<ProductAttributeSaveDTO> productAttributeSaveDTOS = JSONUtil.toList(v, ProductAttributeSaveDTO.class);
        productSaveDTO.setAttributes(productAttributeSaveDTOS);

        String images="[{\"url\":\"https://cbu01.alicdn.com/img/ibank/2019/004/218/10888812400_1788414178.jpg\",\"sort\":1}\n" +
                "     ,{\"url\":\"https://cbu01.alicdn.com/img/ibank/2019/004/218/10888812400_1788414178.jpg\",\"sort\":2}]";


        productSaveDTO.setBrandId("1234567");
        productSaveDTO.setEnableFlag(true);
        productSaveDTO.setCustomClassificationId("1234");


        java.util.List<ProductSkuSaveDTO> skus = new ArrayList<>();
        String sku1="{\n" +
                "     \"skuCode\":\"001\",\n" +
                "     \"price\":\"12\",\n" +
                "     \"supplyPrice\":\"12\",\n" +
                "     \"weight\":\"1\",\n" +
                "     \"enabledFlag\":\"true\",\n" +
                "     \"albums\":[{\"url\":\"https://cbu01.alicdn.com/img/ibank/2019/004/218/10888812400_1788414178.jpg\",\"sort\":1}\n" +
                "     ,{\"url\":\"https://cbu01.alicdn.com/img/ibank/2019/004/218/10888812400_1788414178.jpg\",\"sort\":2}],\n" +
                "     \"attributes\":[{\"name\":\"颜色\",\"value\":\"红\",\"sort\":9},{\"name\":\"尺寸\",\"value\":\"X\",\"sort\":9}],\n" +
                "     \"inventory\":99,\n" +
                "     \"sort\":99,\n" +
                "\n" +
                "     }";
        ProductSkuSaveDTO productSkuSaveDTO1 = JSONUtil.toBean(sku1, ProductSkuSaveDTO.class);
        skus.add(productSkuSaveDTO1);

        String sku2="{\n" +
                "     \"skuCode\":\"002\",\n" +
                "     \"price\":\"12\",\n" +
                "     \"supplyPrice\":\"12\",\n" +
                "     \"weight\":\"1\",\n" +
                "     \"enabledFlag\":\"true\",\n" +
                "     \"albums\":[{\"url\":\"https://cbu01.alicdn.com/img/ibank/2019/004/218/10888812400_1788414178.jpg\",\"sort\":1}\n" +
                "     ,{\"url\":\"https://cbu01.alicdn.com/img/ibank/2019/004/218/10888812400_1788414178.jpg\",\"sort\":2}],\n" +
                "     \"attributes\":[{\"name\":\"颜色\",\"value\":\"蓝\",\"sort\":9},{\"name\":\"尺寸\",\"value\":\"X\",\"sort\":9}],\n" +
                "     \"inventory\":99,\n" +
                "     \"sort\":99,\n" +
                "\n" +
                "     }";
        ProductSkuSaveDTO productSkuSaveDTO2 = JSONUtil.toBean(sku2, ProductSkuSaveDTO.class);
        skus.add(productSkuSaveDTO2);

        String sku3="{\n" +
                "     \"skuCode\":\"003\",\n" +
                "     \"price\":\"12\",\n" +
                "     \"supplyPrice\":\"12\",\n" +
                "     \"weight\":\"1\",\n" +
                "     \"enabledFlag\":\"true\",\n" +
                "     \"albums\":[{\"url\":\"https://cbu01.alicdn.com/img/ibank/2019/004/218/10888812400_1788414178.jpg\",\"sort\":1}\n" +
                "     ,{\"url\":\"https://cbu01.alicdn.com/img/ibank/2019/004/218/10888812400_1788414178.jpg\",\"sort\":2}],\n" +
                "     \"attributes\":[{\"name\":\"颜色\",\"value\":\"红\",\"sort\":9},{\"name\":\"尺寸\",\"value\":\"XL\",\"sort\":9}],\n" +
                "     \"inventory\":99,\n" +
                "     \"sort\":99,\n" +
                "\n" +
                "     }";
        ProductSkuSaveDTO productSkuSaveDTO3 = JSONUtil.toBean(sku3, ProductSkuSaveDTO.class);
        skus.add(productSkuSaveDTO3);

        String sku4="{\n" +
                "     \"skuCode\":\"004\",\n" +
                "     \"price\":\"12\",\n" +
                "     \"supplyPrice\":\"12\",\n" +
                "     \"weight\":\"1\",\n" +
                "     \"enabledFlag\":\"true\",\n" +
                "     \"albums\":[{\"url\":\"https://cbu01.alicdn.com/img/ibank/2019/004/218/10888812400_1788414178.jpg\",\"sort\":1}\n" +
                "     ,{\"url\":\"https://cbu01.alicdn.com/img/ibank/2019/004/218/10888812400_1788414178.jpg\",\"sort\":2}],\n" +
                "     \"attributes\":[{\"name\":\"颜色\",\"value\":\"蓝\",\"sort\":9},{\"name\":\"尺寸\",\"value\":\"XL\",\"sort\":9}],\n" +
                "     \"inventory\":99,\n" +
                "     \"sort\":99,\n" +
                "\n" +
                "     }";
        ProductSkuSaveDTO productSkuSaveDTO4 = JSONUtil.toBean(sku4, ProductSkuSaveDTO.class);
        skus.add(productSkuSaveDTO4);

        productSaveDTO.setSkus(skus);

        productSaveDTO.setExplosivesFlag(true);
        productSaveDTO.setExplosivesImage("https://cbu01.alicdn.com/img/ibank/2019/004/218/10888812400_1788414178.jpg");

        productSaveDTO.setFirstCategory("家居用品");
        productSaveDTO.setSecondCategory("日用百货");
        productSaveDTO.setThirdCategory("清洁洗剂");
        productSaveDTO.setVideoImg("https://cbu01.alicdn.com/img/ibank/2019/423/040/10889040324_1788414178.jpg");
        productSaveDTO.setVideoUrl("https://cbu01.alicdn.com/img/ibank/2019/423/040/10889040324_1788414178.jpg");
        productSaveDTO.setInstallVideoImg("https://cbu01.alicdn.com/img/ibank/2019/423/040/10889040324_1788414178.jpg");
        productSaveDTO.setInstallVideoUrl("https://cbu01.alicdn.com/img/ibank/2019/423/040/10889040324_1788414178.jpg");

        productSaveDTO.setPublicFlag(true);
        productSaveDTO.setRecommendFlag(true);
        productSaveDTO.setNewFlag(true);
        productSaveDTO.setTag("优生活,香水,洗衣液");

        productSaveDTO.setTenantId("001");
        productSaveDTO.setDetail("sadfdsafasdfasdfasdfsdf");
        return productSaveDTO;
    }

}
