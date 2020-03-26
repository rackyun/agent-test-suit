package com.keep.infra.agent.test.suit.keep.monitor.servlet;

import com.keep.infra.agent.test.suit.keep.monitor.manager.FalconContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yunhai.hu
 * at 2020/3/17
 */
public class QueryServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(PushServlet.class);
    public static final String URL = "/v1/get";
    public static final String SERVLET_NAME = "queryServlet";

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String host = req.getRemoteHost();
        if ("0:0:0:0:0:0:0:1".equals(host)) {
            host = "127.0.0.1";
        }
//        logger.debug("{} query data", host);
        String respBody = FalconContext.get(host);
        resp.setStatus(200);
        resp.getWriter().print(respBody);
        resp.getWriter().close();
    }
}
