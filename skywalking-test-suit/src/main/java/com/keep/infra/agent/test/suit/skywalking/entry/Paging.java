package com.keep.infra.agent.test.suit.skywalking.entry;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yunhai.hu
 * at 2020/3/13
 */
@Data
public class Paging implements Serializable {
    private int pageNum = 1;
    private int pageSize = 15;
    private boolean needTotal = true;
}
