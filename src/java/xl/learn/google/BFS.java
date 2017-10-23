package xl.learn.google;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by xuelin on 9/23/17.
 * practive breadth first searh
 */
public class BFS {
    public static class Node<T> {
        T val;
        List<Node<T>> children;
        public Node(T val, List<Node<T>> children) {
            this.val = val;
            this.children = children;
        }

        @Override
        public int hashCode() {
            int code = val.hashCode();
            for (Node node: children) {
                code &= code;
            }
            return code;
        }
    }

    public interface MutableSearchState {

    }

    public static class SearchState<T> implements MutableSearchState{
        int time;
        final Map<Node<T>, Integer> times;
        final Map<Node<T>, Node<T>> pai;
        public SearchState() {
            time = 0;
            this.times = new HashMap<>();
            this.pai = new HashMap<>();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("State time: " + time +"\n");
            sb.append("     times: \n");
            sb.append(times.entrySet().stream().map(e -> "" + e.getKey().val + ": " + e.getValue()).collect(Collectors.joining(", ")) );
            sb.append("\n     pai: \n");
            sb.append(pai.entrySet().stream().map(e -> "" + e.getKey().val + ": " + e.getValue().val).collect(Collectors.joining(", ")) );
            sb.append("\n");
            return sb.toString();
        }

    }

    public static void bfs(Node<Integer> root) {
        int time = 0;
        Map<Node<Integer>, Integer> colorMap = new HashMap<>();
        LinkedList<Node<Integer>> queue = new LinkedList<>();
        queue.add(root);
        colorMap.put(root, 1); //0: white, 1: gray, 2: black
        final SearchState<Integer> state = new SearchState<>();
        while (!queue.isEmpty()) {
            Node<Integer> node = queue.remove();
            for (Node<Integer> child: node.children) {
                Integer color = colorMap.get(child);
                if (color == null || color.intValue() == 0) {
                    state.pai.put(child, node);
                    colorMap.put(child, 1);
                    queue.add(child);
                }
            }
            state.times.put(node, state.time);
            state.time++;
            colorMap.put(node, 2);
        }
        System.out.println(state.toString());
    }

    private static Node<Integer> createGraph(int[] arr) {
        Map<Integer, Node<Integer>> nodes = new HashMap<>();

        for (int i: arr) {
            int p = i / 10;
            Node<Integer> node = new Node<Integer>(i, new ArrayList<Node<Integer>>());
            nodes.put(i, node);
            if (i != 0) {
                Node<Integer> pnode = nodes.get(p);
                pnode.children.add(node);
            }
        }

        return nodes.get(0);
    }
    public static void main(String[] args) {
        int[] nums = new int[]{0, 1, 2, 3, 4, 11, 12, 13, 31, 32, 33, 321, 3211, 41};
        Node<Integer> root = createGraph(nums);

        bfs(root);
    }
}
