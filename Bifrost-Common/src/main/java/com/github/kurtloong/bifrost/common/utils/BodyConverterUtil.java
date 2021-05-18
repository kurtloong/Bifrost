package com.github.kurtloong.bifrost.common.utils;

import java.nio.charset.StandardCharsets;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021/5/18 15:17
 */
public class BodyConverterUtil {
    public static String convertBodyToString(byte[] body) {
        if (body == null) {
            return null;
        }
        return new String(body, StandardCharsets.UTF_8);
    }

    public static byte[] convertStringToBody(String body) {
        if (body == null) {
            return null;
        }
        return body.getBytes(StandardCharsets.UTF_8);
    }
}
