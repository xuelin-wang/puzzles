(ns xl.PartitionAlgorithm)
"Partition a multiset of numbers into two subsets with same total.
 Optimization version of the problem is to minimize diff of the sums.
 This is NP hard but can be solved in pseudo polynomial practically.
 The pseudo comes from the fact that it also depends on size of summed values.
 Key insight is P(i,j): there exists subset of {x1 .. xj} summed to i.
 P(i,j) <=> P(i, j-1) || P(i-xj, j-1).
 Looking for P(floor(K/2), n) by constructing table of size K/2 + 1 by n + 1.
 Called the easiest NP hard problem."

(defn divide [multiset]
  (let [n (count multiset)
        k (apply + multiset)
        half-k (int (/ k 2))
        n1 (inc n)
        half-k1 (inc half-k)
        answer-table (make-array Object half-k1 n1)
        false-answer {:check false}
        ]
    ; init answer-table[0, r] to be true, answer-table[r, 0] to be false except answer-table[0, 0]
    (doseq [ii (range n1)]
      (aset answer-table 0 ii {:check true :elems []}))
    (doseq [ii (range 1 half-k1)]
      (aset answer-table ii 0 {:check false}))

    ;recursively calculate answer-table elems by P(i, j) rule
    (doall
      (for [ii (range 1 half-k1) jj (range 1 n1)]
        (let [x-jj-1 (nth multiset (dec jj))
              answer-ii-jj-1 (aget answer-table ii (dec jj))
              answer1 (if (< ii x-jj-1) nil (aget answer-table (- ii x-jj-1) (dec jj)))
              checked-answer (cond
                               (:check answer-ii-jj-1) answer-ii-jj-1
                               (:check answer1) {:check true :elems (conj (:elems answer1) x-jj-1)}
                               :else false-answer)]
          (aset answer-table ii jj checked-answer)
          )
        )
      )
    (aget answer-table half-k n)
    )
  )

(defn samples []
  (doseq [multiset [
                    [4 5 6 7 8]
                    [3 1 1 2 2 1]
                    [2 5]
                    ]]
    (println "multiset: " multiset)
    (let [{:keys [check elems]} (divide multiset)]
      (if check
        (println "solution: " elems)
        (println "No solution.")
        )
      )
    )
  )

;(samples)