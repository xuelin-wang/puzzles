package xl.learn.uber;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by xuelin on 9/17/17.
 *
 * List all paths that sum to a given target value.
 * The path does not need to start or end at the root or a leaf,
 * but it must go downwards (traveling only from parent nodes to child nodes).
 *
 * target value is t
 *
 * Naive solution:
 * starting from root, decending down all branches,
 * keep state:
 * path_from_root, {<start_index, sum>}, {t sum paths}
 *
 * space is sum(h * c(h)) < sum(h * 2^h)
 * time: similar.
 */
public class SumTargetPaths {

    public static class Node {
        int val;
        private Node left;
        private Node right;
        public Node(int val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        public int getVal() {
            return val;
        }

        public String toString() {
            return "[" + val + ", " + left + ", " + right + "]";
        }
    }

    private static List<List<Node>> getEmptyPrefix() {
        List<List<Node>> prefixes = new ArrayList<>();
        prefixes.add(new ArrayList<>());
        return prefixes;
    }

    public static Collection<List<Node>> findPaths(Node root, int t) {
        return findPaths(root, t, getEmptyPrefix(), t);
    }

    public static Collection<List<Node>> findPaths(Node node, int s, List<List<Node>> prefixes, int t) {
        if (node == null) {
            return Collections.emptyList();
        }

        List<List<Node>> extendedPaths = new ArrayList<>();
        for (List<Node> prefix: prefixes) {
            List<Node> path = new ArrayList<>();
            path.addAll(prefix);
            path.add(node);
            extendedPaths.add(path);
        }

        List<List<Node>> retval = new ArrayList<>();
        int val = node.val;

        if (s == val) {
            retval.addAll(extendedPaths);
        }

        if (node.left != null) {
            retval.addAll(findPaths(node.left, s - val, extendedPaths, t));
            retval.addAll(findPaths(node.left, t, getEmptyPrefix(), t));
        }

        if (node.right != null) {
            retval.addAll(findPaths(node.right, s - val, extendedPaths, t));
            retval.addAll(findPaths(node.right, t, getEmptyPrefix(), t));
        }

        return retval;
    }

    public static void main(String[] args) {
        Node root = new Node(1,
                new Node(3,
                        new Node(2, null, null),
                        new Node(1, new Node(1,null, null), null)
                ),
                new Node(-1,
                        new Node(4, new Node(1, null, null), new Node(2, null, null)),
                        new Node(5, null, new Node(6, null, null))
                )
        );

        Collection<List<Node>> paths = findPaths(root, 5);
        for (List<Node> path: paths) {
            System.out.println(
                    path.stream().map(x -> String.valueOf(x.val)).collect(Collectors.joining(", ")));

        }
    }
}
