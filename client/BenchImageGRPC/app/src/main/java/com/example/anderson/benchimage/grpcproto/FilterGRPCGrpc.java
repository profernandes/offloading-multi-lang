package benchimage;

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
    comments = "Source: image.proto")
public final class FilterGRPCGrpc {

  private FilterGRPCGrpc() {}

  public static final String SERVICE_NAME = "benchimage.FilterGRPC";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<benchimage.Image.ImageGRPC,
      benchimage.Image.ImageGRPC> getGrayscaleFilterMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GrayscaleFilter",
      requestType = benchimage.Image.ImageGRPC.class,
      responseType = benchimage.Image.ImageGRPC.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<benchimage.Image.ImageGRPC,
      benchimage.Image.ImageGRPC> getGrayscaleFilterMethod() {
    io.grpc.MethodDescriptor<benchimage.Image.ImageGRPC, benchimage.Image.ImageGRPC> getGrayscaleFilterMethod;
    if ((getGrayscaleFilterMethod = FilterGRPCGrpc.getGrayscaleFilterMethod) == null) {
      synchronized (FilterGRPCGrpc.class) {
        if ((getGrayscaleFilterMethod = FilterGRPCGrpc.getGrayscaleFilterMethod) == null) {
          FilterGRPCGrpc.getGrayscaleFilterMethod = getGrayscaleFilterMethod =
              io.grpc.MethodDescriptor.<benchimage.Image.ImageGRPC, benchimage.Image.ImageGRPC>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GrayscaleFilter"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  benchimage.Image.ImageGRPC.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  benchimage.Image.ImageGRPC.getDefaultInstance()))
              .build();
        }
      }
    }
    return getGrayscaleFilterMethod;
  }

  private static volatile io.grpc.MethodDescriptor<benchimage.Image.ImageGRPC,
      benchimage.Image.ImageGRPC> getInvertFilterMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "InvertFilter",
      requestType = benchimage.Image.ImageGRPC.class,
      responseType = benchimage.Image.ImageGRPC.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<benchimage.Image.ImageGRPC,
      benchimage.Image.ImageGRPC> getInvertFilterMethod() {
    io.grpc.MethodDescriptor<benchimage.Image.ImageGRPC, benchimage.Image.ImageGRPC> getInvertFilterMethod;
    if ((getInvertFilterMethod = FilterGRPCGrpc.getInvertFilterMethod) == null) {
      synchronized (FilterGRPCGrpc.class) {
        if ((getInvertFilterMethod = FilterGRPCGrpc.getInvertFilterMethod) == null) {
          FilterGRPCGrpc.getInvertFilterMethod = getInvertFilterMethod =
              io.grpc.MethodDescriptor.<benchimage.Image.ImageGRPC, benchimage.Image.ImageGRPC>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "InvertFilter"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  benchimage.Image.ImageGRPC.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  benchimage.Image.ImageGRPC.getDefaultInstance()))
              .build();
        }
      }
    }
    return getInvertFilterMethod;
  }

  private static volatile io.grpc.MethodDescriptor<benchimage.Image.ImageGRPC,
      benchimage.Image.ImageGRPC> getCartoonFilterMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CartoonFilter",
      requestType = benchimage.Image.ImageGRPC.class,
      responseType = benchimage.Image.ImageGRPC.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<benchimage.Image.ImageGRPC,
      benchimage.Image.ImageGRPC> getCartoonFilterMethod() {
    io.grpc.MethodDescriptor<benchimage.Image.ImageGRPC, benchimage.Image.ImageGRPC> getCartoonFilterMethod;
    if ((getCartoonFilterMethod = FilterGRPCGrpc.getCartoonFilterMethod) == null) {
      synchronized (FilterGRPCGrpc.class) {
        if ((getCartoonFilterMethod = FilterGRPCGrpc.getCartoonFilterMethod) == null) {
          FilterGRPCGrpc.getCartoonFilterMethod = getCartoonFilterMethod =
              io.grpc.MethodDescriptor.<benchimage.Image.ImageGRPC, benchimage.Image.ImageGRPC>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CartoonFilter"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  benchimage.Image.ImageGRPC.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  benchimage.Image.ImageGRPC.getDefaultInstance()))
              .build();
        }
      }
    }
    return getCartoonFilterMethod;
  }

  private static volatile io.grpc.MethodDescriptor<benchimage.Image.ImageGRPC,
      benchimage.Image.ImageGRPC> getPencilFilterMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PencilFilter",
      requestType = benchimage.Image.ImageGRPC.class,
      responseType = benchimage.Image.ImageGRPC.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<benchimage.Image.ImageGRPC,
      benchimage.Image.ImageGRPC> getPencilFilterMethod() {
    io.grpc.MethodDescriptor<benchimage.Image.ImageGRPC, benchimage.Image.ImageGRPC> getPencilFilterMethod;
    if ((getPencilFilterMethod = FilterGRPCGrpc.getPencilFilterMethod) == null) {
      synchronized (FilterGRPCGrpc.class) {
        if ((getPencilFilterMethod = FilterGRPCGrpc.getPencilFilterMethod) == null) {
          FilterGRPCGrpc.getPencilFilterMethod = getPencilFilterMethod =
              io.grpc.MethodDescriptor.<benchimage.Image.ImageGRPC, benchimage.Image.ImageGRPC>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PencilFilter"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  benchimage.Image.ImageGRPC.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  benchimage.Image.ImageGRPC.getDefaultInstance()))
              .build();
        }
      }
    }
    return getPencilFilterMethod;
  }

  private static volatile io.grpc.MethodDescriptor<benchimage.Image.ImageGRPC,
      benchimage.Image.ImageGRPC> getSobelFilterMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SobelFilter",
      requestType = benchimage.Image.ImageGRPC.class,
      responseType = benchimage.Image.ImageGRPC.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<benchimage.Image.ImageGRPC,
      benchimage.Image.ImageGRPC> getSobelFilterMethod() {
    io.grpc.MethodDescriptor<benchimage.Image.ImageGRPC, benchimage.Image.ImageGRPC> getSobelFilterMethod;
    if ((getSobelFilterMethod = FilterGRPCGrpc.getSobelFilterMethod) == null) {
      synchronized (FilterGRPCGrpc.class) {
        if ((getSobelFilterMethod = FilterGRPCGrpc.getSobelFilterMethod) == null) {
          FilterGRPCGrpc.getSobelFilterMethod = getSobelFilterMethod =
              io.grpc.MethodDescriptor.<benchimage.Image.ImageGRPC, benchimage.Image.ImageGRPC>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SobelFilter"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  benchimage.Image.ImageGRPC.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  benchimage.Image.ImageGRPC.getDefaultInstance()))
              .build();
        }
      }
    }
    return getSobelFilterMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FilterGRPCStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FilterGRPCStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FilterGRPCStub>() {
        @java.lang.Override
        public FilterGRPCStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FilterGRPCStub(channel, callOptions);
        }
      };
    return FilterGRPCStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FilterGRPCBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FilterGRPCBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FilterGRPCBlockingStub>() {
        @java.lang.Override
        public FilterGRPCBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FilterGRPCBlockingStub(channel, callOptions);
        }
      };
    return FilterGRPCBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FilterGRPCFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FilterGRPCFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FilterGRPCFutureStub>() {
        @java.lang.Override
        public FilterGRPCFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FilterGRPCFutureStub(channel, callOptions);
        }
      };
    return FilterGRPCFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class FilterGRPCImplBase implements io.grpc.BindableService {

    /**
     */
    public void grayscaleFilter(benchimage.Image.ImageGRPC request,
        io.grpc.stub.StreamObserver<benchimage.Image.ImageGRPC> responseObserver) {
      asyncUnimplementedUnaryCall(getGrayscaleFilterMethod(), responseObserver);
    }

    /**
     */
    public void invertFilter(benchimage.Image.ImageGRPC request,
        io.grpc.stub.StreamObserver<benchimage.Image.ImageGRPC> responseObserver) {
      asyncUnimplementedUnaryCall(getInvertFilterMethod(), responseObserver);
    }

    /**
     */
    public void cartoonFilter(benchimage.Image.ImageGRPC request,
        io.grpc.stub.StreamObserver<benchimage.Image.ImageGRPC> responseObserver) {
      asyncUnimplementedUnaryCall(getCartoonFilterMethod(), responseObserver);
    }

    /**
     */
    public void pencilFilter(benchimage.Image.ImageGRPC request,
        io.grpc.stub.StreamObserver<benchimage.Image.ImageGRPC> responseObserver) {
      asyncUnimplementedUnaryCall(getPencilFilterMethod(), responseObserver);
    }

    /**
     */
    public void sobelFilter(benchimage.Image.ImageGRPC request,
        io.grpc.stub.StreamObserver<benchimage.Image.ImageGRPC> responseObserver) {
      asyncUnimplementedUnaryCall(getSobelFilterMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGrayscaleFilterMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                benchimage.Image.ImageGRPC,
                benchimage.Image.ImageGRPC>(
                  this, METHODID_GRAYSCALE_FILTER)))
          .addMethod(
            getInvertFilterMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                benchimage.Image.ImageGRPC,
                benchimage.Image.ImageGRPC>(
                  this, METHODID_INVERT_FILTER)))
          .addMethod(
            getCartoonFilterMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                benchimage.Image.ImageGRPC,
                benchimage.Image.ImageGRPC>(
                  this, METHODID_CARTOON_FILTER)))
          .addMethod(
            getPencilFilterMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                benchimage.Image.ImageGRPC,
                benchimage.Image.ImageGRPC>(
                  this, METHODID_PENCIL_FILTER)))
          .addMethod(
            getSobelFilterMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                benchimage.Image.ImageGRPC,
                benchimage.Image.ImageGRPC>(
                  this, METHODID_SOBEL_FILTER)))
          .build();
    }
  }

  /**
   */
  public static final class FilterGRPCStub extends io.grpc.stub.AbstractAsyncStub<FilterGRPCStub> {
    private FilterGRPCStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FilterGRPCStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FilterGRPCStub(channel, callOptions);
    }

    /**
     */
    public void grayscaleFilter(benchimage.Image.ImageGRPC request,
        io.grpc.stub.StreamObserver<benchimage.Image.ImageGRPC> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGrayscaleFilterMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void invertFilter(benchimage.Image.ImageGRPC request,
        io.grpc.stub.StreamObserver<benchimage.Image.ImageGRPC> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getInvertFilterMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void cartoonFilter(benchimage.Image.ImageGRPC request,
        io.grpc.stub.StreamObserver<benchimage.Image.ImageGRPC> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCartoonFilterMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void pencilFilter(benchimage.Image.ImageGRPC request,
        io.grpc.stub.StreamObserver<benchimage.Image.ImageGRPC> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPencilFilterMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void sobelFilter(benchimage.Image.ImageGRPC request,
        io.grpc.stub.StreamObserver<benchimage.Image.ImageGRPC> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSobelFilterMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class FilterGRPCBlockingStub extends io.grpc.stub.AbstractBlockingStub<FilterGRPCBlockingStub> {
    private FilterGRPCBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FilterGRPCBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FilterGRPCBlockingStub(channel, callOptions);
    }

    /**
     */
    public benchimage.Image.ImageGRPC grayscaleFilter(benchimage.Image.ImageGRPC request) {
      return blockingUnaryCall(
          getChannel(), getGrayscaleFilterMethod(), getCallOptions(), request);
    }

    /**
     */
    public benchimage.Image.ImageGRPC invertFilter(benchimage.Image.ImageGRPC request) {
      return blockingUnaryCall(
          getChannel(), getInvertFilterMethod(), getCallOptions(), request);
    }

    /**
     */
    public benchimage.Image.ImageGRPC cartoonFilter(benchimage.Image.ImageGRPC request) {
      return blockingUnaryCall(
          getChannel(), getCartoonFilterMethod(), getCallOptions(), request);
    }

    /**
     */
    public benchimage.Image.ImageGRPC pencilFilter(benchimage.Image.ImageGRPC request) {
      return blockingUnaryCall(
          getChannel(), getPencilFilterMethod(), getCallOptions(), request);
    }

    /**
     */
    public benchimage.Image.ImageGRPC sobelFilter(benchimage.Image.ImageGRPC request) {
      return blockingUnaryCall(
          getChannel(), getSobelFilterMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class FilterGRPCFutureStub extends io.grpc.stub.AbstractFutureStub<FilterGRPCFutureStub> {
    private FilterGRPCFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FilterGRPCFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FilterGRPCFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<benchimage.Image.ImageGRPC> grayscaleFilter(
        benchimage.Image.ImageGRPC request) {
      return futureUnaryCall(
          getChannel().newCall(getGrayscaleFilterMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<benchimage.Image.ImageGRPC> invertFilter(
        benchimage.Image.ImageGRPC request) {
      return futureUnaryCall(
          getChannel().newCall(getInvertFilterMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<benchimage.Image.ImageGRPC> cartoonFilter(
        benchimage.Image.ImageGRPC request) {
      return futureUnaryCall(
          getChannel().newCall(getCartoonFilterMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<benchimage.Image.ImageGRPC> pencilFilter(
        benchimage.Image.ImageGRPC request) {
      return futureUnaryCall(
          getChannel().newCall(getPencilFilterMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<benchimage.Image.ImageGRPC> sobelFilter(
        benchimage.Image.ImageGRPC request) {
      return futureUnaryCall(
          getChannel().newCall(getSobelFilterMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GRAYSCALE_FILTER = 0;
  private static final int METHODID_INVERT_FILTER = 1;
  private static final int METHODID_CARTOON_FILTER = 2;
  private static final int METHODID_PENCIL_FILTER = 3;
  private static final int METHODID_SOBEL_FILTER = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final FilterGRPCImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(FilterGRPCImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GRAYSCALE_FILTER:
          serviceImpl.grayscaleFilter((benchimage.Image.ImageGRPC) request,
              (io.grpc.stub.StreamObserver<benchimage.Image.ImageGRPC>) responseObserver);
          break;
        case METHODID_INVERT_FILTER:
          serviceImpl.invertFilter((benchimage.Image.ImageGRPC) request,
              (io.grpc.stub.StreamObserver<benchimage.Image.ImageGRPC>) responseObserver);
          break;
        case METHODID_CARTOON_FILTER:
          serviceImpl.cartoonFilter((benchimage.Image.ImageGRPC) request,
              (io.grpc.stub.StreamObserver<benchimage.Image.ImageGRPC>) responseObserver);
          break;
        case METHODID_PENCIL_FILTER:
          serviceImpl.pencilFilter((benchimage.Image.ImageGRPC) request,
              (io.grpc.stub.StreamObserver<benchimage.Image.ImageGRPC>) responseObserver);
          break;
        case METHODID_SOBEL_FILTER:
          serviceImpl.sobelFilter((benchimage.Image.ImageGRPC) request,
              (io.grpc.stub.StreamObserver<benchimage.Image.ImageGRPC>) responseObserver);
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

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (FilterGRPCGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .addMethod(getGrayscaleFilterMethod())
              .addMethod(getInvertFilterMethod())
              .addMethod(getCartoonFilterMethod())
              .addMethod(getPencilFilterMethod())
              .addMethod(getSobelFilterMethod())
              .build();
        }
      }
    }
    return result;
  }
}
