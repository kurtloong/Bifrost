package com.github.kurtloong.bifrost.heimdall.domain.request;

import com.github.kurtloong.bifrost.heimdall.domain.entity.HttpEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpMethod;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021/5/18 15:15
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class IncomingRequest extends HttpEntity {

    protected HttpMethod method;
    protected String uri;
    protected String host;

}
