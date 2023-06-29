package com.rc.cloud.app.distributor.application.service.impl;

import com.rc.cloud.app.distributor.appearance.req.AppDistributorSourceCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorSourcePageReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorSourceUpdateReqVO;
import com.rc.cloud.app.distributor.application.service.DistributorSourceService;
import com.rc.cloud.app.distributor.infrastructure.persistence.mapper.DistributorSourceMapper;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorSourceDO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.util.collection.ArrayUtils;
import com.rc.cloud.common.test.core.ut.BaseDbUnitTest;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static com.rc.cloud.common.core.util.object.ObjectUtils.cloneIgnoreId;
import static com.rc.cloud.common.test.core.util.AssertUtils.assertPojoEquals;
import static com.rc.cloud.common.test.core.util.RandomUtils.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author WJF
 * @create 2023-06-28 13:08
 * @description TODO
 */
@Import(DistributorSourceServiceImpl.class)
class DistributorSourceServiceImplTest extends BaseDbUnitTest{

    @Resource
    private DistributorSourceService sourceService;

    @Resource
    private DistributorSourceMapper sourceMapper;
    
    @Test
    void createSource() {
        AppDistributorSourceCreateReqVO reqVO = randomPojo(AppDistributorSourceCreateReqVO.class, o -> {});
        // 调用
        Long sourceId = sourceService.createSource(reqVO);
        // 断言
        assertNotNull(sourceId);
        // 校验记录的属性是否正确
        DistributorSourceDO sourceDO = sourceMapper.selectById(sourceId);
        assertPojoEquals(reqVO, sourceDO);
    }

    @Test
    void updateSource() {
        // mock 数据
        DistributorSourceDO sourceDO = randomDistributorSourceDO();
        sourceMapper.insert(sourceDO);// @Sql: 先插入出一条存在的数据
        // 准备参数
        AppDistributorSourceUpdateReqVO reqVO = randomPojo(AppDistributorSourceUpdateReqVO.class, o -> {
            // 设置更新的 ID
            o.setId(sourceDO.getId());
        });

        // 调用
        sourceService.updateSource(reqVO);
        // 校验是否更新正确
        DistributorSourceDO source = sourceMapper.selectById(reqVO.getId());
        assertPojoEquals(reqVO, source);
    }

    @Test
    void deleteSource() {
        // mock 数据
        DistributorSourceDO postDO =  randomDistributorSourceDO();
        sourceMapper.insert(postDO);
        // 准备参数
        Long id = postDO.getId();

        // 调用
        sourceService.deleteSource(id);
        assertNull(sourceMapper.selectById(id));
    }

    @Test
    void getSource() {
        // mock 数据
        DistributorSourceDO sourceDO = randomDistributorSourceDO();
        sourceMapper.insert(sourceDO);
        // 准备参数
        Long id = sourceDO.getId();
        // 调用
        DistributorSourceDO source = sourceService.getSource(id);
        // 断言
        assertNotNull(source);
        assertPojoEquals(sourceDO, source);
    }

    @Test
    void getSourceList() {
        // mock 数据
        DistributorSourceDO postDO01 = randomDistributorSourceDO();
        sourceMapper.insert(postDO01);

        DistributorSourceDO postDO02 = randomDistributorSourceDO();
        sourceMapper.insert(postDO02);
        // 准备参数
        List<Long> ids = Arrays.asList(postDO01.getId(), postDO02.getId());

        // 调用
        List<DistributorSourceDO> list = sourceService.getSourceList(ids);
        // 断言
        assertEquals(2, list.size());
        assertPojoEquals(postDO01, list.get(0));
    }

    @Test
    void getSourcePage() {
        // mock 数据
        DistributorSourceDO sourceDO = randomPojo(DistributorSourceDO.class, o -> {
            o.setName("码仔");
        });
        sourceMapper.insert(sourceDO);
        // 测试 name 不匹配
        sourceMapper.insert(cloneIgnoreId(sourceDO, o -> o.setName("程序员")));
        // 测试 status 不匹配
        sourceMapper.insert(cloneIgnoreId(sourceDO, o -> o.setName("程序员2")));
        // 准备参数
        AppDistributorSourcePageReqVO reqVO = new AppDistributorSourcePageReqVO();
        reqVO.setName("码");

        // 调用
        PageResult<DistributorSourceDO> pageResult = sourceService.getSourcePage(reqVO);
        // 断言
        assertEquals(1, pageResult.getTotal());
        assertEquals(1, pageResult.getList().size());
        assertPojoEquals(sourceDO, pageResult.getList().get(0));
    }
    @SafeVarargs
    private static DistributorSourceDO randomDistributorSourceDO(Consumer<DistributorSourceDO>... consumers) {
        Consumer<DistributorSourceDO> consumer = (o) -> {
            //o.set(randomCommonStatus()); // 保证 status 的范围
        };
        return randomPojo(DistributorSourceDO.class, ArrayUtils.append(consumer, consumers));
    }
}