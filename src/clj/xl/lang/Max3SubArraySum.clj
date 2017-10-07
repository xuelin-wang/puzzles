"
In a given array nums of positive integers, find three non-overlapping subarrays with maximum sum.

Each subarray will be of size k, and we want to maximize the sum of all 3*k entries.

Return the result as a list of indices representing the starting position of each interval (0-indexed).
If there are multiple answers, return the lexicographically smallest one.

Example:
Input: [1,2,1,2,6,7,5,1], 2
Output: [0, 3, 5]
Explanation: Subarrays [1, 2], [2, 6], [7, 5] correspond to the starting indices [0, 3, 5].
We could have also taken [2, 1], but an answer of [1, 3, 5] would be lexicographically larger.
Note:
nums.length will be between 1 and 20000.
nums[i] will be between 1 and 65535.
k will be between 1 and floor(nums.length / 3).

Analysis:
think of the result is the optimal k subarray in each of the three segments in all three-subsections,
each subsection is at least k size.

find out optimal 2 ksubarr for each of the subarr from i: k to n-2k to the end

find out max k subarr from i to j takes time O(j-i).
find out max 2k subarr for all subarrays takes O(n^2).
so total takes O(n^2).

optimization:
derive new array with elements being the k subarr sums.
so for array of size n, W, the k-sum array, has n-k elements.
Now for W, need find i,j,m, where i+k <= j, j+k <= m, and max W(i) + W(j) + W(m).

dynamic programming, in linear time, can find max of W up to j, or max of W from end up to j.
Then a single scan can find max W(i,j,m).


"
(ns xl.lang.Max3SubArraySum)

(defn max-3k [arr k]
  (let [n (count arr)
        k-sums
        (let [k-sums-state      (reduce
                        (fn [{:keys [res sum]} index]
                          (let [new-sum (+ sum (nth arr index))
                                new-sum (if (< index k) new-sum (- new-sum (nth arr (- index k))))
                                new-res (if (< index (dec k)) res (conj res new-sum))
                                ]
                            {:res new-res :sum new-sum}
                            )
                          )
                        {:res [] :sum 0}
                        (range n)
                        )
              ]
          (:res k-sums-state)
          )

        maxes (fn [res j sum]
          (let [[last-j last-sum] (last res)]
            (if (or (nil? last-sum) (< last-sum sum))
              (conj res [j sum])
              (conj res [last-j last-sum])
              ))
          )

        pre-maxes
        (reduce-kv maxes [] k-sums)

        post-maxes
        (reduce-kv maxes [] (vec (reverse k-sums)))

        max-ijk
        (reduce
          (fn [{:keys [i j m max-sum] :as state} j1]
            (let [i1 (- j1 k)
                  m1 (+ j1 k)
                  last-sum-index (- n k)]
              ;(println (str "i1: " i1 ", j1: " j1 ", m1: " m1 ", last-sum-index: " last-sum-index))
              ;(println pre-maxes)
              ;(println post-maxes)
              (if (and (nat-int? i1) (<= m1 last-sum-index))
                (let [[pre-max-i pre-max-sum] (nth pre-maxes i1)
                      [post-max-i post-max-sum] (nth post-maxes (- last-sum-index m1))
                      ; _ (println (str "pre: " [pre-max-i pre-max-sum] ", post: " [post-max-i post-max-sum]))
                      new-sum (+ pre-max-sum  (nth k-sums j1) post-max-sum)]
                  (if (or (nil? max-sum) (< max-sum new-sum))
                    {:i pre-max-i :j j1 :m (- last-sum-index post-max-i) :max-sum new-sum}
                    state)
                  )
                state
                )
              )
            )
          {}
          (range (inc (- n k))))
        ]
    max-ijk
    )
  )

(defn samples []
  (let [res
        (max-3k [1,2,1,2,6,7,5,1] 2)
        ]
    (println res)
    )

  )

(samples)


