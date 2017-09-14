package xl.learn;

import com.google.common.base.Joiner;
import com.google.common.primitives.Ints;

import java.util.Arrays;

/**
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

 Solution:
 maximize the first digit:
   earliest max to ensure at least k-1 digits left at tails of both.
   Keep all max from ranges to [i to end] for all i for both sequence


 Forgot to respect original order in sequence:
 O(n) to use bucket sort to get numbers map for each num seq, {d -> count}
 then loop through using merge-like operaration.
 total time cost is O(n).
 total spece cost is the two maps: O(k) for k = 10, plus result sequence k.

 * Created by xuelin on 9/14/17.
 */
public class MaxNumber {

    private static int[] computeMaxIndexes(int[] ns) {
        int[] maxes = new int[ns.length];

        for (int i = ns.length - 1; i >= 0; i--) {
            if (i == ns.length - 1) {
                maxes[i] = i;
            }
            else {
                if (ns[i] > ns[maxes[i + 1]]) {
                    maxes[i] = i;
                }
                else {
                    maxes[i] = maxes[i + 1];
                }
            }
        }
        return maxes;
    }

    private static int getEarlistMax(int[] ns, int from, int to, boolean rangeEnd, int[] maxIndexes) {
        if (rangeEnd)
            return maxIndexes[from];

        int currMax = Integer.MIN_VALUE;
        int currIndex = -1;
        for (int k = from; k < to; k++) {
            if (ns[k] > currMax) {
                currIndex = k;
                currMax = ns[k];
            }
        }
        return currIndex;
    }


    private static int[] max(int[] n1, int[] n2, int k) {
        int[] result = new int[k];
        int len1 = n1.length;
        int len2 = n2.length;
        int index1 = 0;
        int index2 = 0;
        int[] maxIndexes1 = computeMaxIndexes(n1);
        int[] maxIndexes2 = computeMaxIndexes(n2);
        for (int i = 0; i < k; i++) {
            int restLen1 = len1 - index1;
            int restLen2 = len2 - index2;
            int remaining = k - i;
            int end1 = restLen2 >= remaining ? len1 : (len1 + 1 - (remaining - restLen2));
            int end2 = restLen1 >= remaining ? len2 : (len2 + 1- (remaining - restLen1));
            int i1 = restLen1 <= 0 ? -1 :
                    getEarlistMax(n1, index1, end1, end1 == len1, maxIndexes1);
            int i2 = restLen2 <= 0 ? -1 : getEarlistMax(n2, index2, end2, end2 == len2, maxIndexes2);

            if (i2 < 0 || i1 >= 0 && n1[i1] >= n2[i2]) {
                result[i] = n1[i1];
                index1 = i1 + 1;
            }
            else {
                result[i] = n2[i2];
                index2 = i2 + 1;
            }
        }
        return result;
    }


//    not respecting original order
//    public static int[] max(int[] n1, int[] n2, int k) {
//        assert(n1.length > 0 && n2.length > 0 && k < n1.length + n2.length);
//
//        int[] countMap1 = new int[10];
//        Arrays.fill(countMap1, 0);
//        int[] countMap2 = new int[10];
//        Arrays.fill(countMap2, 0);
//
//        for (int i = 0; i < n1.length; i++) {
//            int di = n1[i];
//            countMap1[di]++;
//        }
//
//        for (int i = 0; i < n2.length; i++) {
//            int di = n2[i];
//            countMap2[di]++;
//        }
//
//        int i1 = 9;
//        int i2 = 9;
//        int[] result = new int[k];
//        int remaining = k;
//        while (remaining > 0) {
//            if (i1 >= 0 && countMap1[i1] == 0) {
//                i1--;
//                continue;
//            }
//            if (i2 >= 0 && countMap2[i2] == 0) {
//                i2--;
//                continue;
//            }
//
//            if (i1 >= i2) {
//                result[k - remaining] = i1;
//                countMap1[i1]--;
//                remaining--;
//            }
//            else {
//                result[k - remaining] = i2;
//                remaining--;
//                countMap2[i2]--;
//            }
//        }
//
//        return result;
//    }
//
    public static void main(String[] args) {
        for (Object[] params: new Object[][]{
                new Object[]{
                        new int[]{3, 4, 6, 5},
                        new int[]{9, 1, 2, 5, 8, 3},
                        5,
                },
                new Object[]{
                        new int[]{6, 7},
                        new int[]{6, 0, 4},
                        5,
                },
                new Object[]{
                        new int[]{3, 9},
                        new int[]{8, 9},
                        3,
                },
        }) {
            int[] n1 = (int[])params[0];
            int[] n2 = (int[])params[1];
            int k = (Integer)params[2];

            System.out.println("n1: " + Joiner.on(", ").join(Ints.asList(n1)) + ", n2: " + Joiner.on(", ").join(Ints.asList(n2)) + ", k: " + k);
            System.out.println("results is: " + Joiner.on(", ").join(Ints.asList(max(n1, n2, k))));
        }
    }
}
