package xl.learn.google;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by xuelin on 9/29/17.
 * There is a garden with N slots. In each slot, there is a flower. The N flowers will bloom one by one in N days. In each day, there will be exactly one flower blooming and it will be in the status of blooming since then.

 Given an array flowers consists of number from 1 to N. Each number in the array represents the place where the flower will open in that day.

 For example, flowers[i] = x means that the unique flower that blooms at day i will be at position x, where i and x will be in the range from 1 to N.

 Also given an integer k, you need to output in which day there exists two flowers in the status of blooming, and also the number of flowers between them is k and these flowers are not blooming.

 If there isn't such day, output -1.

 Example 1:
 Input:
 flowers: [1,3,2]
 k: 1
 Output: 2
 Explanation: In the second day, the first and the third flower have become blooming.
 Example 2:
 Input:
 flowers: [1,2,3]
 k: 1
 Output: -1
 Note:
 The given array will be in the range [1, 20000].

 Solution:
 Sorted bloomed indices:
 maintain sorted bloomed indices.
 For ith day, check the new index's higher and lower neibough see if diff = k.
 Cost: Time: O(nlogn). Space: O(n)

 minqueue:
 for k contigous block in flower bed, if it's empty slots, then
 the min bloom date must be bigger than left/right neighbors of the window.
 Cost: O(n), space O(k)

 */
public class KEmptySlot {
    public static class MinQueue<E extends Comparable<E>> extends ArrayDeque<E> {
        Deque<E> mins;
        public MinQueue() {
            mins = new ArrayDeque<>();
        }

        @Override
        public void addLast(E x) {
            super.addLast(x);
            while (mins.peekLast() != null && x.compareTo(mins.peekLast()) < 0)
                    mins.pollLast();
            mins.addLast(x);
        }

        @Override
        public E pollFirst() {
            E x=super.pollFirst();
            if (x == mins.peekFirst())
                mins.pollFirst();
            return x;
        }

        public E min() {
            return mins.peekFirst();
        }
    }

    public static int kEmptySlot(int[] flowers, int k) {
        MinQueue<Integer> minQueue = new MinQueue<>();
        for (int i = 0; i < flowers.length; i++) {
            if (i >= k-1) {
                minQueue.pollFirst();
            }

            minQueue.addLast(flowers[i]);

            if (i >= k-1) {
                if (i >= k && i < flowers.length-1) {
                    int min = minQueue.min();
                    if (min > flowers[i-k] && min > flowers[i+1]) {
                        return Math.max(flowers[i-k], flowers[i+1]);
                    }
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] flowers;
        int k;

        flowers = new int[]{1,3,2};
        k = 1;
        System.out.println(kEmptySlot(flowers, k));

        flowers = new int[]{1,2,3};
        k = 1;
        System.out.println(kEmptySlot(flowers, k));

//        MinQueue<Integer> minQueue = new MinQueue<>();
//        for (int[] nums: new int[][]{
//                {1, 3, 6, 2, 4, 8}
//        }) {
//            for (int i: nums) {
//                minQueue.addLast(i);
//            }
//            while (minQueue.min() != null) {
//                System.out.println(minQueue.min());
//                minQueue.pollFirst();
//            }
//        }
    }
}
