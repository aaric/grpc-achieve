package com.incarcloud.grpc.runner;

import com.incarcloud.grpc.service.StreamService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * gRPC服务启动类
 *
 * @author Aaric, created on 2020-05-20T12:09.
 * @version 0.6.0-SNAPSHOT
 */
@Slf4j
@Order(1)
@Component
public class GRpcServerRunner implements CommandLineRunner {

    @Autowired
    private StreamService streamService;

    @Override
    public void run(String... args) throws Exception {
        // 启动服务
        log.info("starting...");
        Server server = ServerBuilder.forPort(12345)
                .addService(streamService)
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
}
