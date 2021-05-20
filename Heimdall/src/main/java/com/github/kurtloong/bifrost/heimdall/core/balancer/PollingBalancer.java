package com.github.kurtloong.bifrost.heimdall.core.balancer;

import com.github.kurtloong.bifrost.heimdall.domain.entity.ServerConfigEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021/5/20 16:16
 */
public class PollingBalancer implements LoadBalancer{
    private final AtomicInteger flag = new AtomicInteger(0);
    /**
     * 负载均衡算法
     *
     * @param routes the routes
     * @return the server config entity
     */
    @Override
    public ServerConfigEntity chooseDestination(Map<String, ServerConfigEntity> routes) {
        List<ServerConfigEntity> configEntityList = new ArrayList<>();
        routes.forEach((s, serverConfigEntity) -> configEntityList.add(serverConfigEntity));
        if (configEntityList.size()<2){
            return configEntityList.get(0);
        }
        if (flag.get()>routes.size()){
            flag.set(0);
        }
        ServerConfigEntity configEntity = configEntityList.get(flag.get());
        flag.getAndIncrement();
        return configEntity;
    }
}
