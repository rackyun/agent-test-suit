package com.keep.infra.agent.test.suit.keep.monitor.util;

import com.keep.infra.agent.test.suit.keep.monitor.client.entry.FalconMetricEntry;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yunhai.hu
 * at 2020/3/18
 */
public class FalconMetricUtil {

    public static boolean match(FalconMetricEntry[] metricEntries, String metricName, Map<String, String> tags) {
        if (metricEntries == null || metricEntries.length == 0) {
            return false;
        }
        return Arrays.stream(metricEntries).anyMatch(item -> {
            if (metricName.equals(item.getMetric())) {
                boolean hasTags = true;
                if (tags == null || tags.isEmpty()) {
                    return true;
                }
                for (Map.Entry<String, String> tagItem : tags.entrySet()) {
                    if (!item.getTags().contains(tagItem.getKey() + "=" + tagItem.getValue())) {
                        hasTags = false;
                    }
                }
                return hasTags;
            }
            return false;
        });

    }

    public static FalconMetricEntry[] findMetric(FalconMetricEntry[] metricEntries, String metricName) {
        if (StringUtils.isEmpty(metricName) || metricEntries == null || metricEntries.length == 0) {
            return null;
        }
        List<FalconMetricEntry> result = new ArrayList<>();
        for (FalconMetricEntry entry : metricEntries) {
            if (entry != null && metricName.equals(entry.getMetric())) {
                result.add(entry);
            }
        }

        if (result.isEmpty()) {
            return null;
        }
        return result.toArray(new FalconMetricEntry[0]);
    }
}
