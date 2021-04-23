package com.github.kurtloong.bifrost.heimdall.core.mappings;

import com.github.kurtloong.bifrost.heimdall.domain.entity.GatewayEntity;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021-04-23 9:59
 */
public class GatewayMapping {

    ConcurrentHashMap<String, GatewayEntity> gatewayMap = new ConcurrentHashMap<>();



}
