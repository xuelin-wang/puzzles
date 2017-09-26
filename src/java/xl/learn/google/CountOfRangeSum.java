package xl.learn.google;

import java.util.*;

/**
 * Created by xuelin on 9/26/17.
 * Given an integer array nums, return the number of range sums that lie in [lower, upper] inclusive.
 Range sum S(i, j) is defined as the sum of the elements in nums between indices i and j (i ≤ j), inclusive.

 Note:
 A naive algorithm of O(n2) is trivial. You MUST do better than that.

 Example:
 Given nums = [-2, 5, -1], lower = -2, upper = 2,
 Return 3.
 The three ranges are : [0, 0], [2, 2], [0, 2] and their respective sums are: -2, -1, 2.

 Solution:
 0: binary index tree: still n aquare?

1:
 dp[i] is the set of ranges sums among 0 up to element i, with two subsets:
     sets not ending with i and element ending with i.
 dp[0] = {}, {nums[0]}
 dp(i+1) = dp[i][0] union dp[i][1],  union {dp[i][1] + nums[i], nums[i]}
 still square

 2. divide and conquer
 mid = (i + j) / 2
 c[i, j] = c[i, mid-1] + c[mid, j] + seqs including mid-1 and mid elements,
 the last part takes O((j-i)^2).
 So total cost is still n^2
 */
public class CountOfRangeSum {
    //this is still square algo
    public static int countNSquare(int[] nums, int lower, int higher)
    {
        int n = nums.length;
        Set<Integer> nonAdjSums = new HashSet();
        Set<Integer> prev1 = new HashSet<Integer>();
        if (nums[0] >= lower && nums[0] <= higher) {
            prev1.add(nums[0]);
        }

        for (int i = 1; i < n; i++) {
            nonAdjSums.addAll(prev1);

            Set<Integer> curr1 = new HashSet<>();
            for (Integer k: nonAdjSums)
                curr1.add(k + nums[i]);
            curr1.add(nums[i]);
            prev1 = curr1;
        }

        nonAdjSums.addAll(prev1);

        int count = 0;
        for (Integer k: nonAdjSums) {
            if (k >= lower && k <= higher)
                count++;
        }
        return count;
    }

    private static void sums(int i, int j, int[] nums, Set<Integer> sums) {
        if (i == j) {
            sums.add(nums[i]);
            return;
        }

        int mid = (i + j)/2;
        sums(i, mid, nums, sums);
        sums(mid + 1, j, nums, sums);
        int leftsum = 0;
        for (int k = mid; k >= i; k--) {
            leftsum += nums[k];
            sums.add(leftsum);
            int rightsum = leftsum;
            for (int m = mid + 1; m < nums.length; m++) {
                rightsum += nums[m];
                sums.add(rightsum);
            }
        }
    }

    public static int count2(int[] nums, int lower, int higher) {

        int count = 0;
        for (Integer k: nums) {
            if (k >= lower && k <= higher)
                count++;
        }
        return count;
    }

    public static int count(int[] nums, int lower, int higher) {
        int n = nums.length;
        long[] sums = new long[n];
        sums[0] = nums[0];
        for (int i = 1; i< n; i++) {
            sums[i] = sums[i-1] + nums[i];
        }

        return countMergeSort(0, n, sums, nums, lower, higher);
    }

    private static int countMergeSort(int start, int end, long[] sums, int[] nums, int lower, int higher) {
        if (end - start <= 0)
            return 0;
        if (end - start == 1) {
            if (nums[start] >= lower && nums[start]<= higher)
                return 1;
            else
                return 0;
        }

        int mid = (start + end) / 2;
        int c1 = countMergeSort(start, mid, sums, nums, lower, higher);
        int c2 = countMergeSort(mid, end, sums, nums, lower, higher);
        int count = c1 + c2;
        long[] cache = new long[end-start];
        int t = mid;
        int r = 0;
        for (int i = start; i < mid; i++) {
            int j = mid;
            while (j < end && sums[j] - sums[i] < lower) j++;
            int k = mid;
            while (k < end && sums[k] - sums[i] <= higher) k++;
            count += k - j;
            while (t < end && sums[t] < sums[i]) {
                cache[r++] = sums[t];
                t++;
            }
            cache[r++] = sums[i];
        }
        System.arraycopy(cache, 0, sums, start, r);
        return count;
    }

    public static void main(String[] args) {
        int[] nums = {-2, 5, -1};
        System.out.println("count: " + count(nums, -2, 2));
    }
//    public static int count3(int[] nums, int lower, int higher) {
//        int n = nums.length;
//        int[] bit = new int[n + 1];
//        bit[0] = 0;
//        int sum = 0;
//        for (int i = 1; i <= n; i++) {
//            sum += nums[i - 1];
//            bit[i] = sum - bit[i - (i & -i)]
//        }
//
//
//    }
}
