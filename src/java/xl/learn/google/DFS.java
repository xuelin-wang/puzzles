package xl.learn.google;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by xuelin on 9/23/17.
 */
public class DFS {
    public static class Node {
        int val;
        List<Node> children;
        public Node(int val, List<Node> children) {
            this.val = val;
            this.children = children;
        }


    }

    public static class State {
        int time = 0;
        Map<Integer, Integer> ds = new HashMap<>();
        Map<Integer, Integer> fs = new HashMap<>();
        Map<Integer, Integer> pi = new HashMap<>();
        Map<Integer, Integer> colors = new HashMap<>();


        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("State time: " + time +"\n");
            sb.append("     discovery times: \n");
            sb.append(ds.entrySet().stream().map(e -> "" + e.getKey()  + ": " + e.getValue()).collect(Collectors.joining(", ")) );
            sb.append("\n     finish times: \n");
            sb.append(fs.entrySet().stream().map(e -> "" + e.getKey()  + ": " + e.getValue()).collect(Collectors.joining(", ")) );
            sb.append("\n     pai: \n");
            sb.append(pi.entrySet().stream().map(e -> "" + e.getKey()  + ": " + e.getValue() ).collect(Collectors.joining(", ")) );
            sb.append("\n");
            return sb.toString();
        }

    }


    private static Node  createGraph(int[] arr) {
        Map<Integer, Node > nodes = new HashMap<>();

        for (int i: arr) {
            int p = i / 10;
            Node  node = new Node (i, new ArrayList<>());
            nodes.put(i, node);
            if (i != 0) {
                Node  pnode = nodes.get(p);
                pnode.children.add(node);
            }
        }

        return nodes.get(0);
    }

    public static void dfs(Node root, State state) {

        //should check if we have white nodes left, but here only start with node and assume all done.

        state.time++;
        state.ds.put(root.val, state.time);
        state.colors.put(root.val, 1); //gray
        for (Node child: root.children) {
            Integer color = state.colors.get(child);
            if (color == null || color.intValue() == 0) { //white
                state.pi.put(child.val, root.val);
                dfs(child, state);
            }
        }

        state.time++;
        state.fs.put(root.val, state.time);
        state.colors.put(root.val, 2);
    }

    public static void main(String[] args) {
        int[] nums = new int[]{0, 1, 2, 3, 4, 11, 12, 13, 31, 32, 33, 321, 3211, 41};
        Node  root = createGraph(nums);

        State state = new State();
        dfs(root, state);

        System.out.println(state.toString());
    }

}
