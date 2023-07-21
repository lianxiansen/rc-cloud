package com.rc.cloud.app.operate.application;

import com.rc.cloud.app.operate.application.bo.BrandBO;
import com.rc.cloud.app.operate.application.dto.BrandCreateDTO;
import com.rc.cloud.app.operate.application.dto.BrandQueryPageDTO;
import com.rc.cloud.app.operate.application.dto.BrandUpdateDTO;
import com.rc.cloud.app.operate.application.service.BrandApplicationService;
import com.rc.cloud.app.operate.domain.model.brand.Brand;
import com.rc.cloud.app.operate.domain.model.brand.BrandDomainService;
import com.rc.cloud.app.operate.domain.model.brand.BrandRepository;
import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.BrandRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.LocalIdRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.ProductRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.BrandMapper;
import com.rc.cloud.app.operate.infrastructure.util.ConditionUtil;
import com.rc.cloud.app.operate.infrastructure.util.RandomUtils;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.util.TenantContext;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import com.rc.cloud.common.test.core.ut.BaseDbUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.Objects;

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
 * 6.分页检索品牌，每页大小为10，记录数26，指定页码1，返回记录数10
 * |-6.1分页检索品牌，每页大小为10，记录数26，指定页码3，返回记录数6
 * 7.根据唯一标识获取品牌
 */
@Import({BrandApplicationService.class, LocalIdRepositoryImpl.class, BrandDomainService.class, BrandRepositoryImpl.class, ProductRepositoryImpl.class})
@DisplayName("品牌集成测试")
public class BrandApplicationServiceIntegratedTest extends BaseDbUnitTest {
    @Autowired
    private BrandApplicationService brandApplicationService;

    @Autowired
    private BrandDomainService brandDomainService;
    @Resource
    private IdRepository idRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private BrandMapper brandMapper;
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
        BrandBO brandBO = brandApplicationService.create(createBrandDTO);
        assertEquals(createBrandDTO,brandBO);
    }




    @Test
    @DisplayName("更新品牌")
    public void updateBrand() {
        updateBrandDTO.setId(brandMock.getId().id());
        BrandBO brandBO = brandApplicationService.update(updateBrandDTO);
        assertEquals(updateBrandDTO,brandBO);
    }



    @Test
    @DisplayName("删除品牌")
    public void removeBrand() {
        when(productRepositoryStub.existsByBrandId(brandMock.getId())).thenReturn(false);
        Assertions.assertTrue(brandApplicationService.remove(brandMock.getId().id()), "删除品牌失败");
    }

    @Test
    @DisplayName("删除已关联产品的品牌")
    public void removeBrandWhenAssocatedProductThenThrowException() {
        when(productRepositoryStub.existsByBrandId(brandMock.getId())).thenReturn(true);
        Assertions.assertThrows(ServiceException.class, () -> {
            brandApplicationService.remove(brandMock.getId().id());
        });
    }



    @Test
    @DisplayName("分页检索品牌返回首页列表")
    public void selectPageResultReturnFirstPage() {
        //数据准备
        brandMapper.delete(new LambdaQueryWrapperX<>());
        BrandQueryPageDTO queryBrandDTO = new BrandQueryPageDTO();
        queryBrandDTO.setPageNo(1);
        queryBrandDTO.setPageSize(10);
        long totalCount = 26;
        for (long i = 0; i < totalCount; i++) {
            brandApplicationService.create(createBrandDTO);
        }
        //执行
        PageResult<BrandBO> brandVOPageResult = brandApplicationService.selectPageResult(queryBrandDTO);
        //验证
        Assertions.assertTrue(brandVOPageResult.getTotal().longValue() == totalCount && brandVOPageResult.getList().size() == queryBrandDTO.getPageSize(), "分页检索品牌失败");
    }


    @Test
    @DisplayName("分页检索品牌返回末页列表")
    public void selectPageResultReturnLastPage() {
        //数据准备
        brandMapper.delete(new LambdaQueryWrapperX<>());
        BrandQueryPageDTO queryBrandDTO = new BrandQueryPageDTO();
        queryBrandDTO.setPageNo(3);
        queryBrandDTO.setPageSize(10);
        long totalCount = 26;
        for (long i = 0; i < totalCount; i++) {
            brandApplicationService.create(createBrandDTO);
        }
        //执行
        PageResult<BrandBO> brandVOPageResult = brandApplicationService.selectPageResult(queryBrandDTO);
        //验证
        Assertions.assertTrue(brandVOPageResult.getTotal().longValue() == totalCount && brandVOPageResult.getList().size() == totalCount % queryBrandDTO.getPageSize(), "分页检索品牌失败");
    }

    @Test
    @DisplayName("根据唯一标识获取品牌")
    public void findById(){
        BrandBO bo=brandApplicationService.findById(brandMock.getId().id());
        assertEquals(brandMock,bo);
    }





    private void initStub() {

    }

    private void initFixture() {
        String imgUrl = "http://127.0.0.1:9000/test/2023/07/20/56a3d87acd3b4105950be3647abc5383.jpg";
        TenantContext.setTenantId("test");
        createBrandDTO = new BrandCreateDTO();
        createBrandDTO.setName(RandomUtils.randomString())
                .setSort(RandomUtils.randomInteger())
                .setEnabled(new Boolean(true))
                .setType(RandomUtils.randomString())
                .setLogo(imgUrl);
        updateBrandDTO = new BrandUpdateDTO();
        updateBrandDTO.setName(RandomUtils.randomString())
                .setSort(RandomUtils.randomInteger())
                .setEnabled(new Boolean(true))
                .setType(RandomUtils.randomString())
                .setLogo(imgUrl);
        BrandBO brandBO = brandApplicationService.create(createBrandDTO);
        brandMock = brandDomainService.findById(new BrandId(brandBO.getId()));
    }

    private void assertEquals(BrandCreateDTO expected, BrandBO actual, boolean... condition) {
        Assertions.assertTrue(Objects.nonNull(actual.getId()) &&
                expected.getName().equals(actual.getName()) &&
                expected.getLogo().equals(actual.getLogo()) &&
                expected.getType().equals(actual.getType()) &&
                expected.getSort().intValue() == actual.getSort() &&
                expected.getEnabled().booleanValue() == actual.isEnabled() &&
                Objects.nonNull(actual.getCreateTime())&&
                ConditionUtil.booleanValue(condition), "创建品牌失败");
    }
    private void assertEquals(BrandUpdateDTO expected, BrandBO actual, boolean... condition) {
        Assertions.assertTrue(Objects.nonNull(actual.getId()) &&
                expected.getName().equals(actual.getName()) &&
                expected.getLogo().equals(actual.getLogo()) &&
                expected.getType().equals(actual.getType()) &&
                expected.getSort().intValue() == actual.getSort() &&
                expected.getEnabled().booleanValue() == actual.isEnabled() &&
                Objects.nonNull(actual.getCreateTime())&&
                ConditionUtil.booleanValue(condition), "创建品牌失败");
    }
    private void assertEquals(Brand expected, BrandBO actual, boolean... condition) {
        Assertions.assertTrue(Objects.nonNull(actual.getId()) &&
                expected.getName().equals(actual.getName()) &&
                expected.getLogo().equals(actual.getLogo()) &&
                expected.getType().equals(actual.getType()) &&
                expected.getSort() == actual.getSort() &&
                expected.isEnabled()== actual.isEnabled() &&
                Objects.nonNull(actual.getCreateTime())&&
                ConditionUtil.booleanValue(condition), "创建品牌失败");
    }

}
