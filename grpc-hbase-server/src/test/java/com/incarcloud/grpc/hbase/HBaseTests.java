package com.incarcloud.grpc.hbase;

import lombok.extern.log4j.Log4j2;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.MessageFormat;

/**
 * HBaseTests
 *
 * @author Aaric, created on 2020-05-13T08:50.
 * @version 0.4.2-SNAPSHOT
 */
@Log4j2
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class HBaseTests {

    @Autowired
    private Connection hbaseConnection;

    @Value("${incarcloud.hbase.table.test}")
    private String hbaseTableTest;

    @Test
    @Disabled
    public void testStorageIc() throws Exception {
        String mockData = "{\"member\":0,\"dataPackVehicleConfig\":{\"isGetPosition\":1,\"isGetSoc\":1,\"isGetSpeed\":1,\"isGetMileage\":1,\"isGetDoor\":1,\"isGetLock\":1,\"isGetWindow\":1,\"isGetAcc\":1,\"isGetHandBrake\":1,\"isGetGear\":1,\"isGetCharge\":1,\"isGetCircuit\":1,\"isGetBatteryVoltage\":1,\"isGetPowerCellVoltage\":1,\"isGetNetworkSignal\":1,\"isGetGnss\":1,\"isGetFault\":1,\"isGetSkylight\":1,\"isGetRemoteControlLock\":1,\"isGetPke\":1,\"isGetStart\":1,\"isGetHeadlight\":1,\"isGetFootBrake\":1,\"isGetSeatBelts\":1,\"isGetAirConditioner\":1,\"isGetRechargeMileage\":1,\"isGetFuelQuantity\":1,\"isGetTirePressure\":1,\"isGetInTemperature\":1,\"packId\":11,\"deviceId\":\"YK001912F6\",\"detectionTime\":\"20200403092529\",\"packType\":3,\"receiveTime\":\"20200403102822\"},\"dataPackPosition\":{\"positionMode\":1,\"longitudeType\":0,\"latitudeType\":0,\"longitude\":113.94556,\"latitude\":22.534908,\"direction\":0.0,\"altitude\":11.0,\"speed\":0.0,\"positionTime\":\"20200403092529\",\"packId\":11,\"deviceId\":\"YK001912F6\",\"vin\":\"NC123456720200403\",\"detectionTime\":\"20200403092529\",\"packType\":3,\"receiveTime\":\"20200403102822\"},\"leftFrontDoorStatus\":0,\"rightFrontDoorStatus\":0,\"leftBackDoorStatus\":0,\"rightBackDoorStatus\":0,\"trunkStatus\":0,\"centralLockStatus\":0,\"windowStatus\":0,\"accStatus\":2,\"handBrakeStatus\":0,\"clutchStatus\":31,\"chargeStatus\":2,\"evChargerStatus\":0,\"leftFrontWindowStatus\":0,\"rightFrontWindowStatus\":0,\"leftBackWindowStatus\":0,\"rightBackWindowStatus\":0,\"skylightStatus\":0,\"remoteControlLockStatus\":0,\"pkeStatus\":0,\"carStatus\":0,\"dippedHeadlightStatus\":0,\"footBrakeStatus\":0,\"mainSeatBeltsStatus\":0,\"passengerSeatBeltsStatus\":0,\"soc\":0,\"vehicleSpeed\":0.0,\"mileage\":0.0,\"batteryVoltage\":14,\"powerCellVoltage\":0,\"gnssSatelliteNumber\":7,\"gnssChannelNumber\":0,\"fault\":\"Kong\",\"airConditionerStatus\":2,\"airConditionerFanStatus\":0,\"rechargeMileage\":0.0,\"fuelQuantity\":0,\"leftFrontTirePressure\":0,\"rightFrontTirePressure\":0,\"leftRearTirePressure\":0,\"rightRearTirePressure\":0,\"insideTemperature\":-40.0,\"dynamicalFuel\":0.0,\"avgOilUsed\":0.0,\"packageType\":1,\"packId\":11,\"deviceId\":\"YK001912F6\",\"vin\":\"NC123456720200403\",\"detectionTime\":\"20200403092529\",\"packType\":3,\"receiveTime\":\"20200403102822\"}";
        String mockOrigin = "292932005b0a594b31323032353230390000259b00000000ffffffff010aaaa8000001551414050d08221e06e87cc801b4e9760063003f00000000000000000c00000c070001010004b017027b00f0027b00f503520000007955aaecccb6f70d";
        Table table = hbaseConnection.getTable(TableName.valueOf(hbaseTableTest));

        for (int i = 0; i < 9000; i++) {
            String rowKey = MessageFormat.format("0bf2000NC123456720200403OVERVIEW#######20200513090000####{0,number,0000}", i + 1);
            Put put = new Put(rowKey.getBytes());
            put.addColumn(Bytes.toBytes("base"), Bytes.toBytes("data"), Bytes.toBytes(mockData));
            put.addColumn(Bytes.toBytes("base"), Bytes.toBytes("origin"), Bytes.toBytes(mockOrigin));
            table.put(put);

            log.debug("Put rowKey: {}", rowKey);
        }
    }

    @Test
    @Disabled
    public void testStorageJtt808() throws Exception {
        String mockData = "{\"speed\":1.8000001,\"longitude\":113.906096,\"latitude\":22.583498,\"altitude\":0,\"direction\":0.0,\"positionMode\":6,\"positionModeDesc\":\"未知\",\"positionTime\":\"20180417114218\",\"packId\":36,\"deviceId\":\"018168000002\",\"vin\":\"LFV2A21J970002020\",\"detectionTime\":\"20180417114218\",\"packType\":3,\"receiveTime\":\"20200509182652\"}";
        String mockOrigin = "7e0200002801816800000200240000008000000082015898ca06ca11b000000012000018041711421801040000000630010f31010bd47e";
        Table table = hbaseConnection.getTable(TableName.valueOf(hbaseTableTest));

        for (int i = 0; i < 9000; i++) {
            String rowKey = MessageFormat.format("1e11000LFV2A21J970002020JTT808POSITION#20180417114218####{0,number,0000}", i + 1);
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes("base"), Bytes.toBytes("data"), Bytes.toBytes(mockData));
            put.addColumn(Bytes.toBytes("base"), Bytes.toBytes("origin"), Bytes.toBytes(mockOrigin));
            table.put(put);

            log.debug("Put rowKey: {}", rowKey);
        }
    }
}
