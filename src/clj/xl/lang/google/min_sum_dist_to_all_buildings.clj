"
You want to build a house on an empty land which reaches all buildings in the shortest amount of distance. You can only move up, down, left and right. You are given a 2D grid of values 0, 1 or 2, where:

Each 0 marks an empty land which you can pass by freely.
Each 1 marks a building which you cannot pass through.
Each 2 marks an obstacle which you cannot pass through.
For example, given three buildings at (0,0), (0,4), (2,2), and an obstacle at (0,2):

1 - 0 - 2 - 0 - 1
|   |   |   |   |
0 - 0 - 0 - 0 - 0
|   |   |   |   |
0 - 0 - 1 - 0 - 0
The point (1,2) is an ideal empty land to build a house, as the total travel distance of 3+3+1=7 is minimal. So return 7.

Note:
There will be at least one building. If it is not possible to build such house according to the above rules, return -1.

Analysis:
for each of the buildings,
starting from steps from 0, incrementing 1 each round.
each building location [i, j] -> {k (min steps) -> {sets of free spaces} }
until no changes.
"
(ns xl.lang.google.min-sum-dist-to-all-buildings
  (:require [clojure.set :as set])
  )

(defn- parse-matrix [matrix]
  (let [m (count matrix)
        n (count (first matrix))
        v [nil nil nil]
        vals (for [r (range m) c (range n)]
          (assoc v (nth (nth matrix r) c) [r c])
          )
        rcs (apply (partial map vector) vals)
        rc-sets (map #(set (keep identity %)) rcs)
        ]
    rc-sets
    )
  )

(defn optimal-cell [matrix]
  (let [m (count matrix)
        n (count (first matrix))
        [frees builds walls] (parse-matrix matrix)
        neighbors (fn [[i j]] (for [[di dj] [[0 1] [1 0] [0 -1] [-1 0]] :let [i1 (+ i di) j1 (+ j dj)]
                                   :when (and (< i1 m) (< j1 n) (frees [i1 j1]))]
                               [i1 j1]))
        build-ds (fn [b]
                   (loop [ds {0 #{b} :all #{b}} k 0]
                     (let [k-set (ds k)
                           all (:all ds)
                           k1-set (reduce
                                      (fn [res [i j]]
                                        (let [nbs (neighbors [i j])
                                              nbs (remove all nbs)]
                                              (set/union res (set nbs))
                                          )
                                        )
                                      #{}
                                      k-set)
                           ]
                       (if (empty? k1-set)
                         ds
                         (recur (assoc ds (inc k) k1-set :all (set/union all k1-set)) (inc k))
                         )
                       )
                     )
                   )
        all-ds (into {} (map (fn [b] [b (dissoc (build-ds b) :all)]) builds))
        ;        _ (println (str "all-ds: " all-ds))
        find-d (fn [f b]
                 (ffirst (filter (fn [[k v]] (v f)) (all-ds b)))
                 )
        free-sums (into {}
                        (map
                          (fn [f]
                            (let [d  (reduce (fn [sum b]
                                               (let [d (find-d f b)]
                                                 ; (println (str "d: " d " for b: " b ", f: " f ", sum: " sum))
                                                 (if (nil? d) (reduced d) (+ d sum)))
                                               )
                                             0 builds)
                                  ]
                              [f d]
                              )
                            )
                          frees)
                        )
        reachable (filter (fn [[f d]] (some? d)) (seq free-sums))
        [min-f min-d] (apply (partial min-key second) (seq reachable))
        ]
    min-f
    )
  )


(defn samples []
         (let [matrix [
                       [1 0 2 0 1]
                       [0 0 0 0 0]
                       [0 0 1 0 0]
                       ]]

           (println (str "optimal cell: " (optimal-cell matrix)))
           )
         )

(samples)