package xl.learn.google;

/**
 * Created by xuelin on 9/29/17.
 *
 * LeetCode wants to give one of its best employees the option to travel among N cities to collect algorithm problems. But all work and no play makes Jack a dull boy, you could take vacations in some particular cities and weeks. Your job is to schedule the traveling to maximize the number of vacation days you could take, but there are certain rules and restrictions you need to follow.

 Rules and restrictions:
 You can only travel among N cities, represented by indexes from 0 to N-1. Initially, you are in the city indexed 0 on Monday.
 The cities are connected by flights. The flights are represented as a N*N matrix (not necessary symmetrical), called flights representing the airline status from the city i to the city j.
 If there is no flight from the city i to the city j, flights[i][j] = 0; Otherwise, flights[i][j] = 1.
 Also, flights[i][i] = 0 for all i.
 You totally have K weeks (each week has 7 days) to travel. You can only take flights at most once per day
 and can only take flights on each week's Monday morning. Since flight time is so short, we don't consider the impact of flight time.
 For each city, you can only have restricted vacation days in different weeks, given an N*K matrix called days representing
 this relationship. For the value of days[i][j], it represents the maximum days you could take vacation in the city i in the week j.
 You're given the flights matrix and days matrix, and you need to output the maximum vacation days you could take during K weeks.

 Example 1:
 Input:flights = [[0,1,1],[1,0,1],[1,1,0]], days = [[1,3,1],[6,0,3],[3,3,3]]
 Output: 12
 Explanation:
 Ans = 6 + 3 + 3 = 12.

 One of the best strategies is:
 1st week : fly from city 0 to city 1 on Monday, and play 6 days and work 1 day.
 (Although you start at city 0, we could also fly to and start at other cities since it is Monday.)
 2nd week : fly from city 1 to city 2 on Monday, and play 3 days and work 4 days.
 3rd week : stay at city 2, and play 3 days and work 4 days.
 Example 2:
 Input:flights = [[0,0,0],[0,0,0],[0,0,0]], days = [[1,1,1],[7,7,7],[7,7,7]]
 Output: 3
 Explanation:
 Ans = 1 + 1 + 1 = 3.

 Since there is no flights enable you to move to another city, you have to stay at city 0 for the whole 3 weeks.
 For each week, you only have one day to play and six days to work.
 So the maximum number of vacation days is 3.
 Example 3:
 Input:flights = [[0,1,1],[1,0,1],[1,1,0]], days = [[7,0,0],[0,7,0],[0,0,7]]
 Output: 21
 Explanation:
 Ans = 7 + 7 + 7 = 21

 One of the best strategies is:
 1st week : stay at city 0, and play 7 days.
 2nd week : fly from city 0 to city 1 on Monday, and play 7 days.
 3rd week : fly from city 1 to city 2 on Monday, and play 7 days.
 Note:
 N and K are positive integers, which are in the range of [1, 100].
 In the matrix flights, all the values are integers in the range of [0, 1].
 In the matrix days, all the values are integers in the range [0, 7].
 You could stay at a city beyond the number of vacation days, but you should work on the extra days, which won't be counted as vacation days.
 If you fly from the city A to the city B and take the vacation on that day, the deduction towards vacation days will count towards the vacation days of city B in that week.
 We don't consider the impact of flight hours towards the calculation of vacation days.

 Solution:
 4D dynamic:
 dp[i, j, m, n], starting from i, ending at j, from week m to week n. 0 <= m <= n < k.
 dp[i, j, m, m]: if there is flight from i to j, max(days[i, m], days[j, m]), otherwise days[i, m].
 dp[i, j, m, n]: dp[i, x, m, n-1] + dp[x, j, n, n]
 return max dp(0, x, 0, k-1)
 Cost: O(n^2 * k^2)
 Only need 2D:
 dp(i, m): starting from city i and at mth week.


 iterate and backtrack:
 cost same as below.

 Naive:
 all possible itenarary from city 0 for k weeks. O(N^k).Excluding no flights cases and compare vacation days.
 */
public class MaxVacationDays {
    public static int solve(int[][] days, int[][] flights) {
        int n = flights.length;
        int k = days[0].length;
        int[][] dp = new int[n][k+1];

        for (int i = k-1; i>=0; i--) {
            for (int j = 0; j < n; j++) {
                int max = days[j][i] + dp[j][i+1];
                for (int m = 0; m < n; m++) {
                    if (flights[j][m] > 0 && days[m][i] + dp[m][i+1] > max) {
                        max = days[m][i] + dp[m][i+1];
                    }
                }
                dp[j][i] = max;
            }
        }
        return dp[0][0];
    }

    public static void main(String[] args) {
        int[][] flights = {{0,1,1},{1,0,1},{1,1,0}};
        int[][] days = {{1,3,1},{6,0,3},{3,3,3}};
        System.out.println(solve(days, flights));

    }
}
