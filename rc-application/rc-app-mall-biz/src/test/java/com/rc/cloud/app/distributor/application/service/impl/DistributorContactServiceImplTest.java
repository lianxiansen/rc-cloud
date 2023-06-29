package com.rc.cloud.app.distributor.application.service.impl;

import com.rc.cloud.app.distributor.appearance.req.AppDistributorContactUpdatePasswordReqVO;
import com.rc.cloud.app.distributor.application.service.DistributorContactService;
import com.rc.cloud.app.distributor.infrastructure.config.DistributorAutoConfig;
import com.rc.cloud.app.distributor.infrastructure.persistence.mapper.DistributorContactMapper;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorContactDO;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import com.rc.cloud.common.test.annotation.RcTest;
import com.rc.cloud.common.test.core.ut.BaseDbUnitTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
        DistributorContactDO contact1 = randomPojo(DistributorContactDO.class,o->{
            o.setMobile("13700000998");
        });

        DistributorContactDO contact2 = randomPojo(DistributorContactDO.class,o->{
            o.setMobile("13700000999");
        });

        // 准备参数
        List<DistributorContactDO> contactDOS = Arrays.asList(contact1, contact2);
        contactService.updateContacts(1L,contactDOS);
        List<DistributorContactDO> contactDOList = contactMapper.selectList(new LambdaQueryWrapperX<DistributorContactDO>()
                .eq(DistributorContactDO::getDistributorId, 1L));

        // 断言
        assertEquals(2, contactDOList.size());
        assertPojoEquals(contact1, contactDOList.get(0));
    }

    @Test
    void updateContacts_fail() {
        DistributorContactDO contact1 = randomPojo(DistributorContactDO.class,o->{
            o.setMobile("13700000003");
        });

        DistributorContactDO contact2 = randomPojo(DistributorContactDO.class,o->{
            o.setMobile("13700000999");
        });

        DistributorContactDO contact3 = randomPojo(DistributorContactDO.class,o->{
            o.setMobile("13700000999");
        });

        // 验证手机号重复
        final List<DistributorContactDO> contactDOS2 = Arrays.asList(contact2, contact3);
        assertServiceException(() -> contactService.updateContacts(1L,contactDOS2), DISTRIBUTOR_CONTACT_PHONE_DUPLICATE);

        // 验证手机号被其他绑定
        final List<DistributorContactDO> contactDOS = Arrays.asList(contact1, contact2);
        assertServiceException(() -> contactService.updateContacts(1L,contactDOS), DISTRIBUTOR_CONTACT_PHONE_EXIST);
    }

    @Test
    void getByDistributorId() {
        List<DistributorContactDO> contactDOS = contactService.getByDistributorId(1L);
        // 断言
        assertEquals(2, contactDOS.size());
    }

    @Test
    void updatePassword() {
        AppDistributorContactUpdatePasswordReqVO reqVO=randomPojo(AppDistributorContactUpdatePasswordReqVO.class, o->{
            o.setId(1L);
        });
        System.out.println(reqVO.toString());
        contactService.updatePassword(reqVO);
        DistributorContactDO contactDO = contactMapper.selectById(reqVO.getId());
        assertEquals(true,webPasswordEncoder.matches(reqVO.getPassword(),contactDO.getPassword()));
    }

    @Test
    void resetPassword() {
        contactService.resetPassword(1L);
        DistributorContactDO contactDO = contactMapper.selectById(1L);

        assertEquals(true,webPasswordEncoder.matches("000001",contactDO.getPassword()));

    }


}