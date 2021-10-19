package org.rackyun.infra.agent.test.suit.component.feign.http.v9x.service;

import org.springframework.stereotype.Component;

/**
 * @author yunhai.hu
 * @date 2018/10/18
 */
@Component
public class TestWebServiceFallback implements TestWebService {


    @Override
    public String catNodes() {
        return null;
    }
}
