"
A message containing letters from A-Z is being encoded to numbers using the following mapping way:

'A' -> 1
'B' -> 2
...
'Z' -> 26
Beyond that, now the encoded string can also contain the character '*', which can be treated as one of the numbers from 1 to 9.

Given the encoded message containing digits and the character '*', return the total number of ways to decode it.

Also, since the answer may be very large, you should return the output mod 109 + 7.

Example 1:
Input: '*'
Output: 9
Explanation: The encoded message can be decoded to the string: 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'.
Example 2:
Input: '1*'
Output: 9 + 9 = 18
Note:
The length of the input string will fit in range [1, 105].
The input string will only contain the character '*' and digits '0' - '9'.

Analysis:
considering without '*':
Given an encoding e of length k,
f(e, i) = f(e, i-1) | last digit mapped to a letter
          + f(e, i-2) | if second from lst is 1, or 2 and last digit is 1 to 6.

considering '*':
f(e, i) = f(e, i-1) | last digit non-star mapped to a letter
          9 * f(e, i-1) | last digit star mapped to a letter
          + f(e, i-2) | if second from lst non-star is 1, or 2 and last digit is 1 to 6.
          + 9 * f(e, i-2) | if second from lst non-star is 1 and last digit is star.
          + 6 * f(e, i-2) | if second from lst non-star is 2 and last digit is star.
          + 2 * f(e, i-2) | if second from lst star, last is non-star 1-6
          + f(e, i-2) | if second from lst star, last is non-star 7-9
          + 15 * f(e, i-2) | if second from lst star, last is star

"

(ns xl.decode-ways)

