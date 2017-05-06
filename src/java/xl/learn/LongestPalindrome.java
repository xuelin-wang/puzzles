package xl.learn;

import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by xuelin on 5/4/17.
 */
public class LongestPalindrome {
    public static int longest(String str)
    {
        if (str.length() <= 1)
            return str.length() - 1;

        int currMax = 0;
        outer: for (int to = 1; to < str.length(); to++) {
            for (int ii = 0, jj = to; ii <= jj + 1 && jj < str.length(); ii++, jj--) {
                if (ii == jj || ii == jj + 1) {
                    currMax = to;
                    break;
                }
                else if (str.charAt(ii) != str.charAt(jj)){
                    break;
                }
            }
        }
        return currMax;
    }

    private static int[] buildKmp(String word)
    {
        int len = word.length();
        int[] moves = new int[len];
        moves[0] = 1;
        for (int i = 1; i < len; i++) {
            int move = moves[i - 1];
            if (word.charAt(i) == word.charAt(i - move)) {
                moves[i] = move;
                continue;
            }

            for (move = move + 1; move <= i + 1; move++){
                boolean goodMove = true;
                for (int j = i; j >= move; j--) {
                    if (word.charAt(j) != word.charAt(j - move)) {
                        goodMove = false;
                        break;
                    }
                }
                if (goodMove) {
                    moves[i] = move;
                    break;
                }
            }
        }
        return moves;
    }

    public static int longestKmp(String str) {
        return -1;
    }

    public static void test() {
        for (String str: new String[]{"a", "aa", "ab", "aba", "abb", "aab", "abc", "aaa", "abba", "abbc", "abbb"}) {
            int index = longest(str);
            System.out.println("str: " + str);
            System.out.println("index: " + index);
            String prefix;
            if (index < str.length() - 1) {
                prefix = new StringBuilder(str.substring(index + 1)).reverse().toString();
            }
            else {
                prefix = "";
            }
            System.out.println("prefix: " + prefix);
        }
    }

    public static void testKmp() {
        for (String str: new String[]{"a", "aa", "ab", "aaa", "aab", "aba", "baa", "abc",
                "aaaa", "aaab", "aaba", "abaa", "baaa", "abcd", "abab", "abca", "abcd"}) {
            int[] arr = buildKmp(str);
            System.out.println("word: " + str);
            System.out.println("kmp array: ");
            for (int i: arr) {
                System.out.print(i);
                System.out.print(", ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        testKmp();
    }
}
