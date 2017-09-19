package xl.learn.uber;

/**
 * Created by xuelin on 9/19/17.
 *
 * '.' Matches any single character.
 '*' Matches zero or more of the preceding element.

 The matching should cover the entire input string (not partial).

 The function prototype should be:
 bool isMatch(const char *s, const char *p)

 Some examples:
 isMatch("aa","a") → false
 isMatch("aa","aa") → true
 isMatch("aaa","aa") → false
 isMatch("aa", "a*") → true
 isMatch("aa", ".*") → true
 isMatch("ab", ".*") → true
 isMatch("aab", "c*a*b") → true

 solution:
pattern head:

 m("", x*y) = m("", y)
 m("", x[~*]y) = false
 m("", x) = false
 m("", "") = true

 m(ax, b) = false
 m(ax, b[~*]y) = false
 m(ax, b*y) = m(ax, y)
 m(ax, a) = empty(x)
 m(ax, a[~*]y) = m(x, [~*]y)
 m(ax, a[*]y) = m(x, a[*]y) || m(ax, a[*]y)
 m(ax, .) = empty(x)
 m(ax, .[~*]y) = m(x, [~*]y)
 m(ax, .[*]y) = m(ax, y)  || m(x, y) ||
     x:: a{k}p: m(a{k from 1 to k}, y)

 */
public class Regex {
    private static boolean matchEmpty(String pattern) {
        if (pattern.length() == 0)
            return true;

        if (pattern.length() == 1)
            return false;

        if (pattern.charAt(1) == '*')
            return matchEmpty(pattern.substring(2));
        else
            return false;
    }

    public static boolean match(String str, String pattern) {
        if (str.isEmpty())
            return matchEmpty(pattern);

        if (pattern.isEmpty())
            return false;

        char s0 = str.charAt(0);
        char p0 = pattern.charAt(0);
        boolean followStar = pattern.length() > 1 && pattern.charAt(1) == '*';
        if (!followStar) {
            if (p0 != '.' && p0 != s0)
                return false;
            return match(str.substring(1), pattern.substring(1));
        }

        if (p0 == s0 || p0 == '.') {
            return match(str.substring(1), pattern.length() == 2 ? "" : pattern.substring(2)) ||
                    match(str.substring(1), pattern);
            //comments is considering ab does not match .*, but aa does.
//            int k = 1;
//            for (int index = 1; index < str.length(); index++) {
//                if (str.charAt(index) != s0)
//                    break;
//                k++;
//            }
//
//            String newStr = str.substring(k);
//            for (int m = 0; m <= k; m++) {
//                for (int i = 0; i < m; i++)
//                    newStr = s0 + newStr;
//                if (match(newStr, pattern.substring(2)))
//                    return true;
//            }
//            return false;
        }
        else {
            return match(str, pattern.length() == 2 ? "" : pattern.substring(2));
        }
    }

    public static void main(String[] args) {
        for (String[] strPat: new String[][]{
                {"aa", "a"},
                {"aa", "aa"},
                {"aaa", "aa"},
                {"aa", "a*"},
                {"aa", ".*"},
                {"ab", ".*"},
                {"aab", "c*a*b"},
        }) {
            String str = strPat[0];
            String pat = strPat[1];
            System.out.println("Str: " + str + " Pattern: " + pat + " matched: " + match(str, pat));
        }
    }
}
