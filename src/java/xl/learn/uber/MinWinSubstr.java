package xl.learn.uber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuelin on 9/19/17.
 *
 * Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).

 For example,
 S = "ADOBECODEBANC"
 T = "ABC"
 Minimum window is "BANC".

 Note:
 If there is no such window in S that covers all characters in T, return the empty string "".

 If there are multiple such windows, you are guaranteed that there will always be only one unique minimum window in S.

 f(ax) = min(min starts with a, m(x))


 */


public class MinWinSubstr {
    private static boolean and(boolean[] matched) {
        for (boolean m: matched) {
            if (!m)
                return false;
        }
        return true;
    }

    public static String window(String s, String t) {
        List<Integer> events = new ArrayList<>();
        boolean[] matched = new boolean[t.length()];
        for (int index = 0; index < matched.length; index++)
            matched[index] = false;

        int[] minWin = null;
        for (int index = 0; index < s.length(); index++) {
            char ch = s.charAt(index);
            int tIndex = t.indexOf(ch);
            if (tIndex < 0)
                continue;

            matched[tIndex] = true;
            events.add(index);
            if (and(matched)) {
                int[] currWin = new int[]{events.get(0), events.get(events.size() - 1)};
                if (minWin == null || minWin[1] - minWin[0] > currWin[1] - currWin[0]) {
                    minWin = currWin;
                }
                List<Integer> newEvents = new ArrayList<>();
                newEvents.addAll(events);
                int first = events.get(0);
                newEvents.remove(0);
                matched[t.indexOf(s.charAt(first))] = false;
                events = newEvents;
            }
            else {
                //remove all previous events of same char
                for (int ei = events.size() - 2; ei >= 0; ei--) {
                    if (s.charAt(events.get(ei)) == ch) {
                        events.remove(ei);
                        break;
                    }
                }
            }
        }

        if (minWin == null)
            return null;

        return s.substring(minWin[0], minWin[1] + 1);
    }

    public static void main(String[] args) {
        for (String[] st: new String[][]{
                {"ADOBECODEBANC", "ABC"}
        }) {
            System.out.println("s: " + st[0] + ", t: " + st[1] + ", win: " + window(st[0], st[1]));
        }
    }
}
