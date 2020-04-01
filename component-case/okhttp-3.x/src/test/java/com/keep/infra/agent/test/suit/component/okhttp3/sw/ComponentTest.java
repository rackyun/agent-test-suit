package com.keep.infra.agent.test.suit.component.okhttp3.sw;

import com.keep.infra.agent.test.suit.common.AgentTestExecutor;
import com.keep.infra.agent.test.suit.common.Constants;
import com.keep.infra.agent.test.suit.common.TestConfig;
import com.keep.infra.agent.test.suit.mock.server.MockServer;
import com.keep.infra.agent.test.suit.skywalking.AbstractSkywalkingComponentTest;
import com.keep.infra.agent.test.suit.skywalking.TraceQueryClient;
import com.keep.infra.agent.test.suit.skywalking.entry.BasicTrace;
import com.keep.infra.agent.test.suit.skywalking.entry.Service;
import com.keep.infra.agent.test.suit.skywalking.entry.Span;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author yunhai.hu
 * at 2020/3/13
 */
public class ComponentTest extends AbstractSkywalkingComponentTest {

    private static final String[] NAME_POOL = new String[]{"jack", "tom", "rose", "mick"};
    private static final String COMPONENT_NAME = "OKHttp";
    private static final String TEST_API_PATH = "/test/okhttp/v3/run";
    private static final String DEFAULT_APP_CODE = "agent-test-okhttp-v3";
    private TraceQueryClient traceQueryClient;
    private final Random random = new Random();

    private MockServer mockServer;

    @Before
    public void setUp() throws Exception {
        mockServer = new MockServer();
        mockServer.start();
        traceQueryClient = new TraceQueryClient("http://172.16.1.40:12800/graphql");
    }

    @After
    public void teardown() throws Exception{
        mockServer.close();
    }

    @Test
    public void agentTest() throws Exception {
        File directory = new File(".");
        String baseDir = directory.getCanonicalPath();
        String appName = "agent-test-okhttp-v3-" + random.nextInt(9999);
        AgentTestExecutor executor = new AgentTestExecutor(baseDir + "/target/" + getJarName(),
                "",
                TestConfig.SW_AGENT_PATH,
                "");
        executor.start();
        try {
            TimeUnit.SECONDS.sleep(Constants.SERVER_START_SECONDS);
            invokeTestController();
            TimeUnit.SECONDS.sleep(Constants.WAIT_TRACE_REPORT_SECONDS);
            invokeTestController();
            verifyComponent();
        } finally {
            executor.close();
        }
    }

    @Override
    public void verifyComponent() throws Exception {
        Service[] services = traceQueryClient.searchServiceCode(StringUtils.isEmpty(getAppName()) ? DEFAULT_APP_CODE : getAppName());
        assertEquals(1, services.length);
        BasicTrace[] traces = traceQueryClient.searchTraceListByEndpointName(TEST_API_PATH);
        assertTrue(traces.length > 0);
        Span[] spans = traceQueryClient.searchTraceDetail(traces[0].getTraceIds().get(0));
        boolean hasComponent = false;
        for (Span span : spans) {
            if (COMPONENT_NAME.equals(span.getComponent())) {
                hasComponent = true;
            }
        }
        assertTrue(hasComponent);
    }

    private OkHttpClient httpClient = new OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS).build();

    @Override
    public void invokeTestController() throws Exception {
        try(Response response = httpClient.newCall(new Request.Builder().url("http://localhost:8888" + TEST_API_PATH +
                "?name=" + NAME_POOL[Math.abs(random.nextInt()) % 4]).
                get().build()).execute()) {
            int code = response.code();
            String respBody = response.body().string();
            assertEquals(200, code);
            assertEquals("OK", respBody);
        }
    }

}
