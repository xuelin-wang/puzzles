package xl.learn;

/**
 * Created by xuelin on 7/21/17.
 *
 * Given an integer n, find the closest integer (not including itself), which is a palindrome.
 *
 * The 'closest' is defined as absolute difference minimized between two integers.
 *
 * Example 1:
 * Input: "123"
 * Output: "121"
 * Note:
 * The input n is a positive integer represented by string, whose length will not exceed 18.
 * If there is a tie, return the smaller one as answer.
 *
 * Solution:
 * the diff digit should have biggest index as possible
 * Seems replicate first half to second with middle digit unchanged is the answer.
 * However, exception:
 * possible to change middle digit to minimize diffs:
 * 10987 to 11011.
 * when middle digit is 9, add first half by 1,
 * when middle digit is 0, sub first half by 1,
 * also consider replicate first half.
 * compare those three, get the right noe as the return value.
 */
public class ClosestPalindrome {
    private static int pairIndex(int i, int l) {
        return l - 1 - i;
    }
    public static int find(int n) {
        String s = String.format("%d", n);
        int l = s.length();
        int m = (l - 1) / 2;

        return -1;
    }
}
