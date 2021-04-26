from concurrent import futures
import time
import math
import logging
import cv2
import numpy as np

import grpc

import image_pb2
import image_pb2_grpc  

class FilterGRPCServicer(image_pb2_grpc.FilterGRPCServicer):
    def __init__(self):
        pass

    def GrayscaleFilter(self, request, context):    
        init_proc = time.time()

        # Receive and decode input
        src = cv2.imdecode(np.frombuffer(request.content[0], dtype='uint8'), cv2.IMREAD_UNCHANGED)
 
        # Apply filter
        init_op = time.time()
        dest = cv2.cvtColor(src, cv2.COLOR_RGB2GRAY)
        end_op = time.time()

        # Decode and create output
        dest_img = cv2.imencode(".jpg", dest)

        end_proc = time.time()

        print("Python,GrayScale,%d,%d" % (((end_op - init_op) * 1000), ((end_proc - init_proc) * 1000)))

        return image_pb2.ImageGRPC(content = [dest_img[1].tobytes()])
        
    def InvertFilter(self, request, context):
        pass

def RunServer():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10), options=[('grpc.max_send_message_length', 10 * 1024 * 1024), ('grpc.max_receive_message_length', 10 * 1024 * 1024)])
    image_pb2_grpc.add_FilterGRPCServicer_to_server(FilterGRPCServicer(), server)
    server.add_insecure_port('[::]:50051')
    print("Server started, listening on 50051")
    print("Language,Operation,OpTime,TotalTime")
    server.start()
    server.wait_for_termination()


if __name__ == '__main__':
    logging.basicConfig()
    RunServer()
