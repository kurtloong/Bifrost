package com.github.kurtloong.bifrost.heimdall.core.listener;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.github.kurtloong.bifrost.common.utils.WebSocketCacheUtil;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import static com.github.kurtloong.bifrost.heimdall.constant.ErrorMessageConstant.ON_CONNECT_PARAM_ERROR;


/**
 * The type Socket listener.
 *
 * @author kurt.loong
 * @version 1.0
 * @date 2021 -04-22 17:56
 */
@Slf4j
@Component
public class SocketListener {

    private static final String EVENT = "event";

    private static final String HOST = "host";

    private static final String APPLICATION_CONNECT = "APPLICATION_CONNECT";


    /**
     * On connect.
     *
     * @param client the client
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        String event = client.getHandshakeData().getSingleUrlParam(EVENT);
        Preconditions.checkArgument(StringUtils.isEmpty(event),ON_CONNECT_PARAM_ERROR);
        if (APPLICATION_CONNECT.equals(event)){
            String host = client.getHandshakeData().getSingleUrlParam(HOST);
            Preconditions.checkArgument(StringUtils.isEmpty(host),ON_CONNECT_PARAM_ERROR);
            WebSocketCacheUtil.saveApplicationClient(host,client);
        }else {
            WebSocketCacheUtil.saveClient(event,client);
        }
        log.info("socket connected event: {}",event);
    }


    /**
     * On disconnect.
     *
     * @param client the client
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        String event = client.getHandshakeData().getSingleUrlParam(EVENT);
        Preconditions.checkArgument(StringUtils.isEmpty(event),ON_CONNECT_PARAM_ERROR);
        if (APPLICATION_CONNECT.equals(event)){
            String host = client.getHandshakeData().getSingleUrlParam(HOST);
            Preconditions.checkArgument(StringUtils.isEmpty(host),ON_CONNECT_PARAM_ERROR);
            WebSocketCacheUtil.deleteApplicationClient(client.getSessionId(),host);
        }else {
            WebSocketCacheUtil.deleteClient(event,client.getSessionId());
        }
        log.info("socket disconnect event: {}",event);
    }
}
