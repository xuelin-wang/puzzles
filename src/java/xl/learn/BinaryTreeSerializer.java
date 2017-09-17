package xl.learn;

import java.util.Random;

/**
 * Created by xuelin on 9/17/17.
 *
 * Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.

 Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your serialization/deserialization algorithm should work. You just need to ensure that a binary tree can be serialized to a string and this string can be deserialized to the original tree structure.

 For example, you may serialize the following tree

 1
 / \
 2   3
 / \
 4   5
 as "[1,2,3,null,null,4,5]", just the same as how LeetCode OJ serializes a binary tree. You do not necessarily need to follow this format, so please be creative and come up with different approaches yourself.
 Note: Do not use class member/global/static variables to store states. Your serialize and deserialize algorithms should be stateless.

 */
public class BinaryTreeSerializer {
    public static class Node {
        Node left;
        Node right;
        int val;
        public Node(int val, Node left, Node right) {
            this.left = left;
            this.right = right;
            this.val = val;
        }
        public String toString() {
            return "{" + val + ", " + left + ", " + right + "}";
        }
    }

    /**
     * Assume all node has an int value.
     * node v, l, r: [v[l][r]]
     * nil as [].
     * @param root
     * @return
     */
    public static String serialize(Node node) {
        if (node == null)
            return "[";
//        return "[]";

        return "[" + node.val + serialize(node.left) + serialize(node.right);
//        return "[" + node.val + serialize(node.left) + serialize(node.right) + "]";
    }

    private static class ParseResult {
        private Node node;
        private int index;
        public ParseResult(Node node, int index) {
            this.node = node;
            this.index = index;
        }
    }

    private static ParseResult deserialize(String str, int from) {
        if (str.length() <= from || str.charAt(from) != '[')
            throw new RuntimeException("Expecting [ at " + from + ", but got: " + (str.length() <= from ? "empty string" : str.charAt(from)));

        if (str.length() <= from + 2 || str.charAt(from+1) == '[')
            return new ParseResult(null, from + 1);
//        if (str.charAt(from + 1) == ']')
//            return new ParseResult(null, from + 2);

        int leftStart = str.indexOf('[', from + 1);
        int val = Integer.parseInt(str.substring(from + 1, leftStart));
        ParseResult leftResult = deserialize(str, leftStart);
        ParseResult rightResult = deserialize(str, leftResult.index);
//        if (str.charAt(rightResult.index) != ']')
//            throw new RuntimeException("Expecting ]");

        Node node = new Node(val,  leftResult.node, rightResult.node);
//        if (str.charAt(rightResult.index) != ']')
//            throw new RuntimeException("Expecting ] but got: " + str.charAt(rightResult.index) + " at " + rightResult.index);
        return new ParseResult(node, rightResult.index);
//        return new ParseResult(node, rightResult.index + 1);
    }
    public static Node deserialize(String str) {
        ParseResult res = deserialize(str, 0);
        return res.node;
    }

    public static boolean sameTree(Node t1, Node t2) {
        if (t1 == null)
            return t2 == null;
        if (t2 == null)
            return false;
        return t1.val == t2.val && sameTree(t1.left, t2.left) && sameTree(t1.right, t2.right);
    }

    public static Node genRandomTree(Random rand, boolean canBeNull) {
        int val = rand.nextInt();
        System.out.println("rand: " + val);
        if (canBeNull) {
            if (val % 2 == 0)
                return null;
        }

        Node left = genRandomTree(rand, true);
        Node right = genRandomTree(rand, true);
        return new Node(val%3, left, right);
    }

    public static void main(String[] args) {
        Node root = genRandomTree(new Random(), false);
        System.out.println("Tree: " + root);
        String str = serialize(root);
        System.out.println("serialized: " + str);
        Node des = deserialize(str);
        System.out.println("Same tree: " + sameTree(root, des));
        System.out.println("Deserialized tree: " + des );
        String str2 = serialize(des);
        System.out.println("same string: " + (str.equals(str2)));
    }
}
