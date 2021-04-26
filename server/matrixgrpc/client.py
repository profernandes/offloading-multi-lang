import sys
sys.path.append('python_server/')

#import logging
import random

import grpc
import matrix_pb2
import matrix_pb2_grpc

class MatrixClient(object):
    def __init__(self):
        pass

    def __create_request(self, matrix1, matrix2, rows, cols):
        return matrix_pb2.InMatrices(mat1_data = matrix1, mat2_data = matrix2, rows = rows, cols = cols)


    def run(self):
        channel = grpc.insecure_channel('127.0.0.1:50051')
        stub = matrix_pb2_grpc.OperationsStub(channel)
 
        req = self.__create_request([1,1,2,2], [1,1,2,2], 2, 2)

        resp = stub.Add(req)
        resp = [int(i) for i in resp.data]
        print(str(resp) + " == " + str([2, 2, 4, 4]) + " ? " + str(resp == [2, 2, 4, 4]))

        resp = stub.Multiply(req)
        resp = [int(i) for i in resp.data]
        print(str(resp) + " == " + str([3, 3, 6, 6]) + " ? " + str(resp == [3, 3, 6, 6]))
        
def RunClient():
    client = MatrixClient()
    client.run()

if __name__ == '__main__':
    #logging.basicConfig()
    RunClient()
