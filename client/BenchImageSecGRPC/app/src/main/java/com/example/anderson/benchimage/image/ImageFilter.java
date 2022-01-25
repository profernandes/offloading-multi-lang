package com.example.anderson.benchimage.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

import com.example.anderson.benchimage.util.ImageUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public final class ImageFilter implements CloudletFilter, InternetFilter {

    public byte[] mapTone(byte source[], byte map[]) {
        byte data[] = null;
        try {
            int image[][] = mapTone(ImageUtils.decodeJpegToRaw(source), ImageUtils.decodeJpegToRaw(map));
            data = ImageUtils.encodeRawToJpeg(image);
            image = null;
            System.gc();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public int[][] mapTone(int source[][], int map[][]) {
        int imgWidth = source.length;
        int imgHeight = source[0].length;
        int filterHeight = map[0].length;

        for (int x = 0; x < imgWidth; x++) {
            for (int y = 0; y < imgHeight; y++) {

                int color = source[x][y];
                int channelRed = ImageUtils.getRed(color);
                int channelGreen = ImageUtils.getGreen(color);
                int channelBlue = ImageUtils.getBlue(color);

                if (filterHeight == 1) {
                    channelRed = map[channelRed][0];
                    channelGreen = map[channelGreen][0];
                    channelBlue = map[channelBlue][0];
                } else {
                    channelRed = map[channelRed][0];
                    channelGreen = map[channelGreen][1];
                    channelBlue = map[channelBlue][2];
                }
                source[x][y] = ImageUtils.setColor(ImageUtils.getRed(channelRed), ImageUtils.getGreen(channelGreen), ImageUtils.getBlue(channelBlue));
            }
        }
        return source;
    }

    public byte[] cartoonizerImage(byte source[]) {
        byte data[] = null;
        try {
            int image[][] = cartoonizerImage(ImageUtils.decodeJpegToRaw(source));
            data = ImageUtils.encodeRawToJpeg(image);
            image = null;
            System.gc();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public int[][] cartoonizerImage(int source[][]) {
        source = greyScaleImage(source);

        int imageInvert[][] = invertColors(ImageUtils.rawImageClone(source));

        double maskFilter[][] = { {1, 2, 1}, {2, 4, 2}, {1, 2, 1}};
        imageInvert = filterApply(imageInvert, maskFilter, 1.0 / 16.020, 0.0);

        return colorDodgeBlendOptimized(imageInvert, source);
    }

    public byte[] filterApply(byte source[], double filter[][], double factor, double offset) {
        byte data[] = null;
        try {
            int image[][] = filterApply(ImageUtils.decodeJpegToRaw(source), filter, factor, offset);
            data = ImageUtils.encodeRawToJpeg(image);
            image = null;
            System.gc();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public int[][] filterApply(int source[][], double filter[][], double factor, double offset) {
        int imgWidth = source.length;
        int imgHeight = source[0].length;

        int filterHeight = filter.length;
        int filterWidth = filter[0].length;

        for (int x = 0; x < imgWidth; x++) {
            for (int y = 0; y < imgHeight; y++) {
                int red = 0;
                int green = 0;
                int blue = 0;

                for (int filterX = 0; filterX < filterWidth; filterX++) {
                    for (int filterY = 0; filterY < filterHeight; filterY++) {
                        int imageX = (x - (filterWidth / 2) + filterX + imgWidth) % imgWidth;
                        int imageY = (y - (filterHeight / 2) + filterY + imgHeight) % imgHeight;

                        int color = source[imageX][imageY];

                        double maskValue = filter[filterX][filterY];

                        red += (ImageUtils.getRed(color) * maskValue);
                        green += (ImageUtils.getGreen(color) * maskValue);
                        blue += (ImageUtils.getBlue(color) * maskValue);
                    }
                }

                red = Math.min(Math.max((int) (factor * red + offset), 0), 255);
                green = Math.min(Math.max((int) (factor * green + offset), 0), 255);
                blue = Math.min(Math.max((int) (factor * blue + offset), 0), 255);

                source[x][y] = ImageUtils.setColor(red, green, blue);
            }
        }

        return source;
    }

    public byte[] greyScaleImage(byte data[]) {
        Bitmap bmpOriginal = BitmapFactory.decodeByteArray(data, 0, data.length);

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

        return byteArray;
    }

    public int[][] greyScaleImage(int source[][]) {
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

        return source;
    }

    public int[][] sepiaScaleImage(int source[][]) {
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

                red = (int) ((red * 0.393) + (green * 0.769) + (blue * 0.189));
                green = (int) ((red * 0.349) + (green * 0.686) + (blue * 0.168));
                blue = (int) ((red * 0.272) + (green * 0.534) + (blue * 0.131));

                if (red > 255) red = 255;
                if (green > 255) green = 255;
                if (blue > 255) blue = 255;

                source[x][y] = ImageUtils.setColor(red, green, blue);
            }
        }

        return source;
    }

    private int[][] colorDodgeBlendOptimized(int source[][], int layer[][]) {
        int imgWidth = source.length;
        int imgHeight = source[0].length;

        for (int i = 0; i < imgHeight; i++) {
            for (int j = 0; j < imgWidth; j++) {
                int filterInt = layer[j][i];
                int srcInt = source[j][i];

                int redValueFinal = colordodge(ImageUtils.getRed(filterInt), ImageUtils.getRed(srcInt));
                int greenValueFinal = colordodge(ImageUtils.getGreen(filterInt), ImageUtils.getGreen(srcInt));
                int blueValueFinal = colordodge(ImageUtils.getBlue(filterInt), ImageUtils.getBlue(srcInt));

                source[j][i] = ImageUtils.setColor(redValueFinal, greenValueFinal, blueValueFinal);
            }
        }

        return source;
    }

    public byte[] invertColors(byte data[]) {
        Bitmap bmpOriginal = BitmapFactory.decodeByteArray(data, 0, data.length);

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

        return byteArray;
    }

    public byte[] sepiaFilter(byte data[]) {
        Bitmap bmpOriginal = BitmapFactory.decodeByteArray(data, 0, data.length);

        Bitmap bmpInvert = Bitmap.createBitmap(bmpOriginal.getWidth(), bmpOriginal.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmpInvert);
        Paint paint = new Paint();

        final ColorMatrix matrixA = new ColorMatrix();
        matrixA.setSaturation(0);

        final ColorMatrix matrixB = new ColorMatrix();
        matrixB.setScale(1f, .95f, .82f, 1.0f);
        matrixA.setConcat(matrixB, matrixA);

        final ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrixA);
        paint.setColorFilter(filter);

        canvas.drawBitmap(bmpOriginal, 0, 0, paint);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmpInvert.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bmpInvert.recycle();

        return byteArray;
    }

    private int[][] invertColors(int source[][]) {
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
        return source;
    }

    private int colordodge(int in1, int in2) {
        float image = (float) in2;
        float mask = (float) in1;
        return ((int) ((image == 255) ? image : Math.min(255, (((long) mask << 8) / (255 - image)))));
    }
}