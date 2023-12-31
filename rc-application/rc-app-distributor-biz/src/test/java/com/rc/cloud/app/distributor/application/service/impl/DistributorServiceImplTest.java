package com.rc.cloud.app.distributor.application.service.impl;

import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorContactCreateReqVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorCreateReqVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorPageReqVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorUpdateReqVO;
import com.rc.cloud.app.distributor.application.service.DistributorContactService;
import com.rc.cloud.app.distributor.application.service.DistributorService;
import com.rc.cloud.app.distributor.infrastructure.config.DistributorAutoConfig;
import com.rc.cloud.app.distributor.infrastructure.persistence.mapper.DistributorContactMapper;
import com.rc.cloud.app.distributor.infrastructure.persistence.mapper.DistributorDetailMapper;
import com.rc.cloud.app.distributor.infrastructure.persistence.mapper.DistributorMapper;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorContactPO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorPO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorDetailPO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import com.rc.cloud.common.test.core.ut.BaseDbUnitTest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.rc.cloud.app.distributor.infrastructure.config.DistributorErrorCodeConstants.DISTRIBUTOR_NOT_EXISTS;
import static com.rc.cloud.common.test.core.util.AssertUtils.assertPojoEquals;
import static com.rc.cloud.common.test.core.util.AssertUtils.assertServiceException;
import static com.rc.cloud.common.test.core.util.RandomUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;

/**
 * @author WJF
 * @create 2023-06-28 15:45
 * @description TODO
 */
@Import({DistributorServiceImpl.class, DistributorContactServiceImpl.class, DistributorAutoConfig.class})
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
        DistributorContactCreateReqVO reqVO1 = randomPojo(DistributorContactCreateReqVO.class, o -> {
            o.setMobile("13700000111");
        });
        DistributorContactCreateReqVO reqVO2 = randomPojo(DistributorContactCreateReqVO.class, o -> {
            o.setMobile("13700000222");
        });
        List<DistributorContactCreateReqVO> voList = Arrays.asList(reqVO1, reqVO2);
        DistributorCreateReqVO reqVO = randomPojo(DistributorCreateReqVO.class, o -> {
            o.setContacts(voList);
            o.setLocking(0);
        });
        String id = distributorService.create(reqVO);
        //断言
        Assert.assertNotNull(id);
        DistributorDetailPO detailDO = detailMapper.selectOne(new LambdaQueryWrapperX<DistributorDetailPO>()
                .eq(DistributorDetailPO::getDistributorId, id));
        //判断明细表
        Assert.assertEquals(reqVO.getDistributorDetail(), detailDO.getDistributorDetail());

        List<DistributorContactPO> contactDOList = contactMapper.selectList(new LambdaQueryWrapperX<DistributorContactPO>()
                .eq(DistributorContactPO::getDistributorId, id));
        //判断联系人
        assertEquals("13700000111", contactDOList.get(0).getMobile());
        assertNotNull(contactDOList.get(0).getPassword());
        assertEquals("13700000222", contactDOList.get(1).getMobile());
    }

    @Test
    void update() {
        // mock 数据
        DistributorPO distributorPO = randomPojo(DistributorPO.class, o -> {

        });
        distributorMapper.insert(distributorPO);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Random random = new Random();
        DistributorUpdateReqVO reqVO = randomPojo(DistributorUpdateReqVO.class, o -> {
            // 设置更新的 ID
            o.setId(distributorPO.getId());
            o.getContacts().forEach(x -> x.setMobile(
                    //生成随机手机号
                    "13575" + String.format("%06d", random.nextInt(10000)
                    )));
        });

        // 调用
        distributorService.update(reqVO);
        // 校验是否更新正确
        DistributorPO distributorPO1 = distributorMapper.selectById(reqVO.getId());
        assertPojoEquals(reqVO, distributorPO1);

        //检验插入明细为空
        DistributorUpdateReqVO reqVO1 = randomPojo(DistributorUpdateReqVO.class, o -> {
            // 设置更新的 ID
            o.setId(distributorPO.getId());
            o.setDistributorDetail("");
            o.getContacts().forEach(x -> x.setMobile(
                    //生成随机手机号
                    "13575" + String.format("%06d", random.nextInt(10000)
                    )));
        });
        // 调用
        distributorService.update(reqVO1);
        // 校验是否更新正确
        DistributorDetailPO detailDO = detailMapper.selectOne(new LambdaQueryWrapperX<DistributorDetailPO>().eq(DistributorDetailPO::getDistributorId, distributorPO.getId()));
        assertEquals("", detailDO.getDistributorDetail());
    }

    @Test
    void delete() {
        //mock对象
        DistributorContactCreateReqVO reqVO1 = randomPojo(DistributorContactCreateReqVO.class, o -> {
            o.setMobile("13700000111");
        });
        DistributorContactCreateReqVO reqVO2 = randomPojo(DistributorContactCreateReqVO.class, o -> {
            o.setMobile("13700000222");
        });
        List<DistributorContactCreateReqVO> voList = Arrays.asList(reqVO1, reqVO2);
        DistributorCreateReqVO reqVO = randomPojo(DistributorCreateReqVO.class, o -> {
            o.setContacts(voList);
            o.setLocking(0);
        });
        String id = distributorService.create(reqVO);

        //删除
        distributorService.delete(id);
        DistributorPO distributorPO = distributorMapper.selectById(id);
        assertNull(distributorPO);

        //获取明细
        // 调用，并断言异常
        assertServiceException(() -> distributorService.getDetail(id), DISTRIBUTOR_NOT_EXISTS);

    }

    @Test
    void get() {
        //mock对象
        DistributorContactCreateReqVO reqVO1 = randomPojo(DistributorContactCreateReqVO.class, o -> {
            o.setMobile("13700000111");
        });
        DistributorContactCreateReqVO reqVO2 = randomPojo(DistributorContactCreateReqVO.class, o -> {
            o.setMobile("13700000222");
        });
        List<DistributorContactCreateReqVO> voList = Arrays.asList(reqVO1, reqVO2);
        DistributorCreateReqVO reqVO = randomPojo(DistributorCreateReqVO.class, o -> {
            o.setContacts(voList);
            o.setLocking(0);
        });
        String id = distributorService.create(reqVO);

        DistributorPO distributorPO = distributorService.get(id);
        assertNotNull(distributorPO);
    }

    @Test
    void getDetail() {

        //mock对象
        DistributorContactCreateReqVO reqVO1 = randomPojo(DistributorContactCreateReqVO.class, o -> {
            o.setMobile("13700000111");
        });
        DistributorContactCreateReqVO reqVO2 = randomPojo(DistributorContactCreateReqVO.class, o -> {
            o.setMobile("13700000222");
        });
        List<DistributorContactCreateReqVO> voList = Arrays.asList(reqVO1, reqVO2);
        DistributorCreateReqVO reqVO = randomPojo(DistributorCreateReqVO.class, o -> {
            o.setContacts(voList);
            o.setLocking(0);
            o.setDistributorDetail("10086");
        });
        String id = distributorService.create(reqVO);

        DistributorDetailPO detail = distributorService.getDetail(id);
        assertNotNull(detail);
        assertEquals("10086", detail.getDistributorDetail());
    }

    @Test
    void getList() {
        //mock对象
        DistributorContactCreateReqVO reqVO1 = randomPojo(DistributorContactCreateReqVO.class, o -> {
            o.setMobile("13700000111");
        });
        DistributorContactCreateReqVO reqVO2 = randomPojo(DistributorContactCreateReqVO.class, o -> {
            o.setMobile("13700000222");
        });
        List<DistributorContactCreateReqVO> voList = Arrays.asList(reqVO1, reqVO2);
        DistributorCreateReqVO reqVO = randomPojo(DistributorCreateReqVO.class, o -> {
            o.setContacts(voList);
            o.setLocking(0);
            o.setDistributorDetail("10086");
        });
        String id = distributorService.create(reqVO);

        DistributorContactCreateReqVO reqVO3 = randomPojo(DistributorContactCreateReqVO.class, o -> {
            o.setMobile("13700000333");
        });
        DistributorContactCreateReqVO reqVO4 = randomPojo(DistributorContactCreateReqVO.class, o -> {
            o.setMobile("13700000444");
        });
        List<DistributorContactCreateReqVO> voList2 = Arrays.asList(reqVO3, reqVO4);
        DistributorCreateReqVO reqVO5 = randomPojo(DistributorCreateReqVO.class, o -> {
            o.setContacts(voList2);
            o.setLocking(0);
            o.setDistributorDetail("10087");
        });
        String id2 = distributorService.create(reqVO5);

        List<DistributorPO> list = distributorService.getList(Arrays.asList(id, id2));

        assertEquals(2,list.stream().count());
        assertEquals(id,list.get(0).getId());
    }

    @Test
    void getPage() {
        //mock对象
        DistributorContactCreateReqVO reqVO1 = randomPojo(DistributorContactCreateReqVO.class, o -> {
            o.setMobile("13700000111");
        });
        DistributorContactCreateReqVO reqVO2 = randomPojo(DistributorContactCreateReqVO.class, o -> {
            o.setMobile("13700000222");
        });
        List<DistributorContactCreateReqVO> voList = Arrays.asList(reqVO1, reqVO2);
        DistributorCreateReqVO reqVO = randomPojo(DistributorCreateReqVO.class, o -> {
            o.setContacts(voList);
            o.setLocking(0);
            o.setDistributorDetail("10086");
            o.setCompanyName("66666");
        });
        String id = distributorService.create(reqVO);

        DistributorContactCreateReqVO reqVO3 = randomPojo(DistributorContactCreateReqVO.class, o -> {
            o.setMobile("13700000333");
        });
        DistributorContactCreateReqVO reqVO4 = randomPojo(DistributorContactCreateReqVO.class, o -> {
            o.setMobile("13700000444");
        });
        List<DistributorContactCreateReqVO> voList2 = Arrays.asList(reqVO3, reqVO4);
        DistributorCreateReqVO reqVO5 = randomPojo(DistributorCreateReqVO.class, o -> {
            o.setContacts(voList2);
            o.setLocking(0);
            o.setDistributorDetail("10087");
            o.setCompanyName("77777");
        });
        String id2 = distributorService.create(reqVO5);

        DistributorPageReqVO pageReqVO=new DistributorPageReqVO();
        pageReqVO.setMobile("444");
        PageResult<DistributorPO> page = distributorService.getPage(pageReqVO);

        assertEquals(1,page.getTotal());
        assertEquals("77777",page.getList().get(0).getCompanyName());
    }

    @Test
    void testGetList() {
    }
}