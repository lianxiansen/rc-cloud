package com.rc.cloud.app.marketing.infrastructure.repository;

import com.rc.cloud.app.marketing.domain.entity.comfirmorder.ComfirmOrder;
import com.rc.cloud.app.marketing.domain.entity.comfirmorder.ComfirmOrderRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName ComfirmOrderRepositoryImpl
 * @Author liandy
 * @Date 2023/8/3 14:38
 * @Description TODO
 * @Version 1.0
 */
@Repository
public class ComfirmOrderRepositoryImpl implements ComfirmOrderRepository {

    @Override
    public boolean save(ComfirmOrder comfirmOrder) {
        return false;
    }
}
