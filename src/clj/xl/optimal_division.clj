(ns xl.optimal-division
  (:require [clojure.math.combinatorics :as combo]
            [clojure.string]
            [clojure.spec :as s]
            [clojure.spec.gen :as g]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as tcg]
            ))

(defn- get-min-max [min? start ncount min-maxes]
  ((if min? :min :max) (get min-maxes [start ncount]))
  )

(defn- calc-min-max [min? nums start ncount min-maxes]
  {:order [] :result nil}
  (case ncount
    1
      {:result (nth nums start) :order [start]}
    (reduce
      (fn [result-order last-div]
        (let [left-count (inc last-div)
              left-data (get-min-max min? start left-count min-maxes)
              left-result (:result left-data)
              right-count (- ncount left-count)
              right-data (get-min-max (not min?) (+ start left-count) right-count min-maxes)
              right-result (:result right-data)
              new-result (/ left-result right-result)
              better? ((if min? < >) new-result (:result result-order))
              ]
              (if better? {:result new-result :order [(:order left-data) (:order right-data)]} result-order)
          )
        )
      {:result (if min? (Double/MAX_VALUE) (- (Double/MAX_VALUE)))
       :order []
       }
      (range (dec ncount))
      )
    )
  )

(defn- calc-count-min-max [nums ncount min-maxes]
  (let [nlen (count nums)]
    (reduce
      (fn [result start]
        (let [min-data (calc-min-max true nums start ncount min-maxes)
              max-data (calc-min-max false nums start ncount min-maxes)]
            (assoc result [start ncount] {:min min-data :max max-data})
          )
        )
      min-maxes
      (range (inc (- nlen ncount)))
      )
    )
  )

(defn- calc-all-min-max [nums]
  (reduce
    (fn [result ncount_1]
      (calc-count-min-max nums (inc ncount_1) result)
      )
    {}
    (range (count nums))
    )
  )

(defn- output-calc [nums order]
  (if (> (count order) 1)
    (let [[a b] order]
      ["(" (output-calc nums a) "/" (output-calc nums b) ")"]
      )
    [(nth nums (first order))]
    )
  )

(defn max-div [nums]
  (let [min-maxes (calc-all-min-max nums)
        max-data (:max (get min-maxes [0 (count nums)]))
        ]
    max-data
    )
  )

(defn output-max-div [nums]
  (let [max-data (max-div nums)
        max-order (:order max-data)
        result-str   (apply str (output-calc nums max-order))
        ]
    (println (str nums))
    (println (str "max-data: " max-data))
    (println result-str)
    )
  )

(defn do-main []
  (doseq [nums [
                [1 3 5 2 4]
                [1000, 100, 10, 2]
                [2 1 2 2]
                [2 2 2 2 1 2]
                ]]
    (output-max-div nums)
    )
  )


(s/def ::result number?)
(s/def ::order vector?)

(defn calc-permu [porder nums]
  (if (= 1 (count nums))
    (first nums)
    (let [next-div (first porder)
          q (/ (nth nums next-div) (nth nums (inc next-div)))]
      (calc-permu (map #(if (< % next-div) % (dec %)) (rest porder)) (concat (take next-div nums) [q] (drop (+ 2 next-div) nums)))
      )
    )
  )

;; (stest/check `di/max-div) hangs, why?
(s/fdef max-div
        :args (s/cat :nums (s/coll-of pos-int? :min-count 1 :kind vector? :gen-max 4))
        :ret (s/keys :req-un [::result ::order])
        :fn (s/and
              #(let [ret (:ret %)
                     order (:order ret)
                     flat-order (flatten order)
                     nums (-> % :args :nums)
                     ]
                 (= flat-order (range (count nums)))
                 )

              ))

(comment
  #(let [ret (:ret %)
         result (:result ret)
         nums (-> % :args :nums)
         permu (combo/permutations (range (dec (count nums))))
         permu-result (calc-permu permu nums)
         ]
     (every? (fn [porder] (>= result (calc-permu porder nums))) permu)
     )
  )

;(do-main)
