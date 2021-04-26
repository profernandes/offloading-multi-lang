package com.example.matrixgrpc.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class MatrixStrategy {

    public int[][] random(int m, int n) {
        int[][] C = new int[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = (int) Math.random();
        return C;
    }

    public int[][] random(int m, int n, int max) {
        Random rd = new Random();
        int[][] C = new int[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = 1 + (int) (Math.random() * max);
        return C;
    }

    // return C = A^T
    public int[][] transpose(int[][] A) {
        int m = A.length;
        int n = A[0].length;
        int[][] C = new int[n][m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                C[j][i] = A[i][j];
        return C;
    }

    public ArrayList<Integer> castToArrayList(int[][] to_cast) {
        ArrayList<Integer> to_return = new ArrayList<Integer>();

        for (int i = 0; i < to_cast.length; i++) {
            for (int j = 0; j < to_cast[i].length; j++) {
                to_return.add(Integer.valueOf(to_cast[i][j]));
            }
        }

        return to_return;
    }

    public int[][] castToArray ( List<Integer> to_cast, int cols) {
        int[][] to_return = new int[cols][cols];

        int a = 0, b = 0;
        for (Integer value : to_cast) {
            to_return[a][b] = value.intValue(); b++;
            if (b == cols) { a++; b = 0; }
        }

        return to_return;
    }

    public abstract int[][] add(int[][] A, int[][] B);

    public abstract int[][] multiply(int[][] A, int[][] B);

    public abstract void closeChannel();
}
