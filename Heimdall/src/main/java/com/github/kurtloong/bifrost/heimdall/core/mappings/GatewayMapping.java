package com.github.kurtloong.bifrost.heimdall.core.mappings;

import com.github.kurtloong.bifrost.common.utils.HttpClientUtil;
import com.github.kurtloong.bifrost.heimdall.domain.entity.GatewayEntity;
import lombok.Getter;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021-04-23 9:59
 */

@Getter
@Component
public class GatewayMapping {

    private final  ConcurrentHashMap<String, GatewayEntity> gatewayMap = new ConcurrentHashMap<>();




    public void  sendToGateway(Object body){
        gatewayMap.forEach((s, gatewayEntity) -> {
            if (!gatewayEntity.getStatus()){
                return;
            }
            HttpClientUtil.post(s,body);
        });

    }

}
