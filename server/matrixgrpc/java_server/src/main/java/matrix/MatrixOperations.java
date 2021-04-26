package matrix;

import java.lang.Double;
import java.lang.Double;
import java.util.List;
import java.util.ArrayList;

public class MatrixOperations {

	/* Multiply Matrices */
        public static ArrayList<Integer> multiplyMatrices(int[][] firstMatrix, int[][] secondMatrix, int n_rows, int n_cols) {
                ArrayList<Integer> product = new ArrayList<Integer>();

                for(int i = 0; i < n_rows; i++) {
                        for (int j = 0; j < n_cols; j++) {
                                int aux = 0;
                                for (int k = 0; k < n_cols; k++) {
                                        aux += firstMatrix[i][k] * secondMatrix[k][j];
                                }
                                product.add(new Integer(aux));
                        }
                }

                return product;
        }

        /* Sum Matrices */
        public static ArrayList<Integer> sumMatrices(int[][] firstMatrix, int[][] secondMatrix, int n_rows, int n_cols) {
                //double[][] product = new double[n_rows][n_cols];
                ArrayList<Integer> result = new ArrayList<Integer>();

                for(int i = 0; i < n_rows; i++) {
                    for (int j = 0; j < n_cols; j++) {
                            result.add(new Integer(firstMatrix[i][j] + secondMatrix[i][j]));
                    }
                }

                return result;
        }

}
