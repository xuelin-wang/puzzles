package xl.learn;

import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuelin on 9/11/17.
 * cell 0 means non passable. 1 passable. 2 passable but with weight of 2 instead of "normal" 1
 */
public class ShortestMazePath {
    public static class Cell {
        public int r;
        public int c;
        public Cell p;
        public int color; //0 white, 1 gray, 2 black
        public int d;

        public Cell(int r, int c, int color, int d) {
            this.r = r;
            this.c = c;
            this.color = color;
            this.d = d;
            this.p = null;
        }
    }

    private static Cell getMinCell(List<Cell> cells) {
        Cell minCell = null;
        for (Cell cell: cells) {
            if (minCell == null || minCell.d >= cell.d) {
                minCell = cell;
            }
        }
        return minCell;
    }

    private static boolean isValid(int r, int c, int rowCount, int colCount) {
        return r>=0 && r<rowCount && c>=0 && c<colCount;
    }
    private static List<int[]> getValidNeighbors(int r, int c, int rowCount, int colCount) {
        List<int[]> retval = new ArrayList<>();
        for (int[] delta: new int[][]{new int[]{0, 1}, new int[]{1, 0}, new int[]{0, -1}, new int[]{-1, 0}}) {
            int r1 = r + delta[0];
            int c1 = c + delta[1];
            if (isValid(r1, c1, rowCount, colCount)) {
                retval.add(new int[]{r1, c1});
            }
        }
        return retval;
    }

    private static Cell[][] toCells(int[][] maze) {
        int rowCount = maze.length;
        int colCount = maze[0].length;
        Cell[][] cells = new Cell[rowCount][colCount];
        for (int r = 0; r < rowCount; r++)
            for (int c = 0; c < colCount; c++)
            {
                cells[r][c] = new Cell(r, c, 0, Integer.MAX_VALUE);
            }
        return cells;
    }

    public static List<int[]> getShortestPath(int r1, int c1, int r2, int c2, int[][] maze) {
        Cell[][] cells = toCells(maze);

        int rowCount = maze.length;
        int colCount = maze[0].length;

        List<Cell> queue = new ArrayList<>();
        cells[r2][c2].color = 1;
        cells[r2][c2].d = 0;
        queue.add(cells[r2][c2]);

        while (!queue.isEmpty()) {
            Cell minCell = getMinCell(queue);
            if (minCell.r == r1 && minCell.c == c1) {
                List<int[]> rcs = new ArrayList<>();
                Cell cell = minCell;
                while (cell != null) {
                    rcs.add(new int[]{cell.r, cell.c});
                    cell = cell.p;
                }
                return rcs;
            }

            List<int[]> ns = getValidNeighbors(minCell.r, minCell.c, rowCount, colCount);
            for (int[] rc: ns) {
                int nr = rc[0];
                int nc = rc[1];
                Cell ncell = cells[nr][nc];
                int cost = maze[nr][nc];
                if (cost == 0) {
                    ncell.color = 2;
                }
                else {
                    if (ncell.d > minCell.d + cost) {
                        ncell.d = cost + minCell.d;
                        ncell.p = minCell;
                    }
                    if (ncell.color == 0) {
                        queue.add(ncell);
                    }
                }
                minCell.color = 2;
                queue.remove(minCell);
            }
        }
        throw new RuntimeException("should never be here");
    }

    private static void output(List<int[]> path) {
        for (int[] rc: path) {
            System.out.print("[" + rc[0] + ", " + rc[1] + "] ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[][] maze = new int[][]{
                {1, 1, 0, 2},
                {1, 1, 1, 2},
                {1, 0, 1, 2},
                {1, 2, 1, 1},
        };
        List<int[]> path = getShortestPath(0, 0, 3, 3, maze);
        output(path);
    }
}
