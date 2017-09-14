package xl.learn;

/**
 * A message containing letters from A-Z is being encoded to numbers using the following mapping way:

 'A' -> 1
 'B' -> 2
 ...
 'Z' -> 26
 Beyond that, now the encoded string can also contain the character '*', which can be treated as one of the numbers from 1 to 9.

 Given the encoded message containing digits and the character '*', return the total number of ways to decode it.

 Also, since the answer may be very large, you should return the output mod 109 + 7.

 Example 1:
 Input: "*"
 Output: 9
 Explanation: The encoded message can be decoded to the string: "A", "B", "C", "D", "E", "F", "G", "H", "I".
 Example 2:
 Input: "1*"
 Output: 9 + 9 = 18
 Note:
 The length of the input string will fit in range [1, 105].
 The input string will only contain the character '*' and digits '0' - '9'.
 * Created by xuelin on 9/14/17.
 *
 *
 *
 *
 */
public class NumEncoding {
    public static int computeCounts(String cypher) {
        int len = cypher.length();
        int[] counts = new int[len + 1];
        //we can further recude memory usage since counts[i] only depends on counts[i+1] and counts[i+2].
        //also, we can do it ther other direction too.
        counts[len] = 1;

        for (int i = len - 1; i >= 0; i--) {
            char ch = cypher.charAt(i);
            if (i == len - 1) {
                if (ch == '*')
                    counts[i] = 9;
                else if (ch == '0')
                    counts[i] = 0;
                else
                    counts[i] = 1;
                continue;
            }

            char nextch = cypher.charAt(i + 1);
            if (ch == '*') {
                counts[i] = 9 * counts[i + 1];
                if (nextch == '*') {
                    counts[i] += 17 * counts[i + 2];
                }
                else {
                    counts[i] += counts[i + 2];
                    if (nextch < '7')
                        counts[i] += counts[i + 2];
                }
            }
            else {
                if (ch == '0') {
                    counts[i] = 0;
                }
                else {
                    counts[i] = counts[i + 1];
                    if (ch == '1') {
                        if (nextch == '*')
                            counts[i] += 10 * counts[i + 2];
                        else
                            counts[i] += counts[i + 2];
                    }
                    else if (ch == '2') {
                        if (nextch == '*'){
                            counts[i] += 7 * counts[i + 2];
                        }
                        else if (nextch < '7')
                            counts[i] += counts[i + 2];
                    }
                }
            }
        }
        return counts[0];
    }

    public static void main(String[] args) {
        for (String cypher: new String[]{
                "*", "1*"
        }) {
            System.out.println("cypher: " + cypher + " count: " + computeCounts(cypher));
        }
    }
}
