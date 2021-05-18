package com.github.kurtloong.bifrost.heimdall.domain.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.github.kurtloong.bifrost.common.utils.BodyConverterUtil.convertBodyToString;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021/5/18 15:16
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ForwardRequest extends IncomingRequest {

    protected String mappingName;
    protected byte[] body;



    public String getBodyAsString() { return convertBodyToString(body); }

}