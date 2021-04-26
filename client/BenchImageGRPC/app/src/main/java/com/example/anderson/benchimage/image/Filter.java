package com.example.anderson.benchimage.image;

public interface Filter {
    public int[][] greyScaleImage(int source[][]);

    public byte[] mapTone(byte source[], byte map[]);

    public int[][] mapTone(int source[][], int map[][]);

    public byte[] filterApply(byte source[], double filter[][], double factor, double offset);

    public int[][] filterApply(int source[][], double filter[][], double factor, double offset);

    public byte[] cartoonizerImage(byte source[]);

    public int[][] cartoonizerImage(int source[][]);

    public int[][] sepiaScaleImage(int source[][]);
}