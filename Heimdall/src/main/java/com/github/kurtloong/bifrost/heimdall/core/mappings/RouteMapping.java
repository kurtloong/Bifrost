package com.github.kurtloong.bifrost.heimdall.core.mappings;


import com.github.kurtloong.bifrost.heimdall.domain.entity.ServerConfigEntity;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021-04-15 20:12
 */
public class RouteMapping {

    /**
     * The Route map.
     */
    ConcurrentHashMap<String, List<ServerConfigEntity>> routeMap = new ConcurrentHashMap<>();



}
