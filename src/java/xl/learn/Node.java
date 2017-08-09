package xl.learn;

public class Node {
    public int val;
    public Node left;
    public Node right;
    public Node parent;
    public Node(int val, Node l, Node r) {
        this.val = val;
        this.left = l;
        this.right = r;
    }
}
