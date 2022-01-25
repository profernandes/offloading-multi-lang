package ufc.crateus.gohan;

import io.grpc.netty.GrpcSslContexts;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.ClientAuth;

import java.io.File;

public class ImageServerUtils {

	public static SslContextBuilder getTslContextBuilder(String serverCert, String serverKey) {
		SslContextBuilder sslClientContextBuilder = SslContextBuilder.forServer(new File(serverCert), new File(serverKey));
		sslClientContextBuilder = sslClientContextBuilder.protocols("TLSv1.2");
		return GrpcSslContexts.configure(sslClientContextBuilder);
	}

	public static SslContextBuilder getMTslContextBuilder(String serverCert, String serverKey, String clientCert) {
		SslContextBuilder sslClientContextBuilder = SslContextBuilder.forServer(new File(serverCert), new File(serverKey))
															.trustManager(new File(clientCert)).clientAuth(ClientAuth.REQUIRE);
		sslClientContextBuilder = sslClientContextBuilder.protocols("TLSv1.2");
		return GrpcSslContexts.configure(sslClientContextBuilder);
	}
}
