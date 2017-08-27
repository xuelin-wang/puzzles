package xl.learn;

import com.google.common.base.Joiner;
import com.google.common.primitives.Ints;

/**
 * Created by xuelin on 8/27/17.
 *
 * Given two integers n and k, you need to construct a list which contains n different positive integers ranging from 1 to n
 * and obeys the following requirement:
 Suppose this list is [a1, a2, a3, ... , an], then the list [|a1 - a2|, |a2 - a3|, |a3 - a4|, ... , |an-1 - an|]
 has exactly k distinct integers.

 If there are multiple answers, print any of them.

 Example 1:
 Input: n = 3, k = 1
 Output: [1, 2, 3]
 Explanation: The [1, 2, 3] has three different positive integers ranging from 1 to 3, and the [1, 1]
 has exactly 1 distinct integer: 1.
 Example 2:
 Input: n = 3, k = 2
 Output: [1, 3, 2]
 Explanation: The [1, 3, 2] has three different positive integers ranging from 1 to 3, and the [2, 1]
 has exactly 2 distinct integers: 1 and 2.
 Note:
 The n and k are in the range 1 <= k < n <= 104.
 */
public class BeautifulArrangement {
    /**
     * reverse tail k times
     * @param n
     * @param k
     * @return
     */
    public static int[] arrange(int n, int k) {
        int[] r = new int[n];
        for (int i = 0; i < n; i++)
            r[i] = i + 1;

        for (int i = 1; i < k; i++) {
            int m = i + n - 1;
            for (int j = i; j <= i + (n - 1 - i + 1) / 2 && j < n; j++) {
                int tmp = r[j];
                r[j] = r[m - j];
                r[m - j] = tmp;
            }
        }

        return r;
    }

    public static void main(String[] args) {
        for (int[] nk: new int[][]{
                new int[]{7, 3}
        }) {
            System.out.println("n: " + nk[0] + ", k: " + nk[1]);
            System.out.println("solution: " + Joiner.on(", ").join(Ints.asList(arrange(nk[0], nk[1]))));
        }
    }
}
