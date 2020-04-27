package com.incarcloud.grpc.runner;

import com.incarcloud.grpc.service.PositionService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * gRPC服务启动类
 *
 * @author Aaric, created on 2020-04-27T18:41.
 * @version 0.4.1-SNAPSHOT
 */
@Log4j2
@Order(2)
@Component
public class GRpcServerRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
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
}
