package xl.learn.google;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by xuelin on 9/21/17.
 *
 * Given a 2D board and a list of words from the dictionary, find all words in the board.

 Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.

 For example,
 Given words = ["oath","pea","eat","rain"] and board =

 [
 ['o','a','a','n'],
 ['e','t','a','e'],
 ['i','h','k','r'],
 ['i','f','l','v']
 ]
 Return ["eat","oath"].
 Note:
 You may assume that all inputs are consist of lowercase letters a-z.

 Solution:
 navigate the 2d board and backtrack
 make the words a trie?
 */
public class WordSearch {
    private static String getWord(List<int[]> cells, char[][] board) {
        StringBuilder sb = new StringBuilder();
        for (int[] cell: cells) {
            sb.append(board[cell[0]][cell[1]]);
        }
        return sb.toString();
    }
    public static boolean checkWord(String word, Set<String> dict, Set<String> found) {
        boolean exists = dict.contains(word);
        if (exists) {
            found.add(word);
            dict.remove(word);
        }
        return exists;
    }

    private static boolean contains(List<int[]> cells, int[] cell) {
        for (int[] tmp: cells) {
            if (tmp[0] == cell[0] && tmp[1] == cell[1])
                return true;
        }
        return false;
    }

    private static boolean valid(int[] cell, int rowCount, int colCount) {
        return cell[0] >= 0 && cell[0] < rowCount && cell[1] >= 0 && cell[1] < colCount;
    }

    private static int[] nextNeighbor(int[] cell, int[] neighbor, int rowCount, int colCount, List<int[]> cells) {
        int[] d0 = neighbor == null ? null : new int[]{neighbor[0] - cell[0], neighbor[1] - cell[1]};
        int[][] deltas =  new int[][]{
                {0, 1}, {1, 0}, {0, -1}, {-1, 0}
        };
        boolean started = false;
        for (int[] delta: deltas) {
            if (neighbor != null) {
                if (!started) {
                    started = delta[0] == d0[0] && delta[1] == d0[1];
                    continue;
                }
            }

            int newR = cell[0] + delta[0];
            int newC = cell[1] + delta[1];

            int[] newCell = new int[]{newR, newC};
            if (!valid(newCell, rowCount, colCount) || contains(cells, newCell))
                continue;

            return new int[]{newR, newC};
        }
        return null;
    }

    private static boolean moveToChild(List<int[]> cells, int rowCount, int colCount) {
        int[] lastCell = cells.get(cells.size() - 1);
        int[] newCell = nextNeighbor(lastCell, null, rowCount, colCount, cells);
        if (newCell != null) {
            cells.add(newCell);
            return true;
        }
        else
            return false;
    }

    private static boolean moveToSibling(List<int[]> cells, int rowCount, int colCount) {
        int[] lastCell = cells.get(cells.size() - 1);
        if (cells.size() == 1) {
            int[] newCell = lastCell[1] == colCount -1 ? new int[] {lastCell[0] + 1, lastCell[1]} : new int[]{lastCell[0], lastCell[1] + 1};
            if (newCell[0] >= rowCount)
                return false;
            cells.remove(cells.size() - 1);
            cells.add(newCell);
            return true;
        }

        int[] ppCell = cells.get(cells.size() - 2);
        int[] newCell = nextNeighbor(ppCell, lastCell, rowCount, colCount, cells);
        if (newCell == null)
            return false;
        cells.remove(cells.size() - 1);
        cells.add(newCell);
        return true;
    }

    private static boolean moveNext(List<int[]> cells, int rowCount, int colCount, int maxLen) {
        if (cells.size() < maxLen) {
            boolean res = moveToChild(cells, rowCount, colCount);
            if (res)
                return true;
        }

        while (true) {
            boolean res = moveToSibling(cells, rowCount, colCount);
            if (res)
                return true;

            if (cells.size() == 1)
                return false;

            cells.remove(cells.size() - 1);
        }
    }

    public static Set<String> findWords(char[][] board, Set<String> dict) {
        int rowCount = board.length;
        int colCount = board[0].length;

        Set<String> words = new HashSet<>();
        words.addAll(dict);
        int maxLen = 0;
        for (String word: dict) {
            if (maxLen < word.length())
                maxLen = word.length();
        }
        Set<String> found = new HashSet<>();

        List<int[]> cells = new ArrayList<>();
        cells.add(new int[] {0, 0});
        checkWord(getWord(cells, board), words, found);

        while (moveNext(cells, rowCount, colCount, maxLen)) {
            checkWord(getWord(cells, board), words, found);
            if (words.isEmpty())
                break;
        }

        return found;
    }


    public static void main(String[] args) {
        char[][] board = new char[][]{
                {'o','a','a','n'},
                {'e','t','a','e'},
                {'i','h','k','r'},
                {'i','f','l','v'}
        };
        String[] dictArr = new String[]{"oath", "pea", "eat", "rain"};
        Set<String> dict = new HashSet<>();
        for (String word: dictArr) {
            dict.add(word);
        }

        Set<String> found = findWords(board, dict);

        System.out.println("found words: " + found.stream().collect(Collectors.joining(", ")));
    }
}
