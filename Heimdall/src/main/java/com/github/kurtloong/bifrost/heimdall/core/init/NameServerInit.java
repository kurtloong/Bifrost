package com.github.kurtloong.bifrost.heimdall.core.init;

import com.github.kurtloong.bifrost.heimdall.config.properties.NameServerConfigProperties;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import io.netty.util.internal.SocketUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
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
@Component
@EnableConfigurationProperties({NameServerConfigProperties.class})
@Slf4j
public class NameServerInit implements CommandLineRunner {
    private static final HashMap<String, Socket> NAME_SERVER_MAP = new HashMap<>();

    @Bean
    public void initSocket (NameServerConfigProperties configProperties){
        Preconditions.checkNotNull(configProperties,NAME_SERVER_NOT_NULL);
        Preconditions.checkArgument(configProperties.getNameSeverAddress()!= null &&
                configProperties.getNameSeverAddress().size()>0,NAME_SERVER_ADDRESS_NOT_NULL);
        List<String> address = configProperties.getNameSeverAddress();

        address.forEach(s ->{
           String[] strings = StringUtils.split(s,":");
           Preconditions.checkArgument(strings.length>1,NAME_SERVER_ADDRESS_FORMAT_ERROR);
            try {
                Socket socket = new Socket(strings[0],Integer.parseInt(strings[1]));
                NAME_SERVER_MAP.put(strings[0],socket);
            } catch (IOException e) {
                log.info(NAME_SERVER_SOCKET_CREATE_ERROR,e);
            }
        } );


    }


    @Override
    public void run(String... args) throws Exception {

    }
}
