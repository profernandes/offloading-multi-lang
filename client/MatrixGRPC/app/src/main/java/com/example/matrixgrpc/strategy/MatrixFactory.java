package com.example.matrixgrpc.strategy;

public class MatrixFactory {

        public static MatrixStrategy getLocalMethod(String method) {
            if (method.equals("Normal")) {
                return new MatrixLocal();
            } else {
                return null;
            }
        }

        public static MatrixStrategy getRemoteMethod(String method, String host, int port) {
            MatrixStrategy strategy = null;

            if (method.equals("GRPC/Proto")) {
                strategy = new MatrixRemoteGRPCProtoBuffer(host, port);
            } // else if (method.equals("NEW METHOD"))

            return strategy;
        }

}
