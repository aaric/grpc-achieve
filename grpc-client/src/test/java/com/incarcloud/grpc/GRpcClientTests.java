package com.incarcloud.grpc;

import com.incarcloud.grpc.proto.Position;
import com.incarcloud.grpc.proto.PositionServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

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
    public void testClient() {
        // 创建通道和存根
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 12345)
                .usePlaintext()
                .build();
        PositionServiceGrpc.PositionServiceBlockingStub stub = PositionServiceGrpc.newBlockingStub(channel);

        // 查询数据
        Position.PositionParam positionParam = Position.PositionParam.newBuilder()
                .setVin("LFV2A21J970002020")
                .build();
        Position.PositionDataList positionDataList = stub.queryList(positionParam);

        // 打印结果
        log.info(positionDataList.getPositionData(0).toString());
        Assertions.assertNotEquals(0, positionDataList.getPositionDataCount());
    }
}
