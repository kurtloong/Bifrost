package com.github.kurtloong.bifrost.common.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.OkHttpClient.Builder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author kurt.loong
 * @version 1.0
 * @date 2021-04-29 11:28
 */
@Slf4j
public class HttpClientUtil {

    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient.Builder()
            .readTimeout(1,TimeUnit.SECONDS)
            .build();

    public static <T> void  post(String url,T body){
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), JsonUtil.toJSONString(body));
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = HTTP_CLIENT.newCall(request);
        try {
            call.execute();
        } catch (IOException e) {
            log.error("http failed!!",e);
        }
    }
}
