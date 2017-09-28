package xl.learn.google;

import com.google.common.base.Joiner;
import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by xuelin on 9/27/17.
 * Given an array nums, we call (i, j) an important reverse pair if i < j and nums[i] > 2*nums[j].

 You need to return the number of important reverse pairs in the given array.

 Example1:

 Input: [1,3,2,3,1]
 Output: 2
 Example2:

 Input: [2,4,3,5,1]
 Output: 3
 Note:
 The length of the given array will not exceed 50,000.
 All the numbers in the input array are in the range of 32-bit integer.

 Solution:
 BST:
 scanning from beginning, construct Binary Search Tree along the way. Each node contains count of nodes greater than or equal to val of the node
 in the subtree of this node as root.
 For each a[i], search 2*a[i] in the BST and can find number of nodes bigger than that.
cost: O(nlogn), worst O(n^2) when tree degressed into chain. Space: O(n)

BIT:
 f(i) is the count of elements before i which >= a[i].
 Idea is similar to BST, but note the key here:
     sort array a. then when searching a[i] in the sorted array, assuming is j.
     The tricky idea is that in the BIT, when scanning i, BIT stores information about items greater than sortedNum[index] so far.

 MergeSort and count:
   O(nLogn).

 Naive:
 for each i > 0, find reverse pairs(k, i) such that k<i and a[k] > 2*a[i]
 Cost is: O(N^2)
 */

public class ReversePairs {
    private static void update(int[] bit, int index, int delta) {
        int i = index;
        while (i > 0) {
            bit[i] += delta;
            i -= i & -i;
        }
    }

    private static int query(int[] bit, int index) {
        int val = 0;
        int i = index;
        while (i < bit.length) {
            val += bit[i];
            i += i & -i;
        }
        return val;
    }

    public static int solveByBIT(List<Integer> nums) {
        List<Integer> numsCopy = new ArrayList<>();
        numsCopy.addAll(nums);
        Collections.sort(numsCopy);

        int n = nums.size();
        int[] bit = new int[n + 1];

        int count = 0;
        for (int i = 0; i < n; i++) {
            int j = Collections.binarySearch(numsCopy, 2 * nums.get(i) + 1);
            if (j < 0)
                j = - j - 1;
            count += query(bit, j + 1);

            j = Collections.binarySearch(numsCopy, nums.get(i));
            if (j < 0)
                j = -j - 1;
            update(bit, j + 1, 1);
        }

        return count;
    }

    public static void main(String[] args) {
        for (int[] arr: new int[][]{
                {1,3,2,3,1},
                {2,4,3,5,1}
        }) {
            List<Integer> list = Ints.asList(arr);
            System.out.println("arr: " + Joiner.on(", ").join(list));
            System.out.println("reverse pair count: " + solveByBIT(list));
        }
    }
}
