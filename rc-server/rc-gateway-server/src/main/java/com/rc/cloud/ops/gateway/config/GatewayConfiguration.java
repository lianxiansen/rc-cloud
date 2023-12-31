package com.rc.cloud.ops.gateway.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.ops.gateway.filter.PasswordDecoderFilter;
import com.rc.cloud.ops.gateway.filter.RcRequestGlobalFilter;
import com.rc.cloud.ops.gateway.filter.SwaggerBasicGatewayFilter;
import com.rc.cloud.ops.gateway.filter.ValidateCodeGatewayFilter;
import com.rc.cloud.ops.gateway.handler.GlobalExceptionHandler;
import com.rc.cloud.ops.gateway.handler.ImageCodeHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 网关配置
 *
 * @author L.cm
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(GatewayConfigProperties.class)
public class GatewayConfiguration {

	@Bean
	public PasswordDecoderFilter passwordDecoderFilter(GatewayConfigProperties configProperties) {
		return new PasswordDecoderFilter(configProperties);
	}

	@Bean
	public RcRequestGlobalFilter rcRequestGlobalFilter() {
		return new RcRequestGlobalFilter();
	}

	@Bean
	@ConditionalOnProperty(name = "swagger.basic.enabled")
	public SwaggerBasicGatewayFilter swaggerBasicGatewayFilter(
			SpringDocConfiguration.SwaggerDocProperties swaggerProperties) {
		return new SwaggerBasicGatewayFilter(swaggerProperties);
	}

	@Bean
	public ValidateCodeGatewayFilter validateCodeGatewayFilter(GatewayConfigProperties configProperties,
															   ObjectMapper objectMapper, StringRedisTemplate stringRedisTemplate) {
		return new ValidateCodeGatewayFilter(configProperties, objectMapper, stringRedisTemplate);
	}

	@Bean
	public GlobalExceptionHandler globalExceptionHandler(ObjectMapper objectMapper) {
		return new GlobalExceptionHandler(objectMapper);
	}

	@Bean
	public ImageCodeHandler imageCodeHandler(StringRedisTemplate stringRedisTemplate) {
		return new ImageCodeHandler(stringRedisTemplate);
	}

}
