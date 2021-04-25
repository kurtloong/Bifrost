package com.github.kurtloong.bifrost.heimdall.domain.entity;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.net.URISyntaxException;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021-04-25 15:07
 */
@Slf4j
public class SocketEntity {
    private final IO.Options options = new IO.Options();
    @Value("${socket-io.reconnectionAttempts}")
    private Integer reconnectionAttempts;
    @Value("${socket-io.reconnectionDelay}")
    private Integer reconnectionDelay;
    @Value("${socket-io.reconnectionTimeout}")
    private Integer reconnectionTimeout;

    private Socket socket;

    public SocketEntity(String url) {
        initOptions();
        try {
            this.socket = IO.socket(url,this.options);
        } catch (URISyntaxException e) {
            log.error("create socket error !",e);
        }
    }

    public void connect (){
        this.socket.connect();
    }

    public Boolean checkConnected(){
        return this.socket.connected();
    }

    public void disconnect(){
        this.socket.disconnect();
    }

    public void on(String event, Emitter.Listener fn){
        this.socket.on(event,fn);
    }

    private void  initOptions(){
        this.options.transports = new String[]{"websocket"};
        this.options.reconnectionAttempts = this.reconnectionAttempts;
        this.options.reconnectionDelay = this.reconnectionDelay;
        this.options.timeout = this.reconnectionTimeout;
    }


}
