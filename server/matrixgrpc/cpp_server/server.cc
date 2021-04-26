#include <iostream>
#include <memory>
#include <string>

#include <grpcpp/grpcpp.h>

#include <Eigen/Dense>

#ifdef BAZEL_BUILD
#include "examples/protos/sorter.grpc.pb.h"
#else
#include "matrix.grpc.pb.h"
#endif

using grpc::Server;
using grpc::ServerBuilder;
using grpc::ServerContext;
using grpc::Status;
using gprotomatrix::Operations; 
using gprotomatrix::InMatrices;
using gprotomatrix::OutMatrix;

// Logic and data behind the server's behavior.
class OperationsServiceImpl final : public Operations::Service {
  Status Add(ServerContext *context, const InMatrices *request, OutMatrix *response) {
	auto init_proc = std::chrono::high_resolution_clock::now();

        Eigen::MatrixXd mat1(request->rows(),request->cols()), mat2(request->rows(),request->cols()), res(request->rows(),request->cols());

        // Cast 1D Array to NxN Matrix (mat1)
        int a = 0, b = 0;
        for (int i = 0; i < request->mat1_data_size(); i++) {
                mat1(a,b) = request->mat1_data(i); b++;
                if (b == request->cols()) { a++; b = 0; }
        }

        // Cast 1D Array to NxN Matrix (mat2)
        a = 0, b = 0;
        for (int i = 0; i < request->mat2_data_size(); i++) {
                mat2(a,b) = request->mat2_data(i); b++;
                if (b == request->cols()) { a++; b = 0; }
        }

        auto init_op = std::chrono::high_resolution_clock::now();
	res = mat1 + mat2;
        auto end_op = std::chrono::high_resolution_clock::now();

        for (int i = 0; i < request->rows(); i++) {
                for (int j = 0; j < request->cols(); j++) {
                        response->add_data(res(i,j));
                }
        }
	response->set_rows(request->rows());
        response->set_cols(request->cols());

	auto end_proc = std::chrono::high_resolution_clock::now();

        std::cout << "C++,Sum," << std::chrono::duration_cast<std::chrono::milliseconds>(end_op - init_op).count() << "," << std::chrono::duration_cast<std::chrono::milliseconds>(end_proc - init_proc).count() << std::endl;

        return Status::OK;
  }

  Status Multiply(ServerContext *context, const InMatrices *request, OutMatrix *response) {
	auto init_proc = std::chrono::high_resolution_clock::now();

	Eigen::MatrixXd mat1(request->rows(),request->cols()), mat2(request->rows(),request->cols()), res(request->rows(),request->cols());

	// Cast 1D Array to NxN Matrix (mat1)
	int a = 0, b = 0;
	for (int i = 0; i < request->mat1_data_size(); i++) {
		mat1(a,b) = request->mat1_data(i); b++;
		if (b == request->cols()) { a++; b = 0; }
	}

	// Cast 1D Array to NxN Matrix (mat2)
	a = 0, b = 0;
	for (int i = 0; i < request->mat2_data_size(); i++) {
		mat2(a,b) = request->mat2_data(i); b++;
                if (b == request->cols()) { a++; b = 0; }
        }

	auto init_op = std::chrono::high_resolution_clock::now();
	res = mat1 * mat2;
	auto end_op = std::chrono::high_resolution_clock::now();

    	for (int i = 0; i < request->rows(); i++) {
        	for (int j = 0; j < request->cols(); j++) {
			response->add_data(res(i,j));
		}
    	}
	response->set_rows(request->rows());
	response->set_cols(request->cols());

	auto end_proc = std::chrono::high_resolution_clock::now();

        std::cout << "C++,Mult," << std::chrono::duration_cast<std::chrono::milliseconds>(end_op - init_op).count() << "," << std::chrono::duration_cast<std::chrono::milliseconds>(end_proc - init_proc).count() << std::endl;

	return Status::OK;
  }
};

void RunServer() {
  std::string server_address("0.0.0.0:50051");
  OperationsServiceImpl service;

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
