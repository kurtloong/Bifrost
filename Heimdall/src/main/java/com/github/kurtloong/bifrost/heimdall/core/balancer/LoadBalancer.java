package com.github.kurtloong.bifrost.heimdall.core.balancer;

import com.github.kurtloong.bifrost.heimdall.domain.entity.ServerConfigEntity;

import java.util.Map;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021/5/20 16:13
 */
public interface LoadBalancer {
    /**
     * 负载均衡算法
     *
     * @param routes the routes
     * @return the server config entity
     */
    ServerConfigEntity chooseDestination(Map<String,ServerConfigEntity> routes);
}
