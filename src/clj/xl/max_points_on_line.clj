(ns xl.max-points-on-line
  [:require [clojure.set :as set]]
  )
"
Given n points on a 2D plane, find the maximum number of points that lie on the same straight line.
"
(defn- on-line [point line]
  (let [[[x1 y1] [x2 y2]] line
        [x3 y3] point]
    (or
      (and (= x1 x3) (= y1 y3))
      (and (= x2 x3) (= y2 y3))
      (= x1 x2 x3)
      (and (and (not= x1 x2) (not= x2 x3))
           (= (/ (- y1 y2) (- x1 x2)) (/ (- y3 y2) (- x3 x2)))
           )
      )
    )
  )

(defn- merge-line [line1 line2]
  (let [[p11 p12] (seq line1)
        [p21 p22] (seq line2)]
    (if (and (on-line p21 [p11 p12]) (on-line p22 [p11 p12]))
      (set/union (set line1) (set line2))
      nil
      )
    )
  )

(defn- merge-lines-step [line other-lines]
  (loop [changed? false other-lines (seq other-lines) result-lines #{}]
    (cond
      changed? {:changed true :step-lines (set/union result-lines other-lines)}
      (empty? other-lines)
        {:changed false :step-lines (conj result-lines line)}
      :else
      (let [other-line (first other-lines)
            merged (merge-line line other-line)
            ]
        (if (nil? merged)
          (recur false (rest other-lines) (conj result-lines other-line))
          (recur true (rest other-lines) (conj result-lines merged))
          )
        )
      )
    )
  )

(defn- merge-lines [lines]
  (loop [curr-lines (set lines) pending-lines (seq lines)]
    (if (empty? pending-lines)
      curr-lines
      (let [line (first pending-lines)
            {:keys [changed step-lines]} (merge-lines-step line (set (remove #{line} curr-lines)))]
        (if changed
          (recur step-lines step-lines)
          (recur step-lines (rest pending-lines))
          )
        )
      )
    )
  )

(defn max-on-line [points]
  (let [lines
        (for [i (range (dec (count points))) j (range (inc i) (count points))]
          #{(nth points i) (nth points j)}
          )
        merged (merge-lines lines)
        ]
    (last (sort-by count (seq merged)))
    )
  )

(defn samples []
  (doseq [sample
        (for [points
              [
               [[1 1] [2 2] [1 3] [3 3] [3 1]]
               ]
              ]
          [points (max-on-line points)]
          )
        ]
    (println "points: " (first sample))
    (println "max on line: " (second sample))
    )
  )

(samples)