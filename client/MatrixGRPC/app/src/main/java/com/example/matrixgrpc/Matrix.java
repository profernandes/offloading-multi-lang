package com.example.matrixgrpc;

import java.util.ArrayList;

public interface Matrix {

    public int[][] random(int m, int n);

    public int[][] add(int[][] A, int[][] B);

    public int[][] multiply(int[][] A, int[][] B);

    public ArrayList<Integer> castToArrayList(int[][] to_cast);
}
