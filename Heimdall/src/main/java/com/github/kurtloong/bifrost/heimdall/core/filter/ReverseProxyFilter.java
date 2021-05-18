package com.github.kurtloong.bifrost.heimdall.core.filter;

import com.github.kurtloong.bifrost.heimdall.config.properties.HeimdallConfigProperties;
import com.github.kurtloong.bifrost.heimdall.core.http.RequestDataExtractor;
import com.github.kurtloong.bifrost.heimdall.core.trace.ProxyingTraceInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021/5/18 14:04
 */
@Slf4j
public class ReverseProxyFilter extends OncePerRequestFilter {
    protected static final String X_FORWARDED_FOR_HEADER = "X-Forwarded-For";
    protected static final String X_FORWARDED_PROTO_HEADER = "X-Forwarded-Proto";
    protected static final String X_FORWARDED_HOST_HEADER = "X-Forwarded-Host";
    protected static final String X_FORWARDED_PORT_HEADER = "X-Forwarded-Port";

    protected final RequestDataExtractor extractor;
    protected final HeimdallConfigProperties configProperties;
    protected final ProxyingTraceInterceptor traceInterceptor;
    public ReverseProxyFilter(RequestDataExtractor extractor, HeimdallConfigProperties configProperties, ProxyingTraceInterceptor traceInterceptor) {
        this.extractor = extractor;
        this.configProperties = configProperties;
        this.traceInterceptor = traceInterceptor;
    }


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest httpServletRequest, @NonNull HttpServletResponse httpServletResponse, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String originUri = extractor.extractUri(httpServletRequest);
        String originHost = extractor.extractHost(httpServletRequest);

        log.debug("Incoming request method {} host {} uri {}", httpServletRequest.getMethod(), originHost, originUri);

        HttpHeaders headers = extractor.extractHttpHeaders(httpServletRequest);
        HttpMethod method = extractor.extractHttpMethod(httpServletRequest);

        String traceId = traceInterceptor.generateTraceId();
        traceInterceptor.onRequestReceived(traceId, method, originHost, originUri, headers);

    }
}
