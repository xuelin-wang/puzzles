package xl.learn;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuelin on 10/2/17.
 *
 *
 * Say you have an array for which the ith element is the price of a given stock on day i.

 Design an algorithm to find the maximum profit. You may complete at most k transactions.

 Note:
 You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).

 Analysis:
 if there are less than k increasing subsequence, then solution is to profit from those sequences: sum(seq)
 If there are more than k increasing subsequence?
     say there are k+1 sub sequence. We need compare pick k out of k+1 with biggest delta.
         or: merge two of those.

 assertion 1: for each max transaction b/s, b must be beginning of a inc sequence and s must be end of a inc sequence.

 assertion 2: if a max transaction contains three monotone seqs, s1(up)s2(down)s3(up), then s1 begin price must be less than s3 s3 begin price.

 so problems becomes picking at most k non-overlapping begin/end of inc sequences.

 DP:
 so simplify orig sequence to inc sequences: bi, ei.
 if pairs count <= k, simply return transaction per inc seq.
 dp[i, j] represents use 0 to ith sequences, at most j transactions, the optimal answer.
 dp[i, 1] = scan the sequences, lowest val so far minLower, remember max(high-minLower). linear to find the most profitable.
 dp(i, j) | j<i = max( dp(i-1, j),

 last trans including jth:
 go back from jth seq to mth, highs from mth to j-1 th must be < jth high,
 m+1 to jth lower must all be higher than mth low.
 dp[i, j] | j >= i = all i sequences.
 cost: O(n + slogs), s is number of increasing subsequences, at most O(nlogn). space O(s^2), at most O(n^2)
 */
public class MaxStockProfit {
    public static double solve(int[] prices, int k) {
        List<int[]> incSeqs = new ArrayList<>();
        int currBegin = 0;
        int n = prices.length;

        while (currBegin < n) {
            int i = currBegin + 1;
            while (i < n) {
                if (prices[i] >= prices[i - 1])
                    i++;
                else
                    break;
            }
            incSeqs.add(new int[]{currBegin, i-1});
            currBegin = i;
        }

        int s = incSeqs.size();
        double[][] dp = new double[s][k+1];

        for (int i = 0; i < s; i++) {
            for (int j = 1; j <= k; j++) {
                if (j == 1) {
                    int minLowIndex = -1;
                    double maxProfit = -1;
                    for (int m = 0; m < i; m++) {
                        if (minLowIndex < 0 || prices[incSeqs.get(m)[0]] < prices[minLowIndex])
                            minLowIndex = incSeqs.get(m)[0];
                        double delta = prices[incSeqs.get(m)[1]] - prices[minLowIndex];
                        if (delta > maxProfit)
                            maxProfit = delta;
                    }
                    dp[i][j] = maxProfit;
                }
                else if (j >= i+1) {
                    double maxProfit = 0;
                    for (int m = 0; m < i; m++)
                        maxProfit += prices[incSeqs.get(m)[1]] - prices[incSeqs.get(m)[0]];
                    dp[i][j] = maxProfit;
                }
                else {
                    //check transactions including incSeqs.get(i)
                    double minLow = prices[incSeqs.get(i)[0]];
                    double lastHigh = prices[incSeqs.get(i)[1]];
                    double maxProfit = dp[i-1][j];
                    for (int m = i-1; m >=0; m--) {
                        int[] seq = incSeqs.get(m);
                        if (prices[seq[0]] < minLow && prices[seq[1]] < lastHigh) {
                            double currProfit = m > 0 ? dp[m-1][j-1] : 0;
                            minLow = prices[seq[0]];
                            currProfit += lastHigh - minLow;
                            if (maxProfit < currProfit)
                                maxProfit = currProfit;
                        }
                    }
                    dp[i][j] = maxProfit;
                }
            }
        }
        return dp[s-1][k];
    }
}
