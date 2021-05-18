package com.github.kurtloong.bifrost.heimdall.core.trace;

import com.github.kurtloong.bifrost.heimdall.config.properties.HeimdallConfigProperties;
import com.github.kurtloong.bifrost.heimdall.domain.request.ForwardRequest;
import com.github.kurtloong.bifrost.heimdall.domain.request.IncomingRequest;
import com.github.kurtloong.bifrost.heimdall.domain.response.ReceivedResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.UUID;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021/5/18 15:12
 */
public class ProxyingTraceInterceptor {
    protected final TraceInterceptor traceInterceptor;
    protected final HeimdallConfigProperties configProperties;

    public ProxyingTraceInterceptor(TraceInterceptor traceInterceptor, HeimdallConfigProperties configProperties) {
        this.traceInterceptor = traceInterceptor;
        this.configProperties = configProperties;
    }

    public String generateTraceId(){ return  configProperties.getTracing() ? UUID.randomUUID().toString():null;}

    public void onRequestReceived(String traceId, HttpMethod method, String host, String uri, HttpHeaders headers) {
        runIfTracingIsEnabled(() -> {
            IncomingRequest request = getIncomingRequest(method, host, uri, headers);
            traceInterceptor.onRequestReceived(traceId, request);
        });
    }

    protected void runIfTracingIsEnabled(Runnable operation) {
        if (configProperties.getTracing()) {
            operation.run();
        }
    }

    private IncomingRequest getIncomingRequest(HttpMethod method, String host, String uri, HttpHeaders headers) {
        IncomingRequest request = new IncomingRequest();
        request.setMethod(method);
        request.setHost(host);
        request.setUri(uri);
        request.setHeaders(headers);
        return request;
    }

    public void onNoMappingFound(String traceId, HttpMethod method, String host, String uri, HttpHeaders headers) {
        runIfTracingIsEnabled(() -> {
            IncomingRequest request = getIncomingRequest(method, host, uri, headers);
            traceInterceptor.onNoMappingFound(traceId, request);
        });
    }

    public void onForwardStart(String traceId, String mappingName, HttpMethod method, String host, String uri, byte[] body, HttpHeaders headers) {
        runIfTracingIsEnabled(() -> {
            ForwardRequest request = new ForwardRequest();
            request.setMappingName(mappingName);
            request.setMethod(method);
            request.setHost(host);
            request.setUri(uri);
            request.setBody(body);
            request.setHeaders(headers);
            traceInterceptor.onForwardStart(traceId, request);
        });
    }

    public void onForwardFailed(String traceId, Throwable error) {
        runIfTracingIsEnabled(() -> traceInterceptor.onForwardError(traceId, error));
    }

    public void onForwardComplete(String traceId, HttpStatus status, byte[] body, HttpHeaders headers) {
        runIfTracingIsEnabled(() -> {
            ReceivedResponse response = new ReceivedResponse();
            response.setStatus(status);
            response.setBody(body);
            response.setHeaders(headers);
            traceInterceptor.onForwardComplete(traceId, response);
        });
    }



}
