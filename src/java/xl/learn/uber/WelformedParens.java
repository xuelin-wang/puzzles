package xl.learn.uber;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by xuelin on 10/4/17.
 * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.

 For example, given n = 3, a solution set is:

 [
 "((()))",
 "(()())",
 "(())()",
 "()(())",
 "()()()"
 ]

 Analysis:
 valid: no more closing than open at any point.
 ()
 ()(), (())
 ()()(), (())(), ((())), (()())

 n possible different top level structure:
 from 1 to n.
 for case i (i top level groups): permutations of allocate n-i parens into these different groups.
 for each allocation, recursively count combos for each.
 */
public class WelformedParens {
    //partition into i natural numbers with total = n.
    private static class IntPartition {
        private int parts;
        private int total;
        private int[] currPart;
        private int check = 0;
        public IntPartition(int n, int k) {
            this.total = n;
            this.parts = k;
            this.currPart = null;
        }

        public int[] next() {
            if (currPart == null) {
                currPart = new int[parts];
                currPart[parts-1] =total;
                return currPart;
            }

            int lastIndex = 0;
            for (int i = parts-1; i >= 0; i--) {
                if (currPart[i] > 0) {
                    lastIndex = i;
                    break;
                }
            }

            if (lastIndex == 0)
                return null;

            currPart[lastIndex - 1]++;
            int j = currPart[lastIndex] - 1;
            currPart[lastIndex] = 0;
            if (j > 0)
                currPart[0] = j;

            return currPart;
        }
    }

    public static List<String> perm2(int n) {
        return perm2(new ArrayList<>(), "", 0, 0, n);

    }
    public static List<String> perm2(List<String> list, String prefix, int open, int close, int n) {
        if (prefix.length() == 2 * n)
            list.add(prefix);
        if (open < n)
            perm2(list, prefix + "(", open+1, close, n);
        if (close < open)
            perm2(list, prefix + ")", open, close+1, n);
        return list;
    }

    public static List<String> perm(int n) {
        return perm(n, new HashMap<>());
    }
        public static List<String> perm(int n, Map<Integer, List<String>> cache) {
        if (n == 0)
            return Collections.singletonList("");

        if (n == 1)
            return Collections.singletonList("()");

        List<String> list = cache.get(n);
        if (list != null)
            return list;

        list = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            IntPartition parts = new IntPartition(n - i, i);
            while (true) {
                int[] next = parts.next();
                if (next == null)
                    break;

                List<String> combo = new ArrayList<>();
                combo.add("");

                for (int k = 0; k < i; k++) {
                    int j = next[k];
                    List<String> jStrs = perm(j, cache);
                    List<String> newList = new ArrayList<>();
                    for (String str: combo) {
                        for (String jStr : jStrs) {
                            newList.add(str + "(" + jStr + ")");
                        }
                    }
                    combo = newList;
                }

                list.addAll(combo);

            }
        }

        cache.put(n, list);
        return list;
    }

    public static void main(String[] args) {
        List<String> strs = perm2(3);
        System.out.println(strs.stream().collect(Collectors.joining(", ")));
    }

}
