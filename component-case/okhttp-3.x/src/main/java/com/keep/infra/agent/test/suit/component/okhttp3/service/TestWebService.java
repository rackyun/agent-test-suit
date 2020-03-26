package com.keep.infra.agent.test.suit.component.okhttp3.service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yunhai.hu
 * at 2020/3/17
 */
@Service
public class TestWebService {

    private static final Logger logger = LoggerFactory.getLogger(TestWebService.class);
    private final String url = "http://localhost:2015/okhttpv3";

    @Autowired
    private OkHttpClient okHttpClient;

    public String catNodes(){
        try {
            Request request = new Request.Builder().url(url)
                    .get().build();

            return okHttpClient.newCall(request).execute().body().string();
        } catch (Exception e) {
            logger.error("request cat nodes error", e);
        }
        return "";
    }

}
