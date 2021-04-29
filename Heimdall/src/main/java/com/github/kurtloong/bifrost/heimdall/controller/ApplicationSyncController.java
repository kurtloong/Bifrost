package com.github.kurtloong.bifrost.heimdall.controller;

import com.github.kurtloong.bifrost.heimdall.core.mappings.RouteMapping;
import com.github.kurtloong.bifrost.heimdall.domain.entity.ServerConfigEntity;
import com.github.kurtloong.bifrost.heimdall.domain.request.ApplicationSyncRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021-04-29 10:15
 */
@RestController
@Slf4j
public class ApplicationSyncController {

    private final RouteMapping routeMapping;

    public ApplicationSyncController(RouteMapping routeMapping) {
        this.routeMapping = routeMapping;
    }

    @PostMapping("/applicationSync")
    public void applicationSync(ApplicationSyncRequest request) {
        routeMapping.update(request.getServers(), request.getVersion());
    }
}
