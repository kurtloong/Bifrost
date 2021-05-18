package com.github.kurtloong.bifrost.heimdall.domain.response;

import com.github.kurtloong.bifrost.heimdall.domain.entity.HttpEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import static com.github.kurtloong.bifrost.common.utils.BodyConverterUtil.convertBodyToString;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021/5/18 15:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ReceivedResponse extends HttpEntity {

    protected HttpStatus status;
    protected byte[] body;

    public String getBodyAsString() { return convertBodyToString(body); }

}
