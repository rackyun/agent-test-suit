package com.keep.infra.agent.test.suit.integration.v1.sw;

import com.keep.infra.agent.test.suit.common.AgentTestExecutor;
import com.keep.infra.agent.test.suit.common.TestConfig;
import com.keep.infra.agent.test.suit.skywalking.AbstractSkywalkingComponentTest;
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
    private static final String APP_CODE = "integration-v1";

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void agentTest() throws Exception {
        File directory = new File(".");
        String baseDir = directory.getCanonicalPath();
        AgentTestExecutor executor = null;
        if (TestConfig.START_SERVER) {
            executor = new AgentTestExecutor(baseDir + "/target/server-springboot-1.x.jar",
                    "",
                    TestConfig.SW_AGENT_PATH,
                    "");
            executor.start();
        }
        try {
            TimeUnit.SECONDS.sleep(30);
            ServiceLoader<AbstractSkywalkingComponentTest> componentTests = ServiceLoader.load(AbstractSkywalkingComponentTest.class);
            for (AbstractSkywalkingComponentTest abstractComponentTest : componentTests) {
                logger.info("component {} will be test", abstractComponentTest.getClass().getCanonicalName());
                abstractComponentTest.setAppName(APP_CODE);
                abstractComponentTest.setUp();
                abstractComponentTest.invokeTestController();
            }
            TimeUnit.SECONDS.sleep(60);
            for (AbstractSkywalkingComponentTest abstractComponentTest : componentTests) {
                abstractComponentTest.invokeTestController();
                abstractComponentTest.verifyComponent();
                abstractComponentTest.teardown();
                logger.info(abstractComponentTest.getClass().getCanonicalName() + " test passed");
            }
        } finally {
            if (executor != null) {
                executor.close();
            }
        }
    }
}
