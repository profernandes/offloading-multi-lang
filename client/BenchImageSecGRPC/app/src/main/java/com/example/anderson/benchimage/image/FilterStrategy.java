package com.example.anderson.benchimage.image;

public interface FilterStrategy {
    public byte[] applyFilter(byte source[]);

    public int[][] applyFilter(int source[][]);
}
