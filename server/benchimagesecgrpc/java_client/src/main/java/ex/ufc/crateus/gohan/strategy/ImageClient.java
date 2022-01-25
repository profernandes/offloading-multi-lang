package benchimage;

import io.grpc.ManagedChannel;

import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;

import com.google.protobuf.ByteString;

public class ImageClient {
    protected ManagedChannel channel;

    public void computeTask() {
        FilterGRPCGrpc.FilterGRPCBlockingStub stub = FilterGRPCGrpc.newBlockingStub(channel);

        nu.pattern.OpenCV.loadLocally();

        Imgcodecs imageCodecs = new Imgcodecs();
        Mat color_img = imageCodecs.imread("../img5.jpg");

        MatOfByte req_img = new MatOfByte();
        Imgcodecs.imencode(".jpg", color_img, req_img);	
        ImageGRPC request = ImageGRPC.newBuilder().addContent(ByteString.copyFrom(req_img.toArray())).build();

        ImageGRPC reply = stub.grayscaleFilter(request);

        byte[] resp_img = reply.getContent(0).toByteArray();
        Mat gray_img = Imgcodecs.imdecode(new MatOfByte(resp_img), Imgcodecs.IMREAD_UNCHANGED);
        ImageClientUtils.saveImage(gray_img);
    }

}
