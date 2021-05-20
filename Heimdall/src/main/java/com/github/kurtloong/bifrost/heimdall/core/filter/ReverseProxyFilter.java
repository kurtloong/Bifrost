package com.github.kurtloong.bifrost.heimdall.core.filter;

import com.github.kurtloong.bifrost.heimdall.config.properties.HeimdallConfigProperties;
import com.github.kurtloong.bifrost.heimdall.core.balancer.PollingBalancer;
import com.github.kurtloong.bifrost.heimdall.core.http.RequestDataExtractor;
import com.github.kurtloong.bifrost.heimdall.core.mappings.RouteMapping;
import com.github.kurtloong.bifrost.heimdall.core.trace.ProxyingTraceInterceptor;
import com.github.kurtloong.bifrost.heimdall.domain.entity.RequestDataEntity;
import com.github.kurtloong.bifrost.heimdall.domain.entity.ServerConfigEntity;
import com.github.kurtloong.bifrost.heimdall.exceptions.JsonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.util.CollectionUtils.isEmpty;

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
    protected final RouteMapping routeMapping;
    protected final PollingBalancer pollingBalancer;
    public ReverseProxyFilter(RequestDataExtractor extractor, HeimdallConfigProperties configProperties, ProxyingTraceInterceptor traceInterceptor, RouteMapping routeMapping, PollingBalancer pollingBalancer) {
        this.extractor = extractor;
        this.configProperties = configProperties;
        this.traceInterceptor = traceInterceptor;
        this.routeMapping = routeMapping;
        this.pollingBalancer = pollingBalancer;
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

        Map<String, ServerConfigEntity> map = routeMapping.getRoutesMapByHost(originHost);

        if (CollectionUtils.isEmpty(map)){

            traceInterceptor.onNoMappingFound(traceId, method, originHost, originUri, headers);

            log.debug(String.format("Forwarding: %s %s %s -> no mapping found", method, originHost, originUri));

            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.getWriter().println("Unsupported domain");
            return;
        }else {
            log.debug(String.format("Forwarding: %s %s %s -> %s", method, originHost, originUri, pollingBalancer.chooseDestination(map)));
        }


        byte[] body = extractor.extractBody(httpServletRequest);
        addForwardHeaders(httpServletRequest, headers);

        RequestDataEntity dataToForward = new RequestDataEntity(method, originHost, originUri, headers, body, httpServletRequest);
//        preForwardRequestInterceptor.intercept(dataToForward, mapping);
//        if (dataToForward.isNeedRedirect() && !isBlank(dataToForward.getRedirectUrl())) {
//            log.debug(String.format("Redirecting to -> %s", dataToForward.getRedirectUrl()));
//            httpServletResponse.sendRedirect(dataToForward.getRedirectUrl());
//            return;
//        }
//
//        ResponseEntity<byte[]> responseEntity =
//                requestForwarder.forwardHttpRequest(dataToForward, traceId, mapping);
//        this.processResponse(httpServletResponse, responseEntity);




    }


    protected void addForwardHeaders(HttpServletRequest request, HttpHeaders headers) {
        List<String> forwordedFor = headers.get(X_FORWARDED_FOR_HEADER);
        if (isEmpty(forwordedFor)) {
            forwordedFor = new ArrayList<>(1);
        }
        forwordedFor.add(request.getRemoteAddr());
        headers.put(X_FORWARDED_FOR_HEADER, forwordedFor);
        headers.set(X_FORWARDED_PROTO_HEADER, request.getScheme());
        headers.set(X_FORWARDED_HOST_HEADER, request.getServerName());
        headers.set(X_FORWARDED_PORT_HEADER, valueOf(request.getServerPort()));
    }

    protected void processResponse(HttpServletResponse response, ResponseEntity<byte[]> responseEntity) {
        response.setStatus(responseEntity.getStatusCode().value());
        responseEntity.getHeaders().forEach((name, values) ->
                values.forEach(value -> response.addHeader(name, value))
        );
        if (responseEntity.getBody() != null) {
            try {
                response.getOutputStream().write(responseEntity.getBody());
            } catch (IOException e) {
                throw new JsonException("Error writing body of HTTP response", e);
            }
        }
    }
}
