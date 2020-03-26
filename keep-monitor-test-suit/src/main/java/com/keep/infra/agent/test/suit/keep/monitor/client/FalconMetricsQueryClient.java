package com.keep.infra.agent.test.suit.keep.monitor.client;

import com.google.gson.Gson;
import com.keep.infra.agent.test.suit.keep.monitor.client.entry.FalconMetricEntry;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author yunhai.hu
 * at 2020/3/18
 */
public class FalconMetricsQueryClient {

    private static final Logger logger = LoggerFactory.getLogger(FalconMetricsQueryClient.class);
    private String url;
    private OkHttpClient okHttpClient;
    private Gson gson = new Gson();

    public FalconMetricsQueryClient(String url) {
        this.url = url;
        this.okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).build();
    }

    public FalconMetricEntry[] getMetrics() {
        return executeGetRequest(FalconMetricEntry[].class);
    }

    private <T> T executeGetRequest(Class<T> respClass) {
        Request request = new Request.Builder().url(this.url + "/v1/get").get().build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() == 200) {
                String respStr = response.body().string();
                return gson.fromJson(respStr, respClass);
            } else {
                throw new RuntimeException("request falcon get metric error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void pushMetrics(String requestBodyStr) {
        RequestBody requestBody = RequestBody.create(MediaType.get("application/json"), requestBodyStr);
        Request request = new Request.Builder().url(this.url + "/v1/push").method("POST", requestBody).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() == 200) {
                String respStr = response.body().string();

            } else {
                throw new RuntimeException("request skywalking search trace error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
