package xl.learn.google;

import java.util.PriorityQueue;

/**
 * Created by xuelin on 9/25/17.
 *
 * Nearly every one have used the Multiplication Table. But could you find out the k-th smallest number quickly from the multiplication table?

 Given the height m and the length n of a m * n Multiplication Table, and a positive integer k, you need to return the k-th smallest number in this table.

 Example 1:
 Input: m = 3, n = 3, k = 5
 Output:
 Explanation:
 The Multiplication Table:
 1	2	3
 2	4	6
 3	6	9

 The 5-th smallest number is 3 (1, 2, 2, 3, 3).
 Example 2:
 Input: m = 2, n = 3, k = 6
 Output:
 Explanation:
 The Multiplication Table:
 1	2	3
 2	4	6

 The 6-th smallest number is 6 (1, 2, 2, 3, 4, 6).
 Note:
 The m and n will be in the range [1, 30000].
 The k will be in the range [1, m * n]

 Solution
 Maintain a heap with one elements from each row. Remove the min and insert next element from same row of min.


 Naive: (assuming one time query only)
 Use devide and conquer. Average O(mn), constructing the multiplication table also takes O(mn).
 */
public class KthMultiplicationTable {
    public static class Node{
        int val;
        int root;
        public Node(int val, int root) {
            this.val = val;
            this.root = root;
        }
    }
    public static int kth(int m, int n, int k) {
        PriorityQueue<Node> pq = new PriorityQueue<>(m);
        for (int i = 0; i < m; i++) {
            pq.offer(new Node(i, i));
        }

        Node min = null;
        for (int j = 0; j < k; j++) {
            min = pq.poll();
            int newVal = min.val + min.root;
            if (newVal <= n * min.root) {
                pq.offer(new Node(newVal, min.root));
            }
        }
        return min.val;
    }
}
