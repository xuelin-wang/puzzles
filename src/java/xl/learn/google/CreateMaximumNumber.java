package xl.learn.google;

/**
 * Created by xuelin on 10/1/17.
 *
 * Given two arrays of length m and n with digits 0-9 representing two numbers. Create the maximum number of length k <= m + n from digits of the two. The relative order of the digits from the same array must be preserved. Return an array of the k digits. You should try to optimize your time and space complexity.

 Example 1:
 nums1 = [3, 4, 6, 5]
 nums2 = [9, 1, 2, 5, 8, 3]
 k = 5
 return [9, 8, 6, 5, 3]

 Example 2:
 nums1 = [6, 7]
 nums2 = [6, 0, 4]
 k = 5
 return [6, 7, 6, 0, 4]

 Example 3:
 nums1 = [3, 9]
 nums2 = [8, 9]
 k = 3
 return [9, 8, 9]

 Analysis:
 we can check first m digits | k <= n, or first m - (k - n) letters otherwise in string nums1, or
     similar prefix in string nums2. find the first max digit in two prefixes, but choose which one?
 try dp:
 dp[i, j, k]: nums1's substring from ith and nums2's substring from jth for length k.
 dp[i, j, 1]: max digit in (nums1.substring(i), nums2.substring(j)).
 dp[m, j, k]: max sub nums2 of k length.
 dp(i, n, k]: max sub nums1 of k length.

 dp[i, j, k+1]: max of: dp(i+1, j, k+1), dp(k, j+1, k+1), ith digit -- dp(i+1, j, k), jth digit == dp(i, j+1, k)

 Cost: space O(m * n * k), time: similar.
 */
public class CreateMaximumNumber {
    private static int[] maxOne(int[] nums, int k) {
        if (nums.length < k)
            return null;

        int[] res = new int[k];
        int curr = 0;
        int rest = k;
        while (rest > 0) {
            int max = -1;
            int maxIndex = -1;
            for (int i = curr; nums.length - i >= rest;) {
                if (nums[i] > max) {
                    max = nums[i];
                    maxIndex = i;
                }
            }
            res[k - rest] = nums[maxIndex];
            curr = maxIndex + 1;
            rest --;
        }
        return res;
    }

    public static int[] solve(int[] nums1, int[] nums2, int k) {
        int m = nums1.length;
        int n = nums2.length;
        int[][][][] dp = new int[m+1][n+1][k+1][];

        for (int i = 0; i < m + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                dp[i][j][1] = new int[]{Math.max(nums1[i], nums2[j])};
            }
        }

        for (int j = 0; j < n + 1; j++) {
            dp[m][j][k] = maxOne(nums2, k);
        }

        for (int i = 0; i < m + 1; i++) {
            dp[i][n][k] = maxOne(nums1, k);
        }
//        dp[i, j, s]: max of: dp(i+1, j, s), dp(i, j+1, s), ith digit -- dp(i+1, j, s-1), jth digit == dp(i, j+1, s-1)

        for (int s = 2; s <= k; s++) {
            for (int i = m; i >= 0; i--) {
                for (int j = n; j >= 0; j--) {
                    int[] res1 = dp[i+1][j][s];
                    int[] res2 = dp[i][j+1][s];
                    int[] res3 = new int[s];
                    int[] tmpres3 = dp[i+1][j][s-1];

                }
            }
        }

        return null;
    }
}
