package ufc.crateus.gohan.strategy;


import java.io.IOException;
import io.grpc.netty.NettyServerBuilder;
import ufc.crateus.gohan.ImageServerUtils;

public class ImageServerTLS12 extends ImageServer {

	String certPath = "../tls_files/server.crt";
	String keyPath = "../tls_files/server.key";

	public ImageServerTLS12(int serverPort) throws IOException {
		port = serverPort;
		server = NettyServerBuilder.forPort(port)
                        .addService(new ImageServiceImpl("TLSv1.2"))
                        .sslContext(ImageServerUtils.getTslContextBuilder(certPath, keyPath).build())
						.maxInboundMessageSize(50 * 1024 * 1024)
                        .build().start();
	}
}
