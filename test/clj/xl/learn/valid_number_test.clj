(ns xl.learn.valid-number-test
  (:require
    [clojure.test :refer :all]
    [clojure.test.check :as tc]
    [clojure.test.check.generators :as gen]
    [clojure.test.check.properties :as prop]
    [clojure.test.check.clojure-test :refer [defspec]]
    [xl.valid-number :as vn])
  )

(def check-valid-number-prop
  (prop/for-all [[a v] (gen/tuple (gen/vector gen/char-alpha 1 3) (gen/vector gen/char-alpha-numeric))]
                (let [
                      ss (apply str (shuffle (into a v)))]
                  (not (vn/is-no-sign-int? ss))
                  )
))

(defspec prop-number-negative 100 check-valid-number-prop)
