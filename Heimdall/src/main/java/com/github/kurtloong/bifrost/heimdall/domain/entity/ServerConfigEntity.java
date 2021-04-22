package com.github.kurtloong.bifrost.heimdall.domain.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021-04-15 20:26
 */
@Data
@Builder
public class ServerConfigEntity {
    private String name;
    private String host;
    private Integer port;
    private Integer timeOut;
    private Integer retryCount;
}
