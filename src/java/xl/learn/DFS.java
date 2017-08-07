package xl.learn;

import java.util.List;

public class DFS {

    private static int findColor(int[] colors, int color) {
        for (int i = 0; i < colors.length; i++) {
            if (colors[i] == color)
                return i;
        }
        return -1;
    }
    public static int[] walk(List<Integer>[] adjLists) {
        int n = adjLists.length;
        int[] colors = new int[n];
        for (int i = 0; i < n; i++) {
            colors[i] = 0;
        }
        int[] preds = new int[n];
        for (int i = 0; i < n; i++) {
            preds[i] = -1;
        }

        int[] d = new int[n];
        int[] f = new int[n];
        int time = 0;
        while (true) {
            int i = findColor(colors, 0);
            dfs(i, time, d, f, preds, colors, adjLists);
        }
    }

    private static int dfs(int i, int time, int[] d, int[] f, int[] preds, int[] colors, List<Integer>[] adjLists) {
        colors[i] = 1;
        int currTime = time;
        d[i] = currTime;

        List<Integer> adjList = adjLists[i];
        boolean done = false;
        for (int j: adjList) {
            if (colors[j] == 0) {
                currTime++;
                colors[j] = 1;
                currTime = dfs(j, currTime, d, f, preds, colors, adjLists);
            }
        }
        f[i] = currTime;
        return currTime + 1;
    }
}
