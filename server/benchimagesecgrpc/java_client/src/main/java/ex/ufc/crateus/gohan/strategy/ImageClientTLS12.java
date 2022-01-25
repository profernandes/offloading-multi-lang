package benchimage;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.net.ssl.HostnameVerifier;

import com.squareup.okhttp.ConnectionSpec;
import io.grpc.okhttp.OkHttpChannelBuilder;

import io.grpc.ManagedChannel;

import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;

import com.google.protobuf.ByteString;

public class ImageClientTLS12 extends ImageClient {

    String serverCert = "../tls_files/server.crt";

    public ImageClientTLS12(int serverPort) throws SSLException, GeneralSecurityException, IOException {
        SSLContext sslContext = ImageClientUtils.createTls12Environment(serverCert);
        channel = OkHttpChannelBuilder.forAddress("localhost", serverPort)
                                    .useTransportSecurity()
                                    .maxInboundMessageSize(50 * 1024 * 1024)
                                    .connectionSpec(ConnectionSpec.MODERN_TLS)
                                    .hostnameVerifier(new HostnameVerifier() {
                                        @Override
                                        public boolean verify(String s, SSLSession sslSession) {
                                            return true;
                                        }
                                    })
                                    .sslSocketFactory(sslContext.getSocketFactory())
                                    .build();
    }
}
