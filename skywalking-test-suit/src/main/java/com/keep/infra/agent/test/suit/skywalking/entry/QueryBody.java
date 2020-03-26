package com.keep.infra.agent.test.suit.skywalking.entry;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author yunhai.hu
 * at 2020/3/13
 */
@Data
public class QueryBody implements Serializable {
    private String query;
    private Map<String, Object> variables;
}
