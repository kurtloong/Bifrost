package com.github.kurtloong.bifrost.heimdall.domain.entity;

import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;

import static com.github.kurtloong.bifrost.common.utils.BodyConverterUtil.convertBodyToString;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021/5/20 16:54
 */
public class UnmodifiableRequestDataEntity {
    protected HttpMethod method;
    protected String uri;
    protected String host;
    protected HttpHeaders headers;
    protected byte[] body;
    protected HttpServletRequest originRequest;

    public UnmodifiableRequestDataEntity(RequestDataEntity requestData) {
        this(
                requestData.getMethod(),
                requestData.getHost(),
                requestData.getUri(),
                requestData.getHeaders(),
                requestData.getBody(),
                requestData.getOriginRequest()
        );
    }

    public UnmodifiableRequestDataEntity(HttpMethod method,
                                   String host,
                                   String uri,
                                   HttpHeaders headers,
                                   byte[] body,
                                   HttpServletRequest request
    ) {
        this.method = method;
        this.host = host;
        this.uri = uri;
        this.headers = headers;
        this.body = body;
        this.originRequest = request;
    }


    public HttpMethod getMethod() {
        return method;
    }

    public String getHost() { return host; }

    public String getUri() {
        return uri;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }

    public HttpServletRequest getOriginRequest() { return this.originRequest; }

    public String getBodyAsString() {
        return convertBodyToString(body);
    }
}
