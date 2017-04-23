(ns xl.max-1-line-in-matrix)

(defn elem [matrix ii jj]
  (nth (nth matrix ii) jj)
  )

(defn- out-range [ii jj m n]
  (not (and (<= 0 ii (dec m)) (<= 0 jj (dec n)))))

(defn- iterate-point [ii jj factor-x factor-y m n]
  (if (out-range ii jj m n)
    (case [factor-x factor-y]
      [0 1] [(inc ii) 0 false]
      [1 0] [0 (inc jj) false]
      [1 -1] (let [i+j (+ ii jj)
                   row0-col (inc i+j)
                   lastcol-row (- row0-col (dec n))
                   ]
               (if (< row0-col n) [0 row0-col false] [lastcol-row (dec n) false])
               )
      [1 1] (let [i-j (- ii jj)
                  row0-col (- (inc i-j))
                  col0-row (inc i-j)
                  ]
              (if (>= row0-col 0) [0 row0-col false] [col0-row 0 false])
              )
      )
    [ii jj true]
    )
  )

(defn- next-point [ii jj row-factor col-factor m n delta]
  (let [new-ii (+ ii (* delta row-factor))
        new-jj (+ jj (* delta col-factor))
        [it-ii it-jj in-range] (iterate-point new-ii new-jj row-factor col-factor m n)]
    (lazy-seq (cons [it-ii it-jj in-range]
                    (next-point it-ii it-jj row-factor col-factor m n delta))
              )
    ))

(defn walk [matrix m n start-i start-j row-factor col-factor]
  (reduce
    (fn [{:keys [max-len curr-len]} [ii jj in-range]]
      (if (out-range ii jj m n)
        (reduced {:max-len (max max-len curr-len) :curr-len 0})
        (if in-range
          (if (zero? (elem matrix ii jj))
            {:max-len (max max-len curr-len) :curr-len 0}
            {:max-len max-len :curr-len (inc curr-len)}
            )
          {:max-len (max max-len curr-len) :curr-len (if (zero? (elem matrix ii jj)) 0 1)}
          )
        )
      )
    {:max-len 0 :curr-len 0}
    (next-point start-i start-j row-factor col-factor m n 1)
    )
  )

(defn max-1s [matrix]
  (let [m (count matrix)
        n (if (zero? m) 0 (count (nth matrix 0)))
        walk-results
          (for [[factor-x factor-y start-x start-y] [[1 0 -1 0] [0 1 0 -1] [1 -1 -1 1] [1 1 -1 (- n 2)]]]
            (walk matrix m n start-x start-y factor-x factor-y)
            )
        ]
        (apply max (map :max-len walk-results))
    )
  )

(defn samples []
  (doseq [matrix
          [[]
           [[]]
           [[0 0] [0 1]]
           [[0 1 1] [1 1 0] [1 0 1]]
           [[1 0 0] [0 0 0] [0 0 1]]
           [[1 0 1] [0 0 0] [1 0 1]]
           [[0 0 0] [1 1 0] [0 0 1]]
           [[1 1 0] [1 1 0] [1 0 1]]
           [[0 1 1] [1 1 0] [1 0 1]]
           [[0 1 1] [0 0 0] [1 1 1]]
           ]
          ]
    (println "matrix: " matrix ", max 1s: " (max-1s matrix))
    )
  )

(samples)