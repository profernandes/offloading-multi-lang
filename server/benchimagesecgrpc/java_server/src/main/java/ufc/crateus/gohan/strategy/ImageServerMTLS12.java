package ufc.crateus.gohan.strategy;

import java.io.IOException;
import io.grpc.netty.NettyServerBuilder;
import ufc.crateus.gohan.ImageServerUtils;

public class ImageServerMTLS12 extends ImageServer {

	String serverCert = "../mtls_files/server.crt";
	String serverKey = "../mtls_files/server.key";
	String clientCert = "../mtls_files/client.crt";

	public ImageServerMTLS12(int serverPort) throws IOException {
		port = serverPort;
		server = NettyServerBuilder.forPort(port)
						.addService(new ImageServiceImpl("MTLSv1.2"))
						.sslContext(ImageServerUtils.getMTslContextBuilder(serverCert, serverKey, clientCert).build())
						.maxInboundMessageSize(50 * 1024 * 1024)
						.build().start();
	}
}
