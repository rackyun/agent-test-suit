package org.rackyun.infra.agent.test.suit.monitor.client;

import org.apache.catalina.LifecycleException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rackyun.infra.agent.test.suit.monitor.MockFalconAgentServer;
import org.rackyun.infra.agent.test.suit.monitor.client.entry.FalconMetricEntry;
import org.rackyun.infra.agent.test.suit.monitor.util.FalconMetricUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * @author yunhai.hu
 * at 2020/3/18
 */
public class FalconMetricsQueryClientTest {

    private static final String METRIC_DATA = "[{\"endpoint\":\"huyunhaideMacBook-Pro-3\",\"metric\":\"jvm.classes" +
            ".loaded\",\"timestamp\":1584498892,\"step\":60,\"value\":12426.0,\"counterType\":\"GAUGE\"," +
            "\"tags\":\"app=jetcache-test\"},{\"endpoint\":\"huyunhaideMacBook-Pro-3\",\"metric\":\"jvm.classes" +
            ".unloaded\",\"timestamp\":1584498892,\"step\":60,\"value\":1.0,\"counterType\":\"COUNTER\"," +
            "\"tags\":\"app=jetcache-test\"},{\"endpoint\":\"huyunhaideMacBook-Pro-3\",\"metric\":\"cpu.process" +
            ".load\",\"timestamp\":1584498892,\"step\":60,\"value\":0.0,\"counterType\":\"GAUGE\"," +
            "\"tags\":\"app=jetcache-test\"},{\"endpoint\":\"huyunhaideMacBook-Pro-3\",\"metric\":\"gc.PS-MarkSweep" +
            ".count\",\"timestamp\":1584498892,\"step\":60,\"value\":3.0,\"counterType\":\"COUNTER\"," +
            "\"tags\":\"app=jetcache-test,name=PS MarkSweep\"},{\"endpoint\":\"huyunhaideMacBook-Pro-3\"," +
            "\"metric\":\"gc.PS-MarkSweep.time\",\"timestamp\":1584498892,\"step\":60,\"value\":167.0," +
            "\"counterType\":\"COUNTER\",\"tags\":\"app=jetcache-test,name=PS MarkSweep\"}]";
    private MockFalconAgentServer agentServer;
    private FalconMetricsQueryClient metricsQueryClient;

    @Before
    public void setup() {
        agentServer = new MockFalconAgentServer();
        metricsQueryClient = new FalconMetricsQueryClient("http://localhost:1988");
        agentServer.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                agentServer.await();
            }
        }).start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        metricsQueryClient.pushMetrics(METRIC_DATA);
    }

    @After
    public void tearDown() {
        try {
            agentServer.close();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getMetrics() {
        FalconMetricEntry[] metricEntries = metricsQueryClient.getMetrics();
        Map<String, String> tags = new HashMap<>();
        tags.put("app", "jetcache-test");
        assertTrue(FalconMetricUtil.match(metricEntries, "jvm.classes.loaded", tags));
    }
}
