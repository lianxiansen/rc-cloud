package com.rc.cloud.app.operate.application;

import com.rc.cloud.app.operate.application.bo.BrandBO;
import com.rc.cloud.app.operate.application.dto.BrandCreateDTO;
import com.rc.cloud.app.operate.application.dto.BrandQueryPageDTO;
import com.rc.cloud.app.operate.application.dto.BrandUpdateDTO;
import com.rc.cloud.app.operate.application.service.BrandApplicationService;
import com.rc.cloud.app.operate.domain.model.brand.Brand;
import com.rc.cloud.app.operate.domain.model.brand.BrandService;
import com.rc.cloud.app.operate.domain.model.brand.BrandRepository;
import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.infrastructure.persistence.repository.LocalIdRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.util.RandomUtils;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.exception.DomainException;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.util.TenantContext;
import com.rc.cloud.common.core.util.object.ObjectUtils;
import com.rc.cloud.common.test.core.ut.BaseMockitoUnitTest;
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
 * |-5.1删除已关联产品的品牌，阻止删除
 * 6.分页检索品牌，每页大小为10，记录数小于10，指定页码1，返回页数1和全部记录数
 * |-6.1分页检索品牌，每页大小为10，记录数小于10，指定页码2，返回记录为空 TODO
 * |-6.2分页检索品牌，每页大小为10，记录数为25，指定页码1，返回前10条记录 TODO
 * |-6.3分页检索品牌，每页大小为10，记录数为25，指定页码3，返回最后5条记录 TODO
 */
@ExtendWith({SpringExtension.class})
@Import({BrandApplicationService.class, BrandService.class, LocalIdRepositoryImpl.class})
@DisplayName("品牌应用服务测试")
@FixMethodOrder(MethodSorters.DEFAULT)
public class BrandApplicationServiceUnitTest extends BaseMockitoUnitTest {
    @Autowired
    private BrandApplicationService brandApplicationService;
    @Autowired
    private IdRepository idRepository;
    @MockBean
    private BrandRepository brandRepositoryStub;

    @MockBean
    private ProductRepository productRepositoryStub;

    private BrandCreateDTO createBrandDTO;

    private BrandUpdateDTO updateBrandDTO;

    private Brand brandMock;
    @BeforeEach
    public void beforeEach() {
        initStub();
        initFixture();
    }


    @Test
    @DisplayName("创建品牌")
    public void createBrand() {
        BrandBO brandBO = brandApplicationService.createBrand(createBrandDTO);
        Assertions.assertTrue(ObjectUtils.isNotNull(brandBO.getId()) && createBrandDTO.getName().equals(brandBO.getName()) && createBrandDTO.getEnabled().booleanValue() == brandBO.isEnable() && createBrandDTO.getSortId().intValue() == brandBO.getSort() && createBrandDTO.getType().equals(brandBO.getType()), "创建品牌失败");

    }

    @Test
    @DisplayName("启用品牌")
    public void enableBrand() {
        brandMock.disable();
        Assertions.assertFalse(brandMock.isEnable());
        when(brandRepositoryStub.findById(brandMock.getId())).thenReturn(brandMock);
        brandApplicationService.changeState(brandMock.getId().id());
        Assertions.assertTrue(brandMock.isEnable(), "启用品牌失败");
    }

    @Test
    @DisplayName("禁用品牌")
    public void disableBrand() {
        brandMock.enable();
        Assertions.assertTrue(brandMock.isEnable());
        when(brandRepositoryStub.findById(brandMock.getId())).thenReturn(brandMock);
        brandApplicationService.changeState(brandMock.getId().id());
        Assertions.assertTrue(!brandMock.isEnable(), "启用品牌失败");
    }


    @Test
    @DisplayName("更新品牌")
    public void updateBrand() {
        updateBrandDTO.setId(brandMock.getId().id());
        when(brandRepositoryStub.findById(brandMock.getId())).thenReturn(brandMock);
        BrandBO brandBO = brandApplicationService.updateBrand(updateBrandDTO);
        Assertions.assertTrue(brandMock.getId().id().equals(brandBO.getId()) && updateBrandDTO.getName().equals(brandBO.getName()) && updateBrandDTO.getEnabled().booleanValue() == brandBO.isEnable() && updateBrandDTO.getSortId().intValue() == brandBO.getSort() && updateBrandDTO.getType().equals(brandBO.getType()), "更新品牌失败");
    }


    @Test
    @DisplayName("删除品牌")
    public void removeBrand() {
        when(productRepositoryStub.existsByBrandId(brandMock.getId())).thenReturn(false);
        when(brandRepositoryStub.findById(brandMock.getId())).thenReturn(brandMock);
        when(brandRepositoryStub.removeById(brandMock.getId())).thenReturn(true);
        Assertions.assertTrue(brandApplicationService.remove(brandMock.getId().id()), "删除品牌失败");
    }

    @Test
    @DisplayName("删除已关联产品的品牌")
    public void removeBrandWhenAssocatedProductThenThrowException() {
        when(productRepositoryStub.existsByBrandId(brandMock.getId())).thenReturn(true);
        when(brandRepositoryStub.exists(brandMock.getId())).thenReturn(true);
        when(brandRepositoryStub.removeById(brandMock.getId())).thenReturn(true);
        Assertions.assertThrows(DomainException.class, () -> {
            brandApplicationService.remove(brandMock.getId().id());
        });
    }


    @Test
    @DisplayName("分页检索品牌")
    public void selectPageResult() {
        //分页检索品牌，每页大小为10，记录数小于10，指定页码1，返回页数1和全部记录数
        BrandQueryPageDTO queryBrandDTO = new BrandQueryPageDTO();
        queryBrandDTO.setPageNo(1);
        queryBrandDTO.setPageSize(10);
        long totalCount = 5;
        Answer answer = new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String method = invocation.getMethod().getName();
                if (method == "selectPageResult") {
                    List<Brand> brandList = new ArrayList<>();
                    for (long i = 0; i < totalCount; i++) {
                        Brand brand = new Brand(new BrandId(idRepository.nextId()), createBrandDTO.getName());
                        brandList.add(brand);
                    }
                    PageResult<Brand> brandPageResult = new PageResult<>();
                    brandPageResult.setTotal(5L);
                    brandPageResult.setList(brandList);
                    return brandPageResult;
                }
                return null;
            }
        };
        when(brandRepositoryStub.selectPageResult(queryBrandDTO.getPageNo(), queryBrandDTO.getPageSize(), queryBrandDTO.getName())).thenAnswer(answer);
        PageResult<BrandBO> brandVOPageResult = brandApplicationService.selectPageResult(queryBrandDTO);
        Assertions.assertTrue(brandVOPageResult.getTotal().longValue() == totalCount && brandVOPageResult.getList().size() == totalCount, "分页检索品牌失败");
    }
    private void initStub() {

    }
    private void initFixture() {
        TenantContext.setTenantId("test");
        createBrandDTO = new BrandCreateDTO();
        createBrandDTO.setName(RandomUtils.randomString()).setSortId(RandomUtils.randomInteger()).setEnabled(new Boolean(true)).setType(RandomUtils.randomString());
        updateBrandDTO = new BrandUpdateDTO();
        updateBrandDTO.setName(RandomUtils.randomString()).setSortId(RandomUtils.randomInteger()).setEnabled(new Boolean(true)).setType(RandomUtils.randomString());

        brandMock = new Brand(new BrandId(idRepository.nextId()), createBrandDTO.getName());
        brandMock.setName(createBrandDTO.getName());
        brandMock.setSort(createBrandDTO.getSortId());
        brandMock.setType(createBrandDTO.getType());
    }




}
