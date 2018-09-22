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
b[j] be the number of subarrays with a[j] as the left-most min. The reason for  left-most is to prevent double counting.

"
  (:require [clojure.math.numeric-tower :as math])
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

(defn sum-mins-2 [nums]
  (let
    [n (count nums)
     {:keys [counts stack]}
     (reduce
       (fn [{:keys [counts stack]} i]
         (let [ai (nth nums i)]
           (loop [counts1 counts stack1 stack]
             (if (or (empty? stack1) (<= (nth nums (last stack1)) ai))
               {:counts counts1 :stack (conj stack1 i)}
               (let [s (count stack1)
                     l (nth stack1 (dec s))
                     p (if (= s 1) -1 (nth stack1 (- s 2)))
                     ]
                 (recur (assoc counts1 l [(inc p) (dec i)]) (subvec stack1 0 (dec s)))
                 )
               )
             )
           )
         )
       {:counts {} :stack []}
       (range n))
       counts1
         (loop [counts {} stack stack]
           (if (empty? stack)
             counts
             (let [s (count stack)
                   k (dec (count nums))
                   j (nth stack (dec s))
                   i (if (= s 1) 0 (inc (nth stack (- s 2))))
                   ]
               (recur (assoc counts j [i k]) (subvec stack 0 (dec s)))
               )
             )
           )
     ]
    (reduce
      (fn [sum [i [j k]]]
        (+ sum (* (nth nums i) (* (inc (- i j)) (inc (- k i)))))
        )
      0
      (seq (into counts counts1))
      )
    )
  )

(defn do-sum []
  (doseq [nums [
                [3 1 2 4]
                ]]
    (println "nums: " nums)
    (println "sum: " (sum-mins-2 nums))
    )
  )

;(do-sum)
