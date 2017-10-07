package xl.learn;

/**
 * Created by xuelin on 10/2/17.
 *
 *
 * Given a string S, you are allowed to convert it to a palindrome by adding characters in front of it.
 * Find and return the shortest palindrome you can find by performing this transformation.

 For example:

 Given "aacecaaa", return "aaacecaaa".

 Given "abcd", return "dcbabcd".

 Analysis:
   This is equivalent to longest palindrome prefix.
   Naive is to check all prefixes to see if palindrome.

   Improvement: KMP, lookup in reverse str.
 */
public class ShortestPalindrome {
}
