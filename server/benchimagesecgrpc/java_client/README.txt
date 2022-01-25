To run do it:

mvn exec:java -Dexec.mainClass="benchimage.Main" -Dexec.args="X"
or
mvn exec:java@main -Dexec.args="X"

where: X = tls12, mtls12 or insec
