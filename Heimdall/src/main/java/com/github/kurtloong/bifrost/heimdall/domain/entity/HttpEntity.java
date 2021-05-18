package com.github.kurtloong.bifrost.heimdall.domain.entity;

import lombok.Data;
import org.springframework.http.HttpHeaders;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021/5/18 15:14
 */
@Data
public abstract class HttpEntity {

    protected HttpHeaders headers;

    public HttpHeaders getHeaders() { return headers; }

}
