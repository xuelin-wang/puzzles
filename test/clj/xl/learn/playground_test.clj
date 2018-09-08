(ns xl.learn.playground-test
  (:require [clojure.test :refer :all]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :refer [defspec]]
            )
  )

(def sort-id-prop
  (prop/for-all [v (gen/vector gen/int)]
                (= (sort v) (sort (sort v)))))

;(tc/quick-check 100 sort-id-prop)


(comment

  (defspec sort-id
           100 sort-id-prop
           )

  (defspec 100 prop-first-less-than-last)

  )


(def prop-first-less-than-last
  (prop/for-all [v (gen/not-empty (gen/vector gen/int))]
                (let [s (sort v)]
                  (< (first s) (last s))
                  )
                )
  )

(defspec prop-1-n 100 prop-first-less-than-last)


