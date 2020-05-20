package com.incarcloud.grpc.service;

import com.incarcloud.grpc.proto.Simple;
import com.incarcloud.grpc.proto.SimpleServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

/**
 * SimpleService
 *
 * @author Aaric, created on 2020-04-15T15:37.
 * @version 0.2.0-SNAPSHOT
 */
@Service
public class SimpleService extends SimpleServiceGrpc.SimpleServiceImplBase {

    /**
     * 实现queryList服务
     */
    @Override
    public void queryList(Simple.SimpleParam request, StreamObserver<Simple.SimpleDataList> responseObserver) {
        // 获取vin字符串
        String vin = request.getVin();
        System.out.println("vin: " + vin);

        // 根据vin查询位置数据
        Simple.SimpleData positionData = Simple.SimpleData.newBuilder()
                .setLongitude(113.906096)
                .setLatitude(22.583498)
                .build();
        Simple.SimpleDataList positionDataList = Simple.SimpleDataList.newBuilder()
                .addSimpleData(positionData)
                .build();

        // 向客户端返回结果，结束本次请求
        responseObserver.onNext(positionDataList);
        responseObserver.onCompleted();
    }
}
