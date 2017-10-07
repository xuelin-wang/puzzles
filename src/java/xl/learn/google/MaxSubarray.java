package xl.learn.google;

/**
 * Created by xuelin on 9/26/17.
 *
 * Given an array consisting of n integers, find the contiguous subarray whose length is greater than or equal to k
 * that has the maximum average value. And you need to output the maximum average value.

 Example 1:
 Input: [1,12,-5,-6,50,3], k = 4
 Output: 12.75
 Explanation:
 when length is 5, maximum average value is 10.8,
 when length is 6, maximum average value is 9.16667.
 Thus return 12.75.
 Note:
 1 <= k <= n <= 10,000.
 Elements of the given array will be in range [-10,000, 10,000].
 The answer with the calculation error less than 10-5 will be accepted.


 Solution:

 binary search between min and max
 idea is consecutive k vals bigger than zero means target is bigger than current mid
 O(nlog(max-min))

 For i from 0 to n-1:
     f(i) = subarray ending at or before i with max average
     f(i+1) = max(f(i), max subarray avageges ending at i+1)

 Cost: O(nk). Space: O(n)
 */
public class MaxSubarray {
}
