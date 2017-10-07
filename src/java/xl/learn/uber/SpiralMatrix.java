package xl.learn.uber;

import com.google.common.primitives.Ints;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by xuelin on 10/4/17.
 */
public class SpiralMatrix {
    public static void main(String[] args) {
        int[][] matrix = new int[][]{
                { 1, 2, 3 },
                { 4, 5, 6 },
                { 7, 8, 9 }
        };
        int[] res = spiral(matrix);
        System.out.println(Ints.asList(res).stream().map(x -> String.valueOf(x)).collect(Collectors.joining(", ")));
    }

    public static int[] spiral(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;

        int r = 0, c = 0;
        int direction = 0;
        int lowRow = 0;
        int highRow = row - 1;
        int lowCol = 0;
        int highCol = col - 1;
        int next = 0;

        int[] res = new int[row * col];

        while (true) {
            res[next++] = matrix[r][c];

            if (next >= row * col)
                break;

            switch (direction) {
                case 0:
                    if (c + 1 > highCol) {
                        direction = 1;
                        r++;
                        lowRow++;
                    }
                    else
                        c++;
                    break;
                case 1:
                    if (r + 1 > highRow) {
                        direction = 2;
                        c--;
                        highCol--;
                    }
                    else
                        r++;
                    break;
                case 2:
                    if (c - 1 < lowCol) {
                        direction = 3;
                        r--;
                        highRow--;
                    }
                    else
                        c--;
                    break;
                case 3:
                    if (r - 1 < lowRow) {
                        direction = 0;
                        c++;
                        lowCol++;
                    }
                    else
                        r--;
                    break;
            }

        }
        return res;
    }
}
