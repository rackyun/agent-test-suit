package com.keep.infra.agent.test.suit.component.hbase.v1.config;

import com.keep.commons.storage.hadoop.hbase.HBaseTemplate;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yunhai.hu
 * at 2018/12/14
 */
@Configuration
public class HBaseConfig {

    @Autowired
    private org.apache.hadoop.conf.Configuration hadoopConfig;

    @Bean
    public HBaseTemplate hBaseTemplate() {
        return new HBaseTemplate(hadoopConfig);
    }

    @Bean
    public Admin hBaseAdmin() {
        try {
            Connection connection = ConnectionFactory.createConnection(hadoopConfig);
            HBaseAdmin.checkHBaseAvailable(hadoopConfig);
            return connection.getAdmin();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
