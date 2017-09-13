(ns xl.puzzles
  (:require
        [clojure.test.check :as tc]
           [clojure.test.check.generators :as tcg]
           [clojure.test.check.properties :as tcp]
           [clojure.spec.alpha :as s]
           [clojure.spec.gen.alpha :as sg]
           )
  )

(defn contains-sum [ns s]
  (let [n (count ns)
        ss (sort ns)]
    (reduce (fn [result i]
              (if result (reduced true)
                         (reduce
                           (fn [result j]
                             (if result (reduced true)
                                        (reduce
                                          (fn [result k]
                                            (println "testing: " i j k)
                                            (println "       : " (doall (map #(nth ss %) [i j k])))
                                            (let [sum (apply + (map #(nth ss %) [i j k]))]
                                              (cond
                                                (< sum s) false
                                                (= sum s) (reduced true)
                                                (> sum s) (reduced false)
                                                )
                                              )
                                            )
                                          false
                                          (range (inc j) n))
                                        )
                             )
                           false
                           (range (inc i) (dec n)))

                         )
              )
            false
            (range (- n 2))

            )
    )
  )


(defn preoder-leaves
  "patterns:
  single node: yes.
  last node: yes
  1 0 -1: 0 is not leaf
  1 0 0.5: 0 is not leaf
  1 0 2: 0 is leaf

  -1 - -r: 0 is not leaf
  -1 0 1: if exists 0 < p < 1, 0 is leaf, else, no"

  [nums]
  (cond
    (empty? nums) []
    (= (count nums) 1) nums
    :else (loop [leaves [] index 1]
            (let [p (nth nums (dec index))
                  curr (nth nums index)]
              (if
                (= index (dec (count nums))) (conj leaves curr)
                                             (let [nn (nth nums (inc index))
                                                   new-leaves
                                                   (cond
                                                     (and (< curr p) (> nn p)) (conj leaves curr)
                                                     (and (> curr p) (some #(and (< curr %) (< % nn)) (take index nums))) (conj leaves curr)
                                                     :else leaves
                                                     )
                                                   ]
                                               (recur new-leaves (inc index))
                                               )
                                             )

              )
            )
    )
  )

(s/def ::point-2d (s/tuple number? number?))
(s/def ::rec (s/and
               (s/tuple ::point-2d ::point-2d)
               (fn [[[x1 y1] [x2 y2]]]
                 (and (< x1 x2) (< y1 y2))
                 )
               ))

(defn- contains-point [[[x1 y1] [x2 y2]] [x y]]
  (let [between (fn [x x1 x2]
                  (let [begin-x (min x1 x2)
                        end-x (max x1 x2)
                        ]
                    (and (<= begin-x x) (<= x end-x)
                    )))
        ]
    (and (between x x1 x2) (between y y1 y2))
    )
  )

(defn- intersect-line? [[a1 a2] [b1 b2]]
  (and (< b1 a2) (< a1 b2))
  )

(defn- intersect-rect? [[[x11 y11] [x12 y12]] [[x21, y21] [x22 y22]]]
  (and (intersect-line? [x11 x12] [x21 x22])
       (intersect-line? [y11 y12] [y21 y22])
       )
  )

(defn- contain-rect? [rec1 [bottom-left top-right :as rec2]]
  (and (contains-point rec1 bottom-left) (contains-point rec1 top-right))
  )

(defn expand-rect [[[x1 y1] [x2 y2] :as rect] rects]
  (let [x1s (sort (map #(first (first %)) rects))
        x2s (sort (map #(first (second %)) rects))
        y1s (sort (map #(second (first %)) rects))
        y2s (sort (map #(second (second %)) rects))
        next-val (fn [val vals]
                   (reduce
                     (fn [result next-val]
                       (if (< val next-val) (reduced next-val) val)
                       )
                     val
                     vals))

        ]
    ()
    )
  )

(defn merge-rect [rects]
  )

(defn clip-by-rect [[[x11 y11] [x12 y12] :as rec1] [[x21 y21] [x22 y22] :as rec2]]
  (if
    (intersect-rect? rec1 rec2)
    (let [up-rect (if (> y22 y12) [[x21 y12] [x22 y22]] nil)
          down-rect (if (< y21 y11) [[x21 y21] [x22 y11]] nil)
          left-rect (if (< x21 x11) [[x21 (max y11 y21)] [x11 (min y12 y22)]] nil)
          right-rect (if (> x22 x12) [[x12 (max y11 y21)] [x22 (min y12 y22)]] nil)
          ]
      (filter (complement nil?) [up-rect down-rect left-rect right-rect])
      )
    [rec2]
    ))

(s/fdef clip-by-rect
        :args (s/cat :rec1 ::rec :rec2 ::rec)
        :ret (s/coll-of ::rec)
        :fn #(let [rec1 (-> % :args :rec1)
                   rec2 (-> % :args :rec2)
                   ret (:ret %)
                   ]
               (and
                 (every? (fn [rect] (contain-rect? rec2 rect)) ret)
                                  (every? (fn [rect] (not (intersect-rect? rec1 rect))) ret)
                 )
               )
        )


(defn clip-by-rects [clip-rects clipped-rect]
  (reduce
    (fn [clipped-rects clip-rect]
      (reduce
        (fn [results clipped-rect]
          (concat results (clip-by-rect clip-rect clipped-rect))
          )
        []
        clipped-rects
        )
      )
    [clipped-rect]
    clip-rects)
  )

(s/fdef clip-by-rects
        :args (s/cat :clip-rects (s/coll-of ::rec) :clipped-rect ::rec)
        :ret (s/coll-of ::rec)
        :fn #(let [clip-rects (-> % :args :clip-rects)
                   clipped-rect (-> % :args :clipped-rect)
                   ret (:ret %)
                   ]
               (and
                 (every? (fn [rect] (contain-rect? clipped-rect rect)) ret)
                                  (every? (fn [rect] (every? (fn [clip-rect] (not (intersect-rect? rect clip-rect)))
                                                             clip-rects) )
                                          ret)
                 )
               )
        )




(defn- compare-rects [[[x11, y11] [x12 y12]] [[x21, y21] [x22 y22]]]
  (cond
    (not= x11 x21) (x11 - x21)
    (not= y11 y21) (y11 - y21)
    (not= x12 x22) (x12 - x22)
    :else (y12 - y22)
    )
  )

(defn non-overlapping-recs [rects]
  (loop [results [] remaining rects]
    (if
      (empty? remaining) results
                         (let [rect (first remaining)]
                           (recur (concat results (clip-by-rects results rect)) (rest remaining))
                           )
                         )
    )
  )

(defn midian-of-unsorted-arr [nums] nil)

;; (println (preoder-leaves [5 3 2 4 8 7 9]))

(defn- show-rect [[[x1 y1] [x2 y2]]]
  (println x1 "," y1 " - " x2 "," y2)
  )

(comment
  (let [rects
        [
         [[0 0] [3 3]]
         [[1 1] [4 4]]
         [[1 1] [2 5]]
         [[1 1] [5 2]]
         ]
        ]
    (dorun (map (fn [rect] (show-rect rect))
                (non-overlapping-recs rects)
                ))
    )
  )

;; (s/exercise-fn `clip-by-rect)


(defn- get-min-col [row start-col end-col]
  (loop [min-val (Integer/MAX_VALUE) min-col -1 col start-col]
    (if (>= col end-col)
      min-col
      (let [col-val (nth row col)
            next-col (inc col)
            ]
        (if (< col-val min-val)
          (recur col-val col next-col)
          (recur min-val min-col next-col)
          )
        )
      )
    )
  )

(s/fdef get-min-col
        :args (s/cat :row (s/coll-of int?) :start-col nat-int? :end-col pos?)
        :ret nat-int?
        :fn #(let [row (-> % :args :row)
                   start-col (-> % :args :start-col)
                   end-col (-> % :args :end-col)
                   ret (:ret %)
                   min-val (nth row ret)
                   ]
               (and
                 (<= start-col ret)
                 (< ret end-col)
                 (every? (fn [x] (>= x min-val)) row)
                 )
               )
        )



(defn- monge-min-cols [matrix start-row end-row start-col end-col row-gap in-min-cols]
  (if (>= (+ start-row row-gap) end-row)
    (let [min-col (get-min-col (nth matrix start-row) start-col end-col)
          min-cols (transient in-min-cols)
          _ (assoc! min-cols start-row min-col)
          ]
      (persistent! min-cols)
      )
    (let [
          row-gap-2 (* 2 row-gap)
          min-cols-0 (monge-min-cols matrix start-row end-row start-col end-col row-gap-2 in-min-cols)
          min-cols (transient min-cols-0)
          ]
      (loop [row (+ start-row row-gap) curr-start-col (nth min-cols start-row)]
        (if (>= row end-row)
          (persistent! min-cols)
          (let [next-row (+ row row-gap)
                curr-end-col (if (< next-row end-row) (inc (nth min-cols next-row)) end-col)
                min-col (get-min-col (nth matrix row) curr-start-col curr-end-col)]
                (assoc! min-cols row min-col)
                (recur (+ row row-gap-2) min-col)
            )
          )
        )
      )
    )
  )

(defn min-cols [matrix]
    (let [row-count (count matrix)
          col-count (count (first matrix))
          min-cols (monge-min-cols matrix 0 row-count 0 col-count 1
                                   (into [] (take row-count (repeat -1))))
          ]
      min-cols
        )
      )

;;(s/exercise-fn `get-min-col)

(comment
  (let [matrix [[37 23 22 32]
                [21 7 6 10]
                [53 34 30 31]
                [32 13 9 6]
                [43 21 15 8]
                ]]
    (do
      (println "matrix:")
      (println
        (clojure.string/join "\n" (map #(clojure.string/join ", " %) matrix))
        )
      (println (clojure.string/join "\n" (min-cols matrix)))
      )
    )

  )
