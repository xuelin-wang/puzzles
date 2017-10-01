package xl.learn.google;

/**
 * Created by xuelin on 9/29/17.
 * For an integer n, we call k>=2 a good base of n, if all digits of n base k are 1.

 Now given a string representing n, you should return the smallest good base of n in string format.

 Example 1:
 Input: "13"
 Output: "3"
 Explanation: 13 base 3 is 111.
 Example 2:
 Input: "4681"
 Output: "8"
 Explanation: 4681 base 8 is 11111.
 Example 3:
 Input: "1000000000000000000"
 Output: "999999999999999999"
 Explanation: 1000000000000000000 base 999999999999999999 is 11.
 Note:
 The range of n is [3, 10^18].
 The string representing n is always valid and will not have leading zeros.

 Solution:
find x such that s = sum x^i = (x^k - 1) / (x - 1)
 1:
 Can use Java BigInteger. modula and division, check remainder is 1.
 Cost: O(logn * BegInteger cost for doing length n division/modulo).

 2:
 find the exponent k so that x / (y^(k/2))) = x % (y^(k/2)) when k if odd.
 when k is even,  (x - y^(k/2)) / (y^(k/2+1)) = x % (y^(k/2+1)) ,x

 */
public class SmallestGoodBase {
}
