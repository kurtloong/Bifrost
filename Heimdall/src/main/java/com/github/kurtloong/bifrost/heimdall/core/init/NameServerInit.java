package com.github.kurtloong.bifrost.heimdall.core.init;

import com.github.kurtloong.bifrost.common.utils.JsonUtil;
import com.github.kurtloong.bifrost.heimdall.domain.entity.SocketEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021-04-25 16:01
 */
@Component
@Slf4j
public class NameServerInit implements CommandLineRunner {
    private static final String EVENT = "GATEWAY_LIST_SYNC";

    private final HashMap<String, SocketEntity> nameServerSockets;

    public NameServerInit(HashMap<String, SocketEntity> nameServerSockets) {
        this.nameServerSockets = nameServerSockets;
    }

    @Override
    public void run(String... args) {
        if (nameServerSockets.size()<1){
            return;
        }

        nameServerSockets.forEach((s, socketEntity) -> {
            socketEntity.connect();
            if (socketEntity.checkConnected()){
                log.info("name server {} already connected",s);
            }
            socketEntity.on(EVENT,objects -> {
                //TODO 处理逻辑
                log.info(JsonUtil.toJSONString(objects));
            });
        });
    }
}
