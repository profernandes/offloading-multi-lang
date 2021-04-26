#include <iostream>
#include <cstddef>
#include <memory>
//#include <string>

#include <grpcpp/grpcpp.h>
#include "opencv2/opencv.hpp"
#include "opencv2/imgproc/types_c.h"

#ifdef BAZEL_BUILD
#include "examples/protos/image.grpc.pb.h"
#else
#include "image.grpc.pb.h"
#endif

using grpc::Server;
using grpc::ServerBuilder;
using grpc::ServerContext;
using grpc::Status;

using benchimage::ImageGRPC;
using benchimage::FilterGRPC;

using namespace cv;

class ImageServiceImpl final : public FilterGRPC::Service {

	Status GrayscaleFilter(ServerContext *context, const ImageGRPC *request, ImageGRPC *response) {
		auto init_proc = std::chrono::high_resolution_clock::now();

		/* Receive and decode input */
		std::vector<uint8_t> img_data(request->content(0).cbegin(), request->content(0).cend());
		Mat image(imdecode(Mat(img_data, true), 1)), dest;

		/* Apply filter */
		auto init_op = std::chrono::high_resolution_clock::now();
		cvtColor(image, dest, CV_RGB2GRAY);
		auto end_op = std::chrono::high_resolution_clock::now();

		/* Decode and create output */
		std::vector<uint8_t> dest_img;
		imencode(".jpg", dest, dest_img);
		response->add_content(std::string(dest_img.begin(), dest_img.end()));

		auto end_proc = std::chrono::high_resolution_clock::now();

		std::cout << "C++,GrayFilter," << std::chrono::duration_cast<std::chrono::milliseconds>(end_op - init_op).count() << "," << std::chrono::duration_cast<std::chrono::milliseconds>(end_proc - init_proc).count() << std::endl;

		return Status::OK;
	}

	Status InvertFilter(ServerContext *context, const ImageGRPC *request, ImageGRPC *response) {
                return Status::OK;
        }
};

void RunServer() {
  std::string server_address("0.0.0.0:50051");
  ImageServiceImpl service;

  ServerBuilder builder;
  builder.AddListeningPort(server_address, grpc::InsecureServerCredentials());
  builder.SetMaxReceiveMessageSize(10 * 1024 * 1024);
  builder.SetMaxSendMessageSize(10 * 1024 * 1024);
  builder.RegisterService(&service);
  std::unique_ptr<Server> server(builder.BuildAndStart());

  std::cout << "Server started, listening on 50051" << std::endl;
  std::cout << "Language,Operation,OpTime,TotalTime" << std::endl;
  server->Wait();
}

int main(int argc, char** argv) {
  RunServer();

  return 0;
}
