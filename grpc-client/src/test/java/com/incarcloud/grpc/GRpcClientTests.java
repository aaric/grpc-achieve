package com.incarcloud.grpc;

import com.incarcloud.grpc.proto.Simple;
import com.incarcloud.grpc.proto.SimpleServiceGrpc;
import com.incarcloud.grpc.proto.Stream;
import com.incarcloud.grpc.proto.StreamServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * GRpcClientTests
 *
 * @author Aaric, created on 2020-04-16T16:37.
 * @version 0.3.0-SNAPSHOT
 */
@Slf4j
public class GRpcClientTests {

    @Test
    @Disabled
    public void testSimple() throws InterruptedException {
        // 创建通道和存根
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 12345)
                .usePlaintext()
                .build();
        SimpleServiceGrpc.SimpleServiceBlockingStub stub = SimpleServiceGrpc.newBlockingStub(channel);

        // 查询数据
        Simple.SimpleParam request = Simple.SimpleParam.newBuilder()
                .setVin("LFV2A21J880002020")
                .build();
        Simple.SimpleDataList response = stub.queryList(request);

        // 打印结果
        log.info(response.getSimpleData(0).toString());
    }

    @Test
    public void testQueryServerStream() throws InterruptedException {
        // 创建通道和存根
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 12345)
                .usePlaintext()
                .build();
        StreamServiceGrpc.StreamServiceStub stub = StreamServiceGrpc.newStub(channel);

        // 服务端流模式
        Stream.StreamParam request = Stream.StreamParam.newBuilder()
                .setVin("LFV2A21J880002020")
                .build();
        stub.queryServerStream(request, new StreamObserver<Stream.StreamData>() {

            @Override
            public void onNext(Stream.StreamData value) {
                log.info("longitude: {}, latitude: {}", value.getLongitude(), value.getLatitude());
            }

            @Override
            public void onError(Throwable t) {
                log.error("queryServerStream", t);
            }

            @Override
            public void onCompleted() {
                log.info("completed");
            }
        });

        // waiting 10s for result
        TimeUnit.SECONDS.sleep(10L);
    }

    @Test
    public void testQueryServerStreamWithBlockingStub() throws InterruptedException {
        // 创建通道和存根
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 12345)
                .usePlaintext()
                .build();
        StreamServiceGrpc.StreamServiceBlockingStub stub = StreamServiceGrpc.newBlockingStub(channel);

        // 服务端流模式
        Stream.StreamParam request = Stream.StreamParam.newBuilder()
                .setVin("LFV2A21J880002020")
                .build();
        Iterator<Stream.StreamData> response = stub.queryServerStream(request);
        for (int i = 1; response.hasNext(); i++) {
            Stream.StreamData value = response.next();
            log.info("longitude: {}, latitude: {}", value.getLongitude(), value.getLatitude());
        }
    }

    @Test
    public void testQueryClientStream() throws InterruptedException {
        // 倒计时
        final CountDownLatch latch = new CountDownLatch(1);

        // 创建通道和存根
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 12345)
                .usePlaintext()
                .build();
        StreamServiceGrpc.StreamServiceStub stub = StreamServiceGrpc.newStub(channel);

        // 客户端流模式
        StreamObserver<Stream.StreamParam> observer = stub.queryClientStream(new StreamObserver<Stream.StreamData>() {

            @Override
            public void onNext(Stream.StreamData value) {
                // 打印结果
                log.info("longitude: {}, latitude: {}", value.getLongitude(), value.getLatitude());
            }

            @Override
            public void onError(Throwable t) {
                log.error("testQueryClientStream", t);
            }

            @Override
            public void onCompleted() {
                log.info("completed");

                // 结束倒计时
                latch.countDown();
            }
        });

        // 构建请求参数
        Stream.StreamParam request = Stream.StreamParam.newBuilder()
                .setVin("LFV2A21J880002020")
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

    @Test
    public void testQueryAllStream() throws InterruptedException {
        // 倒计时
        final CountDownLatch latch = new CountDownLatch(1);

        // 创建通道和存根
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 12345)
                .usePlaintext()
                .build();
        StreamServiceGrpc.StreamServiceStub stub = StreamServiceGrpc.newStub(channel);

        // 双流模式
        StreamObserver<Stream.StreamParam> observer = stub.queryClientStream(new StreamObserver<Stream.StreamData>() {

            @Override
            public void onNext(Stream.StreamData value) {
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
        Stream.StreamParam request = Stream.StreamParam.newBuilder()
                .setVin("LFV2A21J880002020")
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
