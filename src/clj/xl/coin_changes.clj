(ns xl.coin-changes)
"Given a total and a list of coins, returns number of combinations of coins for the change total"

(defn changes-count
  "Use dynamic programming. construct c[i, j] for count of total j and coins from 0 to i."
  [coins total]
  (let [n (count coins)
        dp
          (reduce
            (fn [m t]
              (reduce
                (fn [m i]
                  (if (zero? t)
                    (assoc m [i t] 1)
                    (let [ci (nth coins i)
                          v-i1 (if (zero? i) 0 (m [(dec i) t]))]
                      (assoc m [i t]
                               (if (< t ci)
                                 v-i1
                                 (+ (m [i (- t ci)]) v-i1)
                                 )
                        )
                      )
                    )
                  )
                m
                (range n)
                )
              )
            {}
            (range (inc total)))
        ]
    (dp [(dec n) total])
    )
  )

(defn samples []
  (doseq [[coins t]
          [
           [[1 2] 2]
           [[1 2] 3]
           [[1 2] 4]
           [[1 2 5] 4]
           [[1 2 5] 10]
           [[2 5] 10]
           [[2 4] 9]
           ]
          ]
    (println "total: " t "coins: " coins)
    (println "count: " (changes-count coins t))
    )
  )

;(samples)