package com.keep.infra.agent.test.suit.integration.v2.kmonitor;

import com.keep.infra.agent.test.suit.common.AgentTestExecutor;
import com.keep.infra.agent.test.suit.common.TestConfig;
import com.keep.infra.agent.test.suit.keep.monitor.AbstractKeepMonitorComponentTest;
import com.keep.infra.agent.test.suit.keep.monitor.MockFalconAgentServer;
import com.keep.infra.agent.test.suit.mock.server.MockServer;
import org.apache.catalina.LifecycleException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ServiceLoader;
import java.util.concurrent.TimeUnit;

/**
 * @author yunhai.hu
 * at 2020/3/18
 */
public class IntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(IntegrationTest.class);
    private static final String APP_CODE = "integration-v2";
    private MockFalconAgentServer agentServer;
    private MockServer mockServer;

    @Before
    public void setUp() {
        agentServer = new MockFalconAgentServer();
        mockServer = new MockServer();
        mockServer.start();
        agentServer.start();
    }

    @After
    public void tearDown() {
        try {
            agentServer.close();
            mockServer.close();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void agentTest() throws Exception {


        File directory = new File(".");
        String baseDir = directory.getCanonicalPath();
        AgentTestExecutor executor = new AgentTestExecutor(baseDir + "/target/server-springboot-2.x.jar",
                "",
                TestConfig.KMONITOR_AGENT_PATH,
                "");
        executor.start();
        try {
            TimeUnit.SECONDS.sleep(30);
            ServiceLoader<AbstractKeepMonitorComponentTest> componentTests = ServiceLoader.load(AbstractKeepMonitorComponentTest.class);
            for (AbstractKeepMonitorComponentTest abstractComponentTest : componentTests) {
                logger.info("component {} will be test", abstractComponentTest.getClass().getCanonicalName());
                abstractComponentTest.setAppName(APP_CODE);
                abstractComponentTest.setUp();
                abstractComponentTest.invokeTestController();
            }

            TimeUnit.SECONDS.sleep(60);

            for (AbstractKeepMonitorComponentTest abstractComponentTest : componentTests) {
                abstractComponentTest.verifyComponent();
                abstractComponentTest.teardown();
                logger.info(abstractComponentTest.getClass().getCanonicalName() + " test passed");
            }
        } finally {
            executor.close();

        }
    }
}
