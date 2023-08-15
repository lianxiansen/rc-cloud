package com.rc.cloud.app.marketing.infrastructure.repository;

import com.rc.cloud.app.marketing.domain.entity.customer.Customer;
import com.rc.cloud.app.marketing.domain.entity.customer.CustomerRepository;
import com.rc.cloud.common.security.service.RcUser;
import com.rc.cloud.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * @ClassName CustomerRepositoryImpl
 * @Author liandy
 * @Date 2023/8/15 14:47
 * @Description TODO
 * @Version 1.0
 */
@Repository
public class CustomerRepositoryImpl implements CustomerRepository {
    @Value("${spring.profiles.active}")
    private String active;
    @Override
    public Customer getCustomer() {
        if(active.equals("local")){
            return Customer.mock();
        }
        RcUser rcUser = SecurityUtils.getUser();
        return new Customer(rcUser.getId(),rcUser.getName(),rcUser.getMobile());
    }
}
