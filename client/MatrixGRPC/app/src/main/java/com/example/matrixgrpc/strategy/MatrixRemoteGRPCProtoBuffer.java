package com.example.matrixgrpc.strategy;

import java.util.ArrayList;
import java.util.List;

import gprotomatrix.OperationsGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;


public class MatrixRemoteGRPCProtoBuffer extends MatrixStrategy {

    private ManagedChannel channel;
    OperationsGrpc.OperationsBlockingStub stub;

    public MatrixRemoteGRPCProtoBuffer(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().maxInboundMessageSize(50 * 1024 * 1024).build();
        stub = OperationsGrpc.newBlockingStub(channel);
    }

    @Override
    public int[][] add(int[][] A, int[][] B) {

        List<Integer> mat1 = castToArrayList(A),  mat2 = castToArrayList(B), matR = new ArrayList<>();

        gprotomatrix.InMatrices in_ops = gprotomatrix.InMatrices.newBuilder().setRows(A.length).setCols(A[0].length)
                .addAllMat1Data(mat1).addAllMat2Data(mat2).build();

        gprotomatrix.OutMatrix resp = stub.add(in_ops);

        for (int i = 0; i < resp.getDataCount(); i++) {
            matR.add(new Integer(resp.getData(i)));
        }

        int[][] response = castToArray(matR, resp.getCols());

        return response;
    }

    @Override
    public int[][] multiply(int[][] A, int[][] B) {

        List<Integer> mat1 = castToArrayList(A),  mat2 = castToArrayList(B), matR = new ArrayList<>();

        gprotomatrix.InMatrices in_ops = gprotomatrix.InMatrices.newBuilder().setRows(A.length).setCols(A[0].length)
                .addAllMat1Data(mat1).addAllMat2Data(mat2).build();

        gprotomatrix.OutMatrix resp = stub.multiply(in_ops);

        for (int i = 0; i < resp.getDataCount(); i++) {
            matR.add(new Integer(resp.getData(i)));
        }

        int[][] response = castToArray(matR, resp.getCols());

        return response;
    }

    @Override
    public void closeChannel() {
	channel.shutdownNow();
    }
}
