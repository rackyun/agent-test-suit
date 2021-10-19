package org.rackyun.infra.agent.test.suit.skywalking;

import org.rackyun.infra.agent.test.suit.skywalking.entry.BasicTrace;
import org.rackyun.infra.agent.test.suit.skywalking.entry.Service;
import org.rackyun.infra.agent.test.suit.skywalking.entry.Span;

import static org.junit.Assert.assertTrue;

/**
 * @author yunhai.hu
 * at 2020/3/12
 */
public class TraceQueryClientTest {

    private TraceQueryClient traceQueryClient;

    @org.junit.Before
    public void setUp() throws Exception {
        traceQueryClient = new TraceQueryClient("https://zipkin.sre.gotokeep.com/graphql");
    }

    @org.junit.Test
    public void searchServiceCode() {

        Service[] services = traceQueryClient.searchServiceCode("account");
        assertTrue(services.length > 0);
    }

    @org.junit.Test
    public void searchTrace() {
        BasicTrace[] traces = traceQueryClient.searchTraceList("717");
        assertTrue(traces.length > 0);
    }

    @org.junit.Test
    public void searchTraceDetail() {
        BasicTrace[] traces = traceQueryClient.searchTraceList("717");
        if (traces.length > 0) {
            Span[] spans = traceQueryClient.searchTraceDetail(traces[0].getTraceIds().get(0));
            assertTrue(spans.length > 0);
        }
    }
}
