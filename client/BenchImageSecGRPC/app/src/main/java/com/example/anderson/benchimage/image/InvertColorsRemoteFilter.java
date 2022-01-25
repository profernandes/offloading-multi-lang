package com.example.anderson.benchimage.image;

import com.google.protobuf.ByteString;

import java.util.concurrent.TimeUnit;

import benchimage.FilterGRPCGrpc;
import benchimage.Image;
import io.grpc.ManagedChannel;
import io.grpc.okhttp.OkHttpChannelBuilder;

public class InvertColorsRemoteFilter extends RemoteFilterStrategy {
    private int port;
    private String ipRemote;

    private ManagedChannel channel;

    public InvertColorsRemoteFilter(String ipRemote, int port) {
        super();

        this.port = port;
        this.ipRemote = ipRemote;
    }

    @Override
    public byte[] applyFilter(byte[] source) {

        long initialTime = System.nanoTime();

        channel = OkHttpChannelBuilder.forAddress(ipRemote, port)
                .usePlaintext().maxInboundMessageSize(30 * 1024 * 1024).build();

        Image.ImageGRPC in_img = Image.ImageGRPC.newBuilder().addContent(ByteString.copyFrom(source)).build();
        FilterGRPCGrpc.FilterGRPCBlockingStub stub = FilterGRPCGrpc.newBlockingStub(channel);
        Image.ImageGRPC out_img = stub.invertFilter(in_img);

        try {
            channel.shutdown().awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        offloadingTime = System.nanoTime() - initialTime;

        return out_img.getContent(0).toByteArray();
    }
}
