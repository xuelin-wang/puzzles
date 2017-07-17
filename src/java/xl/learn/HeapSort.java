package xl.learn;

import com.google.common.base.Joiner;

import java.util.Arrays;
import java.util.Stack;

/**
 * Created by xuelin on 7/17/17.
 */
public class HeapSort {
    private static int parent(int index) {
        return (int)Math.floor((double)(index - 1) / 2.0);
    }
    private static int left(int index) {
        return 2 * index + 1;
    }
    private static int right(int index) {
        return 2 * index + 2;
    }
    private static void swapVal(double nums[], int a, int b) {
        double tmp = nums[a];
        nums[a] = nums[b];
        nums[b] = tmp;
    }

    public static void siftDown(double[] nums, int index, int end)
    {
        int l = left(index);
        if (l > end)
            return;

        int root = index;
        while (left(root) <= end) {
            int swap = root;
            int c = left(root);
            if (nums[c] < nums[root])
                swap = c;
            if (c + 1 <= end && nums[c+1] < nums[swap])
                swap = c + 1;
            if (swap == root)
                return;
            swapVal(nums, swap, root);
            root = swap;
        }
    }

    public static void heapify(double[] nums)
    {
        int end = nums.length - 1;
        for (int start = parent(end);  start >= 0; start--) {
            siftDown(nums, start, end);
        }
    }

    public static void sort(double[] nums) {
        heapify(nums);
        int end = nums.length - 1;

        while (end > 0) {
            swapVal(nums, 0, end);
            end--;
            siftDown(nums, 0, end);
        }
    }

    public static void output(double[] nums) {
        System.out.println();
        for (double num: nums) {
            System.out.print("" + num + ", ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        double[] nums = new double[]{1, -1, 2, -2, 3, -4, -5, 7, -100, 9, 10};
        sort(nums);
        output(nums);
    }
}
