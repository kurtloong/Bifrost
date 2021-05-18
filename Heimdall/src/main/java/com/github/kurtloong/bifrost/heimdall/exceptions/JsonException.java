package com.github.kurtloong.bifrost.heimdall.exceptions;

import com.github.kurtloong.bifrost.heimdall.constant.ErrorStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021/5/18 14:16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Slf4j
public class JsonException extends RuntimeException{
    private static final long serialVersionUID = 6642212891860757912L;
    private Integer code;
    private String message;
    private HttpStatus httpStatus;

    public JsonException(ErrorStatus status, HttpStatus httpStatus,Exception e) {
        super(status.getMessage());
        this.code = status.getCode();
        this.message = status.getMessage();
        this.httpStatus = httpStatus;
        log.error(message,e);
    }

    public JsonException(ErrorStatus status,Exception e) {
        super(status.getMessage());
        this.code = status.getCode();
        this.message = status.getMessage();
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        log.error(message,e);
    }

    public JsonException(String message,Exception e) {
        super(message);
        this.code = 500;
        this.message = message;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        log.error(message,e);
    }

    public JsonException(Integer code, String message,HttpStatus httpStatus,Exception e) {
        super(message);
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
        log.error(message,e);
    }
}