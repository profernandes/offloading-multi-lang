package com.example.anderson.benchimage.image;

import com.google.protobuf.ByteString;

import benchimage.FilterGRPCGrpc;
import benchimage.Image;
import io.grpc.ManagedChannel;
import io.grpc.okhttp.OkHttpChannelBuilder;

public class GrayScaleRemoteGRPCProtoBuffer extends RemoteFilterStrategy {
    private ManagedChannel channel;
    private FilterGRPCGrpc.FilterGRPCBlockingStub stub;

    public GrayScaleRemoteGRPCProtoBuffer(String ipRemote, int port) {
        super();

        channel = OkHttpChannelBuilder.forAddress(ipRemote, port)
                                        .usePlaintext()
                                        .maxInboundMessageSize(30 * 1024 * 1024)
                                        .build();

        stub = FilterGRPCGrpc.newBlockingStub(channel);
    }

    @Override
    public byte[] applyFilter(byte[] source) {
        long initialTime = System.nanoTime();

        Image.ImageGRPC in_img = Image.ImageGRPC.newBuilder().addContent(ByteString.copyFrom(source)).build();
        Image.ImageGRPC out_img = stub.grayscaleFilter(in_img);

        byte[] resp = out_img.getContent(0).toByteArray();

        offloadingTime = (System.nanoTime() - initialTime) / 1000000;

        return resp;
    }

    @Override
    public void closeChannel() { channel.shutdownNow(); }
}
