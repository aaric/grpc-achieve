package com.incarcloud.grpc.runner;

import com.incarcloud.proto.pvo.Jt808DataServiceGrpc;
import com.incarcloud.proto.pvo.Pvo;
import com.incarcloud.proto.register.PvoServiceGrpc;
import com.incarcloud.proto.register.Register;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * gRPC初始化
 *
 * @author Aaric, created on 2020-05-21T13:37.
 * @version 0.6.1-SNAPSHOT
 */
@Slf4j
@Order(1)
@Component
public class GRpcClientRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        // 注册登记查询vin所属pvo
        ManagedChannel channel = ManagedChannelBuilder.forAddress("116.63.79.61", 40000)
                .usePlaintext()
                .build();
        PvoServiceGrpc.PvoServiceBlockingStub stub = PvoServiceGrpc.newBlockingStub(channel);
        Register.QueryServerParam param = Register.QueryServerParam.newBuilder()
                .setVin("LFV2A21J970002040")
                .build();
        Register.QueryServerData data = stub.queryServer(param);
        log.info("data: {}", data);

        // pvo查询vin位置信息
        if (null != data) {
            ManagedChannel channel2 = ManagedChannelBuilder.forAddress(data.getHostname(), data.getPort())
                    .usePlaintext()
                    .build();
            Jt808DataServiceGrpc.Jt808DataServiceBlockingStub stub2 = Jt808DataServiceGrpc.newBlockingStub(channel2);
            Pvo.PositionParam param2 = Pvo.PositionParam.newBuilder()
                    .setVin("LFV2A21J970002030")
                    .build();
            Pvo.PositionData data2 = stub2.queryPosition(param2);
            log.info("data2: {}", data2);
        }
    }
}
