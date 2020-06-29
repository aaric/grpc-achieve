package com.incarcloud.grpc;

import com.incarcloud.proto.vehicle.Vehicle;
import com.incarcloud.proto.vehicle.VehicleArchivesServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * GRpcPlatformTests
 *
 * @author Aaric, created on 2020-06-29T13:55.
 * @version 0.6.3-SNAPSHOT
 */
@Slf4j
public class GRpcPlatformTests {

    @Test
    public void testGetAllVehicleVin() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 18888) //122.9.51.1
                .usePlaintext()
                .build();
        VehicleArchivesServiceGrpc.VehicleArchivesServiceBlockingStub stub = VehicleArchivesServiceGrpc.newBlockingStub(channel);
        Vehicle.AllVehicleVinParam param = Vehicle.AllVehicleVinParam.newBuilder()
                .build();
        Vehicle.AllVehicleVinResponse data = stub.getAllVehicleVin(param);
        channel.shutdownNow();

        log.info("data: {}", data);
    }
}
