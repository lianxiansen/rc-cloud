package com.rc.cloud.app.distributor;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.autoconfigure.MybatisPlusJoinAutoConfiguration;
import com.rc.cloud.app.distributor.appearance.req.DistributorPageReqVO;
import com.rc.cloud.app.distributor.application.service.DistributorContactService;
import com.rc.cloud.app.distributor.infrastructure.config.DistributorAutoConfig;
import com.rc.cloud.app.distributor.infrastructure.persistence.mapper.DistributorMapper;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorContactPO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorPO;
import com.rc.cloud.common.mybatis.core.query.MPJLambdaWrapperX;
import com.rc.cloud.common.mybatis.core.util.MyBatisUtils;
import com.rc.cloud.common.tenant.core.context.TenantContextHolder;
import com.rc.cloud.common.test.annotation.RcTest;
import com.rc.cloud.common.test.core.ut.BaseDbUnitTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author WJF
 * @create 2023-06-27 16:43
 * @description TODO
 */
@RcTest
class DistributorApplicationTests{

    @Autowired
    DistributorMapper distributorMapper;

    @Autowired
    DistributorContactService contactService;
    @Test
    void contextLoads() {
        TenantContextHolder.setTenantId("1");
        // MyBatis Plus 关联表 查询
        DistributorPageReqVO reqVO =new DistributorPageReqVO();
        reqVO.setCompanyName("7");

        IPage<DistributorPO> mpPage = MyBatisUtils.buildPage(reqVO);
        IPage<DistributorPO> page = distributorMapper.selectJoinPage(mpPage, DistributorPO.class,
                ((MPJLambdaWrapperX<DistributorPO>) new MPJLambdaWrapperX<DistributorPO>()
                        .selectAll(DistributorPO.class)
                        .distinct()
                        .leftJoin(DistributorContactPO.class, DistributorContactPO::getDistributorId, DistributorPO::getId))
                        .likeIfPresent(DistributorContactPO::getMobile, reqVO.getMobile())
                        .likeIfPresent(DistributorContactPO::getName, reqVO.getContact())
                        .likeIfPresent(DistributorPO::getCompanyName, reqVO.getCompanyName())
                        .likeIfPresent(DistributorPO::getProvince, reqVO.getProvince())
                        .eqIfPresent(DistributorPO::getCity, reqVO.getCity())
                        .eqIfPresent(DistributorPO::getCounty, reqVO.getCounty())
                        .eqIfPresent(DistributorPO::getAddress, reqVO.getAddress())
                        .eqIfPresent(DistributorPO::getStartTime, reqVO.getStartTime())
                        .eqIfPresent(DistributorPO::getEndTime, reqVO.getEndTime())
                        .eqIfPresent(DistributorPO::getStatus, reqVO.getStatus())
                        .eqIfPresent(DistributorPO::getRemarks, reqVO.getRemarks())
                        .eqIfPresent(DistributorPO::getCreator, reqVO.getCreator())
                        .eqIfPresent(DistributorPO::getAdminId, reqVO.getAdminId())
                        .eqIfPresent(DistributorPO::getTelephone, reqVO.getTelephone())
                        .eqIfPresent(DistributorPO::getChannel, reqVO.getChannel())
                        .eqIfPresent(DistributorPO::getSource, reqVO.getSource())
                        .eqIfPresent(DistributorPO::getLevel, reqVO.getLevel())
                        .eqIfPresent(DistributorPO::getReputation, reqVO.getReputation())
                        .eqIfPresent(DistributorPO::getEstablishedTime, reqVO.getEstablishedTime())
                        .eqIfPresent(DistributorPO::getDeleted, reqVO.getDeleted())
                        .eqIfPresent(DistributorPO::getLocking, reqVO.getLocking())
                        .orderByDesc(DistributorPO::getId)
        );

        System.out.println(page);
    }

    //多租户测试
    @Test
    void testTenant(){
        TenantContextHolder.setTenantId("1");
        DistributorContactPO contactPO = contactService.getById("5");
        assertNull(contactPO);
        TenantContextHolder.setTenantId("2");
        contactPO = contactService.getById("5");
        assertNotNull(contactPO);
    }
}
