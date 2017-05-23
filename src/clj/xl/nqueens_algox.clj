(ns xl.nqueens-algox
  (:require [clojure.string :as ss]
            [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as g]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as tcg]
            [clojure.set])
  (:import [xl.learn AlgoX]))

(defn- queens-covered [row col m n]
  (let [diag (let [delta (min row (- (dec n) col))
                   [end-row end-col] [(- row delta) (+ col delta)]
                   ]
               (if (zero? end-row) (dec end-col) (+ (- n 2) end-row))
               )
        antidiag (let [delta (min row col)
                       [end-row end-col] [(- row delta) (- col delta)]
                       ]
                   (if (zero? end-row) (- (- n 2) end-col) (+ (- n 2) end-row))
                   )
        ]
    [row col diag antidiag]
    )
  )

(defn- queens-row-to-point [row n]
  (let [r (int (/ row n))
        c (mod row n)]
    [r c])
  )

(defn- queens-matrix [n]
  (let [
        ranks (into [] (repeat n (int 0)))
        files ranks
        diags-count (- (* 2 n) 3)
        diags (into [] (repeat diags-count (int 0)))
        antidiags diags
        matrix-rows
        (for [row (range n) col (range n)]
          (let [[r c diag antidiag] (queens-covered row col n n)
                row-cover (assoc ranks r (int 1))
                col-cover (assoc files c (int 1))
                diag-cover (if (<= 0 diag (dec diags-count))  (assoc diags diag (int 1)) diags)
                antidiag-cover (if (<= 0 antidiag (dec diags-count))  (assoc antidiags antidiag (int 1)) antidiags)
                ]
            (concat row-cover col-cover diag-cover antidiag-cover)
            )
          )
        ]
    [matrix-rows (range (* 2 n))]
    )
  )

(defn queens-show-solution [points n]
  (let [sorted (sort #(< (first %1) (first %2)) points)
        empty-row (into [] (repeat n (str "O ")))
        solution-strs
        (for [row (range n)]
          (let [point (nth sorted row)
                _ (if (not= (first point) row) (throw (Exception. (str "Row " row " is not covered by solution: " sorted))))
                solution-row (assoc empty-row (second point) "* ")
                ]
            (ss/join "" solution-row)
            )
          )
        ]
      (println (ss/join "\n" solution-strs))
    )
  )

(defn queens [n use-heuristic find-first]
  (let [[matrix primary-cols] (queens-matrix n)
        rows-solutions (AlgoX/solveGrid matrix nil primary-cols use-heuristic find-first)
        ]
    (map
      (fn [rows]
        (map
          #(queens-row-to-point % n)
          rows)
        )
      rows-solutions)
    )
        )

(defn soduku [problem-arr use-heuristic find-first]
        )

(defn- queens-samples []
  (doseq [[n use-heuristic find-first]
          [
           [4 true true]
           [8 true false]
           ]
          ]
    (println "n: " n)
    (println "use-heuristic: " use-heuristic)
    (println "find-first: " find-first)
    (println "solutions: ")
    (doall
      (map
        (fn [solution]
          (println solution)
          (queens-show-solution solution n)
          )
        (queens n use-heuristic find-first)
        )
      )
    )
  )

;(queens-samples)
