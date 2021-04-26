# Offloading Multi-language Research Repository

## Contextualization:

This repository contains the source codes of the Android client applications and the C++, Go, Java, and Python server processes used in the experiments carried out that generated the article **"An Empirical Study about the Adoption of Multi-language Technique in Computation Offloading in a Mobile Cloud Computing Scenario"** published at the *11th International Conference on Cloud Computing and Services Science (CLOSER 2021).* For more information about the experiments, we suggest reading the [paper](https://www.insticc.org/node/TechnicalProgram/closer/2021/presentationDetails/104378).

The repository has two main folders in its root directory: *server/* (dedicated to server process codes) and *client/* (dedicated to the Android Studio projects of the client applications). Internally, each of these folders has two other folders: one for each application used in the experiments.

In order to simplify future experiments and interaction with the scenario, we have also developed and made available three Python 2.7 scripts:

1. ***client.py*:** There are two scripts, one in each folder dedicated to server processes, whose role is to generate a simple request for the server process, that is, to simulate the action of the client smartphone. We believe that this script can be useful for quickly testing server-side solutions and validating that the server is running correctly;
2. ***results_server.py*:** This script is in the root directory of the repository, and its role is to write the data sent by the client application to a CSV file. For this, the script receives a file name and listens to port 5000. When the application finishes executing the tests, it sends the collected data through that port. So, we advise you to run this script before running the experiments.

## Installation:

This section describes how to perform the codes present in this repository.

### Prerequisites:

First, the prerequisites, i.e., languages, tools, and libraries needed to run the test scenario. We highlight that the indicated versions are the same as those we used in our tests, so we suggest adopting these same versions in future experiments.

#### Client side:

1. Both applications:
	* gRPC All 1.26.1 (already on build.gradle)
	* Protoc gRPC Java 1.28.1 (already on build.gradle)

#### Server side:

1. Both Applications:
	* C++ 5.1.0, Python 2.7.16, Java Openjdk 8, Maven 3.6.3, Go 1.14.6, Protoc 3.6.1, and the lattest gRPC Plugins for each language.
2. MatrixGRPC:
	*  Eigen 3.3.7 (C++), Numpy 1.16.6 (Python), Apache Math 3.6.1 (Java), and Gonum 0.7.0 (Go).
3. BenchImageGRPC:
	* OpenCV 4.2.0 (C++), OpenCV-Python 4.2.0 (Python), OpenCV-Java 4.2.0 (Java), and GoCV 0.22.0 (Go).

After the requirements are installed, you must generate the necessary files for gRPC to work. This procedure can vary from one language to another, so we suggest reading the [official gRPC documentation](https://grpc.io/docs/) to guide you through this step. 

### Running Scenario:

After installing all the prerequisites, we can run the scenarios. Initially, you must clone this repository. That done, you must, via Android Studio, open the project of the desired application, compile it and run it on the mobile device. On the server-side, there are different ways to execute the processes due to the different languages. Below, in the root directory of the server process codes, we describe the commands to execute the processes by language:

#### C++
> ```make // compile the code```<br/>
> ```./server // run the server```<br/>


#### Go
> ```go run server.go // compile and run the server```<br/>


#### Java
> ```mvn install // compile the Maven project```<br/>
> ```mvn exec:java // run the server```<br/>

#### Python
> ```python server.py // run the server```<br/>


All processes, when running correctly, should print messages similar to the following on the screen:

> ```Server started, listening on 50051```<br/>
> ```Language,Operation,OpTime,TotalTime```<br/>

If this message appears on your screen, the server process is ready to receive offloading from the client! We suggest you run a quick test with the *client.py* script related to the chosen application to confirm that everything is working correctly on the server-side.

## Analysing generated data:

While the offloading is performed, processing information on the server-side is printed on the screen. Here is a example about this information (in CSV format):

> ```Language,Operation,OpTime,TotalTime```<br/>
> ```Java,GrayScale,3,282```<br/>
> ```(...)```<br/>

In addition to information such as the language adopted by the server process (Java) and the operation performed by it (GrayScale), each line also contains the time spent only with the main action (OpTime) and the time spent with the complete processing of the method (TotalTime). For example, in the case of GrayScale, OpTime is the time spent only with the application of the gray filter, while TotalTime is the time spent with the complete operation (reading and writing messages, conversions, and the application of the filter).

The client-side also generates information about its processing. 
However, this information is just sent to the server (via *results_server.py*) at the end of the processing. Here is an example of this information (in CSV format):

> ```Round,Method,PhotoSize,TimeCelProc,TimeCelTotal```<br/>
> ```1,gRPCProto,8MP,4083,4484,```<br/>
> ```(...)```<br/>

First, This data generated contains information about the RPC method adopted (gRPCProto) and the exclusive data per application (PhotoSize or MatrixD). Second, the line also contains information about the time spent just with the offloading (TimeCelProc) and with all operation (in addition to the offloading time, the time spent preparing data before to sent and after receiving).

## Contact us:

Any question or problem? Please, contact us <filipe.fernandes@crateus.ufc.br>

## How to Cite:

Like our work? Please, cite us:

