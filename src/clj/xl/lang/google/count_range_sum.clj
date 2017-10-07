"
Given an integer array nums, return the number of range sums that lie in [lower, upper] inclusive.
Range sum S(i, j) is defined as the sum of the elements in nums between indices i and j (i ≤ j), inclusive.

Note:
A naive algorithm of O(n2) is trivial. You MUST do better than that.

Example:
Given nums = [-2, 5, -1], lower = -2, upper = 2,
Return 3.
The three ranges are : [0, 0], [2, 2], [0, 2] and their respective sums are: -2, -1, 2.

Analysis:
continue counting subarrays ending at current index i using a BST.
They are the seqs ending at i-1 plus one more. Although the neq sums are the node number + accumulating sums
Each node will have count of nodes of subtree rooted here.
"

(ns xl.lang.google.count-range-sum
  (:import (java.util Collections))
  )

(defn range-sums [arr low high]
  (let [n (count arr)
        state
        (reduce
          (fn [{:keys [sum sums range-count] :as state} index]
            (let [item (nth arr index)
                  sum (+ sum item)
                  delta (- item sum)
                  sums (sort (conj sums delta))
                  q-low (- low sum)
                  q-high (- high sum)
                  bs (fn [arr key] (Collections/binarySearch arr key))
                  [l-index0 h-index0] (map (partial bs sums) [q-low q-high])
                  [l-index h-index] (map #(if (neg? %) (dec (- %)) %) [l-index0 h-index0])
                  range-count (+ range-count (+ (- h-index l-index) (if (neg? h-index0) 0 1)))
                  ]
              {:sum sum :sums sums :range-count range-count}
              )
            )
          {:sum 0 :sums [] :range-count 0}
          (range n))]
    (:range-count state)
    )
  )

(defn samples []
  (let [nums [-2, 5, -1]
        low -2
        high 2]
    (println (range-sums nums low high))
    )
  )

(samples)



