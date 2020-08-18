package com.incarcloud.grpc;

import com.incarcloud.boar.cmd.CommandFactory;
import com.incarcloud.boar.cmd.CommandType;
import com.incarcloud.boar.datapack.IcCommandFactory;
import com.incarcloud.boar.datapack.ic.model.control.BaseControlData;
import com.incarcloud.boar.datapack.ic.model.control.BluetoothControlData;
import com.incarcloud.boar.datapack.ic.model.control.DoorControlData;
import com.incarcloud.boar.datapack.ic.model.control.RequstVehicleInfoControlData;
import com.incarcloud.boar.datapack.ic.utils.IcDataPackUtils;
import com.incarcloud.proto.gateway.CommandServiceGrpc;
import com.incarcloud.proto.gateway.Gateway;
import com.incarcloud.proto.pvo.ControlServiceGrpc;
import com.incarcloud.proto.pvo.IcDataServiceGrpc;
import com.incarcloud.proto.pvo.Jt808DataServiceGrpc;
import com.incarcloud.proto.pvo.Pvo;
import com.incarcloud.proto.register.PvoServiceGrpc;
import com.incarcloud.proto.register.Register;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
public class GRpcPvoTests {

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
    public void testHelloBytes() {
        byte[] helloBytes = Base64.getDecoder().decode("KSkHAB4KQ1MyMDIwMDEyNIIpxrgBAAAAAAAAAAJQpWaB0g0=");
        log.info(ByteBufUtil.hexDump(helloBytes));
        log.info("epoch milli: {}", Instant.now().toEpochMilli());
        log.info("epoch second: {}", Instant.now().getEpochSecond());
        Assertions.assertNotNull(helloBytes);
    }

    @Test
    public void testCreateCommandBytes() throws Exception {
        CommandFactory commandFactory = new IcCommandFactory();

        // Bluetooth Secret
        BluetoothControlData controlData = new BluetoothControlData();
        controlData.setBoxFlag("KEYTEST000001");
        controlData.setCommandId(1L); //统一获取流水号
        controlData.setKey(Base64.getDecoder().decode("MDEyMzQ1Njc4OWFiY2RlZg=="));
        controlData.setBluetoothSecret(IcDataPackUtils.strToBcd("867858032224872"));

        ByteBuf commandByteBuf = commandFactory.createCommand(CommandType.SET_BLUETOOTH_SECRET, controlData);
        log.info(ByteBufUtil.hexDump(commandByteBuf));
        ReferenceCountUtil.release(commandByteBuf); //记得释放buffer

        // Restart
        BaseControlData baseControlData = new BaseControlData();
        baseControlData.setBoxFlag("KEYTEST000001");
        baseControlData.setCommandId(1L); //统一获取流水号
        baseControlData.setKey(Base64.getDecoder().decode("MDEyMzQ1Njc4OWFiY2RlZg=="));

        commandByteBuf = commandFactory.createCommand(CommandType.RESTART_TERMINAL, baseControlData);
        log.info(ByteBufUtil.hexDump(commandByteBuf));
        String cmdString = Base64.getEncoder().encodeToString(commandByteBuf.array());
        log.info(cmdString);
        ReferenceCountUtil.release(commandByteBuf); //记得释放buffer

        // Device
        baseControlData = new BaseControlData();
        baseControlData.setBoxFlag("KEYTEST000001");
        baseControlData.setCommandId(1L); //统一获取流水号
        baseControlData.setKey(Base64.getDecoder().decode("MDEyMzQ1Njc4OWFiY2RlZg=="));

        commandByteBuf = commandFactory.createCommand(CommandType.QUERY_TERMINAL_ATTRS, baseControlData);
        log.info(ByteBufUtil.hexDump(commandByteBuf));
        cmdString = Base64.getEncoder().encodeToString(commandByteBuf.array());
        log.info(cmdString);
        ReferenceCountUtil.release(commandByteBuf); //记得释放buffer
    }

    @Test
    public void testExecuteCommand() throws Exception {
        // 设备号
        String vin = "TESTGPS0000000001";
        String deviceId = "KEYTEST000001";
        long msgSn = Instant.now().getEpochSecond();

        // 发下指令
        CommandFactory commandFactory = new IcCommandFactory();

        // 解锁车门
        DoorControlData controlData = new DoorControlData();
        controlData.setBoxFlag(deviceId);
        controlData.setCommandId(msgSn);
        controlData.setKey(Base64.getDecoder().decode("MDEyMzQ1Njc4OWFiY2RlZg=="));
        ByteBuf commandByteBuf = commandFactory.createCommand(CommandType.OPEN_DOOR, controlData);

        byte[] commandBytes = ByteBufUtil.getBytes(commandByteBuf);
        log.info(ByteBufUtil.hexDump(commandByteBuf));
        ReferenceCountUtil.release(commandByteBuf);

        // 执行指令
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 40000)
                .usePlaintext()
                .build();
        CommandServiceGrpc.CommandServiceBlockingStub stub2 = CommandServiceGrpc.newBlockingStub(channel);
        Gateway.CommandParam param = Gateway.CommandParam.newBuilder()
                /*.setMsgId(0x07)*/
                .setMsgSn(msgSn)
                .setDeviceId(deviceId)
                .setVin(vin)
                .setCommandString(Base64.getEncoder().encodeToString(commandBytes))
                .build();

        Gateway.CommandData data = stub2.execute(param);
        channel.shutdownNow();

        log.info("data2: {}", data);
    }

    @Test
    public void testExecuteCommandForResult() throws Exception {
        String vin = "TESTGPS0000000001";
        String deviceId = "KEYTEST000001";
        long msgSn = 2; //Instant.now().getEpochSecond();

        CommandFactory commandFactory = new IcCommandFactory();

        // 请求整车数据
        /*RequstVehicleInfoControlData controlData = new RequstVehicleInfoControlData();
        controlData.setBoxFlag(deviceId);
        controlData.setCommandId(msgSn);
        controlData.setKey(Base64.getDecoder().decode("MDEyMzQ1Njc4OWFiY2RlZg=="));
        ByteBuf commandByteBuf = commandFactory.createCommand(CommandType.REQUEST_VEHICLE_DATA, controlData);*/

        // 获取设备信息
        BaseControlData baseControlData = new BaseControlData();
        baseControlData.setBoxFlag(deviceId);
        baseControlData.setCommandId(1L); //统一获取流水号
        baseControlData.setKey(Base64.getDecoder().decode("MDEyMzQ1Njc4OWFiY2RlZg=="));
        ByteBuf commandByteBuf = commandFactory.createCommand(CommandType.QUERY_TERMINAL_ATTRS, baseControlData);

        byte[] commandBytes = ByteBufUtil.getBytes(commandByteBuf);
        log.info(ByteBufUtil.hexDump(commandByteBuf));
        ReferenceCountUtil.release(commandByteBuf);

        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 40010)
                .usePlaintext()
                .build();
        ControlServiceGrpc.ControlServiceBlockingStub stub = ControlServiceGrpc.newBlockingStub(channel);
        Pvo.ControlParam param = Pvo.ControlParam.newBuilder()
                .setMsgSn(msgSn)
                .setDeviceId(deviceId)
                .setVin(vin)
                .setCommandString(Base64.getEncoder().encodeToString(commandBytes))
                .build();

        Pvo.ControlData data = stub.executeForResult(param);
        channel.shutdownNow();

        log.info("data: {}", data);
    }

    @Test
    public void testQueryStatus() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 40010)
                .usePlaintext()
                .build();
        Jt808DataServiceGrpc.Jt808DataServiceBlockingStub stub = Jt808DataServiceGrpc.newBlockingStub(channel);
        Pvo.StatusParam param = Pvo.StatusParam.newBuilder()
                .setVin("TESTGPS0000000001")
                .setDeviceId("91111000001")
                .build();
        Pvo.StatusData data = stub.queryStatus(param);
        channel.shutdownNow();

        log.info("data: {}", data);
    }

    @Test
    public void testQueryPosition() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 40010)
                .usePlaintext()
                .build();
        Jt808DataServiceGrpc.Jt808DataServiceBlockingStub stub = Jt808DataServiceGrpc.newBlockingStub(channel);
        Pvo.PositionParam param = Pvo.PositionParam.newBuilder()
                .setVin("TESTGPS0000000001")
                .setDeviceId("91111000001")
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
                log.info("vin: {}, longitude: {}, latitude: {}, detectionTime: {}", value.getVin(), value.getLongitude(), value.getLatitude(), dateFormat.format(detectionTime));
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
        String testVin = "TESTGPS0000000001";
        String testDeviceId = "91111000001";
        Pvo.PositionDataStreamParam request = Pvo.PositionDataStreamParam.newBuilder()
                .setAppId("test")
                .setVin(testVin)
                .setDeviceId(testDeviceId)
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
                .setDeviceId(testDeviceId)
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
                .setVin("TESTGPS0000000001")
                .setDeviceId("KEYTEST000001")
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
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 40010) //122.9.51.1
                .usePlaintext()
                .build();
        IcDataServiceGrpc.IcDataServiceStub stub = IcDataServiceGrpc.newStub(channel);

        // 双流模式
        StreamObserver<Pvo.VehicleStatusDataStreamParam> observer = stub.queryVehicleStatusStream(new StreamObserver<Pvo.VehicleStatusData>() {

            @Override
            public void onNext(Pvo.VehicleStatusData value) {
                Date detectionTime = Date.from(Instant.ofEpochMilli(value.getDetectionTime()));
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                log.info("vin: {}, longitude: {}, latitude: {}, gpsSpeed: {}, detectionTime: {}", value.getVin(), value.getLongitude(), value.getLatitude(), value.getGpsSpeed(), dateFormat.format(detectionTime));
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
        String testVin = "TESTGPS0000000001";
        String testDeviceId = "KEYTEST000001";
        Pvo.VehicleStatusDataStreamParam request = Pvo.VehicleStatusDataStreamParam.newBuilder()
                .setAppId("test")
                .setVin(testVin)
                .setDeviceId(testDeviceId)
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
                .setDeviceId(testDeviceId)
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
    public void testQueryDeviceStatus() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 40010)
                .usePlaintext()
                .build();
        IcDataServiceGrpc.IcDataServiceBlockingStub stub = IcDataServiceGrpc.newBlockingStub(channel);

        Pvo.DeviceStatusParam param = Pvo.DeviceStatusParam.newBuilder()
                .setDeviceId("KEYTEST000001")
                .setVin("TESTBOX0000000001")
                .build();
        Pvo.DeviceStatusData data = stub.queryDeviceStatus(param);
        channel.shutdownNow();

        log.info("data: {}", data);
    }
}
