package com.keep.infra.agent.test.suit.skywalking.entry;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yunhai.hu
 * at 2020/3/13
 */
@Data
public class TraceBrief implements Serializable {
    private BasicTrace[] data;
    private int total;
}
