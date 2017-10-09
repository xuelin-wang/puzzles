"
See java class of similar name.
There are various algorithms with various optimizations.
This solution attempts to use binary indexed tree.
precompute return results of precompute.
update and query do 2d sum operations.
"
(ns xl.lang.google.range2d-sum)

(defprotocol sums
  "2d matrix rectangles sum"
  (precompute [this matrix] "precompute to speed up update/query")
  (change [this rc val] "update state for updating matrix element i,j to val")
  (query [this rc0 rc1] "query sum of rectangle from r0,c0 to r1,c1, inclusive")
  )

(deftype BitSums [state]
  sums
  (precompute [this matrix]
    (let [row (count matrix)
          col (count (first matrix))
          _ (reset! state {:row row :col col :sums {}})
          ]
      (doseq [r (range row) c (range col)]
        (change this [r c] (nth (nth matrix r) c))
        )
      )
    )

  (change [this [r c] delta]
    ; (println "sums before change: " (:sums @state))
    ;(println "chaning " [r c] " val: " delta)
    (let [{:keys [sums row col]} @state
          br (int (inc r))
          bc (int (inc c))
          next-i (fn [i] (+ i (bit-and i (- i))))
          sums
          (reduce
            (fn [sums [y x]]
              ;         (println "updating " [y x] " with delta: " delta)
              (update sums [y x] #(+ delta (or % 0)))
              )
            sums
            (for [y (take-while #(<= % row) (iterate next-i br)) x (take-while #(<= % col) (iterate next-i bc))]
              [y x]
              )
            )
          ]
          (swap! state #(assoc % :sums sums))
      )
    )

  (query [this [r0 c0] [r1 c1]]
    ; (println "sums before querying for " [r0 c0] [r1 c1]  " : " (:sums @state))
    (let [prev-i #(- % (bit-and % (- %)))
          sums (:sums @state)
          sum (fn [[r c]]
                ;(println "querying: r, c: " [r c])
                (if (or (zero? r) (zero? c))
                  0
                  (reduce
                    (fn [total rc]
                      ; (println (str "adding for rc: " rc ": " (get sums rc)))
                      (+ total (or (get sums rc) 0))
                      )
                    0
                    (for [y (take-while pos? (iterate prev-i r))  x (take-while pos? (iterate prev-i c))] [y x])
                    )
                  )
                )
          [br0 bc0 br1 bc1] (map #(inc (int %)) [(dec r0) (dec c0) r1 c1])
          [sum00 sum01 sum10 sum11] (map #(sum %) [[br0 bc0] [br0 bc1] [br1 bc0] [br1 bc1]])
          ]
      (- (+ sum00 sum11) (+ sum01 sum10))
      )
    )
  )

(defn samples []
(let [matrix [
              [3 0 1 4 2]
              [5 6 3 2 1]
              [1 2 0 1 5]
              [4 1 0 1 7]
              [1 0 3 0 5]
              ]
      sum2d (BitSums. (atom {}))
      _ (precompute sum2d matrix)
      res (query sum2d [2 1] [4 3])
      ]
  (println (str "result is: " res))
  )
  )

(samples)
