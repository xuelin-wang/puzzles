package xl.learn.google;

/**
 * Created by xuelin on 9/28/17.
 * Given a sorted positive integer array nums and an integer n, add/patch elements to the array such that any number in range [1, n] inclusive can be formed by the sum of some elements in the array. Return the minimum number of patches required.

 Example 1:
 nums = [1, 3], n = 6
 Return 1.

 Combinations of nums are [1], [3], [1,3], which form possible sums of: 1, 3, 4.
 Now if we add/patch 2 to nums, the combinations are: [1], [2], [3], [1,3], [2,3], [1,2,3].
 Possible sums are 1, 2, 3, 4, 5, 6, which now covers the range [1, 6].
 So we only need 1 patch.

 Example 2:
 nums = [1, 5, 10], n = 20
 Return 2.
 The two patches can be [2, 4].

 Example 3:
 nums = [1, 2, 2], n = 5
 Return 0.

 Solution:

 Best:
 missing range [1,miss) = [1,1): empty
 for each item in nums:
   if nums[i] < miss, [1, miss) = [1, miss+nums[i]), i++,continue
   else: patch miss, count++, [1, miss)<-[1, miss+miss), continue;
 cost:O(m + logn), space O(1)

 O(n^2)
 sums[n+1] record if sums contains j, then sums[j] = 1
 for each i from 0 to nums.length - 1:
   add nums[i] to existing numbers in sums and record the result in sums too.
 Must add min missing number in sums.
 Cost: O(n^2) + O(nk). k is the number of patches. so O(n^2). space O(n)
 Optimization:
   Use binary search tree to keep track of sums.



 {1..n} - dp(n-1) = missing numbers. this takes at most O(n^2), space is O(n).
 say missing indexes in b[0..k-1]
 for i from 0 to k-1
   find missing if any, for each of the missing, continue recursion until all numbers filled.

 Naive:
 check if k can be a sum:
   search k, got index j, if j>=0, yes. else, check 2 sums, for each of the left item i, check k-i. If not, check 3 sums...
 */
public class PatchingArray {

    public static int solve(int[] nums, int n) {
        int miss = 1;
        int count = 0;
        for (int i = 0; i < nums.length; ) {
            if (nums[i] <= miss) {
                miss = miss + nums[i];
                i++;
            }
            else {
                count++;
                miss = miss + miss;
            }
            if (miss > n)
                break;
        }
        return count;
    }

    public static int solve1(int[] nums, int n) {
        int[] sums = new int[n+1];
        for (int i = 0; i < nums.length; i++) {
            for (int j = n; j >= 1; j--) {
                if (sums[j] > 0 && j + nums[i] <= n)
                    sums[j + nums[i]] = 1;
            }
            if (nums[i] <= n)
                sums[nums[i]] = 1;
        }

        //min must be added,
        boolean done = false;
        int count = 0;
        while (true) {
            int min = -1;
            for (int j = 1; j <= n; j++) {
                if (sums[j] == 0) {
                    min = j;
                    break;
                }
            }
            if (min < 0)
                break;
            for (int j = n; j >= 0; j--) {
                if (sums[j] > 0 && j + min <= n)
                    sums[j + min] = 1;
            }
            sums[min] = 1;
            count++;
        }
        return count;
    }

    public static void main(String[] args) {
        int[] nums;
        int n;

        nums = new int[]{1,3};
        n = 6;
        System.out.println(solve(nums, n));

        nums = new int[]{1,5, 10};
        n = 20;
        System.out.println(solve(nums, n));

        nums = new int[]{1,2,2};
        n = 5;
        System.out.println(solve(nums, n));

    }
}
