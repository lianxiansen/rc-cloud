package com.rc.cloud.app.distributor.application.service.impl;

import com.rc.cloud.app.distributor.appearance.req.AppDistributorLevelCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorLevelPageReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorLevelUpdateReqVO;
import com.rc.cloud.app.distributor.application.service.DistributorLevelService;
import com.rc.cloud.app.distributor.infrastructure.persistence.mapper.DistributorLevelMapper;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorLevelDO;
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
import static com.rc.cloud.common.test.core.util.RandomUtils.randomPojo;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author WJF
 * @create 2023-06-28 13:09
 * @description TODO
 */
@Import(DistributorLevelServiceImpl.class)
class DistributorLevelServiceImplTest extends BaseDbUnitTest {
    
    @Resource
    private DistributorLevelService levelService;

    @Resource
    private DistributorLevelMapper levelMapper;
    @Test
    void createLevel() {
        AppDistributorLevelCreateReqVO reqVO = randomPojo(AppDistributorLevelCreateReqVO.class, o -> {});
        // 调用
        Long levelId = levelService.createLevel(reqVO);
        // 断言
        assertNotNull(levelId);
        // 校验记录的属性是否正确
        DistributorLevelDO levelDO = levelMapper.selectById(levelId);
        assertPojoEquals(reqVO, levelDO);
    }

    @Test
    void updateLevel() {
        // mock 数据
        DistributorLevelDO levelDO = randomDistributorLevelDO();
        levelMapper.insert(levelDO);// @Sql: 先插入出一条存在的数据
        // 准备参数
        AppDistributorLevelUpdateReqVO reqVO = randomPojo(AppDistributorLevelUpdateReqVO.class, o -> {
            // 设置更新的 ID
            o.setId(levelDO.getId());
        });

        // 调用
        levelService.updateLevel(reqVO);
        // 校验是否更新正确
        DistributorLevelDO level = levelMapper.selectById(reqVO.getId());
        assertPojoEquals(reqVO, level);
    }

    @Test
    void deleteLevel() {
        // mock 数据
        DistributorLevelDO postDO =  randomDistributorLevelDO();
        levelMapper.insert(postDO);
        // 准备参数
        Long id = postDO.getId();

        // 调用
        levelService.deleteLevel(id);
        assertNull(levelMapper.selectById(id));
    }

    @Test
    void getLevel() {
        // mock 数据
        DistributorLevelDO levelDO = randomDistributorLevelDO();
        levelMapper.insert(levelDO);
        // 准备参数
        Long id = levelDO.getId();
        // 调用
        DistributorLevelDO level = levelService.getLevel(id);
        // 断言
        assertNotNull(level);
        assertPojoEquals(levelDO, level);
    }

    @Test
    void getLevelList() {
        // mock 数据
        DistributorLevelDO postDO01 = randomDistributorLevelDO();
        levelMapper.insert(postDO01);

        DistributorLevelDO postDO02 = randomDistributorLevelDO();
        levelMapper.insert(postDO02);
        // 准备参数
        List<Long> ids = Arrays.asList(postDO01.getId(), postDO02.getId());

        // 调用
        List<DistributorLevelDO> list = levelService.getLevelList(ids);
        // 断言
        assertEquals(2, list.size());
        assertPojoEquals(postDO01, list.get(0));
    }

    @Test
    void getLevelPage() {
        // mock 数据
        DistributorLevelDO levelDO = randomPojo(DistributorLevelDO.class, o -> {
            o.setName("码仔");
        });
        levelMapper.insert(levelDO);
        // 测试 name 不匹配
        levelMapper.insert(cloneIgnoreId(levelDO, o -> o.setName("程序员")));
        // 测试 status 不匹配
        levelMapper.insert(cloneIgnoreId(levelDO, o -> o.setName("程序员2")));
        // 准备参数
        AppDistributorLevelPageReqVO reqVO = new AppDistributorLevelPageReqVO();
        reqVO.setName("码");

        // 调用
        PageResult<DistributorLevelDO> pageResult = levelService.getLevelPage(reqVO);
        // 断言
        assertEquals(1, pageResult.getTotal());
        assertEquals(1, pageResult.getList().size());
        assertPojoEquals(levelDO, pageResult.getList().get(0));
    }

    @SafeVarargs
    private static DistributorLevelDO randomDistributorLevelDO(Consumer<DistributorLevelDO>... consumers) {
        Consumer<DistributorLevelDO> consumer = (o) -> {
            //o.set(randomCommonStatus()); // 保证 status 的范围
        };
        return randomPojo(DistributorLevelDO.class, ArrayUtils.append(consumer, consumers));
    }
}