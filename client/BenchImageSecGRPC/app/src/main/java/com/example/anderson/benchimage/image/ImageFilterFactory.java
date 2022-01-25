package image;

import com.example.anderson.benchimage.image.FilterStrategy;
import com.example.anderson.benchimage.image.GrayScaleFilter;
import com.example.anderson.benchimage.image.GrayScaleRemoteGRPCProto;
import com.example.anderson.benchimage.image.GrayScaleRemoteGRPCProtoMTls12;
import com.example.anderson.benchimage.image.GrayScaleRemoteGRPCProtoTls12;
import com.example.anderson.benchimage.image.RemoteFilterStrategy;

import java.io.IOException;
import java.security.GeneralSecurityException;

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

            try {
                if (method.equals("GRPCProtoTLSv1.2")) {
                    strategy = new GrayScaleRemoteGRPCProtoTls12(host, port);
                } else if (method.equals("GRPCProtoMTLSv1.2")) {
                    strategy = new GrayScaleRemoteGRPCProtoMTls12(host, port);
                } else {
                    strategy = new GrayScaleRemoteGRPCProto(host, port);
                }
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return strategy;
        }

}
