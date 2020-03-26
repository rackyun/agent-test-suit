package com.keep.infra.agent.test.suit.mock.server;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.util.ServerInfo;
import org.apache.tomcat.util.res.StringManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * @author yunhai.hu
 * at 2020/3/17
 */
public class MockServer {

    private static final Logger logger = LoggerFactory.getLogger(MockServer.class);
    protected static final StringManager sm = StringManager.getManager("org.apache.catalina.startup");

    private Tomcat tomcat;

    public void start() {
        try {
            tomcat = new Tomcat();
            tomcat.setPort(2015);
//            tomcat.getHost().setAutoDeploy(false);
            tomcat.getConnector();
            StandardContext ctx = new StandardContext();
            String contextPath = "";
            ctx.setPath(contextPath);
            ctx.addLifecycleListener(new Tomcat.FixContextListener());
            logger.info(getBaseDir());

            tomcat.getHost().addChild(ctx);
            tomcat.addServlet(contextPath, "defaultServlet", new DefaultServlet());
            ctx.addServletMappingDecoded("/*", "defaultServlet");
//            log();
            logger.info("Server配置加载完成，正在启动中...");
            tomcat.start();

        } catch (Exception e) {
            logger.error("Server启动失败...", e);
        }
    }

    public void await() {
        tomcat.getServer().await();
    }

    private static String getBaseDir() {
        final String path = MockServer.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        System.out.println(path);
        return "";
    }

    private static String getWebappDirLocation() {
        final String path = MockServer.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File file = new File(path);
        String libPath = file.getParentFile().getAbsolutePath();
        //如果是jar包启动，则file是jar包文件，如果是本地main方法启动，file是target目录下的classes目录
        return libPath;
    }

    static class DefaultServlet extends HttpServlet {

        public void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException
        {
            resp.setStatus(200);
            resp.getWriter().println("OK");
            resp.getWriter().close();
        }
    }

    public static void log() {
        logger.info("Server project dir:    {}", new File("").getAbsolutePath());
        logger.info(sm.getString("versionLoggerListener.serverInfo.server.version",
                ServerInfo.getServerInfo()));
        logger.info(sm.getString("versionLoggerListener.serverInfo.server.built",
                ServerInfo.getServerBuilt()));
        logger.info(sm.getString("versionLoggerListener.serverInfo.server.number",
                ServerInfo.getServerNumber()));
        logger.info(sm.getString("versionLoggerListener.os.name",
                System.getProperty("os.name")));
        logger.info(sm.getString("versionLoggerListener.os.version",
                System.getProperty("os.version")));
        logger.info(sm.getString("versionLoggerListener.os.arch",
                System.getProperty("os.arch")));
        logger.info(sm.getString("versionLoggerListener.java.home",
                System.getProperty("java.home")));
        logger.info(sm.getString("versionLoggerListener.vm.version",
                System.getProperty("java.runtime.version")));
        logger.info(sm.getString("versionLoggerListener.vm.vendor",
                System.getProperty("java.vm.vendor")));
        logger.info(sm.getString("versionLoggerListener.catalina.base",
                System.getProperty("catalina.base")));
        logger.info(sm.getString("versionLoggerListener.catalina.home",
                System.getProperty("catalina.home")));
        //argument
        List<String> args = ManagementFactory.getRuntimeMXBean().getInputArguments();
        for (String arg : args) {
            logger.info(sm.getString("versionLoggerListener.arg", arg));
        }

    }

    public void close() throws LifecycleException {
        tomcat.getServer().stop();
    }

    public static void main(String[] args) {
        MockServer server = new MockServer();
        try {
            server.start();
            server.await();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (LifecycleException e) {
                e.printStackTrace();
            }
        }
    }
}
