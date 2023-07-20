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
 */
@Import({BrandApplicationService.class, LocalIdRepositoryImpl.class, BrandDomainService.class, BrandRepositoryImpl.class, ProductRepositoryImpl.class})
@DisplayName("品牌单元测试")
public class BrandApplicationServiceUnitTest extends BaseDbUnitTest {
    @Autowired
    private BrandApplicationService brandApplicationService;

    @Autowired
    private BrandDomainService brandDomainService;
    @Resource
    private IdRepository idRepository;
    @Autowired
    private BrandRepository brandRepository;

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
        Brand brand = brandDomainService.findById(new BrandId(brandBO.getId()));
        Assertions.assertTrue(Objects.nonNull(brand) && createBrandDTO.getName().equals(brandBO.getName()) && createBrandDTO.getLogo().equals(brandBO.getLogo()) && createBrandDTO.getEnabled().booleanValue() == brandBO.isEnabled() && createBrandDTO.getSort().intValue() == brandBO.getSort() && createBrandDTO.getType().equals(brandBO.getType()), "创建品牌失败");
    }


    @Test
    @DisplayName("更新品牌")
    public void updateBrand() {
        updateBrandDTO.setId(brandMock.getId().id());
        BrandBO brandBO = brandApplicationService.update(updateBrandDTO);
        Assertions.assertTrue(brandMock.getId().id().equals(brandBO.getId()) && updateBrandDTO.getName().equals(brandBO.getName()) && updateBrandDTO.getLogo().equals(brandBO.getLogo()) && updateBrandDTO.getEnabled().booleanValue() == brandBO.isEnabled() && updateBrandDTO.getSort().intValue() == brandBO.getSort() && updateBrandDTO.getType().equals(brandBO.getType()), "更新品牌失败");
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

    @Autowired
    private BrandMapper brandMapper;

    @Test
    @DisplayName("分页检索品牌返回首页列表")
    public void selectPageResultReturnFirstPage() {
        brandMapper.delete(new LambdaQueryWrapperX<>());
        BrandQueryPageDTO queryBrandDTO = new BrandQueryPageDTO();
        queryBrandDTO.setPageNo(1);
        queryBrandDTO.setPageSize(10);
        long totalCount = 26;
        for (long i = 0; i < totalCount; i++) {
            brandApplicationService.create(createBrandDTO);
        }
        PageResult<BrandBO> brandVOPageResult = brandApplicationService.selectPageResult(queryBrandDTO);
        Assertions.assertTrue(brandVOPageResult.getTotal().longValue() == totalCount && brandVOPageResult.getList().size() == queryBrandDTO.getPageSize(), "分页检索品牌失败");
    }


    @Test
    @DisplayName("分页检索品牌返回末页列表")
    public void selectPageResultReturnLastPage() {
        brandMapper.delete(new LambdaQueryWrapperX<>());
        //分页检索品牌，每页大小为10，记录数小于10，指定页码1，返回页数1和全部记录数
        BrandQueryPageDTO queryBrandDTO = new BrandQueryPageDTO();
        queryBrandDTO.setPageNo(3);
        queryBrandDTO.setPageSize(10);
        long totalCount = 26;
        for (long i = 0; i < totalCount; i++) {
            brandApplicationService.create(createBrandDTO);
        }
        PageResult<BrandBO> brandVOPageResult = brandApplicationService.selectPageResult(queryBrandDTO);
        Assertions.assertTrue(brandVOPageResult.getTotal().longValue() == totalCount && brandVOPageResult.getList().size() == totalCount % queryBrandDTO.getPageSize(), "分页检索品牌失败");
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


}
