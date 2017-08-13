package xl.learn;

import com.google.common.base.Joiner;
import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class CoinPath {
    public static Collection<Integer> coinPath(int[] a, int b) {
        int n = a.length;
        int[] next = new int[n];
        int[] minCost = new int[n];
        Arrays.fill(next, -1);
        Arrays.fill(minCost, Integer.MAX_VALUE);

        for (int i = n - 1; i >= 0; i--) {
            if (i == n - 1) {
                next[i] = n -1;
                minCost[i] = a[i];
            }
            else if (a[i] >= 0) {
                int min = Integer.MAX_VALUE;
                int minNext = -1;
                for (int j = Math.min(i + b, n - 1); j > i; j--) {
                    if (minCost[j] < min) {
                        min = minCost[j];
                        minNext = j;
                    }
                }
                minCost[i] = min == Integer.MAX_VALUE ? Integer.MAX_VALUE : min + a[i];
                next[i] = minNext;
            }
        }

        Collection<Integer> path = new ArrayList<>();
        int i = 0;
        while (next[i] > 0 && i < n - 1) {
            path.add(i);
            i = next[i];
        }
        return path;
    }

    public static void main(String[] args) {
        for (Object[] config: new Object[][]{
                new Object[]{new int[]{1,2,4,-1,2}, 2},
                new Object[]{new int[]{1,2,4,-1,2}, 1},
        }) {
            Collection<Integer> path = CoinPath.coinPath((int[])config[0], (Integer)config[1]);
            System.out.println("Optimal path for " + Joiner.on(", ").join(Ints.asList((int[])config[0])) +
              " and step: " + config[1] + ":");
            System.out.println("[" + Joiner.on(", ").join(path) + "]");
        }
    }
}
