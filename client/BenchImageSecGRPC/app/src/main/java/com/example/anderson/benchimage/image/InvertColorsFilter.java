package com.example.anderson.benchimage.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

import com.example.anderson.benchimage.util.ImageUtils;

import java.io.ByteArrayOutputStream;

public class InvertColorsFilter implements FilterStrategy {

    private long offloadingTime;

    public InvertColorsFilter() {
        offloadingTime = 0;
    }

    @Override
    public byte[] applyFilter(byte[] source) {

        long initialTime = System.nanoTime();

        Bitmap bmpOriginal = BitmapFactory.decodeByteArray(source, 0, source.length);

        Bitmap bmpInvert = Bitmap.createBitmap(bmpOriginal.getWidth(), bmpOriginal.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmpInvert);
        Paint paint = new Paint();

        ColorMatrix matrixGrayscale = new ColorMatrix();
        matrixGrayscale.setSaturation(0);

        ColorMatrix matrixInvert = new ColorMatrix();
        matrixInvert.set(new float[]
                {
                        -1.0f, 0.0f, 0.0f, 0.0f, 255.0f,
                        0.0f, -1.0f, 0.0f, 0.0f, 255.0f,
                        0.0f, 0.0f, -1.0f, 0.0f, 255.0f,
                        0.0f, 0.0f, 0.0f, 1.0f, 0.0f
                });
        matrixInvert.preConcat(matrixGrayscale);

        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrixInvert);
        paint.setColorFilter(filter);

        canvas.drawBitmap(bmpOriginal, 0, 0, paint);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmpInvert.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bmpInvert.recycle();

        offloadingTime = System.nanoTime() - initialTime;

        return byteArray;
    }

    @Override
    public int[][] applyFilter(int[][] source) {

        long initialTime = System.nanoTime();

        int pixelColor;
        int red, green, blue;

        int imgWidth = source.length;
        int imgHeight = source[0].length;
        for (int y = 0; y < imgHeight; y++) {
            for (int x = 0; x < imgWidth; x++) {
                pixelColor = source[x][y];

                red = 255 - ImageUtils.getRed(pixelColor);
                green = 255 - ImageUtils.getGreen(pixelColor);
                blue = 255 - ImageUtils.getBlue(pixelColor);

                source[x][y] = ImageUtils.setColor(red, green, blue);
            }
        }

        offloadingTime = System.nanoTime() - initialTime;

        return source;
    }

    public long getOffloadingTime() { return offloadingTime; }

    public void setOffloadingTime(long offloadingTime) { this.offloadingTime = offloadingTime; }
}
