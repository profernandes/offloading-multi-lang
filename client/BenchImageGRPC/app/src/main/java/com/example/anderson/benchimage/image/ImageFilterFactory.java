package com.example.anderson.benchimage.image;

public class ImageFilterFactory {

        public static FilterStrategy getLocalMethod(String method) {
            if (method.equals("Normal")) {
                return new GrayScaleFilter();
            } else {
                return null;
            }
        }

        public static RemoteFilterStrategy getRemoteMethod(String method, String host, int port) {
            RemoteFilterStrategy strategy = null;

            if (method.equals("Cloudlet")) {
                strategy = new GrayScaleRemoteGRPCProtoBuffer(host, port);
            } // else if (method.equals("ANOTHER REMOTE PROCESSING TYOE"))

            return strategy;
        }
}
