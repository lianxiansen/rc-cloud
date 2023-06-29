package com.rc.cloud.app.distributor.application.service.impl;

import com.rc.cloud.app.distributor.appearance.req.AppDistributorChannelCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorChannelPageReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorChannelUpdateReqVO;
import com.rc.cloud.app.distributor.application.service.DistributorChannelService;
import com.rc.cloud.app.distributor.infrastructure.persistence.mapper.DistributorChannelMapper;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorChannelDO;
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
        AppDistributorChannelCreateReqVO reqVO = randomPojo(AppDistributorChannelCreateReqVO.class, o -> {});
        // 调用
        Long channelId = channelService.createChannel(reqVO);
        // 断言
        assertNotNull(channelId);
        // 校验记录的属性是否正确
        DistributorChannelDO channelDO = channelMapper.selectById(channelId);
        assertPojoEquals(reqVO, channelDO);
    }

    @Test
    void updateChannel() {
        // mock 数据
        DistributorChannelDO channelDO = randomDistributorChannelDO();
        channelMapper.insert(channelDO);// @Sql: 先插入出一条存在的数据
        // 准备参数
        AppDistributorChannelUpdateReqVO reqVO = randomPojo(AppDistributorChannelUpdateReqVO.class, o -> {
            // 设置更新的 ID
            o.setId(channelDO.getId());
        });

        // 调用
        channelService.updateChannel(reqVO);
        // 校验是否更新正确
        DistributorChannelDO channel = channelMapper.selectById(reqVO.getId());
        assertPojoEquals(reqVO, channel);
    }

    @Test
    void deleteChannel() {
        // mock 数据
        DistributorChannelDO postDO =  randomDistributorChannelDO();
        channelMapper.insert(postDO);
        // 准备参数
        Long id = postDO.getId();

        // 调用
        channelService.deleteChannel(id);
        assertNull(channelMapper.selectById(id));
    }

    @Test
    void getChannel() {
        // mock 数据
        DistributorChannelDO channelDO = randomDistributorChannelDO();
        channelMapper.insert(channelDO);
        // 准备参数
        Long id = channelDO.getId();
        // 调用
        DistributorChannelDO channel = channelService.getChannel(id);
        // 断言
        assertNotNull(channel);
        assertPojoEquals(channelDO, channel);
    }

    @Test
    void getChannelList() {
        // mock 数据
        DistributorChannelDO postDO01 = randomDistributorChannelDO();
        channelMapper.insert(postDO01);

        DistributorChannelDO postDO02 = randomDistributorChannelDO();
        channelMapper.insert(postDO02);
        // 准备参数
        List<Long> ids = Arrays.asList(postDO01.getId(), postDO02.getId());

        // 调用
        List<DistributorChannelDO> list = channelService.getChannelList(ids);
        // 断言
        assertEquals(2, list.size());
        assertPojoEquals(postDO01, list.get(0));
    }

    @Test
    void getChannelPage() {
        // mock 数据
        DistributorChannelDO channelDO = randomPojo(DistributorChannelDO.class, o -> {
            o.setName("码仔");
        });
        channelMapper.insert(channelDO);
        // 测试 name 不匹配
        channelMapper.insert(cloneIgnoreId(channelDO, o -> o.setName("程序员")));
        // 测试 status 不匹配
        channelMapper.insert(cloneIgnoreId(channelDO, o -> o.setName("程序员2")));
        // 准备参数
        AppDistributorChannelPageReqVO reqVO = new AppDistributorChannelPageReqVO();
        reqVO.setName("码");

        // 调用
        PageResult<DistributorChannelDO> pageResult = channelService.getChannelPage(reqVO);
        // 断言
        assertEquals(1, pageResult.getTotal());
        assertEquals(1, pageResult.getList().size());
        assertPojoEquals(channelDO, pageResult.getList().get(0));
    }

    @SafeVarargs
    private static DistributorChannelDO randomDistributorChannelDO(Consumer<DistributorChannelDO>... consumers) {
        Consumer<DistributorChannelDO> consumer = (o) -> {
            //o.set(randomCommonStatus()); // 保证 status 的范围
        };
        return randomPojo(DistributorChannelDO.class, ArrayUtils.append(consumer, consumers));
    }
}