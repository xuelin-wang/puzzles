(ns xl.cover
  (:require [clojure.spec :as s]
            [clojure.spec.gen :as g]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as tcg]
            [clojure.test.check.generators :as gen]
            [clojure.set])
  (:import [xl.learn AlgoX]))

(defn cover [rows cols]
  (AlgoX/cover nil))