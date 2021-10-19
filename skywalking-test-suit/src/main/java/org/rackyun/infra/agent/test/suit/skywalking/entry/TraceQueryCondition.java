package org.rackyun.infra.agent.test.suit.skywalking.entry;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yunhai.hu
 * at 2020/3/13
 */
@Data
public class TraceQueryCondition implements Serializable {
    private Duration queryDuration;
    private String serviceId;
    private String endpointName;
    private String traceState = "ALL";
    private Paging paging = new Paging();
    private String queryOrder = "BY_DURATION";
}
