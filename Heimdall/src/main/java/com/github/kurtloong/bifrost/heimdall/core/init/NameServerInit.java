package com.github.kurtloong.bifrost.heimdall.core.init;

import com.github.kurtloong.bifrost.heimdall.config.properties.NameServerConfigProperties;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

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
public class NameServerInit  {

    @Bean
    public HashMap<String, Socket> nameServerSockets (NameServerConfigProperties configProperties){
        final HashMap<String, Socket> nameServerMap = new HashMap<>();

        Preconditions.checkNotNull(configProperties,NAME_SERVER_NOT_NULL);

        if (!configProperties.getCluster()){
            log.info("Turn off the cluster function！");
            return nameServerMap;
        }

        Preconditions.checkArgument(configProperties.getNameSeverAddress()!= null &&
                configProperties.getNameSeverAddress().size()>0,NAME_SERVER_ADDRESS_NOT_NULL);

        List<String> address = configProperties.getNameSeverAddress();

        address.forEach(s ->{
           String[] strings = StringUtils.split(s,":");
           Preconditions.checkArgument(strings.length>1,NAME_SERVER_ADDRESS_FORMAT_ERROR);
            try {
                Socket socket = new Socket(strings[0],Integer.parseInt(strings[1]));
                nameServerMap.put(strings[0],socket);
            } catch (IOException e) {
                log.info(NAME_SERVER_SOCKET_CREATE_ERROR,e);
            }
        } );

        return nameServerMap;
    }


//    @Override
//    public void run(String... args) {
//        if (NAME_SERVER_MAP.size()<1){
//            return;
//        }
//
//        NAME_SERVER_MAP.forEach((s, socket) -> {
//            //TODO 重试
//            if (socket.isConnected()){
//                log.info("name server {} already connected",s);
//            }
//        });
//    }
}
