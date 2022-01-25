package com.example.anderson.benchimage.image;

public interface Filter {
    public byte[] greyScaleImage(byte source[]);

    public int[][] greyScaleImage(int source[][]);

    public int[][] sepiaScaleImage(int source[][]);

    public byte[] mapTone(byte source[], byte map[]);

    public int[][] mapTone(int source[][], int map[][]);

    public byte[] filterApply(byte source[], double filter[][], double factor, double offset);

    public int[][] filterApply(int source[][], double filter[][], double factor, double offset);

    public byte[] cartoonizerImage(byte source[]);

    public int[][] cartoonizerImage(int source[][]);

    public byte[] invertColors(byte data[]);

    public byte[] sepiaFilter(byte data[]);
}