package com.keep.infra.agent.test.suit.skywalking.entry;

import lombok.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yunhai.hu
 * at 2020/3/13
 */
@Data
public class Duration implements Serializable {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HHmm");

    private String start;
    private String end;
    private String step;

    public Duration() {
        long endTime = System.currentTimeMillis();
        long startTime = endTime - 1 * 60 * 1000;
        this.start = SIMPLE_DATE_FORMAT.format(new Date(startTime));
        this.end = SIMPLE_DATE_FORMAT.format(new Date(endTime));
        this.step = "MINUTE";
    }
}