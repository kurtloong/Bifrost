package com.github.kurtloong.bifrost.heimdall.core.mappings;


import com.github.kurtloong.bifrost.heimdall.domain.entity.ServerConfigEntity;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.github.kurtloong.bifrost.heimdall.constant.ErrorMessageConstant.PARAM_NULL_ERROR;

/**
 * @author kaka
 * @version 1.0
 * @date 2021-04-15 20:12
 */
@Getter
@Component
public class RouteMapping {

    /**
     * TODO 是否会内存泄漏
     * The Route map.
     */
    private final ConcurrentHashMap<String, Map<String,ServerConfigEntity>> routeMap = new ConcurrentHashMap<>();

    private final AtomicInteger version = new AtomicInteger(0);

    public void put(ServerConfigEntity serverConfigEntity) {

        Preconditions.checkNotNull(serverConfigEntity, PARAM_NULL_ERROR, serverConfigEntity.getClass().getSimpleName());

        String name = serverConfigEntity.getName();

        synchronized (this){
            if (routeMap.containsKey(name)) {
                routeMap.get(name).put(serverConfigEntity.getHost(),serverConfigEntity);
            } else {
                Map<String ,ServerConfigEntity> serverConfigEntityMap = new HashMap<>();
                serverConfigEntityMap.put(serverConfigEntity.getHost(),serverConfigEntity);
                routeMap.put(name, serverConfigEntityMap);
            }
        }

        version.incrementAndGet();
    }

    public void remove(ServerConfigEntity serverConfigEntity){
        Preconditions.checkNotNull(serverConfigEntity, PARAM_NULL_ERROR, serverConfigEntity.getClass().getSimpleName());

        String name = serverConfigEntity.getName();
        String host = serverConfigEntity.getHost();

        synchronized (this){
            if (!routeMap.containsKey(name)){
                return;
            }

            routeMap.get(name).remove(host);

            if (routeMap.get(name).size()<1){
                routeMap.remove(name);
            }
        }

        version.incrementAndGet();

    }

    public void update(List<ServerConfigEntity> servers, Integer version){

        if (version<this.version.get()){
            return;
        }

        synchronized (this){
            Map<String , List<ServerConfigEntity>> serverMap = servers.stream().collect(Collectors.groupingBy(ServerConfigEntity::getName));

            if (version == this.version.get()){
                merge(serverMap);
                this.version.set(version);
                return;
            }

            if (CollectionUtils.isEmpty(servers)){
                routeMap.clear();
                this.version.set(version);
                return;
            }


            routeMap.clear();

            serverMap.forEach((s, serverConfigEntities) -> {
                HashMap<String, ServerConfigEntity> maps = Maps.newHashMap();
                serverConfigEntities.forEach(serverConfigEntity -> maps.put(serverConfigEntity.getHost(),serverConfigEntity));
                routeMap.put(s, maps);
            });

            this.version.set(version);
        }

    }

    private void merge(Map<String , List<ServerConfigEntity>> serverMap){
        serverMap.forEach((s, serverConfigEntities) -> {

            if (routeMap.containsKey(s)){
                serverConfigEntities.forEach(serverConfigEntity -> {
                    routeMap.get(s).put(serverConfigEntity.getHost(),serverConfigEntity);
                });
            }else {
                HashMap<String, ServerConfigEntity> maps = Maps.newHashMap();
                serverConfigEntities.forEach(serverConfigEntity -> maps.put(serverConfigEntity.getHost(),serverConfigEntity));
                routeMap.put(s, maps);
            }


        });

    }

    public List<ServerConfigEntity> getServerConfigList(){

        List<ServerConfigEntity> result = Lists.newArrayList();

        if (CollectionUtils.isEmpty(routeMap)){
            return result;
        }

        routeMap.forEach((s, stringServerConfigEntityMap) -> {
           result.addAll( stringServerConfigEntityMap.values());

        });

        return result;
    }

}
