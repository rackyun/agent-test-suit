package org.rackyun.infra.agent.test.suit.integration.v1.kmonitor;

import org.apache.catalina.LifecycleException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rackyun.infra.agent.test.suit.common.AgentTestExecutor;
import org.rackyun.infra.agent.test.suit.common.TestConfig;
import org.rackyun.infra.agent.test.suit.mock.server.MockServer;
import org.rackyun.infra.agent.test.suit.monitor.AbstractMonitorComponentTest;
import org.rackyun.infra.agent.test.suit.monitor.MockFalconAgentServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author yunhai.hu
 * at 2020/3/18
 */
public class IntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(IntegrationTest.class);
    private static final String APP_CODE = "integration-v1";
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

    //    @Test
    public void getSystemResources() throws Exception {
        Enumeration<URL> configs = ClassLoader.getSystemResources("META-INF/" + "infra-log-config");
        while (configs.hasMoreElements()) {
            URL u = configs.nextElement();
            if (u != null) {
                for (String configName : parse(u)) {
                    logger.info("config name is {}", configName);
                }
            }
        }
    }

    private Set<String> parse(URL u) {
        Set<String> configNames = new HashSet<>();
        try (InputStream in = u.openStream(); BufferedReader r = new BufferedReader(new InputStreamReader(in,
                "utf-8"))) {
            int lc = 1;
            while ((lc = parseLine(u, r, lc, configNames)) >= 0) ;
        } catch (IOException e) {
            fail(u.getFile(), "Error reading configuration file");
        }
        return configNames;
    }

    private int parseLine(URL u, BufferedReader r, int lc,
                          Set<String> names)
            throws IOException {
        String ln = r.readLine();
        if (ln == null) {
            return -1;
        }
        int ci = ln.indexOf('#');
        if (ci >= 0) ln = ln.substring(0, ci);
        ln = ln.trim();
        int n = ln.length();
        if (n != 0) {
            if ((ln.indexOf(' ') >= 0) || (ln.indexOf('\t') >= 0))
                fail(u.getFile(), "Illegal configuration-file syntax");
            int cp = ln.codePointAt(0);
            if (!Character.isJavaIdentifierStart(cp))
                fail(u.getFile(), "Illegal provider-class name: " + ln);
            for (int i = Character.charCount(cp); i < n; i += Character.charCount(cp)) {
                cp = ln.codePointAt(i);
                if (!Character.isJavaIdentifierPart(cp) && (cp != '.'))
                    fail(u.getFile(), "Illegal provider-class name: " + ln);
            }
            if (!names.contains(ln))
                names.add(ln);
        }
        return lc + 1;
    }

    private static void fail(String fileName, String msg) {
        throw new RuntimeException(fileName + ": " + msg);
    }

    @Test
    public void agentTest() throws Exception {
        File directory = new File(".");
        String baseDir = directory.getCanonicalPath();
        AgentTestExecutor executor = null;
        if (TestConfig.START_SERVER) {
            executor = new AgentTestExecutor(baseDir + "/target/server-springboot-1.x.jar",
                    "",
                    TestConfig.KMONITOR_AGENT_PATH,
                    "");
            executor.start();
        }
        try {
            TimeUnit.SECONDS.sleep(30);
            ServiceLoader<AbstractMonitorComponentTest> componentTests =
                    ServiceLoader.load(AbstractMonitorComponentTest.class);
            for (AbstractMonitorComponentTest abstractComponentTest : componentTests) {
                logger.info("component {} will be test", abstractComponentTest.getClass().getCanonicalName());
                abstractComponentTest.setAppName(APP_CODE);
                abstractComponentTest.setUp();
                abstractComponentTest.invokeTestController();
            }

            TimeUnit.SECONDS.sleep(60);

            for (AbstractMonitorComponentTest abstractComponentTest : componentTests) {
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
