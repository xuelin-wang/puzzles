(ns xl.regex-recursion
  (:require [clojure.spec :as s]
            [clojure.spec.gen :as g]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as tcg]
            [clojure.test.check.generators :as gen]
            [clojure.set]))

(defn mat [pat ii ss jj]
  (let [pat-len (count pat)
        ss-len (count ss)]
    (cond
      (= ii pat-len) (= jj ss-len)
      :else (let [pat-ch (nth pat ii)
            with-star (and (< ii (dec pat-len)) (= \* (nth pat (inc ii))))
            ss-ch (if (>= jj ss-len) nil (nth ss jj))
            match-first (and (some? ss-ch) (or (= pat-ch ss-ch) (= pat-ch \.)))
            ]
          (if with-star
            (let [case-zero (mat pat (+ ii 2) ss jj)
                  ]
              (if match-first
                (or case-zero (mat pat (+ ii 2) ss (inc jj)) (mat pat ii ss (inc jj)))
                case-zero
                )
              )
            (if match-first (mat pat (inc ii) ss (inc jj)) false)
            )
        )
      )
    )
  )

(defn match [pat0 ss0]
  (let [pat (seq pat0)
        pat-len (count pat)
        ss (seq ss0)
        ss-len (count ss)
        ]
      (mat pat 0 ss 0)
    )
  )

(defn run-samples []
  (doseq [ss-pat [
                  ["a" "aa"]
                  ["aa" "aa"]
                  ["aaa" "aa"]
                  ["aa" "a*"]
                  ["aa" ".*"]
                  ["ab" ".*"]
                  ["aab" "c*a*b"]
                  ]]
    (let [[ss pat] ss-pat]
      (println "pat:" pat ", ss:" ss)
      (println "matched: " (match pat ss))
      )
    )
  )

(match ".*T" "")

(defn xnor [a b]
  (or (and a b) (and (not a) (not b)))
  )

(def letters
  (let [get-chars #(set (map (fn [ii] (str (char ii))) (range (int %1) (inc (int %2)))))]
    (clojure.set/union (get-chars \a \z) (get-chars \A \Z))
    )
  )

(def letters-or-dot
  (clojure.set/union letters #{"."})
  )

(def pat0
  (g/fmap (fn [x] (apply str x)) (g/tuple (s/gen letters-or-dot) (s/gen #{"" "*"})))
  )

(def pat
  #(g/fmap (fn [x] (apply str x)) (g/vector pat0))
  )

(s/def ::pat
  (s/with-gen
    string?
    pat
    ))

(s/fdef match
        :args (s/cat :pat ::pat :str string?)
        :ret boolean?
        :fn (s/and
              #(xnor
                 (:ret %)
                 (re-find (re-pattern (str "^" (-> % :args :pat) "$")) (-> % :args :str))
                 )))
