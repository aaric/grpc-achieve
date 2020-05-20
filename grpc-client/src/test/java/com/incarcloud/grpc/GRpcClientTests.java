package com.incarcloud.grpc;

import com.incarcloud.grpc.proto.Simple;
import com.incarcloud.grpc.proto.SimpleServiceGrpc;
import com.incarcloud.grpc.proto.Stream;
import com.incarcloud.grpc.proto.StreamServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

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
    public void testSimple() {
        // 创建通道和存根
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 12345)
                .usePlaintext()
                .build();
        SimpleServiceGrpc.SimpleServiceBlockingStub stub = SimpleServiceGrpc.newBlockingStub(channel);

        // 查询数据
        Simple.SimpleParam param = Simple.SimpleParam.newBuilder()
                .setVin("LFV2A21J880002020")
                .build();
        Simple.SimpleDataList data = stub.queryList(param);

        // 打印结果
        log.info(data.getSimpleData(0).toString());
        Assertions.assertNotEquals(0, data.getSimpleDataCount());
    }

    @Test
    public void testQueryServerStream() throws InterruptedException {
        // 创建通道和存根
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 12345)
                .usePlaintext()
                .build();
        StreamServiceGrpc.StreamServiceStub stub = StreamServiceGrpc.newStub(channel);

        // 服务端流模式
        Stream.StreamParam param = Stream.StreamParam.newBuilder()
                .setVin("LFV2A21J880002020")
                .build();
        stub.queryServerStream(param, new StreamObserver<Stream.StreamData>() {

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
    public void testQueryClientStream() throws InterruptedException {
        // 创建通道和存根
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 12345)
                .usePlaintext()
                .build();
        StreamServiceGrpc.StreamServiceStub stub = StreamServiceGrpc.newStub(channel);

        // 客户端流模式
        StreamObserver<Stream.StreamParam> observer = stub.queryClientStream(new StreamObserver<Stream.StreamData>() {

            @Override
            public void onNext(Stream.StreamData value) {
                log.info("longitude: {}, latitude: {}", value.getLongitude(), value.getLatitude());
            }

            @Override
            public void onError(Throwable t) {
                log.error("testQueryClientStream", t);
            }

            @Override
            public void onCompleted() {
                log.info("completed");
            }
        });

        // 构建请求参数
        Stream.StreamParam param = Stream.StreamParam.newBuilder()
                .setVin("LFV2A21J880002020")
                .build();

        // 发起请求-第1次
        observer.onNext(param);
        // 发起请求-第2次
        observer.onNext(param);

        // 结束本次请求
        observer.onCompleted();

        // waiting 10s for result
        TimeUnit.SECONDS.sleep(10L);
    }

    @Test
    public void testQueryAllStream() throws InterruptedException {
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
            }
        });

        // 构建请求参数
        Stream.StreamParam param = Stream.StreamParam.newBuilder()
                .setVin("LFV2A21J880002020")
                .build();

        // 发起请求-第1次
        observer.onNext(param);
        // 发起请求-第2次
        observer.onNext(param);

        // 结束本次请求
        observer.onCompleted();

        // waiting 10s for result
        TimeUnit.SECONDS.sleep(10L);
    }
}
