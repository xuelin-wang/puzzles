(ns xl.reach)

(defn- dist [[x1 y1] [x2 y2]]
  (let [d-x (Math/abs (- x2 x1))
        d-y (Math/abs (- y2 y1))]
    (max d-x d-y)
    )
  )

(defn- move [x1 x2]
  (cond
    (< x1 x2) 1
    (> x1 x2) -1
    :else 0
    )
  )

(defn- path [[x1 y1] [x2 y2]]
  (loop [results [[x1 y1]]]
    (let [[last-x last-y] (last results)
          move-x (move last-x x2)
          move-y (move last-y y2)
          ]
      (if (= [0 0] [move-x move-y])
        results
        (recur (conj results [(+ last-x move-x) (+ last-y move-y)]))
        )
      )
    )
  )

(defn solve[points]
  (loop [results [(first points)] remaining (rest points)]
    (if (empty? remaining)
      results
      (let [last-point (last results)
            dists (map #(dist last-point %) remaining)
            min-index (first (apply min-key second (map-indexed vector dists)))
            next-point (nth remaining min-index)
            next-path (path last-point next-point)
            [points1 points2] (split-at min-index remaining)
            ]
        (recur (into [] (concat results (rest next-path))) (concat points1 (rest points2)))
        )
      )
    )
  )

(println (solve [[0 0] [10 4] [2 3] [1 2] [5 5] ]))

(comment
  (let [problems [
                  [[0 0]]
                  [[0 0] [0 1] [1 1]]
                  [[0 0] [2 3] [1 2] [5 5] [10 4]]
                  ]
        problem-paths (map (fn [problem] [problem (solve problem)])  problems)
        ]
    (dorun
      (map (fn [[problem path]]
             (println "points: " problem)
             (println "path: " path)
             )
           problem-paths
           )
      )
    )
  )
