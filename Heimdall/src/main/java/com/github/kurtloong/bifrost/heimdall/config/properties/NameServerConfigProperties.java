package com.github.kurtloong.bifrost.heimdall.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021-04-23 10:01
 */
@Data
@ConfigurationProperties(prefix = "heimdall")
public class NameServerConfigProperties {
    private Boolean cluster;
    private List<String> nameSeverAddress;
}
