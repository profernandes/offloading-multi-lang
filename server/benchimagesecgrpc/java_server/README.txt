protoc --plugin=protoc-gen-grpc-java=/home/filipe/.local/share/Trash/files/sorter/target/protoc-plugins/protoc-gen-grpc-java-1.26.0-linux-x86_64.exe --grpc-java_out=./ --proto_path=src/main/proto/ image.proto
protoc --java_out=./ --proto_path=src/main/proto/ image.proto
mvn exec:java@main -Dexec.args="mtls12"
