package com.rc.cloud.app.marketing.appearance.api;

import com.rc.cloud.app.marketing.appearance.api.req.DeliveryAddressCreateRequest;
import com.rc.cloud.app.marketing.appearance.api.req.DeliveryAddressUpdateRequest;
import com.rc.cloud.app.marketing.domain.entity.customer.Customer;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddressRepository;
import com.rc.cloud.app.marketing.infrastructure.repository.mapper.DeliveryAddressMapper;
import com.rc.cloud.app.marketing.infrastructure.repository.po.DeliveryAddressPO;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import com.rc.cloud.common.test.annotation.RcTest;
import com.rc.cloud.common.test.core.util.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @ClassName DeliveryAddressControllerTest
 * @Author liandy
 * @Date 2023/8/7 13:16
 * @Description 收货地址测试
 * @Version 1.0
 */
@RcTest
public class DeliveryAddressControllerTest {
    private MockMvc mvc;
    @Resource
    private WebApplicationContext context;
    @Resource
    private DeliveryAddressMapper deliveryAddressMapper;
    @Resource
    private DeliveryAddressRepository deliveryAddressRepository;
    @Resource
    private IdRepository idRepository;
    private DeliveryAddressCreateRequest deliveryAddressCreateRequest;
    private DeliveryAddressUpdateRequest deliveryAddressUpdateRequest;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        deliveryAddressCreateRequest = RandomUtils.randomPojo(DeliveryAddressCreateRequest.class, o -> {
            o.setDefaulted(true);
            o.setMobile(RandomUtils.randomMobile());
        });
        deliveryAddressUpdateRequest = RandomUtils.randomPojo(DeliveryAddressUpdateRequest.class, o -> {
            o.setDefaulted(true);
            o.setMobile(RandomUtils.randomMobile());
        });
        LambdaQueryWrapperX<DeliveryAddressPO> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.eq(DeliveryAddressPO::getCustomerId, Customer.mock().getId());
        deliveryAddressMapper.delete(queryWrapper);
    }

    @DisplayName(value = "新建收货地址")
    @Test
    public void create() throws Exception {
        String requestBody = "{\n" +
                "  \"name\": \"" + deliveryAddressCreateRequest.getName() + "\",\n" +
                "  \"mobile\": \"" + deliveryAddressCreateRequest.getMobile() + "\",\n" +
                "  \"zipcode\": \"" + deliveryAddressCreateRequest.getZipcode() + "\",\n" +
                "  \"provinceCode\": \"" + deliveryAddressCreateRequest.getProvinceCode() + "\",\n" +
                "  \"province\": \"" + deliveryAddressCreateRequest.getProvince() + "\",\n" +
                "  \"cityCode\": \"" + deliveryAddressCreateRequest.getCityCode() + "\",\n" +
                "  \"city\": \"" + deliveryAddressCreateRequest.getCity() + "\",\n" +
                "  \"districtCode\": \"" + deliveryAddressCreateRequest.getDistrictCode() + "\",\n" +
                "  \"district\": \"" + deliveryAddressCreateRequest.getDistrict() + "\",\n" +
                "  \"detail\": \"" + deliveryAddressCreateRequest.getDetail() + "\",\n" +
                "  \"defaulted\": " + deliveryAddressCreateRequest.getDefaulted() + "\n" +
                "}";
        mvc.perform(post("/app/v1/deliveryAddress/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.customerId").isNotEmpty())
                .andExpect(jsonPath("$.data.name").value(deliveryAddressCreateRequest.getName()))
                .andExpect(jsonPath("$.data.mobile").value(deliveryAddressCreateRequest.getMobile()))
                .andExpect(jsonPath("$.data.zipcode").value(deliveryAddressCreateRequest.getZipcode()))
                .andExpect(jsonPath("$.data.provinceCode").value(deliveryAddressCreateRequest.getProvinceCode()))
                .andExpect(jsonPath("$.data.province").value(deliveryAddressCreateRequest.getProvince()))
                .andExpect(jsonPath("$.data.cityCode").value(deliveryAddressCreateRequest.getCityCode()))
                .andExpect(jsonPath("$.data.city").value(deliveryAddressCreateRequest.getCity()))
                .andExpect(jsonPath("$.data.districtCode").value(deliveryAddressCreateRequest.getDistrictCode()))
                .andExpect(jsonPath("$.data.district").value(deliveryAddressCreateRequest.getDistrict()))
                .andExpect(jsonPath("$.data.detail").value(deliveryAddressCreateRequest.getDetail()))
                .andExpect(jsonPath("$.data.defaulted").value(deliveryAddressCreateRequest.getDefaulted()));
    }

    @DisplayName(value = "更新收货地址")
    @Test
    public void update() throws Exception {
        DeliveryAddressPO po = new DeliveryAddressPO();
        po.setId(idRepository.nextId());
        po.setCustomerId(Customer.mock().getId());
        po.setName(deliveryAddressCreateRequest.getName());
        po.setMobile(deliveryAddressCreateRequest.getMobile());
        po.setZipcode(deliveryAddressCreateRequest.getZipcode());
        po.setProvinceCode(deliveryAddressCreateRequest.getProvinceCode());
        po.setProvince(deliveryAddressCreateRequest.getProvince());
        po.setCityCode(deliveryAddressCreateRequest.getCityCode());
        po.setCity(deliveryAddressCreateRequest.getCity());
        po.setDistrictCode(deliveryAddressCreateRequest.getDistrictCode());
        po.setDistrict(deliveryAddressCreateRequest.getDistrict());
        po.setDetail(deliveryAddressCreateRequest.getDetail());
        po.setDefaulted(false);
        deliveryAddressMapper.insert(po);
        String requestBody = "{\n" +
                "  \"id\": \"" + po.getId() + "\",\n" +
                "  \"name\": \"" + deliveryAddressUpdateRequest.getName() + "\",\n" +
                "  \"mobile\": \"" + deliveryAddressUpdateRequest.getMobile() + "\",\n" +
                "  \"zipcode\": \"" + deliveryAddressUpdateRequest.getZipcode() + "\",\n" +
                "  \"provinceCode\": \"" + deliveryAddressUpdateRequest.getProvinceCode() + "\",\n" +
                "  \"province\": \"" + deliveryAddressUpdateRequest.getProvince() + "\",\n" +
                "  \"cityCode\": \"" + deliveryAddressUpdateRequest.getCityCode() + "\",\n" +
                "  \"city\": \"" + deliveryAddressUpdateRequest.getCity() + "\",\n" +
                "  \"districtCode\": \"" + deliveryAddressUpdateRequest.getDistrictCode() + "\",\n" +
                "  \"district\": \"" + deliveryAddressUpdateRequest.getDistrict() + "\",\n" +
                "  \"detail\": \"" + deliveryAddressUpdateRequest.getDetail() + "\",\n" +
                "  \"defaulted\": " + deliveryAddressUpdateRequest.getDefaulted() + "\n" +
                "}";
        mvc.perform(post("/app/v1/deliveryAddress/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.customerId").isNotEmpty())
                .andExpect(jsonPath("$.data.name").value(deliveryAddressUpdateRequest.getName()))
                .andExpect(jsonPath("$.data.mobile").value(deliveryAddressUpdateRequest.getMobile()))
                .andExpect(jsonPath("$.data.zipcode").value(deliveryAddressUpdateRequest.getZipcode()))
                .andExpect(jsonPath("$.data.provinceCode").value(deliveryAddressUpdateRequest.getProvinceCode()))
                .andExpect(jsonPath("$.data.province").value(deliveryAddressUpdateRequest.getProvince()))
                .andExpect(jsonPath("$.data.cityCode").value(deliveryAddressUpdateRequest.getCityCode()))
                .andExpect(jsonPath("$.data.city").value(deliveryAddressUpdateRequest.getCity()))
                .andExpect(jsonPath("$.data.districtCode").value(deliveryAddressUpdateRequest.getDistrictCode()))
                .andExpect(jsonPath("$.data.district").value(deliveryAddressUpdateRequest.getDistrict()))
                .andExpect(jsonPath("$.data.detail").value(deliveryAddressUpdateRequest.getDetail()))
                .andExpect(jsonPath("$.data.defaulted").value(deliveryAddressUpdateRequest.getDefaulted()));
    }

    @DisplayName(value = "获取收货地址列表")
    @Test
    public void findList() throws Exception {
        int datasize = 3;
        deliveryAddressCreateRequest.setDefaulted(true);
        for (int i = 0; i < datasize; i++) {
            DeliveryAddressPO po = new DeliveryAddressPO();
            po.setId(idRepository.nextId());
            po.setCustomerId(Customer.mock().getId());
            po.setName(deliveryAddressCreateRequest.getName());
            po.setMobile(deliveryAddressCreateRequest.getMobile());
            po.setZipcode(deliveryAddressCreateRequest.getZipcode());
            po.setProvinceCode(deliveryAddressCreateRequest.getProvinceCode());
            po.setProvince(deliveryAddressCreateRequest.getProvince());
            po.setCityCode(deliveryAddressCreateRequest.getCityCode());
            po.setCity(deliveryAddressCreateRequest.getCity());
            po.setDistrictCode(deliveryAddressCreateRequest.getDistrictCode());
            po.setDistrict(deliveryAddressCreateRequest.getDistrict());
            po.setDetail(deliveryAddressCreateRequest.getDetail());
            po.setDefaulted(deliveryAddressCreateRequest.getDefaulted());
            deliveryAddressMapper.insert(po);
        }
        mvc.perform(get("/app/v1/deliveryAddress/findList")
                        .characterEncoding(Charset.defaultCharset())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].id").isNotEmpty())
                .andExpect(jsonPath("$.data[0].customerId").isNotEmpty())
                .andExpect(jsonPath("$.data[0].name").value(deliveryAddressCreateRequest.getName()))
                .andExpect(jsonPath("$.data[0].mobile").value(deliveryAddressCreateRequest.getMobile()))
                .andExpect(jsonPath("$.data[0].zipcode").value(deliveryAddressCreateRequest.getZipcode()))
                .andExpect(jsonPath("$.data[0].provinceCode").value(deliveryAddressCreateRequest.getProvinceCode()))
                .andExpect(jsonPath("$.data[0].province").value(deliveryAddressCreateRequest.getProvince()))
                .andExpect(jsonPath("$.data[0].cityCode").value(deliveryAddressCreateRequest.getCityCode()))
                .andExpect(jsonPath("$.data[0].city").value(deliveryAddressCreateRequest.getCity()))
                .andExpect(jsonPath("$.data[0].districtCode").value(deliveryAddressCreateRequest.getDistrictCode()))
                .andExpect(jsonPath("$.data[0].district").value(deliveryAddressCreateRequest.getDistrict()))
                .andExpect(jsonPath("$.data[0].detail").value(deliveryAddressCreateRequest.getDetail()))
                .andExpect(jsonPath("$.data[0].defaulted").value(deliveryAddressCreateRequest.getDefaulted()));

    }

}
