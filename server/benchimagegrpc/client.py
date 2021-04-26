import sys
sys.path.append('python_server/')

import logging

import cv2
import numpy as np

import grpc
import image_pb2
import image_pb2_grpc

class ImageClient(object):
    def __init__(self):
        pass

    def __create_request(self, img_bytes):
        return image_pb2.ImageGRPC(content = [img_bytes])

    def run(self):
        channel = grpc.insecure_channel('127.0.0.1:50051')
        stub = image_pb2_grpc.FilterGRPCStub(channel)

        color = open("img5.jpg", "r")
        req = self.__create_request(color.read())

        resp = stub.GrayscaleFilter(req)

        gray = cv2.imdecode(np.frombuffer(resp.content[0], dtype='uint8'), cv2.IMREAD_UNCHANGED)
        cv2.imwrite('gray.jpg', gray)

def RunClient():
    client = ImageClient()
    client.run()

if __name__ == '__main__':
    logging.basicConfig()
    RunClient()
