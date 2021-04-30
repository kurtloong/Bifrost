package com.github.kurtloong.bifrost.heimdall.domain.request;

import com.github.kurtloong.bifrost.heimdall.domain.entity.ServerConfigEntity;
import lombok.Data;

import java.util.List;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021-04-29 10:20
 */
@Data
public class ApplicationSyncRequest {
    private List<ServerConfigEntity> servers;
    private Integer version;

    public ApplicationSyncRequest(List<ServerConfigEntity> servers, Integer version) {
        this.servers = servers;
        this.version = version;
    }
}
