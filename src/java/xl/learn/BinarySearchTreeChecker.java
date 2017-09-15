package xl.learn;

/**
 * Created by xuelin on 9/14/17.
 */
public class BinarySearchTreeChecker {
    public static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node(int val, Node left, Node right) {
            this.left = left;
            this.right = right;
            this.val = val;
        }
    }

    private int lastVal = Integer.MIN_VALUE;

    public boolean check(Node root) {
        return checkNode(root);
    }

    private boolean checkNode(Node node) {
        assert(node != null);

        if (node.left != null) {
            if (!checkNode(node.left))
                return false;
        }

        if (lastVal > node.val)
            return false;

        lastVal = node.val;

        if (node.right != null) {
            if (!checkNode(node.right))
                return false;
        }
        return true;
    }

    public static void main(String [] args) {
        Node root = new Node(80,
                new Node(70, new Node(65, null, null), new Node(79, null, null)),
                new Node(80, null, null));
        BinarySearchTreeChecker checker = new BinarySearchTreeChecker();
        System.out.println(checker.check(root));
    }

}
