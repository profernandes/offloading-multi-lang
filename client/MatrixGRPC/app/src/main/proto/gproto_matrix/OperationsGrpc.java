package gproto_matrix;

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
    value = "by gRPC proto compiler (version 1.26.0)",
    comments = "Source: matrix.proto")
public final class OperationsGrpc {

  private OperationsGrpc() {}

  public static final String SERVICE_NAME = "gproto_matrix.Operations";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<gproto_matrix.InMatrices,
      gproto_matrix.OutMatrix> getAddMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Add",
      requestType = gproto_matrix.InMatrices.class,
      responseType = gproto_matrix.OutMatrix.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<gproto_matrix.InMatrices,
      gproto_matrix.OutMatrix> getAddMethod() {
    io.grpc.MethodDescriptor<gproto_matrix.InMatrices, gproto_matrix.OutMatrix> getAddMethod;
    if ((getAddMethod = OperationsGrpc.getAddMethod) == null) {
      synchronized (OperationsGrpc.class) {
        if ((getAddMethod = OperationsGrpc.getAddMethod) == null) {
          OperationsGrpc.getAddMethod = getAddMethod =
              io.grpc.MethodDescriptor.<gproto_matrix.InMatrices, gproto_matrix.OutMatrix>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Add"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gproto_matrix.InMatrices.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gproto_matrix.OutMatrix.getDefaultInstance()))
              .setSchemaDescriptor(new OperationsMethodDescriptorSupplier("Add"))
              .build();
        }
      }
    }
    return getAddMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gproto_matrix.InMatrices,
      gproto_matrix.OutMatrix> getMultiplyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Multiply",
      requestType = gproto_matrix.InMatrices.class,
      responseType = gproto_matrix.OutMatrix.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<gproto_matrix.InMatrices,
      gproto_matrix.OutMatrix> getMultiplyMethod() {
    io.grpc.MethodDescriptor<gproto_matrix.InMatrices, gproto_matrix.OutMatrix> getMultiplyMethod;
    if ((getMultiplyMethod = OperationsGrpc.getMultiplyMethod) == null) {
      synchronized (OperationsGrpc.class) {
        if ((getMultiplyMethod = OperationsGrpc.getMultiplyMethod) == null) {
          OperationsGrpc.getMultiplyMethod = getMultiplyMethod =
              io.grpc.MethodDescriptor.<gproto_matrix.InMatrices, gproto_matrix.OutMatrix>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Multiply"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gproto_matrix.InMatrices.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gproto_matrix.OutMatrix.getDefaultInstance()))
              .setSchemaDescriptor(new OperationsMethodDescriptorSupplier("Multiply"))
              .build();
        }
      }
    }
    return getMultiplyMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static OperationsStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OperationsStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OperationsStub>() {
        @java.lang.Override
        public OperationsStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OperationsStub(channel, callOptions);
        }
      };
    return OperationsStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static OperationsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OperationsBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OperationsBlockingStub>() {
        @java.lang.Override
        public OperationsBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OperationsBlockingStub(channel, callOptions);
        }
      };
    return OperationsBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static OperationsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OperationsFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OperationsFutureStub>() {
        @java.lang.Override
        public OperationsFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OperationsFutureStub(channel, callOptions);
        }
      };
    return OperationsFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class OperationsImplBase implements io.grpc.BindableService {

    /**
     */
    public void add(gproto_matrix.InMatrices request,
        io.grpc.stub.StreamObserver<gproto_matrix.OutMatrix> responseObserver) {
      asyncUnimplementedUnaryCall(getAddMethod(), responseObserver);
    }

    /**
     */
    public void multiply(gproto_matrix.InMatrices request,
        io.grpc.stub.StreamObserver<gproto_matrix.OutMatrix> responseObserver) {
      asyncUnimplementedUnaryCall(getMultiplyMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAddMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gproto_matrix.InMatrices,
                gproto_matrix.OutMatrix>(
                  this, METHODID_ADD)))
          .addMethod(
            getMultiplyMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gproto_matrix.InMatrices,
                gproto_matrix.OutMatrix>(
                  this, METHODID_MULTIPLY)))
          .build();
    }
  }

  /**
   */
  public static final class OperationsStub extends io.grpc.stub.AbstractAsyncStub<OperationsStub> {
    private OperationsStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OperationsStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OperationsStub(channel, callOptions);
    }

    /**
     */
    public void add(gproto_matrix.InMatrices request,
        io.grpc.stub.StreamObserver<gproto_matrix.OutMatrix> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getAddMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void multiply(gproto_matrix.InMatrices request,
        io.grpc.stub.StreamObserver<gproto_matrix.OutMatrix> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getMultiplyMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class OperationsBlockingStub extends io.grpc.stub.AbstractBlockingStub<OperationsBlockingStub> {
    private OperationsBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OperationsBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OperationsBlockingStub(channel, callOptions);
    }

    /**
     */
    public gproto_matrix.OutMatrix add(gproto_matrix.InMatrices request) {
      return blockingUnaryCall(
          getChannel(), getAddMethod(), getCallOptions(), request);
    }

    /**
     */
    public gproto_matrix.OutMatrix multiply(gproto_matrix.InMatrices request) {
      return blockingUnaryCall(
          getChannel(), getMultiplyMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class OperationsFutureStub extends io.grpc.stub.AbstractFutureStub<OperationsFutureStub> {
    private OperationsFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OperationsFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OperationsFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<gproto_matrix.OutMatrix> add(
        gproto_matrix.InMatrices request) {
      return futureUnaryCall(
          getChannel().newCall(getAddMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<gproto_matrix.OutMatrix> multiply(
        gproto_matrix.InMatrices request) {
      return futureUnaryCall(
          getChannel().newCall(getMultiplyMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ADD = 0;
  private static final int METHODID_MULTIPLY = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final OperationsImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(OperationsImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ADD:
          serviceImpl.add((gproto_matrix.InMatrices) request,
              (io.grpc.stub.StreamObserver<gproto_matrix.OutMatrix>) responseObserver);
          break;
        case METHODID_MULTIPLY:
          serviceImpl.multiply((gproto_matrix.InMatrices) request,
              (io.grpc.stub.StreamObserver<gproto_matrix.OutMatrix>) responseObserver);
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

  private static abstract class OperationsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    OperationsBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return gproto_matrix.Matrix.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Operations");
    }
  }

  private static final class OperationsFileDescriptorSupplier
      extends OperationsBaseDescriptorSupplier {
    OperationsFileDescriptorSupplier() {}
  }

  private static final class OperationsMethodDescriptorSupplier
      extends OperationsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    OperationsMethodDescriptorSupplier(String methodName) {
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
      synchronized (OperationsGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new OperationsFileDescriptorSupplier())
              .addMethod(getAddMethod())
              .addMethod(getMultiplyMethod())
              .build();
        }
      }
    }
    return result;
  }
}
