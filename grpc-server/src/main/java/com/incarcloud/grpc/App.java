package com.incarcloud.grpc;

import com.incarcloud.grpc.service.PositionService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.log4j.Log4j2;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@Log4j2
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
        runHBaseEnv();

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

    @Autowired
    private Connection hbaseConnection;

    @Value("${incarcloud.hbase.table.test}")
    private String hbaseTableTest;

    /**
     * 运行HBase环境
     */
    private void runHBaseEnv() {
        String rowKey = "key_" + Instant.now().toEpochMilli();
        try {
            Table table = hbaseConnection.getTable(TableName.valueOf(hbaseTableTest));
            Put put = new Put(rowKey.getBytes());
            put.addColumn(Bytes.toBytes("base"), Bytes.toBytes("data"), Bytes.toBytes("hello world"));

            table.put(put);
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("save rowKey: {}", rowKey);
    }
}
