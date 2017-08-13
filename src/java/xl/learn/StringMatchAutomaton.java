package xl.learn;

import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.Collection;

public class StringMatchAutomaton {
    private static class TransitionTable {
        private final int[][] table;

        public TransitionTable(int stateCount, int charCount) {
            table = new int[stateCount][charCount];
        }

        public int getToState(int from, char ch) {
            return table[from][ch - 'a'];
        }

        public void setToState(int from, char ch, int to) {
            table[from][ch - 'a'] = to;
        }
    }

    private static TransitionTable createTransition(String pattern) {
        int m = pattern.length();
        TransitionTable t = new TransitionTable(m + 1, 26);
        for (int i = 0; i < m + 1; i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                int to = 0;
                String pi = pattern.substring(0, i) + c;
                for (int k = Math.min(i+1, m); k > 0; k--) {
                    if (pi.endsWith(pattern.substring(0, k))) {
                        to = k;
                        break;
                    }
                }
                t.setToState(i, c, to);
            }
        }

        return t;
    }

    public static Collection<Integer> findMatches(String pattern, String str) {
        TransitionTable t = createTransition(pattern);
        int q = 0;
        int m = pattern.length();
        Collection<Integer> matches = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            q = t.getToState(q, str.charAt(i));
            if (q == m) {
                matches.add(i - m + 1);
            }
        }
        return matches;
    }

    public static void main(String[] args) {
        for (String[] pAndS: new String[][]{
                new String[]{"a", "ababc"},
                new String[]{"ab", "ababc"},
                new String[]{"ababcd", "ababczababcdwww"},
                new String[]{"ababcd", "abababcdw"},
                new String[]{"ababcd", "abxababyabababcdwababababcd"},
        }) {
            Collection<Integer> matches = findMatches(pAndS[0], pAndS[1]);
            System.out.println("matches for pattern: " + pAndS[0] + " in string: " + pAndS[1]);
            System.out.println(Joiner.on(", ").join(matches));
        }
    }

}
