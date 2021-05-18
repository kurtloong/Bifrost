package com.github.kurtloong.bifrost.heimdall.constant;

import lombok.Getter;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021/5/18 14:18
 */
@Getter
public enum ErrorStatus {
    /**
     * 服务器出错啦
     */
    UNKNOWN_ERROR(500, "服务器出错啦");
    /**
     * 状态码
     */
    private final Integer code;
    /**
     * 内容
     */
    private final String message;

    ErrorStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
