from concurrent import futures
import time
import math
import logging
import numpy as np

import grpc

import matrix_pb2
import matrix_pb2_grpc  

class OperationsServicer(matrix_pb2_grpc.OperationsServicer):
    def __init__(self):
        pass

    def Add(self, request, context):
        init_proc = time.time()

        mat1 = np.array(request.mat1_data).reshape((request.rows,request.cols))
        mat2 = np.array(request.mat2_data).reshape((request.rows,request.cols))

        init_op = time.time()
        mat3 = mat1 + mat2
        end_op = time.time()

        to_return = []
        for row in mat3.tolist():
            to_return = to_return + row

        response = matrix_pb2.OutMatrix(rows = request.rows, cols = request.cols, data = to_return)

        end_proc = time.time()

        print("Python,Sum,%d,%d" % (((end_op - init_op) * 1000), ((end_proc - init_proc) * 1000)))

        return response

    def Multiply(self, request, context):
        init_proc = time.time()

        mat1 = np.array(request.mat1_data).reshape((request.rows,request.cols))
        mat2 = np.array(request.mat2_data).reshape((request.rows,request.cols))

        init_op = time.time()
        mat3 = mat1.dot(mat2)
        end_op = time.time()

        to_return = []
        for row in mat3.tolist():
            to_return = to_return + row

        response = matrix_pb2.OutMatrix(rows = request.rows, cols = request.cols, data = to_return)

        end_proc = time.time()

        print("Python,Mult,%d,%d" % (((end_op - init_op) * 1000), ((end_proc - init_proc) * 1000)))

        return response

def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10), options=[('grpc.max_send_message_length', 10 * 1024 * 1024), ('grpc.max_receive_message_length', 10 * 1024 * 1024)])
    matrix_pb2_grpc.add_OperationsServicer_to_server(OperationsServicer(), server)
    server.add_insecure_port('[::]:50051')
    print("Server started, listening on 50051")
    print("Language,Operation,OpTime,TotalTime")
    server.start()
    server.wait_for_termination()


if __name__ == '__main__':
    logging.basicConfig()
    serve()
