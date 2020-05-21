package com.incarcloud.grpc;

import com.incarcloud.proto.pvo.Jt808DataServiceGrpc;
import com.incarcloud.proto.pvo.Pvo;
import com.incarcloud.proto.register.PvoServiceGrpc;
import com.incarcloud.proto.register.Register;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * GRpcRegisterPvoTests
 *
 * @author Aaric, created on 2020-05-20T08:59.
 * @version 0.6.0-SNAPSHOT
 */
@Slf4j
public class GRpcRegisterPvoTests {

    @Test
    public void testCallback() {
        // 注册登记查询vin所属pvo
        ManagedChannel channel = ManagedChannelBuilder.forAddress("116.63.79.61", 40000)
                .usePlaintext()
                .build();
        PvoServiceGrpc.PvoServiceBlockingStub stub = PvoServiceGrpc.newBlockingStub(channel);
        Register.QueryServerParam param = Register.QueryServerParam.newBuilder()
                .setVin("LFV2A21J970002030")
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

    @Test
    public void testQueryPositionStream() throws InterruptedException {
        // 倒计时
        final CountDownLatch latch = new CountDownLatch(1);

        // 创建通道和存根
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 40010)
                .usePlaintext()
                .build();
        Jt808DataServiceGrpc.Jt808DataServiceStub stub = Jt808DataServiceGrpc.newStub(channel);

        // 双流模式
        StreamObserver<Pvo.PositionDataStreamParam> observer = stub.queryPositionStream(new StreamObserver<Pvo.PositionData>() {

            @Override
            public void onNext(Pvo.PositionData value) {
                log.info("longitude: {}, latitude: {}", value.getLongitude(), value.getLatitude());
            }

            @Override
            public void onError(Throwable t) {
                log.error("testQueryAllStream", t);
            }

            @Override
            public void onCompleted() {
                log.info("completed");

                // 结束倒计时
                latch.countDown();
            }
        });

        // 构建请求参数
        Pvo.PositionDataStreamParam request = Pvo.PositionDataStreamParam.newBuilder()
                .setVin("LFV2A21J880002020")
                .setType(1)
                .build();

        // 发起请求
        for (int i = 0; i < 5; i++) {
            observer.onNext(request);
        }

        // 结束本次请求
        observer.onCompleted();

        // waiting for result
        if (!latch.await(5, TimeUnit.SECONDS)) {
            log.info("end");
        }
    }
}
