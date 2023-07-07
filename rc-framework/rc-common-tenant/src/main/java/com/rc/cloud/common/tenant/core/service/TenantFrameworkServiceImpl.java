package com.rc.cloud.common.tenant.core.service;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.rc.cloud.app.system.api.tenant.TenantApi;
import com.rc.cloud.app.system.api.tenant.feign.RemoteTenantService;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.util.cache.CacheUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.List;

/**
 * Tenant 框架 Service 实现类
 *
 * @author 芋道源码
 */
@RequiredArgsConstructor
public class TenantFrameworkServiceImpl implements TenantFrameworkService {

    private static final ServiceException SERVICE_EXCEPTION_NULL = new ServiceException();

//    private final TenantApi tenantApi;

    private final RemoteTenantService remoteTenantService;

    /**
     * 针对 {@link #getTenantIds()} 的缓存
     */
    private final LoadingCache<Object, List<String>> getTenantIdsCache = CacheUtils.buildAsyncReloadingCache(
            Duration.ofMinutes(1L), // 过期时间 1 分钟
            new CacheLoader<Object, List<String>>() {

                @Override
                public List<String> load(Object key) {
                    return remoteTenantService.getTenantIdList().getData();
                }

            });

    /**
     * 针对 {@link #validTenant(String)} 的缓存
     */
    private final LoadingCache<String, ServiceException> validTenantCache = CacheUtils.buildAsyncReloadingCache(
            Duration.ofMinutes(1L), // 过期时间 1 分钟
            new CacheLoader<String, ServiceException>() {

                @Override
                public ServiceException load(String id) {
                    try {
                        remoteTenantService.validateTenant(id);
                        return SERVICE_EXCEPTION_NULL;
                    } catch (ServiceException ex) {
                        return ex;
                    }
                }

            });

    @Override
    @SneakyThrows
    public List<String> getTenantIds() {
        return getTenantIdsCache.get(Boolean.TRUE);
    }

    @Override
    public void validTenant(String id) {
        ServiceException serviceException = validTenantCache.getUnchecked(id);
        if (serviceException != SERVICE_EXCEPTION_NULL) {
            throw serviceException;
        }
    }

}
