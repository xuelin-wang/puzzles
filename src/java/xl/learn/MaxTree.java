package xl.learn;

public class MaxTree {

    private static Node addNode(Node root, int i)
    {
        if (root == null) {
            return new Node(i, null, null);
        }

        Node newRoot = root;

        Node curr = root;
        Node parent = null;
        while (true) {
            if (curr == null || curr.val < i) {
                Node newNode = new Node(i, curr, null);
                if (parent != null) {
                    parent.right = newNode;
                }
                if (curr == root) {
                    newRoot = newNode;
                }
                break;
            }
            else {
                parent = curr;
                curr = curr.right;
            }
        }
        return newRoot;
    }

    public static String treeToString(Node root) {
        if (root == null)
            return "null";
        StringBuilder sb = new StringBuilder("{").append(treeToString(root.left)).append(", ")
                .append(root.val).append(", ").append(treeToString(root.right)).append("}");
        return sb.toString();
    }

    public static Node makeTree(int[] nums)
    {
        Node root = null;
        for (int i = 0; i < nums.length; i++) {
            root = addNode(root, nums[i]);
        }
        return root;
    }

    public static void main(String[] args)
    {
        for (int[] nums: new int[][]{
                new int[]{3, 2, 1, 6, 0, 5}
        }) {
            Node root = makeTree(nums);
            System.out.println(treeToString(root));
        }
    }
}
