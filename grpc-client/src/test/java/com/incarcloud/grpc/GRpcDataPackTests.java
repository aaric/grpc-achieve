package com.incarcloud.grpc;

import com.incarcloud.boar.util.RowKeyUtil;
import com.incarcloud.proto.pvo.DataPack;
import com.incarcloud.proto.pvo.DataPackServiceGrpc;
import com.incarcloud.proto.pvo.IcDataServiceGrpc;
import com.incarcloud.proto.pvo.Pvo;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.netty.buffer.ByteBufUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

/**
 * GRpcDataPackTests
 *
 * @author Aaric, created on 2020-06-09T09:53.
 * @version 0.6.3-SNAPSHOT
 */
@Slf4j
public class GRpcDataPackTests {

    @Test
    public void testPack() {
        String cmdString = "KSkKABoKWUsxMjAxNDkyMV7oQUX/////VT6r69y4DQ==";
        String cmdBytes = ByteBufUtil.hexDump(Base64.getDecoder().decode(cmdString));
        log.info("cmdBytes -> {}", cmdBytes);
        Assertions.assertNotNull(cmdBytes);
    }

    @Test
    public void testDeviceId() {
        String hexString = "4B455954455354303530";
        String deviceId = new String(ByteBufUtil.decodeHexDump(hexString));
        log.info("deviceId -> {}", deviceId);
        Assertions.assertNotNull(deviceId);
    }

    @Test
    public void testMakeMaxRowKey() {
        //String rowKey = RowKeyUtil.makeMaxRowKey("CS123456720242617", "OVERVIEW");
        //String rowKey = RowKeyUtil.makeMaxRowKey("LFV2A21J970002020", "OVERVIEW");
        //String rowKey = RowKeyUtil.makeMaxRowKey("LVGBPB9E7KG006111", "CHECK");
        String rowKey = RowKeyUtil.makeMaxRowKey("VINTEST0000100001", "OVERVIEW");
        log.info("row key: {}", rowKey);
        Assertions.assertNotNull(rowKey);
    }

    @Test
    public void testQueryDataForJtt808Position() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 40010) //116.63.79.61
                .usePlaintext()
                .build();
        DataPackServiceGrpc.DataPackServiceBlockingStub stub = DataPackServiceGrpc.newBlockingStub(channel);
        DataPack.KeyParam param = DataPack.KeyParam.newBuilder()
                .setRowKey("3cca000LFV2A21J970002010JTT808POSITION#20180417114218####0001")
                .build();
        DataPack.Jtt808PositionData data = stub.queryDataForJtt808Position(param);
        channel.shutdownNow();

        log.info("data: {}", data);
        Assertions.assertNotNull(data);
    }

    @Test
    public void testQueryDataForIcOverview() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 40010) //122.9.51.1
                .usePlaintext()
                .build();
        DataPackServiceGrpc.DataPackServiceBlockingStub stub = DataPackServiceGrpc.newBlockingStub(channel);
        DataPack.KeyParam param = DataPack.KeyParam.newBuilder()
                .setRowKey("d98e000LVGBPB9E7KG006111OVERVIEW#######20200617093645####0001")
                .build();
        DataPack.IcOverviewData data = stub.queryDataForIcOverview(param);
        channel.shutdownNow();

        log.info("data: {}", data);
        Assertions.assertNotNull(data);
    }

    @Test
    public void testQueryRangeForIcCheck() throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 40010)
                .usePlaintext()
                .build();
        DataPackServiceGrpc.DataPackServiceBlockingStub stub = DataPackServiceGrpc.newBlockingStub(channel);
        DataPack.RangeParam param = DataPack.RangeParam.newBuilder()
                .setVin("LVGBPB9E7KG006111") //车架号
                .setBeginTime(dateFormat.parse("2020-06-01 00:00:00").getTime()) //开始时间
                .setEndTime(Instant.now().toEpochMilli()) //结束时间
                .setPageSize(1) //返回记录条数
                .setDesc(true) //是否倒序
                .build();
        DataPack.IcCheckDataList data = stub.queryRangeForIcCheck(param);
        channel.shutdownNow();

        log.info("data: {}", data);
        Assertions.assertNotNull(data);
    }

    @Test
    public void testQueryRangeForIcOverview() throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        long start = 1592875710000L;
//        long end = 1592882910000L;
//        System.err.println(dateFormat.format(new Date(start)));
//        System.err.println(dateFormat.format(new Date(end)));

        ManagedChannel channel = ManagedChannelBuilder.forAddress("122.9.49.238", 40010)
                .usePlaintext()
                .build();
        DataPackServiceGrpc.DataPackServiceBlockingStub stub = DataPackServiceGrpc.newBlockingStub(channel);
        DataPack.RangeParam param = DataPack.RangeParam.newBuilder()
                .setVin("LE4WG7HB5LL616925")
                .setBeginTime(dateFormat.parse("2020-08-11 09:52:00").getTime()) //开始时间
                .setEndTime(dateFormat.parse("2020-08-12 00:00:00").getTime()) //结束时间
//                .setBeginTime(start) //开始时间
//                .setEndTime(end) //结束时间
                .setPageSize(9999) //返回记录条数
                .setDesc(true) //是否倒序
                .build();
        DataPack.IcOverviewDataList data = stub.queryRangeForIcOverview(param);
        channel.shutdownNow();

        log.info("data: {}", data.getIcOverviewDataCount());
        Assertions.assertNotNull(data);
    }

    @Test
    public void testQueryRangeForIcOverviewMin() throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long start = 1592875710000L;
        long end = 1592882910000L;
        System.err.println(dateFormat.format(new Date(start)));
        System.err.println(dateFormat.format(new Date(end)));

        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 40010) //116.63.70.11
                .usePlaintext()
                .build();
        DataPackServiceGrpc.DataPackServiceBlockingStub stub = DataPackServiceGrpc.newBlockingStub(channel);
        DataPack.RangeParam param = DataPack.RangeParam.newBuilder()
                .setVin("VINTEST0000100001") //车架号-LVGBPB9E7KG006111
//                .setBeginTime(dateFormat.parse("2020-06-01 00:00:00").getTime()) //开始时间
//                .setEndTime(Instant.now().toEpochMilli()) //结束时间
                .setBeginTime(start) //开始时间
                .setEndTime(end) //结束时间
                .setPageSize(9999) //返回记录条数
                .setDesc(true) //是否倒序
                .build();
        DataPack.IcOverviewDataMinList data = stub.queryRangeForIcOverviewMin(param);
        channel.shutdownNow();

        log.info("data: {}", data);
        Assertions.assertNotNull(data);
    }

    @Test
    public void testQueryStatus() throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 40010) //122.9.51.1
                .usePlaintext()
                .build();
        IcDataServiceGrpc.IcDataServiceBlockingStub stub = IcDataServiceGrpc.newBlockingStub(channel);
        Pvo.StatusParam param = Pvo.StatusParam.newBuilder()
                .setVin("LVGBPB9E7KG006111") //车架号
                .build();
        Pvo.StatusData data = stub.queryStatus(param);
        channel.shutdownNow();

        // 车辆状态：0-未知, 1-在线, 2-离线, 3-异常, 4-行驶中, 5-待机
        log.info("data: {}", data);
        Assertions.assertNotNull(data);
    }

    @Test
    public void testQueryRangeForIcPosition() throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 40010)
                .usePlaintext()
                .build();
        DataPackServiceGrpc.DataPackServiceBlockingStub stub = DataPackServiceGrpc.newBlockingStub(channel);
        DataPack.RangeParam param = DataPack.RangeParam.newBuilder()
                .setVin("LE4WG7HB5LL616925")
                .setBeginTime(dateFormat.parse("2020-08-11 09:52:00").getTime()) //开始时间
                .setEndTime(dateFormat.parse("2020-08-12 00:00:00").getTime()) //结束时间
                .setPageSize(9999) //返回记录条数
                .setDesc(true) //是否倒序
                .build();
        DataPack.IcPositionDataList data = stub.queryRangeForIcPosition(param);
        channel.shutdownNow();

        log.info("data: {}", data);
        Assertions.assertNotNull(data);
    }
}
