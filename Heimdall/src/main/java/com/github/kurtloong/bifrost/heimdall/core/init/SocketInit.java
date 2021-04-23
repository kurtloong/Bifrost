package com.github.kurtloong.bifrost.heimdall.core.init;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021-04-23 10:11
 */
@Slf4j
@Component
public class SocketInit implements CommandLineRunner {
    private final SocketIOServer socketIOServer;

    public SocketInit(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
    }

    @Override
    public void run(String... args) {
        socketIOServer.start();
        log.info("websocket server start!!!");
    }
}
