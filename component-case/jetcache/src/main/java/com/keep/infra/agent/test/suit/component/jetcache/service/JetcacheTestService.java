package com.keep.infra.agent.test.suit.component.jetcache.service;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author yunhai.hu
 * at 2019/4/18
 */
@Service
public class JetcacheTestService {

    private static final Logger logger = LoggerFactory.getLogger(JetcacheTestService.class);

    @Cached(name="userCache.", expire = 3600, cacheType = CacheType.BOTH)
    public String getTime(String name) {
        logger.info("I'm real method invoke.");
        return DateFormatUtils.ISO_8601_EXTENDED_TIME_FORMAT.format(new Date());
    }
}
