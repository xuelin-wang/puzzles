(ns xl.sum-min-subarr
"sum of minimum elements of every subarray
Example 1:
Input: [3,1,2,4]\nOutput: 17
Explanation: Subarrays are [3], [1], [2], [4], [3,1], [1,2], [2,4], [3,1,2], [1,2,4], [3,1,2,4].
Minimums are 3, 1, 2, 4, 1, 1, 2, 1, 1, 1.  Sum is 17

Solution:
dp[i] is the sum of mins of all subarrs of a[0..i]

dp[0] = a[0]

dp[i], need add mins of all subarrays with a[i] as last element.
a[i], min(a[i..i-1]), min(a[i..i-2]), ...min(a[i..0])
      min(a[i], a[i-1]), min(a[i], a[i-1..i-2]), ..., min(a[i], a[i-1..0])


O(n^2) space and O(n^2) time.

Optimize:
track local minimum, is the complexity worth it?
"
  )

(defn sum-mins [nums]
  (let [n (count nums)]
    (:sum
      (reduce
        (fn [{:keys [sum mins]} i]
          (let [ai (nth nums i)
                new-mins (into [ai] (mapv #(min % ai) mins))
                new-sum (apply + sum new-mins)
                ]
            {:mins new-mins :sum new-sum}
            )
          )
        {:sum 0 :mins []}
        (range n))
      )
    )
  )


(defn do-sum []
  (doseq [nums [
                 [3 1 2 4]
                 ]]
          (println "nums: " nums)
          (println "sum: " (sum-mins nums))
          )
  )

;(do-sum)
