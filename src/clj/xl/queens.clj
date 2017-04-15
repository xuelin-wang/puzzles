(ns xl.queens)

(defn queens [n]
  (let [place-queens
        (fn place-queens [k]
          (if (= k 0)
            [[]]
            (let [valid (fn [col cols]
                          (let [m (count cols)
                                check-cols (map-indexed (fn [idx itm] (and (not= itm col) (not= (Math/abs (- col itm)) (- m idx)))) cols)
                                result (every? identity check-cols)
                                ]
                            result
                            ))
                  placed (place-queens (dec k))
                  new-placed
                    (reduce (fn [results cols]
                              (concat results (map #(if (valid % cols) (conj cols %) []) (range n)))
                              )
                            []
                            placed)
                  filtered-placed (filter (complement empty?) new-placed)
                  ]
              filtered-placed
              )
            )
          )
        ]
    (place-queens n)
    )
  )

(defn show-results [row-cols-list]
  (dorun
    (map
      (fn [row-cols]
        (dorun (map (fn [[row col]]
                      (let [n (count row-cols)
                            s (take n (repeat "* "))
                            s2 (concat (take col s) ["X "] (drop (inc col) s))
                            ] (println (apply str s2)))
                      ) row-cols))
        (println)
        ) row-cols-list)
    )
  )


(comment

  (let [cols-list (queens 8)
        row-cols-list (map (fn [cols] (map-indexed #(list %1 %2) cols)) cols-list)
        ]
    (show-results row-cols-list)
    (println "number of solutions:" (count row-cols-list))
    )
  )