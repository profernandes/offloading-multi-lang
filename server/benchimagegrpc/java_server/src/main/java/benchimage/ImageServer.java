package benchimagegrpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.core.Mat;
import org.opencv.core.Core;
import org.opencv.core.MatOfByte;
import org.opencv.imgproc.Imgproc;

import com.google.protobuf.ByteString; 

public class ImageServer {
	private final int port = 50051;
	private Server server;

	private void start() throws IOException {
		server = ServerBuilder.forPort(port)
			.maxInboundMessageSize(10 * 1024 * 1024)
			.addService(new ImageServiceImpl()).build().start();

		System.out.println("Server started, listening on " + port);
		System.out.println("Language,Operation,OpTime,TotalTime");
    		Runtime.getRuntime().addShutdownHook(new Thread() {
      			@Override
      			public void run() {
        			System.out.println("shutting down gRPC server since JVM is shutting down");
        			try {
          				ImageServer.this.stop();
        			} catch (InterruptedException e) {
          				e.printStackTrace(System.err);
        			}
        			System.out.println("server shut down");
      			}
    		});
	}

	private void stop() throws InterruptedException {
    		if (server != null) {
      			server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
    		}
  	}

        private void blockUntilShutdown() throws InterruptedException { 
		if (server != null) {
      			server.awaitTermination();
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		final ImageServer server = new ImageServer();
		server.start();
		server.blockUntilShutdown();
	}

	static class ImageServiceImpl extends FilterGRPCGrpc.FilterGRPCImplBase {
		@Override
		public void grayscaleFilter(ImageGRPC request, StreamObserver<ImageGRPC> responseObserver) {
			long init_proc = System.nanoTime();

			nu.pattern.OpenCV.loadLocally();

			/* Receive and decode input */
			byte[] img = request.getContent(0).toByteArray();
			Mat src = Imgcodecs.imdecode(new MatOfByte(img), Imgcodecs.IMREAD_UNCHANGED), dest = new Mat();

			/* Apply filter */
			long init_op = System.nanoTime();
			Imgproc.cvtColor(src, dest, Imgproc.COLOR_RGB2GRAY, src.channels());
			long end_op = System.nanoTime();

			/* Decode and create output */
			MatOfByte dest_img = new MatOfByte();
			Imgcodecs.imencode(".jpg", dest, dest_img);
			ImageGRPC reply = ImageGRPC.newBuilder().addContent(ByteString.copyFrom(dest_img.toArray())).build();
			responseObserver.onNext(reply);

			long end_proc = System.nanoTime();

                        System.out.println("Java,GrayScale," + ((end_op - init_op) / 1000000) + "," + ((end_proc - init_proc) / 1000000));

			responseObserver.onCompleted();
		}

		@Override
                public void invertFilter(ImageGRPC request, StreamObserver<ImageGRPC> responseObserver) {
                        System.out.println("To implement");
			responseObserver.onCompleted();
                }
	}
}
