package xl.learn;

/**
 * Created by xuelin on 7/15/17.
 *
 * https://leetcode.com/problems/maximum-average-subarray-ii/#/solution
 */
public class MaxAverage {
    public double findMaxAverageNaive(int[] nums, int k) {
        int n = nums.length;
        double max = Integer.MIN_VALUE;
        for (int i = 0; i <= n - k; i++) {
            //check array starting from i
            int sum = 0;
            for (int j = i; j < n; j++) {
                sum += nums[j];
                if (j - i + 1>= k) {
                    double avg = ((double)sum) / (j - i + 1);
                    if (max < avg)
                        max = avg;
                }
            }
        }
        return max;
    }

    public double findMaxAverage(int[] nums, int k) {
        double minVal = Integer.MAX_VALUE;
        double maxVal = Integer.MIN_VALUE;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            minVal = Math.min(minVal, nums[i]);
            maxVal = Math.max(maxVal, nums[i]);
        }
        double error = 0.000001;

        while (maxVal - minVal > error) {
            double mid = (minVal + maxVal) / 2;
            boolean checked = check(nums, k, minVal, maxVal, mid);
            if (checked) {
                minVal = mid;
            }
            else
                maxVal = mid;
        }
        return minVal;
    }

    public boolean check(int[] nums, int k, double minVal, double maxVal, double mid) {
        int n = nums.length;
        double prev = 0;
        double min_sum = 0;
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum += (nums[i] - mid);
            if (i >= k - 1) {
                if ( i >= k) {
                    prev += (nums[i - k] - mid);
                    min_sum = Math.min(prev, min_sum);
                }
                if (sum - min_sum >= 0)
                    return true;
            }
        }
        return false;
    }


    public static void main (String[] args) {
        double max = new MaxAverage().findMaxAverage(new int[]{1,12,-5,-6,50,3}, 4);
        System.out.println("max = " + max);
    }
}

