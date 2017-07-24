package xl.learn;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuelin on 5/10/17.
 *
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

 Naive: O(n2)

 scan from end:
 For each item, count how many items less than half of it in the postfix.
 add accum with double that.
 How can we construct a "balanced" tree to speed up this:
 nlogn?

 BST can be unbalanced, worst to n square

 BIT: (binary indexed tree)
 https://www.topcoder.com/community/data-science/data-science-tutorials/binary-indexed-trees/
 */
public class ReversePair {
    private static class Node {
        public int val;
        public int biggerCount;
        public Node left;
        public Node right;
        public Node(int val) {
            this(val, 1);
        }
        public Node(int val, int biggerCount) {
            this.val = val;
            this.biggerCount = biggerCount;
            left = null;
            right = null;
        }
    }

    /*
    binary search tree
     */
    private static int findBST(int m, Node node) {
        if (node == null)
            return 0;

        if (m == node.val)
            return node.biggerCount;

        if (m < node.val) {
            return node.biggerCount + findBST(m, node.left);
        }
        else {
            return findBST(m, node.right);
        }
    }

    private static void insertBST(int m, Node root) {
        Node node = root;
        while (root != null) {
            if (m == root.val) {
                root.biggerCount++;
            }
            else if (m > root.val) {
                root.biggerCount ++;
                if (root.right == null) {
                    Node newNode = new Node(m);
                    root.right = newNode;
                }
                else {
                    insertBST(m, root.right);
                }
            }
            else {
                if (root.left == null) {
                    Node newNode = new Node(m);
                    root.left = newNode;
                }
                else {
                    insertBST(m, root.left);
                }
            }
        }
    }

    public static int findReverseBST(int[] nums) {
        Node root = null;
        int total = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i == 0) {
                root = new Node(nums[i]);
                continue;
            }
            total += findBST(2 * nums[i] + 1, root);
            insertBST(nums[i], root);
        }
        return total;
    }

//    private static void updateBIT(int index, int val, int [] bit) {
//    }
//    private int findBIT(int m, int index, int[] bit) {
//    }
//

}
