(ns xl.shortest-subarr-sum-at-least-k
  "
  Return the length of the shortest, non-empty, contiguous subarray of A with sum at least K.

  If there is no non-empty subarray with sum at least K, return -1.



  Example 1:

  Input: A = [1], K = 1
  Output: 1
  Example 2:

  Input: A = [1,2], K = 4
  Output: -1
  Example 3:

  Input: A = [2,-1,2], K = 3
  Output: 3


  Note:

  1 <= A.length <= 50000
  -10 ^ 5 <= A[i] <= 10 ^ 5
  1 <= K <= 10 ^ 9

  Solution:
  * Naive:
    n^2 time and linear space.
  * Optimization:
  for array a, construct sum array b with each bi = sum(a from 0 to i-1). b = [0, a0, a0 + a1, ...)
  This can be done in O(n) in both time and space.
  Then problem becomes find smallest j-i where b[j] - b[i] >= k.
  Now we look at i = 0, let j moving from 1 rightwards until we find a sum b[j] - b[0] >= k.
  Then we let i move rightwards until b[j] - b[i] < k.
      property 1: for x1 < x2 < y, if b[x1] >= b[x2], x1 cannot be answer for y. so answer for y is in increasing sequence p
      property 2: once x is already a potential answer for y1 < y2, then x can not be answer for y2.
  We maintain a increasing queue p. when adding new element at index y, pop from end of queue.
  if b[y] - b[head] >= k, pop head out of queue.
  "
  )

(defn min-range1 [nums k]
  (let [
        b (reduce
            (fn [res n]
              (conj res (+ n (nth res (dec (count res)))))
              )
            [0] nums)
        ]
    (loop [j 1 q [0] answer (Integer/MAX_VALUE)]
      (let [bj (nth b j)
            new-q (vec (take-while #(< (nth b %) bj) q))
            new-q (conj new-q j)
            drop-index  (loop [i 0]
                (if (>= (- bj (nth b (nth new-q i))) k)
                  (recur (inc i))
                  i
                  )
                )
            new-q (subvec new-q drop-index)
            new-answer (if (pos? drop-index) (min (inc (- j drop-index)) answer) answer)
           ]
        (if (= j (count nums)) new-answer (recur (inc j) new-q new-answer) )
        )
      )
    )
  )

(defn min-range [nums k]
  (let [answer (min-range1 nums k)]
    (if (= answer (Integer/MAX_VALUE)) -1 answer)
    )
  )

(defn do-samples []
  (doseq [
          [nums k] [
                    [[1] 1]
                    [[1 2] 4]
                    [[2 -1 2] 3]
                    ]
          ]
    (println "[nums k]: " [nums k])
    (println "answer: " (min-range nums k))
    )
  )

;(do-samples)
