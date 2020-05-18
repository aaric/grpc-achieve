package com.incarcloud.grpc.config;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * HBase配置
 *
 * @author Aaric, created on 2020-04-27T10:34.
 * @version 0.4.0-SNAPSHOT
 */
@Configuration
public class HBaseConfig {

    @Value("${incarcloud.hbase.zookeeper.quorum}")
    private String zookeeperQuorum;

    @Value("${incarcloud.hbase.zookeeper.property.clientPort}")
    private String zookeeperClientPort;

    @Bean
    public Connection hbaseConnection() throws IOException {
        org.apache.hadoop.conf.Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", zookeeperQuorum);
        configuration.set("hbase.zookeeper.property.clientPort", zookeeperClientPort);

        return ConnectionFactory.createConnection(configuration);
    }
}
