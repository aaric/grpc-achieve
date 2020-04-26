package com.incarcloud.grpc.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.28.1)",
    comments = "Source: Position.proto")
public final class PositionServiceGrpc {

  private PositionServiceGrpc() {}

  public static final String SERVICE_NAME = "PositionService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.incarcloud.grpc.proto.Position.PositionParam,
      com.incarcloud.grpc.proto.Position.PositionDataList> getQueryListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "queryList",
      requestType = com.incarcloud.grpc.proto.Position.PositionParam.class,
      responseType = com.incarcloud.grpc.proto.Position.PositionDataList.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.incarcloud.grpc.proto.Position.PositionParam,
      com.incarcloud.grpc.proto.Position.PositionDataList> getQueryListMethod() {
    io.grpc.MethodDescriptor<com.incarcloud.grpc.proto.Position.PositionParam, com.incarcloud.grpc.proto.Position.PositionDataList> getQueryListMethod;
    if ((getQueryListMethod = PositionServiceGrpc.getQueryListMethod) == null) {
      synchronized (PositionServiceGrpc.class) {
        if ((getQueryListMethod = PositionServiceGrpc.getQueryListMethod) == null) {
          PositionServiceGrpc.getQueryListMethod = getQueryListMethod =
              io.grpc.MethodDescriptor.<com.incarcloud.grpc.proto.Position.PositionParam, com.incarcloud.grpc.proto.Position.PositionDataList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "queryList"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.incarcloud.grpc.proto.Position.PositionParam.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.incarcloud.grpc.proto.Position.PositionDataList.getDefaultInstance()))
              .setSchemaDescriptor(new PositionServiceMethodDescriptorSupplier("queryList"))
              .build();
        }
      }
    }
    return getQueryListMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PositionServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PositionServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PositionServiceStub>() {
        @java.lang.Override
        public PositionServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PositionServiceStub(channel, callOptions);
        }
      };
    return PositionServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PositionServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PositionServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PositionServiceBlockingStub>() {
        @java.lang.Override
        public PositionServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PositionServiceBlockingStub(channel, callOptions);
        }
      };
    return PositionServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PositionServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PositionServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PositionServiceFutureStub>() {
        @java.lang.Override
        public PositionServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PositionServiceFutureStub(channel, callOptions);
        }
      };
    return PositionServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class PositionServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void queryList(com.incarcloud.grpc.proto.Position.PositionParam request,
        io.grpc.stub.StreamObserver<com.incarcloud.grpc.proto.Position.PositionDataList> responseObserver) {
      asyncUnimplementedUnaryCall(getQueryListMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getQueryListMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.incarcloud.grpc.proto.Position.PositionParam,
                com.incarcloud.grpc.proto.Position.PositionDataList>(
                  this, METHODID_QUERY_LIST)))
          .build();
    }
  }

  /**
   */
  public static final class PositionServiceStub extends io.grpc.stub.AbstractAsyncStub<PositionServiceStub> {
    private PositionServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PositionServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PositionServiceStub(channel, callOptions);
    }

    /**
     */
    public void queryList(com.incarcloud.grpc.proto.Position.PositionParam request,
        io.grpc.stub.StreamObserver<com.incarcloud.grpc.proto.Position.PositionDataList> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getQueryListMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class PositionServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<PositionServiceBlockingStub> {
    private PositionServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PositionServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PositionServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.incarcloud.grpc.proto.Position.PositionDataList queryList(com.incarcloud.grpc.proto.Position.PositionParam request) {
      return blockingUnaryCall(
          getChannel(), getQueryListMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class PositionServiceFutureStub extends io.grpc.stub.AbstractFutureStub<PositionServiceFutureStub> {
    private PositionServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PositionServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PositionServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.incarcloud.grpc.proto.Position.PositionDataList> queryList(
        com.incarcloud.grpc.proto.Position.PositionParam request) {
      return futureUnaryCall(
          getChannel().newCall(getQueryListMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_QUERY_LIST = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PositionServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PositionServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_QUERY_LIST:
          serviceImpl.queryList((com.incarcloud.grpc.proto.Position.PositionParam) request,
              (io.grpc.stub.StreamObserver<com.incarcloud.grpc.proto.Position.PositionDataList>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class PositionServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PositionServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.incarcloud.grpc.proto.Position.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PositionService");
    }
  }

  private static final class PositionServiceFileDescriptorSupplier
      extends PositionServiceBaseDescriptorSupplier {
    PositionServiceFileDescriptorSupplier() {}
  }

  private static final class PositionServiceMethodDescriptorSupplier
      extends PositionServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PositionServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PositionServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PositionServiceFileDescriptorSupplier())
              .addMethod(getQueryListMethod())
              .build();
        }
      }
    }
    return result;
  }
}
