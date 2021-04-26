package com.example.matrixgrpc;

import java.util.ArrayList;
import java.util.Random;

public class MatrixImpl implements Matrix {

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
                C[i][j] = (int) Math.random() * (rd.nextInt(max) + 1);
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

    // return C = A + B
    public int[][] add(int[][] A, int[][] B) {
        int m = A.length;
        int n = A[0].length;
        int[][] C = new int[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] + B[i][j];
        return C;
    }

    // return C = A - B
    public int[][] subtract(int[][] A, int[][] B) {
        int m = A.length;
        int n = A[0].length;
        int[][] C = new int[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] - B[i][j];
        return C;
    }

    // return C = A * B
    public int[][] multiply(int[][] A, int[][] B) {
        int mA = A.length;
        int nA = A[0].length;
        int mB = B.length;
        int nB = B[0].length;
        if (nA != mB)
            throw new RuntimeException("Illegal matrix dimensions.");
        int[][] C = new int[mA][nB];
        for (int i = 0; i < mA; i++)
            for (int j = 0; j < nB; j++)
                for (int k = 0; k < nA; k++)
                    C[i][j] += (A[i][k] * B[k][j]);
        return C;
    }

    @Override
    public ArrayList<Integer> castToArrayList(int[][] to_cast) {
        ArrayList<Integer> to_return = new ArrayList<Integer>();

        for (int i = 0; i < to_cast.length; i++) {
            for (int j = 0; j < to_cast[i].length; j++) {
                to_return.add(Integer.valueOf(to_cast[i][j]));
            }
        }

        return to_return;
    }
}
