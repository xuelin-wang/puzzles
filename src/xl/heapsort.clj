(ns xl.heapsort
  (:require [clojure.spec :as s])
  )

(defn- swap-val [^doubles ns a b]
  (let [tmp (nth ns a)]
    (aset ns a (nth ns b))
    (aset ns b tmp)
    )
  )

(defn sift-down [^doubles ns start end]
  (loop [curr start]
    (when (< curr end)
      (let [left (int (dec (* (inc curr) 2)))
            ]
        (when (and (< left end) (< (nth ns left) (nth ns curr)))
          (swap-val ns curr left)
          (sift-down ns left end)
          )
        )
      (let [right (int (* (inc curr) 2))
            ]
        (when (and (< right end) (< (nth ns right) (nth ns curr)))
          (swap-val ns curr right)
          (sift-down ns right end)
          )
        )
      )
    )
  )

(defn heapify [^doubles ns]
  (let [len (count ns)]
    (loop [curr (dec (int (Math/ceil (/ len 2))))]
      (when-not (neg? curr)
        (sift-down ns curr len)
        (recur (dec curr))
        )
      )
    )
  )

(defn- internal-heap-sort [^doubles ns]
  (heapify ns)
  (loop [end (dec (count ns))]
    (when (pos? end)
      (swap-val ns 0 end)
      (sift-down ns 0 end)
      (recur (dec end))
      )
    )
  )

(defn heap-sort [xs0]
  (if (<= (count xs0) 1)
    xs0
    (let [xs (double-array xs0)]
      (internal-heap-sort xs)
      (seq xs)
      )
    )
  )

(doseq [ns [
            [1.0 -1.0 1.0 -1.0 1.0]
            ]]
  (println "ns: " (seq ns))
  (println "sorted: " (heap-sort ns))
  )

(defn ordered? [xs]
  (if (<= (count xs) 1)
    true
    (let [[x & rest] xs]
      (and (ordered? rest) (>= x (first rest)))
      )
    )
  )

(defn- remove-first [xs y]
  (if (empty? xs)
    xs
    (let [[x & rest] xs]
      (if (= x y)
        rest
        (cons x (remove-first rest y))
        )
      )
    )
  )

(defn same-items? [xs ys]
  (and
    (= (count xs) (count ys))
    (or
      (empty? xs)
      (let [[x & rest-xs] xs]
        (and (some #{x} ys) (same-items? rest-xs (remove-first ys x)))
        )
      )
    )
  )

(s/fdef heap-sort
        :args (s/cat :ns (s/coll-of (s/double-in :NaN? false :infinite? false)))
        :ret (s/coll-of  (s/double-in :NaN? false :infinite? false))
        :fn (s/and
              #(ordered? (:ret %))
              #(same-items? (-> % :args :ns) (:ret %))
              ))
