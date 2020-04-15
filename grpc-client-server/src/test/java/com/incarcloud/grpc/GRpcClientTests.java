package com.incarcloud.grpc;

import com.incarcloud.grpc.proto.Position;
import com.incarcloud.grpc.proto.PositionServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;

/**
 * GRpcClientTests
 *
 * @author Aaric, created on 2020-04-15T16:37.
 * @version 0.2.0-SNAPSHOT
 */
public class GRpcClientTests {

    @Test
    public void testClient() {
        // 创建通道和存根
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 12345)
                .build();
        PositionServiceGrpc.PositionServiceStub stub = PositionServiceGrpc.newStub(channel);

        // 查询数据
        Position.PositionParam positionParam = Position.PositionParam.newBuilder()
                .setVin("LFV2A21J970002020")
                .build();
        stub.queryList(positionParam, new StreamObserver<Position.PositionDataList>() {

            @Override
            public void onNext(Position.PositionDataList value) {
                System.out.println("-----------------------------");
                System.out.println("size: " + value.getPositionDataCount());
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {

            }
        });
    }
}
