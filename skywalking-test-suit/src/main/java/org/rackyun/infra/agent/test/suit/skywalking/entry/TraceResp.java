package org.rackyun.infra.agent.test.suit.skywalking.entry;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yunhai.hu
 * at 2020/3/13
 */
@Data
public class TraceResp implements Serializable {
    private TraceData data;
}
