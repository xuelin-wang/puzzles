package xl.learn.google;

/**
 * Created by xuelin on 9/27/17.
 * Given a positive integer n, return the number of all possible attendance records with length n,
 * which will be regarded as rewardable. The answer may be very large, return it after mod 10^9 + 7.

 A student attendance record is a string that only contains the following three characters:

 'A' : Absent.
 'L' : Late.
 'P' : Present.
 A record is regarded as rewardable if it doesn't contain more than one 'A' (absent) or more than two continuous 'L' (late).

 Example 1:
 Input: n = 2
 Output: 8
 Explanation:
 There are 8 records with length 2 will be regarded as rewardable:
 "PP" , "AP", "PA", "LP", "PL", "AL", "LA", "LL"
 Only "AA" won't be regarded as rewardable owing to more than one absent times.
 Note: The value of n won't exceed 100,000.

 Solution:

 dp(i) for the following counts with length i:
 {0, 1 A} * {ends with 0, 1, 2Ls}
 f0A0L
 f1A0L
 f0A1L
 f1A1L
 f0A2L
 f1A2L
 total rewardable = sum of above
 f0A0L[i+1] = f0A{0,1,2}L[i] //add P
 f1A0L[i+1] = f0A{0,1,2}L[i] //add A
               + f1A{0,1,2}L[i] //add P
 f0A1L[i+1] = f0A0L[i] // add L
 f1A1L[i+1] = f1A0L[i] // add L
 f0A2L[i+1] = f0A1L[i] // add L
 f1A2L[i+1] = f1A1L[i] // add L

 cost: O(n), space: O(n) or O(1) if only keep previous i's state.

 */
public class StudentAttendanceRecord {
}
