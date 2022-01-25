package com.example.anderson.benchimage.image;

import android.os.Debug;

import com.google.protobuf.ByteString;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.concurrent.TimeUnit;

import benchimage.FilterGRPCGrpc;
import benchimage.Image;
import io.grpc.ManagedChannel;
import io.grpc.okhttp.OkHttpChannelBuilder;

public class GrayScaleRemoteGRPCProto extends RemoteFilterStrategy {
    private ManagedChannel channel;
    private FilterGRPCGrpc.FilterGRPCBlockingStub stub;

    public GrayScaleRemoteGRPCProto(String ipRemote, int port)
                                                    throws GeneralSecurityException, IOException {
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

        Debug.startMethodTracing("sample");

        Image.ImageGRPC in_img = Image.ImageGRPC.newBuilder().addContent(ByteString.copyFrom(source)).build();
        Image.ImageGRPC out_img = stub.grayscaleFilter(in_img);

        try {
            channel.shutdown().awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        byte[] resp = out_img.getContent(0).toByteArray();

        Debug.stopMethodTracing();

        offloadingTime = (System.nanoTime() - initialTime) / 1000000;

        return resp;
    }
}
