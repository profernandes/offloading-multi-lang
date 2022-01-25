package ufc.crateus.gohan.strategy;

import java.io.IOException;
import io.grpc.ServerBuilder;

public class ImageServerInsec extends ImageServer {

        public ImageServerInsec(int serverPort) throws IOException {
                port = serverPort;
		server = ServerBuilder.forPort(port)
                        .maxInboundMessageSize(50 * 1024 * 1024)
                        .addService(new ImageServiceImpl("Insec")).build().start();
        }
}
