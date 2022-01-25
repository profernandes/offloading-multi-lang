package main

import (
	"context"
	"log"
	"os"
	"net"
	"fmt"
	"time"

	"gocv.io/x/gocv"
	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials"

	"crypto/tls"
	"crypto/x509"
	"io/ioutil"

	pb "image.pb"
)

func getInsecContextBuilder() ([]grpc.ServerOption) {
	opts := []grpc.ServerOption{
			grpc.MaxRecvMsgSize(50 * 1024 * 1024),
	}

	return opts
}

func getTslContextBuilder(crtFile string, keyFile string) ([]grpc.ServerOption) {
	cert, err := tls.LoadX509KeyPair(crtFile, keyFile)
	if err != nil {
	   log.Fatalf("failed to load key pair: %s", err)
	}

	creds := credentials.NewTLS(&tls.Config {
										Certificates: []tls.Certificate{cert},
									})
	
	opts := []grpc.ServerOption{
		grpc.MaxRecvMsgSize(50 * 1024 * 1024),
		grpc.Creds(creds),
	}

	return opts
}

func getMTslContextBuilder(crtServer string, keyServer string, caClient string) ([]grpc.ServerOption) {
	cert, err := tls.LoadX509KeyPair(crtServer, keyServer)
	if err != nil {
	   log.Fatalf("failed to load key pair: %s", err)
	}

	certPool := x509.NewCertPool()
	ca, err := ioutil.ReadFile(caClient)
	if err != nil {
		log.Fatalf("could not read ca certificate: %s", err)
	}
	if ok := certPool.AppendCertsFromPEM(ca); !ok {
		log.Fatalf("failed to append ca certificate")
	}

	creds := credentials.NewTLS(&tls.Config {
										ClientAuth:   tls.RequireAndVerifyClientCert,
										Certificates: []tls.Certificate{cert},
										ClientCAs:    certPool,
									})
	
	opts := []grpc.ServerOption{
		grpc.MaxRecvMsgSize(50 * 1024 * 1024),
		grpc.Creds(creds),
	}

	return opts
}

var method = ""

const (
	port = ":50055"

	crtFile = "../tls_files/server.crt"
	keyFile = "../tls_files/server.key"

	crtServer = "../mtls_files/server.crt"
	keyServer = "../mtls_files/server.key"
	crtClient = "../mtls_files/client.crt"
)

type server struct {
	pb.FilterGRPCServer
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

	fmt.Printf("%s,Go,Grayscale,%d,%d\n", method, end_op - init_op, end_proc - init_proc)

	return &out_img, nil
}

func (s *server) InvertFilter(ctx context.Context, in *pb.ImageGRPC) (*pb.ImageGRPC, error) {
	fmt.Println("Not implemented!")
	return in, nil
}

func main() {
	lis, err := net.Listen("tcp", port)
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}

	var opts []grpc.ServerOption
	if (os.Args[1] == "tls12") {
		opts = getTslContextBuilder(crtFile, keyFile)
		method = "TLSv1.2"
	} else if (os.Args[1] == "mtls12") {
		opts = getMTslContextBuilder(crtServer, keyServer, crtClient)
		method = "MTLSv1.2"
	} else if (os.Args[1] == "insec") {
		opts = getInsecContextBuilder()
		method = "Insec"
	}

	s := grpc.NewServer(opts...)
	pb.RegisterFilterGRPCServer(s, &server{})
	fmt.Printf("Running server on port %s\n", port[1:])
	fmt.Printf("RPCMethod,Language,Operation,OpTime,TotalTime\n")
	if err := s.Serve(lis); err != nil {
		log.Fatalf("failed to serve: %v", err)
	}
}
