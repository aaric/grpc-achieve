package com.incarcloud.grpc;

import com.incarcloud.grpc.service.PositionService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * grpc服务端启动类
 *
 * @author Aaric, created on 2020-04-15T15:17.
 * @version 0.2.0-SNAPSHOT
 */
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
        // 启动服务
        PositionService positionService = new PositionService();
        Server server = ServerBuilder.forPort(12345)
                .addService(positionService)
                .addService(positionService)
                .build()
                .start();

        // 关闭服务
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                server.awaitTermination();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
    }
}
