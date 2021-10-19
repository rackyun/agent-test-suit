package org.rackyun.infra.agent.test.suit.common;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

/**
 * @author yunhai.hu
 * at 2020/3/18
 */
public class TestConfig {

    public static final String SW_AGENT_PATH;

    public static final String BUILD_JAR_NAME;

    public static final String KMONITOR_AGENT_PATH;

    public static final String TEST_SERVER_URL;

    public static boolean START_SERVER;

    static {
        SW_AGENT_PATH = System.getProperty("skywalking-agent-path", "/Users/yunhai.hu/Documents/projects/java" +
                "-projects/incubator-skywalking/skywalking-agent/skywalking-agent.jar");
        BUILD_JAR_NAME = System.getProperty("build-jar-name");

        KMONITOR_AGENT_PATH = System.getProperty("keep-monitor-agent-path", "/Users/yunhai.hu/Documents/projects/java" +
                "-projects/keep-monitor" +
                "/output/kmonitor-agent.jar");

        TEST_SERVER_URL = System.getProperty("test-server-url", "http://localhost:8888");
        START_SERVER = TEST_SERVER_URL.equals("http://localhost:8888");
    }

    public static class App {
        public static String APP_CODE;

        static {
            Configurations configs = new Configurations();
            try {
                Configuration config =
                        configs.properties(TestConfig.class.getClassLoader().getResource("bootstrap.properties").getFile());
                APP_CODE = config.getString("spring.application.name");
            } catch (ConfigurationException e) {
                APP_CODE = null;
                e.printStackTrace();
            }
        }

    }

    public enum AGENT_SWITCH {
        ON, OFF
    }


}
