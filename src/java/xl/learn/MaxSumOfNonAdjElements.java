package xl.learn;

/**
 * Created by xuelin on 9/14/17.
 */
public class MaxSumOfNonAdjElements {
    public static int maxSum(int[] arr) {
        // f(i) = max(f(i-1), sum of ith to 0 ~ i-2th
        int currSum = Integer.MIN_VALUE;
        for (int m = 2; m < arr.length; m++) {
            currSum = max(arr, currSum, m);
        }
        return currSum;
    }

    private static int max(int[] arr, int prevMax, int m) {
        int max = prevMax;
        for (int i = 0; i <= m - 2; i++) {
            int sum = arr[m] + arr[i];
            if (sum > max) {
                max = sum;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1, 0, 3, 9, 2};
        System.out.println(maxSum(arr));
    }
}
