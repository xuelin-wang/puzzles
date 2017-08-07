package xl.learn;

public class TwoSumBST {
    private static boolean find(Node root, int num) {
        Node curr = root;
        while (curr != null) {
            if (curr.val == num)
                return true;
            if (num < curr.val)
                return find(curr.left, num);
            else
                return find(curr.right, num);
        }
        return false;
    }

    public static boolean find2Sum(Node root, Node searchNode, int sum)
    {
        Node curr = searchNode;
        while (curr != null) {
            int currVal = curr.val;
            int delta = sum - currVal;
            if (find(root, delta)) {
                return true;
            }

            return find2Sum(root, 2 * currVal > sum ? searchNode.left : searchNode.right, sum);
        }
        return false;
    }
}
