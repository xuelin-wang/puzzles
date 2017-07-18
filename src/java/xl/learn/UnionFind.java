package xl.learn;

import com.sun.javafx.scene.control.skin.VirtualFlow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by xuelin on 7/18/17.
 * https://en.wikipedia.org/wiki/Disjoint-set_data_structure
 */
public class UnionFind {
    public static int[] getRootRank(int[] forest, int x) {
        int xRoot = x;
        int rank = 0;
        while (forest[xRoot] != xRoot) {
            xRoot = forest[xRoot];
            rank++;
        }
        return new int[]{xRoot, rank};
    }

    public static int find(int[] forest, int x)
    {
        int xRoot = x;
        List<Integer> path = new ArrayList<>();
        path.add(xRoot);
        while (forest[xRoot] != xRoot) {
            path.add(xRoot);
            xRoot = forest[xRoot];
        }

        for (int i: path) {
            forest[i] = xRoot;
        }
        return xRoot;
    }


    public static void merge(int[] forest, int x, int y)
    {
        int[] xRootRank = getRootRank(forest, x);
        int[] yRootRank = getRootRank(forest, y);
        if (xRootRank[0] == yRootRank[0])
            return;

        if (xRootRank[1] > yRootRank[1])
            forest[yRootRank[0]] = xRootRank[0];
        else if (xRootRank[1] < yRootRank[1])
            forest[xRootRank[0]] = yRootRank[0];
        else {
            boolean low = Math.random() < 0.5;
            if (low)
                forest[yRootRank[0]] = xRootRank[0];
            else
                forest[xRootRank[0]] = yRootRank[0];
        }
    }

    public static int[] initForest(Collection<int[]> relations, int n)
    {
        int[] forest = new int[n];
        for (int i = 0; i < n; i++)
            forest[i] = i;

        for (int[] rel: relations) {
            merge(forest, rel[0], rel[1]);
        }

        return forest;
    }
}
