# Offloading Multi-language Research Repository

## Contextualization:

This repository contains the source codes of the Android client applications and the Go and Java server processes used in the experiments performed in the article **"Secure Computational Offloading with gRPC: a Performance Evaluation in a Mobile Cloud Computing Environment"** published at the *11th ACM International Symposium on Design and Analysis of Intelligent Vehicular Networks and Applications (DIVANet'21).* For more information about the experiments, we suggest reading the [paper](https://dl.acm.org/doi/abs/10.1145/3479243.3487295).

This repository has two main folders in its root directory: *server/* (dedicated to the server process codes) and *client/* (dedicated to the Android Studio projects of the client application). In this paper, we just used one mobile application: BenchImageSecGRPC. So each folder has only one internal folder related to it.

In order to simplify future experiments and interaction with the scenario, we have also developed and made available two auxiliary codes:

1. ***java_client*:** Inside of the folder *server/benchimagesecgrpc/* exists a Maven project that simulates the client smartphone's action. We believe that this project can be helpful for quickly testing server-side solutions and validating that the server is running correctly. Please, read the README.txt file inside the folder *java_client* to learn how to run the project;
2. ***results_server.py*:** This script is in the repository's root directory, and its role is to write the data sent by the client application to a CSV file. The script receives a file name and listens to port 5000. When the application finishes the tests, it sends the collected data through that port. So, we advise you to run this script before running the experiments.

## Installation:

This section describes how to perform the codes present in this repository.

### Prerequisites:

First, the prerequisites, i.e., languages, tools, and libraries needed to run the test scenario. We highlight that the indicated versions are the same as those we used in our tests, so we suggest adopting these same versions in future experiments.

#### Client side:

1. BenchImageSecGRPC:<br/>
        * gRPC All 1.26.1 (already on build.gradle);<br/>
        * Protoc gRPC Java 1.28.1 (already on build.gradle).

#### Server side:

1. BenchImageSecGRPC: <br/>
        * Python 2.7.16 (necessary only to run the *results_server.py* script), Java Openjdk 8, Maven 3.6.3, Go 1.14.6, Protoc 3.6.1, and the lattest gRPC Plugins for each language;<br/>
        * OpenCV-Java 4.2.0 (Java), and GoCV 0.22.0 (Go).

After installing the requirements, you must generate the necessary files for gRPC to work. This procedure change from one language to another, so we suggest reading the [official gRPC documentation](https://grpc.io/docs/) to guide you through this step.

It is also necessary to generate the TLS and mTLS protocols files: the client and server certificates and keys. We suggest reading this [tutorial](https://www.digitalocean.com/community/tutorials/openssl-essentials-working-with-ssl-certificates-private-keys-and-csrs) to guide you through this step. 

On the server-side, you must to save the files in the following folders:<br/>
	* **server/benchimagesecgrpc/mtls_files:** Save the files related to MTLS protocol (server.key, server.crt, client.key, and client.crt);<br/>
	* **server/benchimagesecgrpc/tls_files:** Save the files related to TLS protocol (certificate.crt and privateKey.key)<br/>

On the client-side, you must save all necessary files in a folder on a mobile device's file system and edit the GrayScaleRemoteGRPCProtoMTls12.java and GrayScaleRemoteGRPCProtoTls12.java files with the path to them. 

### Running Scenario:

After installing all the prerequisites, we can run the scenarios. Initially, you must clone this repository. That done, you must, via Android Studio, open the application project, compile it and run it on the mobile device. There are different ways to execute the processes on the server side due to the different languages. Below, in the root directory of the server process codes, we describe the commands to execute the processes by language:

#### Go
> ```// compile and run the server```<br/>
> ```go run server.go <tls12 or mtls12 or insec>```<br/>


#### Java
> ```// compile and install the Maven project```<br/>
> ```mvn install```<br/>
> ```// run the server```<br/>
> ```mvn exec:java@main -Dexec.args=<"tls12" or "mtls12" or "insec"> ```<br/>

All processes, when running correctly, should print messages similar to these:

> ```Server started, listening on 50051```<br/>
> ```RPCMethod,Language,Operation,OpTime,TotalTime```<br/>

If these messages appear on your screen, the server process is ready to receive offloading from the client! We suggest you run a quick test with the *java_client* Maven project to confirm that everything is working correctly on the server side.

## Analysing generated data:

While the offloading is performed, processing information on the server side is printed on the screen. Here is an example about this information (in CSV format):

> ```RPCMethod,Language,Operation,OpTime,TotalTime```<br/>
> ```TLSv1.2,Java,GrayScale,2,29```<br/>
> ```(...)```<br/>

First, the result presents the method adopted during the offloading: TLSv1.2, MTLSv1.2, or Insec. In addition to information such as the language adopted by the server process (Java) and the operation performed by it (GrayScale), each line also contains the time spent only with the main action (OpTime) and the time spent with the complete processing of the method (TotalTime). For example, in the case of GrayScale, OpTime is the time spent only with the application of the gray filter, while TotalTime is the time spent with the complete operation (reading and writing messages, conversions, and the application of the filter).

The client-side also generates information about its processing. However, this information is just sent to the server (via *results_server.py*) at the end of the processing. Here is an example of this information (in CSV format):

> ```Round,Method,FilterType,PhotoSize,TimeCelProc,TimeCelTotal```<br/>
> ```1,TLSv1.2,GreyScale,1MP,3648,3805```<br/>
> ```(...)```<br/>

First, this data contains information about the RPC method adopted (Method), the filter applied (FilterType), and the image resolution used during the tests (PhotoSize). Second, it also contains information about the time spent just with the offloading (TimeCelProc) and with all operations (in addition to the offloading time, the time spent preparing data before to sent and after receiving).

## Contact us:

Any question or problem? Please, contact us <filipe.fernandes@crateus.ufc.br>

## How to Cite:

Like our work? Please, cite us: <br/> <br/>
Filipe F. S. B. de Matos, Paulo A. L. Rego, and Fernando A. M. Trinta. 2021. Secure Computational Offloading with gRPC: A Performance Evaluation in a Mobile Cloud Computing Environment. In Proceedings of the 11th ACM Symposium on Design and Analysis of Intelligent Vehicular Networks and Applications (DIVANet '21). Association for Computing Machinery, New York, NY, USA, 45--52. DOI:https://doi.org/10.1145/3479243.3487295
