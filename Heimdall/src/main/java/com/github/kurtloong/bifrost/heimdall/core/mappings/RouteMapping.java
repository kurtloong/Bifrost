package com.github.kurtloong.bifrost.heimdall.core.mappings;


import com.github.kurtloong.bifrost.heimdall.domain.entity.ServerConfigEntity;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.github.kurtloong.bifrost.heimdall.constant.ErrorMessageConstant.PARAM_NULL_ERROR;

/**
 * @author kaka
 * @version 1.0
 * @date 2021-04-15 20:12
 */
public class RouteMapping {

    /**
     * The Route map.
     */
    private final ConcurrentHashMap<String, List<ServerConfigEntity>> routeMap = new ConcurrentHashMap<>();

    private final AtomicInteger version = new AtomicInteger(0);

    public void add(ServerConfigEntity serverConfigEntity) {

        Preconditions.checkNotNull(serverConfigEntity, PARAM_NULL_ERROR, serverConfigEntity.getClass().getSimpleName());

        String name = serverConfigEntity.getName();


        if (routeMap.containsKey(name)) {
            routeMap.get(name).add(serverConfigEntity);
        } else {
            List<ServerConfigEntity> serverConfigList = new ArrayList<>();
            serverConfigList.add(serverConfigEntity);
            routeMap.put(name, serverConfigList);
        }


        version.incrementAndGet();
    }

}
