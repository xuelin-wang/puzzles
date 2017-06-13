(ns xl.lang.lazy
  (:import (clojure.lang IPending)))

(defn count-realized [s]
  (loop [s s, n 0]
    (if (instance? IPending s)
      (if (and (realized? s) (seq s))
        (recur (rest s) (inc n))
        n)
      (if (seq s)
        (recur (rest s) (inc n))
        n))))

(defn lazy-nats
  ([] (lazy-nats 0 (Long/MAX_VALUE)))
  ([n] (lazy-nats n (Long/MAX_VALUE)))
  ([n count]
   (if (pos? count)
     (lazy-seq (cons n (lazy-nats (inc n) (dec count))))
     nil)))

(defn half-lazy-nats
  [realized-count  nonrealized-count]
  (if (zero? realized-count)
    (lazy-nats 0 nonrealized-count)
    (let [ll (concat (into [] (range realized-count)) (lazy-nats realized-count nonrealized-count))
          _ (first ll)]
      ll)))





(defn samples []
  (doseq [x [(half-lazy-nats 0 0) (half-lazy-nats 0 1) (half-lazy-nats 1 0) (half-lazy-nats 1 1)
             (half-lazy-nats 2 0)  (half-lazy-nats 2 1)  (half-lazy-nats 2 2)  (half-lazy-nats 2 3)]
          ]
    (println "realized: " (count-realized x))
    )
  (doseq [x (take 4 (half-lazy-nats 2 4))]
    (print x ", ")
    )
  )

;(samples)



(defn next-results
  "Placeholder for function which computes some intermediate
  collection of results."
  [n]
  (range 1 n))

(defn build-result [n]
  (loop [counter 1
         results []]
    (if (< counter n)
      (recur (inc counter)
             (concat results (next-results counter)))
      results)))


(defn overflow-1 []
  (seq (build-result 5000)))
;(overflow-1)


(defn build-result-2 [n]
  (loop [counter 1
         results []]
    (if (< counter n)
      (recur (inc counter)
             (into results (next-results counter)))
      results)))


(defn overflow-2 []
  (seq (build-result-2 5000)))
;(overflow-2)


(defn build-result-3 [n]
  (mapcat #(range 1 %) (range 1 n)))
(defn overflow-3 []
  (seq (build-result-3 5000))
(println (take 21 (build-result-3 5000))))

;(overflow-3)


(comment

  (defn concat [x y]
    (lazy-seq
      (if-let [s (seq x)]
        (cons (first s) (concat (rest s) y))
        y)))

  )
