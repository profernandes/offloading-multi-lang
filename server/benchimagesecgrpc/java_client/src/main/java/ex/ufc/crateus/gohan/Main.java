package benchimage;

import javax.net.ssl.SSLException;

public class Main {
        public static void main(String[] args) throws SSLException {
                ImageClient client = null;
                int serverPort = 50055;

                try {
                        switch (args[0]) {
                                case "tls12":
                                        System.out.println("Running TLSv1.2 ImageClient");
                                        client = new ImageClientTLS12(serverPort);
                                        break;
                                case "mtls12":
                                        System.out.println("Running Mutual TLSv1.2 ImageClient");
                                        client = new ImageClientMTLS12(serverPort);
                                        break;
                                case "insec":
                                        System.out.println("Running Insecure ImageClient");
                                        client = new ImageClientInsec(serverPort);
                                        break;
                                default:
                                        System.out.println("ERROR: Wrong parameter value! Use insec, tls12, or mtls12!");
                        }

                        if(client != null) {
                                client.computeTask();
                        }
                } catch (Exception ex) {
                        System.err.println(ex);
                }
        }
}
