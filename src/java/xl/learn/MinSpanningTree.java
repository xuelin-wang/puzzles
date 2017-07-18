package xl.learn;

import java.util.*;

/**
 * Created by xuelin on 7/18/17.
 * https://en.wikipedia.org/wiki/Minimum_spanning_tree#Algorithms
 */
public class MinSpanningTree {
    public static class Edge
        implements Comparable<Edge>
    {
        public int x;
        public int y;
        public double weight;

        @Override
        public int compareTo(Edge o2) {
            if (o2 == null)
                return 1;
            if (this.weight < o2.weight)
                return -1;
            else if (this.weight > o2.weight)
                return 1;
            else {
                if (this.x != o2.x)
                    return this.x - o2.x;
                else
                    return this.y - o2.y;
            }
        }
    }

    public static Collection<Edge> kruskal(PriorityQueue<Edge> edges, int n)
    {
        int[] forest = UnionFind.initForest(Collections.emptyList(), n);
        Collection<Edge> treeEdges = new ArrayList<>();
        while (true) {
            Edge edge = edges.remove();
            if (edge == null)
                break;
            int x = edge.x;
            int xRoot = UnionFind.find(forest, x);
            int y = edge.y;
            int yRoot = UnionFind.find(forest, y);
            if (xRoot == yRoot)
                continue;

            UnionFind.merge(forest, xRoot, yRoot);
            treeEdges.add(edge);
        }

        return treeEdges;
    }
}
