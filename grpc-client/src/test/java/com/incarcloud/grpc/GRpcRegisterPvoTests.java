package com.incarcloud.grpc;

import com.incarcloud.boar.datapack.DataParserIc;
import com.incarcloud.proto.gateway.CommandServiceGrpc;
import com.incarcloud.proto.gateway.Gateway;
import com.incarcloud.proto.pvo.IcDataServiceGrpc;
import com.incarcloud.proto.pvo.Jt808DataServiceGrpc;
import com.incarcloud.proto.pvo.Pvo;
import com.incarcloud.proto.register.GatewayServiceGrpc;
import com.incarcloud.proto.register.PvoServiceGrpc;
import com.incarcloud.proto.register.Register;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * GRpcRegisterPvoTests
 *
 * @author Aaric, created on 2020-05-20T08:59.
 * @version 0.6.0-SNAPSHOT
 */
@Slf4j
public class GRpcRegisterPvoTests {

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void testCallback() {
        // 注册登记查询vin所属pvo
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 40000) //116.63.79.61
                .usePlaintext()
                .build();
        PvoServiceGrpc.PvoServiceBlockingStub stub = PvoServiceGrpc.newBlockingStub(channel);
        Register.QueryServerParam param = Register.QueryServerParam.newBuilder()
                .setVin("LFV2A21J970002040")
                .build();
        Register.QueryServerData data = stub.queryServer(param);
        channel.shutdownNow();

        log.info("data: {}", data);

        // pvo查询vin位置信息
        if (null != data) {
            ManagedChannel channel2 = ManagedChannelBuilder.forAddress(data.getHostname(), data.getPort())
                    .usePlaintext()
                    .build();
            Jt808DataServiceGrpc.Jt808DataServiceBlockingStub stub2 = Jt808DataServiceGrpc.newBlockingStub(channel2);
            Pvo.PositionParam param2 = Pvo.PositionParam.newBuilder()
                    .setVin("LFV2A21J970002040")
                    .build();
            Pvo.PositionData data2 = stub2.queryPosition(param2);
            channel2.shutdownNow();

            log.info("data2: {}", data2);
        }
    }

    @Test
    public void testExecuteCommand() {
        // 注册登记查询deviceId所属gateway
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 40000) //116.63.79.61
                .usePlaintext()
                .build();
        GatewayServiceGrpc.GatewayServiceBlockingStub stub = GatewayServiceGrpc.newBlockingStub(channel);
        Register.QueryDeviceParam param = Register.QueryDeviceParam.newBuilder()
                .setDeviceId("CS20200124")
                .build();
        Register.QueryDeviceData data = stub.queryDevice(param);
        channel.shutdownNow();

        log.info("data: {}", data);

        // 发下指令
        if (StringUtils.isNotBlank(data.getHostname())) {
            ManagedChannel channel2 = ManagedChannelBuilder.forAddress(data.getHostname(), data.getPort())
                    .usePlaintext()
                    .build();
            CommandServiceGrpc.CommandServiceBlockingStub stub2 = CommandServiceGrpc.newBlockingStub(channel2);
            Gateway.CommandParam param2 = Gateway.CommandParam.newBuilder()
                    .setMsgId(0x80)
                    .setMsgSn(Instant.now().toEpochMilli())
                    .setDeviceId("CS20200124")
                    .setVin("LFV2A21J970002020")
                    .setProtocolName(DataParserIc.PROTOCOL_NAME)
                    .setCommandString(Base64.getEncoder().encodeToString("hello world".getBytes()))
                    .build();

            Gateway.CommandData data2 = stub2.execute(param2);
            channel2.shutdownNow();

            log.info("data2: {}", data2);
        }
    }

    @Test
    public void testQueryPosition() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 40010)
                .usePlaintext()
                .build();
        Jt808DataServiceGrpc.Jt808DataServiceBlockingStub stub = Jt808DataServiceGrpc.newBlockingStub(channel);
        Pvo.PositionParam param = Pvo.PositionParam.newBuilder()
                .setVin("LFV2A21J970002010")
                .build();
        Pvo.PositionData data = stub.queryPosition(param);
        channel.shutdownNow();

        log.info("data: {}", data);
    }

    @Test
    public void testQueryPositionList() throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 40010)
                .usePlaintext()
                .build();
        Jt808DataServiceGrpc.Jt808DataServiceBlockingStub stub = Jt808DataServiceGrpc.newBlockingStub(channel);

        Pvo.PositionListParam param = Pvo.PositionListParam.newBuilder()
                .setVin("LFV2A21J970002030")
                .setBeginTime(dateFormat.parse("2020-05-12 00:00:00").getTime())
//                .setEndTime(dateFormat.parse("2020-05-12 23:59:59").getTime())
//                .setPageSize(5)
//                .setDesc(true)
                .setStartRowKey("")
                .build();

        Pvo.PositionDataList list = stub.queryPositionList(param);
        channel.shutdownNow();

        log.info("data: {}", list);
    }

    @Test
    public void testQueryPositionStream() throws InterruptedException {
        // 倒计时
        final CountDownLatch latch = new CountDownLatch(1);

        // 创建通道和存根
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 40010) //116.63.79.61
                .usePlaintext()
                .build();
        Jt808DataServiceGrpc.Jt808DataServiceStub stub = Jt808DataServiceGrpc.newStub(channel);

        // 双流模式
        StreamObserver<Pvo.PositionDataStreamParam> observer = stub.queryPositionStream(new StreamObserver<Pvo.PositionData>() {

            @Override
            public void onNext(Pvo.PositionData value) {
                Date detectionTime = Date.from(Instant.ofEpochMilli(value.getDetectionTime()));
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                log.info("longitude: {}, latitude: {}, detectionTime: {}", value.getLongitude(), value.getLatitude(), dateFormat.format(detectionTime));
            }

            @Override
            public void onError(Throwable t) {
                // 打印异常日志
                log.error("testQueryPositionStream", t);
            }

            @Override
            public void onCompleted() {
                log.info("completed");

                // 结束倒计时
                latch.countDown();
            }
        });

        // 构建请求参数
        String testVin = "LFV2A21J970002010"; //LFV2A21J970002040
        Pvo.PositionDataStreamParam request = Pvo.PositionDataStreamParam.newBuilder()
                .setVin(testVin)
                .setType(1)
                .build();

        // 发起请求
        for (int i = 0; i < 5; i++) {
            observer.onNext(request);

            TimeUnit.SECONDS.sleep(10);
        }

        // 关闭监听
        request = Pvo.PositionDataStreamParam.newBuilder()
                .setVin(testVin)
                .setType(2)
                .build();
        observer.onNext(request);

        // 结束本次请求
        observer.onCompleted();

        // 断开客户端连接
        channel.shutdownNow();

        // waiting for result
        if (!latch.await(5, TimeUnit.SECONDS)) {
            log.info("end");
        }
    }

    @Test
    public void testQueryVehicleStatus() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 40010)
                .usePlaintext()
                .build();
        IcDataServiceGrpc.IcDataServiceBlockingStub stub = IcDataServiceGrpc.newBlockingStub(channel);
        Pvo.VehicleStatusParam param = Pvo.VehicleStatusParam.newBuilder()
                .setVin("LFV2A21J970002020")
                .build();
        Pvo.VehicleStatusData data = stub.queryVehicleStatus(param);
        channel.shutdownNow();

        log.info("data: {}", data);
    }

    @Test
    public void testQueryVehicleStatusStream() throws InterruptedException {
        // 倒计时
        final CountDownLatch latch = new CountDownLatch(1);

        // 创建通道和存根
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 40010)
                .usePlaintext()
                .build();
        IcDataServiceGrpc.IcDataServiceStub stub = IcDataServiceGrpc.newStub(channel);

        // 双流模式
        StreamObserver<Pvo.VehicleStatusDataStreamParam> observer = stub.queryVehicleStatusStream(new StreamObserver<Pvo.VehicleStatusData>() {

            @Override
            public void onNext(Pvo.VehicleStatusData value) {
                Date detectionTime = Date.from(Instant.ofEpochMilli(value.getDetectionTime()));
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                log.info("longitude: {}, latitude: {}, gpsSpeed: {}, detectionTime: {}", value.getLongitude(), value.getLatitude(), value.getGpsSpeed(), dateFormat.format(detectionTime));
            }

            @Override
            public void onError(Throwable t) {
                // 打印异常日志
                log.error("testQueryVehicleStatusStream", t);
            }

            @Override
            public void onCompleted() {
                log.info("completed");

                // 结束倒计时
                latch.countDown();
            }
        });

        // 构建请求参数
        String testVin = "LFV2A21J970002020";
        Pvo.VehicleStatusDataStreamParam request = Pvo.VehicleStatusDataStreamParam.newBuilder()
                .setVin(testVin)
                .setType(1)
                .build();

        // 发起请求
        observer.onNext(request);
        for (int i = 0; i < 5; i++) {

            TimeUnit.SECONDS.sleep(10);
        }

        // 关闭监听
        request = Pvo.VehicleStatusDataStreamParam.newBuilder()
                .setVin(testVin)
                .setType(2)
                .build();
        observer.onNext(request);

        // 结束本次请求
        observer.onCompleted();

        // 断开客户端连接
        channel.shutdownNow();

        // waiting for result
        if (!latch.await(5, TimeUnit.SECONDS)) {
            log.info("end");
        }
    }
}
