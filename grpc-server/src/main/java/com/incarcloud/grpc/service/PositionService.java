package com.incarcloud.grpc.service;

import com.incarcloud.grpc.proto.Position;
import com.incarcloud.grpc.proto.PositionServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

/**
 * PositionService
 *
 * @author Aaric, created on 2020-04-15T15:37.
 * @version 0.2.0-SNAPSHOT
 */
@Slf4j
public class PositionService extends PositionServiceGrpc.PositionServiceImplBase {

    /**
     * 实现queryList服务
     */
    @Override
    public void queryList(Position.PositionParam request, StreamObserver<Position.PositionDataList> responseObserver) {
        // 获取vin字符串
        String vin = request.getVin();
        log.info("vin: {}", vin);

        // 根据vin查询位置数据
        Position.PositionData positionData = Position.PositionData.newBuilder()
                .setVin(vin)
                .setLatitude(113.906096)
                .setLatitude(22.583498)
                .build();
        Position.PositionDataList positionDataList = Position.PositionDataList.newBuilder()
                .addPositionData(positionData)
                .build();

        // 向客户端返回结果，结束本次请求
        responseObserver.onNext(positionDataList);
        responseObserver.onCompleted();
    }
}
