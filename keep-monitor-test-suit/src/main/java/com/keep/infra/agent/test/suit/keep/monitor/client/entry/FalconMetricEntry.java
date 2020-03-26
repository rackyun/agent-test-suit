package com.keep.infra.agent.test.suit.keep.monitor.client.entry;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yunhai.hu
 * at 2020/3/18
 */
@Data
public class FalconMetricEntry implements Serializable {

    private String endpoint;
    private String metric;
    private long timestamp;
    private int step;
    private float value;
    private String counterType;
    private String tags;
}
