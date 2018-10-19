(ns xl.reverse-pairs
  "Given an array nums, we call (i, j) an important reverse pair if i < j and nums[i] > 2*nums[j].

  You need to return the number of important reverse pairs in the given array.

  Example1:

  Input: [1,3,2,3,1]
  Output: 2
  Example2:

  Input: [2,4,3,5,1]
  Output: 3
  Note:
  The length of the given array will not exceed 50,000.
  All the numbers in the input array are in the range of 32-bit integer.

  Solution:
  1. Naive:
  n^2 time and constant space. comparing each pair.

  2. suppose we have a binary search tree. (L < M < R). Each node also maintains number of nodes with this value.
  For next value v, we traverse nodes with values < 2v to figure out number of important pairs with this j.
  Time is log j (average. not necessarily balanced tree). So total time is n log n.
  "
  )

