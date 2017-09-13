(ns xl.median-two-sorted
  (:require [clojure.string]
            [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as g]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as tcg]
            [clojure.test.check.generators :as gen])
  )

(defn median
  "
  To find i, j that split l1 and l2, such that:
    abs((i + j) - (s1 + s2 - (i + j))) <= 1
    and l1[i] <= l2[j+1], l2[j] <= l1[i+1] (check-split)

  while (!check-split) {
  }

  "
  [l1 l2]
  (let [
        check1 (fn [i j a b]
                (and
                  (or (or (zero? i) (= j (count b))) (<= (nth a (dec i)) (nth b j)))
                  )
                )
        check2 (fn [i j a b]
                 (and (check1 i j a b) (check1 j i b a))
                 )
        max-len (max (count l1) (count l2))
        [a b] (if (= max-len (count l1)) [l1 l2] [l2 l1])
        m (count a)
        n (count b)
        len (+ m n)
        half-len (int (/ (+ m n) 2))
        [i j]
          (loop [j (int (/ n 2)) min-j 0 max-j n]
            (let [i (- half-len j)]
              (if (= min-j max-j)
                [i j]
                (if (check2 i j a b)
                  [i j]
                  (if (check1 j i b a)
                    (recur (int (Math/ceil (/ (+ j max-j) 2))) (inc j) max-j)
                    (recur (int (/ (+ j min-j) 2)) min-j (dec j))
                    )
                  )
                )
              )
            )
        get-max-left (fn [a i] (if (zero? i) (- Double/MAX_VALUE) (nth a (dec i))))
        get-min-right (fn [a i] (if (= i (count a)) (Double/MAX_VALUE) (nth a i)))
        ]
    (let [max-left (max (get-max-left a i) (get-max-left b j))
          min-right (min (get-min-right a i) (get-min-right b j))
          ]
      (if (zero? (rem len 2))
        (double (/ (+ max-left min-right) 2))
        min-right
        )
      )
    )
  )

(comment
  (doseq [[x y]
          [
           [[1] [2]]
           [[2] [1]]
           [[1 3] [2]]
           [[1 2] [3]]
           [[1 3 5 8 11 13 15 17 19] [16 17 18 19]]
           ]]
    (println "r:" x "\ny:" y "\nmedian:" (median x y) "\n")
    )

  )

(median [-1.0] [1.0])

(def gen-sorted-vec ())

(s/def ::sorted-numbers
  (s/with-gen
    #(and
       (vector %)
       (not (empty? %))
       (every?
         (fn [x] (and (double? x) (not (Double/isNaN x)) (not (Double/isInfinite x))))
         %)
       (apply <= %)
       )
    #(gen/fmap (fn [x] (into [] (sort x))) (g/vector
                                             (g/such-that (fn [x]
                                                            (and (not (Double/isInfinite x)) (not (Double/isNaN x))))
                                                          (g/double)
                                                          )
                                             ))
    )
  )

(s/fdef median
        :args (s/cat :l1 ::sorted-numbers :l2 ::sorted-numbers)
        :ret number?
        :fn (s/and
              #(let [ret (:ret %)
                     l1 (-> % :args :l1)
                     l2 (-> % :args :l2)
                     l (sort (concat l1 l2))
                     len (count l)
                     mid (let [m (int (/ len 2))
                               ]
                           (if (even? len)
                             (double (/ (+ (nth l (dec m)) (nth l m)) 2))
                             (nth l m)
                             )
                           )
                     ]
                 (= ret mid)
                 )
              ))


