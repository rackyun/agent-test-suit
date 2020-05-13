package com.keep.infra.agent.test.suit.component.okhttp3.kmonitor;

import com.keep.infra.agent.test.suit.common.AgentTestExecutor;
import com.keep.infra.agent.test.suit.common.Constants;
import com.keep.infra.agent.test.suit.common.TestConfig;
import com.keep.infra.agent.test.suit.keep.monitor.AbstractKeepMonitorComponentTest;
import com.keep.infra.agent.test.suit.keep.monitor.MockFalconAgentServer;
import com.keep.infra.agent.test.suit.keep.monitor.client.FalconMetricsQueryClient;
import com.keep.infra.agent.test.suit.keep.monitor.client.entry.FalconMetricEntry;
import com.keep.infra.agent.test.suit.keep.monitor.util.FalconMetricUtil;
import com.keep.infra.agent.test.suit.mock.server.MockServer;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.catalina.LifecycleException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author yunhai.hu
 * at 2020/3/13
 */
public class ComponentTest extends AbstractKeepMonitorComponentTest {

    private static final String[] NAME_POOL = new String[]{"jack", "tom", "rose", "mick"};
    private static final String TEST_API_PATH = "/test/okhttp/v3/run";
    private MockFalconAgentServer agentServer;
    private FalconMetricsQueryClient metricsQueryClient;
    private Random random = new Random();
    private MockServer mockServer;

    @Before
    public void setUp() throws Exception {
        metricsQueryClient = new FalconMetricsQueryClient("http://localhost:1988");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void agentTest() throws Exception {
        agentServer = new MockFalconAgentServer();
        mockServer = new MockServer();
        mockServer.start();
        agentServer.start();

        File directory = new File(".");
        String baseDir = directory.getCanonicalPath();
        AgentTestExecutor executor = new AgentTestExecutor(baseDir + "/target/" + getJarName(),
                "",
                TestConfig.KMONITOR_AGENT_PATH,
                "");
        executor.start();
        try {
            TimeUnit.SECONDS.sleep(Constants.SERVER_START_SECONDS);
            invokeTestController();
            TimeUnit.SECONDS.sleep(60);

            verifyComponent();
        } finally {
            executor.close();
            agentServer.close();
            mockServer.close();
        }
    }

    @Override
    public void verifyComponent() throws Exception {
        FalconMetricEntry[] metricEntries = metricsQueryClient.getMetrics();
        FalconMetricEntry[] targetMetric = FalconMetricUtil.findMetric(metricEntries, "http.client.timer.count");
        assertTrue(targetMetric.length > 0);
    }

    private OkHttpClient httpClient = new OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS).build();

    @Override
    public void invokeTestController() throws Exception {
        try(Response response = httpClient.newCall(new Request.Builder().url(TestConfig.TEST_SERVER_URL + TEST_API_PATH +
                "?name=" + NAME_POOL[Math.abs(random.nextInt()) % 4]).
                get().build()).execute()) {
            int code = response.code();
            String respBody = response.body().string();
            assertEquals(200, code);
            assertEquals("OK", respBody);
        }
    }

}
