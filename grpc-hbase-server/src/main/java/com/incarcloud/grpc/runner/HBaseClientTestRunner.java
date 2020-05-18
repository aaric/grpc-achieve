package com.incarcloud.grpc.runner;

import lombok.extern.log4j.Log4j2;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * HBase客户端测试启动类
 *
 * @author Aaric, created on 2020-04-27T18:43.
 * @version 0.4.1-SNAPSHOT
 */
@Log4j2
@Order(1)
@Component
public class HBaseClientTestRunner implements CommandLineRunner {

    @Autowired
    private Connection hbaseConnection;

    @Value("${incarcloud.hbase.table.test}")
    private String hbaseTableTest;

    @Override
    public void run(String... args) throws Exception {
        String rowKey = "key_" + Instant.now().toEpochMilli();
        Table table = hbaseConnection.getTable(TableName.valueOf(hbaseTableTest));

        // 存储
        Put put = new Put(rowKey.getBytes());
        put.addColumn(Bytes.toBytes("base"), Bytes.toBytes("data"), Bytes.toBytes("hello world"));
        table.put(put);
        log.info("Put rowKey: {}", rowKey);

        // 读取
        Get get = new Get(Bytes.toBytes(rowKey));
        Result result = table.get(get);
        String content = Bytes.toString(result.getValue(Bytes.toBytes("base"), Bytes.toBytes("data")));
        log.info("Get content: {}", content);
    }
}
