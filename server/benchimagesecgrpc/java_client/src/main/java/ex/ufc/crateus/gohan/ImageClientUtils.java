package benchimage;

import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;

import java.io.File;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.util.Base64;

import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.PrivateKey;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.KeyManager;

// readKeyFile imports
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.util.Scanner;

public class ImageClientUtils {

	private static String readKeyFile(String keyFilePath) {
		String contentFile = new String();
		try {
			Scanner readerFile = new Scanner(new File(keyFilePath));
			while(readerFile.hasNextLine()) {
				contentFile += readerFile.nextLine().replace("\n","");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		contentFile = contentFile.replace("-----BEGIN PRIVATE KEY-----", "");
		contentFile = contentFile.replace("-----END PRIVATE KEY-----", "");

		return contentFile;
	}

	private static TrustManager[] createTrustManagers(String serverCert) throws GeneralSecurityException, IOException {
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate cert = (X509Certificate) cf.generateCertificate(new FileInputStream(new File(serverCert)));
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(null);
        ks.setCertificateEntry("cert", cert);
        tmf.init(ks);

		return tmf.getTrustManagers();
	}

	private static KeyManager[] createKeyManagers(String clientCert, String clientKey) throws GeneralSecurityException, IOException {
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate cert = (X509Certificate) cf.generateCertificate(new FileInputStream(new File(clientCert)));
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());

		KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(null, null);
        KeyFactory kf = KeyFactory.getInstance("RSA");
		byte[] privateKeyBytes = Base64.getDecoder().decode(readKeyFile(clientKey));
        PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        ks.setKeyEntry("key", privateKey, null, new Certificate[]{cert});
        kmf.init(ks, "".toCharArray());

		return kmf.getKeyManagers();
	}

	public static SSLContext createTls12Environment(String serverCert) throws GeneralSecurityException, IOException {
        SSLContext tlsContext = SSLContext.getInstance("TLSv1.2");

		TrustManager[] trustManagers = createTrustManagers(serverCert);
        tlsContext.init(null, trustManagers, null);

        return tlsContext;
    }

    public static SSLContext createMTls12Context(String serverCert, String clientKey, String clientCert) throws GeneralSecurityException, IOException  {
        SSLContext tlsContext = SSLContext.getInstance("TLSv1.2");

		TrustManager[] trustManagers = createTrustManagers(serverCert);
		KeyManager[] keyManagers = createKeyManagers(clientCert, clientKey);
        tlsContext.init(keyManagers, trustManagers, new SecureRandom());

        return tlsContext;
    }

	public static boolean saveImage(Mat to_save) {
		try {
			MatOfByte mob = new MatOfByte();
			Imgcodecs.imencode(".jpg", to_save, mob);
			byte ba[] = mob.toArray();
			
			BufferedImage bi = ImageIO.read(new ByteArrayInputStream(ba));
			
			File outputfile = new File("../gray.jpg");
			ImageIO.write(bi, "jpg", outputfile);
			return true;
		} catch (IOException e) {
			System.out.println("DEU RUIM!!!!!");
			return false;
		}
	}
}
