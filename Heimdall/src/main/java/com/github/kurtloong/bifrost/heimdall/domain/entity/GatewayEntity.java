package com.github.kurtloong.bifrost.heimdall.domain.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021-04-23 9:55
 */
@Data
@Builder
public class GatewayEntity {

    private String ip;

    private Boolean status;


}
