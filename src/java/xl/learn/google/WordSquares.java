package xl.learn.google;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xuelin on 9/25/17.
 *
 * Given a set of words (without duplicates), find all word squares you can build from them.

 A sequence of words forms a valid word square if the kth row and column read the exact same string, where 0 ≤ k < max(numRows, numColumns).

 For example, the word sequence ["ball","area","lead","lady"] forms a word square because each word reads the same both horizontally and vertically.

 b a l l
 a r e a
 l e a d
 l a d y
 Note:
 There are at least 1 and at most 1000 words.
 All words will have the exact same length.
 Word length is at least 1 and at most 5.
 Each word contains only lowercase English alphabet a-z.
 Example 1:

 Input:
 ["area","lead","wall","lady","ball"]

 Output:
 [
 [ "wall",
 "area",
 "lead",
 "lady"
 ],
 [ "ball",
 "area",
 "lead",
 "lady"
 ]
 ]

 Explanation:
 The output consists of two word squares. The order of output does not matter (just the order of words in each word square matters).
 Example 2:

 Input:
 ["abat","baba","atan","atal"]

 Output:
 [
 [ "baba",
 "abat",
 "baba",
 "atan"
 ],
 [ "baba",
 "abat",
 "baba",
 "atal"
 ]
 ]

 Explanation:
 The output consists of two word squares. The order of output does not matter (just the order of words in each word square matters).

 Solution:
 word length k,
 for each word, if as first row:
   The other k-1 words' first letter has been determined.
   (Heuristic: use the letter which has fewest words starting with it? but need calc frequencies.)
 trie: O(kn), each node contains counts of decendent words
   pick first word from any of words list n.
   pick second startig with second letter.
   pick third starting with w[0][2], w[1][2]
   ...
   backtrack
   Assuming average branching is m < 26.
   Time: trie: O(n) +  n * (n/m) * (n/m^2) * ... * (n/m^(k-1)) = O(n^k)

 Naive:
 P(n, k), check all permutations
 */
public class WordSquares {
    public static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        public TrieNode() {
        }
        public TrieNode addWord(String word) {
            TrieNode currNode = this;
            for (int index = 0; index < word.length(); index++) {
                char ch = word.charAt(index);
                TrieNode node = currNode.children[ch - 'a'];
                if (node == null) {
                    node = new TrieNode();
                    currNode.children[ch - 'a'] = node;
                }
                currNode = node;
            }
        }
        public String nextWord(String prefix, String word) {
            return null;
        }
    }

    private static TrieNode createTrie(List<String> words) {
        TrieNode root = new TrieNode();
        return root;
    }
    private static String getPrefix(List<String> square) {
        int m = square.size();
        if (m == 0)
            return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++)
            sb.append(square.get(i).charAt(m));
        return sb.toString();
    }
    private static boolean backtrack() {}
    
    public static List<List<String>> wordsSqares(List<String> words) {
        TrieNode root = createTrie(words);

        List<List<String>> squares = new ArrayList<>();
        int k = words.get(0).length();
        List<String> square = new ArrayList<>();
        while (true) {
            String prefix = getPrefix(square);
            String next = root.nextWord(prefix, square.isEmpty() ? null : square.get(square.size() - 1));

            if (next != null)
                square.add(next);

            if (square.size() == k) {
                squares.add(square);
            }

            if (next == null || square.size() == k) {
                if (!backtrack())
                    break;
            }
        }

        return squares;
    }
}
