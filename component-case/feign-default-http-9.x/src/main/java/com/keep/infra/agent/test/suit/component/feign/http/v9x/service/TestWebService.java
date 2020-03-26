package com.keep.infra.agent.test.suit.component.feign.http.v9x.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author yunhai.hu
 * @date 2018/10/17
 */
@FeignClient(name = "test-elastic-service", url = "http://localhost:2015", fallback = TestWebServiceFallback.class)
public interface TestWebService {

    @RequestMapping(method = RequestMethod.GET, value = "/feign/v9")
    String catNodes();
}
