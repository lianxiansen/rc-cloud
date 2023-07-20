package com.rc.cloud.app.distributor.application.service.impl;

import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorChannelCreateReqVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorChannelPageReqVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorChannelUpdateReqVO;
import com.rc.cloud.app.distributor.application.service.DistributorChannelService;
import com.rc.cloud.app.distributor.infrastructure.persistence.mapper.DistributorChannelMapper;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorChannelPO;
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
 * @create 2023-06-28 8:34
 * @description TODO
 */

@Import(DistributorChannelServiceImpl.class)
class DistributorChannelServiceImplTest extends BaseDbUnitTest {

    @Resource
    private DistributorChannelService channelService;

    @Resource
    private DistributorChannelMapper channelMapper;

    @Test
    void createChannel() {
        DistributorChannelCreateReqVO reqVO = randomPojo(DistributorChannelCreateReqVO.class, o -> {});
        // 调用
        String channelId = channelService.createChannel(reqVO);
        // 断言
        assertNotNull(channelId);
        // 校验记录的属性是否正确
        DistributorChannelPO channelDO = channelMapper.selectById(channelId);
        assertPojoEquals(reqVO, channelDO);
    }

    @Test
    void updateChannel() {
        // mock 数据
        DistributorChannelPO channelDO = randomDistributorChannelPO();
        channelMapper.insert(channelDO);// @Sql: 先插入出一条存在的数据
        // 准备参数
        DistributorChannelUpdateReqVO reqVO = randomPojo(DistributorChannelUpdateReqVO.class, o -> {
            // 设置更新的 ID
            o.setId(channelDO.getId());
        });

        // 调用
        channelService.updateChannel(reqVO);
        // 校验是否更新正确
        DistributorChannelPO channel = channelMapper.selectById(reqVO.getId());
        assertPojoEquals(reqVO, channel);
    }

    @Test
    void deleteChannel() {
        // mock 数据
        DistributorChannelPO postDO =  randomDistributorChannelPO();
        channelMapper.insert(postDO);
        // 准备参数
        String id = postDO.getId();

        // 调用
        channelService.deleteChannel(id);
        assertNull(channelMapper.selectById(id));
    }

    @Test
    void getChannel() {
        // mock 数据
        DistributorChannelPO channelDO = randomDistributorChannelPO();
        channelMapper.insert(channelDO);
        // 准备参数
        String id = channelDO.getId();
        // 调用
        DistributorChannelPO channel = channelService.getChannel(id);
        // 断言
        assertNotNull(channel);
        assertPojoEquals(channelDO, channel);
    }

    @Test
    void getChannelList() {
        // mock 数据
        DistributorChannelPO postDO01 = randomDistributorChannelPO();
        channelMapper.insert(postDO01);

        DistributorChannelPO postDO02 = randomDistributorChannelPO();
        channelMapper.insert(postDO02);
        // 准备参数
        List<String> ids = Arrays.asList(postDO01.getId(), postDO02.getId());

        // 调用
        List<DistributorChannelPO> list = channelService.getChannelList(ids);
        // 断言
        assertEquals(2, list.size());
        assertPojoEquals(postDO01, list.get(0));
    }

    @Test
    void getChannelPage() {
        // mock 数据
        DistributorChannelPO channelDO = randomPojo(DistributorChannelPO.class, o -> {
            o.setName("码仔");
        });
        channelMapper.insert(channelDO);
        // 测试 name 不匹配
        channelMapper.insert(cloneIgnoreId(channelDO, o -> o.setName("程序员")));
        // 测试 status 不匹配
        channelMapper.insert(cloneIgnoreId(channelDO, o -> o.setName("程序员2")));
        // 准备参数
        DistributorChannelPageReqVO reqVO = new DistributorChannelPageReqVO();
        reqVO.setName("码");

        // 调用
        PageResult<DistributorChannelPO> pageResult = channelService.getChannelPage(reqVO);
        // 断言
        assertEquals(1, pageResult.getTotal());
        assertEquals(1, pageResult.getList().size());
        assertPojoEquals(channelDO, pageResult.getList().get(0));
    }

    @SafeVarargs
    private static DistributorChannelPO randomDistributorChannelPO(Consumer<DistributorChannelPO>... consumers) {
        Consumer<DistributorChannelPO> consumer = (o) -> {
            //o.set(randomCommonStatus()); // 保证 status 的范围
        };
        return randomPojo(DistributorChannelPO.class, ArrayUtils.append(consumer, consumers));
    }
}