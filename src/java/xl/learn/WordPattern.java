package xl.learn;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xuelin on 9/16/17.
 *
 * Given a pattern and a string str, find if str follows the same pattern.

 Here follow means a full match, such that there is a bijection between a letter in pattern and a non-empty substring in str.

 Examples:
 pattern = "abab", str = "redblueredblue" should return true.
 pattern = "aaaa", str = "asdasdasdasd" should return true.
 pattern = "aabb", str = "xyzabcxzyabc" should return false.
 Notes:
 You may assume both pattern and str contains only lowercase letters.


 */
public class WordPattern {

    /**
     * match(pattern, str, varMap)
     * first var is a:
     * match(patterm_a, substr, a->prefix+varMap)
     *
     * @param pattern
     * @param str
     * @return
     */
    public static boolean match(String pattern, String str) {
        return matchSubstr(pattern, str, new HashMap<>());
    }

    private static boolean matchSubstr(String pattern, String str, Map<Character, String> assign) {
        if (pattern.length() == 0)
            return str.length() == 0;

        if (str.length() == 0)
            return false;

        char ch = pattern.charAt(0);
        String val = assign.get(ch);
        if (val != null) {
            if (!str.startsWith(val))
                return false;
            return matchSubstr(pattern.substring(1), str.substring(val.length()), assign);
        }
        for (int end = 1; end <= str.length(); end++) {
            Map<Character, String> newAssign = new HashMap<>();
            newAssign.putAll(assign);
            newAssign.put(ch, str.substring(0, end));
            if (matchSubstr(pattern.substring(1), str.substring(end), newAssign))
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        for (String[] patStr: new String[][]{
                {"abab",  "redblueredblue"},
                {"aaaa", "asdasdasdasd"},
                {"aabb", "xyzabcxzyabc"}
        }) {
            String pat = patStr[0];
            String str = patStr[1];
            System.out.println("Pattern: " + pat + " String: " + str);
            System.out.println("Matched: " + match(pat, str));
        }
    }
}
