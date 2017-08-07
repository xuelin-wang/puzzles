package xl.learn;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BFS {

    private static int findColor(int[] colors, int color) {
        for (int i = 0; i < colors.length; i++) {
            if (colors[i] == color)
                return i;
        }
        return -1;
    }

    public static int[] walk(List<Integer>[] adjLists)
    {
        int n = adjLists.length;
        int[] preds = new int[n];
        for (int i = 0; i < n; i++)
            preds[i] = -1;

        int[] colors = new int[n];
        for (int i = 0; i < n; i++) {
            colors[i] = 0;
        }

        while (true) {
            int i = findColor(colors, 1);
            if (i < 0) {
                i = findColor(colors, 0);
                colors[i] = 1;
            }
            if (i < 0)
                break;

            bfs(i, colors, preds, adjLists);
        }

        return preds;
    }

    private static void bfs(int i, int[] colors, int[] preds, List<Integer>[] adjLists) {
        List<Integer> adjList = adjLists[i];
        for (int j: adjList) {
            int color = colors[j];
            if (color == 0) {
                preds[j] = i;
                colors[j] = 1;
            }
            else if (color == 2) {
                if (preds[j] < 0)
                    preds[j] = i;
            }
        }
        colors[i] = 2;
    }
}
