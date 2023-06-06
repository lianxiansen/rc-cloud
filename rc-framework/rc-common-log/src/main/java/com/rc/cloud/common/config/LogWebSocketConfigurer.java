package com.rc.cloud.common.config;

import com.rc.cloud.common.log.FileHandler;
import com.rc.cloud.common.log.LogController;
import com.rc.cloud.common.log.LogReader;
import com.rc.cloud.common.log.websocket.LogWebSocketHandler;
import com.rc.cloud.common.log.websocket.WebSocketInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author WJF
 * @create 2022-08-06 14:27
 * @description TODO
 */
@Configuration
@EnableWebSocket
@ConditionalOnProperty(prefix = "sup.log.web", name = "enabled", havingValue = "true", matchIfMissing = true)
@Slf4j
public class LogWebSocketConfigurer implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        log.info("前端日志推送功能开启,正在监听连接 ...");
        registry.addHandler(logWebSocketHandler(), "/logWebSocket")
            .setAllowedOrigins("*")
            .addInterceptors(webSocketInterceptor());
//                .withSockJS();
    }

    @Bean
    public LogWebSocketHandler logWebSocketHandler() {
        return new LogWebSocketHandler();
    }

    @Bean
    public WebSocketInterceptor webSocketInterceptor() {
        return new WebSocketInterceptor();
    }

    @Bean
    public LogReader logReader() {
        return new LogReader();
    }

    @Bean
    public LogController logController(FileHandler fileHandler) {
        return new LogController(fileHandler);
    }

    @Bean
    public FileHandler fileHandler() {
        return new FileHandler();
    }
}
