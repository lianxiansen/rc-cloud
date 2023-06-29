package com.rc.cloud.app.distributor.application.service.impl;

import com.rc.cloud.app.distributor.appearance.req.AppDistributorReputationCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorReputationPageReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorReputationUpdateReqVO;
import com.rc.cloud.app.distributor.application.service.DistributorReputationService;
import com.rc.cloud.app.distributor.infrastructure.persistence.mapper.DistributorReputationMapper;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorReputationDO;
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
 * @create 2023-06-28 13:09
 * @description TODO
 */
@Import(DistributorReputationServiceImpl.class)
class DistributorReputationServiceImplTest extends BaseDbUnitTest{

    @Resource
    private DistributorReputationService reputationService;

    @Resource
    private DistributorReputationMapper reputationMapper;
    
    @Test
    void createReputation() {
        AppDistributorReputationCreateReqVO reqVO = randomPojo(AppDistributorReputationCreateReqVO.class, o -> {});
        // 调用
        Long reputationId = reputationService.createReputation(reqVO);
        // 断言
        assertNotNull(reputationId);
        // 校验记录的属性是否正确
        DistributorReputationDO reputationDO = reputationMapper.selectById(reputationId);
        assertPojoEquals(reqVO, reputationDO);
    }

    @Test
    void updateReputation() {
        // mock 数据
        DistributorReputationDO reputationDO = randomDistributorReputationDO();
        reputationMapper.insert(reputationDO);// @Sql: 先插入出一条存在的数据
        // 准备参数
        AppDistributorReputationUpdateReqVO reqVO = randomPojo(AppDistributorReputationUpdateReqVO.class, o -> {
            // 设置更新的 ID
            o.setId(reputationDO.getId());
        });

        // 调用
        reputationService.updateReputation(reqVO);
        // 校验是否更新正确
        DistributorReputationDO reputation = reputationMapper.selectById(reqVO.getId());
        assertPojoEquals(reqVO, reputation);
    }

    @Test
    void deleteReputation() {
        // mock 数据
        DistributorReputationDO postDO =  randomDistributorReputationDO();
        reputationMapper.insert(postDO);
        // 准备参数
        Long id = postDO.getId();

        // 调用
        reputationService.deleteReputation(id);
        assertNull(reputationMapper.selectById(id));
    }

    @Test
    void getReputation() {
        // mock 数据
        DistributorReputationDO reputationDO = randomDistributorReputationDO();
        reputationMapper.insert(reputationDO);
        // 准备参数
        Long id = reputationDO.getId();
        // 调用
        DistributorReputationDO reputation = reputationService.getReputation(id);
        // 断言
        assertNotNull(reputation);
        assertPojoEquals(reputationDO, reputation);
    }

    @Test
    void getReputationList() {
        // mock 数据
        DistributorReputationDO postDO01 = randomDistributorReputationDO();
        reputationMapper.insert(postDO01);

        DistributorReputationDO postDO02 = randomDistributorReputationDO();
        reputationMapper.insert(postDO02);
        // 准备参数
        List<Long> ids = Arrays.asList(postDO01.getId(), postDO02.getId());

        // 调用
        List<DistributorReputationDO> list = reputationService.getReputationList(ids);
        // 断言
        assertEquals(2, list.size());
        assertPojoEquals(postDO01, list.get(0));
    }

    @Test
    void getReputationPage() {
        // mock 数据
        DistributorReputationDO reputationDO = randomPojo(DistributorReputationDO.class, o -> {
            o.setName("码仔");
        });
        reputationMapper.insert(reputationDO);
        // 测试 name 不匹配
        reputationMapper.insert(cloneIgnoreId(reputationDO, o -> o.setName("程序员")));
        // 测试 status 不匹配
        reputationMapper.insert(cloneIgnoreId(reputationDO, o -> o.setName("程序员2")));
        // 准备参数
        AppDistributorReputationPageReqVO reqVO = new AppDistributorReputationPageReqVO();
        reqVO.setName("码");

        // 调用
        PageResult<DistributorReputationDO> pageResult = reputationService.getReputationPage(reqVO);
        // 断言
        assertEquals(1, pageResult.getTotal());
        assertEquals(1, pageResult.getList().size());
        assertPojoEquals(reputationDO, pageResult.getList().get(0));
    }
    @SafeVarargs
    private static DistributorReputationDO randomDistributorReputationDO(Consumer<DistributorReputationDO>... consumers) {
        Consumer<DistributorReputationDO> consumer = (o) -> {
            //o.set(randomCommonStatus()); // 保证 status 的范围
        };
        return randomPojo(DistributorReputationDO.class, ArrayUtils.append(consumer, consumers));
    }
}