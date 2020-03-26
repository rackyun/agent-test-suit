package com.keep.infra.agent.test.suit.skywalking.entry;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yunhai.hu
 * at 2020/3/13
 */
@Data
public class Span implements Serializable {
    private String traceId;
    private String segmentId;
    private int spanId;
    private int parentSpanId;
    private Ref[] refs;
    private String serviceCode;
    private long startTime;
    private long endTime;
    private String endpointName;
    private String type;
    private String peer;
    private String component;
    private boolean isError;
    private String layer;
    private Tag[] tags;

    @Data
    public static class Ref {
        private String traceId;
        private String parentSegmentId;
        private int parentSpanId;
        private String type;
    }
}
