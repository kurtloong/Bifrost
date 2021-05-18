package com.github.kurtloong.bifrost.heimdall.core.http;

import com.github.kurtloong.bifrost.heimdall.exceptions.JsonException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import static com.google.common.io.ByteStreams.toByteArray;
import static java.util.Collections.list;
import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021/5/18 14:05
 */
public class RequestDataExtractor {

    public byte[] extractBody(HttpServletRequest request) {
        try {
            return toByteArray(request.getInputStream());
        } catch (IOException e) {
            throw new JsonException("Error extracting body of HTTP request with URI: " + extractUri(request), e);
        }
    }

    public HttpHeaders extractHttpHeaders(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            List<String> value = list(request.getHeaders(name));
            headers.put(name, value);
        }
        return headers;
    }

    public HttpMethod extractHttpMethod(HttpServletRequest request) {
        return HttpMethod.resolve(request.getMethod());
    }

    public String extractUri(HttpServletRequest request) {
        return request.getRequestURI() + getQuery(request);
    }

    public String extractHost(HttpServletRequest request) {
        return request.getServerName();
    }

    protected String getQuery(HttpServletRequest request) {
        return request.getQueryString() == null ? EMPTY : "?" + request.getQueryString();
    }
}

