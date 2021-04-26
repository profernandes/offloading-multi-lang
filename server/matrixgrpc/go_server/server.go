package main

import (
	"context"
	"log"
	"net"
	"fmt"
	"time"

	"google.golang.org/grpc"
	"gonum.org/v1/gonum/mat"

	pb "matrix.pb"
)

const (
	port = ":50051"
)

type server struct {
	pb.OperationsServer
}

func (s *server) Add(ctx context.Context, in *pb.InMatrices) (*pb.OutMatrix, error) {
	init_proc := time.Now().UnixNano() / int64(time.Millisecond)

	mat1 := make([]float64, (in.GetRows() * in.GetCols()))
	for i, data := range in.GetMat1Data() {
		mat1[i] = float64(data)
	}

	mat2 := make([]float64, (in.GetRows() * in.GetCols()))
	for i, data := range in.GetMat2Data() {
                mat2[i] = float64(data)
        }

	A := mat.NewDense(int(in.GetRows()), int(in.GetCols()), mat1)
	B := mat.NewDense(int(in.GetRows()), int(in.GetCols()), mat2)
	C := mat.NewDense(int(in.GetRows()), int(in.GetCols()), nil)

	init_op := time.Now().UnixNano() / int64(time.Millisecond)
        C.Add(A, B)
	end_op := time.Now().UnixNano() / int64(time.Millisecond)

	result := make([]int32, (in.GetRows() * in.GetCols()))
        for i, data := range C.RawMatrix().Data {
                result[i] = int32(data)
        }

	response := pb.OutMatrix{Rows: in.GetRows(), Cols: in.GetCols(), Data: result}

	end_proc := time.Now().UnixNano() / int64(time.Millisecond)

        fmt.Printf("Go,Sum,%d,%d\n", end_op - init_op, end_proc - init_proc)

	return &response, nil
}

func (s *server) Multiply(ctx context.Context, in *pb.InMatrices) (*pb.OutMatrix, error) {
	init_proc := time.Now().UnixNano() / int64(time.Millisecond)

	mat1 := make([]float64, (in.GetRows() * in.GetCols()))
        for i, data := range in.GetMat1Data() {
                mat1[i] = float64(data)
        }

        mat2 := make([]float64, (in.GetRows() * in.GetCols()))
        for i, data := range in.GetMat2Data() {
                mat2[i] = float64(data)
        }

        A := mat.NewDense(int(in.GetRows()), int(in.GetCols()), mat1)
        B := mat.NewDense(int(in.GetRows()), int(in.GetCols()), mat2)
        C := mat.NewDense(int(in.GetRows()), int(in.GetCols()), nil)

	init_op := time.Now().UnixNano() / int64(time.Millisecond)
	C.Product(A, B)
	end_op := time.Now().UnixNano() / int64(time.Millisecond)

	result := make([]int32, (in.GetRows() * in.GetCols()))
        for i, data := range C.RawMatrix().Data {
                result[i] = int32(data)
        }

	response := pb.OutMatrix{Rows: in.GetRows(), Cols: in.GetCols(), Data: result}

	end_proc := time.Now().UnixNano() / int64(time.Millisecond)

        fmt.Printf("Go,Mult,%d,%d\n", end_op - init_op, end_proc - init_proc)

        return &response, nil
}

func main() {
	lis, err := net.Listen("tcp", port)
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}
	s := grpc.NewServer(grpc.MaxRecvMsgSize(10 * 1024 * 1024), grpc.MaxSendMsgSize(10 * 1024 * 1024)) 
	pb.RegisterOperationsServer(s, &server{})
	fmt.Printf("Server started, listening on 50051\n")
	fmt.Printf("Language,Operation,OpTime,TotalTime\n")
	if err := s.Serve(lis); err != nil {
		log.Fatalf("failed to serve: %v", err)
	}
}
