package com.keep.infra.agent.test.suit.common.api;

import com.keep.infra.agent.test.suit.common.TestConfig;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author yunhai.hu
 * at 2020/3/18
 */
public abstract class AbstractComponentTest {

    public abstract void setUp() throws Exception;

    public abstract void agentTest() throws Exception;

    public void teardown() throws Exception{
        //do nothing
    }

    public abstract void verifyComponent() throws Exception;

    public abstract void invokeTestController() throws Exception;

    private String appName;

    public String getAppName() {
        return StringUtils.defaultIfEmpty(appName, TestConfig.App.APP_CODE);
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getJarName() throws IOException {
        File directory = new File(".");
        String currentDir = directory.getCanonicalFile().getName();
        return StringUtils.defaultIfEmpty(TestConfig.BUILD_JAR_NAME, currentDir + "-exec.jar");
    }
}
