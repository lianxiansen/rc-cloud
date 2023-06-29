package com.rc.cloud.app.distributor.application.service.impl;

import com.rc.cloud.app.distributor.appearance.req.AppDistributorContactCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorLevelUpdateReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorUpdateReqVO;
import com.rc.cloud.app.distributor.application.convert.DistributorContactConvert;
import com.rc.cloud.app.distributor.application.service.DistributorContactService;
import com.rc.cloud.app.distributor.application.service.DistributorService;
import com.rc.cloud.app.distributor.infrastructure.config.DistributorAutoConfig;
import com.rc.cloud.app.distributor.infrastructure.persistence.mapper.DistributorContactMapper;
import com.rc.cloud.app.distributor.infrastructure.persistence.mapper.DistributorDetailMapper;
import com.rc.cloud.app.distributor.infrastructure.persistence.mapper.DistributorMapper;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorContactDO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorDO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorDetailDO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorLevelDO;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.core.util.collection.CollectionUtils;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import com.rc.cloud.common.test.core.ut.BaseDbUnitTest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.rc.cloud.app.distributor.infrastructure.config.DistributorErrorCodeConstants.DISTRIBUTOR_NOT_EXISTS;
import static com.rc.cloud.common.test.core.util.AssertUtils.assertPojoEquals;
import static com.rc.cloud.common.test.core.util.AssertUtils.assertServiceException;
import static com.rc.cloud.common.test.core.util.RandomUtils.randomPojo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

/**
 * @author WJF
 * @create 2023-06-28 15:45
 * @description TODO
 */
@Import({DistributorServiceImpl.class,DistributorContactServiceImpl.class, DistributorAutoConfig.class})
class DistributorServiceImplTest extends BaseDbUnitTest {

    @Resource
    private DistributorService distributorService;

    @Resource
    private DistributorMapper distributorMapper;

    @Resource
    private DistributorContactService contactService;

    @Resource
    private DistributorContactMapper contactMapper;

    @Resource
    private DistributorDetailMapper detailMapper;

    @Test
    void create() {
        //mock对象
        AppDistributorContactCreateReqVO reqVO1=randomPojo(AppDistributorContactCreateReqVO.class, o->{
            o.setMobile("13700000111");
        });
        AppDistributorContactCreateReqVO reqVO2=randomPojo(AppDistributorContactCreateReqVO.class, o->{
            o.setMobile("13700000222");
        });
        List<AppDistributorContactCreateReqVO> voList= Arrays.asList(reqVO1,reqVO2);
        AppDistributorCreateReqVO reqVO=randomPojo(AppDistributorCreateReqVO.class,o->{
            o.setContacts(voList);
            o.setLocking(0);
        });
        Long id = distributorService.create(reqVO);
        //断言
        Assert.assertNotNull(id);
        DistributorDetailDO detailDO = detailMapper.selectOne(new LambdaQueryWrapperX<DistributorDetailDO>()
                .eq(DistributorDetailDO::getDistributorId, id));
        //判断明细表
        Assert.assertEquals(reqVO.getDistributorDetail(),detailDO.getDistributorDetail());

        List<DistributorContactDO> contactDOList = contactMapper.selectList(new LambdaQueryWrapperX<DistributorContactDO>()
                .eq(DistributorContactDO::getDistributorId, id));
        //判断联系人
        assertEquals("13700000111", contactDOList.get(0).getMobile());
        assertNotNull(contactDOList.get(0).getPassword());
        assertEquals("13700000222", contactDOList.get(1).getMobile());
    }

    @Test
    void update() {
        // mock 数据
        DistributorDO distributorDO = randomPojo(DistributorDO.class,o->{

        });
        distributorMapper.insert(distributorDO);// @Sql: 先插入出一条存在的数据
        // 准备参数
        AppDistributorUpdateReqVO reqVO = randomPojo(AppDistributorUpdateReqVO.class, o -> {
            // 设置更新的 ID
            o.setId(distributorDO.getId());
        });

        // 调用
        distributorService.update(reqVO);
        // 校验是否更新正确
        DistributorDO distributorDO1 = distributorMapper.selectById(reqVO.getId());
        assertPojoEquals(reqVO, distributorDO1);

        //检验插入明细为空
        AppDistributorUpdateReqVO reqVO1 = randomPojo(AppDistributorUpdateReqVO.class, o -> {
            // 设置更新的 ID
            o.setId(distributorDO.getId());
            o.setDistributorDetail("");
        });
        // 调用
        distributorService.update(reqVO1);
        // 校验是否更新正确
        DistributorDetailDO detailDO = detailMapper.selectOne(new LambdaQueryWrapperX<DistributorDetailDO>().eq(DistributorDetailDO::getDistributorId,distributorDO.getId()));
        assertEquals("", detailDO.getDistributorDetail());
    }

    @Test
    void delete() {
        //mock对象
        AppDistributorContactCreateReqVO reqVO1=randomPojo(AppDistributorContactCreateReqVO.class, o->{
            o.setMobile("13700000111");
        });
        AppDistributorContactCreateReqVO reqVO2=randomPojo(AppDistributorContactCreateReqVO.class, o->{
            o.setMobile("13700000222");
        });
        List<AppDistributorContactCreateReqVO> voList= Arrays.asList(reqVO1,reqVO2);
        AppDistributorCreateReqVO reqVO=randomPojo(AppDistributorCreateReqVO.class,o->{
            o.setContacts(voList);
            o.setLocking(0);
        });
        Long id = distributorService.create(reqVO);

        //删除
        distributorService.delete(id);
        DistributorDO distributorDO = distributorMapper.selectById(id);
        assertNull(distributorDO);

        //获取明细
        // 调用，并断言异常
        assertServiceException(() ->distributorService.getDetail(id),DISTRIBUTOR_NOT_EXISTS);

    }

    @Test
    void get() {
    }

    @Test
    void getDetail() {
    }

    @Test
    void getList() {
    }

    @Test
    void getPage() {
    }

    @Test
    void testGetList() {
    }
}