package com.rc.cloud.common.feign;

import com.rc.cloud.common.core.util.IpUtils;
import com.rc.cloud.common.core.util.StringUtils;
import com.rc.cloud.common.core.util.servlet.ServletUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * feign 请求拦截器, 在feign请求发出之前，加入一些操作
 *
 * @author oliveoil
 */
@Component
public class FeignRequestInterceptor implements RequestInterceptor
{
    @Override
    public void apply(RequestTemplate requestTemplate)
    {
        HttpServletRequest httpServletRequest = ServletUtils.getRequest();
        if (StringUtils.isNotNull(httpServletRequest))
        {
            Map<String, String> headers = ServletUtils.getHeaders(httpServletRequest);
            headers.entrySet().forEach(entry->{
                requestTemplate.header(entry.getKey(), entry.getValue());
            });

            // 配置客户端IP
            requestTemplate.header("X-Forwarded-For", IpUtils.getIpAddr(ServletUtils.getRequest()));
        }
    }
}
