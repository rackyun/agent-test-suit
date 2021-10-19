package org.rackyun.infra.agent.test.suit.component.okhttp3.kmonitor;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rackyun.infra.agent.test.suit.common.AgentTestExecutor;
import org.rackyun.infra.agent.test.suit.common.Constants;
import org.rackyun.infra.agent.test.suit.common.TestConfig;
import org.rackyun.infra.agent.test.suit.mock.server.MockServer;
import org.rackyun.infra.agent.test.suit.monitor.AbstractMonitorComponentTest;
import org.rackyun.infra.agent.test.suit.monitor.client.PromethuesMetricsQueryClient;

import java.io.File;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author yunhai.hu
 * at 2020/3/13
 */
public class ComponentTest extends AbstractMonitorComponentTest {

    private static final String[] NAME_POOL = new String[]{"jack", "tom", "rose", "mick"};
    private static final String TEST_API_PATH = "/test/okhttp/v3/run";
    private PromethuesMetricsQueryClient metricsQueryClient;
    private Random random = new Random();
    private MockServer mockServer;

    @Before
    public void setUp() throws Exception {
        metricsQueryClient = new PromethuesMetricsQueryClient(TestConfig.TEST_SERVER_URL);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void agentTest() throws Exception {
        mockServer = new MockServer();
        mockServer.start();

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
            mockServer.close();
        }
    }

    @Override
    public void verifyComponent() throws Exception {

        assertTrue("okhttp client metric is not exist", metricsQueryClient.validateMetricsWithOutValue(
                "http_client_requests_seconds_sum", new HashMap<>()));
    }

    private OkHttpClient httpClient = new OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS).build();

    @Override
    public void invokeTestController() throws Exception {
        try (Response response =
                     httpClient.newCall(new Request.Builder().url(TestConfig.TEST_SERVER_URL + TEST_API_PATH +
                             "?name=" + NAME_POOL[Math.abs(random.nextInt()) % 4]).
                             get().build()).execute()) {
            int code = response.code();
            String respBody = response.body().string();
            assertEquals(200, code);
            assertEquals("OK", respBody);
        }
    }

}
