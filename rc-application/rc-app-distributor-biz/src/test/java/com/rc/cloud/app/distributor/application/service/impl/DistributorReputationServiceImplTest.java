package com.rc.cloud.app.distributor.application.service.impl;

import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorReputationCreateReqVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorReputationPageReqVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorReputationUpdateReqVO;
import com.rc.cloud.app.distributor.application.service.DistributorReputationService;
import com.rc.cloud.app.distributor.infrastructure.persistence.mapper.DistributorReputationMapper;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorReputationPO;
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
        DistributorReputationCreateReqVO reqVO = randomPojo(DistributorReputationCreateReqVO.class, o -> {});
        // 调用
        String reputationId = reputationService.createReputation(reqVO);
        // 断言
        assertNotNull(reputationId);
        // 校验记录的属性是否正确
        DistributorReputationPO reputationDO = reputationMapper.selectById(reputationId);
        assertPojoEquals(reqVO, reputationDO);
    }

    @Test
    void updateReputation() {
        // mock 数据
        DistributorReputationPO reputationDO = randomDistributorReputationPO();
        reputationMapper.insert(reputationDO);// @Sql: 先插入出一条存在的数据
        // 准备参数
        DistributorReputationUpdateReqVO reqVO = randomPojo(DistributorReputationUpdateReqVO.class, o -> {
            // 设置更新的 ID
            o.setId(reputationDO.getId());
        });

        // 调用
        reputationService.updateReputation(reqVO);
        // 校验是否更新正确
        DistributorReputationPO reputation = reputationMapper.selectById(reqVO.getId());
        assertPojoEquals(reqVO, reputation);
    }

    @Test
    void deleteReputation() {
        // mock 数据
        DistributorReputationPO postDO =  randomDistributorReputationPO();
        reputationMapper.insert(postDO);
        // 准备参数
        String id = postDO.getId();

        // 调用
        reputationService.deleteReputation(id);
        assertNull(reputationMapper.selectById(id));
    }

    @Test
    void getReputation() {
        // mock 数据
        DistributorReputationPO reputationDO = randomDistributorReputationPO();
        reputationMapper.insert(reputationDO);
        // 准备参数
        String id = reputationDO.getId();
        // 调用
        DistributorReputationPO reputation = reputationService.getReputation(id);
        // 断言
        assertNotNull(reputation);
        assertPojoEquals(reputationDO, reputation);
    }

    @Test
    void getReputationList() {
        // mock 数据
        DistributorReputationPO postDO01 = randomDistributorReputationPO();
        reputationMapper.insert(postDO01);

        DistributorReputationPO postDO02 = randomDistributorReputationPO();
        reputationMapper.insert(postDO02);
        // 准备参数
        List<String> ids = Arrays.asList(postDO01.getId(), postDO02.getId());

        // 调用
        List<DistributorReputationPO> list = reputationService.getReputationList(ids);
        // 断言
        assertEquals(2, list.size());
        assertPojoEquals(postDO01, list.get(0));
    }

    @Test
    void getReputationPage() {
        // mock 数据
        DistributorReputationPO reputationDO = randomPojo(DistributorReputationPO.class, o -> {
            o.setName("码仔");
        });
        reputationMapper.insert(reputationDO);
        // 测试 name 不匹配
        reputationMapper.insert(cloneIgnoreId(reputationDO, o -> o.setName("程序员")));
        // 测试 status 不匹配
        reputationMapper.insert(cloneIgnoreId(reputationDO, o -> o.setName("程序员2")));
        // 准备参数
        DistributorReputationPageReqVO reqVO = new DistributorReputationPageReqVO();
        reqVO.setName("码");

        // 调用
        PageResult<DistributorReputationPO> pageResult = reputationService.getReputationPage(reqVO);
        // 断言
        assertEquals(1, pageResult.getTotal());
        assertEquals(1, pageResult.getList().size());
        assertPojoEquals(reputationDO, pageResult.getList().get(0));
    }
    @SafeVarargs
    private static DistributorReputationPO randomDistributorReputationPO(Consumer<DistributorReputationPO>... consumers) {
        Consumer<DistributorReputationPO> consumer = (o) -> {
            //o.set(randomCommonStatus()); // 保证 status 的范围
        };
        return randomPojo(DistributorReputationPO.class, ArrayUtils.append(consumer, consumers));
    }
}