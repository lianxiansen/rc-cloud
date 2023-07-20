package com.rc.cloud.app.distributor.application.service.impl;

import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorContactUpdatePasswordReqVO;
import com.rc.cloud.app.distributor.application.service.DistributorContactService;
import com.rc.cloud.app.distributor.infrastructure.config.DistributorAutoConfig;
import com.rc.cloud.app.distributor.infrastructure.persistence.mapper.DistributorContactMapper;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorContactPO;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import com.rc.cloud.common.test.core.ut.BaseDbUnitTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

import java.util.Arrays;
import java.util.List;

import static com.rc.cloud.app.distributor.infrastructure.config.DistributorErrorCodeConstants.DISTRIBUTOR_CONTACT_PHONE_DUPLICATE;
import static com.rc.cloud.app.distributor.infrastructure.config.DistributorErrorCodeConstants.DISTRIBUTOR_CONTACT_PHONE_EXIST;
import static com.rc.cloud.common.test.core.util.AssertUtils.assertPojoEquals;
import static com.rc.cloud.common.test.core.util.AssertUtils.assertServiceException;
import static com.rc.cloud.common.test.core.util.RandomUtils.randomPojo;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author WJF
 * @create 2023-06-28 13:10
 * @description TODO
 */
@Import({DistributorContactServiceImpl.class, DistributorAutoConfig.class})
class DistributorContactServiceImplTest extends BaseDbUnitTest{

    @Autowired
    private PasswordEncoder webPasswordEncoder;

    @Resource
    private DistributorContactService contactService;

    @Resource
    private DistributorContactMapper contactMapper;

    @Test
    void updateContacts_success() {
        DistributorContactPO contact1 = randomPojo(DistributorContactPO.class, o->{
            o.setMobile("13700000998");
        });

        DistributorContactPO contact2 = randomPojo(DistributorContactPO.class, o->{
            o.setMobile("13700000999");
        });

        // 准备参数
        List<DistributorContactPO> contactDOS = Arrays.asList(contact1, contact2);
        contactService.updateContacts("1",contactDOS);
        List<DistributorContactPO> contactDOList = contactMapper.selectList(new LambdaQueryWrapperX<DistributorContactPO>()
                .eq(DistributorContactPO::getDistributorId, 1L));

        // 断言
        assertEquals(2, contactDOList.size());
        assertPojoEquals(contact1, contactDOList.get(0));

        assertEquals(true,webPasswordEncoder.matches("000998",contactDOList.get(0).getPassword()));

    }

    @Test
    void updateContacts_fail() {
        DistributorContactPO contact1 = randomPojo(DistributorContactPO.class, o->{
            o.setMobile("13700000003");
        });

        DistributorContactPO contact2 = randomPojo(DistributorContactPO.class, o->{
            o.setMobile("13700000999");
        });

        DistributorContactPO contact3 = randomPojo(DistributorContactPO.class, o->{
            o.setMobile("13700000999");
        });

        // 验证手机号重复
        final List<DistributorContactPO> contactDOS2 = Arrays.asList(contact2, contact3);
        assertServiceException(() -> contactService.updateContacts("1",contactDOS2), DISTRIBUTOR_CONTACT_PHONE_DUPLICATE);

        // 验证手机号被其他绑定
        final List<DistributorContactPO> contactDOS = Arrays.asList(contact1, contact2);
        assertServiceException(() -> contactService.updateContacts("1",contactDOS), DISTRIBUTOR_CONTACT_PHONE_EXIST);
    }

    @Test
    void getByDistributorId() {
        List<DistributorContactPO> contactDOS = contactService.getByDistributorId("1");
        // 断言
        assertEquals(2, contactDOS.size());
    }

    @Test
    void updatePassword() {
        DistributorContactUpdatePasswordReqVO reqVO=randomPojo(DistributorContactUpdatePasswordReqVO.class, o->{
            o.setId("1");
        });
        System.out.println(reqVO.toString());
        contactService.updatePassword(reqVO);
        DistributorContactPO contactDO = contactMapper.selectById(reqVO.getId());
        assertEquals(true,webPasswordEncoder.matches(reqVO.getPassword(),contactDO.getPassword()));
    }

    @Test
    void resetPassword() {
        contactService.resetPassword("1");
        DistributorContactPO contactDO = contactMapper.selectById("1");

        assertEquals(true,webPasswordEncoder.matches("000001",contactDO.getPassword()));

    }


}