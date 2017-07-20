package xl.learn;

/**
 * Created by xuelin on 7/20/17.
 * a-z mapped to 0 to 25
 * find out how many ways to map a number sequence to letters
 * A dynamic programming problem:
 * Going backwards, c(ijX) =
 *   i=0 or i>2 or i=2 and j > 5: c(jX)
 *   i=2, j<=5 or i = 1: c(jX) + c(X)
 */
public class NumberToLetter {
    public static int count(String digits) {
        /*
        // recursive:
        //boundary
        if (digits.length() == 1)
            return 1;

        int i = digits.charAt(0) - 'a';
        int j = digits.charAt(1) - 'a';
        if (digits.length() == 2) {
            if (i == 0 || i > 2)
                return 1;
            if (i == 2) {
                if (j > 5) return 1;
                return 2;
            }
            //i == 1
            return 2;
        }

        //induction
        if (i == 0 || i > 2 || i == 2 && j > 5)
            return count(digits.substring(1));
        return count(digits.substring(1) + digits.substring(2));
        */

        int n = digits.length();
        int[] counts = new int[n];
        counts[n - 1] = 1;

        for (int k = n - 2; k >= 0; k--) {
            int i = digits.charAt(k) - 'a';
            int j = digits.charAt(k + 1) - 'a';
            if (i == 0 || i > 2)
                counts[k] = counts[k + 1];
            else if (i == 2) {
                if (j > 5) counts[k] = counts[k + 1];
                else counts[k] = counts[k + 1] + 1;
            }
            else //i == 1
                counts[k] = counts[k + 1] + k == n - 2 ? 1 :  counts[k + 2];
            //i == 1
        }
        return counts[0];
    }
}
