package com.example.anderson.benchimage.image;

import com.example.anderson.benchimage.util.ImageUtils;

public class GrayScaleFilter implements FilterStrategy {

    private long offloadingTime;

    public GrayScaleFilter() {
        offloadingTime = 0;
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

        offloadingTime = (System.nanoTime() - initialTime) / 1000000;

        return source;
    }

    public long getOffloadingTime() { return offloadingTime; }

    public void setOffloadingTime(long offloadingTime) { this.offloadingTime = offloadingTime; }
}
