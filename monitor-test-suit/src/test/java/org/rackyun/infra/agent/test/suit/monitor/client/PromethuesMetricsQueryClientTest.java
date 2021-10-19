package org.rackyun.infra.agent.test.suit.monitor.client;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author yunhai.hu
 * at 2021/10/18
 */
public class PromethuesMetricsQueryClientTest {

    @Test
    public void testValidate() throws Exception {
        String[] testCases = new String[]{
                "http_client_requests_seconds_sum{exception=\"None\",host=\"xmen.intra.ke.com\",method=\"GET\"," +
                        "module=\"cloud-monitor-proxy\",remote=\"xmen.intra.ke.com\",status=\"200\"," +
                        "uri=\"/common/api/v1/cache\",} 0.122064489",
                "# HELP http_client_requests_seconds_max Timer of Feign client",
                "# TYPE http_client_requests_seconds_max gauge",
                "http_client_requests_seconds_max{exception=\"None\",host=\"xmen.intra.ke.com\",method=\"GET\"," +
                        "module=\"cloud-monitor-proxy\",remote=\"xmen.intra.ke.com\",status=\"200\"," +
                        "uri=\"/common/api/v1/cache\",} 0.07722853",
                "# HELP jvm_memory_used_bytes The amount of used memory",
                "# TYPE jvm_memory_used_bytes gauge",
                "jvm_memory_used_bytes{area=\"nonheap\",id=\"Metaspace\",module=\"cloud-monitor-proxy\",} 7.4067496E7",
                "jvm_memory_used_bytes{area=\"heap\",id=\"PS Eden Space\",module=\"cloud-monitor-proxy\",} 3" +
                        ".15452528E8",
                "jvm_memory_used_bytes{area=\"nonheap\",id=\"Compressed Class Space\",module=\"cloud-monitor-proxy\"," +
                        "} 9836504.0",
                "jvm_memory_used_bytes{area=\"heap\",id=\"PS Old Gen\",module=\"cloud-monitor-proxy\",} 4.6210256E7",
                "jvm_memory_used_bytes{area=\"heap\",id=\"PS Survivor Space\",module=\"cloud-monitor-proxy\",} 0.0",
                "jvm_memory_used_bytes{area=\"nonheap\",id=\"Code Cache\",module=\"cloud-monitor-proxy\",} 1.4211648E7"
        };
        Pattern pattern = Pattern.compile("jvm_memory_used_bytes\\{(([\\w-]+=\"([\\w- ])+\",)+)\\}\\s([0-9E\\.]+)");
        for (String txt : testCases) {
            Matcher matcher = pattern.matcher(txt);
            if (matcher.find()) {
                System.out.printf("%s, %s, %s \n", matcher.group(0), matcher.group(1), matcher.group(4));
            }
        }
    }

    @Test
    public void testValidateMetrics() throws Exception {
        PromethuesMetricsQueryClient client = new PromethuesMetricsQueryClient("http://localhost:8080");
        Map<String, String> tags = new HashMap<>();
        tags.put("area", "heap");
        assertTrue(client.validateMetricsWithOutValue("jvm_memory_used_bytes", tags));
        assertFalse(client.validateMetricsWithOutValue("http_client_requests_seconds_sum", tags));
        //process_files_max_files{module="cloud-monitor-proxy",} 10240.0
        Map<String, String> tags2 = new HashMap<>();
        tags2.put("module", "cloud-monitor-proxy");
        assertTrue(client.validateMetrics("process_files_max_files", 10240.0F, tags2));
    }
}
