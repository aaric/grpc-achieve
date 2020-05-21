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
    comments = "Source: Stream.proto")
public final class StreamServiceGrpc {

  private StreamServiceGrpc() {}

  public static final String SERVICE_NAME = "stream.StreamService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.incarcloud.grpc.proto.Stream.StreamParam,
      com.incarcloud.grpc.proto.Stream.StreamData> getQueryServerStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "queryServerStream",
      requestType = com.incarcloud.grpc.proto.Stream.StreamParam.class,
      responseType = com.incarcloud.grpc.proto.Stream.StreamData.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.incarcloud.grpc.proto.Stream.StreamParam,
      com.incarcloud.grpc.proto.Stream.StreamData> getQueryServerStreamMethod() {
    io.grpc.MethodDescriptor<com.incarcloud.grpc.proto.Stream.StreamParam, com.incarcloud.grpc.proto.Stream.StreamData> getQueryServerStreamMethod;
    if ((getQueryServerStreamMethod = StreamServiceGrpc.getQueryServerStreamMethod) == null) {
      synchronized (StreamServiceGrpc.class) {
        if ((getQueryServerStreamMethod = StreamServiceGrpc.getQueryServerStreamMethod) == null) {
          StreamServiceGrpc.getQueryServerStreamMethod = getQueryServerStreamMethod =
              io.grpc.MethodDescriptor.<com.incarcloud.grpc.proto.Stream.StreamParam, com.incarcloud.grpc.proto.Stream.StreamData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "queryServerStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.incarcloud.grpc.proto.Stream.StreamParam.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.incarcloud.grpc.proto.Stream.StreamData.getDefaultInstance()))
              .setSchemaDescriptor(new StreamServiceMethodDescriptorSupplier("queryServerStream"))
              .build();
        }
      }
    }
    return getQueryServerStreamMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.incarcloud.grpc.proto.Stream.StreamParam,
      com.incarcloud.grpc.proto.Stream.StreamData> getQueryClientStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "queryClientStream",
      requestType = com.incarcloud.grpc.proto.Stream.StreamParam.class,
      responseType = com.incarcloud.grpc.proto.Stream.StreamData.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<com.incarcloud.grpc.proto.Stream.StreamParam,
      com.incarcloud.grpc.proto.Stream.StreamData> getQueryClientStreamMethod() {
    io.grpc.MethodDescriptor<com.incarcloud.grpc.proto.Stream.StreamParam, com.incarcloud.grpc.proto.Stream.StreamData> getQueryClientStreamMethod;
    if ((getQueryClientStreamMethod = StreamServiceGrpc.getQueryClientStreamMethod) == null) {
      synchronized (StreamServiceGrpc.class) {
        if ((getQueryClientStreamMethod = StreamServiceGrpc.getQueryClientStreamMethod) == null) {
          StreamServiceGrpc.getQueryClientStreamMethod = getQueryClientStreamMethod =
              io.grpc.MethodDescriptor.<com.incarcloud.grpc.proto.Stream.StreamParam, com.incarcloud.grpc.proto.Stream.StreamData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "queryClientStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.incarcloud.grpc.proto.Stream.StreamParam.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.incarcloud.grpc.proto.Stream.StreamData.getDefaultInstance()))
              .setSchemaDescriptor(new StreamServiceMethodDescriptorSupplier("queryClientStream"))
              .build();
        }
      }
    }
    return getQueryClientStreamMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.incarcloud.grpc.proto.Stream.StreamParam,
      com.incarcloud.grpc.proto.Stream.StreamData> getQueryAllStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "queryAllStream",
      requestType = com.incarcloud.grpc.proto.Stream.StreamParam.class,
      responseType = com.incarcloud.grpc.proto.Stream.StreamData.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<com.incarcloud.grpc.proto.Stream.StreamParam,
      com.incarcloud.grpc.proto.Stream.StreamData> getQueryAllStreamMethod() {
    io.grpc.MethodDescriptor<com.incarcloud.grpc.proto.Stream.StreamParam, com.incarcloud.grpc.proto.Stream.StreamData> getQueryAllStreamMethod;
    if ((getQueryAllStreamMethod = StreamServiceGrpc.getQueryAllStreamMethod) == null) {
      synchronized (StreamServiceGrpc.class) {
        if ((getQueryAllStreamMethod = StreamServiceGrpc.getQueryAllStreamMethod) == null) {
          StreamServiceGrpc.getQueryAllStreamMethod = getQueryAllStreamMethod =
              io.grpc.MethodDescriptor.<com.incarcloud.grpc.proto.Stream.StreamParam, com.incarcloud.grpc.proto.Stream.StreamData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "queryAllStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.incarcloud.grpc.proto.Stream.StreamParam.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.incarcloud.grpc.proto.Stream.StreamData.getDefaultInstance()))
              .setSchemaDescriptor(new StreamServiceMethodDescriptorSupplier("queryAllStream"))
              .build();
        }
      }
    }
    return getQueryAllStreamMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static StreamServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StreamServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StreamServiceStub>() {
        @java.lang.Override
        public StreamServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StreamServiceStub(channel, callOptions);
        }
      };
    return StreamServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static StreamServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StreamServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StreamServiceBlockingStub>() {
        @java.lang.Override
        public StreamServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StreamServiceBlockingStub(channel, callOptions);
        }
      };
    return StreamServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static StreamServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StreamServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StreamServiceFutureStub>() {
        @java.lang.Override
        public StreamServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StreamServiceFutureStub(channel, callOptions);
        }
      };
    return StreamServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class StreamServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * 服务端流模式
     * </pre>
     */
    public void queryServerStream(com.incarcloud.grpc.proto.Stream.StreamParam request,
        io.grpc.stub.StreamObserver<com.incarcloud.grpc.proto.Stream.StreamData> responseObserver) {
      asyncUnimplementedUnaryCall(getQueryServerStreamMethod(), responseObserver);
    }

    /**
     * <pre>
     * 客户端流模式
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.incarcloud.grpc.proto.Stream.StreamParam> queryClientStream(
        io.grpc.stub.StreamObserver<com.incarcloud.grpc.proto.Stream.StreamData> responseObserver) {
      return asyncUnimplementedStreamingCall(getQueryClientStreamMethod(), responseObserver);
    }

    /**
     * <pre>
     * 双流模式
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.incarcloud.grpc.proto.Stream.StreamParam> queryAllStream(
        io.grpc.stub.StreamObserver<com.incarcloud.grpc.proto.Stream.StreamData> responseObserver) {
      return asyncUnimplementedStreamingCall(getQueryAllStreamMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getQueryServerStreamMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                com.incarcloud.grpc.proto.Stream.StreamParam,
                com.incarcloud.grpc.proto.Stream.StreamData>(
                  this, METHODID_QUERY_SERVER_STREAM)))
          .addMethod(
            getQueryClientStreamMethod(),
            asyncClientStreamingCall(
              new MethodHandlers<
                com.incarcloud.grpc.proto.Stream.StreamParam,
                com.incarcloud.grpc.proto.Stream.StreamData>(
                  this, METHODID_QUERY_CLIENT_STREAM)))
          .addMethod(
            getQueryAllStreamMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                com.incarcloud.grpc.proto.Stream.StreamParam,
                com.incarcloud.grpc.proto.Stream.StreamData>(
                  this, METHODID_QUERY_ALL_STREAM)))
          .build();
    }
  }

  /**
   */
  public static final class StreamServiceStub extends io.grpc.stub.AbstractAsyncStub<StreamServiceStub> {
    private StreamServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StreamServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StreamServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 服务端流模式
     * </pre>
     */
    public void queryServerStream(com.incarcloud.grpc.proto.Stream.StreamParam request,
        io.grpc.stub.StreamObserver<com.incarcloud.grpc.proto.Stream.StreamData> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getQueryServerStreamMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 客户端流模式
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.incarcloud.grpc.proto.Stream.StreamParam> queryClientStream(
        io.grpc.stub.StreamObserver<com.incarcloud.grpc.proto.Stream.StreamData> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getQueryClientStreamMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * 双流模式
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.incarcloud.grpc.proto.Stream.StreamParam> queryAllStream(
        io.grpc.stub.StreamObserver<com.incarcloud.grpc.proto.Stream.StreamData> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getQueryAllStreamMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class StreamServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<StreamServiceBlockingStub> {
    private StreamServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StreamServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StreamServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 服务端流模式
     * </pre>
     */
    public java.util.Iterator<com.incarcloud.grpc.proto.Stream.StreamData> queryServerStream(
        com.incarcloud.grpc.proto.Stream.StreamParam request) {
      return blockingServerStreamingCall(
          getChannel(), getQueryServerStreamMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class StreamServiceFutureStub extends io.grpc.stub.AbstractFutureStub<StreamServiceFutureStub> {
    private StreamServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StreamServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StreamServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_QUERY_SERVER_STREAM = 0;
  private static final int METHODID_QUERY_CLIENT_STREAM = 1;
  private static final int METHODID_QUERY_ALL_STREAM = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final StreamServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(StreamServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_QUERY_SERVER_STREAM:
          serviceImpl.queryServerStream((com.incarcloud.grpc.proto.Stream.StreamParam) request,
              (io.grpc.stub.StreamObserver<com.incarcloud.grpc.proto.Stream.StreamData>) responseObserver);
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
        case METHODID_QUERY_CLIENT_STREAM:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.queryClientStream(
              (io.grpc.stub.StreamObserver<com.incarcloud.grpc.proto.Stream.StreamData>) responseObserver);
        case METHODID_QUERY_ALL_STREAM:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.queryAllStream(
              (io.grpc.stub.StreamObserver<com.incarcloud.grpc.proto.Stream.StreamData>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class StreamServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    StreamServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.incarcloud.grpc.proto.Stream.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("StreamService");
    }
  }

  private static final class StreamServiceFileDescriptorSupplier
      extends StreamServiceBaseDescriptorSupplier {
    StreamServiceFileDescriptorSupplier() {}
  }

  private static final class StreamServiceMethodDescriptorSupplier
      extends StreamServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    StreamServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (StreamServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new StreamServiceFileDescriptorSupplier())
              .addMethod(getQueryServerStreamMethod())
              .addMethod(getQueryClientStreamMethod())
              .addMethod(getQueryAllStreamMethod())
              .build();
        }
      }
    }
    return result;
  }
}
