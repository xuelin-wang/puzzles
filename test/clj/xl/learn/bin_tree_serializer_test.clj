(ns xl.learn.bin-tree-serializer-test
  (:require
    [xl.bin-tree-serializer :as ser]
    [clojure.test :refer :all]
    [clojure.test.check :as tc]
    [clojure.test.check.generators :as gen]
    [clojure.test.check.properties :as prop]
    [clojure.test.check.clojure-test :refer [defspec]]
    )
  )

(def scalars (gen/frequency [[1 (gen/return nil)] [3 (gen/tuple gen/int (gen/return nil) (gen/return nil))]]))

(def compound (fn [inner-gen] (gen/frequency [[1 (gen/return nil)] [3 (gen/tuple gen/int inner-gen inner-gen)]])))

(def tree-gen (gen/recursive-gen compound scalars))

(def good-ser
  (prop/for-all [tree tree-gen]
                (let [ss (ser/ser tree)
                      tt (ser/deser ss)
                      ]
                  ;                  (println {:tree tree :seq ss :new-tree tt})
                  (= tree tt)
                  )
                )
  )

(defspec check-good-ser
         100
         good-ser)

(comment


  ;(tc/quick-check 100 good-min)
  )
