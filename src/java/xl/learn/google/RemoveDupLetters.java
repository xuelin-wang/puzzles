package xl.learn.google;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by xuelin on 10/1/17.
 * Given a string which contains only lowercase letters, remove duplicate letters so that every letter appear once and only once.
 * You must make sure your result is the smallest in lexicographical order among all possible results.

 Example:
 Given "bcabc"
 Return "abc"

 Given "cbacdcbc"
 Return "acdb"

 Analysis:
 Consider state that we have chosen letters set L, so we are at the first position p of a letter x not belong to L.
 another letter y at position q is usable if all letters before q and not in L, their last appearances are after q.

 Now the problem is to find the smallest usable letter in remaining string.

 O(n), we can get all end positions of each letter.
 Naively, for each new letter position, find min usable letter.
 Cost: O(n^2), space: O(n)

 Optimization:
    only 26 characters!
 */
public class RemoveDupLetters {
    public static String solve(String ss) {
        if (ss.length() == 0 || ss.length() == 1)
            return ss;

        int[] counts = new int[26];
        for (int i = 0; i < ss.length(); i++) {
            counts[ss.charAt(i) - 'a']++;
        }

        int pos = 0;
        for (int i = 0; i < ss.length(); i++) {
            char ch = ss.charAt(i);
            if (ss.charAt(pos) > ch)
                pos = i;
            if (--counts[ss.charAt(i) - 'a'] == 0)
                break;
        }

        return ss.charAt(pos) + solve(ss.substring(pos + 1).replace(ss.substring(pos, pos+1), ""));

    }
    public static void main(String[] args) {
        for (String ss: new String[]{
                "bcabc",
                "cbacdcbc"
        }) {
            System.out.println("min dedup of " + ss + " is: " + solve(ss));
        }
    }
}
