package xl.learn.google;

import com.google.common.base.Joiner;

import java.util.*;

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
        String prefix;
        TrieNode[] children;
        public TrieNode(String prefix) {
            this.prefix = prefix;
            this.children = new TrieNode[26];
        }
        public TrieNode nextChild(int index) {
            for (int i = index + 1; i < 26; i++) {
                if (this.children[i] != null)
                    return this.children[i];
            }
            return null;
        }
        public TrieNode firstChild() {
            return nextChild(-1);
        }
    }

    public static void addWord(String word, TrieNode root) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode child = node.children[ch - 'a'];
            if (child == null) {
                child = new TrieNode(node.prefix + ch);
                node.children[ch - 'a'] = child;
            }
            node = child;
        }
    }

    public static TrieNode makeTrie(List<String> words) {
        TrieNode root = new TrieNode("");
        for (String word: words) {
            addWord(word, root);
        }
        return root;
    }

    private static String firstWord(String prefix, TrieNode root) {
        List<TrieNode> path = new ArrayList<>();
        path.add(root);
        for (int i = 0; i < prefix.length(); i++) {
            char ch = prefix.charAt(i);
            TrieNode child = path.get(path.size() - 1).children[ch - 'a'];
            if (child == null)
                return null;
            path.add(child);
        }

        while (true) {
            TrieNode last = path.get(path.size() - 1);
            TrieNode child = last.firstChild();
            if (child == null)
                break;
            path.add(child);
        }
        return path.get(path.size() - 1).prefix;
    }

    private static String nextWord(String prefix, String word, TrieNode root) {
        assert(word.startsWith(prefix));

        List<TrieNode> path = new ArrayList<>();
        path.add(root);
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            path.add(path.get(path.size() - 1).children[ch - 'a']);
        }

        while (path.size() > prefix.length() + 1) {
            TrieNode last = path.get(path.size() - 1);
            TrieNode p = path.get(path.size() - 2);
            int index = last.prefix.charAt(last.prefix.length() - 1) - 'a';
            TrieNode next = p.nextChild(index);
            if (next == null) {
                path.remove(path.size() - 1);
            }
            else {
                path.set(path.size() - 1, next);
                break;
            }
        }

        if (path.size() == prefix.length() + 1)
            return null;

        while (path.size() < word.length() + 1) {
            path.add(path.get(path.size() - 1).firstChild());
        }

        return path.get(path.size() - 1).prefix;
    }

    private static String getnextWordPrefix(List<String> square) {
        if (square.isEmpty())
            return "";

        StringBuilder sb = new StringBuilder();
        int m = square.size();
        for (int i = 0; i < square.size(); i++)
            sb.append(square.get(i).charAt(m));
        return sb.toString();
    }
    public static List<List<String>> wordsSquares(List<String> words) {
        TrieNode root = makeTrie(words);

        List<List<String>> squares = new ArrayList<>();
        List<String> square = new ArrayList<>();
        int k = words.get(0).length();
        while (true) {
            String next;
            if (square.isEmpty()) {
                next = firstWord("", root);
                square.add(next);
            }
            else {
                String last = square.get(square.size() - 1);
                String prefix = getnextWordPrefix(square.subList(0, square.size() - 1));
                next = nextWord(prefix, last, root);
                if (next == null) {
                    square.remove(square.size() - 1);
                    if (square.isEmpty()) {//done
                        break;
                    }
                    continue;
                }
                square.set(square.size() - 1, next);
            }

            while (square.size() < k) {
                String prefix = getnextWordPrefix(square);
                next = firstWord(prefix, root);
                if (next == null)
                    break;
                square.add(next);
            }

            if (square.size() == k) {
                List<String> answer = new ArrayList<>();
                answer.addAll(square);
                squares.add(answer);
            }

        }
        return squares;
    }

    public static void main(String[] args) {
        for (String[] words: new String[][]{
                {"area","lead","wall","lady","ball"},
                {"abat","baba","atan","atal"}
        }) {
            System.out.println(Joiner.on(", ").join(words));
            System.out.println("Answers: ");
            List<List<String>> wordsList = wordsSquares(Arrays.asList(words));
            for (List<String> res: wordsList) {
                System.out.println("    " + Joiner.on(", ").join(res));
            }
        }
    }
}
