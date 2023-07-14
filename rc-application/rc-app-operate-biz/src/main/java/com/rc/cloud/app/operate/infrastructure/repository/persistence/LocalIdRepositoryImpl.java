package com.rc.cloud.app.operate.infrastructure.repository.persistence;

import com.rc.cloud.common.core.domain.IdRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @ClassName: RemoteIdRepository
 * @Author: liandy
 * @Date: 2023/7/12 10:21
 * @Description: TODO
 */
@Service
@Primary
public class LocalIdRepositoryImpl implements IdRepository {
    @Override
    public String nextId() {
        return UUID.randomUUID().toString().substring(0,31);
    }
}
