package com.keep.infra.agent.test.suit.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yunhai.hu
 * at 2020/3/12
 */
@Slf4j
public class AgentTestExecutor implements AutoCloseable {

    private String pid;
    private boolean started = false;

    private Process process;

    private String targetJarPath;
    private String mainClass;
    private String targetAgentPath;
    private String agentArg;

    public AgentTestExecutor(String targetJarPath, String mainClass, String targetAgentPath, String agentArg) {
        this.targetJarPath = targetJarPath;
        this.mainClass = mainClass;
        this.targetAgentPath = targetAgentPath;
        this.agentArg = agentArg;

    }

    public InputStream start() throws IOException {
//        checkFile(targetJarPath);
//        checkFile(targetAgentPath);
        List<String> commands = new ArrayList<>();
        commands.add("java");
        if (StringUtils.isNotEmpty(targetAgentPath)) {
            commands.add("-javaagent:" + targetAgentPath + (StringUtils.isEmpty(agentArg) ? "" : "=" + agentArg));
        }
        if (StringUtils.isNotEmpty(mainClass)) {
            commands.add("-Dloader.main=" + mainClass);
        }
        commands.add("-jar");
        commands.add(targetJarPath);
        ProcessBuilder processBuilder = new ProcessBuilder();
        process = processBuilder.command(commands).start();
//        process = Runtime.getRuntime().exec(commands.toArray(new String[0]));
        started = true;
        return process.getInputStream();
    }

    @Override
    public void close() throws Exception {
        if (started && process != null) {
            log.info("jar server {} will be shutdown", targetJarPath);
            process.destroy();
            started = false;
        }
    }

    private static void checkFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("file " + filePath + " is not exist");
        }
    }
}
