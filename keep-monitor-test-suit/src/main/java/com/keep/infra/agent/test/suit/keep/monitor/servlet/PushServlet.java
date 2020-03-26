package com.keep.infra.agent.test.suit.keep.monitor.servlet;

import com.keep.infra.agent.test.suit.keep.monitor.manager.FalconContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author yunhai.hu
 * at 2020/3/17
 */
public class PushServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(PushServlet.class);
    public static final String URL = "/v1/push";
    public static final String SERVLET_NAME = "pushServlet";

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        this.doGet(request, response);
        String host = request.getRemoteHost();
        if ("0:0:0:0:0:0:0:1".equals(host)) {
            host = "127.0.0.1";
        }
        String body = charReader(request);
//        logger.debug("received push data {}, from {}", body, host);
        if (StringUtils.isNotBlank(body)) {
            FalconContext.save(host, body);
        }
        response.setStatus(200);
        response.getWriter().print("OK");
        response.getWriter().close();
    }

    String charReader(HttpServletRequest request) {
        try {
            BufferedReader br = request.getReader();

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line = br.readLine()) != null){
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
