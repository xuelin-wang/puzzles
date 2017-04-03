(ns xl.substr
  (:require [clojure.string]
            [clojure.spec :as s]
            [clojure.spec.gen :as g]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as tcg]
            )
  )

(defn- longest_from [ss index]
  (if (clojure.string/blank? ss)
    ""
    (let [limit (dec (count ss))]
      (loop [right index]
        (cond
          (= right limit) (.substring ss index (inc right))
          (nat-int? (.indexOf (.substring ss index (inc right)) (int (.charAt ss (inc right))) ))
          (.substring ss index (inc right))
          :else (recur (inc right))

          )

        )
      )
    )
  )

(defn longest-nonrepeating [ss]
  (if (clojure.string/blank? ss)
    ""
    (loop [index 0 longest ""]
      (let [this-longest (longest_from ss index)
            new-longest (if (> (count this-longest) (count longest)) this-longest longest)
            ]
        (if (= index (dec (count ss)))
          new-longest
          (recur (inc index) new-longest)
          )
        )
      )
    )
  )

(s/def ::base (s/with-gen number? #(s/gen (set (range -3 3)))))
(s/def ::exp (s/with-gen nat-int? #(s/gen (set (range 0 10)))))
(defn unique-chars [s]
  (= (count s) (count (set (seq s))))
  )

(s/fdef longest-nonrepeating
        :args (s/cat :str string?)
        :ret string?
        :fn (s/and
              #(unique-chars (:ret %))
              #(.contains (-> % :args :str) (:ret %))
              #(let [ret (:ret %)
                     ss (-> % :args :str)
                     len (inc (count ret))
                     ]
                 (every? (fn [ii]  (not (unique-chars (.substring ss ii (+ ii len)))))
                         (range 0 (- (count ss) len)))
                 )
              ))


(comment
  (doseq [ss ["" "a" "ab" "aa" "abc" "aba" "aab" "abb" "aaa" "abcd" "abcb" "abca" "abbc"]]
    (println ss " has substr: " (longest-nonrepeating ss))
    )
  )
