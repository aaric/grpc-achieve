package com.incarcloud.grpc.service;

import com.incarcloud.grpc.proto.Stream;
import com.incarcloud.grpc.proto.StreamServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * StreamService
 *
 * @author Aaric, created on 2020-05-20T11:29.
 * @version 0.6.0-SNAPSHOT
 */
@Slf4j
@Service
public class StreamService extends StreamServiceGrpc.StreamServiceImplBase {

    private volatile int index = 0;

    private volatile double longitude = 0.0;
    private volatile double latitude = 0.0;

    @Override
    public void queryServerStream(Stream.StreamParam request, StreamObserver<Stream.StreamData> responseObserver) {
        // 获取请求参数
        log.debug("vin: {}", request.getVin());

        // 构建请求数据
        Stream.StreamData data = Stream.StreamData.newBuilder()
                .setLongitude(longitude + 1)
                .setLatitude(latitude + 1)
                .build();

        // 返回结果-第1次
        responseObserver.onNext(data);
        // 返回结果-第2次
        responseObserver.onNext(data);

        // 结束本次请求
        responseObserver.onCompleted();

    }

    @Override
    public StreamObserver<Stream.StreamParam> queryClientStream(StreamObserver<Stream.StreamData> responseObserver) {

        return new StreamObserver<Stream.StreamParam>() {

            @Override
            public void onNext(Stream.StreamParam value) {
                // 获取请求参数
                log.debug("vin: {}", value.getVin());

                // 记录请求次数
                index = index + 1;
            }

            @Override
            public void onError(Throwable t) {
                log.error("queryClientStream", t);
            }

            @Override
            public void onCompleted() {
                // 构建请求数据
                Stream.StreamData data = Stream.StreamData.newBuilder()
                        .setLongitude(longitude + index)
                        .setLatitude(latitude + index)
                        .build();

                // 返回结果（多次请求仅一次返回结果）
                responseObserver.onNext(data);

                // 结束本次请求
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<Stream.StreamParam> queryAllStream(StreamObserver<Stream.StreamData> responseObserver) {

        return new StreamObserver<Stream.StreamParam>() {

            @Override
            public void onNext(Stream.StreamParam value) {
                // 获取请求参数
                log.debug("vin: {}", value.getVin());

                // 记录请求次数
                index = index + 1;

                // 构建请求数据
                Stream.StreamData data = Stream.StreamData.newBuilder()
                        .setLongitude(longitude + index)
                        .setLatitude(latitude + index)
                        .build();

                // 返回结果
                responseObserver.onNext(data);
            }

            @Override
            public void onError(Throwable t) {
                log.error("queryAllStream", t);
            }

            @Override
            public void onCompleted() {
                // 结束本次请求
                responseObserver.onCompleted();
            }
        };
    }
}
