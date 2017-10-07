package xl.learn;

/**
 * Created by xuelin on 10/4/17.
 */
public class MaxSubarr {
    public static int maxSum(int[] arr) {
        int minPrefix = 0;
        int sum = 0;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            if (sum - minPrefix > max)
                max = sum - minPrefix;
            int thisPrefix = Math.min(0, sum);
            if (minPrefix > thisPrefix)
                minPrefix = thisPrefix;
        }
        return max;
    }
}
