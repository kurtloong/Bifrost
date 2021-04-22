package com.github.kurtloong.bifrost.heimdall.core.listener;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021-04-22 17:56
 */
@Slf4j
@Component
public class SocketListener {
    /**
     * 客户端连接
     *
     * @param client
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {

        log.info("建立连接");


    }

    /**
     * 客户端断开
     *
     * @param client
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        log.info("关闭连接");
    }
}
