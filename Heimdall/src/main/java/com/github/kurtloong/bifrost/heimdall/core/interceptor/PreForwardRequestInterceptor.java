package com.github.kurtloong.bifrost.heimdall.core.interceptor;

import com.github.kurtloong.bifrost.heimdall.domain.entity.RequestDataEntity;
import com.github.kurtloong.bifrost.heimdall.domain.entity.ServerConfigEntity;

import java.util.Map;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021/5/20 17:11
 */
public interface PreForwardRequestInterceptor {
    void intercept(RequestDataEntity data,  Map<String, ServerConfigEntity> routes);
}

