package ufc.crateus.gohan;

import ufc.crateus.gohan.strategy.*;

public class Main {
    public static void main(String[] args) {
		ImageServer server = null;
        int serverPort = 50055;

        try {
            switch (args[0]) {
                case "tls12":
                    System.out.println("Running TLSv1.2 ImageServer");
                    server = new ImageServerTLS12(serverPort);
                    break;
                case "mtls12":
                    System.out.println("Running Mutual TLSv1.2 ImageServer");
                    server = new ImageServerMTLS12(serverPort);
                    break;
		        case "insec":
                    System.out.println("Running Insecure ImageServer");
                    server = new ImageServerInsec(serverPort);
                    break;
                default:
                    System.out.println("ERROR: Wrong parameter value! Use insec, tls12, or mtls12!");
            }
            if (server != null) {
                server.start();
                server.blockUntilShutdown();
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
	}
}
