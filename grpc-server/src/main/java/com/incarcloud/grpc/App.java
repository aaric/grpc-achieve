package com.incarcloud.grpc;

import com.incarcloud.grpc.service.PositionService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.time.Instant;

/**
 * grpc服务端启动类
 *
 * @author Aaric, created on 2020-04-15T15:17.
 * @version 0.2.0-SNAPSHOT
 */
@Slf4j
@SpringBootApplication
public class App implements CommandLineRunner {

    /**
     * Main
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Test
        log.info("hbase init...");
        //runHBaseEnv();

        // 启动服务
        log.info("starting...");
        Server server = ServerBuilder.forPort(12345)
                .addService(new PositionService())
                .build()
                .start();

        // 关闭服务
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("stopping...");
            if (null != server) {
                server.shutdown();
            }
            log.info("stopped.");
        }));

        // 等待终止...
        log.info("started.");
        server.awaitTermination();
    }

    /**
     * 运行HBase环境
     */
    private void runHBaseEnv() {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "10.0.11.34,10.0.11.35,10.0.11.39");
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        configuration.set("hbase.master", "10.0.11.35:60000");

        String rowKey = "key_" + Instant.now().toEpochMilli();
        try {
            Connection connection = ConnectionFactory.createConnection(configuration);
            Table table = connection.getTable(TableName.valueOf("zsgroup:telemetry"));
            Put put = new Put(rowKey.getBytes());
            put.addColumn(Bytes.toBytes("base"), Bytes.toBytes("data"), Bytes.toBytes("hello world"));

            table.put(put);
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("save rowKey: {}", rowKey);
    }
}
