package com.example.anderson.benchimage.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

import com.example.anderson.benchimage.util.ImageUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;

import java.util.Scanner;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class GrayScaleFilter implements FilterStrategy {

    private long offloadingTime;

    public GrayScaleFilter() {
        offloadingTime = 0;
    }

    @Override
    public byte[] applyFilter(byte[] source) {

        long initialTime = System.nanoTime();

        Bitmap bmpOriginal = BitmapFactory.decodeByteArray(source, 0, source.length);

        Bitmap bmpGrayscale = Bitmap.createBitmap(bmpOriginal.getWidth(), bmpOriginal.getHeight(), Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        new Canvas(bmpGrayscale).drawBitmap(bmpOriginal, 0, 0, paint);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmpGrayscale.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bmpGrayscale.recycle();

        offloadingTime = System.nanoTime() - initialTime;

        return byteArray;
    }

    @Override
    public int[][] applyFilter(int[][] source) {

        long initialTime = System.nanoTime();

        final double greyScaleRed = 0.299;
        final double greyScaleGreen = 0.587;
        final double greyScaleBlue = 0.114;

        int red, green, blue;
        int pixel;

        int imgWidth = source.length;
        int imgHeight = source[0].length;

        for (int x = 0; x < imgWidth; x++) {
            for (int y = 0; y < imgHeight; y++) {
                pixel = source[x][y];

                red = ImageUtils.getRed(pixel);
                green = ImageUtils.getGreen(pixel);
                blue = ImageUtils.getBlue(pixel);

                red = green = blue = (int) (greyScaleRed * red + greyScaleGreen * green + greyScaleBlue * blue);

                source[x][y] = ImageUtils.setColor(red, green, blue);
            }
        }

        offloadingTime = System.nanoTime() - initialTime;

        return source;
    }

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
        byte[] privateKeyBytes = android.util.Base64.decode(readKeyFile(clientKey), android.util.Base64.DEFAULT);
        PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        ks.setKeyEntry("key", privateKey, null, new Certificate[]{cert});
        kmf.init(ks, "".toCharArray());

        return kmf.getKeyManagers();
    }

    public static SSLContext createTlsEnvironment(String serverCert, String serverKey) throws GeneralSecurityException, IOException {
        SSLContext tlsContext = SSLContext.getInstance("TLSv1.2");

        TrustManager[] trustManagers = createTrustManagers(serverCert);
        tlsContext.init(null, trustManagers, null);

        return tlsContext;
    }

    public static SSLContext createMTslContext(String serverCert, String clientKey, String clientCert) throws GeneralSecurityException, IOException  {
        SSLContext tlsContext = SSLContext.getInstance("TLSv1.2");

        TrustManager[] trustManagers = createTrustManagers(serverCert);
        KeyManager[] keyManagers = createKeyManagers(clientCert, clientKey);
        tlsContext.init(keyManagers, trustManagers, new SecureRandom());

        return tlsContext;
    }

    public long getOffloadingTime() { return offloadingTime; }

    public void setOffloadingTime(long offloadingTime) { this.offloadingTime = offloadingTime; }
}
