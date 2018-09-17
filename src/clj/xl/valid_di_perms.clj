(ns xl.valid-di-perms
"
We are given S, a length n string of characters from the set {'D', 'I'}. (These letters stand for \"decreasing\" and \"increasing\".)
A valid permutation is a permutation P[0], P[1], ..., P[n] of integers {0, 1, ..., n}, such that for all
If S[i] == 'D', then P[i] > P[i+1], and
If S[i] == 'I', then P[i] < P[i+1]
How many valid permutations are there?  Since the answer may be large, return your answer modulo 10^9 + 7.

Solution 1:
Peak and valleys: find all peak and valleys. The top number or bottom number must be in one of those spots.
Then divide the sequence into two seqs to apply the rest of numbers.
f(s0) = sum: per peak(or valley) C(n, k) * f(s1) * f(s2)

dynamic programming:
dp(i, j) is the ith number pi has rank j, meaning numbers after pi has j numbers less than pi.
dp[n+1, n+1].
dp[0, i] = 1

consider d[4, 3]:
say S[3] = I -> d[3, 0]   + d[3, 1] + d[3, 2] + d[3, 3]
                [0123, 7] + [XXX, a]
           D -> d[3, 4]    + d[3, 5] + d[3, 6] + ... + d[3, n]
dp[1, 0] =I -> dp[0, 0] ; D -> dp[0, 1]
               (0, 1)           (1, 0)
dp[1, 1] =I -> dp[0, 1] + dp[0, 0]; D -> dp[0, 2] +
               (1, 2)      (0, 2)        (2, 1)
dp[1, 2] = I -> + dp[0, 2] + dp[0, 1] + dp[0, 0]; D ->  dp[0, 3]

dp[i, j] = I -> dp[i-1, j] + dp[i-1][0]; D -> dp[i -1, j+1]
         = dp[i, j-1] + dp[i-1, j]     ;

note that j < n+1-i, otherwise is 0.

"
  (:require [clojure.math.combinatorics :as combo])
  )

(def to-p identity)
(def from-p to-p)
(defn to-v [index] (dec (- index)))
(def from-v to-v)

(defn  pivots
  "return array of indices, positive means it's peak, negative for valley."
  [dis]
  (let [n (count dis)
        is-pivot
          (fn [index check-peak]
            (if (or (neg? index) (> index n))
              false
              (let [[left-ch ch] (if check-peak [\I \D] [\D \I])
                    left-check (if (zero? index) (constantly true) #(= (nth dis (dec %)) left-ch))
                    right-check (if (= n index) (constantly true) #(= (nth dis %) ch))
                    ]
                (every? identity ((juxt left-check right-check) index))
                )
              )
            )
        ]
    (reduce
      (fn [res index]
        (cond
          (is-pivot index true) (conj res (to-p index))
          (is-pivot index false) (conj res (to-v index))
          :else res
          )
        )
      []
      (range (inc n))
      )
    )
  )

(defn pvs-in-range [dis from-index to-index]
  (let [pvs (pivots (subvec dis from-index to-index))]
    (mapv #(if (neg? %) (- % from-index) (+ % from-index)) pvs)
    )
  )

(defn combo [n i]
  (combo/count-combinations (range n) i)
  )

(defn count-perms-range [from to dis]
  (cond
    (> from to) 1
    (= from to) 1
    :else
    (let [                                                  ;_ (println "dis: " dis ", from: " from ", to: " to)
          ]
      (reduce
        (fn [res p]
          (let [l (count-perms-range from (dec p) dis)
                r (count-perms-range (inc p) to dis)
                k (- to from)
                m (combo k (- p from))
                ]
            ;            (println "res: " res ", l: " l ", r:" r ", m: " m ", from: " from ", to: " to ", p: " p)
            (mod (+ res (* m l r)) 1000000007)
            )
          )
        0
        (filter #(or (pos? %) (zero? %)) (pvs-in-range dis from to)))
      )
    )
  )

(defn count-perms [dis]
  (count-perms-range 0 (count dis) dis)
  )

(defn do-pvs []
  (doseq [dis ["" "I" "D" "ID" "DI" "II" "DD" "IDI" "DID" "III" "DDD" "IID"]
          [from to] [[0 0] [0 1] [0 2] [1 2] [0 10] [3 5]]]
    (println)
    (println "dis:" dis)
    (println "range: " [from to] ", pvs: " (pvs-in-range dis from to))
    )
  )

(defn do-count []
  (doseq [dis ["I" "D" "ID" "DI" "II" "DD" "IDI" "DID" "III" "DDD" "IID"]
          ]
    (println)
    (println "dis:" dis)
    (println "cnt:" (count-perms (vec dis)))
    )
  )


;(do-pvs)
(do-count)
;(count-perms (vec "DD"))
(comment
  )

