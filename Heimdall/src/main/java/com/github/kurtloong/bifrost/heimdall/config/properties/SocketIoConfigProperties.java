package com.github.kurtloong.bifrost.heimdall.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021-04-22 16:42
 */
@ConfigurationProperties(prefix = "socket-io")
@Data
public class SocketIoConfigProperties {
    private Integer port;

    private Integer bossCount;

    private Integer workCount;

    private Boolean allowCustomRequests;

    private Integer upgradeTimeout;

    private Integer pingTimeout;

    private Integer pingInterval;

    private String context;
}
