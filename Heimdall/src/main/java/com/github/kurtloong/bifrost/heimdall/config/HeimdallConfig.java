package com.github.kurtloong.bifrost.heimdall.config;

import com.github.kurtloong.bifrost.heimdall.config.properties.HeimdallConfigProperties;
import com.github.kurtloong.bifrost.heimdall.config.properties.NameServerConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021/5/18 15:05
 */
@Configuration
@EnableConfigurationProperties({HeimdallConfigProperties.class})
@Slf4j
public class  HeimdallConfig {



}
