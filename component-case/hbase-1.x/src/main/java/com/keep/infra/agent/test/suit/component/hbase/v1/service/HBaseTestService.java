package com.keep.infra.agent.test.suit.component.hbase.v1.service;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.keep.commons.storage.hadoop.hbase.HBaseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * @author yunhai.hu
 * @date 2018/10/26
 */
@Slf4j
@Component
public class HBaseTestService {

    @Autowired
    private HBaseTemplate hBaseTemplate;

    private static final String USER_ID_COLUMN = "u";

    private static final String TRAINING_MODEL_COLUMN = "t";

    private static final String DEFAULT_COLUMN_FAMILY = "co";

    private static final String TABLE_NAME = "TrainingExitRecord";

    private static final String EXERCISE_RECORD_TABLE_NAME = "ExerciseRecord";

    private static final String TRAINING_RECORD_KEY_COLUMN = "tk";

    private static final String EXERCISE_ID_COLUMN = "eid";

    private static final String EXERCISE_COLUMN = "e";

    //@Test
    public void scan() throws Exception {
        String startRow = "54826e417fb786000069ada2";
        String stopRow = "54826e417fb786000069ada3";
        Set<String> qualifiers = Sets.newHashSet(USER_ID_COLUMN, TRAINING_MODEL_COLUMN);
        Map<String, Map<String, byte[]>> trainingExitRecord =
                hBaseTemplate.scan(TABLE_NAME, startRow.getBytes(), stopRow.getBytes(),
                        DEFAULT_COLUMN_FAMILY.getBytes(), qualifiers, 2);
        for (String key : trainingExitRecord.keySet()) {
            log.debug(key);
        }
        Map<String, Map<String, byte[]>> trainingExitRecord1 =
                hBaseTemplate.scan(TABLE_NAME, startRow.getBytes(), stopRow.getBytes(),
                        startRow.getBytes(),
                        DEFAULT_COLUMN_FAMILY.getBytes(), qualifiers, 2);
        for (String key : trainingExitRecord1.keySet()) {
            System.out.println(key);
        }

    }

    //@Test
    public void test() throws Exception {
        Set<String> qualifiers = Sets.newHashSet(USER_ID_COLUMN, TRAINING_MODEL_COLUMN);
        Map<String, byte[]> trainingExitRecord = hBaseTemplate.read(TABLE_NAME,
                "54826e417fb786000069ada2_9223370534136788807_tr".getBytes(),
                DEFAULT_COLUMN_FAMILY.getBytes(), qualifiers, false);
        System.out.println(MapUtils.isNotEmpty(trainingExitRecord));

        String rowkey = "xxxxx";

        Map<String, byte[]> map =
                ImmutableMap.of(USER_ID_COLUMN, "54826e417fb786000069ada2".getBytes());
        hBaseTemplate.write(TABLE_NAME, "xxxxx".getBytes(), DEFAULT_COLUMN_FAMILY.getBytes(),
                map);
        Map<String, byte[]> read = hBaseTemplate.read(TABLE_NAME, "xxxxx".getBytes(),
                DEFAULT_COLUMN_FAMILY.getBytes(), qualifiers, false);
        System.out.println(read.get(USER_ID_COLUMN));
        System.out.println(MapUtils.isNotEmpty(read) && ArrayUtils.isNotEmpty(read.get(USER_ID_COLUMN)));
        System.out.println(hBaseTemplate.delete(TABLE_NAME, rowkey.getBytes()));
        read = hBaseTemplate.read(TABLE_NAME, "xxxxx".getBytes(),
                DEFAULT_COLUMN_FAMILY.getBytes(), qualifiers, false);
        System.out.println(MapUtils.isNotEmpty(read) && ArrayUtils.isNotEmpty(read.get(USER_ID_COLUMN)));
    }

    @Autowired
    private Admin admin;

    public void prepare() {
        try {
            if (!admin.tableExists(TableName.valueOf(TABLE_NAME))) {
                HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(TABLE_NAME));
                tableDescriptor.addFamily(new HColumnDescriptor(DEFAULT_COLUMN_FAMILY));
                admin.createTable(tableDescriptor);
                log.info("create table {}", TABLE_NAME);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                admin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
