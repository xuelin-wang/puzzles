package xl.learn.google;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xuelin on 9/27/17.
 * Given n balloons, indexed from 0 to n-1. Each balloon is painted with a number on it represented by array nums. You are asked to burst all the balloons. If the you burst balloon i you will get nums[left] * nums[i] * nums[right] coins. Here left and right are adjacent indices of i. After the burst, the left and right then becomes adjacent.

 Find the maximum coins you can collect by bursting the balloons wisely.

 Note:
 (1) You may imagine nums[-1] = nums[n] = 1. They are not real therefore you can not burst them.
 (2) 0 ≤ n ≤ 500, 0 ≤ nums[i] ≤ 100

 Example:

 Given [3, 1, 5, 8]

 Return 167

 nums = [3,1,5,8] --> [3,5,8] -->   [3,8]   -->  [8]  --> []
 coins =  3*1*5      +  3*5*8    +  1*3*8      + 1*8*1   = 167

 Solution:
    f(i, j, k, m): best bursting sequence for j to k with i+1 to j-1 and k+1 to m-1 missing.

    f(i,j, j,m):    [j]  for all i,m pairs, j from i+1 to m-1
    f(i,j, j+1, m): optimal of [j, j+1] and [j+1, j].
    f(i,j, k, m): optimal of:
         f(i, j, k',k) + [k] + f(i, k'+1, k-1, m)

    return f(-1, 0, n-1, n)
    Cost: O(n^4). space: O(n^4)

    Optimization:
      Only need dp(start, end) with before start-1 and after end+1. Initial implementation(code) is overcomplex.

 */
public class BurstingBaloons {
    private static int val(int k, int[] nums) {
        if (k == -1 || k == nums.length)
            return 1;
        return nums[k];
    }
    private static int val(int i, int j, int k, int[] nums) {
        return val(i, nums) * val(j, nums) * val(k, nums);
    }
    private static void set(int[] ijkm, int val, Map<String, Integer> state) {
        state.put(toKey(ijkm), val);
    }
    private static Integer get(int[] ijkm, Map<String, Integer> state) {
        return state.get(toKey(ijkm));
    }

    public static int solveDP(int[] nums) {
        int n = nums.length;
        Map<String, Integer> state = new HashMap<>();
        for (int djk = 0; djk < n; djk++) {
            for (int j = 0; j < n; j++) {
                int k = j + djk;
                if (k > n-1)
                    break;

                for (int i = -1; i < j; i++) {
                    for (int m = k+1; m <= n; m++) {
                        int[] ijkm = {i, j, k, m};
                        if (j == k) {
                            set(ijkm, val(i, j, m, nums), state);
                        }
                        else {
                            int max = Integer.MIN_VALUE;
                            for (int s = j; s<=k; s++) {
                                int coins = 0;
                                coins += (s == j ? 0 : get(new int[]{i, j, s-1, s}, state) )
                                        + (s == k ? 0 : get(new int[]{s, s+1, k, m}, state));
                                coins += val(i, s, m, nums);
                                //val(s == j? i : (s-1), s, s == k ? m : (s + 1), nums);

                                if (coins > max)
                                    max = coins;
                            }
                            set(ijkm, max, state);
                        }
                    }
                }
            }
        }
        return get(new int[]{-1, 0, n-1, n}, state);
    }

    private static String toKey(int[] ks) {
        Preconditions.checkArgument(ks != null && ks.length == 4);

        return Joiner.on(",").join(Ints.asList(ks));
    }

    public static void main(String[] args) {
        for (int[] nums: new int[][]{
                {3, 1, 5, 8}
        }) {
            System.out.println("nums: " + Joiner.on(", ").join(Ints.asList(nums)));
            System.out.println("results: " + solveDP(nums));
        }
    }
}
