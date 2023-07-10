package com.rc.cloud.app.operate.application;

import com.rc.cloud.app.operate.application.bo.BrandBO;
import com.rc.cloud.app.operate.application.dto.BrandCreateDTO;
import com.rc.cloud.app.operate.application.dto.BrandQueryPageDTO;
import com.rc.cloud.app.operate.application.dto.BrandUpdateDTO;
import com.rc.cloud.app.operate.application.service.BrandApplicationService;
import com.rc.cloud.app.operate.domain.common.DomainException;
import com.rc.cloud.app.operate.domain.model.brand.Brand;
import com.rc.cloud.app.operate.domain.model.brand.BrandRepository;
import com.rc.cloud.app.operate.domain.model.brand.valobj.BrandId;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.service.BrandDomainService;
import com.rc.cloud.app.operate.infrastructure.util.RandomUtils;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.util.object.ObjectUtils;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runners.MethodSorters;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * @ClassName: BrandApplicationServiceTest
 * @Author: liandy
 * @Date: 2023/7/7 11:01
 * 1.创建品牌
 * 2.启用品牌
 * 3.禁用品牌
 * 4.更新品牌
 * 5.删除品牌
 *  |-5.1删除已关联产品的品牌，阻止删除
 * 6.分页检索品牌，每页大小为10，记录数小于10，指定页码1，返回页数1和全部记录数
 *  |-6.1分页检索品牌，每页大小为10，记录数小于10，指定页码2，返回记录为空 TODO
 *  |-6.2分页检索品牌，每页大小为10，记录数为25，指定页码1，返回前10条记录 TODO
 *  |-6.3分页检索品牌，每页大小为10，记录数为25，指定页码3，返回最后5条记录 TODO
 *
 */
@ExtendWith({SpringExtension.class})
@Import({BrandApplicationService.class, BrandDomainService.class})
@DisplayName("品牌应用服务测试")
@FixMethodOrder(MethodSorters.DEFAULT)
public class BrandApplicationServiceTest {
    @MockBean
    private BrandRepository brandRepositoryMock;
    @MockBean
    private ProductRepository productRepositoryMock;
    @Autowired
    private BrandApplicationService brandApplicationService;

    private BrandCreateDTO createBrandDTO;

    private BrandUpdateDTO updateBrandDTO;
    @BeforeEach
    public void beforeEach() {
        createBrandDTO = new BrandCreateDTO();
        createBrandDTO.setName(RandomUtils.randomString())
                .setSortId(RandomUtils.randomInteger())
                .setEnabled(new Boolean(true))
                .setType(RandomUtils.randomString());
        updateBrandDTO= new BrandUpdateDTO();
        updateBrandDTO.setName(RandomUtils.randomString())
                .setSortId(RandomUtils.randomInteger())
                .setEnabled(new Boolean(true))
                .setType(RandomUtils.randomString());

        Answer answer = new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String method = invocation.getMethod().getName();
                if (method == "nextBrandId") {
                    return new BrandId(UUID.randomUUID().toString().substring(0, 31));
                }
                return null;
            }
        };
        when(brandRepositoryMock.nextId()).thenAnswer(answer);
    }

    @Test
    @DisplayName("创建品牌")
    public void createBrand() {
        Brand brand=brandApplicationService.createBrand(createBrandDTO);
        Assertions.assertTrue(ObjectUtils.isNotNull(brand.getId()) &&
                createBrandDTO.getName().equals(brand.getName()) &&
                createBrandDTO.getEnabled().booleanValue() == brand.isEnable()&&
                createBrandDTO.getSortId().intValue()==brand.getSort()&&
                createBrandDTO.getType().equals(brand.getType()), "创建品牌失败");

    }
    @Test
    @DisplayName("启用品牌")
    public void enableBrand() {
        createBrandDTO.setEnabled(Boolean.FALSE);
        Brand brand=brandApplicationService.createBrand(createBrandDTO);
        Assertions.assertFalse(brand.isEnable());
        when(brandRepositoryMock.findById(brand.getId())).thenReturn(brand);
        brandApplicationService.changeState(brand.getId().id());
        Assertions.assertTrue(ObjectUtils.isNotNull(brand.getId()) &&
                createBrandDTO.getName().equals(brand.getName()) &&
                brand.isEnable()&&
                createBrandDTO.getSortId().intValue()==brand.getSort()&&
                createBrandDTO.getType().equals(brand.getType()), "启用品牌失败");
    }
    @Test
    @DisplayName("禁用品牌")
    public void disableBrand() {
        createBrandDTO.setEnabled(Boolean.TRUE);
        Brand brand=brandApplicationService.createBrand(createBrandDTO);
        Assertions.assertTrue(brand.isEnable());
        when(brandRepositoryMock.findById(brand.getId())).thenReturn(brand);
        brandApplicationService.changeState(brand.getId().id());
        Assertions.assertTrue(ObjectUtils.isNotNull(brand.getId()) &&
                createBrandDTO.getName().equals(brand.getName()) &&
                !brand.isEnable()&&
                createBrandDTO.getSortId().intValue()==brand.getSort()&&
                createBrandDTO.getType().equals(brand.getType()), "启用品牌失败");
    }


    @Test
    @DisplayName("更新品牌")
    public void updateBrand() {
        Brand brand=brandApplicationService.createBrand(createBrandDTO);
        updateBrandDTO.setId(brand.getId().id());
        when(brandRepositoryMock.findById(brand.getId())).thenReturn(brand);
        brandApplicationService.updateBrand(updateBrandDTO);
        Assertions.assertTrue(ObjectUtils.isNotNull(brand.getId()) &&
                updateBrandDTO.getName().equals(brand.getName()) &&
                updateBrandDTO.getEnabled().booleanValue() == brand.isEnable()&&
                updateBrandDTO.getSortId().intValue()==brand.getSort()&&
                updateBrandDTO.getType().equals(brand.getType()), "更新品牌失败");
    }
    @Test
    @DisplayName("删除品牌")
    public void removeBrand() {
        Brand brand=brandApplicationService.createBrand(createBrandDTO);
        when(brandRepositoryMock.removeById(brand.getId())).thenReturn(true);
        Assertions.assertTrue(brandApplicationService.remove(brand.getId().id()),"删除品牌失败");
    }

    @Test
    @DisplayName("删除已关联产品的品牌")
    public void removeBrandAssocatedProduct() {
        Brand brand=brandApplicationService.createBrand(createBrandDTO);
        when(productRepositoryMock.existsByBrandId(brand.getId())).thenReturn(true);
        when(brandRepositoryMock.removeById(brand.getId())).thenReturn(true);
        Assertions.assertThrows(DomainException.class, () -> {
            brandApplicationService.remove(brand.getId().id());
        });
    }
    @Test
    @DisplayName("分页检索品牌")
    public void selectPageResult() {
        //分页检索品牌，每页大小为10，记录数小于10，指定页码1，返回页数1和全部记录数
        BrandQueryPageDTO queryBrandDTO=new BrandQueryPageDTO();
        queryBrandDTO.setPageNo(1);
        queryBrandDTO.setPageSize(10);
        long totalCount=5;
        Answer answer = new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String method = invocation.getMethod().getName();
                if (method == "selectPageResult") {
                    List<Brand> brandList=new ArrayList<>();
                    for(long i = 0; i < totalCount; i++){
                        Brand brand=brandApplicationService.createBrand(createBrandDTO);
                        brandList.add(brand);
                    }
                    PageResult<Brand> brandPageResult=new PageResult<>();
                    brandPageResult.setTotal(5L);
                    brandPageResult.setList(brandList);
                    return brandPageResult;
                }
                return null;
            }
        };
        when(brandRepositoryMock.selectPageResult(queryBrandDTO.getPageNo(),queryBrandDTO.getPageSize(),queryBrandDTO.getName())).thenAnswer(answer);
        PageResult<BrandBO> brandVOPageResult= brandApplicationService.selectPageResult(queryBrandDTO);
        Assertions.assertTrue(brandVOPageResult.getTotal().longValue()==totalCount&&
                brandVOPageResult.getList().size() == totalCount ,"分页检索品牌失败");
    }


}
