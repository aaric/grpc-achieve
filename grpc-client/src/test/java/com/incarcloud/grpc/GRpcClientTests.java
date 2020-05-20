package com.incarcloud.grpc;

import com.incarcloud.grpc.proto.Simple;
import com.incarcloud.grpc.proto.SimpleServiceGrpc;
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
    public void testSimple() {
        // 创建通道和存根
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 12345)
                .usePlaintext()
                .build();
        SimpleServiceGrpc.SimpleServiceBlockingStub stub = SimpleServiceGrpc.newBlockingStub(channel);

        // 查询数据
        Simple.SimpleParam positionParam = Simple.SimpleParam.newBuilder()
                .setVin("LFV2A21J880002020")
                .build();
        Simple.SimpleDataList positionDataList = stub.queryList(positionParam);

        // 打印结果
        log.info(positionDataList.getSimpleData(0).toString());
        Assertions.assertNotEquals(0, positionDataList.getSimpleDataCount());
    }
}
