package matrix;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

import gprotomatrix.*;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;

public class MatrixServer {
	private final int port = 50051, N = 100;
	private Server server;

	private void start() throws IOException {
		server = ServerBuilder.forPort(port)
			.maxInboundMessageSize(10 * 1024 * 1024)
			.addService(new MatrixServiceImpl()).build().start();
		System.out.println("Server started, listening on " + port);
		System.out.println("Language,Operation,OpTime,TotalTime");
    		Runtime.getRuntime().addShutdownHook(new Thread() {
      			@Override
      			public void run() {
        			System.err.println("Shutting down gRPC server since JVM is shutting down");
        			try {
          				MatrixServer.this.stop();
        			} catch (InterruptedException e) {
          				e.printStackTrace(System.err);
        			}
        			System.err.println("Server shut down");
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
		final MatrixServer server = new MatrixServer();
		server.start();
		server.blockUntilShutdown();
	}

	static class MatrixServiceImpl extends OperationsGrpc.OperationsImplBase {
		@Override
                public void add(InMatrices request, StreamObserver<OutMatrix> responseObserver) {
                        long init_proc = System.nanoTime();

			RealMatrix mat1 = new Array2DRowRealMatrix(request.getRows(), request.getCols());
                        int a = 0, b = 0;
                        for (Integer value : request.getMat1DataList()) {
				mat1.addToEntry(a, b, value.intValue()); b++;
                                if (b == request.getCols()) { a++; b = 0; }
                        }

			RealMatrix mat2 = new Array2DRowRealMatrix(request.getRows(), request.getCols());
                        a = 0; b = 0;
                        for (Integer value : request.getMat2DataList()) {
				mat2.addToEntry(a, b, value.intValue()); b++;
                                if (b == request.getCols()) { a++; b = 0; }
                        }

                        long init_op = System.nanoTime();
			RealMatrix res = mat1.add(mat2);
			long end_op = System.nanoTime();

			ArrayList<Integer> aux = new ArrayList<Integer>();
                        for (int row = 0; row < request.getRows(); row++) {
                                for (int col = 0; col < request.getCols(); col++) {
					aux.add((int)res.getEntry(row, col));
                                }
                        }
                        OutMatrix reply = OutMatrix.newBuilder()
				.addAllData(aux)
				.setCols(request.getCols())
                                .setRows(request.getRows()).build();


                        responseObserver.onNext(reply);
                        long end_proc = System.nanoTime();

                        System.out.println("Java,Sum," + ((end_op - init_op) / 1000000) + "," + ((end_proc - init_proc) / 1000000));
                        responseObserver.onCompleted();

                }

		@Override
		public void multiply(InMatrices request, StreamObserver<OutMatrix> responseObserver) {
			long init_proc = System.nanoTime();

			RealMatrix mat1 = new Array2DRowRealMatrix(request.getRows(), request.getCols());
                        int a = 0, b = 0;
                        for (Integer value : request.getMat1DataList()) {
				mat1.addToEntry(a, b, value.intValue()); b++;
                                if (b == request.getCols()) { a++; b = 0; }
                        }

			RealMatrix mat2 = new Array2DRowRealMatrix(request.getRows(), request.getCols());
                        a = 0; b = 0;
                        for (Integer value : request.getMat2DataList()) {
				mat2.addToEntry(a, b, value.intValue()); b++; 
                                if (b == request.getCols()) { a++; b = 0; }
                        }

                        long init_op = System.nanoTime();
			RealMatrix res = mat1.multiply(mat2);
			long end_op = System.nanoTime();

			ArrayList<Integer> aux = new ArrayList<Integer>();
			for (int row = 0; row < request.getRows(); row++) {
				for (int col = 0; col < request.getCols(); col++) {
					aux.add((int)res.getEntry(row, col));
				}
			}			
			OutMatrix reply = OutMatrix.newBuilder()
				.addAllData(aux)
				.setCols(request.getCols())
				.setRows(request.getRows()).build();


                        responseObserver.onNext(reply);
                        long end_proc = System.nanoTime();

                        System.out.println("Java,Mult," + ((end_op - init_op) / 1000000) + "," + ((end_proc - init_proc) / 1000000));
			responseObserver.onCompleted();

		}		
	}
}
