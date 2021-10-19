package org.rackyun.infra.agent.test.suit.monitor.client;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author yunhai.hu
 * at 2021/10/18
 */
public class PromethuesMetricsQueryClient {
    private static final Logger logger = LoggerFactory.getLogger(PromethuesMetricsQueryClient.class);
    private String url;
    private OkHttpClient okHttpClient;
    private Gson gson = new Gson();

    public PromethuesMetricsQueryClient(String url) {
        this.url = url;
        this.okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).build();
    }

    public boolean validateMetrics(String metrics, Float value, Map<String, String> tags) {

        Request request = new Request.Builder().url(this.url + "/actuator/prometheus").get().build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() != 200 || response.body() == null) {
                return false;
            }
            Pattern pattern = Pattern.compile(metrics + "\\{(([\\w-]+=\"([\\w- ])+\",)+)\\}\\s([0-9E\\.]+)");
            List<String> promContents = IOUtils.readLines(response.body().byteStream(),
                    response.body().contentType().charset());
            LINE_CONTINUE:
            for (String line : promContents) {
                Matcher matcher = pattern.matcher(line);
                if (!matcher.find()) {
                    continue;
                }
                String labelTxt = matcher.group(1);
                float realValue = Float.parseFloat(matcher.group(4));
                String[] labelPairTxtList = labelTxt.split(",");
                Map<String, String> labels = new HashMap<>();
                for (String labelPairTxt : labelPairTxtList) {
                    if (StringUtils.isBlank(labelPairTxt)) {
                        continue;
                    }
                    String[] splitTxt = labelPairTxt.split("=");
                    if (splitTxt.length == 2) {
                        labels.put(splitTxt[0], splitTxt[1].replaceAll("\\\"", ""));
                    }
                }
                for (Map.Entry<String, String> entry : tags.entrySet()) {
                    if (labels.containsKey(entry.getKey()) && !labels.get(entry.getKey()).equals(entry.getValue())) {
                        continue LINE_CONTINUE;
                    }
                }
                if (value == null || realValue == value) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean validateMetricsWithOutValue(String metrics, Map<String, String> tags) {
        return validateMetrics(metrics, null, tags);
    }
}
