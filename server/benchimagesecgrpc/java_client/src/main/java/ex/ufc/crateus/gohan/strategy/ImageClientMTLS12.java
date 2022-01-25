package benchimage;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.net.ssl.HostnameVerifier;

import com.squareup.okhttp.ConnectionSpec;
import io.grpc.okhttp.OkHttpChannelBuilder;

public class ImageClientMTLS12 extends ImageClient {

    String serverCert = "../mtls_files/server.crt";
    String clientKey = "../mtls_files/client.key";
    String clientCert = "../mtls_files/client.crt";

    public ImageClientMTLS12(int serverPort) throws SSLException, GeneralSecurityException, IOException {
        SSLContext sslContext = ImageClientUtils.createMTls12Context(serverCert, clientKey, clientCert);
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
