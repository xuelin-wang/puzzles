package xl.learn;

/**
 * Created by xuelin on 7/20/17.
 * No limit on number of coins for each denomination
 * find out numbere of ways for coin changes
 * Another dynamic programming problem:
 *   order denoms ascending array c
 *   c[0] c[1] c[2] ...
 *   f(t, i): f(t - c[i], i) + f(t, i - 1)
 *   f(0, i) = 1
 */
public class CoinChanges {
    public static int counts(int total, int[] sortedCoins) {
        int n = sortedCoins.length;
        int[][] f = new int[total + 1][n];

        for (int t = 0; t <= total; t++) {
            for (int i = 0; i < n; i++) {
                if (i == 0)
                    f[t][i] = 1;
                else
                    f[t][i] = f[t - sortedCoins[i]][i] + f[t][i - 1];
            }
        }
        return f[total + 1][n - 1];
    }
}
