"Given an integer array, return the k-th smallest distance among all the pairs.
The distance of a pair (A, B) is defined as the absolute difference between A and B.

Example 1:
Input:
nums = [1,3,1]
k = 1
Output: 0
Explanation:
Here are all the pairs:
(1,3) -> 2
(1,1) -> 0
(3,1) -> 2
Then the 1st smallest distance pair is (1,1), and its distance is 0.
Note:
2 <= len(nums) <= 10000.
0 <= nums[i] < 1000000.
1 <= k <= len(nums) * (len(nums) - 1) / 2.
Discuss

Analysis:
Naive is to pick kth from O(n^2) pairs. time is O(n^2).
Optimization:
sort it: O(nlogn).
Then scan the sorted list,
fore each ai, only need look at the k diffs from ai to the previous k elements.
maintains current k smallest distances. Use max heap to pop max whenever adding a new one. that takes log k.
so total time is O(n log n) + O (n log k)

"

(ns xl.kth-smallest-distance)
