(ns xl.learn.min-seq-test
  (:require
    [xl.min-seq]
    [clojure.test :refer :all]
    [clojure.test.check :as tc]
    [clojure.test.check.generators :as gen]
    [clojure.test.check.properties :as prop]
    [clojure.test.check.clojure-test :refer [defspec]]
    ))

(def good-min
  (prop/for-all [v (gen/not-empty (gen/vector gen/int))]
                (let [m (xl.min-seq/min-seq v)
                      l (count v)
                      sums
                        (for [i (range l) j (range (inc i) (inc l))]
                          (apply + (subvec v i j))
                          )
                      ]
                  (and
                    (every? #(>= % m) sums)
                    (some #(= % m) sums)
                    )
                  )
                )
  )

;(tc/quick-check 100 good-min)
(defspec check-good-min
         100
         good-min)