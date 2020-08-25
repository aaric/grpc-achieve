package com.incarcloud.grpc;

import com.incarcloud.proto.monitor.MonitorApi;
import com.incarcloud.proto.monitor.MonitorHomeApiServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * GRpcMonitorTests
 *
 * @author Aaric, created on 2020-08-25T11:08.
 * @version 0.6.5-SNAPSHOT
 */
@Slf4j
public class GRpcMonitorTests {

    private ManagedChannel channel;

    @BeforeEach
    public void setup() {
        channel = ManagedChannelBuilder.forAddress("127.0.0.1", 6117) //116.63.79.61
                .usePlaintext()
                .build();
    }

    @AfterEach
    public void tearDown() {
        if (channel.isTerminated()) {
            channel.shutdownNow();
        }
    }

    @Test
    public void testQueryOverview() {
        MonitorHomeApiServiceGrpc.MonitorHomeApiServiceBlockingStub stub = MonitorHomeApiServiceGrpc.newBlockingStub(channel);
        MonitorApi.BlankParam param = MonitorApi.BlankParam.newBuilder().build();
        MonitorApi.OverviewData data = stub.queryOverview(param);

        log.info("data: {}", data);
        Assertions.assertNotNull(data);
    }
}
