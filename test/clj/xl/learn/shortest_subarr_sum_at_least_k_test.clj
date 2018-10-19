(ns xl.learn.shortest-subarr-sum-at-least-k-test
  (:require
    [xl.shortest-subarr-sum-at-least-k :as shortest]
    [clojure.test :refer :all]
    [clojure.test.check :as tc]
    [clojure.test.check.generators :as gen]
    [clojure.test.check.properties :as prop]
    [clojure.test.check.clojure-test :refer [defspec]]
    ))


  (defn naive-min-range1 [nums k]
        (reduce
          (fn [res i]
            (let [m-d
                  (reduce
                    (fn [res j]
                      (let [s (apply + (map #(nth nums %) range (j (inc i))))
                            d (- i j -1)]
                        (if (and (>= s k) (< d res)) d res)
                        )
                      )
                    (Integer/MAX_VALUE)
                    (range i))
                  ]
              (if (< m-d res) m-d res)
              )
            )
          (Integer/MAX_VALUE)
          (range (count nums)))
        )

  (defn naive-min-range [nums k]
        (let [answer (naive-min-range1 nums k)]
          (if (= answer (Integer/MAX_VALUE)) -1 answer)
          )
        )

  (def good
       (prop/for-all [v (gen/not-empty (gen/vector gen/int)) k gen/int]
                     (= (shortest/min-range v k) (naive-min-range v k))
                     )
       )

;(tc/quick-check 100 good-min)
(defspec check-good-sum
         100
         good)