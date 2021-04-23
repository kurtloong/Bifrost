package com.github.kurtloong.bifrost.heimdall.config;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.Transport;

import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.github.kurtloong.bifrost.heimdall.config.properties.SocketIoConfigProperties;
import com.github.kurtloong.bifrost.heimdall.core.listener.SocketListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021-04-22 17:11
 */

@Component
@EnableConfigurationProperties({SocketIoConfigProperties.class})
public class SocketIoConfig   {

    private final SocketListener socketListener;

    public SocketIoConfig(SocketListener socketListener) {
        this.socketListener = socketListener;
    }

    @Bean
    public SocketIOServer initServer(SocketIoConfigProperties socketIoConfigProperties){
        Configuration config = new Configuration();
        config.setPort(socketIoConfigProperties.getPort());
        config.setContext(socketIoConfigProperties.getContext());
        config.setBossThreads(socketIoConfigProperties.getBossCount());
        config.setWorkerThreads(socketIoConfigProperties.getWorkCount());
        config.setAllowCustomRequests(socketIoConfigProperties.getAllowCustomRequests());
        config.setUpgradeTimeout(socketIoConfigProperties.getUpgradeTimeout());
        config.setPingTimeout(socketIoConfigProperties.getPingTimeout());
        config.setPingInterval(socketIoConfigProperties.getPingInterval());
        config.setTransports(Transport.POLLING, Transport.WEBSOCKET);
        config.setOrigin(":*:");
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);
        socketConfig.setTcpNoDelay(true);
        socketConfig.setSoLinger(0);
        config.setSocketConfig(socketConfig);
        SocketIOServer server = new SocketIOServer(config);
        server.addListeners(socketListener);
        return server;
    }

    /**
     * Spring 扫描自定义注解
     */
    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer server) {
        return new SpringAnnotationScanner(server);
    }



}
