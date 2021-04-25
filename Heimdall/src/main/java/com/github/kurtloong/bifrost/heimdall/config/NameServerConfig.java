package com.github.kurtloong.bifrost.heimdall.config;

import com.github.kurtloong.bifrost.heimdall.config.properties.NameServerConfigProperties;
import com.github.kurtloong.bifrost.heimdall.domain.entity.SocketEntity;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

import static com.github.kurtloong.bifrost.heimdall.constant.ErrorMessageConstant.*;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021-04-23 10:45
 */
@Configuration
@EnableConfigurationProperties({NameServerConfigProperties.class})
@Slf4j
public class NameServerConfig  {

    @Bean
    public HashMap<String, SocketEntity> nameServerSockets (NameServerConfigProperties configProperties){
        final HashMap<String, SocketEntity> nameServerMap = new HashMap<>();

        Preconditions.checkNotNull(configProperties,NAME_SERVER_NOT_NULL);

        if (!configProperties.getCluster()){
            log.info("Turn off the cluster function！");
            return nameServerMap;
        }

        Preconditions.checkArgument(configProperties.getNameSeverAddress()!= null &&
                configProperties.getNameSeverAddress().size()>0,NAME_SERVER_ADDRESS_NOT_NULL);

        List<String> address = configProperties.getNameSeverAddress();

        address.forEach(s ->{
            SocketEntity socket = new SocketEntity(s);
            nameServerMap.put(s,socket);
        } );

        return nameServerMap;
    }

}
