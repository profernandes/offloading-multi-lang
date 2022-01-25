package com.example.anderson.benchimage.image;

import android.os.Debug;

import com.google.protobuf.ByteString;
import com.squareup.okhttp.ConnectionSpec;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import benchimage.FilterGRPCGrpc;
import benchimage.Image;
import io.grpc.ManagedChannel;
import io.grpc.okhttp.OkHttpChannelBuilder;

import static com.example.anderson.benchimage.image.GrayScaleFilter.createMTslContext;

public class GrayScaleRemoteGRPCProtoMTls12 extends RemoteFilterStrategy {
    private ManagedChannel channel;
    private FilterGRPCGrpc.FilterGRPCBlockingStub stub;

    private String serverCert = "/storage/emulated/0/grpcSec/mtls_files/server.crt";
    private String clientKey = "/storage/emulated/0/grpcSec/mtls_files/client.key";
    private String clientCert = "/storage/emulated/0/grpcSec/mtls_files/client.crt";

    public GrayScaleRemoteGRPCProtoMTls12(String ipRemote, int port) throws GeneralSecurityException, IOException {
        super();

        SSLContext sslContext = createMTslContext(serverCert, clientKey, clientCert);
        channel = OkHttpChannelBuilder.forAddress(ipRemote, port)
                                        .useTransportSecurity()
                                        .maxInboundMessageSize(30 * 1024 * 1024)
                                        .connectionSpec(ConnectionSpec.MODERN_TLS)
                                        .hostnameVerifier(new HostnameVerifier() {
                                            @Override
                                            public boolean verify(String s, SSLSession sslSession) {
                                                return (s.compareTo(ipRemote) == 0) ? true : false;
                                            }
                                        })
                                        .sslSocketFactory(sslContext.getSocketFactory())
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
