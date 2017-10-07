package xl.learn.uber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by xuelin on 9/20/17.
 *
 * Given a set of candidate numbers (C) (without duplicates) and a target number (T),
 * find all unique combinations in C where the candidate numbers sums to T.

 The same repeated number may be chosen from C unlimited number of times.

 Note:
 All numbers (including target) will be positive integers.
 The solution set must not contain duplicate combinations.
 For example, given candidate set [2, 3, 6, 7] and target 7,
 A solution set is:
 [
 [7],
 [2, 2, 3]
 ]

 Solution:
dynamic programming:
 d[i] contains list of subsets which add up tos using only elements from 0 - i;
 s from 0 to t

 k from 0 to n - 1
 d[k, 0] = {[]}

 d[k, s] = d[k-1, s] union d[k, s-a[k]] + a[k]


 */
public class CombinationSum {
    private static List<List<Integer>> union(List<List<Integer>> l1, List<List<Integer>> l2) {
        List<List<Integer>> retval = new ArrayList<>();
        retval.addAll(l1);
        retval.addAll(l2);
        return retval;
    }

    private static List<List<Integer>> concat(List<List<Integer>>ls, int n) {
        if (ls.isEmpty())
            return Collections.emptyList();
        List<List<Integer>> retval = new ArrayList<>();
        for (List<Integer> l: ls) {
            List<Integer> tmp = new ArrayList<>();
            tmp.addAll(l);
            tmp.add(n);
            retval.add(tmp);
        }
        return retval;
    }

    public static List<List<Integer>> seqs(int[] ns, int t) {
        int m = ns.length;
        List<List<Integer>>[][] dp = new List[m+1][t + 1];
        List<List<Integer>> singleEmpty = Collections.singletonList(Collections.emptyList());
        for (int i = 0; i < m + 1; i++) {
            dp[i][0] = singleEmpty;
        }
        for (int s = 1; s < t + 1; s++) {
            dp[0][s] = Collections.emptyList();
        }

        for (int s = 1; s < t + 1; s++) {
            for (int i = 1; i < m + 1; i++) {
                if (s < ns[i - 1])
                    dp[i][s] = dp[i-1][s];
                else
                    dp[i][s] = union(dp[i-1][s], concat(dp[i][s-ns[i - 1]], ns[i - 1]));
            }
        }
        return dp[m][t];
    }

    public static void main(String[] args) {
        List<List<Integer>> answer = seqs(new int[]{2, 3,6,7}, 7);
        for (List<Integer> seq: answer) {
            System.out.print("[");
            System.out.print(seq.stream().map(x -> String.valueOf(x)).collect(Collectors.joining(", ")));
            System.out.println("]");
        }
    }
}
