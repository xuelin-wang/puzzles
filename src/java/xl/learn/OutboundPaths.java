package xl.learn;


import java.util.*;

/**
 * There is an m by n grid with a ball. Given the start coordinate (i,j) of the ball,
 * you can move the ball to adjacent cell or cross the grid boundary in four directions (up, down, left, right).
 * However, you can at most move N times. Find out the number of paths to move the ball out of grid boundary.
 * The answer may be very large, return it after mod 109 + 7.
 * Created by xuelin on 5/7/17.
 */
public class OutboundPaths {
    private static class Node {
        public int x;
        public int y;

        public static String toKey(int i, int j) {
            return "" + i + "_ " + j;
        }
        public static int[] fromKey(String k) {
            int index = k.indexOf("_");
            int i = Integer.parseInt(k.substring(0, index));
            int j = Integer.parseInt(k.substring(index + 1));
            return new int[]{i, j};
        }
        public Node(int i, int j) {
            x = i;
            y = j;
        }

        public int getNeighborCode(Node node) {
            if (node == null)
                return -1;
            int deltaX = node.x - x;
            int deltaY = node.y - y;
            if (deltaX == 0 && deltaY == -1)
                return 0;
            else if (deltaX == 0 && deltaY == 1)
                return 2;
            else if (deltaY == 0 && deltaX == 1)
                return 1;
            else if (deltaY == 0 && deltaX == -1)
                return 3;
            else
                return 4;
        }
        public Node getNeighbor(int neiboughCode) {
            switch (neiboughCode) {
                case 0: {
                    return new Node(x, y - 1);
                }
                case 1: {
                    return new Node(x + 1, y);
                }
                case 2:
                    return new Node(x, y + 1);
                case 3:
                    return new Node(x - 1, y);
                default:
                    return null;
            }
        }

        public Node nextNeighbor(Node node) {
            return getNeighbor(getNeighborCode(node) + 1);
        }
    }

    int m, n, ll;
    int count;

    public OutboundPaths(int m, int n, int ll) {
        this.m = m;
        this.n = n;
        this.ll = ll;
    }

    public static boolean outBound(int x, int y, int m, int n) {
        return x < 0 || y < 0 || x >= m || y >= n;
    }

    public void moveNext(List<Node> path) {
        boolean goDeeper;
        Node top = path.get(path.size() - 1);
        if (path.size() == 1)
            goDeeper = true;
        else if (outBound(top.x, top.y, m, n))
            goDeeper = false;
        else
            goDeeper = path.size() < ll + 1;
        if (!goDeeper) {
            while (true) {
                Node lastNode = path.remove(path.size() - 1);
                Node parent = path.get(path.size() - 1);
                Node next = null;
                next = parent.nextNeighbor(lastNode);
                if (next != null) {
                    path.add(next);
                    return;
                }
                if (path.size() == 1) {
                    return;
                }
                continue;
            }

        }

        Node lastNode = path.get(path.size() - 1);
        Node child = lastNode.nextNeighbor(null);
        path.add(child);
    }

    public int getCount(int i, int j)
    {
        count = 0;

        List<Node> path = new ArrayList<>();
        Node startNode = new Node(i, j);
        path.add(startNode);

        while (true) {
            Node top = path.get(path.size() - 1);
            if (outBound(top.x, top.y, m, n)) {
                System.out.println("path: ");

                for (Node node: path) {
                    System.out.println("[" + node.x + ", " + node.y + "], ");
                }
                System.out.println();
                count++;
            }

            moveNext(path);
            if (path.size() == 1) {
                break;
            }
        }
        return count;
    }

    public static int outPathCount(final int m, final int n, final int ll, int i, int j)
    {
        OutboundPaths paths = new OutboundPaths(m, n, ll);
        return paths.getCount(i, j);
    }


    public static void samples() {
        {
            int m = 2, n = 2, ll = 2, i = 0, j = 0;
            System.out.println(OutboundPaths.outPathCount(m, n, ll, i, j));
        }
        {
            int m = 1, n = 3, ll = 3, i = 0, j = 1;
            System.out.println(OutboundPaths.outPathCount(m, n, ll, i, j));
        }
    }

    public static void main(String[] args) {
        samples();
    }
}
