(ns xl.friends
  (:require [clojure.spec :as s]
            [clojure.spec.gen :as g]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as tcg]
            [clojure.set :as set]
            ))

"Given number of friends and their relationship, return number of friends circles"

(defn- root
  "return root and depth from query node to root"
  [arr a]
  (loop [k a depth 0]
    (let [v (nth arr k)]
      (if (= k v) [k depth] (recur v (inc depth))
        )
      )
    )
  )

(defn- init-arr [n]
      (let [arr (make-array Integer/TYPE n)]
        (doseq [i (range n)]
          (aset arr i i))
        arr
        )
      )

(defn- mk-tree
  "returns a tree in array representation. Simpler than mk-tree 2, but tree may degenerate a chain because balance is done only by
  checking depth from search node to root"
  [n rels]
      (let [arr (init-arr n)
            _
            (reduce
              (fn [_ [a b]]
                (let [[[root-a depth-a] [root-b depth-b]] (map (partial root arr) [a b])]
                  ; try to balance to minimize root seeking cost
                  (if (<= depth-a depth-b) (aset arr root-a root-b) (aset arr root-b root-a))
                  )
                )
              nil
              rels)
            ]
        arr
        )
      )

; use heuristicly balanced tree
(defn count-circles [n rels]
  (let [arr (mk-tree n rels)]
    (apply + (map-indexed #(if (= %1 (nth arr %2)) 1 0) arr))
    )
  )

(defn- root2
  "return root"
  [arr a]
  (loop [k a]
    (let [v (nth arr k)]
      (if (= k v) k (recur v)
                  )
      )
    )
  )

(defn- mk-tree2
  "returns a tree in array representation."
  [n rels]
  (let [arr (init-arr n)
        _
        (reduce
          (fn [depth-map [a b]]
            (let [[root-a root-b] (map (partial root2 arr) [a b])
                  [depth-a depth-b] (map
                                      #(let [depth (depth-map %)]
                                         (if depth depth 0))
                                      [a b])
                  use-root-a? (> depth-a depth-b)]
              ; try to balance to minimize root seeking cost
              (if use-root-a? (aset arr root-b root-a) (aset arr root-a root-b))
              (if use-root-a?
                (assoc depth-map root-a (max (inc depth-b) depth-a))
                (assoc depth-map root-b (max (inc depth-a) depth-b))
                )
              )
            )
          {}                                                ;map from node to tree depth
          rels)
        ]
    arr
    )
  )

; use perfect balancing tree
(defn count-circles2 [n rels]
  (let [arr (mk-tree2 n rels)]
    (apply + (map-indexed #(if (= %1 (nth arr %2)) 1 0) arr))
    )
  )


(defn naive-count [n rels]
  (let [
        find-set (fn [sets a] (first (filter #(% a) sets)))
        sets
        (reduce
          (fn [sets [a b]]
            (let [remove-from-sets (fn [removed] (remove #(removed %) sets))
                  [set-a set-b] (map (partial find-set sets) [a b])]
              (conj
                (remove-from-sets (if (= set-a set-b) #{set-a} #{set-a set-b}))
                (set/union #{a} #{b} set-a set-b))
              )
            )
          []
          rels
          )
        loners-cnt
          (count (filter (complement #(find-set sets %)) (range n)))
        ]
        (+ loners-cnt (count sets))
    )
  )


; spec def for automatic test check
(s/def ::people-count (s/int-in 8 10))

;(s/def ::g0-7-pair (s/tuple (s/int-in 0 7) (s/int-in 0 7)) )

(s/def ::g0-7pairs (s/coll-of (s/tuple (s/int-in 0 7) (s/int-in 0 7))) )

;(s/def ::rels (s/with-gen (s/coll-of (s/tuple nat-int? nat-int?)) #(s/gen (s/coll-of ::g0-7s))))

(s/fdef count-circles
        :args (s/cat :n ::people-count :rels ::g0-7pairs)
        :ret nat-int?
        :fn (s/and #(= (:ret %) (naive-count (-> % :args :n) (-> % :args :rels)))
                   ))

(s/fdef count-circles2
        :args (s/cat :n ::people-count :rels ::g0-7pairs)
        :ret nat-int?
        :fn (s/and #(= (:ret %) (naive-count (-> % :args :n) (-> % :args :rels)))
                   ))





; tests
(defn- samples []
  (doseq [[n rels]
          [
            [4 [[1 2]]]
           [4 [[1 2] [0 1]]]
           [6 [[0 1] [1 2] [2 3] [3 4]]]
           [8 [[0 1] [1 2] [2 3] [3 4]]]
           [8 [[0 1] [1 2] [2 3] [3 4] [7 0] [6 5]]]
           ]
          ]
    (let [cnt (count-circles n rels)]
      (println "\nn: " n ", rels: " rels)
      (println "circles: " cnt)
      (println "naive count: " (naive-count n rels))
      )
    )
  )

; tests
;(samples)

; commented tests
(comment

  (defn- sample-trees []
    (doseq [[n rels]
            [
             ; [4 [[1 2]]]
             ;[4 [[1 2] [0 1]]]
             [6 [[0 1] [1 2] [2 3] [3 4]]]
             ]
            ]
      (let [tree (mk-tree n rels)]
        (println "\nn: " n ", rels: " rels)
        (doall
          (map #(print (nth tree %) ", ") (range n))
          )
        )
      )
    )

  (defn- sample-trees2 []
    (doseq [[n rels]
            [
             ; [4 [[1 2]]]
             ;[4 [[1 2] [0 1]]]
             [6 [[0 1] [1 2] [2 3] [3 4]]]
             ]
            ]
      (let [tree (mk-tree2 n rels)]
        (println "\nn: " n ", rels: " rels)
        (doall
          (map #(print (nth tree %) ", ") (range n))
          )
        )
      )
    )

  (sample-trees2)


  )

