package org.rackyun.infra.agent.test.suit.skywalking;

import com.google.gson.Gson;
import okhttp3.*;
import org.rackyun.infra.agent.test.suit.skywalking.entry.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author yunhai.hu
 * at 2020/3/12
 */
public class TraceQueryClient {

    private String url;
    private OkHttpClient okHttpClient;
    private Gson gson = new Gson();

    private static final String SEARCH_SERVICES_GRAPHQL = "query searchServices($duration: Duration!, $keyword: " +
            "String!) {\n    services: searchServices(duration: $duration, keyword: $keyword) {\n      key: id\n  " +
            "    label: name\n    }\n  }";

    public TraceQueryClient(String url) {
        this.url = url;
        this.okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).build();
    }

    public Service[] searchServiceCode(String appName) {
        QueryBody queryBody = new QueryBody();
        queryBody.setQuery(SEARCH_SERVICES_GRAPHQL);
        Map<String, Object> variables = new HashMap<>();
        Duration duration = new Duration();
        variables.put("duration", duration);
        variables.put("keyword", appName);
        queryBody.setVariables(variables);

        ServiceResp resp = executeRequest(queryBody, ServiceResp.class);
        if (resp == null || resp.getData() == null || resp.getData().getServices() == null) {
            throw new RuntimeException("skywalking server response error message");
        }
        if (resp.getData().getServices().length > 0) {
            List<Service> filteredService = new ArrayList<>();
            for (Service service : resp.getData().getServices()) {
                if (appName.equals(service.getLabel())) {
                    filteredService.add(service);
                }
            }
            return filteredService.toArray(new Service[0]);
        }
        return new Service[0];
    }

    private static final String SEARCH_TRACE_GRAPHQL = "query queryTraces($condition: TraceQueryCondition) {\n  " +
            "traces: queryBasicTraces(condition: $condition) {\n    data: traces {\n      key: segmentId\n      " +
            "endpointNames\n      duration\n      start\n      isError\n      traceIds\n    }\n    total\n  }}";

    public BasicTrace[] searchTraceList(String serviceId) {
        QueryBody queryBody = new QueryBody();
        queryBody.setQuery(SEARCH_TRACE_GRAPHQL);
        Map<String, Object> variables = new HashMap<>();
        TraceQueryCondition traceQueryCondition = new TraceQueryCondition();
        traceQueryCondition.setServiceId(serviceId);
        traceQueryCondition.setQueryDuration(new Duration());
        variables.put("condition", traceQueryCondition);
        queryBody.setVariables(variables);

        TraceResp resp = executeRequest(queryBody, TraceResp.class);
        if (resp == null || resp.getData() == null || resp.getData().getTraces() == null) {
            throw new RuntimeException("skywalking server response error message");
        }
        if (resp.getData().getTraces().getTotal() > 0) {
            return resp.getData().getTraces().getData();
        }
        return new BasicTrace[0];
    }

    private static final String SEARCH_TRACE_BY_ENDPOINT_GRAPHQL = "query queryTraces($condition: " +
            "TraceQueryCondition) {\n  traces: queryBasicTraces(condition: $condition) {\n    data: traces {\n    " +
            "  key: segmentId\n      endpointNames\n      duration\n      start\n      isError\n      " +
            "traceIds\n    }\n    total\n  }}";

    public BasicTrace[] searchTraceListByEndpointName(String endpointName) {
        QueryBody queryBody = new QueryBody();
        queryBody.setQuery(SEARCH_TRACE_BY_ENDPOINT_GRAPHQL);
        Map<String, Object> variables = new HashMap<>();
        TraceQueryCondition traceQueryCondition = new TraceQueryCondition();
        traceQueryCondition.setEndpointName(endpointName);
        traceQueryCondition.setQueryDuration(new Duration());
        variables.put("condition", traceQueryCondition);
        queryBody.setVariables(variables);

        TraceResp resp = executeRequest(queryBody, TraceResp.class);
        if (resp == null || resp.getData() == null || resp.getData().getTraces() == null) {
            throw new RuntimeException("skywalking server response error message");
        }
        if (resp.getData().getTraces().getTotal() > 0) {
            return resp.getData().getTraces().getData();
        }
        return new BasicTrace[0];
    }

    private <T> T executeRequest(QueryBody queryBody, Class<T> respClass) {
        String bodyStr = gson.toJson(queryBody);
        RequestBody requestBody = RequestBody.create(MediaType.get("application/json"), bodyStr);
        Request request = new Request.Builder().url(this.url).method("POST", requestBody).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() == 200) {
                String respStr = response.body().string();
                return gson.fromJson(respStr, respClass);
            } else {
                throw new RuntimeException("request skywalking search trace error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final String SEARCH_TRACE_DETAIL_GRAPHQL = "query queryTrace($traceId: ID!) {\n  trace: " +
            "queryTrace(traceId: $traceId) {\n    spans {\n      traceId\n      segmentId\n      spanId\n      " +
            "parentSpanId\n      refs {\n        traceId\n        parentSegmentId\n        parentSpanId\n       " +
            " type\n      }\n      serviceCode\n      startTime\n      endTime\n      endpointName\n      " +
            "type\n      peer\n      component\n      isError\n      layer\n      tags {\n        key\n       " +
            " value\n      }\n      logs {\n        time\n        data {\n          key\n          value\n    " +
            "    }\n      }\n    }\n  }\n  }";

    public Span[] searchTraceDetail(String traceId) {
        QueryBody queryBody = new QueryBody();
        queryBody.setQuery(SEARCH_TRACE_DETAIL_GRAPHQL);
        Map<String, Object> variables = new HashMap<>();
        variables.put("traceId", traceId);
        queryBody.setVariables(variables);

        TraceDetailResp resp = executeRequest(queryBody, TraceDetailResp.class);
        if (resp == null || resp.getData() == null || resp.getData().getTrace() == null) {
            throw new RuntimeException("skywalking server response error message");
        }
        return resp.getData().getTrace().getSpans();
    }
}
