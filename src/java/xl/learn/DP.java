package xl.learn;

import java.util.HashMap;
import java.util.Map;

public class DP {

    public static class MatrixCalc {
        public int[] order;
        public int cost;
        public MatrixCalc(int[] order, int cost) {
            this.order = order;
            this.cost = cost;
        }
    }

    /**
     * maxtrix i has dimension of {d[i], d[i + 1]}
     * find out optimal order of multiplication to
     * minimize cost.
     * Note c(M[p, q], N[q, r]) = pqr
     * c(M[0..n-1]) = min i : c(M[0..i]) + c(M[i+1..n-1]) + pqr
     * calculate c botumn up, like a triangle
     * @param d
     * @return
     */
    public static MatrixCalc optimalMatrixMultiply(int[] d)
    {
        Map<int[],MatrixCalc> optimal = new HashMap<>();
        int n = d.length;
        for (int i = 0; i < n - 1; i++) {
            optimal.put(new int[]{i, i}, new MatrixCalc(new int[]{i}, 0));
        }

        for (int count = 1; count < n - 1; count++) {
            for (int i = 0; i + count - 1 < n - 1; i++) {
                int cost = Integer.MAX_VALUE;
                MatrixCalc calc = null;
                for (int j = i; j < i + count - 1; j++) {
                    MatrixCalc cLeft = optimal.get(new int[]{i, j});
                    MatrixCalc cRight = optimal.get(new int[]{j + 1, i + count - 1});
                    int c = cLeft.cost + cRight.cost + d[i] * d[j] * d[i + count];
                    if (c < cost) {
                        calc = new MatrixCalc(new int[]{}, c);
                        int[] order = new int[count];
                        System.arraycopy(cLeft.order, 0, order, 0, cLeft.order.length);
                        optimal.put(new int[]{i, i + count - 1}, new MatrixCalc(order, c));
                        System.arraycopy(cRight.order, 0, order, cLeft.order.length, cRight.order.length);
                        order[count - 1] = j;
                        optimal.put(new int[]{i, i + count - 1}, calc);
                        cost = c;
                    }
                }
            }
        }
        return optimal.get(new int[]{0, n - 2});
    }

    public static void optimalTriangulation()
    {

    }
}
