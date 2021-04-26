package com.github.kurtloong.bifrost.common.utils;

import com.corundumstudio.socketio.SocketIOClient;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021-04-26 15:36
 */
public class WebSocketCacheUtil {

    private static final String APPLICATION_CONNECT = "APPLICATION_CONNECT";

    private static final Map<String, HashMap<UUID, SocketIOClient>> SOCKET_CONNECTION_MAP = new ConcurrentHashMap<>();

    private static final Map<String, UUID> APPLICATION_SOCKET_MAP = new ConcurrentHashMap<>();

    public static void saveClient(String event, SocketIOClient socketIOClient) {
        HashMap<UUID, SocketIOClient> eventCache = SOCKET_CONNECTION_MAP.get(event);
        if (eventCache == null) {
            eventCache = new HashMap<>();
        }
        eventCache.put(socketIOClient.getSessionId(), socketIOClient);
        SOCKET_CONNECTION_MAP.put(event, eventCache);

    }

    public static void saveApplicationClient(String host, SocketIOClient socketIOClient) {
        String md5 = DigestUtils.md5DigestAsHex(host.getBytes(StandardCharsets.UTF_8));
        saveClient(APPLICATION_CONNECT, socketIOClient);
        APPLICATION_SOCKET_MAP.put(md5, socketIOClient.getSessionId());
    }

    public static HashMap<UUID, SocketIOClient> getEventCache(String event) {
        return SOCKET_CONNECTION_MAP.get(event);
    }

    public static SocketIOClient getApplicationSocket(String host) {

        String md5 = DigestUtils.md5DigestAsHex(host.getBytes(StandardCharsets.UTF_8));
        UUID uuid = APPLICATION_SOCKET_MAP.get(md5);
        if (uuid == null) {
            return null;
        }

        HashMap<UUID, SocketIOClient> sockets = SOCKET_CONNECTION_MAP.get(APPLICATION_CONNECT);

        if (CollectionUtils.isEmpty(sockets)) {
            return null;
        }

        return sockets.get(uuid);

    }


    public static void deleteClient(String event, UUID uuid) {
        HashMap<UUID, SocketIOClient> sockets = SOCKET_CONNECTION_MAP.get(event);
        sockets.remove(uuid);
    }


    public static void deleteApplicationClient(UUID uuid, String host) {
        String md5 = DigestUtils.md5DigestAsHex(host.getBytes(StandardCharsets.UTF_8));

        deleteClient(APPLICATION_CONNECT, uuid);

        APPLICATION_SOCKET_MAP.remove(md5);

    }


}
