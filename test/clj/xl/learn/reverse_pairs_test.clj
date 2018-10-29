(ns xl.learn.reverse-pairs-test
  (:require
    [xl.reverse-pairs :as pairs]
    [clojure.test :refer :all]
    [clojure.test.check :as tc]
    [clojure.test.check.generators :as gen]
    [clojure.test.check.properties :as prop]
    [clojure.test.check.clojure-test :refer [defspec]]
    )
  )

(defn- naive [nums]
  (reduce
    (fn [p i]
      (let [ni (nth nums i)
            pi (reduce
                 (fn [m j]
                   (let [nj (nth nums j)]
                     (if (> nj (* 2 ni)) (inc m) m)
                     )
                   )
                 0
                 (range i))
            ]
          (+ p pi)
        )
      )
    0
    (range (count nums)))
  )

(def good
  (prop/for-all [v (gen/not-empty (gen/vector gen/int))]
                (= (pairs/find-important-pairs v) (naive v))
                )
  )

(defspec check-good-sum
         100
         good)
