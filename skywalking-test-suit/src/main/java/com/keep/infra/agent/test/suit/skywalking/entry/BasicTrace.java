package com.keep.infra.agent.test.suit.skywalking.entry;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yunhai.hu
 * at 2020/3/13
 */
@Getter
public class BasicTrace implements Serializable {
    @Setter
    private String segmentId;
    private final List<String> endpointNames;
    @Setter private int duration;
    @Setter private String start;
    @Setter private boolean isError;
    private final List<String> traceIds;

    public BasicTrace() {
        this.endpointNames = new ArrayList<>();
        this.traceIds = new ArrayList<>();
    }
}
