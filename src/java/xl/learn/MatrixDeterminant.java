package xl.learn;

import com.google.common.base.Joiner;

import java.util.Arrays;

/**
 * Created by xuelin on 5/12/17.
 */
public class MatrixDeterminant {
    public static class PermState {
        public int[] perm;
        public int sign;
        public int n;
        public int[] c;
        public int i;

        public PermState(int[] perm, int sign, int n) {
            this.perm = perm;
            this.sign = sign;
            this.n = n;
            c = new int[n];
            for (int ii = 0; ii < n; ii++) {
                c[ii] = 0;
            }
            i = 0;
        }
    }

    private static void swap(int[] arr, int fromIndex, int toIndex) {
        int tmp = arr[fromIndex];
        arr[fromIndex] = arr[toIndex];
        arr[toIndex] = tmp;
    }

    /**
     * heap's algorithm, each permutation sequence is generated in const time
     * @param state
     * @return
     */
    public static PermState moveNext(PermState state) {

        int n = state.n;
        if (state.perm == null) {
            int[] perm = new int[n];
            for (int ii = 0; ii < n; ii++)
                perm[ii] = ii;
            state.perm = perm;
            state.sign = 1;
            return state;
        }

        if (state.i >= n)
            return null;

        int[] c = state.c;
        int i = state.i;
        int[] perm = state.perm;
        if (c[i]  < i) {
            if (i % 2 == 0) {
                swap(perm, 0, i);
            }
            else {
                swap(perm, c[i], i);
            }
            c[i]++;
            state.i = 0;
            state.sign = -state.sign;
            return state;
        }
        else {
            c[i] = 0;
            state.i++;
            return moveNext(state);
        }


    }

    public static PermState moveNext2(PermState state) {
        int n = state.n;
        if (state.perm == null) {
            int[] perm = new int[n];
            for (int ii = 0; ii < n; ii++)
                perm[ii] = ii;
            state.perm = perm;
            state.sign = 1;
            return state;
        }

        int[] perm = state.perm;
        int next = perm[n - 1];
        int swapIndex1 = -1;
        int swapIndex2 = -1;
        for (int ii = n - 2; ii >= 0; ii--) {
            if (perm[ii] < next) {
                swapIndex1 = ii + 1;
                swapIndex2 = ii;
                for (int kk = ii + 2; kk < n; kk++) {
                    if (perm[kk] < perm[ii]) {
                        break;
                    }
                    swapIndex1 = kk;
                }
                break;
            }
            next = perm[ii];
        }

        if (swapIndex1 < 0) {
            return null;
        }

        swap(perm, swapIndex1, swapIndex2);
        state.sign = -state.sign;

        for (int ii = swapIndex2  + 1; ii < n - 1; ii++) {
            int minIndex = -1;
            for (int jj = ii; jj <= n - 1; jj++) {
                if (minIndex < 0)
                    minIndex = jj;
                else if (perm[jj] < perm[minIndex]) {
                    minIndex = jj;
                }
            }
            if (minIndex != ii) {
                swap(perm, ii, minIndex);
                state.sign = -state.sign;
            }
        }

        return state;
    }

    public static double calc(double[][] matrix) {
        int n = matrix.length;
        PermState state = new PermState(null, -1, n);
        double sum = 0;
        while (true) {
            state = moveNext(state);
            if (state == null) {
                break;
            }
            int[] perm = state.perm;
            int sign = state.sign;
            double prod = sign;
            for (int ii = 0; ii < n; ii++) {
                prod = prod * matrix[ii][perm[ii]];
            }
            sum += prod;
        }
        return sum;
    }

    public static void outputMatrix(double[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix.length; col++) {
                System.out.print(matrix[row][col]);
                System.out.print(", ");
            }
            System.out.println();
        }
    }

    private static void outputRow(int[] row) {
        for (int ii = 0; ii < row.length; ii++) {
            System.out.print(row[ii]);
            System.out.print(", ");
        }
        System.out.println();
    }
    public static void permSamples() {
        for (int n: new int[]{2, 3, 4}) {
            System.out.println("permutation for " + n);
            PermState state = new PermState(null, -1, n);
            while (true) {
                state = moveNext(state);
                if (state == null)
                    break;
                outputRow(state.perm);
            }
        }
    }
    public static void samples() {
        for (double[][] matrix: new double[][][] {
            new double[][]{ new double[] {1, 2}, new double[]{1, 2}},
                new double[][]{ new double[] {1, 2}, new double[]{3, 4},},
                new double[][]{ new double[] {1, 2, 3}, new double[]{2, 4, 6}, new double[]{1, 0, 0}},
                new double[][]{ new double[] {1, 2, 3}, new double[]{1, 2, 1}, new double[]{1, 0, 0}},
        }) {
            outputMatrix(matrix);
            System.out.println("determinant: " + calc(matrix));
        }
    }

    public static void main(String[] args) {
//        permSamples();
        samples();
    }
}
