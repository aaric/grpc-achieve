package com.incarcloud.grpc.service;

import com.incarcloud.grpc.proto.Simple;
import com.incarcloud.grpc.proto.SimpleServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * SimpleService
 *
 * @author Aaric, created on 2020-04-15T15:37.
 * @version 0.2.0-SNAPSHOT
 */
@Log4j2
@Service
public class SimpleService extends SimpleServiceGrpc.SimpleServiceImplBase {

    /**
     * 实现queryList服务
     */
    @Override
    public void queryList(Simple.SimpleParam request, StreamObserver<Simple.SimpleDataList> responseObserver) {
        // 获取请求参数
        String vin = request.getVin();
        log.debug("vin: " + vin);

        // 构建请求数据
        Simple.SimpleDataList data = Simple.SimpleDataList.newBuilder()
                .addSimpleData(Simple.SimpleData.newBuilder()
                        .setLongitude(0.0)
                        .setLatitude(0.0)
                        .build())
                .build();

        // 返回结果
        responseObserver.onNext(data);

        // 结束本次请求
        responseObserver.onCompleted();
    }
}
