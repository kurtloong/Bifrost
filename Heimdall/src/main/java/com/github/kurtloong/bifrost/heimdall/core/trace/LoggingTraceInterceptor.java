package com.github.kurtloong.bifrost.heimdall.core.trace;

import com.github.kurtloong.bifrost.heimdall.domain.request.ForwardRequest;
import com.github.kurtloong.bifrost.heimdall.domain.request.IncomingRequest;
import com.github.kurtloong.bifrost.heimdall.domain.response.ReceivedResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021/5/18 15:38
 */
@Slf4j
public class LoggingTraceInterceptor implements TraceInterceptor {
    @Override
    public void onRequestReceived(String traceId, IncomingRequest request) {
        log.info("Incoming HTTP request received: traceId {} method {} host {} uri {} headers {}", traceId,
                request.getMethod(), request.getHost(),
                request.getUri(), request.getHeaders());
    }

    @Override
    public void onNoMappingFound(String traceId, IncomingRequest request) {
        log.info("No mapping found for incoming HTTP request: traceId {} method {} host {} uri {} headers {}", traceId,
                request.getMethod(), request.getHost(),
                request.getUri(), request.getHeaders());
    }

    @Override
    public void onForwardStart(String traceId, ForwardRequest request) {
        log.info("Forwarding HTTP request started:  traceId {} method {} host {} uri {} headers {}", traceId,
                request.getMethod(), request.getHost(),
                request.getUri(), request.getHeaders());
    }

    @Override
    public void onForwardError(String traceId, Throwable error) {
        log.error("Forwarding HTTP request failed: traceId {}", traceId, error);
    }

    @Override
    public void onForwardComplete(String traceId, ReceivedResponse response) {
        log.info("Forwarded HTTP response received: traceId {} status {} body {} headers {}", traceId,
                response.getStatus(), response.getBodyAsString(),
                response.getHeaders());
    }
}
