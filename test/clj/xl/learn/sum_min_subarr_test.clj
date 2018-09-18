(ns xl.learn.sum-min-subarr-test
  (:require [xl.sum-min-subarr :as sum]
            [clojure.test :refer :all]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :refer [defspec]]
            )
  )

(defn naive-sum [nums]
  (let [n (count nums)]
    (apply +
           (for [i (range n) j (range (inc i) (inc n))]
             (apply min (subvec nums i j)))
           )
    )
  )

(defn do-sum []
  (doseq [nums [
                [3 1 2 4]
                ]]
    (println "nums: " nums)
    (println "sum: " (naive-sum nums))
    )
  )

;(do-sum)

(def good-sum
  (prop/for-all [v (gen/not-empty (gen/vector gen/int))]
                (= (sum/sum-mins v) (naive-sum v))
                )
  )

;(tc/quick-check 100 good-min)
(defspec check-good-sum
         100
         good-sum)

