package main

import (
	"context"
	"log"
	"net"

	"image"
        "image/jpeg"
        _ "image/jpeg"
        "os"

	"gocv.io/x/gocv"
	"google.golang.org/grpc"

	"fmt"
	"time"

	pb "image.pb"
)

const (
	port = ":50051"
)

type server struct {
	pb.FilterGRPCServer
}

func saveImage(filename string, img_src image.Image) {
        f, err := os.Create(filename)
        if err != nil {
                log.Fatalf("os.Create failed: %v", err)
        }
        defer f.Close()
        err = jpeg.Encode(f, img_src, nil)
        if err != nil {
                log.Fatalf("png.Encode failed: %v", err)
        }
}

func (s *server) GrayscaleFilter(ctx context.Context, in *pb.ImageGRPC) (*pb.ImageGRPC, error) {
	init_proc := time.Now().UnixNano() / int64(time.Millisecond)

	// Decode bytes sequence received
	src, _ := gocv.IMDecode(in.GetContent()[0], gocv.IMReadUnchanged)
	dst := gocv.NewMat()

	// Apply Filter
	init_op := time.Now().UnixNano() / int64(time.Millisecond)
	gocv.CvtColor(src, &dst, gocv.ColorRGBToGray)
	end_op := time.Now().UnixNano() / int64(time.Millisecond)

	// Encode bytes sequence result
	dst_en, _ := gocv.IMEncode(".jpg", dst)
	out_img := pb.ImageGRPC{Content: [][]byte{dst_en}}

	end_proc := time.Now().UnixNano() / int64(time.Millisecond)

	fmt.Printf("Go,GrayScale,%d,%d\n", end_op - init_op, end_proc - init_proc)

	return &out_img, nil
}

func (s *server) InvertFilter(ctx context.Context, in *pb.ImageGRPC) (*pb.ImageGRPC, error) {
        return in, nil
}

func main() {
	lis, err := net.Listen("tcp", port)
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}

	s := grpc.NewServer(grpc.MaxRecvMsgSize(10 * 1024 * 1024), grpc.MaxSendMsgSize(10 * 1024 * 1024))
	pb.RegisterFilterGRPCServer(s, &server{})
	log.Printf("Server started, listening on 50051")
	fmt.Println("Language,Operation,OpTime,TotalTime")
	if err := s.Serve(lis); err != nil {
		log.Fatalf("failed to serve: %v", err)
	}
}
