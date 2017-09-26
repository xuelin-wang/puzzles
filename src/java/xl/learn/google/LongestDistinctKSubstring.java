package xl.learn.google;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by xuelin on 9/26/17.
 * Given a string, find the length of the longest substring T that contains at most k distinct characters.

 For example, Given s = “eceba” and k = 2,

 T is "ece" which its length is 3.

Solution:
 for each i, find longest k-substring starting from i.
 keep removing head char (may be consecutive same chars), then find next end location.
 Use a set to track current chars in substring.
 O(n)

 */
public class LongestDistinctKSubstring {
    public static int solve(String s, int k) {
        Map<Character, Integer> used = new HashMap<>();
        int i = 0;
        int n = s.length();
        int max = 0;
        int currBegin = 0;

        while (i < n) {
            char ch = s.charAt(i);
            Integer endIndex = used.get(ch);
            if (used.size() < k || endIndex != null) {
                used.put(ch, i);
                i++;
            }
            else {
                int currMax = i - currBegin;
                if (currMax > max)
                    max = currMax;
                Map.Entry<Character, Integer> minEntry = null;
                for (Map.Entry<Character, Integer> entry: used.entrySet()) {
                    if (minEntry == null || entry.getValue() < minEntry.getValue())
                        minEntry = entry;
                }
                currBegin = minEntry.getValue() + 1;
                used.remove(minEntry.getKey());
            }
        }
        return max;
    }
    

    public static void main(String[] args) {
        for (Object[] sAndK: new Object[][]{
                {"eceba", 2}
        }) {
            System.out.println("max k: " + sAndK[1] + " substring in " + sAndK[0] + " is: " + solve((String)sAndK[0], (int)sAndK[1]));
        }
    }
}
