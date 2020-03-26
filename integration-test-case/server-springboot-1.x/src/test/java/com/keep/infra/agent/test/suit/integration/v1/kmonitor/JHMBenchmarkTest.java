package com.keep.infra.agent.test.suit.integration.v1.kmonitor;

import com.keep.infra.agent.test.suit.common.AgentTestExecutor;
import com.keep.infra.agent.test.suit.common.TestConfig;
import com.keep.infra.agent.test.suit.keep.monitor.AbstractKeepMonitorComponentTest;
import com.keep.infra.agent.test.suit.mock.server.MockServer;
import org.apache.catalina.LifecycleException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yunhai.hu
 * at 2020/3/20
 */
@State(Scope.Benchmark)
public class JHMBenchmarkTest {

    private static final Logger logger = LoggerFactory.getLogger(JHMBenchmarkTest.class);
    private static final String APP_CODE = "agent-test-integration-v1";

    private MockServer mockServer = new MockServer();

    @Before
    public void testSetUp() {
        mockServer.start();
    }

    @After
    public void testTearDown() {
        try {
            mockServer.close();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void runBenchmarkTest() throws Exception {

        try {
            Options options = new OptionsBuilder()
                    .include(this.getClass().getName() + ".*")
                    .mode(Mode.Throughput)
                    .timeUnit(TimeUnit.SECONDS)
                    .warmupIterations(3)
                    .threads(50)
                    .measurementIterations(5)
                    .measurementTime(TimeValue.seconds(10))
                    .forks(1)
                    .shouldFailOnError(true)
                    .shouldDoGC(true)
                    .resultFormat(ResultFormatType.JSON)
                    .result("target/jmh.json")
                    .build();

            new Runner(options).run();
        } finally {
            //保证
            this.shutdownTargetServer();
        }

    }

    private List<AbstractKeepMonitorComponentTest> componentTestList = new ArrayList<>();
    private AtomicInteger counter = new AtomicInteger(0);
    private AgentTestExecutor executor;

    @Param({"OFF", "ON"})
    private TestConfig.AGENT_SWITCH agentSwitch = TestConfig.AGENT_SWITCH.OFF;

    @Setup
    public void prepare() throws Exception {
        ServiceLoader<AbstractKeepMonitorComponentTest> componentTests = ServiceLoader.load(AbstractKeepMonitorComponentTest.class);
        for (AbstractKeepMonitorComponentTest abstractComponentTest : componentTests) {
            abstractComponentTest.setAppName(APP_CODE);
            try {
                abstractComponentTest.setUp();
                componentTestList.add(abstractComponentTest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        startTargetServer();
    }

    @TearDown
    public void shutdown() throws Exception {
        shutdownTargetServer();
    }

    private void startTargetServer() throws Exception {
        File directory = new File(".");
        String baseDir = directory.getCanonicalPath();
        executor = new AgentTestExecutor(baseDir + "/target/server-springboot-1.x.jar",
                "",
                TestConfig.AGENT_SWITCH.ON.equals(agentSwitch) ? TestConfig.KMONITOR_AGENT_PATH : "",
                "");
        executor.start();
        logger.info("prepare target server ing.......");
        TimeUnit.SECONDS.sleep(30);
        logger.info("target server started, test will be start.");
    }

    private void shutdownTargetServer() {
        if (executor != null) {
            logger.info("target server shutdown ....");
            try {
                executor.close();
            } catch (Exception e) {
                logger.error("shutdown target server error.", e);
            }
        }
    }

    @Benchmark
    public void testApiInvoke() throws Exception {
        AbstractKeepMonitorComponentTest componentTest =
                componentTestList.get(counter.getAndIncrement() % componentTestList.size());
        componentTest.invokeTestController();
    }

}
