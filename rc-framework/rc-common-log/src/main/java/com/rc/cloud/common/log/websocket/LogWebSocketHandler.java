package com.rc.cloud.common.log.websocket;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.json.JSONUtil;
import com.rc.cloud.common.log.FileHandler;
import com.rc.cloud.common.log.LogReader;
import com.rc.cloud.common.log.SessionData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * @author WJF
 * @create 2022-08-06 14:27
 * @description TODO
 */
@Slf4j
@RequiredArgsConstructor
public class LogWebSocketHandler implements WebSocketHandler {
    @Autowired
    private LogReader logReader;
    @Autowired
    private FileHandler fileHandler;
    @Value("${logging.file.path:}")
    private String logDir;


    // 单次推送默认最大时间为30分钟
    private static final Integer ONCE_OUT_TIME = 1000 * 60 * 30;
    // 默认每秒推送一次
    private static final Integer DEFAULT_DELAY = 1000;
    private static final int MAX_CLIENT = 5;
    private static final ExecutorService executorService = ThreadUtil.newExecutor(MAX_CLIENT, MAX_CLIENT, 1);


    /**
     * 在WebSocket协商成功并且WebSocket连接打开并准备好使用后调用
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {

        log.info("客户端连接[id={},remoteAddress={}]", webSocketSession.getId(), webSocketSession.getRemoteAddress());
        webSocketSession.sendMessage(new TextMessage("<span style='font-weight:bold;'>服务器提示:</span><span style='color: blue;'>日志推送开启>>>>>>>>>>></span>"));
        long start = System.currentTimeMillis();
        SessionData sessionData = new SessionData().setSessionId(webSocketSession.getId()).setNew(true);
        executorService.execute(() -> {
            try {
                while (webSocketSession.isOpen()) {
                    Object logLevel = webSocketSession.getAttributes().get("level");
                    if (logLevel == null) {
                        log.error("客户端[id={},remoteAddress={}],logLevel为空", webSocketSession.getId(), webSocketSession.getRemoteAddress());
                        return;
                    }
                    if (StringUtils.isBlank(logDir)) {
                        log.error("日志文件路径不存在");
                        sendAndClose(webSocketSession, new TextMessage("<span style='font-weight:bold;'>服务器提示:</span><span style='color: red;'>日志文件路径不存在</span>"));
                        return;
                    }
                    String logPath = fileHandler.getFilePath(logLevel);

                    if (!FileUtil.exist(logPath)) {
                        log.error("日志文件不存在");
                        sendAndClose(webSocketSession, new TextMessage("<span style='font-weight:bold;'>服务器提示:</span><span style='color: red;'>日志文件不存在</span>"));
                        return;
                    }
                    String result = logReader.read(logPath, sessionData);
                    if (StringUtils.isNotBlank(result)) {
                        sendMsg(webSocketSession, new TextMessage(result));
                    }
                    if (System.currentTimeMillis() - start >= ONCE_OUT_TIME) {
                        sendAndClose(webSocketSession, new TextMessage("<span style='font-weight:bold;'>服务器提示:</span><span style='color: red;'>已达单次推送最大时长，本次推送结束！如需再次推送请点击'开启日志'</span>"));
                    }
                    Thread.sleep(DEFAULT_DELAY);
                }
                log.info("本次日志推送完毕");
            } catch (Exception e) {
                log.error("日志推送发生异常，客户端: {}", JSONUtil.toJsonStr(webSocketSession));
                log.error("异常信息:", e.getCause());
            } finally {
                try {
                    webSocketSession.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void sendMsg(WebSocketSession webSocketSession, WebSocketMessage<?> msg) throws Exception {
        if (webSocketSession.isOpen()) {
            webSocketSession.sendMessage(msg);
        }
    }

    private void sendAndClose(WebSocketSession webSocketSession, WebSocketMessage<?> msg) throws Exception {
        sendMsg(webSocketSession, msg);
        webSocketSession.close();
    }

    /**
     * 当新的WebSocket消息到达时调用
     */
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        log.info("收到客户端[id={},remoteAddress={}]消息:{}", webSocketSession.getId(), webSocketSession.getRemoteAddress(), ((TextMessage) webSocketMessage).getPayload());
    }

    /**
     * 处理底层WebSocket消息传输中的错误
     */
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        log.error("客户端连接异常[id={},remoteAddress={}]", webSocketSession.getId(), webSocketSession.getRemoteAddress());
        log.error("异常信息", throwable);
    }

    /**
     * 在任何一方关闭WebSocket连接后或在发生传输错误后调用。
     * 尽管会话在技术上可能仍然是打开的，取决于底层实现，在者种情况下上发送消息是不鼓励的，并且很可能不会成功
     */
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        log.info("客户端连接关闭[id={},remoteAddress={}]", webSocketSession.getId(), webSocketSession.getRemoteAddress());
    }

    /**
     * WebSocketHandler是否处理部分消息。 如果这个标志被设置为true，并且底层WebSocket服务器支持部分消息，
     * 那么一个大的WebSocket消息或者一个未知大小的消息可能会被拆分，并且可能通过多次调用
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
