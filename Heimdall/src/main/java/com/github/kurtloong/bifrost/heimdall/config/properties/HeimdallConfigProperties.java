package com.github.kurtloong.bifrost.heimdall.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021/5/18 15:02
 */

@Data
@ConfigurationProperties(prefix = "heimdall")
public class HeimdallConfigProperties {
    private Boolean tracing;
}
