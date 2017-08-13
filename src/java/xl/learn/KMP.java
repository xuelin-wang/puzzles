package xl.learn;

import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.Collection;

//a b a b c d   ababxxababcyy
//-1-10 1 -1-1
public class KMP {
    private static int[] createTable(String p)
    {
        int n = p.length();
        int[] t = new int[n];
        t[0] = -1;

        for (int i = 1; i < n; i++) {
            char ch = p.charAt(i);
            t[i] = -1;
            for (int j = i-1; j>=0; j--) {
                if (ch == p.charAt(t[j] + 1)) {
                    t[i] = t[j] + 1;
                    break;
                }
            }
        }
        return t;
    }

    public static Collection<Integer> findMatches(String p, String s)
    {
        int[] t = createTable(p);

        int m = p.length();
        int n = s.length();
        Collection<Integer> matches = new ArrayList<>();
        int j = 0;
        int i = 0;

        while (i <= n - m) {
            char ch = s.charAt(i + j);
            if (p.charAt(j) == ch) {
                if (j == m - 1) {
                    matches.add(i);
                    j++;
                }
                else {
                    j++;
                    continue;
                }
            }

            if (j == 0) {
                i++;
                continue;
            }

            int j1 = t[j - 1];
            if (j1 == -1) {
                i = i + j;
                j = 0;
            }
            else {
                i += j - j1 - 1;
                j = j1 + 1;
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
