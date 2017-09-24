package xl.learn.google;

import com.google.common.primitives.Ints;

import java.util.stream.Collectors;

/**
 * Created by xuelin on 9/23/17.
 *
 * Given a 2D matrix matrix, find the sum of the elements inside the rectangle defined by its upper left corner (row1, col1) and lower right corner (row2, col2).

 Range Sum Query 2D
 The above rectangle (with the red border) is defined by (row1, col1) = (2, 1) and (row2, col2) = (4, 3), which contains sum = 8.

 Example:
 Given matrix = [
 [3, 0, 1, 4, 2],
 [5, 6, 3, 2, 1],
 [1, 2, 0, 1, 5],
 [4, 1, 0, 1, 7],
 [1, 0, 3, 0, 5]
 ]

 sumRegion(2, 1, 4, 3) -> 8
 update(3, 2, 2)
 sumRegion(2, 1, 4, 3) -> 10
 Note:
 The matrix is only modifiable by the update function.
 You may assume the number of calls to update and sumRegion function is distributed evenly.
 You may assume that row1 ≤ row2 and col1 ≤ col2.

 Solution:
 can we use similar trick as in range 1D?
 1. Sparse table
 Calc all i, j to i + 2^k, j + 2^m for all k and m.
 s[i][j][k][m] = s[i][j][k][m-1] + s[i][j+2^(m-1)][k][m-1]
 s[i][j][k][m] = s[i][j][k-1][m] + s[i+2^(k-1)][j][k-1][m]
 Space: O(mn logm logn), same precompute time, query time: O(logm logn).

 2. squre root blocks:
 if we precompute m^0.5 and n^0.5 blocks, totally O(mn) time.
 when query, calc subblocks + remaining: O(m^0.5 n^0.5)
 when update, update the sum of the block.

 3. naive:
 O(m^3 n^3, 1).

 Binary index tree:

 */
public class Range2DSum {
    final int[][] matrix;
    int[][][][] sums;
    public Range2DSum(int[][] matrix) {
        this.matrix = matrix;
        this.sums = null;
    }

    private int floorLog2(int index, int size) {
        int levels = 0;
        while (index + (1 << levels) - 1 < size)
            levels++;
        levels--;
        return levels;
    }

    private int ceilLog2(int index, int size) {
        int levels = 0;
        while (index + (1 << levels) < size)
            levels++;
        return levels;
    }

    public void update(int r, int c, int v) {
        int delta = v - matrix[r][c];
        matrix[r][c] = v;
        for (int i = r; i>=0; i--) {
            for (int j = c; j >= 0; j--) {
                int dr = r-i+1;
                int logR = ceilLog2(0, dr);
                int dc = c-j+1;
                int logC = ceilLog2(0, dc);
                sums[i][j][logR][logC] += delta;
            }
        }
    }

    public void precompute() {
        if (sums != null)
            return;

        int rowCount = matrix.length;
        int colCount = matrix[0].length;

        sums = new int[rowCount][colCount][][];
        int logRow = floorLog2(0, rowCount);
        int logCol = floorLog2(0, colCount);
        for (int k = 0; k <= logRow; k++) {
            for (int m = 0; m <= logCol; m++) {
                for (int i = 0; i < rowCount; i++) {
                    int logI = floorLog2(i, rowCount);
                    if (k > logI)
                        continue;
                    for (int j = 0; j < colCount; j++) {
                        int logJ = floorLog2(j, colCount);
                        if (m > logJ)
                            continue;

                        if (k == 0 && m == 0) {
                            sums[i][j] = new int[logI + 1][logJ + 1];
                            sums[i][j][k][m] = matrix[i][j];
                        }
                        else {
                            if (m == 0) {
                                sums[i][j][k][m] = sums[i][j][k-1][m] + sums[i + (1 << (k-1))][j][k-1][m];
                            }
                            else {
                                sums[i][j][k][m] = sums[i][j][k][m-1] + sums[i][j+ + (1 << (m-1))][k][m-1];
                            }
                        }
                    }
                }
            }
        }
    }

    public int query(int[] range) {
        return query(range[0], range[1], range[2], range[3]);
    }

    public int query(int i0, int j0, int i1, int j1) {
        precompute();

        int dr = i1 - i0 + 1;
        int dc = j1 - j0 + 1;

        int sum = 0;
        for (int k = dr; k > 0; ) {
            int k1 = k & (-k);
            k = k - k1;
            int logK1 = floorLog2(0, k1);
            for (int m = dc; m > 0; ) {
                int m1 = m & (-m);
                m = m - m1;
                int logM1 = floorLog2(0, m1);
                sum += sums[i0+k][j0+m][logK1][logM1];
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{
                {3, 0, 1, 4, 2},
                {5, 6, 3, 2, 1},
                {1, 2, 0, 1, 5},
                {4, 1, 0, 1, 7},
                {1, 0, 3, 0, 5}
        };
        Range2DSum rangeSum = new Range2DSum(matrix);
        rangeSum.precompute();

        for (int[] indexes: new int[][]{
                new int[]{2, 1, 4, 3}
        }) {
            System.out.println("range: " + Ints.asList(indexes).stream().map(x -> String.valueOf(x)).collect(Collectors.joining(", ")));
            System.out.println("sum is: " + rangeSum.query(indexes));
            rangeSum.update(3, 2, 2);
            System.out.println("sum is: " + rangeSum.query(indexes));
        }
    }

}
