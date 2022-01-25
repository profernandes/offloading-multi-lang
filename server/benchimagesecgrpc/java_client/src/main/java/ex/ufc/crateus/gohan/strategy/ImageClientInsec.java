package benchimage;

import io.grpc.okhttp.OkHttpChannelBuilder;

public class ImageClientInsec extends ImageClient {

    public ImageClientInsec(int serverPort) {
        channel = OkHttpChannelBuilder.forAddress("localhost", serverPort)
                                        .usePlaintext()
                                        .maxInboundMessageSize(50 * 1024 * 1024)
                                        .build();
    }
    
}
