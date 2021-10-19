package org.rackyun.infra.agent.test.suit.integration.v2.sw;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rackyun.infra.agent.test.suit.common.AgentTestExecutor;
import org.rackyun.infra.agent.test.suit.common.TestConfig;
import org.rackyun.infra.agent.test.suit.skywalking.AbstractSkywalkingComponentTest;
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
        AgentTestExecutor executor = new AgentTestExecutor(baseDir + "/target/server-springboot-2.x.jar",
                "",
                TestConfig.SW_AGENT_PATH,
                "");
        executor.start();
        try {
            TimeUnit.SECONDS.sleep(30);
            ServiceLoader<AbstractSkywalkingComponentTest> componentTests =
                    ServiceLoader.load(AbstractSkywalkingComponentTest.class);
            for (AbstractSkywalkingComponentTest abstractComponentTest : componentTests) {
                logger.info("component {} will be test", abstractComponentTest.getClass().getCanonicalName());
                abstractComponentTest.setAppName(APP_CODE);
                abstractComponentTest.setUp();
                abstractComponentTest.invokeTestController();
                TimeUnit.SECONDS.sleep(30);
                abstractComponentTest.invokeTestController();
                abstractComponentTest.verifyComponent();
                abstractComponentTest.teardown();
                logger.info(abstractComponentTest.getClass().getCanonicalName() + " test passed");
            }
        } finally {
            executor.close();
        }
    }
}
