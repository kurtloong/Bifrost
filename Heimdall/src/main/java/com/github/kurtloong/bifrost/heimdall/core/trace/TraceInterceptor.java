package com.github.kurtloong.bifrost.heimdall.core.trace;

import com.github.kurtloong.bifrost.heimdall.domain.request.ForwardRequest;
import com.github.kurtloong.bifrost.heimdall.domain.request.IncomingRequest;
import com.github.kurtloong.bifrost.heimdall.domain.response.ReceivedResponse;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021/5/18 15:13
 */
public interface TraceInterceptor {

    void onRequestReceived(String traceId, IncomingRequest request);

    void onNoMappingFound(String traceId, IncomingRequest request);

    void onForwardStart(String traceId, ForwardRequest request);

    void onForwardError(String traceId, Throwable error);

    void onForwardComplete(String traceId, ReceivedResponse response);
}