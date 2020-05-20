package com.incarcloud.grpc;

import com.incarcloud.proto.pvo.Jt808DataServiceGrpc;
import com.incarcloud.proto.pvo.Pvo;
import com.incarcloud.proto.register.PvoServiceGrpc;
import com.incarcloud.proto.register.Register;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.Test;

/**
 * GRpcRegisterPvoTests
 *
 * @author Aaric, created on 2020-05-20T08:59.
 * @version 0.6.0-SNAPSHOT
 */
public class GRpcRegisterPvoTests {

    @Test
    public void testCallback() {
        // 注册登记查询vin所属pvo
        ManagedChannel channel = ManagedChannelBuilder.forAddress("116.63.79.61", 40000)
                .usePlaintext()
                .build();
        PvoServiceGrpc.PvoServiceBlockingStub stub = PvoServiceGrpc.newBlockingStub(channel);
        Register.QueryServerParam param = Register.QueryServerParam.newBuilder()
                .setVin("LFV2A21J970002030")
                .build();
        Register.QueryServerData data = stub.queryServer(param);
        System.out.println(data);

        // pvo查询vin位置信息
        if (null != data) {
            ManagedChannel channel2 = ManagedChannelBuilder.forAddress(data.getHostname(), data.getPort())
                    .usePlaintext()
                    .build();
            Jt808DataServiceGrpc.Jt808DataServiceBlockingStub stub2 = Jt808DataServiceGrpc.newBlockingStub(channel2);
            Pvo.PositionParam param2 = Pvo.PositionParam.newBuilder()
                    .setVin("LFV2A21J970002030")
                    .build();
            Pvo.PositionData data2 = stub2.queryPosition(param2);
            System.out.println(data2);
        }
    }
}
